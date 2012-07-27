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
package org.cloudsmith.xtext.textflow;

import java.util.List;

import com.google.common.collect.Lists;

/**
 * A CharSequence utilities class providing efficient "strings" consisting of a fixed space,
 * or specified character, a repeating sequence, concatenation, sub sequence and replacement methods for
 * String methods {@link #endsWith(CharSequence, String)}, {@link #indexOf(CharSequence, String, int)}, and a method
 * that splits a CharSequence into a List.
 * 
 */
public class CharSequences {

	/**
	 * A CharSequence consisting of a concatenation of two sequences
	 * 
	 */
	public static class Concatenation implements CharSequence {
		private CharSequence b;

		private CharSequence a;

		private int aLength;

		public Concatenation(CharSequence a, CharSequence b) {
			this.a = a;
			this.aLength = a.length();
			this.b = b;
		}

		@Override
		public char charAt(int index) {
			if(index < aLength)
				return a.charAt(index);
			return b.charAt(index - aLength);
		}

		@Override
		public int length() {
			return a.length() + b.length();
		}

		@Override
		public CharSequence subSequence(int start, int end) {
			return new SubSequence(this, start, end);
		}

	}

	/**
	 * An CharSequence consisting of a sequence of the same character.
	 * 
	 */
	public static class Fixed implements CharSequence {

		private int count;

		private char c;

		public Fixed(char c, int count) {
			this.c = c;
			this.count = count;
		}

		@Override
		public char charAt(int index) {
			return c;
		}

		@Override
		public int length() {
			return count;
		}

		@Override
		public CharSequence subSequence(int start, int end) {
			return new Fixed(c, end - start);
		}
	}

	/**
	 * An CharSequence consisting of a sequence of the same character.
	 * 
	 */
	public static class RepeatingString implements CharSequence {

		private int count;

		private String s;

		private int length;

		public RepeatingString(String s, int count) {
			this.s = s;
			this.count = count;
			this.length = s.length();
		}

		@Override
		public char charAt(int index) {
			if(index >= count * length)
				throw new IndexOutOfBoundsException();
			return s.charAt(index % length);
		}

		@Override
		public int length() {
			return count * length;
		}

		@Override
		public CharSequence subSequence(int start, int end) {
			int max = length();
			if(start < 0 || end < 0 || start > end || end > max)
				throw new IndexOutOfBoundsException();
			return new SubSequence(this, start, end);
		}
	}

	/**
	 * An CharSequence consisting of only space
	 * 
	 */
	public static class Spaces implements CharSequence {

		private int count;

		public Spaces(int count) {
			this.count = count;
		}

		@Override
		public char charAt(int index) {
			return ' ';
		}

		@Override
		public int length() {
			return count;
		}

		@Override
		public CharSequence subSequence(int start, int end) {
			return new Spaces(end - start);
		}
	}

	public static class SubSequence implements CharSequence {
		private CharSequence original;

		private int start;

		private int end;

		public SubSequence(CharSequence original, int start, int end) {
			this.original = original;
			this.start = start;
			this.end = end;
		}

		@Override
		public char charAt(int index) {
			return original.charAt(start + index);
		}

		@Override
		public int length() {
			return end - start;
		}

		@Override
		public CharSequence subSequence(int start, int end) {
			return new SubSequence(this, start, end);
		}

	}

	/**
	 * Produces an efficient concatenation of two CharSequences in a safe way (they may be null).
	 * No copies are being made (thus for excessive iteration of the result it is better to
	 * make a copy of the result).
	 * If a concatenation is not really needed (one is null or empty) the other sequence is returned.
	 * 
	 * @param a
	 * @param b
	 * @return a CharSequence being a concatenation
	 */
	public static CharSequence concatenate(CharSequence a, CharSequence b) {
		if(a == null)
			a = "";
		if(b == null)
			b = "";
		if(a.length() == 0)
			return b;
		if(b.length() == 0)
			return a;
		return new Concatenation(a, b);
	}

	public static boolean endsWith(CharSequence value, String end) {
		if(value instanceof String)
			return ((String) value).endsWith(end);
		int sz = value.length();
		int szEnd = end.length();
		if(value.length() < end.length())
			return false;
		return value.subSequence(sz - szEnd - 1, sz).equals(end);
	}

	public static int indexOf(CharSequence value, String delimiter, int from) {
		if(value instanceof String)
			return ((String) value).indexOf(delimiter, from);
		else if(value instanceof StringBuilder)
			return ((StringBuilder) value).indexOf(delimiter, from);
		return value.toString().indexOf(delimiter, from);

	}

	public static int lastIndexOf(CharSequence value, String delimiter, int from) {
		if(value instanceof String)
			return ((String) value).lastIndexOf(delimiter, from);
		else if(value instanceof StringBuilder)
			return ((StringBuilder) value).lastIndexOf(delimiter, from);
		return value.toString().lastIndexOf(delimiter, from);
	}

	/**
	 * @param value
	 * @param delimiter
	 * @return
	 */
	public static List<CharSequence> split(CharSequence value, String delimiter) {
		List<CharSequence> result = Lists.newArrayList();
		int lastIndex = 0;
		int index = indexOf(value, delimiter, lastIndex);
		while(index != -1) {
			result.add(value.subSequence(lastIndex, index));
			lastIndex = index + delimiter.length();
			index = indexOf(value, delimiter, lastIndex);
		}
		result.add(value.subSequence(lastIndex, value.length()));
		return result;
	}

}
