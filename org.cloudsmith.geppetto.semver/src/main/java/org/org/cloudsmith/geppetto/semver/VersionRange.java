/**
 * Copyright (c) 2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.org.cloudsmith.geppetto.semver;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 */
public class VersionRange implements Serializable {

	private static enum CompareType {
		LESS, LESS_EQUAL, GREATER, GREATER_EQUAL, EQUAL
	}

	private static final long serialVersionUID = 5616258897558001150L;

	private static final Pattern SEM_X_PATTERN = Pattern.compile("^(\\d+)(?:\\.(\\d+))?\\.x$");

	/**
	 * Returns a range based on the given string. The string is parsed according to
	 * the following rules
	 * <ul>
	 * <li>1.2.3 — A specific version.</li>
	 * <li>&gt;1.2.3 — Greater than a specific version.</li>
	 * <li>&lt;1.2.3 — Less than a specific version.</li>
	 * <li>&gt;=1.2.3 — Greater than or equal to a specific version.</li>
	 * <li>&lt;=1.2.3 — Less than or equal to a specific version.</li>
	 * <li>&gt;=1.0.0 &lt;2.0.0 — Range of versions; both conditions must be satisfied. (This example would match 1.0.1 but not 2.0.1)</li>
	 * <li>1.x — A semantic major version. (This example would match 1.0.1 but not 2.0.1, and is shorthand for &gt;=1.0.0 &lt;2.0.0.)</li>
	 * <li>1.2.x — A semantic major & minor version. (This example would match 1.2.3 but not 1.3.0, and is shorthand for &gt;=1.2.0 &lt;1.3.0.)</li>
	 * </ul>
	 * 
	 * @param versionRequirement
	 *            The string form of the version requirement
	 * @return The created range
	 */
	public static VersionRange create(String versionRequirement) {
		if(versionRequirement == null || versionRequirement.length() == 0)
			return null;

		int[] posHandle = new int[] { 0 };
		CompareType compareType = nextCompareType(versionRequirement, posHandle);
		if(compareType == null)
			// Empty string or just whitespace.
			return null;

		boolean minInclude, maxInclude;
		Version min, max;

		String version = nextVersion(versionRequirement, posHandle);
		switch(compareType) {
			case EQUAL: {
				// This may be a semantic version in the form 1.x or 1.2.x
				minInclude = true;
				Matcher m = SEM_X_PATTERN.matcher(version);
				if(m.matches()) {
					maxInclude = false;
					int major = Integer.parseInt(m.group(1));
					String minorStr = m.group(2);
					if(minorStr == null) {
						min = Version.create(major, 0, 0);
						max = Version.create(major + 1, 0, 0, Version.MIN_PRE_RELEASE);
					}
					else {
						int minor = Integer.parseInt(minorStr);
						min = Version.create(major, minor, 0);
						max = Version.create(major, minor + 1, 0, Version.MIN_PRE_RELEASE);
					}
				}
				else {
					maxInclude = true;
					min = max = Version.create(version);
				}
				break;
			}
			case LESS:
			case LESS_EQUAL:
				minInclude = true;
				maxInclude = compareType == CompareType.LESS_EQUAL;
				min = Version.MIN;
				max = Version.create(version);
				break;

			default: // GREATER or GREATER_EQUAL
				minInclude = compareType == CompareType.GREATER_EQUAL;
				maxInclude = true;
				min = Version.create(version);
				max = Version.MAX;
				break;
		}

		CompareType compareType2 = nextCompareType(versionRequirement, posHandle);
		if(compareType2 != null) {
			if(compareType == CompareType.EQUAL)
				throw new IllegalArgumentException("Can't create a range where one condition is of type 'equal'");

			version = nextVersion(versionRequirement, posHandle);
			switch(compareType2) {
				case EQUAL:
					throw new IllegalArgumentException("Can't create a range where one condition is of type 'equal'");
				case LESS:
				case LESS_EQUAL:
					if(compareType == CompareType.LESS || compareType == CompareType.LESS_EQUAL)
						throw new IllegalArgumentException("Can't combine two 'less' conditions into a range");
					max = Version.create(version);
					maxInclude = compareType2 == CompareType.LESS_EQUAL;
					break;

				default: // GREATER or GREATER_EQUAL
					if(compareType == CompareType.GREATER || compareType == CompareType.GREATER_EQUAL)
						throw new IllegalArgumentException("Can't combine two 'greater' conditions into a range");
					min = Version.create(version);
					minInclude = compareType2 == CompareType.GREATER_EQUAL;
					break;

			}
		}
		int cmp = min.compareTo(max);
		if(!(cmp < 0 || (cmp == 0 && minInclude && maxInclude)))
			throw new IllegalArgumentException("lower bound must be less or equal to upper bound");

		return new VersionRange(versionRequirement, min, minInclude, max, maxInclude);
	}

	/**
	 * Returns a range that will be an exact match for the given version.
	 * 
	 * @param version
	 *            The version that the range must match
	 * @return The created range
	 */
	public static VersionRange exact(Version version) {
		return new VersionRange(null, version, true, version, true);
	}

	/**
	 * Returns a range that will match versions greater than the given version.
	 * 
	 * @param version
	 *            The version that serves as the non inclusive lower bound
	 * @return The created range
	 */
	public static VersionRange greater(Version version) {
		return new VersionRange(null, version, false, Version.MAX, true);
	}

	/**
	 * Returns a range that will match versions greater than or equal the given version.
	 * 
	 * @param version
	 *            The version that serves as the inclusive lower bound
	 * @return The created range
	 */
	public static VersionRange greaterOrEqual(Version version) {
		return new VersionRange(null, version, true, Version.MAX, true);
	}

	/**
	 * Returns a range that will match versions less than the given version.
	 * 
	 * @param version
	 *            The version that serves as the non inclusive upper bound
	 * @return The created range
	 */
	public static VersionRange less(Version version) {
		return new VersionRange(null, Version.MIN, true, version, false);
	}

	/**
	 * Returns a range that will match versions less than or equal to the given version.
	 * 
	 * @param version
	 *            The version that serves as the non inclusive upper bound
	 * @return The created range
	 */
	public static VersionRange lessOrEqual(Version version) {
		return new VersionRange(null, Version.MIN, true, version, true);
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
		else if(c >= '0' && c <= '9')
			compareType = CompareType.EQUAL;
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
