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
package org.cloudsmith.geppetto.pp.dsl.ui.coloring;

import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultAntlrTokenToAttributeIdMapper;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;

public class PPTokenToAttributeIdMapper extends DefaultAntlrTokenToAttributeIdMapper {
	@Override
	protected String calculateId(String tokenName, int tokenType) {

		// DEBUG PRINT
		// System.out.print("Highlight id for token: " + tokenName + "\n");

		// treat, 'true', 'false' differently

		if(tokenName.equals("'true'") || tokenName.equals("'false'") || tokenName.equals("'default'") ||
				tokenName.equals("'undef'")) {
			return PPHighlightConfiguration.LITERAL_KW_ID;
		}
		if(tokenName.equals("'\"'"))
			return DefaultHighlightingConfiguration.STRING_ID;

		if("RULE_REGULAR_EXPRESSION".equals(tokenName)) {
			return PPHighlightConfiguration.REGEXP_ID;
		}
		return super.calculateId(tokenName, tokenType);
	}
}
