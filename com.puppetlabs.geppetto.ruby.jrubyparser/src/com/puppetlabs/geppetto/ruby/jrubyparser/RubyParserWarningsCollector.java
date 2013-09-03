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
package com.puppetlabs.geppetto.ruby.jrubyparser;

import java.util.Collections;
import java.util.List;

import com.puppetlabs.geppetto.ruby.spi.IRubyIssue;
import org.jrubyparser.IRubyWarnings;
import org.jrubyparser.SourcePosition;
import org.jrubyparser.lexer.SyntaxException;

import com.google.common.collect.Lists;

/**
 * Collects warnings and errors from JRubyParser callbacks
 */
public class RubyParserWarningsCollector implements IRubyWarnings {

	public static class RubyIssue implements IRubyIssue {
		private ID id;

		private int line;
		private int startLine;
		private String fileName;
		private String message;

		private Object[] data;

		private int startOffset;

		private int length;

		private static final Object[] EMPTY_DATA = {};

		protected RubyIssue(ID id, int line, int startLine, String fileName,
				String message, Object... data) {
			if (id == null)
				throw new IllegalArgumentException("ID may not be null");
			this.id = id;
			this.line = line;
			this.startLine = startLine;
			this.fileName = fileName;
			this.message = message;
			this.data = data;
			this.startOffset = -1;
			this.length = -1;
		}

		protected RubyIssue(ID id, SourcePosition position, String message,
				Object... data) {
			this.id = id;
			this.line = position.getEndLine();
			this.startLine = position.getStartLine();
			this.startOffset = position.getStartOffset();
			this.length = position.getEndOffset() - position.getStartOffset();
			this.fileName = position.getFile();
			this.message = message;
			this.data = data;
		}

		protected RubyIssue(SyntaxException error) {
			this(null, error.getPosition(), error.getMessage(), EMPTY_DATA);
		}

		public Object[] getData() {
			return data;
		}

		/**
		 * Returns null if issue did not report a filename
		 * 
		 * @return
		 */
		public String getFileName() {
			return fileName;
		}

		/**
		 * Implementation specific.
		 * 
		 * @return null if this issue represents a syntax error.
		 */
		public ID getId() {
			return id;
		}

		/**
		 * Returns "jruby.syntax.error" if this issue represents a syntax error,
		 * else the ID as a string. The ID is ruby parser implementation
		 * specific.
		 * 
		 * @return
		 */
		public String getIdString() {
			return id == null ? "jruby.syntax.error" : id.toString();
		}

		public int getLength() {
			return length;
		}

		public int getLine() {
			return line;
		}

		public String getMessage() {
			return message;
		}

		/**
		 * Returns -1 if no start line has been set.
		 * 
		 * @return
		 */
		public int getStartLine() {
			return startLine;
		}

		public int getStartOffset() {
			return startOffset;
		}

		/**
		 * Indicates if this is a syntax error. This is the same as getId() ==
		 * null.
		 * 
		 * @return
		 */
		public boolean isSyntaxError() {
			return id == null;
		}

	}

	private List<IRubyIssue> issues;

	public RubyParserWarningsCollector() {
		issues = Lists.newArrayList();
	}

	public List<IRubyIssue> getIssues() {
		return Collections.unmodifiableList(issues);
	}

	/**
	 * Unknown what this does - there is no javadoc in interface. Returns
	 * 'false'.
	 */
	@Override
	public boolean isVerbose() {
		return false;
	}

	// @Override
	// public Ruby getRuntime() {
	// return runtime;
	// }

	public void syntaxError(SyntaxException error) {
		issues.add(new RubyIssue(error));
	}

	@Override
	public void warn(ID id, SourcePosition position, String message,
			Object... data) {
		issues.add(new RubyIssue(id, position, message, data));
	}

	@Override
	public void warn(ID id, String fileName, int lineNumber, String message,
			Object... data) {
		issues.add(new RubyIssue(id, lineNumber, -1, fileName, message, data));
	}

	@Override
	public void warn(ID id, String message, Object... data) {
		issues.add(new RubyIssue(id, -1, -1, null, message, data));
	}

	@Override
	public void warning(ID id, SourcePosition position, String message,
			Object... data) {
		issues.add(new RubyIssue(id, position, message, data));
	}

	@Override
	public void warning(ID id, String fileName, int lineNumber, String message,
			Object... data) {
		issues.add(new RubyIssue(id, lineNumber, -1, fileName, message, data));
	}

	@Override
	public void warning(ID id, String message, Object... data) {
		issues.add(new RubyIssue(id, -1, -1, null, message, data));
	}
}
