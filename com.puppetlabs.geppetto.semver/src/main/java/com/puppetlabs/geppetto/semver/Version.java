package com.puppetlabs.geppetto.semver;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A class that implements a <a href="http://semver.org/spec/v1.0.0.html">Semantic Versioning 1.0.0</a>.
 */
public class Version implements Comparable<Version>, Serializable {
	private static final long serialVersionUID = 1L;

	public static final String SNAPSHOT_SUFFIX = "-SNAPSHOT";

	public static final Pattern VERSION_PATTERN = Pattern.compile("^(\\d+)\\.(\\d+)\\.(\\d+)(?:-([0-9a-zA-Z-]*))?$");

	public static final Pattern PRE_RELEASE_PATTERN = Pattern.compile("^[0-9a-zA-Z-]*$");

	public static final Version MAX = new Version(Integer.MAX_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE, null);

	public static final String MIN_PRE_RELEASE = "";

	public static final Version MIN = new Version(0, 0, 0, MIN_PRE_RELEASE);

	public static int comparePreReleases(String p1, String p2) {
		int cmp = 0;
		if(p1 == null) {
			if(p2 != null)
				cmp = 1;
		}
		else if(p2 == null)
			cmp = -1;
		else
			cmp = p1.compareTo(p2);
		return cmp;
	}

	public static Version create(int major, int minor, int patch) {
		return create(major, minor, patch, null);
	}

	public static Version create(int major, int minor, int patch, String preRelease) {
		if(major < 0 || minor < 0 || patch < 0)
			throw new IllegalArgumentException("Negative numbers not accepted in version");

		if(preRelease != null && preRelease.length() >= 0 && !PRE_RELEASE_PATTERN.matcher(preRelease).matches())
			throw new IllegalArgumentException("Illegal characters in pre-release");
		return new Version(major, minor, patch, preRelease);
	}

	public static Version create(String version) {
		if(version == null || version.length() == 0)
			return null;

		Matcher m = VERSION_PATTERN.matcher(version);
		if(!m.matches())
			throw new IllegalArgumentException("The string '" + version +
					"' does not represent a valid semantic version");
		return new Version(
			Integer.parseInt(m.group(1)), Integer.parseInt(m.group(2)), Integer.parseInt(m.group(3)), m.group(4));
	}

	private final int major;

	private final int minor;

	private final int patch;

	private final String preRelease;

	private Version(int major, int minor, int patch, String preRelease) {
		this.major = major;
		this.minor = minor;
		this.patch = patch;
		this.preRelease = preRelease;
	}

	@Override
	public int compareTo(Version o) {
		int cmp = major - o.major;
		if(cmp == 0) {
			cmp = minor - o.minor;
			if(cmp == 0) {
				cmp = patch - o.patch;
				if(cmp == 0)
					cmp = comparePreReleases(preRelease, o.preRelease);
			}
		}
		return cmp;
	}

	@Override
	public boolean equals(Object o) {
		if(o instanceof Version) {
			Version v = (Version) o;
			return major == v.major && minor == v.minor && patch == v.patch &&
					(preRelease == v.preRelease || preRelease != null && preRelease.equals(v.preRelease));
		}
		return false;
	}

	public int getMajor() {
		return major;
	}

	public int getMinor() {
		return minor;
	}

	public int getPatch() {
		return patch;
	}

	public String getPreRelease() {
		return preRelease;
	}

	@Override
	public int hashCode() {
		int hash = major;
		hash = 31 * hash + minor;
		hash = 31 * hash + patch;
		if(preRelease != null)
			hash = 31 * hash + preRelease.hashCode();
		return hash;
	}

	public boolean isSnapshot() {
		return preRelease != null && preRelease.endsWith(SNAPSHOT_SUFFIX);
	}

	public boolean isStable() {
		return major > 0 && !isSnapshot();
	}

	@Override
	public String toString() {
		StringBuilder bld = new StringBuilder();
		toString(bld);
		return bld.toString();
	}

	public void toString(StringBuilder bld) {
		bld.append(major);
		bld.append('.');
		bld.append(minor);
		bld.append('.');
		bld.append(patch);
		if(preRelease != null) {
			bld.append('-');
			bld.append(preRelease);
		}
	}
}
