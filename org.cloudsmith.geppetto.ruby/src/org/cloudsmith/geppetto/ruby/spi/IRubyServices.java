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
package org.cloudsmith.geppetto.ruby.spi;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

import org.cloudsmith.geppetto.ruby.PPFunctionInfo;
import org.cloudsmith.geppetto.ruby.PPTypeInfo;
import org.cloudsmith.geppetto.ruby.RubySyntaxException;


/**
 * The interface for a ruby service that provides parsing (to produce syntax errors and warnings), and
 * puppet specific parsing services such as finding custom functions and types.
 *
 */
public interface IRubyServices {
	
	public void setUp();
	public void tearDown();
	public IRubyParseResult parse(File file) throws IOException;
	IRubyParseResult parse(String path, Reader reader) throws IOException;

	public List<PPFunctionInfo> getFunctionInfo(File file) throws IOException, RubySyntaxException;
	public List<PPTypeInfo> getTypeInfo(File file) throws IOException, RubySyntaxException;
	public List<PPTypeInfo> getTypeInfo(String fileName, Reader reader) throws IOException, RubySyntaxException;
	/**
	 * Parses and returns additional properties from a ruby file - such "extra" properties are returned
	 * as a PPTypeInfo that is partially filled with information.
	 * @param file
	 * @return
	 * @throws IOException
	 * @throws RubySyntaxException
	 */
	public List<PPTypeInfo> getTypePropertiesInfo(File file) throws IOException,
	RubySyntaxException;
	public List<PPTypeInfo> getTypePropertiesInfo(String fileName, Reader reader) throws IOException,
	RubySyntaxException;
		
	/**
	 * Indicates if this is a real service or one that produces no results.
	 * @return
	 */
	public boolean isMockService();
	
	/**
	 * Loads a PPTypeInfo describing metaparameters.
	 * @param file - should typically reference 'Type.rb' in a puppet distribution.
	 * @return
	 * @throws IOException
	 * @throws RubySyntaxException
	 */
	public PPTypeInfo getMetaTypeProperties(File file) throws IOException,
			RubySyntaxException;
	
	public List<PPFunctionInfo> getFunctionInfo(String fileName, Reader reader) throws IOException, RubySyntaxException;
	public PPTypeInfo getMetaTypeProperties(String fileName,
			Reader reader) throws IOException, RubySyntaxException;
	
}
