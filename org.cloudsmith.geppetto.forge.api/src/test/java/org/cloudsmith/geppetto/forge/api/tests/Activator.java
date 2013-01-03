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
package org.cloudsmith.geppetto.forge.api.tests;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.net.URL;

public class Activator {

	public static File getTestData(String path) throws IOException {
		URL url = Activator.class.getResource(path);
		if(url == null)
			throw new RuntimeException("Unable to find \"" + path + "\" resource");
		return toFile(url);
	}

	public static File toFile(URL url) throws IOException {
		try {
			return new File(url.toURI());
		}
		catch(URISyntaxException e) {
			File temp = File.createTempFile("test-", ".tmp");
			temp.deleteOnExit();
			OutputStream output = new FileOutputStream(temp);
			InputStream input = url.openStream();
			try {
				byte[] buffer = new byte[4096];
				int cnt;
				while((cnt = input.read(buffer)) > 0)
					output.write(buffer, 0, cnt);
			}
			finally {
				input.close();
				output.close();
			}
			return temp;
		}
	}
}
