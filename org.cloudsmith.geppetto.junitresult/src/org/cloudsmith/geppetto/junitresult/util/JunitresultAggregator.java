/**
 * Copyright (c) 2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.junitresult.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.List;

import org.cloudsmith.geppetto.junitresult.Error;
import org.cloudsmith.geppetto.junitresult.JunitResult;
import org.cloudsmith.geppetto.junitresult.JunitresultFactory;
import org.cloudsmith.geppetto.junitresult.Testcase;
import org.cloudsmith.geppetto.junitresult.Testrun;
import org.cloudsmith.geppetto.junitresult.Testsuite;
import org.cloudsmith.geppetto.junitresult.Testsuites;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.internal.filesystem.local.LocalFileSystem;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.Path;

import com.google.common.collect.Lists;

/**
 * Aggregates JUnit result output stored in XML files under a given directory into a single testsuite
 * while cleaning up and rearranging the combined content.
 * 
 */
public class JunitresultAggregator {

	private static final FileFilter directoryFilter = new FileFilter() {

		@Override
		public boolean accept(File f) {
			return f.isDirectory() && !f.getName().equals(".svn");
		}

	};

	private static final FilenameFilter xmlFileFilter = new FilenameFilter() {

		@Override
		public boolean accept(File dir, String name) {
			return name.endsWith(".xml") && new File(dir, name).isFile();
		}

	};

	private static boolean isSymlink(File f) {
		return LocalFileSystem.getInstance().fromLocalFile(f).fetchInfo().getAttribute(EFS.ATTRIBUTE_SYMLINK);
	}

	private File reportDir;

	private File rootDir;

	private Testsuite rootSuite;

	/**
	 * @param rootSuite
	 * @param f
	 * @param e
	 */
	private void addExceptionalCase(File f, Exception e) {
		Testcase tc = JunitresultFactory.eINSTANCE.createTestcase();
		tc.setName("Error loading: " + f.getAbsolutePath());

		Error error = JunitresultFactory.eINSTANCE.createError();
		StringBuilder builder = new StringBuilder();
		builder.append("Can not load junit xml result from file: ").append(f.getAbsolutePath()).append("\n");
		builder.append("Caused by: ").append(e.toString());
		error.setMessage(e.getMessage());
		error.setValue(builder.toString());

		tc.setNegativeResult(error);
		rootSuite.getTestcases().add(tc);
	}

