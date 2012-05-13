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
import java.text.DecimalFormat;
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
import org.eclipse.emf.common.util.EList;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;

/**
 * Aggregates JUnit result output stored in XML files under a given directory into a single testsuite
 * while cleaning up and rearranging the combined content.
 * 
 */
public class JunitresultAggregator {

	public static class Stats {
		private int count;

		private int errors;

		private int failures;

		private int skipped;

		private int disabled;

		private double time;

		Stats() {
			// all fields are 0
		}

		Stats(int c, int e, int f, int s, int d, double t) {
			count = c;
			errors = e;
			failures = f;
			skipped = s;
			disabled = d;
			time = t;
		}

		Stats add(Stats s) {
			return new Stats(count + s.count, errors + s.errors, failures + s.failures, skipped + s.skipped, disabled +
					s.disabled, time + s.time);
		}
	}

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

	private Testsuite rootSuite;

	private Path rootPath;

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
		error.setValue(builder.toString());
		error.setMessage(e.getMessage());

		tc.getErrors().add(error);
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
		// must be called to ensure that the LocalFileSystem is instantiated
		EFS.getLocalFileSystem();

		// check parameters
		if(!reportDir.getAbsolutePath().startsWith(rootDir.getAbsolutePath()))
			throw new IllegalArgumentException("given directory must be same or subdirectoryu of given root");

