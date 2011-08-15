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
import java.util.Comparator;
import java.util.List;

import org.apache.commons.codec.language.DoubleMetaphone;
import org.apache.commons.lang.StringUtils;
import org.cloudsmith.geppetto.common.score.ScoreKeeper;
import org.cloudsmith.geppetto.common.score.ScoreKeeper.ScoreEntry;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.resource.IEObjectDescription;

import com.google.inject.Inject;
import com.google.inject.internal.Lists;

/**
 * Generator of proposals
 * 
 */
public class PPProposalsGenerator {
	/**
	 * PP FQN to/from Xtext QualifiedName converter.
	 */
	@Inject
	IQualifiedNameConverter converter;

	/**
	 * Attempts to produce a list of more distinct names than the given name by making
	 * name absolute.
	 * 
	 * @param name
	 * @param s
	 * @param statements
	 * @param i
	 * @param importedNames
	 * @param acceptor
	 */

	public String[] computeDistinctProposals(String currentName, List<IEObjectDescription> descs) {
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
		return props;
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
	 * @param types
	 *            if stated, the wanted types of named values
	 * @return
	 *         array of proposals, possibly empty, but never null.
	 */
	public String[] computeProposals(final String currentName, Collection<IEObjectDescription> descs, EClass... types) {
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

		// compares the pronounciation difference between given and candidates
		Comparator<String> x = new Comparator<String>() {

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

		};
		Arrays.sort(proposals, x);
		// System.err.print("Order = ");
		// for(int i = 0; i < proposals.length; i++)
		// System.err.printf("%s, ", proposals[i]);
		// System.err.println();
		return proposals;
	}

}
