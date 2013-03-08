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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cloudsmith.geppetto.common.os.StreamUtil;
import org.cloudsmith.geppetto.forge.ERB;
import org.cloudsmith.geppetto.forge.v2.model.Metadata;

import com.google.inject.Singleton;

@Singleton
class ERBImpl implements ERB {
	private static Pattern expressionPattern = Pattern.compile("\\A\\s*metadata\\.([A-Za-z0-9_]+)\\s*\\z");

	private void consumeComment(Reader input) throws IOException {
		consumeUntilEndMark(input);
	}

	private String consumeUntilEndMark(Reader input) throws IOException {
		StringBuilder bld = new StringBuilder();
		int n1;
		while((n1 = input.read()) >= 0) {
			if(n1 == '%') {
				int n2 = input.read();
				if(n2 == '>')
					return bld.toString();
				if(n2 == '%') {
					int n3 = input.read();
					if(n3 == '>') {
						bld.append("%>");
						continue;
					}
				}
				bld.append('%');
				bld.append((char) n2);
				continue;
			}
			bld.append((char) n1);
		}
		throw new IOException("Unbalanced construct. Missing end %>");
	}

	@Override
	public void generate(Metadata metadata, File template, File dest) throws IOException {
		BufferedWriter output = new BufferedWriter(new FileWriter(dest));
		try {
			generate(metadata, template, output);
		}
		finally {
			StreamUtil.close(output);
		}
	}

	@Override
	public void generate(Metadata metadata, File template, Writer dest) throws IOException {
		BufferedReader input = new BufferedReader(new FileReader(template), 0x1000);
		try {
			int n1;
			while((n1 = input.read()) >= 0) {
				if(n1 == '<') {
					int n2 = input.read();
					if(n2 == '%') {
						int n3 = input.read();
						if(n3 == '#')
							consumeComment(input);
						else if(n3 == '=')
							handleExpression(metadata, input, dest);
						else if(n3 == '%')
							dest.write("<%");
						else
							handleCode(input, dest);
					}
					else {
						dest.write(n1);
						dest.write(n2);
					}
				}
				else if(n1 == '%') {
					int n2 = input.read();
					if(n2 == '%') {
						int n3 = input.read();
						if(n3 == '>') {
							dest.write("%>");
						}
						else {
							dest.write(n1);
							dest.write(n2);
							dest.write(n3);
						}
					}
					else {
						dest.write(n1);
						dest.write(n2);
					}
				}
				else
					dest.write(n1);
			}
		}
		finally {
			StreamUtil.close(input);
		}
	}

	private void handleCode(Reader input, Writer dest) {
		throw new UnsupportedOperationException("Ruby code not handled");
	}

	private void handleExpression(Metadata metadata, Reader input, Writer dest) throws IOException {
		String expr = consumeUntilEndMark(input);
		Matcher m = expressionPattern.matcher(expr);
		if(!m.matches())
			throw new UnsupportedOperationException("This very limited evaluator does not understand the expression: " +
					expr);

		String key = m.group(1);
		if("full_name".equals(key) && metadata.getName() != null)
			dest.write(metadata.getName().toString());
		else if("name".equals(key) && metadata.getName() != null)
			dest.write(metadata.getName().getName());
		else if("user".equals(key) && metadata.getName() != null)
			dest.write(metadata.getName().getOwner());
		else if("author".equals(key) && metadata.getAuthor() != null)
			dest.write(metadata.getAuthor());
		else if("description".equals(key) && metadata.getDescription() != null)
			dest.write(metadata.getDescription());
		else if("license".equals(key) && metadata.getLicense() != null)
			dest.write(metadata.getLicense());
		else if("project_page".equals(key) && metadata.getProjectPage() != null)
			dest.write(metadata.getProjectPage());
		else if("source".equals(key) && metadata.getSource() != null)
			dest.write(metadata.getSource());
		else if("summary".equals(key) && metadata.getSummary() != null)
			dest.write(metadata.getSummary());
		else if("version".equals(key) && metadata.getVersion() != null)
			dest.write(metadata.getVersion().toString());
	}
}
