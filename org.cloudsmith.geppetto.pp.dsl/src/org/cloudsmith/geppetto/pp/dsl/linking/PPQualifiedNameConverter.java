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
package org.cloudsmith.geppetto.pp.dsl.linking;

import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;

/**
 * Puppet Qualified Name Converter defines the separator '::'
 * 
 */
public class PPQualifiedNameConverter extends IQualifiedNameConverter.DefaultImpl {
	private static final String separator = "::";

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.naming.IQualifiedNameConverter.DefaultImpl#getDelimiter()
	 */
	@Override
	public String getDelimiter() {
		return separator;
	}

	/**
	 * Removes leading '$' before converting to qualified name.
	 * 
	 * @see org.eclipse.xtext.naming.IQualifiedNameConverter.DefaultImpl#toQualifiedName(java.lang.String)
	 */
	@Override
	public QualifiedName toQualifiedName(String qualifiedNameAsString) {
		if(qualifiedNameAsString == null || qualifiedNameAsString.length() < 1 || qualifiedNameAsString.equals("$"))
			return QualifiedName.EMPTY;
		return super.toQualifiedName(qualifiedNameAsString.startsWith("$")
				? qualifiedNameAsString.substring(1)
				: qualifiedNameAsString);
	}
}
