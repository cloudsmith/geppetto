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
package org.cloudsmith.xtext.dommodel;

import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.Triple;
import org.eclipse.xtext.util.Tuples;

import com.google.common.base.Preconditions;

/**
 * <p>
 * An instance of RegionMatch matches a given {@link IDomNode} or (more detailed) a {@link CharSequence} and a <code>startOffset</code> with an
 * {@link ITextRegion} to compute the match/relationship between the given text and the region.
 * </p>
 * <p>
 * The matcher is then used to answer question about the relationship {@link #isOutside()}, or {@link #getIntersectionType()}, and can also apply a split based on
 * the relationship using {@link #apply()}.
 * 
 */
public class RegionMatch {
	public static enum IntersectionType {
		BEFORE, CONTAINED, LASTPART_INSIDE, MIDPART_INSIDE, FIRSTPART_INSIDE, AFTER
	}

	final RegionMatch.IntersectionType type;

	final int regionOffset;

	final int regionLength;

	final CharSequence text;

	final int textOffset;

	final int textLength;

	private static final CharSequence emptySequence = "";

	public RegionMatch(CharSequence s, int startOffset, ITextRegion region /* nullable */) {
		Preconditions.checkArgument(startOffset >= 0);

		text = s == null
				? ""
				: s;
		this.textOffset = startOffset;
		textLength = text.length();

		int regionEnd = 0;
		if(region == null) {
			this.type = IntersectionType.CONTAINED;
			regionOffset = 0;
			regionLength = Integer.MAX_VALUE;
			regionEnd = Integer.MAX_VALUE;
		}
		else {
			regionOffset = region.getOffset();
			regionLength = region.getLength();
			regionEnd = regionOffset + Math.max(0, regionLength - 1);
			int textEnd = textOffset + Math.max(0, textLength - 1);

			if(startOffset > regionEnd) {
				type = IntersectionType.AFTER;
			}
			else if(textEnd < regionOffset) {
				type = IntersectionType.BEFORE;
			}
			else if(textOffset < regionOffset && textEnd > regionEnd) {
				type = IntersectionType.MIDPART_INSIDE;
			}
			else if(startOffset < regionOffset) {
				type = IntersectionType.LASTPART_INSIDE;
			}
			else if(textEnd > regionEnd) {
				type = IntersectionType.FIRSTPART_INSIDE;
			}
			else
				type = IntersectionType.CONTAINED;
		}
	}

	public RegionMatch(IDomNode node, ITextRegion r) {
		this(Preconditions.checkNotNull(node).getText(), node.getOffset(), r);
	}

	/**
	 * Applies the region match to the text captured from the node when the match was made.
	 * The return {@link Triple} returns first = part inside region, second = part before region, third = part after
	 * region. All three elements are always set - non existant parts are represented by empty sequences.
	 * 
	 * @return a Triple with parts(before, inside, after)
	 */
	public Triple<CharSequence, CharSequence, CharSequence> apply() {

		int limit = 0;
		switch(type) {
			case BEFORE:
				return Tuples.create(emptySequence, text, emptySequence);
			case AFTER:
				return Tuples.create(emptySequence, emptySequence, text);
			case CONTAINED:
				return Tuples.create(text, emptySequence, emptySequence);
			case LASTPART_INSIDE:
				limit = regionOffset - textOffset;
				return Tuples.create(text.subSequence(limit, textLength), text.subSequence(0, limit), emptySequence);
			case MIDPART_INSIDE:
				limit = regionOffset - textOffset;
				return Tuples.create(
					text.subSequence(limit, limit + regionLength), text.subSequence(0, limit),
					text.subSequence(limit + regionLength, textLength));
			case FIRSTPART_INSIDE:
				limit = regionOffset + regionLength - textOffset;
				return Tuples.create(text.subSequence(0, limit), emptySequence, text.subSequence(limit, textLength));
			default:
				throw new IllegalStateException("should not happen - not a supported region match case");
		}
	}

	/**
	 * @return the text region intersection type
	 */
	public RegionMatch.IntersectionType getIntersectionType() {
		return type;
	}

	/**
	 * @return true when both start and end are inside the region
	 */
	public boolean isContained() {
		return type == IntersectionType.CONTAINED;
	}

	/**
	 * @return true when start or end is inside the region
	 */
	public boolean isInside() {
		return !isOutside();
	}

	/**
	 * @return true when neither start nor end is inside the region
	 */
	public boolean isOutside() {
		return type == IntersectionType.BEFORE || type == IntersectionType.AFTER;
	}
}
