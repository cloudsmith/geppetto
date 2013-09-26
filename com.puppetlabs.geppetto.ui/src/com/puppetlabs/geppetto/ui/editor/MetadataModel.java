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
package com.puppetlabs.geppetto.ui.editor;

import static com.puppetlabs.geppetto.common.Strings.emptyToNull;
import static com.puppetlabs.geppetto.common.Strings.trimToNull;
import static com.puppetlabs.geppetto.forge.Forge.FORGE;
import static com.puppetlabs.geppetto.forge.Forge.MODULEFILE_NAME;
import static com.puppetlabs.geppetto.forge.Forge.PARSE_FAILURE;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.diagnostic.ExceptionDiagnostic;
import com.puppetlabs.geppetto.diagnostic.FileDiagnostic;
import com.puppetlabs.geppetto.forge.model.ModuleName;
import com.puppetlabs.geppetto.forge.model.ModuleName.BadNameCharactersException;
import com.puppetlabs.geppetto.forge.util.CallSymbol;
import com.puppetlabs.geppetto.forge.util.ModuleUtils;
import com.puppetlabs.geppetto.forge.util.RubyValueSerializer;
import com.puppetlabs.geppetto.forge.util.ValueSerializer;
import com.puppetlabs.geppetto.pp.dsl.ui.builder.PPModuleMetadataBuilder;
import com.puppetlabs.geppetto.semver.VersionRange;
import com.puppetlabs.geppetto.ui.UIPlugin;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.ObjectMapper;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.jrubyparser.CompatVersion;
import org.jrubyparser.Parser;
import org.jrubyparser.ast.RootNode;
import org.jrubyparser.lexer.SyntaxException;
import org.jrubyparser.parser.ParserConfiguration;

import com.google.common.io.CharStreams;

public class MetadataModel {
	public interface Dependency {
		void delete();

		int getLine();

		String getModuleName();

		String getVersionRequirement();

		boolean isResolved();

		void setNameAndVersion(String moduleName, String versionRequirement);

		void setResolved(boolean flag);
	}

	class JsonDependency extends JsonObject implements Dependency {
		private boolean resolved;

		JsonDependency(ArgSticker depObj) {
			super(depObj);
		}

		@Override
		public void delete() {
			super.delete();
			removeFromArray(dependenciesCall);
		}

		public String getModuleName() {
			return getString(NAME);
		}

		public String getVersionRequirement() {
			return getString(VERSION_REQUIREMENT);
		}

		public boolean isResolved() {
			return resolved;
		}

		public void setNameAndVersion(String moduleName, String versionRequirement) {
			Map<String, Object> map = getMap();
			if(moduleName == null)
				map.remove(NAME);
			else
				map.put(NAME, moduleName);
			if(versionRequirement == null)
				map.remove(VERSION_REQUIREMENT);
			else
				map.put(VERSION_REQUIREMENT, versionRequirement);
			updateJson(4);
		}

		public void setResolved(boolean flag) {
			resolved = flag;
		}
	}

	abstract class JsonObject {
		private ArgSticker object;

		JsonObject(ArgSticker object) {
			this.object = object;
		}

		public void delete() {
			try {
				MetadataModel.this.remove(object);
			}
			catch(BadLocationException e) {
				throw new RuntimeException(e);
			}
		}

		public int getLine() {
			try {
				return document.getLineOfOffset(object.getOffset()) + 1;
			}
			catch(BadLocationException e) {
				return -1;
			}
		}

		@SuppressWarnings("unchecked")
		Map<String, Object> getMap() {
			Object value = object.getValue();
			if(value instanceof Map)
				return (Map<String, Object>) value;
			return Collections.emptyMap();
		}

		String getString(String key) {
			Object value = getMap().get(key);
			return value instanceof String
					? (String) value
					: null;
		}

		void removeFromArray(CallSticker call) {
			ArgSticker[] args = call.getArguments();
			int idx = args.length;
			if(idx > 0) {
				int rdx = idx - 1;
				ArgSticker[] newArgs = new ArgSticker[rdx];
				if(rdx > 0) {
					while(--idx >= 0) {
						ArgSticker arg = args[idx];
						if(arg != object) {
							if(--rdx < 0)
								break;
							newArgs[rdx] = arg;
						}
					}
				}
				call.setArguments(newArgs);
			}
		}

		void updateJson(int indent) {
			MetadataModel.this.updateJson(object, indent);
		}
	}

	static class Replacement {
		int offset;

		int length;

		String prefix;

		String suffix;

