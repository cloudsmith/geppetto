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
package com.puppetlabs.geppetto.semver;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <p>
 * This class represents a range of semamtic versions. The range can be inclusive or non-inclusive at both ends. Open
 * ended ranges can be created by using an inclusive {@link Version#MIN} as the lower bound or an inclusive
 * {@link Version#MAX} as the upper bound.
 * </p>
 * 
 * <p>
 * A version range can also be created from a string. The string is parsed according to the following rules:
 * <ul>
 * <li>1.2.3 — A specific version.</li>
 * <li>&gt;1.2.3 — Greater than a specific version.</li>
 * <li>&lt;1.2.3 — Less than a specific version.</li>
 * <li>&gt;=1.2.3 — Greater than or equal to a specific version.</li>
 * <li>&lt;=1.2.3 — Less than or equal to a specific version.</li>
 * <li>&gt;=1.0.0 &lt;2.0.0 — Range of versions; both conditions must be satisfied. (This example would match 1.0.1 but
 * not 2.0.1)</li>
 * <li>1.x — A semantic major version. (This example would match 1.0.1 but not 2.0.1, and is shorthand for &gt;=1.0.0
 * &lt;2.0.0-)</li>
 * <li>1.2.x — A semantic major & minor version. (This example would match 1.2.3 but not 1.3.0, and is shorthand for
 * &gt;=1.2.0 &lt;1.3.0-)</li>
 * <li>* — Matches any version</li>
 * </ul>
 * A range specifier starting with a tilde ~ character is matched against a version in the following fashion:
 * <ul>
 * <li>The version must be at least as high as the range.</li>
 * <li>The version must be less than the next minor revision above the range.
 * </ul>
 * For example, the following are equivalent:
 * <ul>
 * <li>~1.2.3 = &gt;=1.2.3 &lt;1.3.0-)</li>
 * <li>~1.2 = &gt;=1.2.0 &lt;1.3.0-)</li>
 * <li>~1 = &gt;=1.0.0 &lt;1.1.0-)</li>
 * </ul>
 * </p>
 */
public class VersionRange implements Serializable {

	private static enum CompareType {
		LESS, LESS_EQUAL, GREATER, GREATER_EQUAL, EQUAL, EQUAL_WITHOUT_OP, DASH, TILDE, MATCH_ALL
	}

	private static final long serialVersionUID = 1L;

	private static final Pattern TILDE_PATTERN = Pattern.compile("^(\\d+)(?:(?:\\.(\\d+))(?:\\.(\\d+))?)?$");

	private static final Pattern X_PATTERN = Pattern.compile("^(\\d+)(?:(?:\\.(x|\\d+))(?:\\.x)?)?$");

	public static final VersionRange ALL_INCLUSIVE = new VersionRange(
		">=" + Version.MIN, Version.MIN, true, Version.MAX, true);

