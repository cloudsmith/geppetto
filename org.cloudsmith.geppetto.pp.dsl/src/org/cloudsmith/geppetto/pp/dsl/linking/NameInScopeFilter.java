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

import java.util.Iterator;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterators;

/**
 * Iterable filter for searching for a name up the scope chain.
 * 
 */
public class NameInScopeFilter implements Iterable<IEObjectDescription> {
	public static class NameInScopePredicate implements Predicate<IEObjectDescription> {
		final QualifiedName scopeName;

		final QualifiedName name;

		final boolean absolute;

		final Match startsWith;

		final EClass[] eclasses;

		public NameInScopePredicate(boolean absolute, Match matchingStrategy, QualifiedName name,
				QualifiedName scopeName, EClass[] eclasses) {
			this.absolute = absolute;
			this.scopeName = scopeName == null
					? QualifiedName.EMPTY
					: scopeName;
			this.name = name;
			this.eclasses = eclasses;
			this.startsWith = matchingStrategy;
		}

		@Override
		public boolean apply(IEObjectDescription candidate) {
			QualifiedName candidateName = candidate.getQualifiedName();
			// error, not a valid name (can not possibly match).
			if(candidateName.getSegmentCount() == 0)
				return false;

			// it is faster to compare exact match as this is a common case, before trying isSuperTypeOf
			int found = -1;
			for(int i = 0; i < eclasses.length; i++)
				if(eclasses[i] == candidate.getEClass() || eclasses[i].isSuperTypeOf(candidate.getEClass())) {
					found = i;
					break;
				}
			if(found < 0)
				return false; // wrong type

			if(absolute)
				return candidateName.equals(name);

			// Since most references are exact (they are global), this is the fastest for the common case.
			if(matches(candidateName, name, startsWith))
				return true;
			// if(candidateName.equals(name) || (startsWith && candidateName.startsWith(name))
			// return true;

			// need to find the common outer scope
			QualifiedName candidateParent = candidateName.skipLast(1);

			// Note: it is not possible to refer to the parent i.e. class foo::bar { bar { }}

			// find the common outer scope
			int commonCount = 0;
			int limit = Math.min(scopeName.getSegmentCount(), candidateParent.getSegmentCount());
			for(int i = 0; i < limit; i++)
				if(scopeName.getSegment(i).equals(candidateName.getSegment(i)))
					commonCount++;
				else
					break;
			// if no common ancestor, then equality check above should have found it.
			if(commonCount == 0)
				return false;

			// commonPart+requestedName == candidate (i.e. wanted "c::d" in scope "a::b" - check "a::b::c::d"
			if(startsWith == Match.STARTS_WITH)
				return candidateName.startsWith(scopeName.skipLast(scopeName.getSegmentCount() - commonCount).append(
					name));
			return scopeName.skipLast(scopeName.getSegmentCount() - commonCount).append(name).equals(candidateName);
		}

		private boolean matches(QualifiedName candidate, QualifiedName query, Match matchingStrategy) {
			if(matchingStrategy != Match.STARTS_WITH)
				return candidate.equals(query);
			if(query.getSegmentCount() > candidate.getSegmentCount())
				return false;
			if(query.getSegmentCount() == 0 && matchingStrategy == Match.STARTS_WITH)
				return true; // everything starts with nothing
			if(!candidate.skipLast(1).equals(query.skipLast(1)))
				return false;
			if(!candidate.getLastSegment().startsWith(query.getLastSegment()))
				return false;
			return true;
		}
	}

	public static enum Match {
		/** Compare using equals, no need to find all matching */
		EQUALS,

		/** Compare using equals, find all that match */
		ALL_EQUALS,

		/** Compare using starts with, find all that match. */
		STARTS_WITH
	}

	private final Iterable<IEObjectDescription> unfiltered;

	final private NameInScopePredicate filter;

	NameInScopeFilter(Match matchingStrategy, Iterable<IEObjectDescription> unfiltered, QualifiedName name,
			QualifiedName scope, EClass[] eclasses) {
		boolean absolute = name.getSegmentCount() > 0 && "".equals(name.getSegment(0));
		this.unfiltered = unfiltered;
		filter = new NameInScopePredicate(absolute, matchingStrategy, absolute
				? name.skipFirst(1)
				: name, scope, eclasses);
	}

	public Iterator<IEObjectDescription> iterator() {
		return Iterators.filter(unfiltered.iterator(), filter);
	}

}
