/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 */
package com.puppetlabs.geppetto.forge.util;

import static com.puppetlabs.geppetto.diagnostic.Diagnostic.ERROR;
import static com.puppetlabs.geppetto.forge.Forge.METADATA_JSON_NAME;
import static com.puppetlabs.geppetto.forge.Forge.PARSE_FAILURE;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.regex.Pattern;

import com.puppetlabs.geppetto.common.os.StreamUtil;
import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.diagnostic.FileDiagnostic;
import com.puppetlabs.geppetto.forge.MetadataExtractor;
import com.puppetlabs.geppetto.forge.v2.model.Dependency;
import com.puppetlabs.geppetto.forge.v2.model.Metadata;
import com.puppetlabs.geppetto.forge.v2.model.ModuleName;
import com.puppetlabs.geppetto.semver.Version;
import com.puppetlabs.geppetto.semver.VersionRange;
import org.jrubyparser.SourcePosition;
import org.jrubyparser.lexer.SyntaxException;

/**
 * Utility class with helper methods for Forge Module related tasks.
 */
public class ModuleUtils {
	// @fmtOff
	public static final String[] DEFAULT_EXCLUDES = {
		"*~",
		"#*#",
		".#*",
		"%*%",
		"._*",
		"CVS",
		".cvsignore",
		"SCCS",
		"vssver.scc",
		".svn",
		".DS_Store",
		".git",
		".gitattributes",
		".gitignore",
		".gitmodules",
		".hg",
		".hgignore",
		".hgsub",
		".hgsubstate",
		".hgtags",
		".bzr",
		".bzrignore",
		".project",
		".forge-releng",
		".settings",
		".classpath",
		".bzrignore",
		"pkg",
		"coverage"
	};
	// @fmtOn

	// Directory names that should not be checksummed or copied.
	public static final Pattern DEFAULT_EXCLUDES_PATTERN = compileExcludePattern(DEFAULT_EXCLUDES);

	public static final FileFilter DEFAULT_FILE_FILTER = new FileFilter() {
		@Override
		public boolean accept(File file) {
			return !DEFAULT_EXCLUDES_PATTERN.matcher(file.getName()).matches();
		}
	};

	private static void addKeyValueNode(PrintWriter out, String key, String... strs) throws IOException {
		if(strs.length == 0)
			return;

		out.print(key);
		out.print(' ');
		ValueSerializer rubySerializer = RubyValueSerializer.INSTANCE;
		switch(strs.length) {
			case 0:
				break;
			case 1:
				rubySerializer.serialize(out, strs[0]);
				break;
			default:
				rubySerializer.serialize(out, strs[0]);
				for(int idx = 1; idx < strs.length; ++idx) {
					out.append(", ");
					rubySerializer.serialize(out, strs[idx]);
				}
		}
		out.println();
	}

	private static void appendExcludePattern(String string, StringBuilder bld) {
		int top = string.length();
		for(int idx = 0; idx < top; ++idx) {
			char c = string.charAt(idx);
			switch(c) {
				case '.':
					bld.append('\\');
					bld.append(c);
					break;
				case '*':
					bld.append('.');
					bld.append('*');
					break;
				case '?':
					bld.append('.');
					break;
				default:
					bld.append(c);
			}
		}
	}

	public static void buildFileName(ModuleName qname, Version version, StringBuilder bld) {
		qname.withSeparator('-').toString(bld);
		bld.append('-');
		version.toString(bld);
	}

	public static void buildFileNameWithExtension(ModuleName qname, Version version, StringBuilder bld) {
		buildFileName(qname, version, bld);
		bld.append(".tar.gz");
	}

	private static Pattern compileExcludePattern(String[] excludes) {
		if(excludes == null || excludes.length == 0)
			return Pattern.compile(".*");

		StringBuilder bld = new StringBuilder();
		bld.append("^(?:");
		appendExcludePattern(excludes[0], bld);
		for(int idx = 1; idx < excludes.length; ++idx) {
			bld.append('|');
			appendExcludePattern(excludes[idx], bld);
		}
		bld.append(")$");
		return Pattern.compile(bld.toString());
	}

	/**
	 * Creates an error diagnostic based on the information found in the <code>syntaxException</code>.
	 * 
	 * @param syntaxException
	 *            The exception containing the diagnostic error
	 * @param id
	 *            An id that will be used as the file name or <code>null</code> if the file name should be extracted
	 *            from the exception
	 * @return The created diagnostic
	 */
	public static FileDiagnostic createSyntaxErrorDiagnostic(SyntaxException syntaxException, String id) {
		SourcePosition pos = syntaxException.getPosition();
		String msg = syntaxException.getMessage();
		if(msg == null)
			msg = "syntax error";
		FileDiagnostic fd = new FileDiagnostic(ERROR, PARSE_FAILURE, msg, new File(id == null
				? pos.getFile()
				: id));
		fd.setLineNumber(pos.getStartLine() + 1);
		return fd;
	}

	/**
	 * Scan for valid directories containing 'metadata.json' files or other types of build time artifacts
	 * that provides metadata and is recognized by the provided <tt>metadataExtractors</tt>.
	 * A directory that contains such a file will not be scanned in turn.
	 * 
	 * @return A list of directories where such files were found
	 */
	public static Collection<File> findModuleRoots(File modulesRoot, FileFilter filter,
			Iterable<MetadataExtractor> metadataExtractors) {
		Collection<File> moduleRoots = new ArrayList<File>();
		if(ModuleUtils.findModuleRoots(filter, modulesRoot, moduleRoots, metadataExtractors))
			// The repository is a module in itself
			moduleRoots.add(modulesRoot);
		return moduleRoots;
	}

