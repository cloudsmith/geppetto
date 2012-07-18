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
package org.cloudsmith.geppetto.pp.dsl.serialization;

import java.util.Collections;
import java.util.Map;

import org.cloudsmith.geppetto.pp.dsl.services.PPGrammarAccess;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.AbstractRule;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.nodemodel.impl.AbstractNode;
import org.eclipse.xtext.parsetree.reconstr.impl.ValueSerializer;
import org.eclipse.xtext.util.Exceptions;
import org.eclipse.xtext.util.PolymorphicDispatcher;
import org.eclipse.xtext.util.PolymorphicDispatcher.ErrorHandler;

import com.google.common.collect.Maps;
import com.google.inject.Inject;

/**
 * TODO: Can probably be simplified further as there is less need for the fancy overrides required for the
 * Xtext 1.0 implementation.
 * TODO: This class can probably be removed - with the change in dollarVar parsing there is nothing left it needs to do.
 * 
 */
public class PPValueSerializer extends ValueSerializer {

	private PPGrammarAccess pga;

	private Map<AbstractRule, String> ruleToText;

	private final PolymorphicDispatcher<String> unassignedDispatch = new PolymorphicDispatcher<String>(
		"unassigned", 2, 3, Collections.singletonList(this), new ErrorHandler<String>() {
			public String handle(Object[] params, Throwable e) {
				return handleError(params, e);
			}
		});

	@Inject
	public PPValueSerializer(IGrammarAccess ga) {
		pga = (PPGrammarAccess) ga;

		/**
		 * Map of terminals and keywords that the serializer can not automatically figure out
		 * how to handle. Adding a mapping to "ruleToText" will ensure that a call from a rule to
		 * the mapped rule will serialize as the mapped string throughout the grammar. If something more
		 * specific is required, a polymorphic "unassigned" method for the specific class should be added.
		 * Note that keywords in the grammar are handled automatically (unless nested in a data rule ?).
		 */
		ruleToText = Maps.newHashMap();
		// ruleToText.put(pga.getDQT_DOLLARRule(), "$");
		// ruleToText.put(pga.getDQT_QUOTERule(), "\"");
	}

	public String doUnassigned(EObject context, RuleCall ruleCall, INode node) {
		if(node == null)
			return unassignedDispatch.invoke(context, ruleCall);
		return unassignedDispatch.invoke(context, ruleCall, node);
	}

	protected String handleError(Object[] params, Throwable e) {
		return Exceptions.throwUncheckedException(e);
	}

	@Override
	public String serializeUnassignedValue(EObject context, RuleCall ruleCall, INode node) {
		try {
			// let the super impl have a go at finding what to serialize, if it fails, check if
			// the pp-specific rules knows better

			return super.serializeUnassignedValue(context, ruleCall, node);
		}
		catch(IllegalArgumentException e) {
			return doUnassigned(context, ruleCall, node);
		}
	}

	public String unassigned(EObject o, RuleCall ruleCall) {
		return unassigned(o, ruleCall, null);
	}

	public String unassigned(EObject o, RuleCall ruleCall, AbstractNode node) {
		String s = ruleToText.get(ruleCall.getRule());
		if(s != null)
			return s;

		throw new IllegalArgumentException("Undefined: unassigned rule call to Rule: " + ruleCall.getRule().getName());
	}

}
