package com.puppetlabs.geppetto.validation.runner;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.puppetlabs.geppetto.common.os.OsUtil;
import com.puppetlabs.geppetto.common.os.StreamUtil;
import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.diagnostic.DiagnosticType;
import com.puppetlabs.geppetto.diagnostic.FileDiagnostic;
import com.puppetlabs.geppetto.validation.ValidationService;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubMonitor;

public class PuppetCatalogCompilerRunner {
	/**
	 * Returns message as message, filename and line are available as Data[0]
	 * and Data[1] (or via the more specific CatalogDiagnostic methods).
	 * 
	 */
	public static class CatalogDiagnostic extends FileDiagnostic {
		private static final long serialVersionUID = 1L;

		public static CatalogDiagnostic create(String message, String fileName, String line, String nodeName) {
			int severity = Diagnostic.ERROR;
			if(message.startsWith("err:")) {
				message = message.substring(4);
			}
			else if(message.startsWith("warning:")) {
				severity = Diagnostic.WARNING;
				message = message.substring(8);
			}
			else if(message.startsWith("info:")) {
				severity = Diagnostic.INFO;
				message = message.substring(5);
			}
			else if(message.startsWith("notice:")) {
				severity = Diagnostic.INFO;
				message = message.substring(7);
			}
			DiagnosticType type = message.startsWith("Could not parse")
					? ValidationService.CATALOG_PARSER
					: ValidationService.CATALOG;
			CatalogDiagnostic diag = new CatalogDiagnostic(severity, type, message, new File(fileName));
			try {
				diag.setLineNumber(Integer.parseInt(line));
			}
			catch(NumberFormatException e) {
				diag.setLineNumber(-1);
			}
			diag.setNode(nodeName);
			return diag;
		}

		public CatalogDiagnostic(int severity, DiagnosticType type, String message, File file) {
			super(severity, type, message, file);
		}
	}

	public static final String[] DEFAULT_ARGUMENTS;

	public static final int MESSAGE_GROUP = 1;

	public static final int FILE_GROUP = 2;

	public static final int LINE_GROUP = 3;

	public static final int NODE_GROUP = 4;

	static {
		try {
			InputStream compileCatalog = PuppetCatalogCompilerRunner.class.getResourceAsStream("/puppet/compile_catalog");
			if(compileCatalog == null)
				throw new IllegalStateException("Compile script not found");
			try {
				File tmpScript = File.createTempFile("compile_catalog-", ".rb");
				tmpScript.deleteOnExit();
				OutputStream out = new FileOutputStream(tmpScript);
				try {
					StreamUtil.copy(compileCatalog, out);
				}
				finally {
					StreamUtil.close(out);
				}
				DEFAULT_ARGUMENTS = new String[] { "ruby", tmpScript.getAbsolutePath() };
			}
			finally {
				StreamUtil.close(compileCatalog);
			}
		}
		catch(Exception e) {
			throw new ExceptionInInitializerError(e);
		}
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
			readErrorStream(new ByteArrayInputStream(err.toByteArray()));
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
		diagnostics.add(CatalogDiagnostic.create(buf.toString(), "", "", ""));
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
					diagnostics.add(CatalogDiagnostic.create(
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