		int getInjectLength(int totInjectLen) {
			return totInjectLen - (prefix.length() + suffix.length());
		}

		int getInjectOffset() {
			return offset + prefix.length();
		}
	}

	class RubyDependency implements Dependency {
		private CallSticker dependencyCall;

		private boolean resolved;

		RubyDependency(CallSticker dependency) {
			this.dependencyCall = dependency;
		}

		public void delete() {
			setArgValue(CallSymbol.dependency, dependencyCall);
		}

		public int getLine() {
			try {
				return document.getLineOfOffset(dependencyCall.getOffset()) + 1;
			}
			catch(BadLocationException e) {
				return -1;
			}
		}

		public String getModuleName() {
			return getArgValue(dependencyCall, 0);
		}

		public String getVersionRequirement() {
			return getArgValue(dependencyCall, 1);
		}

		public boolean isResolved() {
			return resolved;
		}

		public void setNameAndVersion(String moduleName, String versionRequirement) {
			dependencyCall = setArgValue(CallSymbol.dependency, dependencyCall, moduleName, versionRequirement);
		}

		public void setResolved(boolean flag) {
			resolved = flag;
		}
	}

	private static final String VERSION_REQUIREMENT = "version_requirement";

	private static final String NAME = "name";

	public static String getBadNameMessage(IllegalArgumentException e, boolean dependency) {
		String key;
		if(dependency)
			key = e instanceof BadNameCharactersException
					? "_UI_Dependency_name_bad_characters"
					: "_UI_Dependency_name_bad_syntax";
		else
			key = e instanceof BadNameCharactersException
					? "_UI_Module_name_bad_characters"
					: "_UI_Module_name_bad_syntax";
		return UIPlugin.getLocalString(key);
	}

	public static String getUnresolvedMessage(Dependency dep) {
		String vr = trimToNull(dep.getVersionRequirement());
		return vr == null
				? UIPlugin.getLocalString("_UI_Unresolved_dependency_X", new Object[] { dep.getModuleName() })
				: UIPlugin.getLocalString(
					"_UI_Unresolved_dependency_X_Y", new Object[] { dep.getModuleName(), dep.getVersionRequirement() });

	}

	private Parser rubyParser;

	private ParserConfiguration rubyParserConfig;

	private CallSticker nameCall;

	private CallSticker versionCall;

	private CallSticker summaryCall;

	private CallSticker authorCall;

	private CallSticker descriptionCall;

	private CallSticker dependenciesCall;

	private CallSticker sourceCall;

	private CallSticker projectPageCall;

	private CallSticker licenseCall;

	private ValueSerializer serializer;

	private IDocument document;

	private final List<Dependency> dependencies = new ArrayList<Dependency>();

	private JsonFactory jsonFactory;

	private boolean syntaxError;

	private boolean dependencyErrors;

	synchronized void addCall(CallSymbol symbol, CallSticker call) {
		switch(symbol) {
			case author:
				authorCall = call;
				break;
			case dependencies:
				dependenciesCall = call;
				for(ArgSticker dep : call.getArguments())
					dependencies.add(new JsonDependency(dep));
				break;
			case dependency:
				dependencies.add(new RubyDependency(call));
				break;
			case description:
				descriptionCall = call;
				break;
			case license:
				licenseCall = call;
				break;
			case name:
				nameCall = call;
				break;
			case project_page:
				projectPageCall = call;
				break;
			case source:
				sourceCall = call;
				break;
			case summary:
				summaryCall = call;
				break;
			case version:
				versionCall = call;
		}
		addPositions(call);
	}

	public synchronized void addDependency(String moduleName, String versionRequirement) {
		ModuleName m;
		try {
			m = new ModuleName(moduleName);
			moduleName = m.withSeparator('/').toString();
		}
		catch(IllegalArgumentException e) {
			m = null;
		}

		if(m != null) {
			for(Dependency dep : dependencies)
				try {
					ModuleName candidate = new ModuleName(dep.getModuleName());
					if(m.equals(candidate)) {
						dep.setNameAndVersion(moduleName, versionRequirement);
						return;
					}
				}
				catch(IllegalArgumentException e) {

				}
		}
		Dependency dep;
		if(isRuby())
			dep = new RubyDependency(setArgValue(CallSymbol.dependency, null, moduleName, versionRequirement));
		else
			dep = createJsonDependency(moduleName, versionRequirement);

		dep.setResolved(isResolved(dep));
		dependencies.add(dep);
	}

