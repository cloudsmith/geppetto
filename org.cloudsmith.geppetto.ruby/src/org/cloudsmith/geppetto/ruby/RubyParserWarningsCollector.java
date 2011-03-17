package org.cloudsmith.geppetto.ruby;

import java.util.List;

import org.jruby.Ruby;
import org.jruby.common.IRubyWarnings;
import org.jruby.lexer.yacc.ISourcePosition;

import com.google.common.collect.Lists;

/** 
 * Collects warnings and errors from JRubyParser callbacks
 */
public class RubyParserWarningsCollector implements IRubyWarnings {

	private Ruby runtime;
	private List<RubyIssue> issues;
	
	public static class RubyIssue {
		public ID getId() {
			return id;
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

}
