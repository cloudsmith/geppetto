/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   itemis AG - initial API and implementation
 *   Puppet Labs
 * 
 */
package com.puppetlabs.geppetto.pp.dsl.ui.editor.findrefs;

import static java.util.Collections.singleton;

import java.util.Iterator;
import java.util.Set;

import com.puppetlabs.geppetto.pp.dsl.ui.editor.findrefs.PPReferenceFinder.IPPQueryData;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IStorage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IGlobalServiceProvider;
import org.eclipse.xtext.resource.IReferenceDescription;
import org.eclipse.xtext.ui.resource.IStorage2UriMapper;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.SimpleAttributeResolver;

import com.google.common.base.Predicate;
import com.google.inject.Inject;

/**
 * @author Jan Koehnlein - Initial contribution and API
 */
public class FindReferenceQueryDataFactory {

	/**
	 * Provides the label at the top of the search result page
	 * 
	 */
	public static class QueryLabelProvider {
		@Inject
		private IStorage2UriMapper storage2UriMapper;

		@Inject
		private IQualifiedNameProvider qualifiedNameProvider;

		@Inject
		private IQualifiedNameConverter qualifiedNameConverter;

		public String get(EObject target) {
			StringBuilder builder = new StringBuilder();
			builder.append("Puppet references to ");
			QualifiedName qualifiedName = qualifiedNameProvider.getFullyQualifiedName(target);
			if(qualifiedName != null) {
				builder.append(qualifiedNameConverter.toString(qualifiedName));
			}
			else {
				String simpleName = SimpleAttributeResolver.NAME_RESOLVER.getValue(target);
				if(simpleName != null)
					builder.append(simpleName);
				else
					builder.append(target.eResource().getURIFragment(target));
			}
			Iterator<Pair<IStorage, IProject>> storages = storage2UriMapper.getStorages(EcoreUtil.getURI(target)).iterator();
			if(storages.hasNext()) {
				builder.append(" (").append(storages.next().getFirst().getFullPath().toString()).append(")");
			}
			return builder.toString();
		}
	}

	@Inject
	protected IGlobalServiceProvider globalServiceProvider;

	protected String createLabel(EObject target) {
		QueryLabelProvider queryLabelProvider = globalServiceProvider.findService(target, QueryLabelProvider.class);
		// TODO remove after fix: NPE in mark occurrences on a JVM element
		if(queryLabelProvider == null || target.eIsProxy() || target.eResource() == null)
			return "";
		return queryLabelProvider.get(target);
	}

	public IPPQueryData createQueryData(EObject element, URI localResourceURI) {
		URI targetURI = EcoreUtil.getURI(element);
		IPPQueryData queryData = new PPReferenceQueryData(
			targetURI, createTargetURIs(element), localResourceURI, createResultFilter(element), createLabel(element));
		return queryData;
	}

	protected Predicate<IReferenceDescription> createResultFilter(EObject target) {
		return null;
	}

	protected Set<URI> createTargetURIs(EObject target) {
		return singleton(EcoreUtil.getURI(target));
	}
}