	/**
	 * Creates an aggregated JunitResult from all JUnit XML files discovered in a location under the
	 * given <code>reportDir</code>. The resulting JunitResult will have a name that is based on the path
	 * from the given rootDir to the discovered file (including the discovered file's name).
	 * 
	 * The following rules are applied:
	 * <ul>
	 * <li>If the name of the file starts with "SPEC-" this part is dropped.</li>
	 * <li>The suffix ".xml" is not included.</li>
	 * <li>If a loaded testsuide contains testcases and these have different classname attributes, the testsuite is repackaged as one suite per
	 * classname. e.g. if <code>./mystack/allroottests.xml</code> contains testcases from <code>./spec/x_spec.rb</code>, and <code>./y_spec.rb</code>
	 * (and each has testcases <code>a</code> and <code>b</code>) the resulting strucure is:
	 * 
	 * <pre>
	 * mystack : suite 
	 *   allroottests : suite
	 *     spec/x_spec : suite
	 *       a : tc
	 *       b : tc
	 *     y_spec : suite
	 *       a : tc
	 *       b : tc
	 * </pre>
	 * 
	 * </li>
	 * <li>If a loaded testsuite has the same name as a single embedded testcase, the structure is flattented into a single testcase. This handles the
	 * output from ci_reporter. As an example, if tests are named the same way in two different <code>..._spec.rb</code> files Game#score in a_spec.rb
	 * and b_spec.rb the result is:
	 * 
	 * <pre>
	 * mystack : suite
	 *   Game : suite
	 *   Game.0 : suite
	 *   Game-score : suite
	 *     Game#score : tc
	 *   Game-score.0 :suite
	 * </pre>
	 * 
	 * Without the flattening, each tc would be wrapped in a testsuite named the same way as the tc. Note that ci_reporter solves non unique naming by
	 * appending a ".n" to the filenames, but this is not applied inside the resulting xml. (The aggregator's method of using the name of the file as
	 * the name of the suite solves this issue - even if it is impossible to figure out where it comes from, it is at least unique. This is really a
	 * ci_reporter issue).</li>
	 * <li>If the given reportDir, and rootDir are the same the last segment of the rootDir is used as the name of the root testsuite.</li>
	 * <li>If a loaded testsuite's timestamp is missing, it will be derived from the file ctime it is loaded from.</li>
	 * </ul>
	 * <p>
	 * When scanning, all ".xml" files in the structure are assumed to be JUnit reports. Failure to load a file will insert a synthetic testsuite
	 * named after the file, and with a synthetic failed testcase.
	 * <p>
	 * 
	 * @param reportDir
	 * @param rootDir
	 * @return
	 */
	public JunitResult aggregate(File reportDir, File rootDir) {
		// check parameters
		if(!reportDir.getAbsolutePath().startsWith(rootDir.getAbsolutePath()))
			throw new IllegalArgumentException("given directory must be same or subdirectoryu of given root");

		this.reportDir = reportDir;
		this.rootDir = rootDir;

		this.rootSuite = createRootSuite(reportDir, rootDir);

		processingXmlFiles: for(File f : findXMLFiles(reportDir)) {
			JunitResult loaded = null;
			try {
				loaded = JunitresultLoader.loadFromXML(f);
				if(loaded instanceof Testrun) // a bit strange - this is an eclipse JUnit result
					processTestrun(f, ((Testrun) loaded));
				else if(loaded instanceof Testsuite)
					processTestsuite(f, ((Testsuite) loaded));
				else if(loaded instanceof Testsuites)
					processTestsuites(f, ((Testsuites) loaded));
				else
					throw new RuntimeException("Internal error: expected testrun, testsuite or testsuites");
			}
			catch(IOException e) {
				addExceptionalCase(f, e);
				continue processingXmlFiles;
			}
			catch(RuntimeException e) {
				addExceptionalCase(f, e);
				continue processingXmlFiles;
			}

		}
		aggregateCountAndTime();
		return rootSuite;
	}

	/**
	 * Iterates over the rootSuite and ensures that all counts and times are aggregated.
	 */
	private void aggregateCountAndTime() {
		// TODO Auto-generated method stub

	}

	/**
	 * Collects file matching filter while skipping all symbolically linked files.
	 * 
	 * @param root
	 * @param filter
	 * @param result
	 */
	private void collectFiles(File root, FilenameFilter filter, List<File> result) {
		for(File f : root.listFiles(filter))
			if(!isSymlink(f))
				result.add(f);
		for(File f : root.listFiles(directoryFilter))
			if(!isSymlink(f))
				collectFiles(f, filter, result);
	}

	private Testsuite createRootSuite(File reportDir, File rootDir) {
		IPath p = new Path(reportDir.getAbsolutePath());
		IPath relative = p.makeRelativeTo(new Path(rootDir.getAbsolutePath()));
		Testsuite testsuite = JunitresultFactory.eINSTANCE.createTestsuite();
		testsuite.setName(relative.toString());
		return testsuite;
	}

	private List<File> findFiles(File root, FilenameFilter filter) {
		List<File> result = Lists.newArrayList();
		collectFiles(root, filter, result);
		return result;

	}

	private List<File> findXMLFiles(File root) {
		return findFiles(root, xmlFileFilter);
	}

	/**
	 * @param f
	 * @param testrun
	 */
	private void processTestrun(File f, Testrun testrun) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param f
	 * @param testsuite
	 */
	private void processTestsuite(File f, Testsuite testsuite) {
		// TODO Auto-generated method stub

	}

	/**
	 * @param f
	 * @param testsuites
	 */
	private void processTestsuites(File f, Testsuites testsuites) {
		// TODO Auto-generated method stub

	}

}