	/**
	 * Returns a range based on the given string. See class documentation
	 * for details.
	 * 
	 * @param versionRequirement
	 *            The string form of the version requirement
	 * @return The created range
	 */
	public static VersionRange create(String versionRequirement) {
		if(versionRequirement == null)
			return null;

		int[] posHandle = new int[] { 0 };
		CompareType compareType = nextCompareType(versionRequirement, posHandle);
		if(compareType == null)
			// Empty string or just whitespace.
			return null;

		if(compareType == CompareType.MATCH_ALL) {
			if(hasMore(versionRequirement, posHandle))
				throw vomit("Unexpected characters after '*'", versionRequirement);
			return ALL_INCLUSIVE;
		}

		boolean minInclude = true;
		boolean maxInclude;
		Version min, max;

		boolean moreAllowed = false;
		String version = nextVersion(versionRequirement, posHandle);
		switch(compareType) {
			case DASH:
				throw vomit("Cannot start with a dash", versionRequirement);
			case EQUAL:
				// Version with pre-release
				maxInclude = true;
				min = max = createVersion(version, versionRequirement);
				break;

			case TILDE: {
				Matcher m = TILDE_PATTERN.matcher(version);
				if(!m.matches())
					throw vomit("Not a valid tilde version", versionRequirement);

				maxInclude = false;
				int major = Integer.parseInt(m.group(1));
				String minorStr = m.group(2);
				String patchStr = m.group(3);
				if(minorStr == null) {
					min = Version.create(major, 0, 0);
					max = Version.create(major, 1, 0, Version.MIN_PRE_RELEASE);
				}
				else {
					int minor = Integer.parseInt(minorStr);
					if(patchStr == null) {
						min = Version.create(major, minor, 0);
						max = Version.create(major, minor + 1, 0, Version.MIN_PRE_RELEASE);
					}
					else {
						int patch = Integer.parseInt(patchStr);
						min = Version.create(major, minor, patch);
						max = Version.create(major, minor + 1, 0, Version.MIN_PRE_RELEASE);
					}
				}
				break;
			}

			case EQUAL_WITHOUT_OP: {
				Matcher m = X_PATTERN.matcher(version);
				if(!m.matches()) {
					maxInclude = true;
					min = max = createVersion(version, versionRequirement);
					moreAllowed = true; // xxx - yyy range still possible
					break;
				}

				maxInclude = false;
				int major = Integer.parseInt(m.group(1));
				String minorStr = m.group(2);
				if(minorStr == null || "x".equals(minorStr)) {
					min = Version.create(major, 0, 0);
					max = Version.create(major + 1, 0, 0, Version.MIN_PRE_RELEASE);
				}
				else {
					int minor = Integer.parseInt(minorStr);
					min = Version.create(major, minor, 0);
					max = Version.create(major, minor + 1, 0, Version.MIN_PRE_RELEASE);
				}
				break;
			}

			case LESS:
			case LESS_EQUAL:
				maxInclude = compareType == CompareType.LESS_EQUAL;
				min = Version.MIN;
				max = createVersion(version, versionRequirement);
				moreAllowed = true;
				break;

			default: // GREATER or GREATER_EQUAL
				minInclude = compareType == CompareType.GREATER_EQUAL;
				maxInclude = true;
				min = createVersion(version, versionRequirement);
				max = Version.MAX;
				moreAllowed = true;
				break;
		}

		CompareType compareType2 = nextCompareType(versionRequirement, posHandle);
		if(compareType2 != null) {
			if(!moreAllowed)
				throw new IllegalArgumentException("Unexpected characters after version range");

			if(compareType == CompareType.EQUAL_WITHOUT_OP && compareType2 != CompareType.DASH)
				// The only token we accept here is the DASH for the 1.0.0 - 2.0.0 form
				throw new IllegalArgumentException("Can't create a range where one condition is of type 'equal'");

			version = nextVersion(versionRequirement, posHandle);
			switch(compareType2) {
				case DASH:
					if(compareType != CompareType.EQUAL_WITHOUT_OP)
						throw new IllegalArgumentException(
							"Can't create a dash range unless both sides are without operator");
					max = createVersion(version, versionRequirement);
					maxInclude = true;
					break;

				case LESS:
				case LESS_EQUAL:
					if(compareType == CompareType.LESS || compareType == CompareType.LESS_EQUAL)
						throw new IllegalArgumentException("Can't combine two 'less' conditions into a range");
					max = createVersion(version, versionRequirement);
					maxInclude = compareType2 == CompareType.LESS_EQUAL;
					break;

				case GREATER:
				case GREATER_EQUAL:
					if(compareType == CompareType.GREATER || compareType == CompareType.GREATER_EQUAL)
						throw new IllegalArgumentException("Can't combine two 'greater' conditions into a range");
					min = createVersion(version, versionRequirement);
					minInclude = compareType2 == CompareType.GREATER_EQUAL;
					break;

				default:
					throw new IllegalArgumentException("Illegal second operator in range");
			}
		}
		int cmp = min.compareTo(max);
		if(!(cmp < 0 || (cmp == 0 && minInclude && maxInclude)))
			throw new IllegalArgumentException("lower bound must be less or equal to upper bound");

		return new VersionRange(versionRequirement, min, minInclude, max, maxInclude);
	}

	/**
	 * Creates a new VersionRange according to detailed specification.
	 * 
	 * @param lower
	 * @param lowerBoundInclusive
	 * @param upper
	 * @param upperBoundInclusive
	 * @return
	 */
	public static VersionRange create(Version lower, boolean lowerBoundInclusive, Version upper,
			boolean upperBoundInclusive) {
		return new VersionRange(null, lower, lowerBoundInclusive, upper, upperBoundInclusive);
	}

	private static Version createVersion(String version, String range) {
		try {
			return Version.create(version);
		}
		catch(IllegalArgumentException e) {
			throw vomit(e.getMessage(), range);
		}
	}

	/**
	 * Returns a range that will be an exact match for the given version.
	 * 
	 * @param version
	 *            The version that the range must match
	 * @return The created range
	 */
	public static VersionRange exact(Version version) {
		return version == null
				? null
				: new VersionRange(null, version, true, version, true);
	}

	/**
	 * Returns a range that will match versions greater than the given version.
	 * 
	 * @param version
	 *            The version that serves as the non inclusive lower bound
	 * @return The created range
	 */
	public static VersionRange greater(Version version) {
		return version == null
				? null
				: new VersionRange(null, version, false, Version.MAX, true);
	}

	/**
	 * Returns a range that will match versions greater than or equal the given version.
	 * 
	 * @param version
	 *            The version that serves as the inclusive lower bound
	 * @return The created range
	 */
	public static VersionRange greaterOrEqual(Version version) {
		return version == null
				? null
				: new VersionRange(null, version, true, Version.MAX, true);
	}

