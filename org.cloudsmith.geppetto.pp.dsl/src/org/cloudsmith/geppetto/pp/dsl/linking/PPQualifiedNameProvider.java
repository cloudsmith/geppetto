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

import org.cloudsmith.geppetto.pp.Definition;
import org.cloudsmith.geppetto.pp.DefinitionArgument;
import org.cloudsmith.geppetto.pp.HostClassDefinition;
import org.cloudsmith.geppetto.pp.PPPackage;
import org.cloudsmith.geppetto.pp.VariableExpression;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;

import com.google.inject.Inject;

/**
 * A Puppet Qualified Name provider.
 * 
 */
public class PPQualifiedNameProvider extends DefaultDeclarativeQualifiedNameProvider {

	public static QualifiedName splice(QualifiedName a, QualifiedName b) {
		return a == null
				? b
				: a.append(b);
	}

	@Inject
	private IQualifiedNameConverter converter;

	/**
	 * The fully qualified name of the closest named parent.
	 * 
	 * @param o
	 * @return
	 */
	QualifiedName getParentsFullyQualifiedName(EObject o) {
		for(EObject tmp = o.eContainer(); tmp != null; tmp = tmp.eContainer()) {
			QualifiedName n = getFullyQualifiedName(tmp);
			if(n != null)
				return n;
		}
		return null;
	}

	QualifiedName qualifiedName(Definition o) {
		return splice(getParentsFullyQualifiedName(o), converter.toQualifiedName(o.getClassName()));
	}

	QualifiedName qualifiedName(DefinitionArgument o) {
		// stripping of $ is done by PPQualifiedNameConverter
		return splice(getParentsFullyQualifiedName(o), converter.toQualifiedName(o.getArgName()));
	}

	QualifiedName qualifiedName(HostClassDefinition o) {
		return splice(getParentsFullyQualifiedName(o), converter.toQualifiedName(o.getClassName()));
	}

	QualifiedName qualifiedName(VariableExpression o) {
		boolean assignment = o.eContainer().eClass() == PPPackage.Literals.ASSIGNMENT_EXPRESSION;
		boolean append = o.eContainer().eClass() == PPPackage.Literals.APPEND_EXPRESSION;
		if(!(assignment || append))
			return null;
		EStructuralFeature feature = o.eContainingFeature();
		if(feature == null || PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR != feature)
			return null;

		// Do the export of the variable name in context
		String name = o.getVarName();
		if(name.startsWith("$"))
			if(name.length() > 1)
				name = name.substring(1);
			else
				name = "";
		if(name.length() > 0)
			return splice(getParentsFullyQualifiedName(o), converter.toQualifiedName(name));
		return null;
	}
}
