package org.cloudsmith.geppetto.validation.runner;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cloudsmith.geppetto.common.os.OsUtil;
import org.cloudsmith.geppetto.common.util.EclipseUtils;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.Diagnostic;

public class PuppetCatalogCompilerRunner {
	/**
	 * Returns message as message, filename and line are available as Data[0]
	 * and Data[1] (or via the more specific CatalogDiagnostic methods).
	 * 
	 */
	public static class CatalogDiagnostic implements Diagnostic {
		private String message;

		private String fileName;

		private int line;

		private String nodeName;

		public static final int CODE_UNSPECIFIC = 0;

		public static final int CODE_PARSE_ERROR = 1;

		public CatalogDiagnostic(String message, String fileName, String line, String nodeName) {
			this.message = message;
			this.fileName = fileName;
			this.nodeName = nodeName;
			int intLine = -1;
			try {
				intLine = Integer.parseInt(line);
			}
			catch(NumberFormatException e) {
				// do nothing - leave it at -1
			}
			init(message, fileName, intLine, nodeName);
		}

		/**
		 * Always returns null.
		 * 
		 * @return
		 */
		public List<Diagnostic> getChildren() {
			return null;
		}

		/**
		 * Returns CODE_PARSE_ERROR if the message starts with "Could not parse"
		 * otherwise CODE_UNKNOWN
		 * 
		 * @return
		 */
		public int getCode() {
			if(getMessage().startsWith("Could not parse"))
				return CODE_PARSE_ERROR;
			return CODE_UNSPECIFIC;
		}

		/**
		 * Returns a List with String source, and Integer line
		 * 
		 * @return
		 */
		public List<?> getData() {
			List<Object> data = new ArrayList<Object>();
			data.add(fileName);
			data.add(new Integer(line));
			return data;
		}

		/**
		 * Always returns null.
		 * 
		 * @return
		 */
		public Throwable getException() {
			return null;
		}

		public String getFileName() {
			return fileName;
		}

		public int getLine() {
			return line;
		}

		public String getMessage() {
			return message;
		}

		public String getNodeName() {
			return nodeName;
		}

		/**
		 * Calculates the severity based on the beginning of the message: - err:
		 * ERROR - warning: WARNING - info: or notice: INFO
		 */
		public int getSeverity() {
			if(message.startsWith("err:"))
				return Diagnostic.ERROR;
			if(message.startsWith("warning:"))
				return Diagnostic.WARNING;
			if(message.startsWith("info:"))
				return Diagnostic.INFO;
			if(message.startsWith("notice:"))
				return Diagnostic.INFO;
			// default
			return Diagnostic.ERROR;
		}

		public String getSource() {
			return fileName;
		}

		public void init(String message, String fileName, int line, String nodeName) {
			this.message = message;
			this.fileName = fileName;
			this.line = line;
			this.nodeName = nodeName;
		}
	}

	public static final String[] DEFAULT_ARGUMENTS;

	public static final int MESSAGE_GROUP = 1;

	public static final int FILE_GROUP = 2;

	public static final int LINE_GROUP = 3;

	public static final int NODE_GROUP = 4;

	static {
		String compileScript;
		try {
			compileScript = getBundleResourceAsFile(new Path("/puppet/compile_catalog")).getAbsolutePath();
			if(compileScript == null)
				throw new IllegalStateException("Compile script not found");
		}
		catch(Exception e) {
			throw new ExceptionInInitializerError(e);
		}
		DEFAULT_ARGUMENTS = new String[] { "ruby", compileScript };
	}

	/**
	 * Get a resource found in the bundle containing this class as a File.
	 * Extracting it into the filesystem if necessary.
	 * 
	 * @param bundleRelativeResourcePath
	 *        bundle relative path of the resource
	 * @return a {@link File} incarnation of the resource
	 * @throws IOException
	 */
	public static File getBundleResourceAsFile(IPath bundleRelativeResourcePath) throws IOException {
		return EclipseUtils.getFileFromClassBundle(
			PuppetCatalogCompilerRunner.class, bundleRelativeResourcePath.toPortableString());
	}

	final Pattern errorPattern;

	final List<CatalogDiagnostic> diagnostics;

	private String[] arguments;

	public PuppetCatalogCompilerRunner() {
		this(DEFAULT_ARGUMENTS);
	}

	public PuppetCatalogCompilerRunner(String... arguments) {
		this.arguments = arguments;
		errorPattern = Pattern.compile("(.*) at (.*):([0-9]+) on node (.*)$");
		diagnostics = new ArrayList<CatalogDiagnostic>();

	}

	private void clear(StringBuffer buf) {
		buf.delete(0, buf.length());
	}

	public int compileCatalog(File manifest, File moduleDirectory, String nodeName, File factorData,
			IProgressMonitor monitor) {
		SubMonitor ticker = SubMonitor.convert(monitor, 2);
		int offset = arguments.length;
		String[] args = Arrays.copyOf(arguments, offset + 4);
		args[offset++] = manifest.getAbsolutePath();
		args[offset++] = moduleDirectory == null
				? ""
				: moduleDirectory.getAbsolutePath();
		if(nodeName == null)
			throw new IllegalArgumentException("Node name to compile the catalog for must be specified");
		args[offset++] = nodeName;
		args[offset++] = factorData.getAbsolutePath();
		ticker.worked(1);
		int result;
		try {
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ByteArrayOutputStream err = new ByteArrayOutputStream();
			result = OsUtil.runProcess(null, out, err, args);
			readErrorStream(new ByteArrayInputStream(out.toByteArray()));
		}
		catch(IOException e) {
			result = -1;
		}
		ticker.worked(1);
		return result;
	}

	public List<CatalogDiagnostic> getDiagnostics() {
		return diagnostics;
	}

	private boolean isStartOfNew(String line) {
		return (line.startsWith("err:") || line.startsWith("info:") || line.startsWith("notice:") || line.startsWith("warning:"));

	}

	/**
	 * Output buffer as just a message
	 * 
	 * @param buf
	 */
	private void outputBuffer(StringBuffer buf) {
		diagnostics.add(new CatalogDiagnostic(buf.toString(), "", "", ""));
		clear(buf);
	}

	protected void readErrorStream(InputStream inputStream) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		try {
			String line = null;
			StringBuffer buf = new StringBuffer();
			while((line = reader.readLine()) != null) {
				// uncomment for debug echo
				// System.err.println(line);

				// if buffer has data, and the line is the start of a new
				// message - output the old
				if(buf.length() > 0 && isStartOfNew(line))
					outputBuffer(buf);

				// else, this may be a continuation of the previous line
				if(buf.length() > 0)
					buf.append("\\n"); // if joining lines insert a visible new
										// line

				buf.append(line);

				// if we can match as a complete (possibly joined) message - a
				// diagnostic is produced
				Matcher matcher = errorPattern.matcher(buf);
				if(matcher.matches()) {
					diagnostics.add(new CatalogDiagnostic(
						matcher.group(MESSAGE_GROUP), matcher.group(FILE_GROUP), matcher.group(LINE_GROUP),
						matcher.group(NODE_GROUP)));
					clear(buf);
				}
				// else,
				// error is probably reported using several lines, keep line(s)
				// in buffer and continue
			}
			// if things remain in the buffer, output them
			if(buf.length() > 0)
				outputBuffer(buf);

		}
		catch(IOException e) {
			// ignore
		}
		finally {
			try {
				reader.close();
			}
			catch(IOException e) {
				// ignore
			}
		}

	}
}