	private static boolean hasMore(String s, int[] posHandle) {
		return s.length() >= skipWhite(s, posHandle[0]);
	}

	/**
	 * Returns a range that will match versions less than the given version.
	 * 
	 * @param version
	 *            The version that serves as the non inclusive upper bound
	 * @return The created range
	 */
	public static VersionRange less(Version version) {
		return version == null
				? null
				: new VersionRange(null, Version.MIN, true, version, false);
	}

	/**
	 * Returns a range that will match versions less than or equal to the given version.
	 * 
	 * @param version
	 *            The version that serves as the non inclusive upper bound
	 * @return The created range
	 */
	public static VersionRange lessOrEqual(Version version) {
		return version == null
				? null
				: new VersionRange(null, Version.MIN, true, version, true);
	}

	private static CompareType nextCompareType(String s, int[] posHandle) {
		int pos = skipWhite(s, posHandle[0]);
		int top = s.length();
		if(pos >= top)
			return null;

		CompareType compareType;
		char c = s.charAt(pos);
		if(c == '>') {
			++pos;
			if(pos < top && s.charAt(pos) == '=') {
				++pos;
				compareType = CompareType.GREATER_EQUAL;
			}
			else
				compareType = CompareType.GREATER;
		}
		else if(c == '<') {
			++pos;
			if(pos < top && s.charAt(pos) == '=') {
				++pos;
				compareType = CompareType.LESS_EQUAL;
			}
			else
				compareType = CompareType.LESS;
		}
		else if(c == '=') {
			++pos;
			compareType = CompareType.EQUAL;
		}
		else if(c == '-') {
			++pos;
			compareType = CompareType.DASH;
		}
		else if(c == '~') {
			++pos;
			compareType = CompareType.TILDE;
		}
		else if(c == '*') {
			++pos;
			compareType = CompareType.MATCH_ALL;
		}
		else if(c >= '0' && c <= '9')
			compareType = CompareType.EQUAL_WITHOUT_OP;
		else
			throw new IllegalArgumentException("Expected one of '<', '>' or digit at position " + pos + " in range '" +
					s + '\'');

		posHandle[0] = pos;
		return compareType;
	}

	private static String nextVersion(String s, int[] posHandle) {
		int pos = skipWhite(s, posHandle[0]);
		int top = s.length();
		int idx = pos;
		while(idx < top) {
			char c = s.charAt(idx);
			if(c == '>' || c == '<' || Character.isWhitespace(c))
				break;
			++idx;
		}
		if(idx == pos)
			throw new IllegalArgumentException("Expected version at position " + pos + " in range '" + s + '\'');

		posHandle[0] = idx;
		return s.substring(pos, idx);
	}

	private static int skipWhite(String s, int pos) {
		int top = s.length();
		while(pos < top && Character.isWhitespace(s.charAt(pos)))
			++pos;
		return pos;
	}

	private static IllegalArgumentException vomit(String reason, String range) {
		return new IllegalArgumentException(reason + " in range '" + range + '\'');
	}

	private final Version minVersion;

	private final boolean includeMin;

	private final Version maxVersion;

	private final boolean includeMax;

	private final String originalString;

