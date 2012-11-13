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
package org.cloudsmith.geppetto.ruby.resource;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

import org.cloudsmith.geppetto.pp.pptp.Function;
import org.cloudsmith.geppetto.pp.pptp.MetaType;
import org.cloudsmith.geppetto.pp.pptp.PPTPFactory;
import org.cloudsmith.geppetto.pp.pptp.Parameter;
import org.cloudsmith.geppetto.pp.pptp.Property;
import org.cloudsmith.geppetto.pp.pptp.Type;
import org.cloudsmith.geppetto.pp.pptp.TypeFragment;
import org.cloudsmith.geppetto.ruby.PPFunctionInfo;
import org.cloudsmith.geppetto.ruby.PPTypeInfo;
import org.cloudsmith.geppetto.ruby.RubyHelper;
import org.cloudsmith.geppetto.ruby.RubySyntaxException;
import org.cloudsmith.geppetto.ruby.spi.IRubyIssue;
import org.cloudsmith.geppetto.ruby.spi.IRubyParseResult;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.impl.ResourceImpl;

/**
 * A Resource that loads .rb files containing Puppet "target platform"
 * information. Ruby source on particular paths are transformed into PPTP model
 * contents.
 * 
 */
public class PptpRubyResource extends ResourceImpl {

	public enum LoadType {
		TYPE, TYPEFRAGMENT, META, FUNCTION, IGNORED;

	}

	public static class RubyIssueDiagnostic implements Diagnostic {
		private IRubyIssue issue;

		public RubyIssueDiagnostic(IRubyIssue issue) {
			this.issue = issue;
		}

		/**
		 * @throws UnsupportedOperationException
		 *             - column is not available.
		 */
		@Override
		public int getColumn() {
			throw new UnsupportedOperationException();
		}

		@Override
		public int getLine() {
			return issue.getLine();
		}

		@Override
		public String getLocation() {
			return issue.getFileName();
		}

		@Override
		public String getMessage() {
			return issue.getMessage();
		}

	}

	public static class RubySyntaxExceptionDiagnostic implements Diagnostic {
		private RubySyntaxException issue;

		public RubySyntaxExceptionDiagnostic(RubySyntaxException issue) {
			this.issue = issue;
		}

		/**
		 * @throws UnsupportedOperationException
		 *             - column is not available.
		 */
		@Override
		public int getColumn() {
			throw new UnsupportedOperationException();
		}

		@Override
		public int getLine() {
			return issue.getLine();
		}

		@Override
		public String getLocation() {
			return issue.getFilename();
		}

		@Override
		public String getMessage() {
			return issue.getMessage();
		}

	}

	/**
	 * (SOMEROOT/lib/puppet/) parser/functions/F.rb (SOMEROOT/lib/puppet/)
	 * type/T.rb (SOMEROOT/lib/puppet/) type/FRAGMENTDIR/TypeFragment.rb
	 * (SOMEROOT/lib/puppet/) type.rb - META TYPE (typically in a distro)
	 * 
	 * @return
	 */
	public static LoadType detectLoadType(URI uri) {
		List<String> segments = uri.segmentsList();
		final int lastPuppet = segments.lastIndexOf("puppet");
		final int segmentCount = segments.size();
		if(lastPuppet < 0)
			return LoadType.IGNORED;

		int idx = lastPuppet + 1;
		if(idx < segmentCount) {
			String segment = segments.get(idx);
			if("parser".equals(segment)) {
				idx++;
				if(idx < segmentCount) {
					segment = segments.get(idx);
					if("functions".equals(segment)) {
						idx++;
						if(idx == segmentCount - 1 && segments.get(idx).endsWith(".rb"))
							return LoadType.FUNCTION;
					}
				}
			}
			else if("type".equals(segment)) {
				idx++;
				if(idx < segmentCount) {
					segment = segments.get(idx);
					// a .rb file under type
					if(segment.endsWith(".rb") && idx == segmentCount - 1)
						return LoadType.TYPE;

					// typefragment must be in a subdir of type, e.g.
					// type/file/X.rb
					idx++;
					if(idx == segmentCount - 1 && segments.get(idx).endsWith(".rb"))
						return LoadType.TYPEFRAGMENT;
				}
			}
			else if("type.rb".equals(segment) && idx == segmentCount - 1) {
				return LoadType.META;
			}
		}
		return LoadType.IGNORED;
	}

	private LoadType loadType;

	/**
	 * Create an instance with a reference to a resource in Ruby text format.
	 * 
	 * @param uri
	 */
	public PptpRubyResource(URI uri) {
		super(uri);
	}

	protected LoadType detectLoadType() {
		return detectLoadType(getURI());
	}

	@Override
	public void doLoad(InputStream in, Map<?, ?> options) throws IOException {
		loadType = detectLoadType();
		internalLoadRuby(in);
	}

