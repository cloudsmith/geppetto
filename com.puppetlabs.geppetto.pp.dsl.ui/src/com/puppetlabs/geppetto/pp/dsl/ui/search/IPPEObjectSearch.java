/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Itemis AG http://www.itemis.eu - initial API and implementation
 *   Puppet Labs - specialization to puppet
 * 
 */
package com.puppetlabs.geppetto.pp.dsl.ui.search;

import java.util.Collection;

import com.puppetlabs.geppetto.pp.PPPackage;
import com.puppetlabs.geppetto.pp.pptp.PPTPPackage;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.ui.dialogs.SearchPattern;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.inject.ImplementedBy;
import com.google.inject.Inject;

@ImplementedBy(IPPEObjectSearch.Default.class)
public interface IPPEObjectSearch {

	public static class Default implements IPPEObjectSearch {
		@Inject
		private IResourceDescriptions resourceDescriptions;

		@Inject
		private IQualifiedNameConverter qualifiedNameConverter;

		public Iterable<IEObjectDescription> findMatches(final String searchPattern,
				final Collection<EClass> acceptedClasses) {
			return Iterables.filter(getSearchScope(), getSearchPredicate(searchPattern, acceptedClasses));
		}

		public IResourceDescriptions getResourceDescriptions() {
			return resourceDescriptions;
		}

		protected Predicate<IEObjectDescription> getSearchPredicate(final String stringPattern,
				final Collection<EClass> acceptedClasses) {
			final SearchPattern searchPattern = new SearchPattern();
			searchPattern.setPattern(stringPattern);

			return new Predicate<IEObjectDescription>() {
				public boolean apply(IEObjectDescription input) {
					if(isNameMatches(searchPattern, input) && isClassAccepted(input, acceptedClasses)) {
						return true;
					}
					return false;
				}
			};
		}

		protected Iterable<IEObjectDescription> getSearchScope() {
			return Iterables.concat(Iterables.transform(
				getResourceDescriptions().getAllResourceDescriptions(),
				new Function<IResourceDescription, Iterable<IEObjectDescription>>() {
					public Iterable<IEObjectDescription> apply(IResourceDescription from) {
						return from.getExportedObjects();
					}
				}));
		}

		protected boolean isAcceptedByPP(IEObjectDescription od) {
			switch(od.getEClass().getClassifierID()) {
				case PPPackage.HOST_CLASS_DEFINITION:
				case PPPackage.DEFINITION:
				case PPPackage.DEFINITION_ARGUMENT:
				case PPPackage.VARIABLE_EXPRESSION:
				case PPPackage.NODE_DEFINITION:
					return true;
			}
			return false;

		}

		protected boolean isAcceptedByPPTP(IEObjectDescription od) {
			// Contributions from .pptp files are not meaningful to open
			String path = od.getEObjectURI().path();
			if(path != null && path.endsWith(".pptp"))
				return false;

			// all other are ok (i.e. typically from .rb)
			// Only included those that are meaningful for user to open
			switch(od.getEClass().getClassifierID()) {
				case PPTPPackage.TYPE:
				case PPTPPackage.FUNCTION:
				case PPTPPackage.PARAMETER:
				case PPTPPackage.PROPERTY:
				case PPTPPackage.META_TYPE:
				case PPTPPackage.META_VARIABLE:
					return true;
			}
			return false;

		}

		protected boolean isClassAccepted(IEObjectDescription od, Collection<EClass> acceptedClasses) {
			EClass eclass = od.getEClass();
			EPackage epackage = eclass.getEPackage();
			// user supplied filter
			if(acceptedClasses != null && acceptedClasses.size() != 0 && !acceptedClasses.contains(eclass))
				return false;

			if(epackage == PPPackage.eINSTANCE)
				return isAcceptedByPP(od);
			if(epackage == PPTPPackage.eINSTANCE)
				return isAcceptedByPPTP(od);
			return false;
		}

		protected boolean isNameMatches(SearchPattern searchPattern, IEObjectDescription eObjectDescription) {
			String qualifiedName = qualifiedNameConverter.toString(eObjectDescription.getQualifiedName());
			if(qualifiedName == null)
				return false;

			if(searchPattern.matches(qualifiedName))
				return true;

			int index = qualifiedName.lastIndexOf("::");
			if(index != -1 && searchPattern.matches(qualifiedName.substring(index + 1)))
				return true;

			return false;
		}

		public void setResourceDescriptions(IResourceDescriptions resourceDescriptions) {
			this.resourceDescriptions = resourceDescriptions;
		}

	}

	Iterable<IEObjectDescription> findMatches(String searchPattern, Collection<EClass> acceptedClasses);
}
