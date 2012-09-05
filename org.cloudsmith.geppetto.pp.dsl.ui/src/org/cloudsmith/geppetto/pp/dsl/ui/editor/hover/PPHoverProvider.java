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
package org.cloudsmith.geppetto.pp.dsl.ui.editor.hover;

import java.io.IOException;
import java.net.URL;
import java.util.Collections;
import java.util.List;

import org.cloudsmith.geppetto.pp.AssignmentExpression;
import org.cloudsmith.geppetto.pp.AttributeOperation;
import org.cloudsmith.geppetto.pp.Definition;
import org.cloudsmith.geppetto.pp.ExpressionTE;
import org.cloudsmith.geppetto.pp.LiteralNameOrReference;
import org.cloudsmith.geppetto.pp.NodeDefinition;
import org.cloudsmith.geppetto.pp.PPPackage;
import org.cloudsmith.geppetto.pp.VariableExpression;
import org.cloudsmith.geppetto.pp.VariableTE;
import org.cloudsmith.geppetto.pp.VerbatimTE;
import org.cloudsmith.geppetto.pp.dsl.adapters.CrossReferenceAdapter;
import org.cloudsmith.geppetto.pp.dsl.ui.internal.PPDSLActivator;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.URIConverter;
import org.eclipse.jface.action.ToolBarManager;
import org.eclipse.jface.internal.text.html.BrowserInformationControl;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.text.AbstractReusableInformationControlCreator;
import org.eclipse.jface.text.DefaultInformationControl;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.IInputChangedListener;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.ui.editor.hover.html.DefaultEObjectHoverProvider;
import org.eclipse.xtext.ui.editor.hover.html.IXtextBrowserInformationControl;
import org.eclipse.xtext.ui.editor.hover.html.XtextBrowserInformationControl;
import org.eclipse.xtext.ui.editor.hover.html.XtextBrowserInformationControlInput;
import org.eclipse.xtext.util.Files;
import org.eclipse.xtext.util.PolymorphicDispatcher;

import com.google.inject.Inject;

/**
 * Provides Hover information - for what should there be a hover, and what (the actual content is
 * produce by {@link PPDocumentationProvider} (labels, documentation, and images).
 * 
 */
public class PPHoverProvider extends DefaultEObjectHoverProvider {

	/**
	 * Basically a copy of the class with the same name in DefaultEObjectHoverProvider.
	 * A complete copy required since it is not possible to access the openDeclarationAction
	 * instance and siable it from the outside.
	 * 
	 */
	public class PresenterControlCreator extends AbstractReusableInformationControlCreator {

		/**
		 * @since 2.3
		 */
		protected void configureControl(final IXtextBrowserInformationControl control, ToolBarManager tbm, String font) {
			final BackAction backAction = new BackAction(control);
			backAction.setEnabled(false);
			tbm.add(backAction);
			final ForwardAction forwardAction = new ForwardAction(control);
			tbm.add(forwardAction);
			forwardAction.setEnabled(false);

			final OpenDeclarationAction openDeclarationAction = new OpenDeclarationAction(control);
			tbm.add(openDeclarationAction);

			IInputChangedListener inputChangeListener = new IInputChangedListener() {
				public void inputChanged(Object newInput) {
					backAction.update();
					forwardAction.update();

					if(newInput == null) {
					}
					else if(newInput instanceof XtextBrowserInformationControlInput) {
						URI uri = createURI(((XtextBrowserInformationControlInput) newInput).getElement());
						openDeclarationAction.setEnabled(uri != null);
					}
				}
			};
			control.addInputChangeListener(inputChangeListener);
			tbm.update(true);
			addLinkListener(control);
		}

		@Override
		public IInformationControl doCreateInformationControl(Shell parent) {
			if(BrowserInformationControl.isAvailable(parent)) {
				ToolBarManager tbm = new ToolBarManager(SWT.FLAT);
				String font = "org.eclipse.jdt.ui.javadocfont"; // FIXME: mPreferenceConstants.APPEARANCE_JAVADOC_FONT;
				IXtextBrowserInformationControl control = new XtextBrowserInformationControl(parent, font, tbm);
				configureControl(control, tbm, font);
				return control;
			}
			// else {
			return new DefaultInformationControl(parent, true);
			// }
		}
	}

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

	private PresenterControlCreator presenterControlCreator;

	protected Boolean _hover(AttributeOperation o) {
		return true;
	}

	protected Boolean _hover(Definition o) {
		return true;
	}

	protected Boolean _hover(EObject o) {
		List<IEObjectDescription> xrefs = CrossReferenceAdapter.get(o);
		if(xrefs != null && xrefs.size() > 0)
			return true;
		return false;
	}

	protected Boolean _hover(LiteralNameOrReference o) {
		EStructuralFeature feature = o.eContainingFeature();
		if(feature == PPPackage.Literals.RESOURCE_EXPRESSION__RESOURCE_EXPR)
			return true;
		if(feature == PPPackage.Literals.PARENTHESISED_EXPRESSION__EXPR) {
			if(o.eContainer().eContainer() instanceof ExpressionTE)
				return true;
		}
		List<IEObjectDescription> xrefs = CrossReferenceAdapter.get(o);
		if(xrefs != null && xrefs.size() > 0)
			return true;
		return false;
	}

	protected Boolean _hover(NodeDefinition o) {
		return true;
	}

	protected Boolean _hover(VariableExpression o) {
		if(o.eContainer() instanceof AssignmentExpression &&
				o.eContainingFeature() == PPPackage.Literals.BINARY_EXPRESSION__LEFT_EXPR)
			return false;
		return true;
	}

	protected Boolean _hover(VariableTE o) {
		return true;
	}

	protected Boolean _hover(VerbatimTE o) {
		return hasHover(o.eContainer());
	}

	/**
	 * Overrides the default implementation which does not seem to link to something somewhere else than in
	 * the same file. Also, does not handle hovers without a target very well.
	 * 
	 * @see org.eclipse.xtext.ui.editor.hover.html.DefaultEObjectHoverProvider#createURI(org.eclipse.emf.ecore.EObject)
	 */
	@Override
	protected org.eclipse.emf.common.util.URI createURI(EObject o) {
		IEObjectDescription target = documentationProvider.xref(o);
		if(target == null)
			return null; // should disable the link as well

		final URIConverter uriConverter = o.eResource().getResourceSet().getURIConverter();
		final URI uri = target.getEObjectURI();
		final URI normalized = uriConverter.normalize(uri);
		return normalized;
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

	/**
	 * Overrides the default implementation simply because the PPPresenterControlCreator always enables
	 * the OpenDeclaration link.
	 * 
	 * @see org.eclipse.xtext.ui.editor.hover.html.DefaultEObjectHoverProvider#getInformationPresenterControlCreator()
	 */
	@Override
	public IInformationControlCreator getInformationPresenterControlCreator() {
		if(presenterControlCreator == null)
			presenterControlCreator = new PresenterControlCreator(); // specialisation
		return presenterControlCreator;
	}

	@Override
	protected boolean hasHover(EObject o) {
		Boolean result = hoverDispatcher.invoke(o);
		// TODO: make this a trace setting instead...
		// System.out.println("Hover query (" + result + ") for: " + o.getClass()); // For debugging, finding what to react to
		return result;
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
