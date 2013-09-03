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
package com.puppetlabs.geppetto.forge.impl;

import static com.puppetlabs.geppetto.forge.Forge.METADATA_JSON_NAME;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringWriter;

import com.puppetlabs.geppetto.common.os.StreamUtil;
import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.forge.util.StrictMetadataJsonParser;
import com.puppetlabs.geppetto.forge.v2.model.Metadata;

import com.google.inject.Singleton;

@Singleton
public class MetadataJSONExtractor extends AbstractMetadataExtractor {
	public int getCardinal() {
		return 20;
	}

	@Override
	public String getPrimarySource() {
		return METADATA_JSON_NAME;
	}

	@Override
	public boolean hasTypesAndProviders() {
		return true;
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