	private void addPositions(CallSticker fragment) {
		try {
			document.addPosition(fragment);
			Position[] args = fragment.getArguments();
			for(int idx = 0; idx < args.length; ++idx)
				document.addPosition(args[idx]);
		}
		catch(BadLocationException e) {
			throw new RuntimeException(e);
		}
	}

	private Map<String, String> createDepsObj(String... values) {
		if(values.length == 0)
			return Collections.emptyMap();

		Map<String, String> value = new HashMap<String, String>();
		String name = emptyToNull(values[0]);
		if(name != null)
			value.put(NAME, name);

		if(values.length > 1) {
			String vr = emptyToNull(values[1]);
			if(vr != null)
				value.put(VERSION_REQUIREMENT, vr);
		}
		return value;
	}

	private JsonDependency createJsonDependency(String... values) {
		// Dependencies reside inside of the dependencies array. A new
		// dependency may potentially need to create this array from
		// scratch
		Map<String, String> depsObj = createDepsObj(values);
		StringBuilder bld = new StringBuilder();
		if(dependenciesCall == null || dependenciesCall.isDeleted) {
			Replacement r = prepareJSonInsert(-1, 2);
			bld.append(r.prefix);
			try {
				serializer.serialize(bld, "dependencies");
				bld.append(": [\n  ]");
				bld.append(r.suffix);
				document.replace(r.offset, r.length, bld.toString());
				dependenciesCall = new CallSticker(
					r.getInjectOffset(), r.getInjectLength(bld.length()), new ArgSticker[0]);
				document.addPosition(dependenciesCall);
			}
			catch(BadLocationException e) {
				throw new RuntimeException(e);
			}
			catch(IOException e) {
				throw new RuntimeException(e);
			}
			bld.setLength(0);
		}

		int last = dependenciesCall.getOffset() + dependenciesCall.getLength();
		String content = document.get();
		while(last >= 0 && content.charAt(last) != ']')
			--last;
		while(--last >= 0 && Character.isWhitespace(content.charAt(last)))
			;
		++last;

		ArgSticker arg = new ArgSticker(last, 0, depsObj);
		updateJson(arg, 4);
		ArgSticker[] args = dependenciesCall.getArguments();
		ArgSticker[] newArgs = new ArgSticker[args.length + 1];
		System.arraycopy(args, 0, newArgs, 0, args.length);
		newArgs[args.length] = arg;
		dependenciesCall.setArguments(args);
		return new JsonDependency(arg);
	}

	private CallSticker createNewCall(CallSymbol key, String... values) throws IOException, BadLocationException {
		if(!isRuby())
			return createNewJsonCall(key, values);

		String content = document.get();
		int insertPos = content.length();

		if(key == CallSymbol.dependency) {
			// This is a normal call but we make an effort to group the dependencies together
			// Find the last one
			CallSticker best = null;
			for(Dependency dep : dependencies) {
				CallSticker depSticker = ((RubyDependency) dep).dependencyCall;
				if(best == null || depSticker.getOffset() > best.getOffset())
					best = depSticker;
			}
			if(best != null) {
				// Insert directly after 'best' but on a new line
				insertPos = best.getOffset() + best.getLength();
				if(insertPos < content.length() && content.charAt(insertPos) == '\n')
					++insertPos;
			}
		}

		StringBuilder bld = new StringBuilder();
		int nargs = values.length;

		ArgSticker[] argPositions = new ArgSticker[nargs];
		if(insertPos > 0 && content.charAt(insertPos - 1) != '\n')
			bld.append('\n');

		int callOffset = bld.length();
		bld.append(key.name());
		bld.append(' ');

		// Write empty strings for any arguments preceding the new one (in case
		// we get them in the wrong order)
		for(int idx = 0; idx < nargs; ++idx) {
			if(idx > 0)
				bld.append(", ");

			String value = values[idx];
			if(value == null)
				value = "";

			int argOffset = bld.length();
			serializer.serialize(bld, value);
			argPositions[idx] = new ArgSticker(insertPos + argOffset, bld.length() - argOffset, value);
		}
		bld.append('\n');
		document.replace(insertPos, 0, bld.toString());
		return new CallSticker(insertPos + callOffset, bld.length() - callOffset, argPositions);

	}

