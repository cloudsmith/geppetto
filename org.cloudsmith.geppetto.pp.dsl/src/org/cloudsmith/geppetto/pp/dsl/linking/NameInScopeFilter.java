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

import java.util.EnumSet;
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
	public static interface Match {
		/** Compare using equals, no need to find all matching */
		public static final SearchStrategy EQUALS = new SearchStrategy();

		/** Compare using equals, find all that match */
		public static final SearchStrategy ALL_EQUALS = new SearchStrategy(
			EnumSet.complementOf(EnumSet.of(SearchType.EXISTS)));

		/** Compare using starts with, find all that match. */
		public static final SearchStrategy STARTS_WITH = new SearchStrategy(
			SearchType.GLOBAL, SearchType.INHERITED, SearchType.OUTER_SCOPES);

		public static final SearchStrategy NO_OUTER = new SearchStrategy(
			SearchType.EQUALS, SearchType.GLOBAL, SearchType.INHERITED);

		public static final SearchStrategy NO_OUTER_EXISTS = new SearchStrategy(
			SearchType.EQUALS, SearchType.EXISTS, SearchType.GLOBAL, SearchType.INHERITED);

		public static final SearchStrategy NO_OUTER_STARTS_WITH = new SearchStrategy(
			SearchType.GLOBAL, SearchType.INHERITED);
	}

	public static class NameInScopePredicate implements Predicate<IEObjectDescription> {
		final QualifiedName scopeName;

		final QualifiedName name;

		final boolean absolute;

		final SearchStrategy matchStrategy;

		final EClass[] eclasses;

		public NameInScopePredicate(boolean absolute, SearchStrategy matchingStrategy, QualifiedName name,
				QualifiedName scopeName, EClass[] eclasses) {
			this.absolute = absolute;
			this.scopeName = scopeName == null
					? QualifiedName.EMPTY
					: scopeName;
			this.name = name;
			this.eclasses = eclasses;
			this.matchStrategy = matchingStrategy;
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
			if(matches(candidateName, name, matchStrategy))
				return true;

			if(!matchStrategy.searchOuterScopes())
				return false;

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
			{ // TODO: Should not be done for variables
				// if no common ancestor, then equality check above should have found it.
				if(commonCount == 0)
					return false;

				// commonPart+requestedName == candidate (i.e. wanted "c::d" in scope "a::b" - check "a::b::c::d"
				if(matchStrategy.matchStartsWith())
					return candidateName.startsWith(scopeName.skipLast(scopeName.getSegmentCount() - commonCount).append(
						name));
				return scopeName.skipLast(scopeName.getSegmentCount() - commonCount).append(name).equals(candidateName);
			}
		}

		private boolean matches(QualifiedName candidate, QualifiedName query, SearchStrategy matchingStrategy) {
			// equals matching
			if(matchingStrategy.matchEquals())
				return candidate.equals(query);

			// starts with matching
			if(query.getSegmentCount() > candidate.getSegmentCount())
				return false;
			if(query.getSegmentCount() == 0)
				return true; // everything starts with nothing

			// All segments (except last) in query must be equal to the corresponding segment in candidate
			for(int i = 0; i < query.getSegmentCount() - 1; i++)
				if(!candidate.getSegment(i).equals(query.getSegment(i)))
					return false;

			// the last segment in query, must be the start of the corresponding segment in candidate or be empty
			if(!("".equals(query.getLastSegment()) || candidate.getSegment(query.getSegmentCount() - 1).startsWith(
				query.getLastSegment())))
				return false;
			return true;
		}
	}

	public static class SearchStrategy {
		private EnumSet<SearchType> flags;

		/**
		 * Search equals, inherited, outer (global).
		 */
		public SearchStrategy() {
			flags = EnumSet.allOf(SearchType.class);
		}

		public SearchStrategy(EnumSet<SearchType> flags) {
			this.flags = flags;
		}

		public SearchStrategy(SearchType first, SearchType... rest) {
			flags = EnumSet.of(first, rest);
		}

		/**
		 * @return
		 */
		public boolean isExists() {
			return flags.contains(SearchType.EXISTS);
		}

		public boolean matchEquals() {
			return flags.contains(SearchType.EQUALS);
		}

		public boolean matchStartsWith() {
			return !matchEquals();
		}

		public boolean searchGlobal() {
			return flags.contains(SearchType.GLOBAL) || searchOuterScopes();
		}

		public boolean searchInherited() {
			return flags.contains(SearchType.INHERITED);
		}

		public boolean searchOuterScopes() {
			return flags.contains(SearchType.OUTER_SCOPES);
		}
	}

	public static enum SearchType {
		/** Compare using equals, find all that match. (Implies Start with otherwise) */
		EQUALS,

		/** Search up the inheritance chain. */
		INHERITED,

		/** Search with widening scope */
		OUTER_SCOPES,

		/** Search global scope (implied when using WIDENING) */
		GLOBAL,

		/** Stops searching when at least one is found. */
		EXISTS, ;
	}

	private final Iterable<IEObjectDescription> unfiltered;

	final private NameInScopePredicate filter;

	NameInScopeFilter(SearchStrategy matchingStrategy, Iterable<IEObjectDescription> unfiltered, QualifiedName name,
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
