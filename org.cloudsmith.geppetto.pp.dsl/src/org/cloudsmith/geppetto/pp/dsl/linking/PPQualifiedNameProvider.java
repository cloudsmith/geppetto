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
import org.eclipse.emf.ecore.EObject;
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
}
