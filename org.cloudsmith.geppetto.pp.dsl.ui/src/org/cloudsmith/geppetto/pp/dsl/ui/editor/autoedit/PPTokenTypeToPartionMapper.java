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
package org.cloudsmith.geppetto.pp.dsl.ui.editor.autoedit;

import org.eclipse.jface.text.IDocument;
import org.eclipse.xtext.ui.editor.model.ITokenTypeToPartitionTypeMapper;
import org.eclipse.xtext.ui.editor.model.TokenTypeToStringMapper;

import com.google.inject.Singleton;

@Singleton
public class PPTokenTypeToPartionMapper extends TokenTypeToStringMapper implements ITokenTypeToPartitionTypeMapper {

	public final static String COMMENT_PARTITION = "__comment";

	public final static String STRING_LITERAL_PARTITION = "__string";

	public final static String REGEX_LITERAL_PARTITION = "__regex";

	public final static String SL_COMMENT_PARTITION = "__sl_comment";

	protected static final String[] SUPPORTED_PARTITIONS = new String[] {
			COMMENT_PARTITION, SL_COMMENT_PARTITION, STRING_LITERAL_PARTITION, REGEX_LITERAL_PARTITION,
			IDocument.DEFAULT_CONTENT_TYPE };

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.ui.editor.model.TokenTypeToStringMapper#calculateId(java.lang.String, int)
	 */
	@Override
	protected String calculateId(String tokenName, int tokenType) {
		// if("RULE_ML_COMMENT".equals(tokenName) || "RULE_SL_COMMENT".equals(tokenName))
		// return COMMENT_PARTITION;
		if("RULE_ML_COMMENT".equals(tokenName))
			return COMMENT_PARTITION;

		if("RULE_SL_COMMENT".equals(tokenName))
			return SL_COMMENT_PARTITION;

		// This is not really what we want - WS can not be a LITERAL_PARTION unless it is
		// in a string...

		if("RULE_WORD_CHARS".equals(tokenName))
			return STRING_LITERAL_PARTITION;
		if("RULE_ANY_OTHER".equals(tokenName))
			return STRING_LITERAL_PARTITION;
		if("'::'".equals(tokenName))
			return STRING_LITERAL_PARTITION;
		if("'$'".equals(tokenName))
			return STRING_LITERAL_PARTITION;
		// issue #276, screws up bracket matching
		// if("'${'".equals(tokenName))
		// return STRING_LITERAL_PARTITION;
		if("'\\''".equals(tokenName))
			return STRING_LITERAL_PARTITION;
		if("'\\\"'".equals(tokenName))
			return STRING_LITERAL_PARTITION;
		if("'\\$'".equals(tokenName))
			return STRING_LITERAL_PARTITION;
		if("'\\${'".equals(tokenName))
			return STRING_LITERAL_PARTITION;
		if("'\\\\".equals(tokenName))
			return STRING_LITERAL_PARTITION;
		if("'\''".equals(tokenName))
			return STRING_LITERAL_PARTITION;
		if("'\"'".equals(tokenName))
			return STRING_LITERAL_PARTITION;

		if("RULE_REGULAR_EXPRESSION".equals(tokenName))
			return REGEX_LITERAL_PARTITION;

		return IDocument.DEFAULT_CONTENT_TYPE;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.ui.editor.model.ITokenTypeToPartitionTypeMapper#getPartitionType(int)
	 */
	@Override
	public String getPartitionType(int antlrTokenType) {
		return getMappedValue(antlrTokenType);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.ui.editor.model.ITokenTypeToPartitionTypeMapper#getSupportedPartitionTypes()
	 */
	@Override
	public String[] getSupportedPartitionTypes() {
		return SUPPORTED_PARTITIONS;
	}

}
