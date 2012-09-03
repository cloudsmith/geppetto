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
package org.cloudsmith.geppetto.pp.dsl.contentassist;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import org.apache.commons.codec.language.DoubleMetaphone;
import org.apache.commons.lang.StringUtils;
import org.cloudsmith.geppetto.common.score.ScoreKeeper;
import org.cloudsmith.geppetto.common.score.ScoreKeeper.ScoreEntry;
import org.cloudsmith.geppetto.pp.PPPackage;
import org.cloudsmith.geppetto.pp.dsl.PPDSLConstants;
import org.cloudsmith.geppetto.pp.dsl.linking.PPSearchPath;
import org.cloudsmith.geppetto.pp.pptp.PPTPPackage;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.google.inject.Inject;

/**
 * Generator of proposals
 * 
 */
public class PPProposalsGenerator {
	/**
	 * compares the pronunciation difference between given reference and candidates
	 * 
	 */
	public static class PronunciationComparator implements Comparator<String> {

		private DoubleMetaphone encoder;

		private String metaphoneName;

		PronunciationComparator(DoubleMetaphone encoder, String metaphoneReference) {
			this.encoder = encoder;
			this.metaphoneName = metaphoneReference;
		}

		@Override
		public int compare(String a, String b) {
			String am = encoder.encode(a);
			String bm = encoder.encode(b);
			int al = StringUtils.getLevenshteinDistance(metaphoneName, am);
			int bl = StringUtils.getLevenshteinDistance(metaphoneName, bm);
			if(al == bl)
				return 0;
			return al < bl
					? -1
					: 1;
		}

	}

	/**
	 * PP FQN to/from Xtext QualifiedName converter.
	 */
	@Inject
	IQualifiedNameConverter converter;

	protected final static EClass[] DEF_AND_TYPE_ARGUMENTS = {
			PPPackage.Literals.DEFINITION_ARGUMENT, PPTPPackage.Literals.TYPE_ARGUMENT };

	protected final static EClass[] DEF_AND_TYPE = { PPTPPackage.Literals.TYPE, PPPackage.Literals.DEFINITION };

	/**
	 * Computes attribute proposals where the class/definition name must match exactly, but where
	 * parameters are processed with fuzzy logic.
	 * 
	 * @param currentName
	 * @param descs
	 * @param searchPath
	 *            TODO
	 * @param types
	 * @return
	 */
	public String[] computeAttributeProposals(final QualifiedName currentName, Collection<IEObjectDescription> descs,
			PPSearchPath searchPath) {
		if(currentName.getSegmentCount() < 2)
			return new String[0];

		final DoubleMetaphone encoder = new DoubleMetaphone();
		final String metaphoneName = encoder.encode(currentName.getLastSegment());

		Collection<String> proposals = generateAttributeCandidates(currentName, descs, searchPath);
		// propose all, but sort them based on likeness

		String[] result = new String[proposals.size()];
		proposals.toArray(result);
		Arrays.sort(result, new PronunciationComparator(encoder, metaphoneName));
		return result;
	}

	public String[] computeDistinctProposals(String currentName, List<IEObjectDescription> descs) {
		return computeDistinctProposals(currentName, descs, false);
	}

	/**
	 * Attempts to produce a list of more distinct names than the given name by making
	 * name absolute.
	 * 
	 * @param currentName
	 *            the name for which proposals are wanted
	 * @param descs
	 *            index of descriptors
	 */

	public String[] computeDistinctProposals(String currentName, List<IEObjectDescription> descs,
			boolean upperCaseProposals) {
		List<String> proposals = Lists.newArrayList();
		if(currentName.startsWith("::"))
			return new String[0]; // can not make a global name more specific than what it already is
		for(IEObjectDescription d : descs) {
			String s = converter.toString(d.getQualifiedName());
			if(!s.startsWith("::")) {
				String s2 = "::" + s;
				if(!(s2.equals(currentName) || proposals.contains(s2)))
					proposals.add(s2);
			}
			if(s.equals(currentName) || proposals.contains(s))
				continue;
			proposals.add(s);
		}
		String[] props = proposals.toArray(new String[proposals.size()]);
		Arrays.sort(props);
		return upperCaseProposals
				? toUpperCaseProposals(props)
				: props;
	}

