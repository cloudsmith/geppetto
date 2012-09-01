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
package org.cloudsmith.geppetto.pp.dsl.ui.editor.hover;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;

import org.cloudsmith.geppetto.pp.AttributeOperation;
import org.cloudsmith.geppetto.pp.LiteralNameOrReference;
import org.cloudsmith.geppetto.pp.PPPackage;
import org.cloudsmith.geppetto.pp.VariableExpression;
import org.cloudsmith.geppetto.pp.dsl.ui.internal.PPDSLActivator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.ui.editor.hover.html.DefaultEObjectHoverProvider;
import org.eclipse.xtext.util.Files;
import org.eclipse.xtext.util.PolymorphicDispatcher;

import com.google.inject.Inject;

/**
 * @author henrik
 * 
 */
public class PPHoverProvider extends DefaultEObjectHoverProvider {

	private PolymorphicDispatcher<Boolean> hoverDispatcher = new PolymorphicDispatcher<Boolean>(
		"_hover", 1, 1, Collections.singletonList(this), PolymorphicDispatcher.NullErrorHandler.<Boolean> get()) {
		@Override
		protected Boolean handleNoSuchMethod(Object... params) {
			return false;
		}
	};

	private static final String styleSheetFileName = "/css/PPHoverStyleSheet.css";

	@Inject
	private PPDocumentationProvider documentationProvider;

	protected Boolean _hover(AttributeOperation o) {
		return true;
	}

	protected Boolean _hover(LiteralNameOrReference o) {
		EStructuralFeature feature = o.eContainingFeature();
		if(feature == PPPackage.Literals.RESOURCE_EXPRESSION__RESOURCE_EXPR)
			return true;
		// System.err.println("feature " + feature.getName());
		return false;
	}

	protected Boolean _hover(VariableExpression o) {
		return true;
	}

	@Override
	protected String getFirstLine(EObject o) {
		StringBuilder builder = new StringBuilder();
		Image image = documentationProvider.getImage(o);
		if(image != null) {
			URL imageURL = PPDSLActivator.getDefault().getImagesOnFSRegistry().getImageURL(
				ImageDescriptor.createFromImage(image));
			builder.append("<IMG src=\"").append(imageURL.toExternalForm()).append("\"/>");
		}
		builder.append("<b>").append(getLabel(o)).append("</b>");
		String label = documentationProvider.getText(o);
		if(label != null)
			builder.append(" <span class='target'>- ").append(documentationProvider.getText(o)).append("</span>");
		return builder.toString();
	}

	@Override
	protected boolean hasHover(EObject o) {
		// System.out.println("Hover query for: " + o.getClass()); // For debugging, finding what to react to
		return hoverDispatcher.invoke(o) || super.hasHover(o);
	}

	@Override
	protected String loadStyleSheet() {
		URL styleSheetURL = PPDSLActivator.getInstance().getBundle().getEntry(styleSheetFileName);
		if(styleSheetURL != null)
			try {
				return Files.readStreamIntoString(styleSheetURL.openStream());
			}
			catch(IOException e) {
				// ignore
			}
		return super.loadStyleSheet();
	}
}
