/**
 * Copyright (c) 2011 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.puppetlint.impl;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cloudsmith.geppetto.common.os.OsUtil;
import org.cloudsmith.geppetto.common.os.StreamUtil.OpenBAStream;
import org.cloudsmith.geppetto.puppetlint.PuppetLintRunner;
import org.cloudsmith.geppetto.puppetlint.impl.PuppetLintIssue.Severity;

/**
 * A PuppetLintRunner implementation that uses an external command. This runner will require that Ruby is installed
 */
public class ExternalPuppetLintRunner implements PuppetLintRunner {
	private static final Pattern issuePattern = Pattern.compile(
		"^(ERROR|WARNING)\\s+([a-zA-Z0-9_-]+)\\s+#([^#]+)#:(\\d+)\\s+(.*)$", Pattern.MULTILINE);

	private static final Pattern versionPattern = Pattern.compile("^[A-Za-z0-9_.-]+\\s+(\\S+)\\s*$", Pattern.MULTILINE);

	@Override
	public String getVersion() throws IOException {
		// Verify that puppet-lint is installed. If not, then refuse to install this bundle
		OpenBAStream out = new OpenBAStream();
		OpenBAStream err = new OpenBAStream();
		File home = new File(System.getProperty("user.home"));
		if(OsUtil.runProcess(home, out, err, "puppet-lint", "--version") != 0)
			throw new IOException("Unable to run puppet-lint: " + err.toString(Charset.defaultCharset()));
		String version = out.toString(Charset.defaultCharset());
		Matcher m = versionPattern.matcher(version);
		if(m.find())
			version = m.group(1);
		return version;
	}

	@Override
	public List<Issue> run(File fileOrDirectory, Option... options) throws IOException {
		String pathToCheck = ".";
		if(fileOrDirectory.isFile()) {
			pathToCheck = fileOrDirectory.getName();
			fileOrDirectory = fileOrDirectory.getParentFile();
		}
		List<String> params = new ArrayList<String>();
		params.add("puppet-lint");
		params.add("--log-format");
		params.add("%{KIND} %{check} #%{path}#:%{linenumber} %{message}");
		for(Option option : options)
			params.add("--" + option);
		params.add(pathToCheck);

		OpenBAStream out = new OpenBAStream();
		OpenBAStream err = new OpenBAStream();
		if(OsUtil.runProcess(fileOrDirectory, out, err, params.toArray(new String[params.size()])) != 0)
			throw new IOException("Unable to run puppet-lint: " + err.toString(Charset.defaultCharset()));

		List<Issue> issues = new ArrayList<Issue>();
		Matcher m = issuePattern.matcher(out.toString(Charset.defaultCharset()));
		while(m.find()) {
			String path = m.group(3);
			if(path.startsWith("./") || path.startsWith(".\\"))
				path = path.substring(2);
			issues.add(new PuppetLintIssue(
				path, Severity.valueOf(m.group(1)), m.group(2), m.group(5), Integer.parseInt(m.group(4))));
		}
		return issues;
	}
}
