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
package org.cloudsmith.geppetto.ruby.jruby;

import java.util.Collections;
import java.util.List;

import org.cloudsmith.geppetto.ruby.spi.IRubyIssue;
import org.jruby.Ruby;
import org.jruby.common.IRubyWarnings;
import org.jruby.lexer.yacc.ISourcePosition;
import org.jruby.lexer.yacc.SyntaxException;

import com.google.common.collect.Lists;

/** 
 * Collects warnings and errors from JRubyParser callbacks
 */
public class RubyParserWarningsCollector implements IRubyWarnings {

	private Ruby runtime;
	private List<IRubyIssue> issues;
	
	public List<IRubyIssue> getIssues() {
		return Collections.unmodifiableList(issues);
	}
	public static class RubyIssue implements IRubyIssue {
		/**
		 * Indicates if this is a syntax error. This is the same as getId() == null.
		 * @return
		 */
		public boolean isSyntaxError() {
			return id == null;
		}
		
		/**
		 * Implementation specific.
		 * @return null if this issue represents a syntax error.
		 */
		public ID getId() {
			return id;
		}
		/**
		 * Returns "jruby.syntax.error" if this issue represents a syntax error, else the ID as a string.
		 * The ID is ruby parser implementation specific.
		 * @return
		 */
		public String getIdString() {
			return id == null ? "jruby.syntax.error" : id.toString();
		}
		public int getLine() {
			return line;
		}
		/**
		 * Returns -1 if no start line has been set.
		 * @return
		 */
		public int getStartLine() {
			return startLine;
		}
		
		/**
		 * Returns null if issue did not report a filename
		 * @return
		 */
		public String getFileName() {
			return fileName;
		}
		
		public String getMessage() {
			return message;
		}
		
		public Object[] getData() {
			return data;
		}
		
		private ID id;
		private int line;
		private int startLine;
		private String fileName;
		private String message;
		private Object[] data;
		protected RubyIssue(ID id, int line, int startLine, String fileName, String message, Object...data) {
			if(id == null)
				throw new IllegalArgumentException("ID may not be null");
			this.id = id;
			this.line = line;
			this.startLine = startLine;
			this.fileName = fileName;
			this.message = message;
			this.data = data;
		}
		
		protected RubyIssue(ID id, ISourcePosition position, String message, Object... data) {
			this(id, position.getLine(), position.getStartLine(), position.getFile(), message, data);
		}
		
		private static final Object[] EMPTY_DATA = {} ;
		protected RubyIssue(SyntaxException error) {
			ISourcePosition position = error.getPosition();
			this.id = null; // is a syntax error
			this.line = position.getLine();
			this.startLine = position.getStartLine();
			this.fileName = position.getFile();
			this.message = error.getMessage();
			this.data = EMPTY_DATA;
		}
		
	}
	public RubyParserWarningsCollector(Ruby runtime) {
		this.runtime = runtime;
		issues = Lists.newArrayList();
	}

	/**
	 * Unknown what this does - there is no javadoc in interface.
	 * Returns 'false'.
	 */
	@Override
	public boolean isVerbose() {
		return false;
	}

	@Override
	public Ruby getRuntime() {
		return runtime;
	}

	@Override
	public void warn(ID id, ISourcePosition position, String message, Object... data) {
		issues.add(new RubyIssue(id, position, message, data));
	}

	@Override
	public void warn(ID id, String fileName, int lineNumber, String message, Object... data) {
		issues.add(new RubyIssue(id, lineNumber, -1, fileName, message, data));
	}

	@Override
	public void warn(ID id, String message, Object... data) {
		issues.add(new RubyIssue(id, -1, -1, null, message, data));
	}

	@Override
	public void warning(ID id, String message, Object... data) {
		issues.add(new RubyIssue(id, -1, -1, null, message, data));
	}

	@Override
	public void warning(ID id, ISourcePosition position, String message,
			Object... data) {
		issues.add(new RubyIssue(id, position, message, data));
	}

	@Override
	public void warning(ID id, String fileName, int lineNumber, String message, Object... data) {
		issues.add(new RubyIssue(id, lineNumber, -1, fileName, message, data));
	}
	
	public void syntaxError(SyntaxException error) {
		issues.add(new RubyIssue(error));
	}
}