	/**
	 * Loads one (or more) PPTP Type, PPTP Function, PPTP Meta, or PPTP Fragment
	 * depending on the type of load (determined by looking at the path to the
	 * parsed .rb file).
	 * 
	 * @throws IOException
	 */
	protected void internalLoadRuby(InputStream inputStream) throws IOException {
		if(loadType == LoadType.IGNORED) {
			this.getContents().clear();
			return;
		}
		RubyHelper helper = new RubyHelper();
		helper.setUp();

		URI uri = getURI();
		try {
			switch(loadType) {
				case TYPE: {
					List<PPTypeInfo> typeInfo = helper.getTypeInfo(uri.path(), new InputStreamReader(inputStream));
					for(PPTypeInfo info : typeInfo) {
						Type type = PPTPFactory.eINSTANCE.createType();
						type.setName(info.getTypeName());
						type.setDocumentation(info.getDocumentation());
						for(Map.Entry<String, PPTypeInfo.Entry> entry : info.getParameters().entrySet()) {
							Parameter parameter = PPTPFactory.eINSTANCE.createParameter();
							parameter.setName(entry.getKey());
							parameter.setDocumentation(entry.getValue().documentation);
							parameter.setRequired(entry.getValue().isRequired());
							type.getParameters().add(parameter);
						}
						for(Map.Entry<String, PPTypeInfo.Entry> entry : info.getProperties().entrySet()) {
							Property property = PPTPFactory.eINSTANCE.createProperty();
							property.setName(entry.getKey());
							property.setDocumentation(entry.getValue().documentation);
							property.setRequired(entry.getValue().isRequired());
							type.getProperties().add(property);
						}
						getContents().add(type);
					}
				}
					break;

				case FUNCTION: {
					List<PPFunctionInfo> functions = helper.getFunctionInfo(uri.path(), new InputStreamReader(
						inputStream));

					for(PPFunctionInfo info : functions) {
						Function pptpFunc = PPTPFactory.eINSTANCE.createFunction();
						pptpFunc.setName(info.getFunctionName());
						pptpFunc.setRValue(info.isRValue());
						pptpFunc.setDocumentation(info.getDocumentation());
						getContents().add(pptpFunc);
					}
				}
					break;

				case META: {
					PPTypeInfo info = helper.getMetaTypeInfo(uri.path(), new InputStreamReader(inputStream));

					MetaType type = PPTPFactory.eINSTANCE.createMetaType();
					type.setName(info.getTypeName());
					type.setDocumentation(info.getDocumentation());
					for(Map.Entry<String, PPTypeInfo.Entry> entry : info.getParameters().entrySet()) {
						Parameter parameter = PPTPFactory.eINSTANCE.createParameter();
						parameter.setName(entry.getKey());
						parameter.setDocumentation(entry.getValue().documentation);
						parameter.setRequired(entry.getValue().isRequired());
						type.getParameters().add(parameter);
					}
					// TODO: Scan the puppet source for providers for the type
					// This is a CHEAT -
					// https://github.com/cloudsmith/geppetto/issues/37
					Parameter p = PPTPFactory.eINSTANCE.createParameter();
					p.setName("provider");
					p.setDocumentation("");
					p.setRequired(false);
					type.getParameters().add(p);

					getContents().add(type);
					break;
				}

				case TYPEFRAGMENT: {
					for(PPTypeInfo type : helper.getTypeFragments(uri.path(), new InputStreamReader(inputStream))) {
						TypeFragment fragment = PPTPFactory.eINSTANCE.createTypeFragment();
						fragment.setName(type.getTypeName());

						// add the properties (will typically load just one).
						for(Map.Entry<String, PPTypeInfo.Entry> entry : type.getProperties().entrySet()) {
							Property property = PPTPFactory.eINSTANCE.createProperty();
							property.setName(entry.getKey());
							property.setDocumentation(entry.getValue().documentation);
							property.setRequired(entry.getValue().isRequired());
							fragment.getProperties().add(property);
						}

						// add the parameters (will typically load just one).
						for(Map.Entry<String, PPTypeInfo.Entry> entry : type.getParameters().entrySet()) {
							Parameter parameter = PPTPFactory.eINSTANCE.createParameter();
							parameter.setName(entry.getKey());
							parameter.setDocumentation(entry.getValue().documentation);
							parameter.setRequired(entry.getValue().isRequired());
							fragment.getParameters().add(parameter);
						}
						getContents().add(fragment);
					}
					break;
				}
				case IGNORED:
					break;
			}
		}
		catch(RubySyntaxException syntaxException) {
			getErrors().add(new RubySyntaxExceptionDiagnostic(syntaxException));
		}
		finally {
			helper.tearDown();
		}
	}

	@Override
	public void load(Map<?, ?> options) throws IOException {
		if(!super.isLoaded) {
			super.isLoading = true;

			loadType = detectLoadType();
			internalLoadRuby(getURIConverter().createInputStream(uri));

			super.isLoading = false;
			super.isLoaded = true;
		}
	}

	/**
	 * Translates ruby issues to diagnostics using instances of {@link RubyIssueDiagnostic}. All syntax issues are reported as errors,
	 * all others as warnings.
	 * 
	 * @param parseResult
	 */
	protected void rubyIssuesToDiagnostics(IRubyParseResult parseResult) {
		for(IRubyIssue issue : parseResult.getIssues()) {
			if(issue.isSyntaxError())
				getErrors().add(new RubyIssueDiagnostic(issue));
			else
				getWarnings().add(new RubyIssueDiagnostic(issue));
		}
	}

	@Override
	public void save(Map<?, ?> options) throws IOException {
		throw new UnsupportedOperationException("Save of PPTP parsed from a ruby file is not possible.");
	}
}
