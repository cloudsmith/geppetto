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

import java.util.Collections;
import java.util.List;

import org.cloudsmith.geppetto.pp.Definition;
import org.cloudsmith.geppetto.pp.HostClassDefinition;
import org.cloudsmith.geppetto.pp.NodeDefinition;
import org.cloudsmith.geppetto.pp.VerbatimTE;
import org.cloudsmith.geppetto.pp.dsl.adapters.CrossReferenceAdapter;
import org.cloudsmith.geppetto.pp.dsl.adapters.ResourceDocumentationAdapter;
import org.cloudsmith.geppetto.pp.dsl.adapters.ResourceDocumentationAdapterFactory;
import org.cloudsmith.geppetto.pp.dsl.formatting.PPCommentConfiguration;
import org.cloudsmith.geppetto.pp.dsl.services.PPGrammarAccess;
import org.cloudsmith.geppetto.pp.dsl.ui.labeling.PPDescriptionLabelProvider;
import org.cloudsmith.geppetto.pp.dsl.ui.labeling.PPLabelProvider;
import org.cloudsmith.geppetto.pp.pptp.IDocumented;
import org.cloudsmith.geppetto.ruby.RubyDocProcessor;
import org.cloudsmith.xtext.dommodel.formatter.comments.CommentProcessor;
import org.cloudsmith.xtext.dommodel.formatter.comments.CommentProcessor.CommentText;
import org.cloudsmith.xtext.dommodel.formatter.comments.ICommentConfiguration.CommentType;
import org.cloudsmith.xtext.dommodel.formatter.comments.ICommentContainerInformation;
import org.cloudsmith.xtext.textflow.CharSequences;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.swt.graphics.Image;
import org.eclipse.xtext.documentation.IEObjectDocumentationProvider;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.util.PolymorphicDispatcher;

import com.google.inject.Inject;

/**
 * Provider of documentation for PP semantic objects/references.
 * 
 */
public class PPDocumentationProvider implements IEObjectDocumentationProvider {

	private PolymorphicDispatcher<String> documentationDispatcher = new PolymorphicDispatcher<String>(
		"_document", 1, 2, Collections.singletonList(this), PolymorphicDispatcher.NullErrorHandler.<String> get()) {
		@Override
		protected String handleNoSuchMethod(Object... params) {
			return null;
		}
	};

	private PolymorphicDispatcher<Image> imageDispatcher = new PolymorphicDispatcher<Image>(
		"_image", 1, 2, Collections.singletonList(this), PolymorphicDispatcher.NullErrorHandler.<Image> get()) {
		@Override
		protected Image handleNoSuchMethod(Object... params) {
			return null;
		}
	};

	private PolymorphicDispatcher<String> labelDispatcher = new PolymorphicDispatcher<String>(
		"_label", 1, 2, Collections.singletonList(this), PolymorphicDispatcher.NullErrorHandler.<String> get()) {
		@Override
		protected String handleNoSuchMethod(Object... params) {
			return null;
		}
	};

	@Inject
	protected PPDescriptionLabelProvider descriptionLabelProvider;

	@Inject
	protected PPLabelProvider labelProvider;

	// protected String _document(AttributeOperation o) {
	// return getCrossReferenceDocumentation(o);
	// }

	@Inject
	PPCommentConfiguration commentConfiguration;

	@Inject
	PPGrammarAccess ga;

	protected String _document(Definition o) {
		return getPPDocumentation(o);
	}

	/**
	 * Get cross reference for all types of Expressions
	 * 
	 * @param o
	 * @return
	 */
	protected String _document(EObject o) {
		return getCrossReferenceDocumentation(o);
	}

	protected String _document(HostClassDefinition o) {
		return getPPDocumentation(o);
	}

	/**
	 * All PPTP things with documentation are instances of IDocumented
	 * 
	 * @param o
	 * @return
	 */
	protected String _document(IDocumented o) {
		// produces a string, get it as HTML documentation if not already in HTML
		return document(o.getDocumentation());
	}

	protected String _document(NodeDefinition o) {
		return getPPDocumentation(o);
	}

	protected String _document(Object o) {
		return null;
	}

	protected String _document(String o) {
		if(o.startsWith("<"))
			return o;
		// turn puppet internal doc format (from Ruby) into HTML
		StringBuilder builder = new StringBuilder();
		builder.append("<p>");
		builder.append(o);
		builder.append("</p>");
		return builder.toString();
	}

