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
package org.cloudsmith.geppetto.pp.dsl.pptp;

import java.util.Iterator;

import org.cloudsmith.geppetto.pp.pptp.Function;
import org.cloudsmith.geppetto.pp.pptp.Parameter;
import org.cloudsmith.geppetto.pp.pptp.Property;
import org.cloudsmith.geppetto.pp.pptp.Type;

import com.google.inject.ImplementedBy;

/**
 * Interface for Puppet Target Platform
 * 
 */
@ImplementedBy(PPTPManager.class)
public interface IPPTP {

	public abstract Function findFunction(String name);

	public abstract Parameter findParameter(Type t, String name);

	public abstract Property findProperty(Type t, String name);

	/**
	 * Returns the first found type having the given name (if the given name starts with
	 * an upper case letter as when searching for the type itself it is made into lower case before
	 * searching).
	 * 
	 * @param typeName
	 * @return first found type with given name, or null
	 */
	public abstract Type findType(String name);

	/**
	 * @return an iterator over all functions
	 */
	public abstract Iterator<Function> functions();

	/**
	 * Returns an iterator with all functions starting with the given prefix.
	 * 
	 * @param prefix
	 * @return
	 */
	public abstract Iterator<Function> functionsStartingWith(String prefix);

	public abstract Iterator<Parameter> parametersStartingWith(Type t, String prefix);

	public abstract Iterator<Property> propertiesStartingWith(Type t, String prefix);

	/**
	 * @return an iterator over all types
	 */
	public abstract Iterator<Type> types();

	/**
	 * Returns an iterator with all types starting with the given prefix. (If the given prefix starts with
	 * an upper case letter as when searching for the type itself it is made into lower case before
	 * searching).
	 * 
	 * @param prefix
	 * @return
	 */
	public abstract Iterator<Type> typesStartingWith(String prefix);
}
