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
package org.cloudsmith.geppetto.pp.dsl.linking;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IReferenceDescription;

/**
 * Describes a reference from a source in a container to a target.
 * 
 */
public class PPReferenceDescription implements IReferenceDescription {

	/**
	 * Produces a PPReferenceDescription
	 * 
	 * @param sourceReference
	 *            - URI to the soure
	 * @param sourceContainer
	 *            - closest exported container of source
	 * @param targetDescriptor
	 *            - the target descriptor
	 */
	public static PPReferenceDescription create(URI sourceReference, IEObjectDescription sourceContainer,
			IEObjectDescription target) {
		return new PPReferenceDescription(sourceReference, sourceContainer, target);

	}

	/**
	 * The closes exported container (or the manifest)
	 */
	private IEObjectDescription sourceContainer;

	/**
	 * A reference to the model object where the reference is made.
	 */
	private URI sourceReference;

	/**
	 * The reference to the target.
	 */
	private IEObjectDescription targetReference;

	public PPReferenceDescription(URI sourceReference, IEObjectDescription sourceContainer,
			IEObjectDescription targetReference) {
		this.sourceReference = sourceReference;
		this.sourceContainer = sourceContainer;
		this.targetReference = targetReference;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.resource.IReferenceDescription#getContainerEObjectURI()
	 */
	@Override
	public URI getContainerEObjectURI() {
		if(sourceContainer != null)
			return sourceContainer.getEObjectURI();
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.resource.IReferenceDescription#getEReference()
	 */
	@Override
	public EReference getEReference() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.resource.IReferenceDescription#getIndexInList()
	 */
	@Override
	public int getIndexInList() {
		// TODO Auto-generated method stub
		return -1;
	}

	/**
	 * @return the container
	 */
	public IEObjectDescription getSourceContainer(int foo) {
		return sourceContainer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.resource.IReferenceDescription#getSourceEObjectUri()
	 */
	@Override
	public URI getSourceEObjectUri() {
		return sourceReference;
	}

	/**
	 * @return the sourceReference
	 */
	public URI getSourceReference() {
		return sourceReference;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.resource.IReferenceDescription#getTargetEObjectUri()
	 */
	@Override
	public URI getTargetEObjectUri() {
		return targetReference.getEObjectURI();
	}

	/**
	 * @return the target reference
	 */
	public IEObjectDescription getTargetReference(int foo) {
		return targetReference;
	}
}
