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
package com.puppetlabs.geppetto.pp.dsl.formatting;

import com.puppetlabs.geppetto.pp.dsl.formatting.IBreakAndAlignAdvice.WhenToApply;
import com.puppetlabs.xtext.dommodel.IDomNode;
import com.puppetlabs.xtext.dommodel.formatter.DomNodeLayoutFeeder;
import com.puppetlabs.xtext.dommodel.formatter.ILayoutManager.ILayoutContext;
import com.puppetlabs.xtext.dommodel.formatter.LayoutUtils;
import com.puppetlabs.xtext.textflow.ITextFlow;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.AbstractElement;

import com.google.inject.Inject;
import com.google.inject.Provider;

/**
 * @author henrik
 * 
 */
public abstract class AbstractListLayout {

	@Inject
	DomNodeLayoutFeeder feeder;

	@Inject
	LayoutUtils layoutUtils;

	@Inject
	Provider<IBreakAndAlignAdvice> breakAlignAdviceProvider;

	protected boolean format(IDomNode node, ITextFlow flow, ILayoutContext context) {

		final IBreakAndAlignAdvice advisor = getAlignAdvice();
		final WhenToApply advice = advisor.listsAdvice();
		final int clusterWidth = advisor.clusterSize();

		boolean breakAndAlign = false;
		// Do not break and align unless there is more than one element in the list/hash
		if(hasMoreThanOneElement(node.getSemanticObject()))
			switch(advice) {
				case Never:
					break;
				case Always:
					breakAndAlign = true;
					break;
				case OnOverflow:
					// Measure fit from start to final significant element (not counting trailing WS/breaks)
					if(!layoutUtils.fitsOnSameLine(node, getLastSignificantGrammarElement(), flow, context))
						breakAndAlign = true;
					break;
			}
		markup(node, breakAndAlign, clusterWidth, flow, context); // that was easy
		return false;
	}

	protected IBreakAndAlignAdvice getAlignAdvice() {
		return breakAlignAdviceProvider.get();
	}

	protected abstract AbstractElement getLastSignificantGrammarElement();

	protected abstract boolean hasMoreThanOneElement(EObject semantic);

	protected abstract void markup(IDomNode node, final boolean breakAndAlign, final int clusterWidth, ITextFlow flow,
			ILayoutContext context);
}
