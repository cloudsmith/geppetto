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
package com.puppetlabs.geppetto.pp.dsl.ui.resource;

import com.puppetlabs.geppetto.pp.dsl.linking.DiagnosticConsumerBasedMessageAcceptor;
import com.puppetlabs.geppetto.pp.dsl.linking.IMessageAcceptor;
import com.puppetlabs.geppetto.pp.dsl.linking.PPResourceLinker;
import com.puppetlabs.geppetto.pp.dsl.ppdoc.DocumentationAssociator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.impl.ListBasedDiagnosticConsumer;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.IResourceScopeCache;
import org.eclipse.xtext.util.OnChangeEvictingCache;

import com.google.inject.Inject;

/**
 * A PP Resource that performs PP specific linking when the resource is loaded (the first time), and
 * when the parse result is updated.
 * Note that this is UI specific, in headless runtime, the same functionality is triggered by the @link {@link PPLinker}
 * .
 * 
 */
public class PPResource extends LazyLinkingResource {
	@Inject
	private DocumentationAssociator documentationAssociator;

	@Inject
	PPResourceLinker resourceLinker;

	/**
	 * True if the pp linking has been performed.
	 */
	protected volatile boolean fullyLinked = false;

	/**
	 * Protects against code calling back to this resource while it is performing pp linking
	 */
	protected volatile boolean isLinking = false;

	/**
	 * By calling this method, the next call to {@link #resolveLazyCrossReferences(CancelIndicator)} will
	 * perform PP linking.
	 */
	protected void discardLinkedState() {
		if(fullyLinked && !isLinking) {
			fullyLinked = false;
			isLinking = false;
			getCache().clear(this);
		}
	}

	protected void ensureLinkedState(CancelIndicator mon) {
		if(isLoaded && !isLoading && !isLinking && !isUpdating && !fullyLinked) {
			try {
				isLinking = true;
				performPPLinking(mon);
				fullyLinked = true;
			}
			finally {
				isLinking = false;
				getCache().clear(this);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * Overridden to make sure that a resource is not initialized just to compute the root URI fragment for the parse
	 * result.
	 */
	@Override
	protected String getURIFragmentRootSegment(EObject eObject) {
		if(unloadingContents == null) {
			IParseResult parseResult = getParseResult();
			if(parseResult != null && eObject == parseResult.getRootASTElement()) {
				return "0";
			}
		}
		return super.getURIFragmentRootSegment(eObject);
	}

	/**
	 * Performs PP linking, and processes documentation
	 * 
	 * @param mon
	 */
	protected void performPPLinking(CancelIndicator mon) {
		final ListBasedDiagnosticConsumer diagnosticsConsumer = new ListBasedDiagnosticConsumer();
		IMessageAcceptor acceptor = new DiagnosticConsumerBasedMessageAcceptor(diagnosticsConsumer);
		EObject model = this.getParseResult().getRootASTElement();
		documentationAssociator.validateDocumentation(model, acceptor);
		resourceLinker.link(model, acceptor, false);

		if(!isValidationDisabled()) {
			getErrors().addAll(diagnosticsConsumer.getResult(Severity.ERROR));
			getWarnings().addAll(diagnosticsConsumer.getResult(Severity.WARNING));
		}
	}

	@Override
	public void resolveLazyCrossReferences(CancelIndicator mon) {
		super.resolveLazyCrossReferences(mon);
		ensureLinkedState(mon);
	}

	/**
	 * Overridden to make sure that the cache is initialized during {@link #isLoading() loading}.
	 */
	@Override
	protected void updateInternalState(IParseResult newParseResult) {
		super.updateInternalState(newParseResult);
		// make sure that the cache adapter is installed on this resource
		IResourceScopeCache cache = getCache();
		if(cache instanceof OnChangeEvictingCache) {
			((OnChangeEvictingCache) cache).getOrCreate(this);
		}
	}

	@Override
	protected void updateInternalState(IParseResult oldParseResult, IParseResult newParseResult) {
		if(fullyLinked) {
			discardLinkedState();
		}
		super.updateInternalState(oldParseResult, newParseResult);
		EObject model = newParseResult.getRootASTElement();

		documentationAssociator.linkDocumentation(model);
	}

}
