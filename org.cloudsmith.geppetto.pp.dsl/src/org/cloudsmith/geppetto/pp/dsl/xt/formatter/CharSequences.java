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
package org.cloudsmith.geppetto.pp.dsl.xt.formatter;

/**
 * An implementation of utility CharSequences providing efficient strings consisting of a fixed space,
 * or specified character, and a repeating sequence.
 * 
 */
public class CharSequences {

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
			return s.charAt(index % length);
		}

		@Override
		public int length() {
			return count * length;
		}

		@Override
		public CharSequence subSequence(int start, int end) {
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
}
