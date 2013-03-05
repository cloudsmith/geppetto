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
package org.cloudsmith.geppetto.forge.v2.model;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.Expose;

/**
 * A version requirement described as match rule and a version.
 */
public class VersionRequirement {
	/**
	 * A json adapter capable of serializing/deserializing a version requirement as a string.
	 */
	public static class JsonAdapter implements JsonSerializer<VersionRequirement>, JsonDeserializer<VersionRequirement> {
		@Override
		public VersionRequirement deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			return parseVersionRequirement(json.getAsString());
		}

		@Override
		public JsonElement serialize(VersionRequirement src, Type typeOfSrc, JsonSerializationContext context) {
			return new JsonPrimitive(src.toString());
		}
	}

	/**
	 * Compares two Version strings for magnitude
	 */
	public static Comparator<String> VERSION_COMPARATOR = new Comparator<String>() {
		@Override
		public int compare(String av, String bv) {
			List<Object> asegs = parse(av);
			List<Object> bsegs = parse(bv);
			int adx = 0;
			int aTop = asegs.size();
			int bdx = 0;
			int bTop = bsegs.size();
			while(adx < aTop || bdx < bTop) {
				Object a = null;
				Object b = null;
				if(adx < aTop)
					a = asegs.get(adx);
				if(bdx < bTop)
					b = bsegs.get(bdx);

				if(a instanceof Integer) {
					Integer aseg = (Integer) a;
					Integer bseg;

					if(adx < aTop)
						++adx;

					if(b instanceof Integer) {
						bseg = (Integer) b;
						if(bdx < bTop)
							++bdx;
					}
					else
						bseg = ZERO;

					int cmp = aseg.intValue() - bseg.intValue();
					if(cmp == 0)
						continue;
					return cmp;
				}

				if(b instanceof Integer) {
					Integer bseg = (Integer) b;
					Integer aseg;

					if(bdx < bTop)
						++bdx;

					if(a instanceof Integer) {
						aseg = (Integer) a;
						if(adx < aTop)
							++adx;
					}
					else
						aseg = ZERO;

					int cmp = aseg.intValue() - bseg.intValue();
					if(cmp == 0)
						continue;
					return cmp;
				}

				// We are comparing two strings
				if(b == null)
					// a is less since lack of string is greater than a string
					return -1;

				if(a == null)
					return 1;

				int cmp = ((String) a).compareTo((String) b);
				if(cmp != 0)
					return cmp;

				if(adx < aTop)
					++adx;
				if(bdx < bTop)
					++bdx;
			}
			return 0;
		}
	};

	private static final Integer ZERO = Integer.valueOf(0);

	private static void addSegments(List<Object> receiver, String s) {
		int len = s.length();
		int idx;
		for(idx = 0; idx < len; ++idx)
			if(!Character.isDigit(s.charAt(idx)))
				break;

		if(idx > 0) {
			// Segment started with digits. The number deserve
			// a segment of its own.
			if(idx == len) {
				receiver.add(Integer.valueOf(s));
				return;
			}
			receiver.add(Integer.valueOf(s.substring(0, idx)));
			s = s.substring(idx);
		}

		// We do not iterate again. The rule we use is that
		// a string, once started with a non-digit character
		// must be terminated with a delimiter. So:
		//
		// 1b becomes 1.b
		// 1b1b2 becomes 1.b1b2
		//
		receiver.add(s);
	}

	private static Object decrease(Object object) {
		if(object instanceof Integer) {
			int value = ((Integer) object).intValue();
			if(value > 0)
				return Integer.valueOf(value - 1);
		}
		else {
			char[] chars = ((String) object).toCharArray();
			if(chars.length > 0) {
				int lastChar = chars.length;
				while(--lastChar >= 0) {
					char c = chars[lastChar];
					if(c > '!') {
						chars[lastChar] = (char) ((c & 0xffff) - 1);
						return new String(chars);
					}
				}
			}
		}
		return null;
	}

	private static int nextSeparator(String str, int beginIdx) {
		int top = str.length();
		while(beginIdx < top) {
			char c = str.charAt(beginIdx);
			if(c == '.' || c == '-')
				return beginIdx;
			++beginIdx;
		}
		return -1;
	}

	private static List<Object> parse(String version) {
		if(version == null || version.length() == 0)
			return Collections.emptyList();

		ArrayList<Object> segments = new ArrayList<Object>();
		int dotIdx = nextSeparator(version, 0);
		int beginIdx = 0;
		while(dotIdx >= 0) {
			addSegments(segments, version.substring(beginIdx, dotIdx));
			beginIdx = dotIdx + 1;
			dotIdx = nextSeparator(version, beginIdx);
		}
		if(beginIdx < version.length())
			addSegments(segments, version.substring(beginIdx));
		return segments;
	}

	public static VersionRequirement parseVersionRequirement(String versionRequirement) {
		if(versionRequirement == null)
			return null;

		int len = versionRequirement.length();
		if(len == 0)
			return null;

		int idx = 0;
		MatchRule rule = null;
		char c = versionRequirement.charAt(0);
		if(c == '>') {
			++idx;
			if(len > 1 && versionRequirement.charAt(1) == '=') {
				rule = MatchRule.GREATER_OR_EQUAL;
				++idx;
			}
			else
				rule = MatchRule.GREATER;
		}
		else if(c == '<') {
			++idx;
			if(len > 1 && versionRequirement.charAt(1) == '=') {
				rule = MatchRule.LESS_OR_EQUAL;
				++idx;
			}
			else
				rule = MatchRule.LESS;
		}
		else if(c == '=') {
			++idx;
			if(len > 1 && versionRequirement.charAt(1) == '=') {
				rule = MatchRule.PERFECT;
				++idx;
			}
			else
				rule = MatchRule.EQUIVALENT;
		}
		else if(c == '~') {
			++idx;
			rule = MatchRule.COMPATIBLE;
		}
		else {
			if(Character.isLetterOrDigit(c))
				rule = MatchRule.PERFECT;
		}

		if(rule == null)
			throw new IllegalArgumentException("Illegal version operator: " + versionRequirement);

		if(idx > 0) {
			for(; idx < len; ++idx) {
				// We allow space between operator and version
				if(!Character.isWhitespace(versionRequirement.charAt(idx)))
					break;
			}
		}
		if(idx == len)
			throw new IllegalArgumentException("Empty version: " + versionRequirement);

		VersionRequirement vr = new VersionRequirement();
		vr.setMatchRule(rule);
		vr.setVersion(versionRequirement.substring(idx));
		return vr;
	}

	@Expose
	private MatchRule matchRule;

	@Expose
	private String version;

	private transient List<Object> segments;

	private transient List<Object> maxSegments;

	private transient List<Object> minSegments;

	private int compareSegments(List<Object> asegs, List<Object> bsegs) {
		int adx = 0;
		int aTop = asegs.size();
		int bdx = 0;
		int bTop = bsegs.size();
		while(adx < aTop || bdx < bTop) {
			Object a = null;
			Object b = null;
			if(adx < aTop)
				a = asegs.get(adx);
			if(bdx < bTop)
				b = bsegs.get(bdx);

			if(a instanceof Integer) {
				Integer aseg = (Integer) a;
				Integer bseg;

				if(adx < aTop)
					++adx;

				if(b instanceof Integer) {
					bseg = (Integer) b;
					if(bdx < bTop)
						++bdx;
				}
				else
					bseg = ZERO;

				int cmp = aseg.intValue() - bseg.intValue();
				if(cmp != 0)
					return cmp;
				continue;
			}

			if(b instanceof Integer) {
				Integer bseg = (Integer) b;
				Integer aseg;

				if(bdx < bTop)
					++bdx;

				if(a instanceof Integer) {
					aseg = (Integer) a;
					if(adx < aTop)
						++adx;
				}
				else
					aseg = ZERO;

				int cmp = aseg.intValue() - bseg.intValue();
				if(cmp != 0)
					return cmp;
				continue;
			}

			// We are comparing two strings
			if(b == null)
				// a is less since lack of string is greater than a string
				return -1;

			int cmp = (a == null)
					? 1
					: ((String) a).compareTo((String) b);
			if(cmp != 0)
				return cmp;

			if(adx < aTop)
				++adx;
			if(bdx < bTop)
				++bdx;
		}
		return 0;
	}

	/**
	 * Checks if the the range represented by this version requirement
	 * intersects with the range represented by <code>vr</code>. The
	 * requirements are considered to be conflicting unless they
	 * intersect.
	 * 
	 * @param vr
	 *            The version requirement to compare with
	 * @return <tt>true</tt> unless the requirements intersect.
	 */
	public boolean conflictsWith(VersionRequirement vr) {
		return compareSegments(getMaxSegments(), vr.getMinSegments()) < 0 ||
				compareSegments(getMinSegments(), vr.getMaxSegments()) > 0;
	}

	/**
	 * Checks if the match rule and version are exactly the same.
	 * 
	 * @return <tt>true</tt> if the objects are equal
	 */
	@Override
	public boolean equals(Object other) {
		if(this == other)
			return true;
		if(!(other instanceof VersionRequirement))
			return false;
		VersionRequirement vr = (VersionRequirement) other;
		return getMatchRule() == vr.getMatchRule() && getSegments().equals(vr.getSegments());
	}

	/**
	 * @return the matchRule
	 */
	public MatchRule getMatchRule() {
		return matchRule;
	}

	private synchronized List<Object> getMaxSegments() {
		if(maxSegments != null)
			return maxSegments;

		List<Object> segments = getSegments();
		switch(getMatchRule()) {
			case PERFECT:
			case LESS_OR_EQUAL:
				break;
			case COMPATIBLE: {
				Object seg0 = segments.get(0);
				if(seg0 instanceof Integer) {
					List<Object> compatible = new ArrayList<Object>(2);
					compatible.add(seg0);
					compatible.add(Integer.MAX_VALUE);
					segments = compatible;
				}
				break;
			}
			case EQUIVALENT: {
				if(segments.size() > 1) {
					Object seg0 = segments.get(0);
					Object seg1 = segments.get(1);
					if(seg0 instanceof Integer && seg1 instanceof Integer) {
						List<Object> equivalent = new ArrayList<Object>(3);
						equivalent.add(seg0);
						equivalent.add(seg1);
						equivalent.add(Integer.MAX_VALUE);
						segments = equivalent;
					}
				}
				break;
			}
			case LESS: {
				int segs = segments.size();
				int lastPos = segs - 1;
				Object lastSeg = segments.get(lastPos);
				while((lastSeg = decrease(lastSeg)) == null) {
					if(lastPos == 0)
						break;
					lastSeg = segments.get(--lastPos);
				}
				if(lastSeg == null)
					break;

				List<Object> less = new ArrayList<Object>(segments);
				less.set(lastPos, lastSeg);
				segments = less;
				break;
			}

			case GREATER:
			case GREATER_OR_EQUAL:
				segments = Collections.<Object> singletonList(Integer.MAX_VALUE);
		}
		maxSegments = segments;
		return segments;
	}

	private synchronized List<Object> getMinSegments() {
		if(minSegments != null)
			return minSegments;

		List<Object> segments = getSegments();
		switch(getMatchRule()) {
			case PERFECT:
			case GREATER_OR_EQUAL:
				break;
			case COMPATIBLE: {
				Object seg0 = segments.get(0);
				if(seg0 instanceof Integer) {
					List<Object> compatible = new ArrayList<Object>(2);
					compatible.add(seg0);
					compatible.add(ZERO);
					segments = compatible;
				}
				break;
			}
			case EQUIVALENT: {
				if(segments.size() > 1) {
					Object seg0 = segments.get(0);
					Object seg1 = segments.get(1);
					if(seg0 instanceof Integer && seg1 instanceof Integer) {
						List<Object> equivalent = new ArrayList<Object>(3);
						equivalent.add(seg0);
						equivalent.add(seg1);
						equivalent.add(ZERO);
						segments = equivalent;
					}
				}
				break;
			}
			case GREATER: {
				int segs = segments.size();
				int lastPos = segs - 1;
				Object lastSeg = segments.get(lastPos);
				Object lastObj;
				if(lastSeg instanceof Integer) {
					lastObj = Integer.valueOf(((Integer) lastSeg).intValue() + 1);
				}
				else {
					char[] chars = ((String) lastSeg).toCharArray();
					if(chars.length > 0) {
						int lastChar = chars.length - 1;
						chars[lastChar] = (char) ((chars[lastChar] & 0xffff) + 1);
						lastObj = new String(chars);
					}
					else
						lastObj = "!";
				}
				List<Object> greater = new ArrayList<Object>(segments);
				greater.set(lastPos, lastObj);
				segments = greater;
				break;
			}

			case LESS:
			case LESS_OR_EQUAL:
				segments = Collections.<Object> singletonList("!");
		}
		minSegments = segments;
		return segments;
	}

	private synchronized List<Object> getSegments() {
		if(segments == null)
			segments = parse(getVersion());
		return segments;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	@Override
	public int hashCode() {
		return getMatchRule().hashCode() * 31 + getSegments().hashCode();
	}

	/**
	 * Compares the given requirement with this requirement and returns true
	 * if this requirement is equally or more restrictive in appointing a range
	 * of versions. More restrictive means that the appointed range equal or
	 * smaller and completely within the range appointed by the other version.
	 * 
	 * @param vr
	 *            The requirement to compare with
	 * @return <tt>true</tt> if this requirement is as restrictive as the argument
	 */
	public boolean isAsRestrictiveAs(VersionRequirement vr) {
		return compareSegments(getMinSegments(), vr.getMinSegments()) >= 0 &&
				compareSegments(getMaxSegments(), vr.getMaxSegments()) <= 0;
	}

	/**
	 * Checks if the given version is a match for this version requirement.
	 * 
	 * @param version
	 *            The version to match
	 * @return <tt>true</tt> if the version is a match for this requirement.
	 */
	public boolean matches(String version) {
		MatchRule rule = getMatchRule();
		List<Object> asegs = parse(version);
		List<Object> bsegs = getSegments();
		int adx = 0;
		int aTop = asegs.size();
		int bdx = 0;
		int bTop = bsegs.size();
		for(int idx = 0; adx < aTop || bdx < bTop; ++idx) {
			Object a = null;
			Object b = null;
			if(adx < aTop)
				a = asegs.get(adx);
			if(bdx < bTop)
				b = bsegs.get(bdx);

			if(a instanceof Integer) {
				Integer aseg = (Integer) a;
				Integer bseg;

				if(adx < aTop)
					++adx;

				if(b instanceof Integer) {
					bseg = (Integer) b;
					if(bdx < bTop)
						++bdx;
				}
				else
					bseg = ZERO;

				int cmp = aseg.intValue() - bseg.intValue();
				if(cmp == 0)
					continue;

				if(cmp < 0)
					return rule == MatchRule.LESS || rule == MatchRule.LESS_OR_EQUAL;

				switch(rule) {
					case PERFECT:
						return false;
					case EQUIVALENT:
						return idx >= 2;
					case COMPATIBLE:
						return idx >= 1;
				}
				return rule == MatchRule.GREATER || rule == MatchRule.GREATER_OR_EQUAL;
			}

			if(b instanceof Integer) {
				Integer bseg = (Integer) b;
				Integer aseg;

				if(bdx < bTop)
					++bdx;

				if(a instanceof Integer) {
					aseg = (Integer) a;
					if(adx < aTop)
						++adx;
				}
				else
					aseg = ZERO;

				int cmp = aseg.intValue() - bseg.intValue();
				if(cmp == 0)
					continue;

				if(cmp < 0)
					return rule == MatchRule.LESS || rule == MatchRule.LESS_OR_EQUAL;

				switch(rule) {
					case PERFECT:
						return false;
					case EQUIVALENT:
						return idx >= 2;
					case COMPATIBLE:
						return idx >= 1;
				}
				return rule == MatchRule.GREATER || rule == MatchRule.GREATER_OR_EQUAL;
			}

			// We are comparing two strings
			if(b == null)
				// a is less since lack of string is greater than a string
				return rule == MatchRule.LESS || rule == MatchRule.LESS_OR_EQUAL;

			int cmp = (a == null)
					? 1
					: ((String) a).compareTo((String) b);
			if(cmp < 0)
				return rule == MatchRule.LESS || rule == MatchRule.LESS_OR_EQUAL;

			if(cmp > 0) {
				switch(rule) {
					case PERFECT:
						return false;
					case EQUIVALENT:
						return idx >= 2;
					case COMPATIBLE:
						return idx >= 1;
				}
				return rule == MatchRule.GREATER || rule == MatchRule.GREATER_OR_EQUAL;
			}

			if(adx < aTop)
				++adx;
			if(bdx < bTop)
				++bdx;
			--idx; // Has no bearing on major, minor, service
		}
		return rule != MatchRule.LESS && rule != MatchRule.GREATER;
	}

	/**
	 * @param matchRule
	 *            the matchRule to set
	 */
	public void setMatchRule(MatchRule matchRule) {
		this.matchRule = matchRule;
	}

	/**
	 * @param version
	 *            the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		MatchRule rule = getMatchRule();
		if(rule != null)
			result.append(rule);
		String ver = getVersion();
		if(ver != null)
			result.append(ver);
		return result.toString();
	}
}