	private VersionRange(String originalString, Version minVersion, boolean includeMin, Version maxVersion,
			boolean includeMax) {
		this.originalString = originalString;
		this.minVersion = minVersion;
		this.includeMin = includeMin;
		this.maxVersion = maxVersion;
		this.includeMax = includeMax;
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof VersionRange) {
			VersionRange vr = (VersionRange) o;
			return includeMin == vr.includeMin && includeMax == vr.includeMax && minVersion.equals(vr.minVersion) &&
					maxVersion.equals(vr.maxVersion);
		}
		return false;
	}

	/**
	 * Scans the provided collection of candidates and returns the highest version
	 * that is included in this range.
	 * 
	 * @param candidateVersions
	 *            The collection of candidate versions
	 * @return The best match or <tt>null</tt> if no match was found
	 */
	public Version findBestMatch(Iterable<Version> candidateVersions) {
		Version best = null;
		for(Version candidate : candidateVersions)
			if((best == null || candidate.compareTo(best) > 0) && isIncluded(candidate))
				best = candidate;
		return best;
	}

	/**
	 * Returns the upper bound of the match. Whether or not this upper bound is included
	 * in the match is determined by {@link #isMaxIncluded()}.
	 * 
	 * @return The upper bound. Might be equal to {@link Version#MAX} but will never be <code>null</code>.
	 * @see #isMaxIncluded()
	 */
	public Version getMaxVersion() {
		return maxVersion;
	}

	/**
	 * Returns the lower bound of the match. Whether or not this lower bound is included
	 * in the match is determined by {@link #isMinIncluded()}.
	 * 
	 * @return The lower bound. Might be equal to {@link Version#MIN} but will never be <code>null</code>.
	 * @see #isMinIncluded()
	 */
	public Version getMinVersion() {
		return minVersion;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + maxVersion.hashCode();
		result = prime * result + minVersion.hashCode();
		result = prime * result + (includeMax
				? 1231
				: 1237);
		result = prime * result + (includeMin
				? 1231
				: 1237);
		return result;
	}

	public VersionRange intersect(VersionRange r2) {
		int minCompare = minVersion.compareTo(r2.minVersion);
		int maxCompare = maxVersion.compareTo(r2.maxVersion);

		boolean resultMinIncluded;
		Version resultMin;
		if(minCompare == 0) {
			if(maxCompare == 0 && includeMin == r2.includeMin && includeMax == r2.includeMax)
				return this;
			resultMin = minVersion;
			resultMinIncluded = includeMin && r2.includeMin;
		}
		else if(minCompare < 0) {
			resultMin = r2.minVersion;
			resultMinIncluded = r2.includeMin;
		}
		else { // minCompare > 0)
			resultMin = minVersion;
			resultMinIncluded = includeMin;
		}

		boolean resultMaxIncluded;
		Version resultMax;
		if(maxCompare > 0) {
			resultMax = r2.maxVersion;
			resultMaxIncluded = r2.includeMax;
		}
		else if(maxCompare < 0) {
			resultMax = maxVersion;
			resultMaxIncluded = includeMax;
		}
		else {// maxCompare == 0
			resultMax = maxVersion;
			resultMaxIncluded = includeMax && r2.includeMax;
		}

		int minMaxCmp = resultMin.compareTo(resultMax);
		if(minMaxCmp < 0 || (minMaxCmp == 0 && resultMinIncluded && resultMaxIncluded))
			return new VersionRange(null, resultMin, resultMinIncluded, resultMax, resultMaxIncluded);

		return null;
	}

	/**
	 * Compares the given range with this range and returns true
	 * if this requirement is equally or more restrictive in appointing a range
	 * of versions. More restrictive means that the appointed range equal or
	 * smaller and completely within the range appointed by the other version.
	 * 
	 * @param vr
	 *            The requirement to compare with
	 * @return <tt>true</tt> if this requirement is as restrictive as the argument
	 */
	public boolean isAsRestrictiveAs(VersionRange vr) {
		int cmp = vr.minVersion.compareTo(minVersion);
		if(cmp > 0 || (cmp == 0 && includeMin && !vr.includeMin))
			return false;

		cmp = vr.maxVersion.compareTo(maxVersion);
		if(cmp < 0 || (cmp == 0 && includeMax && !vr.includeMax))
			return false;

		return true;
	}

	/**
	 * Checks if <tt>version</tt> is included in the range described by this instance.
	 * 
	 * @param version
	 *            the version to test.
	 * @return <tt>true</tt> if the version is include. <tt>false</tt> if the version
	 *         was <tt>null</tt> or not included in this range.
	 */
	public boolean isIncluded(Version version) {
		if(version == null)
			return false;

		if(minVersion == maxVersion)
			// Can only happen when both includeMin and includeMax are true
			return minVersion.equals(version);

		int minCheck = includeMin
				? 0
				: -1;
		int maxCheck = includeMax
				? 0
				: 1;
		return minVersion.compareTo(version) <= minCheck && maxVersion.compareTo(version) >= maxCheck;
	}

	/**
	 * Returns true if the maximum version is included in the match and
	 * false if a match must be less than the maximum version.
	 * 
	 * @return <code>true</code> to indicate that a version equal to the maximum version will be considered a match
	 */
	public boolean isMaxIncluded() {
		return includeMax;
	}

	/**
	 * Returns true if the minimum version is included in the match and
	 * false if a match must be greater than the minimum version.
	 * 
	 * @return <code>true</code> to indicate that a version equal to the minimum version will be considered a match
	 */
	public boolean isMinIncluded() {
		return includeMin;
	}

	@Override
	public String toString() {
		StringBuilder bld = new StringBuilder();
		toString(bld);
		return bld.toString();
	}

	public void toString(StringBuilder bld) {
		if(originalString != null)
			bld.append(originalString);
		else {
			if(includeMin && includeMax && minVersion.equals(maxVersion))
				// Perfect match
				minVersion.toString(bld);
			else {
				boolean needSpace = false;
				if(!minVersion.equals(Version.MIN)) {
					bld.append('>');
					if(includeMin)
						bld.append('=');
					minVersion.toString(bld);
					needSpace = true;
				}

				if(!maxVersion.equals(Version.MAX)) {
					if(needSpace)
						bld.append(' ');
					bld.append('<');
					if(includeMax)
						bld.append('=');
					maxVersion.toString(bld);
				}
			}
		}
	}
}
