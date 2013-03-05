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
package org.cloudsmith.geppetto.forge.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import org.cloudsmith.geppetto.common.os.FileUtils;
import org.cloudsmith.geppetto.forge.Cache;
import org.cloudsmith.geppetto.forge.Forge;
import org.cloudsmith.geppetto.forge.ForgePackage;
import org.cloudsmith.geppetto.forge.ForgeService;
import org.cloudsmith.geppetto.forge.HttpMethod;
import org.cloudsmith.geppetto.forge.IncompleteException;
import org.cloudsmith.geppetto.forge.Metadata;
import org.cloudsmith.geppetto.forge.ModuleInfo;
import org.cloudsmith.geppetto.forge.ReleaseInfo;
import org.cloudsmith.geppetto.forge.Repository;
import org.cloudsmith.geppetto.forge.util.TarUtils;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Forge</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.forge.impl.ForgeImpl#getVersion <em>Version</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.impl.ForgeImpl#getRepository <em>Repository</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.impl.ForgeImpl#getCache <em>Cache</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.impl.ForgeImpl#getService <em>Service</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ForgeImpl extends EObjectImpl implements Forge {
	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;

	private static void installTemplate(Metadata metadata, File destinationBase, File template, int templateBaseLength,
			FileFilter exclusionFilter) throws IOException {

		if(!exclusionFilter.accept(template))
			return;

		String tempRelative = template.getAbsolutePath().substring(templateBaseLength);
		if(template.isDirectory()) {
			File destination = new File(destinationBase, tempRelative);
			if(!destination.mkdir())
				throw new IOException(destination + " could not be created");
			for(File path : template.listFiles())
				installTemplate(metadata, destinationBase, path, templateBaseLength, exclusionFilter);
			return;
		}
		if(tempRelative.endsWith(".erb")) {
			tempRelative = tempRelative.substring(0, tempRelative.length() - 4);
			ERB erb = new ERB(metadata, template);
			erb.generate(new File(destinationBase, tempRelative));
			return;
		}
		File destFile = new File(destinationBase, tempRelative);
		FileUtils.cp(template, destFile.getParentFile(), destFile.getName());
	}

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected String version = VERSION_EDEFAULT;

	/**
	 * The cached value of the '{@link #getRepository() <em>Repository</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getRepository()
	 * @generated
	 * @ordered
	 */
	protected Repository repository;

	/**
	 * The cached value of the '{@link #getCache() <em>Cache</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getCache()
	 * @generated
	 * @ordered
	 */
	protected Cache cache;

	/**
	 * The cached value of the '{@link #getService() <em>Service</em>}' reference.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getService()
	 * @generated
	 * @ordered
	 */
	protected ForgeService service;

	private static final Type listOfModuleInfoType = new TypeToken<List<ModuleInfoImpl>>() {
	}.getType();

	private static final Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected ForgeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Metadata build(File moduleSource, File destination, FileFilter exclusionFilter) throws IOException,
			IncompleteException {
		if(exclusionFilter == null)
			exclusionFilter = MetadataImpl.DEFAULT_FILE_FILTER;

		Metadata md = getService().loadModule(moduleSource, exclusionFilter);
		String fullName = md.getFullName();
		if(fullName == null)
			throw new IncompleteException("A full name (user-module) must be specified in the Modulefile");

		String ver = md.getVersion();
		if(ver == null)
			throw new IncompleteException("version must be specified in the Modulefile");

		for(File tst = destination; tst != null; tst = tst.getParentFile())
			if(tst.equals(moduleSource))
				throw new IllegalArgumentException("Destination cannot reside within the module itself");

		String fullNameWithVersion = fullName + '-' + ver;
		md.saveJSONMetadata(new File(moduleSource, "metadata.json"));

		File moduleArchive = new File(destination, fullNameWithVersion + ".tar.gz");
		OutputStream out = new GZIPOutputStream(new FileOutputStream(moduleArchive));
		// Pack closes its output
		TarUtils.pack(moduleSource, out, exclusionFilter, false, fullNameWithVersion);
		return md;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public List<File> changes(File path, FileFilter exclusionFilter) throws IOException {
		if(exclusionFilter == null)
			exclusionFilter = MetadataImpl.DEFAULT_FILE_FILTER;

		MetadataImpl md = (MetadataImpl) getService().loadJSONMetadata(new File(path, "metadata.json"));
		List<File> result = new ArrayList<File>();
		md.appendChangedFiles(path, result, exclusionFilter);
		return result;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch(featureID) {
			case ForgePackage.FORGE__VERSION:
				return getVersion();
			case ForgePackage.FORGE__REPOSITORY:
				return getRepository();
			case ForgePackage.FORGE__CACHE:
				return getCache();
			case ForgePackage.FORGE__SERVICE:
				return getService();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch(featureID) {
			case ForgePackage.FORGE__VERSION:
				return VERSION_EDEFAULT == null
						? version != null
						: !VERSION_EDEFAULT.equals(version);
			case ForgePackage.FORGE__REPOSITORY:
				return repository != null;
			case ForgePackage.FORGE__CACHE:
				return cache != null;
			case ForgePackage.FORGE__SERVICE:
				return service != null;
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ForgePackage.Literals.FORGE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public void generate(File destination, Metadata metadata) throws IOException {
		if(!(destination.mkdirs() || destination.exists()))
			throw new IOException(destination + " could not be created");

		File templatesDir = FileUtils.getFileFromClassResource(Forge.class, "/templates");
		if(templatesDir == null || !templatesDir.isDirectory())
			throw new FileNotFoundException("Unable to find templates directory in resources");

		File skeleton = new File(templatesDir, "generator");
		int baseLength = skeleton.getAbsolutePath().length() + 1;
		for(File path : skeleton.listFiles()) {
			installTemplate(metadata, destination, path, baseLength, MetadataImpl.DEFAULT_FILE_FILTER);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Cache getCache() {
		return cache;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public ReleaseInfo getRelease(String fullName) throws IOException {
		String[] names = ForgeServiceImpl.userAndModuleFrom(fullName);
		String user = URLEncoder.encode(names[0], "UTF-8");
		String name = URLEncoder.encode(names[1], "UTF-8");
		HttpURLConnection conn = repository.connect(HttpMethod.GET, "users/" + user + "/modules/" + name +
				"/releases/find.json");
		Reader input = new BufferedReader(
			new InputStreamReader(conn.getInputStream(), RepositoryImpl.getEncoding(conn)));
		try {
			return gson.fromJson(input, ReleaseInfoImpl.class);
		}
		finally {
			input.close();
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Repository getRepository() {
		return repository;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public ForgeService getService() {
		return service;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Metadata install(String fullName, File path, boolean pathIncludesTopFolder, boolean force)
			throws IOException {
		// Ensure that the full name is in the right form, using '-' instead of '/' as
		// the separator between user and module
		String[] sp = ForgeServiceImpl.userAndModuleFrom(fullName);
		fullName = sp[0] + '-' + sp[1];
		ReleaseInfo release = getRelease(fullName);
		if(release == null)
			throw new FileNotFoundException("No releases found for module '" + fullName + '\'');

		if(!pathIncludesTopFolder)
			// Use module name as the default
			path = new File(path, sp[1]);
		if(path.exists()) {
			if(!force)
				throw new IOException("Destination folder is not empty: " + path.getAbsolutePath());

			// Don't remove .project, .settings, .git, .svn, etc. if they are present.
			FileUtils.rmR(path, FileUtils.DEFAULT_EXCLUDES);
		}

		// Unpack closes its input.
		File moduleFile = getCache().retrieve(release.getFile());
		TarUtils.unpack(new GZIPInputStream(new FileInputStream(moduleFile)), path, true, null);
		return service.loadJSONMetadata(new File(path, "metadata.json"));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public List<ModuleInfo> search(String term) throws IOException {
		String urlSuffix = "modules.json";
		if(term != null && term.length() > 0)
			urlSuffix += "?q=" + URLEncoder.encode(term, "UTF-8");
		HttpURLConnection conn = repository.connect(HttpMethod.GET, urlSuffix);
		Reader input = new BufferedReader(
			new InputStreamReader(conn.getInputStream(), RepositoryImpl.getEncoding(conn)));
		try {
			return gson.fromJson(input, listOfModuleInfoType);
		}
		finally {
			input.close();
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if(eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (version: ");
		result.append(version);
		result.append(')');
		return result.toString();
	}

} // ForgeImpl
