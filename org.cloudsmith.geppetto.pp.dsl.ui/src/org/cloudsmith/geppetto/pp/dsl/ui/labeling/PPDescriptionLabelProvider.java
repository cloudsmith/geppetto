/**
 * Copyright (c) 2011, 2012 Cloudsmith, Inc. and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.pp.dsl.ui.labeling;

import java.util.Iterator;

import org.cloudsmith.geppetto.pp.PPPackage;
import org.cloudsmith.geppetto.pp.dsl.linking.PPQualifiedNameConverter;
import org.cloudsmith.geppetto.pp.pptp.PPTPPackage;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IStorage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IReferenceDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.ui.label.DeclarativeLabelProvider;
import org.eclipse.xtext.ui.label.DefaultEditorImageUtil;
import org.eclipse.xtext.ui.resource.IStorage2UriMapper;
import org.eclipse.xtext.util.Pair;

import com.google.inject.Inject;

/**
 * Provides labels for a IEObjectDescriptions and IResourceDescriptions.
 * 
 * see http://www.eclipse.org/Xtext/documentation/latest/xtext.html#labelProvider
 */
public class PPDescriptionLabelProvider extends DeclarativeLabelProvider implements IIconNames {
	@Inject
	PPQualifiedNameConverter nameConverter;

	@Inject
	private IStorage2UriMapper storage2UriMapper;

	@Inject
	private DefaultEditorImageUtil imageUtil;

	public String getClassLabel(EClass clazz) {
		if(clazz.getEPackage() == PPTPPackage.eINSTANCE) {
			switch(clazz.getClassifierID()) {
				case PPTPPackage.TYPE:
					return "Type";
				case PPTPPackage.TP_VARIABLE:
					return "Variable";
				case PPTPPackage.PARAMETER:
					return "Parameter";
				case PPTPPackage.PROPERTY:
					return "Property";
				case PPTPPackage.FUNCTION:
					return "Function";
			}
			// Should not really ever be displayed
			return "PPTP-class(" + clazz.getName() + ")";
		}
		else if(clazz.getEPackage() == PPPackage.eINSTANCE) {
			switch(clazz.getClassifierID()) {
				case PPPackage.HOST_CLASS_DEFINITION:
					return "Class";
				case PPPackage.DEFINITION:
					return "Definition";
				case PPPackage.VARIABLE_EXPRESSION:
					return "Variable";
				case PPPackage.DEFINITION_ARGUMENT:
					return "Parameter";
				default:
					// Should not really ever be displayed
					return "PP-class (" + clazz.getName() + ")";
			}
		}
		return clazz.getName();
	}

	public Object image(IEObjectDescription element) {
		if(element.getEClass().getEPackage() == PPTPPackage.eINSTANCE) {
			return pptpImage(element);
		}
		else if(element.getEClass().getEPackage() == PPPackage.eINSTANCE) {
			return ppImage(element);
		}
		return element.getQualifiedName() + " - " + element.getEClass().getName();
	}

	/**
	 * This method is only invoked if the containerEObjectURI of the {@link IReferenceDescription} is null, i.e. the
	 * reference is owned by an element without any indexed container.
	 * 
	 * @since 2.1
	 */
	public Object image(IReferenceDescription element) {
		URI uri = element.getSourceEObjectUri();
		if(uri != null) {
			String fileName = uri.lastSegment();
			return imageUtil.getDefaultEditorImageDescriptor(fileName);
		}
		return null;
	}

	protected Object ppImage(IEObjectDescription element) {
		switch(element.getEClass().getClassifierID()) {
			case PPPackage.DEFINITION:
				return DEFINITION;
			case PPPackage.HOST_CLASS_DEFINITION:
				return CLASS;
			case PPPackage.VARIABLE_EXPRESSION:
				return VARIABLE;
			case PPPackage.NODE_DEFINITION:
				return NODE;
			case PPPackage.DEFINITION_ARGUMENT:
				return PARAMETER;
		}
		return null;
	}

	protected Object pptpImage(IEObjectDescription element) {
		switch(element.getEClass().getClassifierID()) {
			case PPTPPackage.FUNCTION:
				return FUNCTION;
			case PPTPPackage.TYPE:
				return TYPE;
			case PPTPPackage.TP_VARIABLE:
				return VARIABLE;
			case PPTPPackage.PROPERTY:
			case PPTPPackage.PARAMETER:
				return PARAMETER;
		}
		return null;
	}

	public Object text(IEObjectDescription element) {

		EPackage epkg = element.getEClass().getEPackage();
		if(epkg == PPTPPackage.eINSTANCE || epkg == PPPackage.eINSTANCE) {
			StyledString bld = new StyledString(nameConverter.toString(element.getName()));
			bld.append(" : " + getClassLabel(element.getEClass()), StyledString.DECORATIONS_STYLER);
			return bld;
		}
		return super.text(element);
	}

	public Object text(IReferenceDescription element) {
		StyledString bld = new StyledString("::");
		bld.append("  Top Level Scope", StyledString.DECORATIONS_STYLER);
		return bld;
	}

	public Object text(IResourceDescription element) {
		Iterator<Pair<IStorage, IProject>> storages = storage2UriMapper.getStorages(element.getURI()).iterator();
		if(storages.hasNext()) {
			IStorage storage = storages.next().getFirst();
			return storage.getFullPath().toString();
		}
		return null;
	}

}
