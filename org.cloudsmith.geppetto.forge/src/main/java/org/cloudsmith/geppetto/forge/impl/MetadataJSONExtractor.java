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
package org.cloudsmith.geppetto.forge.impl;

import static org.cloudsmith.geppetto.forge.Forge.METADATA_JSON_NAME;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;

import org.cloudsmith.geppetto.common.os.StreamUtil;
import org.cloudsmith.geppetto.diagnostic.Diagnostic;
import org.cloudsmith.geppetto.forge.util.StrictMetadataJsonParser;
import org.cloudsmith.geppetto.forge.v2.model.Metadata;

public class MetadataJSONExtractor extends AbstractMetadataExtractor {
	public int getCardinal() {
		return 0;
	}

	@Override
	public String getPrimarySource() {
		return METADATA_JSON_NAME;
	}

	@Override
	protected Metadata performMetadataExtraction(File existingFile, Diagnostic result) throws IOException {
		StringWriter swr = new StringWriter((int) existingFile.length());
		FileReader reader = new FileReader(existingFile);
		try {
			char[] buf = new char[4096];
			int cnt;
			while((cnt = reader.read(buf)) > 0)
				swr.write(buf, 0, cnt);
		}
		finally {
			reader.close();
		}

		try {
			Metadata md = new Metadata();
			StrictMetadataJsonParser mdParser = new StrictMetadataJsonParser(md);
			mdParser.parse(existingFile, swr.toString(), result);
			return md;
		}
		finally {
			StreamUtil.close(reader);
		}
	}
}
