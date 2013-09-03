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
package com.puppetlabs.geppetto.pp.dsl.ui.internal;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.runtime.FileLocator;
import org.eclipse.core.runtime.Path;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.resource.ImageRegistry;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.xtext.ui.editor.XtextEditor;

/**
 * Helper for some of the icons.
 * 
 */
public class PPPluginImages {

	private static ImageRegistry PLUGIN_REGISTRY;

	public final static String ICONS_PATH = "icons/"; //$NON-NLS-1$

	/**
	 * Set of predefined Image Descriptors.
	 */

	private static final String PATH_LCL = ICONS_PATH + "elcl16/"; //$NON-NLS-1$

	private static final String PATH_LCL_DISABLED = ICONS_PATH + "dlcl16/"; //$NON-NLS-1$

	/**
	 * LCL
	 */
	public static final ImageDescriptor DESC_LINK_WITH_EDITOR = create(PATH_LCL, "synced.gif"); //$NON-NLS-1$

	public static final ImageDescriptor DESC_ALPHAB_SORT_CO = create(PATH_LCL, "alphab_sort_co.gif"); //$NON-NLS-1$

	public static final ImageDescriptor DESC_LINK_WITH_EDITOR_DISABLED = create(PATH_LCL_DISABLED, "synced.gif"); //$NON-NLS-1$

	public static final ImageDescriptor DESC_ALPHAB_SORT_CO_DISABLED = create(PATH_LCL_DISABLED, "alphab_sort_co.gif"); //$NON-NLS-1$

	public static final ImageDescriptor DESC_SEARCH_PREVIOUS = create(PATH_LCL, "prev_nav.gif");

	public static final ImageDescriptor DESC_SEARCH_NEXT = create(PATH_LCL, "next_nav.gif");

	public static final ImageDescriptor DESC_EXPAND_ALL = create(PATH_LCL, "expandall.gif");

	public static final ImageDescriptor DESC_COLLAPSE_ALL = create(PATH_LCL, "collapseall.gif");

	public static final ImageDescriptor DESC_OPEN_DECLARATION = create(PATH_LCL, "goto_input.gif");

	public static final ImageDescriptor DESC_OPEN_DECLARATION_DISABLED = create(PATH_LCL_DISABLED, "goto_input.gif");

	public static final ImageDescriptor DESC_MARK_OCCURRENCES = create(PATH_LCL, "mark_occurrences.gif");

	public static final ImageDescriptor DESC_MARK_OCCURRENCES_DISABLED = create(
		PATH_LCL_DISABLED, "mark_occurrences.gif");

	private static boolean imagesInitialized;

	private static final Map<String, Image> annotationImagesFixable = new HashMap<String, Image>();

	private static final Map<String, Image> annotationImagesNonFixable = new HashMap<String, Image>();

	private static final Map<String, Image> annotationImagesDeleted = new HashMap<String, Image>();

	private static ImageDescriptor create(String prefix, String name) {
		return ImageDescriptor.createFromURL(makeImageURL(prefix, name));
	}

	private static void ensureInitialized() {
		if(PLUGIN_REGISTRY == null)
			initialize();
	}

	public static Image get(String key) {
		ensureInitialized();
		return PLUGIN_REGISTRY.get(key);
	}

	public static Map<String, Image> getAnnotationImagesDeleted() {
		ensureInitialized();
		return annotationImagesDeleted;
	}

	public static Map<String, Image> getAnnotationImagesFixable() {
		ensureInitialized();
		return annotationImagesFixable;
	}

	public static Map<String, Image> getAnnotationImagesNonfixable() {
		ensureInitialized();
		return annotationImagesNonFixable;
	}

	/* package */
	private static final void initialize() {
		PLUGIN_REGISTRY = new ImageRegistry();

		initializeImageMaps();
	}

	private static final void initializeImageMaps() {
		if(imagesInitialized)
			return;

		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		Image error = sharedImages.getImage(ISharedImages.IMG_OBJS_ERROR_TSK);
		Image warning = sharedImages.getImage(ISharedImages.IMG_OBJS_WARN_TSK);
		annotationImagesNonFixable.put(XtextEditor.ERROR_ANNOTATION_TYPE, error);
		annotationImagesNonFixable.put(XtextEditor.WARNING_ANNOTATION_TYPE, warning);

		Display display = Display.getCurrent();
		annotationImagesDeleted.put(XtextEditor.ERROR_ANNOTATION_TYPE, new Image(display, error, SWT.IMAGE_GRAY));
		annotationImagesDeleted.put(XtextEditor.WARNING_ANNOTATION_TYPE, new Image(display, warning, SWT.IMAGE_GRAY));
	}

	private static URL makeImageURL(String prefix, String name) {
		String path = "$nl$/" + prefix + name; //$NON-NLS-1$
		return FileLocator.find(PPDSLActivator.getInstance().getBundle(), new Path(path), null);
	}

	public static Image manage(String key, ImageDescriptor desc) {
		Image image = desc.createImage();
		PLUGIN_REGISTRY.put(key, image);
		return image;
	}

}
