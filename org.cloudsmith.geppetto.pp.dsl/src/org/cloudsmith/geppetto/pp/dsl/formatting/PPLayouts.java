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

import java.util.Iterator;

import org.cloudsmith.geppetto.pp.AttributeOperation;
import org.cloudsmith.geppetto.pp.AttributeOperations;
import org.cloudsmith.geppetto.pp.ResourceExpression;
import org.cloudsmith.geppetto.pp.dsl.services.PPGrammarAccess;
import org.cloudsmith.xtext.dommodel.IDomNode;
import org.cloudsmith.xtext.dommodel.formatter.FlowLayout;
import org.cloudsmith.xtext.dommodel.formatter.css.IStyleFactory;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleSet;
import org.cloudsmith.xtext.textflow.ITextFlow;
import org.eclipse.emf.ecore.EObject;

import com.google.inject.Inject;

/**
 * @author henrik
 * 
 */
public class PPLayouts {
	public static class AttributeOperationsLayout extends FlowLayout {
		@Inject
		PPGrammarAccess grammarAccess;

		@Inject
		IStyleFactory styles;

		@Override
		protected boolean formatComposite(StyleSet styleSet, IDomNode node, ITextFlow flow, ILayoutContext context) {
			EObject semantic = node.getSemanticObject();
			if(semantic instanceof AttributeOperations == false)
				throw new IllegalStateException("AttributeOperationsLayout associated with non AttributeOperations");

			AttributeOperations aos = (AttributeOperations) semantic;
			int max = 0;
			for(AttributeOperation ao : aos.getAttributes()) {
				max = Math.max(max, ao.getKey() == null
						? 0
						: ao.getKey().length());
			}
			Iterator<IDomNode> iterator = node.treeIterator();
			while(iterator.hasNext()) {
				node = iterator.next();
				if(node.getGrammarElement() == grammarAccess.getAttributeOperationAccess().getKeyNameParserRuleCall_1_0())
					node.getStyles().add(StyleSet.withStyle(styles.width(max)));
				// String label = new GrammarElementTitleSwitch().showAssignments().doSwitch(node.getGrammarElement());
				// System.err.println("AO node: " + label);
				// if(label.startsWith("key="))
				// System.err.println("Found  via name");
				// if(node.getGrammarElement() == grammarAccess.getAttributeOperationAccess().getKeyNameParserRuleCall_1_0())
				// System.err.println("Found via grammar");
			}
			return super.formatComposite(styleSet, node, flow, context);
		}
	}

	public static class ResourceBodyLayout extends FlowLayout {

		@Override
		protected boolean formatComposite(StyleSet styleSet, IDomNode node, ITextFlow flow, ILayoutContext context) {
			// Compute widths

			return super.formatComposite(styleSet, node, flow, context);
		}

	}

	public static class ResourceExpressionLayout extends FlowLayout {

		@Override
		protected boolean formatComposite(StyleSet styleSet, IDomNode node, ITextFlow flow, ILayoutContext context) {
			// Assign styles to bodies
			EObject semantic = node.getSemanticObject();
			if(semantic instanceof ResourceExpression == false)
				throw new IllegalStateException("ResourceExpressionLayout associated with non ResourceExpression");
			ResourceExpression re = (ResourceExpression) semantic;
			ResourceStyle rstyle = null;
			switch(re.getResourceData().size()) {
				case 0:
					rstyle = ResourceStyle.EMPTY;
					break;
				case 1:
					rstyle = re.getResourceData().get(0).getNameExpr() != null
							? ResourceStyle.SINGLEBODY_TITLE
							: ResourceStyle.SINGLEBODY_NO_TITLE;
					break;
				default:
					rstyle = ResourceStyle.MULTIPLE_BODIES;
					break;
			}
			node.getStyleClassifiers().add(rstyle);
			return super.formatComposite(styleSet, node, flow, context);
		}
	}

	public enum ResourceStyle {
		EMPTY, SINGLEBODY_TITLE, SINGLEBODY_NO_TITLE, MULTIPLE_BODIES;
	}
}
