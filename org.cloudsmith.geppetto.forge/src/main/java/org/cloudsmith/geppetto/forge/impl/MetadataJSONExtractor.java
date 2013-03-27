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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;

import org.cloudsmith.geppetto.common.os.StreamUtil;
import org.cloudsmith.geppetto.forge.v2.model.Metadata;

import com.google.gson.Gson;
import com.google.inject.Inject;

public class MetadataJSONExtractor extends AbstractMetadataExtractor {
	@Inject
	private Gson gson;

	public int getCardinal() {
		return 0;
	}

	@Override
	public String getPrimarySource() {
		return "metadata.json";
	}

	@Override
	protected Metadata performMetadataExtraction(File existingFile) throws IOException {
		Reader reader = new BufferedReader(new FileReader(existingFile));
		try {
			Metadata md = gson.fromJson(reader, Metadata.class);
			return md;
		}
		finally {
			StreamUtil.close(reader);
		}
	}
}
