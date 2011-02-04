/**
 * Copyright (c) 2011 Cloudsmith, Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.pp.dsl.ui.coloring;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;

public class PPHighlightConfiguration extends DefaultHighlightingConfiguration {

	public static final String TEXT_ID = "text";

	public static final String TEMPLATE_TEXT_ID = "template";

	public static final String JAVADOC_ID = "javadoc";

	public static final String REGEXP_ID = "regexp";

	public static final String LITERAL_KW_ID = "literalkw";

	public static final String NULL_ID = "null";

	public static final String VERSION_ID = "version";

	public static final String PATH_ID = "path";

	public static final String REAL_ID = "real";

	public static final String PROPERTY_ID = "property";

	public static final String VARIABLE_ID = "variable";

	public static final String SPECIAL_SPACE_ID = "spaces";

	public static final String RESOURCE_REF_ID = "resourceRef";

	public static final String RESOURCE_TITLE_ID = "resourceTitle";

	@Override
	public void configure(IHighlightingConfigurationAcceptor acceptor) {
		super.configure(acceptor);
		acceptor.acceptDefaultHighlighting(TEMPLATE_TEXT_ID, "String - Double Quoted", templateTextStyle());
		// acceptor.acceptDefaultHighlighting(TEXT_ID, "Text", docTextStyle());
		// acceptor.acceptDefaultHighlighting(JAVADOC_ID, "Documentation", docJavaDocStyle());
		acceptor.acceptDefaultHighlighting(REGEXP_ID, "Regular Expression", regexpTextStyle());
		acceptor.acceptDefaultHighlighting(LITERAL_KW_ID, "Literal Keywords", italicKeywordLightStyle());
		// acceptor.acceptDefaultHighlighting(NULL_ID, "Null Literal", nullTextStyle());
		// acceptor.acceptDefaultHighlighting(PROPERTY_ID, "Properties", propertyTextStyle());
		acceptor.acceptDefaultHighlighting(SPECIAL_SPACE_ID, "Special Spaces", specialSpaceTextStyle());

		// from semantic
		// acceptor.acceptDefaultHighlighting(VERSION_ID, "Version", versionTextStyle());
		// acceptor.acceptDefaultHighlighting(PATH_ID, "Path", pathTextStyle());
		// acceptor.acceptDefaultHighlighting(REAL_ID, "Floating point", realTextStyle());
		acceptor.acceptDefaultHighlighting(VARIABLE_ID, "Variable", variableTextStyle());
		acceptor.acceptDefaultHighlighting(RESOURCE_REF_ID, "Resource Reference", resourceRefTextStyle());
		acceptor.acceptDefaultHighlighting(RESOURCE_TITLE_ID, "Resource Title", resourceTitleTextStyle());

	}

	public TextStyle docJavaDocStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(63, 95, 191));
		return textStyle;
	}

	public TextStyle docTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(63, 95, 191));
		// Does not look as good as background color affects only what is behind the text.
		// textStyle.setBackgroundColor(new RGB(252, 255, 240)); // titanium white
		textStyle.setStyle(SWT.ITALIC);
		return textStyle;
	}

	public TextStyle italicKeywordLightStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(127, 0, 85));
		textStyle.setStyle(SWT.ITALIC);
		return textStyle;
	}

	public TextStyle pathTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(63, 95, 191));
		textStyle.setStyle(SWT.ITALIC);
		return textStyle;
	}

	public TextStyle propertyTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0x99, 0x33, 0x00)); // Brownish
		return textStyle;

	}

	public TextStyle realTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(125, 125, 125));
		return textStyle;
	}

	public TextStyle regexpTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(0, 0, 192));
		textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}

	public TextStyle resourceRefTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(128, 0, 0)); // cayenne brown
		return textStyle;
	}

	public TextStyle resourceTitleTextStyle() {
		TextStyle textStyle = new TextStyle();
		textStyle.setStyle(SWT.ITALIC);
		return textStyle;
	}

	public TextStyle specialSpaceTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		// Affects what is behind the text and WS
		textStyle.setBackgroundColor(new RGB(255, 204, 102)); // light orange
		return textStyle;
	}

	public TextStyle templateTextStyle() {
		TextStyle textStyle = stringTextStyle().copy();
		// textStyle.setColor(new RGB(63, 95, 191));
		// Affects what is behind the text and WS - important since this is a verbatim style
		// and whitespace counts.
		// textStyle.setBackgroundColor(new RGB(252, 255, 240)); // titanium white
		// textStyle.setBackgroundColor(new RGB(231, 255, 232)); // pale green
		// textStyle.setStyle(SWT.ITALIC);
		return textStyle;
	}

	public TextStyle variableTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		return textStyle;
	}

}
