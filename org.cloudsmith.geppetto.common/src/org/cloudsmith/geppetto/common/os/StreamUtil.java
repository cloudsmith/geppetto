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
package org.cloudsmith.geppetto.common.os;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class StreamUtil {
	protected static class ForkedOutputStream extends FilterOutputStream {
		private final OutputStream out2;

		ForkedOutputStream(OutputStream out1, OutputStream out2) {
			super(out1);
			this.out2 = out2;
		}

		@Override
		public void flush() throws IOException {
			super.flush();
			out2.flush();
		}

		@Override
		public void write(byte b[], int off, int len) throws IOException {
			super.write(b, off, len);
			out2.write(b, off, len);
		}

		@Override
		public void write(byte[] b) throws IOException {
			super.write(b);
			out2.write(b);
		}

		@Override
		public void write(int b) throws IOException {
			super.write(b);
			out2.write(b);
		}
	}

	public static class OpenBAStream extends ByteArrayOutputStream {
		public InputStream getInputStream() {
			return new ByteArrayInputStream(buf, 0, count);
		}

		public LineNumberReader getReader() {
			return new LineNumberReader(new InputStreamReader(getInputStream(), ASCII));
		}
	}

	public static final Charset ASCII = Charset.forName("ASCII");

	public static Thread backgroundCopy(final InputStream source, final OutputStream target) {
		Thread copier = new Thread() {
			@Override
			public void run() {
				try {
					copy(source, target);
				}
				catch(IOException e) {
					System.err.println("Unable to copy stream: " + e.getMessage());
				}
			}
		};
		copier.start();
		return copier;
	}

	public static void close(Closeable closeable) {
		if(closeable != null) {
			try {
				closeable.close();
			}
			catch(IOException e) {
				// Ignore
			}
		}
	}

	public static long copy(InputStream source, OutputStream target) throws IOException {
		byte[] buffer = new byte[0x4000];
		int read;
		long total = 0;

		while((read = source.read(buffer)) != -1) {
			target.write(buffer, 0, read);
			total += read;
		}
		return total;
	}

	public static OutputStream forkOutput(OutputStream out1, OutputStream out2) {
		return new ForkedOutputStream(out1, out2);
	}
}
