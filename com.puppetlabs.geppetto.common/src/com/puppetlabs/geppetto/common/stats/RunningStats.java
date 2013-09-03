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
package org.cloudsmith.geppetto.common.stats;

import com.google.common.base.Preconditions;

/**
 * Computes running statistics:
 * <ul>
 * <li>min, the minimum observed value</li>
 * <li>max, the maximum observed value</li>
 * <li>mean - the mean/average of the observed values</li>
 * <li>standard deviation - sample and population</li>
 * <li>variance coefficient - a normalized standard deviation in %</li>
 * </ul>
 */
public class RunningStats {
	private int count = 0;

	private double m = 0.0;

	private double s = 0.0;

	private int min = 0;

	private int max = 0;

	public RunningStats() {
	}

	public void addObservation(int x) {
		if(x < min)
			min = x;
		if(x > max)
			max = x;

		count++;
		double prevM = m;
		m += (x - prevM) / count;
		s += (x - prevM) * (x - m);
	}

	public int getCount() {
		return count;
	}

	public int getDistance() {
		return max - min;
	}

	public double getMean() {
		return m;
	}

	/**
	 * Obtains the standard deviation of the sample, where mean is the mean of the samples (not the actual mean of
	 * the population).
	 * 
	 * @return
	 */
	public double getSampleStandardDeviation() {
		Preconditions.checkState(count > 0, "No values added to running stats.");
		return Math.sqrt(s / (count - 1));
	}

	/**
	 * Obtains the standard deviation of the population (where the mean is the mean when every individual in the population
	 * have been sampled).
	 * 
	 * @return
	 */
	public double getStandardDeviation() {
		Preconditions.checkState(count > 0, "No values added to running stats.");
		return Math.sqrt(s / (count));
	}

	/**
	 * Return the variation coefficient (standardDeviation / count)
	 * 
	 * @return variation coefficient in %
	 */
	public double getVariationCoefficient() {
		Preconditions.checkState(count > 0, "No values added to running stats.");
		return getStandardDeviation() / count;
	}
}
