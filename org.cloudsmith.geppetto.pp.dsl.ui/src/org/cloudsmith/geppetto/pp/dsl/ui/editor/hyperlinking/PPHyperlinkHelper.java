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
package org.cloudsmith.geppetto.pp.dsl.ui.editor.hyperlinking;

import java.util.List;

import org.cloudsmith.geppetto.pp.dsl.linking.PPObjectAtOffsetHelper;
import org.cloudsmith.geppetto.pp.dsl.ui.labeling.PPDescriptionLabelProvider;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.jface.text.Region;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.hyperlinking.HyperlinkHelper;
import org.eclipse.xtext.ui.editor.hyperlinking.IHyperlinkAcceptor;
import org.eclipse.xtext.ui.editor.hyperlinking.XtextHyperlink;
import org.eclipse.xtext.util.TextRegion;

import com.google.inject.Inject;

/**
 * A PP specific hyperlink helper, that can find references using CrossReferenceAdapter instead of / in addition to
 * eReferences.
 * 
 */
public class PPHyperlinkHelper extends HyperlinkHelper {

	@Inject
	PPObjectAtOffsetHelper ppObjectAtOffsetHelper;

	@Inject
	private PPDescriptionLabelProvider labelProvider;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.ui.editor.hyperlinking.HyperlinkHelper#createHyperlinksByOffset(org.eclipse.xtext.resource.XtextResource, int,
	 * org.eclipse.xtext.ui.editor.hyperlinking.IHyperlinkAcceptor)
	 */
	@Override
	public void createHyperlinksByOffset(XtextResource resource, int offset, IHyperlinkAcceptor acceptor) {
		super.createHyperlinksByOffset(resource, offset, acceptor);

		INode crossRefNode = ppObjectAtOffsetHelper.getCrossReferenceNode(resource, new TextRegion(offset, 0));
		if(crossRefNode == null)
			return;
		List<IEObjectDescription> crossLinkedObjects = ppObjectAtOffsetHelper.getCrossReferencedElement(crossRefNode);
		Region region = new Region(crossRefNode.getOffset(), crossRefNode.getLength());
		if(crossLinkedObjects != null)
			for(IEObjectDescription iod : crossLinkedObjects)
				createHyperlinksTo(resource, region, iod, acceptor);

	}

	public void createHyperlinksTo(XtextResource from, Region region, IEObjectDescription to,
			IHyperlinkAcceptor acceptor) {
		final URIConverter uriConverter = from.getResourceSet().getURIConverter();
		final String hyperlinkText = labelProvider.getText(to);
		final URI uri = to.getEObjectURI(); // EcoreUtil.getURI(to);
		final URI normalized = uriConverter.normalize(uri);

		XtextHyperlink result = getHyperlinkProvider().get();
		result.setHyperlinkRegion(region);
		result.setURI(normalized);
		result.setHyperlinkText(hyperlinkText);
		acceptor.accept(result);
	}

}
