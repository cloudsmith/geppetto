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
package org.cloudsmith.geppetto.pp.dsl.formatting;

import org.cloudsmith.geppetto.pp.AttributeOperations;
import org.cloudsmith.geppetto.pp.ResourceBody;
import org.cloudsmith.geppetto.pp.ResourceExpression;
import org.cloudsmith.geppetto.pp.SelectorExpression;
import org.cloudsmith.geppetto.pp.dsl.services.PPGrammarAccess;
import org.cloudsmith.xtext.dommodel.IDomNode;
import org.cloudsmith.xtext.dommodel.formatter.DeclarativeSemanticFlowLayout;
import org.cloudsmith.xtext.dommodel.formatter.DomNodeLayoutFeeder;
import org.cloudsmith.xtext.dommodel.formatter.LayoutUtils;
import org.cloudsmith.xtext.dommodel.formatter.css.Alignment;
import org.cloudsmith.xtext.dommodel.formatter.css.IStyleFactory;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleSet;
import org.cloudsmith.xtext.textflow.ITextFlow;

import com.google.inject.Inject;
import com.google.inject.Singleton;

/**
 * Semantic layouts for PP
 * 
 */
@Singleton
public class PPSemanticLayout extends DeclarativeSemanticFlowLayout {
	public enum ResourceStyle {
		EMPTY, SINGLEBODY_TITLE, SINGLEBODY_NO_TITLE, MULTIPLE_BODIES;
	}

	@Inject
	IStyleFactory styles;

	@Inject
	PPGrammarAccess grammarAccess;

	@Inject
	DomNodeLayoutFeeder feeder;

	protected void _after(AttributeOperations aos, StyleSet styleSet, IDomNode node, ITextFlow flow,
			ILayoutContext context) {
		if(aos.eContainer() instanceof ResourceBody) {
			flow.changeIndentation(-1);
		}
	}

	protected void _before(AttributeOperations aos, StyleSet styleSet, IDomNode node, ITextFlow flow,
			ILayoutContext context) {
		if(aos.eContainer() instanceof ResourceBody) {
			flow.changeIndentation(1);
		}
	}

	protected boolean _format(AttributeOperations aos, StyleSet styleSet, IDomNode node, ITextFlow flow,
			ILayoutContext context) {
		LayoutUtils.unifyWidthAndAlign(
			node, grammarAccess.getAttributeOperationAccess().getKeyNameParserRuleCall_1_0(), Alignment.left);
		return false;
	}

	protected boolean _format(ResourceExpression o, StyleSet styleSet, IDomNode node, ITextFlow flow,
			ILayoutContext context) {
		ResourceStyle rstyle = null;
		switch(o.getResourceData().size()) {
			case 0:
				rstyle = ResourceStyle.EMPTY;
				break;
			case 1:
				rstyle = o.getResourceData().get(0).getNameExpr() != null
						? ResourceStyle.SINGLEBODY_TITLE
						: ResourceStyle.SINGLEBODY_NO_TITLE;
				break;
			default:
				rstyle = ResourceStyle.MULTIPLE_BODIES;
				break;
		}
		node.getStyleClassifiers().add(rstyle);
		return false;
	}

	protected boolean _format(SelectorExpression se, StyleSet styleSet, IDomNode node, ITextFlow flow,
			ILayoutContext context) {
		LayoutUtils.unifyWidthAndAlign(
			node, grammarAccess.getSelectorEntryAccess().getSelectorEntryLeftExprAction_1_0(), Alignment.left);
		return false;

	}
}