	/**
	 * Attempts to produce a list of names that are close to the given name. At most 5 proposals
	 * are generated. The returned proposals are made in order of "pronunciation distance" which is
	 * obtained by taking the Levenshtein distance between the Double Monophone encodings of
	 * candidate and given name. Candidates are selected as the names with shortest Levenshtein distance
	 * and names that are Monophonically equal, or starts or ends monophonically.
	 * 
	 * @param currentName
	 *            the name for which proposals are to be generated
	 * @param descs
	 *            the descriptors of available named values
	 * @param searchPath
	 *            TODO
	 * @param types
	 *            if stated, the wanted types of named values
	 * @return
	 *         array of proposals, possibly empty, but never null.
	 */
	public String[] computeProposals(final String currentName, Collection<IEObjectDescription> descs,
			boolean upperCaseProposals, PPSearchPath searchPath, EClass... types) {
		if(currentName == null || currentName.length() < 1)
			return new String[0];

		// compute the 5 best matches and only accept if score <= 5
		ScoreKeeper<IEObjectDescription> tracker = new ScoreKeeper<IEObjectDescription>(5, false, 5);
		// List<IEObjectDescription> metaphoneAlike = Lists.newArrayList();
		final DoubleMetaphone encoder = new DoubleMetaphone();
		final String metaphoneName = encoder.encode(currentName);

		for(IEObjectDescription d : descs) {
			EClass c = d.getEClass();
			typeok: if(types != null && types.length > 0) {
				for(EClass wanted : types)
					if((wanted == c || wanted.isSuperTypeOf(c)))
						break typeok;
				continue;
			}
			// filter based on path visibility
			if(searchPath.searchIndexOf(d) == -1)
				continue; // not visible according to path

			String candidateName = converter.toString(d.getName());
			tracker.addScore(StringUtils.getLevenshteinDistance(currentName, candidateName), d);
			String candidateMetaphone = encoder.encode(candidateName);
			// metaphone matches are scored on the pronounciation distance
			if(metaphoneName.equals(candidateMetaphone) //
					||
					candidateMetaphone.startsWith(metaphoneName) //
					|| candidateMetaphone.endsWith(metaphoneName) //
			)
				tracker.addScore(StringUtils.getLevenshteinDistance(metaphoneName, candidateMetaphone), d);
			// System.err.printf("Metaphone alike: %s == %s\n", currentName, candidateName);
		}
		List<String> result = Lists.newArrayList();
		// System.err.print("Scores = ");
		for(ScoreEntry<IEObjectDescription> entry : tracker.getScoreEntries()) {
			String s = converter.toString(entry.getData().getName());
			result.add(s);
			// System.err.printf("%d %s, ", entry.getScore(), s);
		}
		// System.err.println();

		String[] proposals = result.toArray(new String[result.size()]);

		PronunciationComparator x = new PronunciationComparator(encoder, metaphoneName);

		Arrays.sort(proposals, x);
		// System.err.print("Order = ");
		// for(int i = 0; i < proposals.length; i++)
		// System.err.printf("%s, ", proposals[i]);
		// System.err.println();
		return upperCaseProposals
				? toUpperCaseProposals(proposals)
				: proposals;
	}

	public String[] computeProposals(final String currentName, Collection<IEObjectDescription> descs,
			PPSearchPath searchPath, EClass... types) {
		return computeProposals(currentName, descs, false, searchPath, types);
	}

	public Collection<String> generateAttributeCandidates(final QualifiedName currentName,
			Collection<IEObjectDescription> descs, PPSearchPath searchPath) {
		// find candidate names
		if(currentName.getSegmentCount() < 2)
			return Collections.emptySet();

		// unique set of proposed attribute names (last segment)
		Set<String> proposed = Sets.newHashSet();
		List<QualifiedName> classesToSearch = Lists.newArrayList();
		Set<QualifiedName> visited = Sets.newHashSet();

		classesToSearch.add(currentName.skipLast(1));

		while(classesToSearch.size() > 0) {
			QualifiedName prefix = classesToSearch.remove(0);
			if(visited.contains(prefix))
				continue;
			visited.add(prefix); // prevent recursion

			// find all that start with className and are properties or parameters
			// also find the class/definition itself (possibly ambiguous).
			for(IEObjectDescription d : descs) {
				if(searchPath.searchIndexOf(d) == -1)
					continue; // not visible
				EClass ec = d.getEClass();
				QualifiedName name = d.getName();
				if(name.startsWith(prefix)) {
					if(name.getSegmentCount() == prefix.getSegmentCount()) {
						// exact match, check if this is the correct type
						if(DEF_AND_TYPE[0].isSuperTypeOf(ec) || DEF_AND_TYPE[1].isSuperTypeOf(ec)) {
							String parentName = d.getUserData(PPDSLConstants.PARENT_NAME_DATA);
							if(parentName != null && parentName.length() > 0)
								classesToSearch.add(converter.toQualifiedName(parentName));
						}
						continue; // exact match can not be an argument
					}
					if(DEF_AND_TYPE_ARGUMENTS[0].isSuperTypeOf(ec) || DEF_AND_TYPE_ARGUMENTS[1].isSuperTypeOf(ec))
						proposed.add(d.getName().getLastSegment());
				}
			}
		}
		return proposed;
	}

	private String toInitialUpperCase(String s) {
		if(s == null || s.length() < 1)
			return s;
		char c = s.charAt(0);
		if(Character.isUpperCase(c))
			return s;
		return Character.toString(c).toUpperCase() + s.substring(1);
	}

	private String toUpperCaseProposal(String original) {
		QualifiedName fqn = converter.toQualifiedName(original);
		String[] segments = new String[fqn.getSegmentCount()];
		for(int i = 0; i < fqn.getSegmentCount(); i++)
			segments[i] = toInitialUpperCase(fqn.getSegment(i));
		return converter.toString(QualifiedName.create(segments));
	}

	private String[] toUpperCaseProposals(String[] original) {
		for(int i = 0; i < original.length; i++) {
			original[i] = toUpperCaseProposal(original[i]);
		}
		return original;
	};

}
