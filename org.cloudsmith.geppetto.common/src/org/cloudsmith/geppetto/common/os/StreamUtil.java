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

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class StreamUtil {

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
}