		// this.reportDir = reportDir;
		// this.rootDir = rootDir;
		this.rootPath = new Path(rootDir.getParentFile().getAbsolutePath());

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
		// fix-up all counts and time
		aggregateCountAndTime();
		return rootSuite;
	}

	/**
	 * Iterates over the rootSuite and ensures that all counts and times are aggregated.
	 */
	private void aggregateCountAndTime() {
		updateStats(rootSuite);

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
		IPath relative = p.makeRelativeTo(rootPath);
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

	private String formatDecimal(double d) {
		DecimalFormat myFormatter = new DecimalFormat("0*.0*");
		return myFormatter.format(d);
	}

	private boolean isExtraRspecFormatterStyle(Testsuite testsuite) {
		EList<Testcase> tcs = testsuite.getTestcases();
		for(Testcase tc : tcs) {
			String classname = tc.getClassname();
			if(classname != null && classname.endsWith(".rb"))
				return true;
		}
		return false;
	}

	private boolean isSuiteWrappingSingleCase(Testsuite testsuite) {
		EList<Testcase> tcs = testsuite.getTestcases();
		if(tcs.size() != 1)
			return false;
		Testcase tc = tcs.get(0);
		String tsname = testsuite.getName();
		String tcname = tc.getName();
		if(tsname == null || tcname == null)
			return false;
		return tsname.equals(tcname);
	}

	/**
	 * Wraps the content of the 'testrun' into a new 'testsuite' named after the file, and
	 * adds the resulting container to the root suite.
	 * 
	 * @param f
	 * @param testrun
	 */
	private void processTestrun(File f, Testrun testrun) {
		Testsuite containerSuite = JunitresultFactory.eINSTANCE.createTestsuite();
		containerSuite.setName(suitename(f));
		containerSuite.getTestsuites().addAll(testrun.getTestsuites());
		rootSuite.getTestsuites().add(containerSuite);
	}

	/**
	 * Processes the given testsuite; if it is produced by "rspec extra formatters", the content is
	 * rewrapped based on the source spec.rb file, and if produced by ci_reporter where each tc is
	 * wrapped in a testsuite with the same name, the testsuite is renamed after the file.
	 * 
	 * The resulting container (or container(s) in case of "rspec extra formatters" style) is/are added
	 * to the root container.
	 * 
	 * @param f
	 * @param testsuite
	 */
	private void processTestsuite(File f, Testsuite testsuite) {
		// Is this a testsuite with testcases where testcases have a classname that is a reference to
		// a .rb spec file?
		if(isExtraRspecFormatterStyle(testsuite)) {
			// create a new testsuite to act as the container for all contained (computed suites).
			// this container is named after the path/file
			Testsuite containerSuite = JunitresultFactory.eINSTANCE.createTestsuite();
			containerSuite.setName(suitename(f));

			// create one suite per source classname and add test cases belonging to that suite
			Multimap<String, Testcase> map = ArrayListMultimap.create();
			for(Testcase tc : testsuite.getTestcases()) {
				String key = tc.getClassname();
				if(key == null || key.length() == 0 || !key.endsWith(".rb"))
					key = "unspecified-source";
				map.put(key, tc);
			}
			for(String key : map.keySet()) {
				Testsuite suitePerClass = JunitresultFactory.eINSTANCE.createTestsuite();
				// suite name is classname without leading ./ and trailing .rb
				String suitename = key.substring(key.startsWith("./")
						? 2
						: 0, //
					key.length() - (key.endsWith(".rb")
							? 3
							: 0));
				suitePerClass.setName(suitename);
				// add all testcases from the same source (i.e. same "classname")
				suitePerClass.getTestcases().addAll(map.get(key));
				containerSuite.getTestsuites().add(suitePerClass);
			}
			rootSuite.getTestsuites().add(containerSuite);
			// all work done
		}
		else if(isSuiteWrappingSingleCase(testsuite)) {
			// this is the ci_reporter style where each individual tc is wrapped in a testsuite
			// and saved in a separate file. The name of the file is important as it dissambiguifies
			// between tests with same name from different spec sources (the source is *not* included in
			// ci_reporter's output).

			// Simply rename the wrapping testsuite to reflect the name of the file
			testsuite.setName(suitename(f));
			rootSuite.getTestsuites().add(testsuite);
			// all work done
		}
		else {
			// this is some form of testsuite that is not known - simply include it
			// make sure it has a name, if not, name it after the file
			String suitename = testsuite.getName();
			if(suitename == null || suitename.length() < 1)
				testsuite.setName(suitename(f));
			rootSuite.getTestsuites().add(testsuite);
			// all work done
		}

	}

	/**
	 * Wraps the content of the 'testsuites' into a new 'testsuite' named after the file, and
	 * adds the resulting container to the root suite.
	 * 
	 * @param f
	 * @param testsuites
	 */
	private void processTestsuites(File f, Testsuites testsuites) {
		Testsuite containerSuite = JunitresultFactory.eINSTANCE.createTestsuite();
		containerSuite.setName(suitename(f));
		containerSuite.getTestsuites().addAll(testsuites.getTestsuites());
		rootSuite.getTestsuites().add(containerSuite);
	}

	/**
	 * Produces a suite-name based on the name of the path of the given f, relative to the given root.
	 * 
	 * @param f
	 * @return
	 */
	private String suitename(File f) {
		IPath p = new Path(f.getAbsolutePath());
		IPath relative = p.makeRelativeTo(rootPath);
		relative = relative.removeFileExtension();
		return relative.toString();
	}

	// /**
	// * Returns the time as a double, or 0.0 if the given timeString is null, empty, or can not be parsed as
	// * a double number.
	// *
	// * @param timeString
	// * @return
	// */
	// private double time(String timeString) {
	// if(timeString == null || timeString.length() < 1)
	// return 0.0;
	// try {
	// // make sure value is not negative as this screws up aggregation
	// return Math.abs(Double.valueOf(timeString));
	// }
	// catch(NumberFormatException e) {
	// return 0.0;
	// }
	// }

	/**
	 * Produces a Stats instance populated with the data from the given testcase.
	 * 
	 * Junit4 allows multiple failures, errors and one skipped...
	 * There are no rules that describes how they are counted - it seems
	 * reasonable that:
	 * - the count is the number of tc in different states, not the number of individually logged errors
	 * - if there is a Skipped - then the TC counts as skipped
	 * - if there is an Error - the TC is counted as an error
	 * - else, if there is a Failure, it is counted as a Failure
	 * - else it is ok.
	 * 
	 * @param tc
	 * @return
	 */
	private Stats updateStats(Testcase tc) {
		Stats s = new Stats(1, 0, 0, 0, 0, tc.getTime());

		if(tc.getSkipped() != null)
			s.skipped = 1;
		else if(tc.getErrors().size() > 0)
			s.errors = 1;
		else if(tc.getFailures().size() > 0)
			s.failures = 1;

		return s;
	}

	public Stats updateStats(Testrun testrun) {
		Stats s = new Stats();
		for(Testsuite ts : testrun.getTestsuites())
			s = s.add(updateStats(ts));

		testrun.setTests(s.count);
		testrun.setErrors(s.errors);
		testrun.setFailures(s.failures);
		// there is only one "ignored" that counts tests that did not run
		// use that for tests reported as "disabled" or "skipped" (they are never both).
		testrun.setIgnored(s.disabled + s.skipped);

		// NOTE: "started" counts how many of the tests in the report that were actually started and
		// should have produced a result - this is different from the total number of testcases (i.e. "tests").
		// this is only known by the user of a "testrun" and can not be detected since there is no notion
		// of a "positive testcase" - the existence of a tc is interpreted as success if it is not skipped, has
		// an error, or failure.
		// testrun.setStarted(??)

		// testrun does not have concept of aggregated time
		// testrun.setTime(s.time);
		return s;
	}

	public Stats updateStats(Testsuite testsuite) {
		Stats s = new Stats();
		for(Testcase tc : testsuite.getTestcases())
			s = s.add(updateStats(tc));
		for(Testsuite ts : testsuite.getTestsuites())
			s = s.add(updateStats(ts));

		testsuite.setTests(s.count);
		testsuite.setErrors(s.errors);
		testsuite.setFailures(s.failures);
		testsuite.setSkipped(s.skipped);
		testsuite.setDisabled(s.disabled);
		testsuite.setTime(s.time);
		return s;
	}

	public Stats updateStats(Testsuites testsuites) {
		Stats s = new Stats();
		for(Testsuite ts : testsuites.getTestsuites())
			s = s.add(updateStats(ts));

		testsuites.setTests(s.count);
		testsuites.setErrors(s.errors);
		testsuites.setFailures(s.failures);
		// there is only one "disabled" that counts tests that did not run
		// use that for tests reported as "disabled" or "skipped" (they are never both).
		testsuites.setDisabled(s.disabled + s.skipped);
		testsuites.setTime(s.time);
		return s;
	}

}
