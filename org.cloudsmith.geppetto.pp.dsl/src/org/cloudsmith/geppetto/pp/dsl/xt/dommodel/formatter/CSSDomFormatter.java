/**
 * Copyright (c) 2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter;

import org.cloudsmith.geppetto.pp.dsl.ppformatting.FormStream;
import org.cloudsmith.geppetto.pp.dsl.ppformatting.IFormStream;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.DomModelUtils;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.IDomNode;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.IDomNode.NodeClassifier;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.GraphCSS;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.IFunctionFactory;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.IStyleFactory;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.IStyleVisitor;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.LineBreaks;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.Select;
import org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.Spacing;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.util.ITextRegion;
import org.eclipse.xtext.util.ReplaceRegion;
import org.eclipse.xtext.util.TextRegion;

import com.google.inject.Inject;

/**
 * A Dom Model Formatter that ensures a single whitespace between tokens.
 * 
 */
public class CSSDomFormatter implements IDomModelFormatter {

	private static class WhitespaceStyleCollector extends IStyleVisitor.Default {
		private Spacing spacing;

		private LineBreaks lineBreaks;

		private String text;

		/**
		 * @return the lineBreaks
		 */
		public LineBreaks getLineBreaks() {
			return lineBreaks;
		}

		/**
		 * @return the spacing
		 */
		public Spacing getSpacing() {
			if(spacing == null) {
				// TODO: DEBUG OUTPUT REMOVAL
				System.err.println("No spacing collected - using default");
				spacing = new Spacing(1);
			}
			return spacing;
		}

		/**
		 * @return the text
		 */
		public String getText() {
			return text;
		}

		@Override
		public void lineBreaks(LineBreaks lineBreakInfo) {
			if(lineBreaks == null)
				lineBreaks = new LineBreaks(0);
			lineBreaks = lineBreakInfo;
		}

		@Override
		public void spacing(Spacing value) {
			spacing = value;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.cloudsmith.geppetto.pp.dsl.xt.dommodel.formatter.css.IStyleVisitor.Default#tokenText(java.lang.String)
		 */
		@Override
		public void tokenText(String text) {
			this.text = text;
		}
	}

	boolean hasStarted;

	boolean wsWritten;

	IStyleFactory styles;

	IFunctionFactory functions;

	private GraphCSS css;

	@Inject
	public CSSDomFormatter(IStyleFactory styles, IFunctionFactory functions) {
		this.styles = styles;
		this.functions = functions;

		// Starting with a static CSS
		css = new GraphCSS();

		css.addRules( //
			// Default nodes print their text
			Select.any().withStyle(//
				styles.tokenText(functions.textOfNode())), //

			// Default spacing is one space per whitespace
			Select.whitespace().withStyle(//
				styles.oneSpace()), //

			// Except for leading whitepsace
			Select.before(Select.whitespace(), Select.node(NodeClassifier.FIRST_TOKEN)).withStyle(//
				styles.noSpace()), Select.after(Select.whitespace(), Select.node(NodeClassifier.LAST_TOKEN)).withStyle(//
				styles.noSpace())
		//
		// // Test rules around keyword ","
		// Select.before(Select.whitespace(), Select.keyword(",")).withStyles(styles.noSpace()), //
		// Select.after(Select.whitespace(), Select.keyword(",")).withStyles(styles.oneSpace()) //
		);
	}

	protected int calculateSpaces(String text, Spacing spacing) {
		int length = text == null
				? 0
				: text.length();
		// TODO: DEBUG OUTPUT REMOVAL
		System.err.println("Space(" + length + ") with spacing(" + spacing.getMin() + ", " + spacing.getNormal() +
				", " + spacing.getMax() + ")");
		if(length == 0)
			return spacing.getNormal();
		if(length < spacing.getMin())
			return spacing.getMin();
		if(length > spacing.getMax())
			return spacing.getMax();
		return length;
	}

	@Override
	public ReplaceRegion format(IDomNode dom, ITextRegion regionToFormat, IFormattingContext formattingContext,
			Acceptor errors) {

		hasStarted = false;
		wsWritten = false;
		// final StringBuilder builder = new StringBuilder();
		final IFormStream output = new FormStream();
		internalFormat(dom, regionToFormat, formattingContext, output);
		final String text = output.getText();
		if(regionToFormat == null)
			regionToFormat = new TextRegion(0, text.length());
		return new ReplaceRegion(regionToFormat, text);
	}

	protected void formatComposite(IDomNode node, ITextRegion regionToFormat, IFormattingContext formattingContext,
			IFormStream output) {
		for(IDomNode n : node.getChildren())
			internalFormat(n, regionToFormat, formattingContext, output);
	}

	protected void formatLeaf(IDomNode node, ITextRegion regionToFormat, IFormattingContext formattingContext,
			IFormStream output) {

		if(DomModelUtils.isWhitespace(node)) {
			formatWhitespace(node, regionToFormat, formattingContext, output);
			return;
		}
		if(isFormattingWanted(node, regionToFormat)) {
			String text = node.getText();
			if(text.length() == 0)
				return;
			writeSpaceIfNecessary(node, output);
			output.text(node.getText());
		}
		hasStarted = true;
		wsWritten = false;
	}

	protected void formatWhitespace(IDomNode node, ITextRegion regionToFormat, IFormattingContext formattingContext,
			IFormStream output) {
		if(formattingContext.isWhitespacePreservation()) {
			String text = node.getText();

			if(isFormattingWanted(node, regionToFormat))
				output.text(text);
			if(text.length() > 0)
				wsWritten = true;
		}
		else {
			WhitespaceStyleCollector collector = new WhitespaceStyleCollector();
			css.collectStyles(node, collector);

			Spacing spacing = collector.getSpacing();
			LineBreaks lineBreaks = collector.getLineBreaks();
			String text = collector.getText();

			// TODO: deal with line break
			int count = calculateSpaces(text, spacing);
			if(isFormattingWanted(node, regionToFormat))
				output.space(count);
			if(count > 0)
				wsWritten = true;
		}
	}

	protected void internalFormat(IDomNode node, ITextRegion regionToFormat, IFormattingContext formattingContext,
			IFormStream output) {
		if(node.isLeaf())
			formatLeaf(node, regionToFormat, formattingContext, output);
		else
			formatComposite(node, regionToFormat, formattingContext, output);

	}

	protected boolean isFormattingWanted(IDomNode node, ITextRegion regionToFormat) {
		if(regionToFormat == null)
			return true;
		return regionToFormat.contains(node.getOffset());
	}

	protected void writeSpaceIfNecessary(IDomNode node, IFormStream output) {
		if(hasStarted && !wsWritten) {
			output.oneSpace();
			wsWritten = true;
		}
	}
}
