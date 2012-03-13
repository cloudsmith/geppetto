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
package org.cloudsmith.xtext.dommodel.formatter;

import java.util.Collections;

import org.cloudsmith.xtext.dommodel.IDomNode;
import org.cloudsmith.xtext.dommodel.formatter.css.StyleSet;
import org.cloudsmith.xtext.textflow.ITextFlow;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.util.PolymorphicDispatcher;

/**
 * 
 * 
 */
public class DeclarativeSemanticFlowLayout extends FlowLayout {

	private PolymorphicDispatcher<Boolean> formatDispatcher = new PolymorphicDispatcher<Boolean>(
		"_format", 5, 5, Collections.singletonList(this), PolymorphicDispatcher.NullErrorHandler.<Boolean> get()) {
		@Override
		protected Boolean handleNoSuchMethod(Object... params) {
			return false;
		}
	};

	private PolymorphicDispatcher<Boolean> beforeDispatcher = new PolymorphicDispatcher<Boolean>(
		"_before", 5, 5, Collections.singletonList(this), PolymorphicDispatcher.NullErrorHandler.<Boolean> get()) {
		@Override
		protected Boolean handleNoSuchMethod(Object... params) {
			return false;
		}
	};

	private PolymorphicDispatcher<Boolean> afterDispatcher = new PolymorphicDispatcher<Boolean>(
		"_after", 5, 5, Collections.singletonList(this), PolymorphicDispatcher.NullErrorHandler.<Boolean> get()) {
		@Override
		protected Boolean handleNoSuchMethod(Object... params) {
			return false;
		}
	};

	@Override
	public void afterComposite(StyleSet styleSet, IDomNode node, ITextFlow flow, ILayoutContext context) {
		EObject semantic = node.getSemanticObject();
		if(semantic != null)
			doSemanticAfter(semantic, styleSet, node, flow, context);

		super.beforeComposite(styleSet, node, flow, context);
	}

	@Override
	public void beforeComposite(StyleSet styleSet, IDomNode node, ITextFlow flow, ILayoutContext context) {
		EObject semantic = node.getSemanticObject();
		if(semantic != null)
			doSemanticBefore(semantic, styleSet, node, flow, context);

		super.beforeComposite(styleSet, node, flow, context);
	}

	protected void doSemanticAfter(EObject semantic, StyleSet styleSet, IDomNode node, ITextFlow flow,
			ILayoutContext context) {
		afterDispatcher.invoke(semantic, styleSet, node, flow, context);
	}

	protected void doSemanticBefore(EObject semantic, StyleSet styleSet, IDomNode node, ITextFlow flow,
			ILayoutContext context) {
		beforeDispatcher.invoke(semantic, styleSet, node, flow, context);
	}

	protected boolean doSemanticFormat(EObject semantic, StyleSet styleSet, IDomNode node, ITextFlow flow,
			ILayoutContext context) {
		return formatDispatcher.invoke(semantic, styleSet, node, flow, context);
	}

	@Override
	protected boolean formatComposite(StyleSet styleSet, IDomNode node, ITextFlow flow, ILayoutContext context) {
		EObject semantic = node.getSemanticObject();
		if(semantic != null)
			return doSemanticFormat(semantic, styleSet, node, flow, context);
		return super.formatComposite(styleSet, node, flow, context);
	}
}
