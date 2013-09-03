/*******************************************************************************
 * Copyright (c) 2008, 2012 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *     Puppet Labs Inc - adapted to Geppetto (no JDT)
 *******************************************************************************/
package org.cloudsmith.geppetto.pp.dsl.ui.jdt_ersatz;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.cloudsmith.geppetto.pp.dsl.ui.internal.PPDSLActivator;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.graphics.ImageLoader;

//import org.eclipse.jdt.core.IJavaElement;

//import org.eclipse.jdt.internal.ui.JavaPlugin;

/**
 * Image registry that keeps its images on the local file system.
 * 
 * @since 3.4
 */
public class ImagesOnFileSystemRegistry {

	private static final String IMAGE_DIR = "pp-images"; //$NON-NLS-1$

	private final static Logger LOG = Logger.getLogger(ImagesOnFileSystemRegistry.class);

	private HashMap<ImageDescriptor, URL> fURLMap;

	private final File fTempDir;

	// private final JavaElementImageProvider fImageProvider;
	private int fImageCount;

	public ImagesOnFileSystemRegistry() {
		fURLMap = new HashMap<ImageDescriptor, URL>();
		fTempDir = getTempDir();
		// fImageProvider= new JavaElementImageProvider();
		fImageCount = 0;
	}

	private void delete(File file) {
		if(file.isDirectory()) {
			File[] listFiles = file.listFiles();
			for(int i = 0; i < listFiles.length; i++) {
				delete(listFiles[i]);
			}
		}
		file.delete();
	}

	public void dispose() {
		if(fTempDir != null) {
			delete(fTempDir);
		}
		fURLMap = null;
	}

	private synchronized int getImageCount() {
		return fImageCount++;
	}

	public URL getImageURL(ImageDescriptor descriptor) {
		if(fTempDir == null)
			return null;

		URL url = fURLMap.get(descriptor);
		if(url != null)
			return url;

		File imageFile = getNewFile();
		ImageData imageData = descriptor.getImageData();
		if(imageData == null) {
			return null;
		}

		ImageLoader loader = new ImageLoader();
		loader.data = new ImageData[] { imageData };
		loader.save(imageFile.getAbsolutePath(), SWT.IMAGE_PNG);

		try {
			url = imageFile.toURI().toURL();
			fURLMap.put(descriptor, url);
			return url;
		}
		catch(MalformedURLException e) {
			LOG.error(e);
		}
		return null;
	}

	private File getNewFile() {
		File file;
		do {
			file = new File(fTempDir, String.valueOf(getImageCount()) + ".png"); //$NON-NLS-1$
		} while(file.exists());
		return file;
	}

	private File getTempDir() {
		try {
			File imageDir = PPDSLActivator.getInstance().getStateLocation().append(IMAGE_DIR).toFile();
			if(imageDir.exists()) {
				// has not been deleted on previous shutdown
				delete(imageDir);
			}
			if(!imageDir.exists()) {
				imageDir.mkdir();
			}
			if(!imageDir.isDirectory()) {
				LOG.error("Failed to create image directory " + imageDir.toString()); //$NON-NLS-1$
				return null;
			}
			return imageDir;
		}
		catch(IllegalStateException e) {
			// no state location
			return null;
		}
	}
}
