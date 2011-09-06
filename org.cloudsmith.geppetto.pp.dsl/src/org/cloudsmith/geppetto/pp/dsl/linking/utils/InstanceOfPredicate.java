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
package org.cloudsmith.geppetto.pp.dsl.linking.utils;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.common.base.Predicate;

/**
 * Predicate that tests if a given IEObjectDescription's type is an instance of a set of given classes.
 * 
 */
public class InstanceOfPredicate implements Predicate<IEObjectDescription> {

	final EClass[] eclasses;

	public InstanceOfPredicate(EClass[] eclasses) {
		this.eclasses = eclasses;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.google.common.base.Predicate#apply(java.lang.Object)
	 */
	@Override
	public boolean apply(IEObjectDescription candidate) {
		// it is faster to compare exact match as this is a common case, before trying isSuperTypeOf
		for(int i = 0; i < eclasses.length; i++)
			if(eclasses[i] == candidate.getEClass() || eclasses[i].isSuperTypeOf(candidate.getEClass())) {
				return true;
			}
		return false;
	}
}
