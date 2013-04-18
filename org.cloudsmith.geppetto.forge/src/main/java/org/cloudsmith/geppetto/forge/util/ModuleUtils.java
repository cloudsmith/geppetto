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
package org.cloudsmith.geppetto.forge.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Pattern;

import org.cloudsmith.geppetto.common.os.StreamUtil;
import org.cloudsmith.geppetto.diagnostic.Diagnostic;
import org.cloudsmith.geppetto.diagnostic.FileDiagnostic;
import org.cloudsmith.geppetto.forge.Forge;
import org.cloudsmith.geppetto.forge.MetadataExtractor;
import org.cloudsmith.geppetto.forge.v2.model.Dependency;
import org.cloudsmith.geppetto.forge.v2.model.Metadata;
import org.cloudsmith.geppetto.forge.v2.model.ModuleName;
import org.cloudsmith.geppetto.semver.Version;
import org.cloudsmith.geppetto.semver.VersionRange;
import org.jrubyparser.SourcePosition;
import org.jrubyparser.ast.FCallNode;
import org.jrubyparser.ast.IArgumentNode;
import org.jrubyparser.ast.ListNode;
import org.jrubyparser.ast.NilNode;
import org.jrubyparser.ast.Node;
import org.jrubyparser.ast.NodeType;
import org.jrubyparser.ast.RootNode;
import org.jrubyparser.ast.StrNode;

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

	private static final String[] validProperties = new String[] {
			"name", "author", "description", "license", "project_page", "source", "summary", "version", "dependency" };

	private static void addKeyValueNode(PrintWriter out, String key, String... strs) throws IOException {
		if(strs.length == 0)
			return;

		out.print(key);
		out.print(' ');
		switch(strs.length) {
			case 0:
				break;
			case 1:
				printRubyString(out, strs[0]);
				break;
			default:
				printRubyString(out, strs[0]);
				for(int idx = 1; idx < strs.length; ++idx) {
					out.append(", ");
					printRubyString(out, strs[idx]);
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

	private static void call(Metadata md, String key, String value) {
		if("name".equals(key))
			md.setName(new ModuleName(value));
		else if("author".equals(key))
			md.setAuthor(value);
		else if("description".equals(key))
			md.setDescription(value);
		else if("license".equals(key))
			md.setLicense(value);
		else if("project_page".equals(key))
			md.setProjectPage(value);
		else if("source".equals(key))
			md.setSource(value);
		else if("summary".equals(key))
			md.setSummary(value);
		else if("version".equals(key))
			md.setVersion(Version.create(value));
		else if("dependency".equals(key))
			call(md, key, value, null, null);
		else
			throw noResponse(key, 1);
	}

	private static void call(Metadata md, String key, String value1, String value2) {
		if("dependency".equals(key))
			call(md, key, value1, value2, null);
		else
			throw noResponse(key, 2);
	}

	private static void call(Metadata md, String key, String value1, String value2, String value3) {
		if("dependency".equals(key)) {
			Dependency dep = new Dependency();
			dep.setName(new ModuleName(value1));
			if(value2 != null)
				dep.setVersionRequirement(VersionRange.create(value2));
			md.getDependencies().add(dep);
		}
		else
			throw noResponse(key, 3);
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
	 * Scan for valid directories containing "metadata.json" files or other types of build time artifacts
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
					if("metadata.json".equals(files[idx].getName()))
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

	private static List<String> getStringArguments(IArgumentNode callNode) throws IllegalArgumentException {
		Node argsNode = callNode.getArgs();
		if(!(argsNode instanceof ListNode))
			throw new IllegalArgumentException("IArgumentNode expected");
		ListNode args = (ListNode) argsNode;
		int top = args.size();
		ArrayList<String> stringArgs = new ArrayList<String>(top);
		for(int idx = 0; idx < top; ++idx) {
			Node argNode = args.get(idx);
			if(argNode instanceof StrNode)
				stringArgs.add(((StrNode) argNode).getValue());
			else if(argNode instanceof NilNode)
				stringArgs.add(null);
			else
				throw new IllegalArgumentException("Unexpected ruby code. Node type was: " + (argNode == null
						? "null"
						: argNode.getClass().getSimpleName()));
		}
		return stringArgs;
	}

	private static IllegalArgumentException noResponse(String key, int nargs) {
		StringBuilder bld = new StringBuilder();
		bld.append('\'');
		bld.append(key);
		bld.append("' is not a metadata property");
		int idx = validProperties.length;
		while(--idx >= 0)
			if(validProperties[idx].equals(key)) {
				// This is a valid property so the number of arguments
				// must be wrong.
				bld.append(" that takes ");
				if(nargs > 0)
					bld.append(nargs);
				else
					bld.append("no");
				bld.append(" argument");
				if(nargs == 0 || nargs > 1)
					bld.append('s');
				break;
			}
		return new IllegalArgumentException(bld.toString());
	}

	/**
	 * Parse a Modulefile and create a Metadata instance from the result. The parser <i>will not evaluate</i> actual ruby code. It
	 * just parses the code and extracts values from the resulting AST.
	 * 
	 * @param moduleFile
	 *            The file to parse
	 * @param result
	 *            Diagnostics collecting errors
	 * @return The resulting metadata
	 * @throws IOException
	 *             when it is not possible to read the <tt>modulefile</tt>.
	 * @throws IllegalArgumentException
	 *             if <tt>result</tt> is <tt>null</tt> and errors are detected in the file.
	 */
	public static Metadata parseModulefile(File modulefile, Diagnostic result) throws IOException,
			IllegalArgumentException {
		Metadata receiver = new Metadata();
		RootNode root = RubyParserUtils.parseFile(modulefile);
		for(Node node : RubyParserUtils.findNodes(root.getBody(), new NodeType[] { NodeType.FCALLNODE })) {
			FCallNode call = (FCallNode) node;
			String key = call.getName();
			List<String> args = getStringArguments(call);
			try {
				if(args.size() == 1)
					call(receiver, key, args.get(0));
				else if(args.size() == 2)
					call(receiver, key, args.get(0), args.get(1));
				else if(args.size() == 3)
					call(receiver, key, args.get(0), args.get(1), args.get(2));
				else
					noResponse(key, args.size());
			}
			catch(IllegalArgumentException e) {
				if(result == null)
					throw e;
				SourcePosition pos = call.getPosition();
				FileDiagnostic diag = new FileDiagnostic(Diagnostic.ERROR, Forge.FORGE, e.getMessage(), new File(
					pos.getFile()));
				diag.setLineNumber(pos.getEndLine() + 1);
				result.addChild(diag);
			}
		}
		return receiver;
	}

	private static void printRubyString(Writer out, String str) throws IOException {
		if(str == null)
			return;

		out.append('\'');
		int top = str.length();
		for(int idx = 0; idx < top; ++idx) {
			char c = str.charAt(idx);
			switch(c) {
				case '\\':
				case '\'':
					out.append('\\');
					out.append(c);
					break;
				default:
					out.append(c);
			}
		}
		out.append('\'');
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
			ModuleName name = md.getName();
			addKeyValueNode(out, "name", name.toString());
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
				ModuleName depName = dep.getName();
				VersionRange ver = dep.getVersionRequirement();
				if(ver != null)
					addKeyValueNode(out, "dependency", depName.toString(), ver.toString());
				else
					addKeyValueNode(out, "dependency", depName.toString());
			}
		}
		finally {
			StreamUtil.close(out);
		}
	}

}
