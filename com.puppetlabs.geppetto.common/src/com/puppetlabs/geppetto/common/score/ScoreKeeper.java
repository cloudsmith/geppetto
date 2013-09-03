/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 */
package com.puppetlabs.geppetto.common.score;

import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * ScoreKeeper is used to track/collect data with scores where the wanted result is the n best.
 * The 'best' is either the lowest or the highest score. It is possible to cap results over/under a given
 * threshold. If equal data is eligible with different score only the best score is kept thus giving room to
 * a different data element with worse score. (This avoids monopolizing the top scores with a single item
 * when using multiple scoring methods).
 * 
 */
public class ScoreKeeper<T> {

	public static class ScoreEntry<V> {
		private int score;

		private V data;

		ScoreEntry(int score, V data) {
			this.score = score;
			this.data = data;
		}

		public V getData() {
			return data;
		}

		public int getScore() {
			return score;
		}
	}

	private class ScoreEntryComparator implements Comparator<ScoreEntry<T>> {

		@Override
		public int compare(ScoreEntry<T> a, ScoreEntry<T> b) {
			// for same score, artificially order on hashcode
			if(a.score == b.score) {
				if(a.getData().hashCode() == b.getData().hashCode())
					return 0;
				return a.getData().hashCode() < b.getData().hashCode()
						? -1
						: 1;
			}
			return a.score < b.score
					? -1
					: 1;
		}
	}

	final private int bestScoreCount;

	final SortedSet<ScoreEntry<T>> scores;

	final private boolean higherIsBetter;

	final int cap;

	/**
	 * Creates a score keeper that keeps at most highScoreCount scores. The highest scores
	 * are either the highest integer value scores if higherIsBetter is true, or the lowest integer value
	 * scores if higherIsBetter is false.
	 * 
	 * @param bestScoreCount
	 * @param higherIsBetter
	 * @param cap
	 *            only values over (higherIsBetter is true), or under (higherIsBetter is false) are kept.
	 */
	public ScoreKeeper(int bestScoreCount, boolean higherIsBetter, int cap) {
		this.bestScoreCount = bestScoreCount;
		this.higherIsBetter = higherIsBetter;
		scores = new TreeSet<ScoreEntry<T>>(new ScoreEntryComparator());
		this.cap = cap;
	}

	public boolean addScore(int score, T data) {
		// check cap
		if(higherIsBetter) {
			if(score < cap)
				return false;
		}
		else if(score > cap)
			return false;
		// System.err.printf("Add [%d] %d, %s\n", scores.size(), score, data);

		if(scores.size() < bestScoreCount) {
			ScoreEntry<T> existing = findLowest(data);
			// if new is better than existing, replace it, else ignore the new
			if(existing != null) {
				if(!isBetter(score, existing))
					return false;
				scores.remove(existing);
			}
			scores.add(new ScoreEntry<T>(score, data));
			return true;
		}
		ScoreEntry<T> worst = getWorst();
		if(!isBetter(score, worst))
			return false;

		ScoreEntry<T> existing = findLowest(data);
		// if new is better than existing, replace it, else ignore the new
		if(existing != null) {
			if(!isBetter(score, existing))
				return false;
			scores.remove(existing);
			scores.add(new ScoreEntry<T>(score, data));
			return true;
		}
		// add better than worst
		scores.remove(worst);
		scores.add(new ScoreEntry<T>(score, data));
		return true;

	}

	private ScoreEntry<T> findLowest(T data) {
		for(ScoreEntry<T> e : scores)
			if(e.data.equals(data))
				return e;
		return null;
	}

	public ScoreEntry<T> getBest() {
		if(higherIsBetter)
			return scores.last();
		return scores.first();
	}

	public Set<ScoreEntry<T>> getScoreEntries() {
		return scores;
	}

	public ScoreEntry<T> getWorst() {
		if(higherIsBetter)
			return scores.first();
		return scores.last();
	}

	private boolean isBetter(int score, ScoreEntry<T> entry) {
		if(entry == null)
			return true; // anything is better than nothing
		if(higherIsBetter) {
			if(score <= entry.score)
				return false;
			return true;
		}
		return score < entry.score;
	}
}