	private CallSticker createNewJsonCall(CallSymbol key, String... values) throws IOException, BadLocationException {
		Replacement r = prepareJSonInsert(-1, 2);

		StringBuilder bld = new StringBuilder();
		bld.append(r.prefix);
		serializer.serialize(bld, key.name());
		bld.append(": ");

		int nargs = values.length;
		ArgSticker[] argPositions = new ArgSticker[nargs];

		if(nargs > 1)
			bld.append('[');

		// Write empty strings for any arguments preceding the new one (in case
		// we get them in the wrong order)
		for(int idx = 0; idx < nargs; ++idx) {
			if(idx > 0)
				bld.append(", ");

			String value = values[idx];
			if(value == null)
				value = "";

			int argOffset = bld.length();
			serializer.serialize(bld, value);
			argPositions[idx] = new ArgSticker(r.offset + argOffset, bld.length() - argOffset, value);
		}
		if(nargs > 1)
			bld.append(']');

		bld.append(r.suffix);
		document.replace(r.offset, r.length, bld.toString());
		return new CallSticker(
			r.offset + r.prefix.length(), bld.length() - (r.prefix.length() + r.suffix.length()), argPositions);
	}

	private String getArgValue(CallSticker call, int argNo) {
		if(call != null && document != null && !call.isDeleted) {
			ArgSticker[] args = call.getArguments();
			if(argNo < args.length) {
				ArgSticker fragment = args[argNo];
				if(!fragment.isDeleted) {
					Object v = fragment.getValue();
					if(v != null)
						return v.toString();
				}
			}
		}
		return "";
	}

	public String getAuthor() {
		return getArgValue(authorCall, 0);
	}

	public synchronized Dependency[] getDependencies() {
		return dependencies.toArray(new Dependency[dependencies.size()]);
	}

	public synchronized int getDependencyIndex(Dependency dependency) {
		int idx = dependencies.size();
		while(--idx >= 0)
			if(dependencies.get(idx) == dependency)
				break;
		return idx;
	}

	public String getDescription() {
		return getArgValue(descriptionCall, 0);
	}

	IDocument getDocument() {
		return document;
	}

	public String getLicense() {
		return getArgValue(licenseCall, 0);
	}

	public String getModuleName() {
		return getArgValue(nameCall, 0);
	}

	public String getProjectPage() {
		return getArgValue(projectPageCall, 0);
	}

	public String getSource() {
		return getArgValue(sourceCall, 0);
	}

	public String getSummary() {
		return getArgValue(summaryCall, 0);
	}

	public String getVersion() {
		return getArgValue(versionCall, 0);
	}

	/**
	 * Returns true if the model has unresolved dependencies
	 */
	public boolean hasDependencyErrors() {
		return dependencyErrors;
	}

	private boolean isResolved(Dependency dep) {
		ModuleName name;
		VersionRange range;
		try {
			name = new ModuleName(dep.getModuleName(), false);
			range = VersionRange.create(dep.getVersionRequirement());
			return PPModuleMetadataBuilder.getBestMatchingProject(name, range) != null;
		}
		catch(IllegalArgumentException e) {
			return false;
		}
	}

	private boolean isRuby() {
		return serializer == RubyValueSerializer.INSTANCE;
	}

	/**
	 * Returns true if the parser was unable to parse the syntax of its given input
	 */
	public boolean isSyntaxError() {
		return syntaxError;
	}

	private Replacement prepareJSonInsert(int insertPos, final int indent) {
		String content = document.get();

		// We should be inserting inside the top json object. It might not even exist at this point
		int start = content.indexOf('{');
		int end = content.lastIndexOf('}');

		if(insertPos == -1)
			insertPos = end >= 0
					? end
					: content.length();

		StringBuilder bld = new StringBuilder();
		Replacement replacement = new Replacement();
		if(start >= 0) {
			if(end < start)
				replacement.suffix = "\n}";
		}
		else {
			bld.append("{\n");
			int idx = indent;
			while(--idx >= 0)
				bld.append(' ');
			replacement.prefix = bld.toString();
			if(end < 0)
				replacement.suffix = "\n}";
		}

		int replace = 0;

		if(replacement.prefix == null) {
			int idx = insertPos;
			boolean needsComma = true;
			while(--idx >= 0) {
				char c = content.charAt(idx);
				if(c == ',' || c == '[' || c == '{') {
					needsComma = false;
					break;
				}
				if(!Character.isWhitespace(c))
					break;
				++replace;
			}
			replacement.offset = ++idx;
			if(needsComma)
				bld.append(',');
			bld.append('\n');
			idx = indent;
			while(--idx >= 0)
				bld.append(' ');
			replacement.prefix = bld.toString();
		}
		else
			replacement.offset = insertPos;

		if(replacement.suffix == null) {
			boolean needsComma = true;
			int idx = insertPos;
			int top = content.length();
			while(idx < top) {
				char c = content.charAt(idx);
				if(c == ',' || c == ']' || c == '}') {
					needsComma = false;
					break;
				}
				if(!Character.isWhitespace(c))
					break;
				++replace;
				++idx;
			}
			bld.setLength(0);
			idx = indent;
			if(needsComma)
				bld.append(',');
			else
				// We are followed by ']' or '}'
				idx -= 2;

			bld.append('\n');
			while(--idx >= 0)
				bld.append(' ');
			replacement.suffix = bld.toString();
		}
		replacement.length = replace;
		return replacement;
	}