	protected String _document(VerbatimTE o) {
		// is only possible when it is in a string (eContainer()) that is a reference.
		return getCrossReferenceDocumentation(o.eContainer());
	}

	protected Image _image(EObject o) {
		Image result = getCrossReferenceImage(o);
		if(result == null)
			result = getPPImage(o);
		return result;

	}

	protected Image _image(VerbatimTE o) {
		// is only possible when it is in a string (eContainer()) that is a reference.
		return getCrossReferenceImage(o.eContainer());
	}

	protected String _label(EObject o) {
		return getCrossReferenceLabel(o);
	}

	protected String _label(VerbatimTE o) {
		// is only possible when it is in a string (eContainer()) that is a reference.
		return getCrossReferenceLabel(o.eContainer());
	}

	public String document(Object o) {
		if(o == null)
			return null;
		String result = documentationDispatcher.invoke(o);
		return result == null && o instanceof EObject
				? documentationDispatcher.invoke(((EObject) o).eContainingFeature(), o)
				: result;

	}

	private String getCrossReferenceDocumentation(EObject o) {
		List<IEObjectDescription> xrefs = CrossReferenceAdapter.get(o);
		if(xrefs == null)
			return null;
		if(xrefs.size() > 1) {
			return "Ambiguous reference";
		}
		if(xrefs.size() == 0) {
			return "Unresolved";
		}
		IEObjectDescription ref = xrefs.get(0);

		EObject x = ref.getEObjectOrProxy();
		if(x.eIsProxy())
			x = EcoreUtil.resolve(x, o);
		return document(x);

	}

	private Image getCrossReferenceImage(EObject o) {
		List<IEObjectDescription> xrefs = CrossReferenceAdapter.get(o);
		if(xrefs == null)
			return null;
		if(xrefs.size() > 1) {
			return null;
		}
		if(xrefs.size() == 0) {
			return null;
		}
		IEObjectDescription ref = xrefs.get(0);
		return descriptionLabelProvider.getImage(ref);
	}

	private String getCrossReferenceLabel(EObject o) {
		List<IEObjectDescription> xrefs = CrossReferenceAdapter.get(o);
		if(xrefs == null)
			return null;
		if(xrefs.size() > 1) {
			return null;
		}
		if(xrefs.size() == 0) {
			return null;
		}
		IEObjectDescription ref = xrefs.get(0);
		return descriptionLabelProvider.getText(ref);

	}

	/**
	 * Returns the documentation associated with the object referenced by the given EObject.
	 */
	@Override
	public String getDocumentation(EObject o) {
		return document(o);
	}

	/**
	 * Returns an image for the objct referenced by the given EObject
	 * 
	 * @param o
	 * @return
	 */
	public Image getImage(Object o) {
		if(o == null)
			return null;
		Image result = imageDispatcher.invoke(o);
		return result == null && o instanceof EObject
				? imageDispatcher.invoke(((EObject) o).eContainingFeature(), o)
				: result;

	}

	/**
	 * @param o
	 * @return
	 */
	private String getPPDocumentation(EObject o) {
		ResourceDocumentationAdapter adapter = ResourceDocumentationAdapterFactory.eINSTANCE.adapt(o.eResource());
		List<INode> nodes = adapter.get(o);
		if(nodes != null && nodes.size() > 0) {
			// rip text from the nodes
			CharSequence result = CharSequences.empty();
			for(INode n : nodes)
				result = CharSequences.concatenate(result, n.getText());

			ICommentContainerInformation in = (nodes.size() == 1 && nodes.get(0).getGrammarElement() == ga.getML_COMMENTRule())
					? commentConfiguration.getContainerInformation(CommentType.Multiline)
					: (commentConfiguration.getContainerInformation(CommentType.SingleLine));

			CommentProcessor cpr = new CommentProcessor();
			CommentText comment = cpr.separateCommentFromContainer(result, in, "\n"); // TODO: cheating on line separator
			return new RubyDocProcessor().asHTML(comment.getLines());

		}
		return null;
	}

	private Image getPPImage(EObject o) {
		return labelProvider.getImage(o);
	}

	/**
	 * Returns a text label for the object referenced by the given Object.
	 * 
	 * @param o
	 * @return
	 */
	public String getText(Object o) {
		if(o == null)
			return null;
		String result = labelDispatcher.invoke(o);
		return result == null && o instanceof EObject
				? labelDispatcher.invoke(((EObject) o).eContainingFeature(), o)
				: result;

	}

}