	private static boolean findModuleRoots(FileFilter filter, File modulesRoot, Collection<File> moduleFiles,
			Iterable<MetadataExtractor> metadataExtractors) {
		File[] files = modulesRoot.listFiles(filter);
		if(files != null) {
			// This is a directory
			int idx = files.length;
			if(idx > 0) {
				// And it's not empty
				while(--idx >= 0)
					if(METADATA_JSON_NAME.equals(files[idx].getName()))
						return true;

				for(MetadataExtractor extractor : metadataExtractors)
					if(extractor.canExtractFrom(modulesRoot, filter))
						return true;

				// Check subdirectories
				idx = files.length;
				while(--idx >= 0) {
					File file = files[idx];
					if(findModuleRoots(filter, file, moduleFiles, metadataExtractors))
						moduleFiles.add(file);
				}
			}
		}
		return false;
	}

	/**
	 * Parse a Modulefile and create a Metadata instance from the result. The parser <i>will not evaluate</i> actual
	 * ruby code. It
	 * just parses the code and extracts values from the resulting AST.
	 * 
	 * @param moduleFile
	 *            The file to parse
	 * @param receiver
	 *            The receiver of the parsed metadata
	 * @param chain
	 *            Diagnostics collecting errors
	 * @return The resulting metadata
	 * @throws IOException
	 *             when it is not possible to read the <tt>modulefile</tt>.
	 * @throws IllegalArgumentException
	 *             if <tt>result</tt> is <tt>null</tt> and errors are detected in the file.
	 */
	public static void parseModulefile(File modulefile, Metadata receiver, Diagnostic chain) throws IOException,
			IllegalArgumentException {
		StrictModulefileParser parser = new StrictModulefileParser(receiver);
		try {
			parser.parseRubyAST(RubyParserUtils.parseFile(modulefile), chain);
		}
		catch(SyntaxException e) {
			chain.addChild(createSyntaxErrorDiagnostic(e, null));
		}
	}

	/**
	 * Parse Modulefile content from a string and create a Metadata instance from the result. The parser <i>will not
	 * evaluate</i> actual
	 * ruby code. It
	 * just parses the code and extracts values from the resulting AST.
	 * 
	 * @param id
	 *            The full path of the file to parse
	 * @param content
	 *            The file content
	 * @param receiver
	 *            The receiver of the parsed metadata
	 * @param chain
	 *            Diagnostics collecting errors
	 * @throws IOException
	 *             when it is not possible to read the <tt>content</tt>.
	 * @throws IllegalArgumentException
	 *             if <tt>result</tt> is <tt>null</tt> and errors are detected in the file.
	 */
	public static void parseModulefile(String id, String content, Metadata receiver, Diagnostic chain)
			throws IOException, IllegalArgumentException {
		StrictModulefileParser parser = new StrictModulefileParser(receiver);
		try {
			parser.parseRubyAST(RubyParserUtils.parseString(id, content), chain);
		}
		catch(SyntaxException e) {
			chain.addChild(createSyntaxErrorDiagnostic(e, id));
		}
	}

	/**
	 * Print a {@link Metadata} instance in the Ruby form used in a &quot;Modulefile&quot; on
	 * the given stream
	 * 
	 * @param md
	 *            The metadata to use as input.
	 * @param out
	 *            The stream that will receive the Modulefile content
	 * @throws IOException
	 */
	public static void printModulefile(Metadata md, PrintWriter out) throws IOException {
		ModuleName name = md.getName();
		if(name != null)
			addKeyValueNode(out, "name", name.withSeparator('-').toString());
		if(md.getVersion() != null)
			addKeyValueNode(out, "version", md.getVersion().toString());
		out.println();
		if(md.getAuthor() != null)
			addKeyValueNode(out, "author", md.getAuthor());
		if(md.getLicense() != null)
			addKeyValueNode(out, "license", md.getLicense());
		if(md.getProjectPage() != null)
			addKeyValueNode(out, "project_page", md.getProjectPage().toString());
		if(md.getSource() != null)
			addKeyValueNode(out, "source", md.getSource());
		if(md.getSummary() != null)
			addKeyValueNode(out, "summary", md.getSummary());
		if(md.getDescription() != null)
			addKeyValueNode(out, "description", md.getDescription());
		for(Dependency dep : md.getDependencies()) {
			ModuleName depName = dep.getName().withSeparator('/');
			VersionRange ver = dep.getVersionRequirement();
			if(ver != null)
				addKeyValueNode(out, "dependency", depName.toString(), ver.toString());
			else
				addKeyValueNode(out, "dependency", depName.toString());
		}
	}

	/**
	 * Store a {@link Metadata} instance in the Ruby form used in a &quot;Modulefile&quot;.
	 * 
	 * @param md
	 *            The metadata to store.
	 * @param moduleFile
	 *            The file to store to
	 * @throws IOException
	 */
	public static void saveAsModulefile(Metadata md, File moduleFile) throws IOException {
		PrintWriter out = new PrintWriter(moduleFile);
		try {
			printModulefile(md, out);
		}
		finally {
			StreamUtil.close(out);
		}
	}

	/**
	 * Produce Modulefile content in string form
	 * 
	 * @param metadata
	 *            The metadata to use as input
	 * @return The Modulefile content that represents the metadata
	 */
	public static String toModulefileContent(Metadata metadata) {
		StringWriter bld = new StringWriter();
		PrintWriter out = new PrintWriter(bld);
		try {
			printModulefile(metadata, out);
			out.flush();
		}
		catch(IOException e) {

		}
		return bld.toString();
	}

}