	private void remove(int offset, int len) throws BadLocationException {
		String content = document.get();
		int last = content.length();
		if(offset > last)
			return;

		// Find preceding position that isn't whitespace.
		boolean removesPreceedingComma = false;
		boolean atZero = offset == 0;
		if(!atZero) {
			char c = 0;
			while(offset > 0) {
				--offset;
				++len;
				c = content.charAt(offset);
				if(!Character.isWhitespace(c))
					break;
			}
			removesPreceedingComma = c == ',';
		}

		if(!removesPreceedingComma) {
			if(!atZero) {
				++offset; // Retain this character
				--len;
			}

			// Find out if we need to remove a succeeding comma instead
			int pos = offset + len;
			int step = 0;
			while(pos < last) {
				++step;
				char c = content.charAt(pos);
				if(c == ',') {
					len += step;
					break;
				}
				if(!Character.isWhitespace(c))
					break;
				++pos;
			}
		}
		document.replace(offset, len, "");
	}

	private void remove(Position pos) throws BadLocationException {
		document.removePosition(pos);
		remove(pos.getOffset(), pos.getLength());
	}

	public synchronized void removeDependency(Dependency dep) {
		if(dependencies.remove(dep))
			dep.delete();
	}

	private CallSticker setArgValue(CallSymbol key, CallSticker call, String... values) {
		try {
			if(call == null || call.isDeleted)
				// We need to append a new call to the document
				return createNewCall(key, values);

			ArgSticker[] argPositions = call.getArguments();
			boolean hasArgs = false;
			boolean hasChanges = false;
			int idx = values.length;
			while(--idx >= 0) {
				String arg = values[idx];
				Object oldArg = null;
				if(idx < argPositions.length)
					oldArg = argPositions[idx].getValue();

				if(arg != null) {
					hasArgs = true;
					if(!hasChanges)
						hasChanges = !arg.equals(oldArg);
				}
				else if(oldArg != null)
					hasChanges = true;
			}

			if(!hasArgs) {
				remove(call);
				return null;
			}

			if(!hasChanges)
				return call;

			boolean ruby = isRuby();

			if(!ruby && key == CallSymbol.dependency)
				// Needs special handling since it's stored as json objects
				// in an array
				return updateJsonDependency(call, values);

			// Replace or add arguments to existing call
			ArgSticker[] newPositions = null;
			int nargs = values.length;
			if(nargs > argPositions.length) {
				// Call has additional parameters
				newPositions = new ArgSticker[nargs];
				for(idx = 0; idx < argPositions.length; ++idx)
					newPositions[idx] = argPositions[idx];
			}

			int newArgsLen = 0;
			for(idx = 0; idx < nargs; ++idx) {
				String value = values[idx];
				if(idx < argPositions.length) {
					// Replace argument content
					ArgSticker pos = argPositions[idx];
					pos.setValue(value);
					if(value == null)
						value = ruby
								? "''"
								: "\"\"";
					else {
						StringBuilder bld = new StringBuilder();
						serializer.serialize(bld, value);
						value = bld.toString();
					}
					document.replace(pos.getOffset(), pos.getLength(), value);
					pos.length = value.length();
				}
				else {
					int callEnd = call.getOffset() + call.getLength();
					String content = document.get();
					if(callEnd >= content.length())
						callEnd = content.length() - 1;

					// Find last non-whitespace character
					while(callEnd >= 0 && Character.isWhitespace(content.charAt(callEnd)))
						--callEnd;
					++callEnd; // Directly after last non-whitespace

					StringBuilder bld = new StringBuilder();
					if(idx > 0)
						bld.append(',');
					bld.append(' ');
					int argStart = bld.length();
					serializer.serialize(bld, value);
					newPositions[idx] = new ArgSticker(callEnd + argStart, bld.length() - argStart, value);
					document.replace(callEnd, 0, bld.toString());
					newArgsLen += bld.length();
				}
			}
			if(newPositions != null)
				call = new CallSticker(call.offset, call.length + newArgsLen, newPositions);
			return call;
		}
		catch(BadLocationException e) {
			throw new RuntimeException(e);
		}
		catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	public void setAuthor(String author) {
		authorCall = setArgValue(CallSymbol.author, authorCall, author);
	}

	public void setDescription(String description) {
		descriptionCall = setArgValue(CallSymbol.description, descriptionCall, description);
	}

	synchronized void setDocument(IDocument document, IPath path, Diagnostic chain) {
		syntaxError = false;
		dependencyErrors = false;
		authorCall = null;
		descriptionCall = null;
		licenseCall = null;
		nameCall = null;
		projectPageCall = null;
		sourceCall = null;
		summaryCall = null;
		versionCall = null;
		dependencies.clear();
		this.document = document;
		if(document != null) {
			try {
				if(MODULEFILE_NAME.equals(path.lastSegment())) {
					if(rubyParser == null) {
						serializer = RubyValueSerializer.INSTANCE;
						rubyParser = new Parser();
						rubyParserConfig = new ParserConfiguration(0, CompatVersion.RUBY1_9);
					}
					RootNode root = (RootNode) rubyParser.parse(
						path.toOSString(), new StringReader(document.get()), rubyParserConfig);
					new LenientModulefileParser(this).parseRubyAST(root, chain);
				}
				else {
					if(jsonFactory == null) {
						jsonFactory = new JsonFactory();
						serializer = new ValueSerializer() {
							@Override
							public void serialize(Appendable bld, Object value) throws IOException {
								JsonGenerator generator = jsonFactory.createJsonGenerator(CharStreams.asWriter(bld));
								generator.setCodec(new ObjectMapper(jsonFactory));
								generator.writeObject(value);
								generator.flush();
							}
						};
					}
					new LenientMetadataJsonParser(this).parse(path.toFile(), document.get(), chain);
				}
				for(Dependency dep : dependencies) {
					boolean resolved = isResolved(dep);
					if(!resolved) {
						FileDiagnostic diag = new FileDiagnostic(
							Diagnostic.ERROR, FORGE, getUnresolvedMessage(dep), path.toFile());
						diag.setLineNumber(dep.getLine());
						chain.addChild(diag);
						dependencyErrors = true;
					}
					dep.setResolved(resolved);
				}
			}
			catch(SyntaxException e) {
				syntaxError = true;
				chain.addChild(ModuleUtils.createSyntaxErrorDiagnostic(e, null));
			}
			catch(Exception e) {
				syntaxError = true;
				e.printStackTrace();
				chain.addChild(new ExceptionDiagnostic(
					Diagnostic.ERROR, PARSE_FAILURE, "Unable to parse file " + path, e));
			}
		}
	}

	public void setLicense(String license) {
		licenseCall = setArgValue(CallSymbol.license, licenseCall, license);
	}

	public void setModuleName(String moduleName) {
		nameCall = setArgValue(CallSymbol.name, nameCall, moduleName);
	}

	public void setProjectPage(String projectPage) {
		projectPageCall = setArgValue(CallSymbol.project_page, projectPageCall, projectPage);
	}

	public void setSource(String source) {
		sourceCall = setArgValue(CallSymbol.source, sourceCall, source);
	}

	public void setSummary(String summary) {
		summaryCall = setArgValue(CallSymbol.summary, summaryCall, summary);
	}

	public void setVersion(String version) {
		versionCall = setArgValue(CallSymbol.version, versionCall, version);
	}

	private void updateJson(ArgSticker arg, int indent) {
		if(arg == null || arg.isDeleted)
			return;

		try {
			Replacement r = prepareJSonInsert(arg.getOffset(), indent);

			StringBuilder bld = new StringBuilder();
			bld.append(r.prefix);
			serializer.serialize(bld, arg.getValue());
			bld.append(r.suffix);

			document.replace(r.offset, r.length, bld.toString());
			arg.setOffset(r.getInjectOffset());
			arg.setLength(r.getInjectLength(bld.length()));
		}
		catch(BadLocationException e) {
			throw new RuntimeException(e);
		}
		catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

	private CallSticker updateJsonDependency(CallSticker call, String... values) {
		ArgSticker sticker = new ArgSticker(call.getOffset(), call.getLength(), createDepsObj(values));
		updateJson(sticker, 4);
		call.setOffset(sticker.getOffset());
		call.setLength(sticker.getLength());
		return call;
	}
}
