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

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;
import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;
import org.eclipse.xtext.ui.editor.utils.TextStyle;

public class PPHighlightConfiguration extends DefaultHighlightingConfiguration {

	public static final String TEXT_ID = "text";

	public static final String TEMPLATE_TEXT_ID = "template";

	public static final String DOCUMENTATION_ID = "documentation";

	public static final String DOC_FIXED_ID = "doc_fixed";

	public static final String DOC_BOLD_ID = "doc_bold";

	public static final String DOC_ITALIC_ID = "doc_italic";

	public static final String DOC_PLAIN_ID = "doc_plain";

	public static final String DOC_HEADING1_ID = "doc_h1";

	public static final String DOC_HEADING2_ID = "doc_h2";

	public static final String DOC_HEADING3_ID = "doc_h3";

	public static final String DOC_HEADING4_ID = "doc_h4";

	public static final String DOC_HEADING5_ID = "doc_h5";

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

	public static final String TASK_ID = "task";

	@Override
	public void configure(IHighlightingConfigurationAcceptor acceptor) {
		super.configure(acceptor);
		acceptor.acceptDefaultHighlighting(TEMPLATE_TEXT_ID, "String - Double Quoted", templateTextStyle());
		// acceptor.acceptDefaultHighlighting(TEXT_ID, "Text", docTextStyle());
		acceptor.acceptDefaultHighlighting(DOCUMENTATION_ID, "Documentation", documentationStyle());
		acceptor.acceptDefaultHighlighting(DOC_HEADING1_ID, "Doc H1", documentationH1Style());
		acceptor.acceptDefaultHighlighting(DOC_HEADING2_ID, "Doc H2", documentationH2Style());
		acceptor.acceptDefaultHighlighting(DOC_HEADING3_ID, "Doc H3", documentationH3Style());
		acceptor.acceptDefaultHighlighting(DOC_HEADING4_ID, "Doc H4", documentationH4Style());
		acceptor.acceptDefaultHighlighting(DOC_HEADING5_ID, "Doc H5", documentationH5Style());
		acceptor.acceptDefaultHighlighting(DOC_PLAIN_ID, "Doc plain", documentationPlainStyle());
		acceptor.acceptDefaultHighlighting(DOC_BOLD_ID, "Doc bold", documentationBoldStyle());
		acceptor.acceptDefaultHighlighting(DOC_FIXED_ID, "Doc code", documentationFixedStyle());
		acceptor.acceptDefaultHighlighting(DOC_ITALIC_ID, "Doc italic", documentationItalicStyle());

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

		acceptor.acceptDefaultHighlighting(TASK_ID, "Tasks", taskTextStyle());
	}

	public TextStyle documentationBoldStyle() {
		TextStyle textStyle = documentationPlainStyle().copy();
		// // textStyle.getFontData()[0].setHeight(12);
		// // Font f = new Font(Display.getCurrent(), "Arial", 12, SWT.BOLD);
		// // textStyle.setFontData(f.getFontData());
		// // changeHeight(textStyle, 12);
		// // modifyFontData(textStyle, 12, SWT.BOLD);
		// textStyle.setStyle(SWT.BOLD);
		return textStyle;
	}

	public TextStyle documentationFixedStyle() {
		TextStyle textStyle = documentationStyle().copy();
		// Font f = new Font(Display.getCurrent(), "Courier", 11, SWT.NONE);
		// textStyle.setFontData(f.getFontData());
		// f.dispose();
		return textStyle;
	}

	public TextStyle documentationH1Style() {
		TextStyle textStyle = documentationPlainStyle().copy();
		// Font f = new Font(Display.getCurrent(), "Arial", 22, SWT.BOLD);
		// textStyle.setFontData(f.getFontData());
		// f.dispose();
		// textStyle.setColor(new RGB(63, 95, 191));
		// // Font f = new Font(Display.getCurrent(), "Arial", 22, SWT.BOLD);
		// // textStyle.setFontData(f.getFontData());
		return textStyle;
	}

	public TextStyle documentationH2Style() {
		TextStyle textStyle = documentationH1Style().copy();
		// // Font f = new Font(Display.getCurrent(), "Arial", 20, SWT.BOLD);
		// // textStyle.setFontData(f.getFontData());
		// modifyFontData(textStyle, 20, SWT.BOLD);
		return textStyle;
	}

	public TextStyle documentationH3Style() {
		TextStyle textStyle = documentationH1Style().copy();
		// // Font f = new Font(Display.getCurrent(), "Arial", 18, SWT.BOLD);
		// // textStyle.setFontData(f.getFontData());
		// modifyFontData(textStyle, 18, SWT.BOLD);
		return textStyle;
	}

	public TextStyle documentationH4Style() {
		TextStyle textStyle = documentationH1Style().copy();
		// // Font f = new Font(Display.getCurrent(), "Arial", 16, SWT.BOLD);
		// // textStyle.setFontData(f.getFontData());
		// modifyFontData(textStyle, 16, SWT.BOLD);
		return textStyle;
	}

	public TextStyle documentationH5Style() {
		TextStyle textStyle = documentationH1Style().copy();
		// // Font f = new Font(Display.getCurrent(), "Arial", 14, SWT.BOLD);
		// // textStyle.setFontData(f.getFontData());
		// modifyFontData(textStyle, 14, SWT.BOLD);
		return textStyle;
	}

	public TextStyle documentationItalicStyle() {
		TextStyle textStyle = documentationPlainStyle().copy();
		// // Font f = new Font(Display.getCurrent(), "Arial", 12, SWT.ITALIC);
		// // textStyle.setFontData(f.getFontData());
		// // modifyFontData(textStyle, 12, SWT.ITALIC);
		// textStyle.setStyle(SWT.ITALIC);
		return textStyle;
	}

	public TextStyle documentationPlainStyle() {
		TextStyle textStyle = documentationStyle().copy();
		// Font f = new Font(Display.getCurrent(), "Arial", 12, SWT.NONE);
		// textStyle.setFontData(f.getFontData());
		// modifyFontData(textStyle, 12, SWT.NORMAL);
		// f.dispose();
		// textStyle.setColor(new RGB(63, 95, 191));
		return textStyle;
	}

	public TextStyle documentationStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(63, 95, 191));
		return textStyle;
	}

	public TextStyle italicKeywordLightStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(127, 0, 85));
		textStyle.setStyle(SWT.ITALIC);
		return textStyle;
	}

	protected void modifyFontData(TextStyle textStyle, int height, int style) {
		FontData fd = textStyle.getFontData()[0];
		fd.setHeight(height);
		fd.setStyle(style);
		textStyle.setFontData(fd);
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

	public TextStyle taskTextStyle() {
		TextStyle textStyle = defaultTextStyle().copy();
		textStyle.setColor(new RGB(68, 68, 68));
		textStyle.setStyle(SWT.BOLD);
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
