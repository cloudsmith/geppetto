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
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.net.URI;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.cloudsmith.geppetto.common.os.StreamUtil;
import org.cloudsmith.geppetto.forge.Cache;
import org.cloudsmith.geppetto.forge.Forge;
import org.cloudsmith.geppetto.forge.ForgeFactory;
import org.cloudsmith.geppetto.forge.ForgePackage;
import org.cloudsmith.geppetto.forge.ForgeService;
import org.cloudsmith.geppetto.forge.Metadata;
import org.cloudsmith.geppetto.forge.Repository;
import org.cloudsmith.geppetto.forge.util.JsonUtils;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.impl.EObjectImpl;

import com.google.gson.Gson;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Service</b></em>'. <!-- end-user-doc -->
 * <p>
 * </p>
 * 
 * @generated
 */
public class ForgeServiceImpl extends EObjectImpl implements ForgeService {

	private static final Pattern FULL_NAME_PATTERN = Pattern.compile("\\A([^-\\/|.]+)[-|\\/](.+)\\z");

	static String[] userAndModuleFrom(String fullName) throws IllegalArgumentException {
		Matcher m = FULL_NAME_PATTERN.matcher(fullName);
		if(!m.matches())
			throw new IllegalArgumentException("Not a valid full name: " + fullName);
		return new String[] { m.group(1), m.group(2) };
	}

	/**
	 * @generated
	 */
	protected ForgeServiceImpl() {
		super();
	}

	/**
	 * @generated NOT
	 */
	@Override
	public Cache createCache(File cacheDirectory, Repository repository) {
		CacheImpl cache = (CacheImpl) ForgeFactory.eINSTANCE.createCache();
		cache.location = cacheDirectory;
		cache.repository = repository;
		return cache;
	}

	/**
	 * @generated NOT
	 */
	@Override
	public Forge createForge(Repository repository, Cache cache) {
		ForgeImpl forge = (ForgeImpl) ForgeFactory.eINSTANCE.createForge();
		forge.repository = repository;
		forge.cache = cache;
		forge.version = "1.0";
		forge.service = this;
		return forge;
	}

	/**
	 * @generated NOT
	 */
	@Override
	public Forge createForge(URI repository) {
		return createForge(repository, null);
	}

	/**
	 * @generated NOT
	 */
	@Override
	public Forge createForge(URI uri, File cacheDirectory) {
		Repository repository = createRepository(uri);
		return createForge(repository, createCache(cacheDirectory, repository));
	}

	/**
	 * @generated NOT
	 */
	@Override
	public Metadata createMetadata(String fullName) {
		MetadataImpl metadata = (MetadataImpl) ForgeFactory.eINSTANCE.createMetadata();
		metadata.setFullName(fullName);
		return metadata;
	}

	/**
	 * @generated NOT
	 */
	@Override
	public Repository createRepository(URI uri) {
		RepositoryImpl repository = (RepositoryImpl) ForgeFactory.eINSTANCE.createRepository();
		repository.repository = uri;
		return repository;
	}

	/**
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ForgePackage.Literals.FORGE_SERVICE;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Metadata loadJSONMetadata(File jsonFile) throws IOException {
		Reader reader = new BufferedReader(new FileReader(jsonFile));
		try {
			Gson gson = JsonUtils.getGSon();
			MetadataImpl md = gson.fromJson(reader, MetadataImpl.class);
			md.location = jsonFile.getParentFile();
			return md;
		}
		finally {
			StreamUtil.close(reader);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public Metadata loadModule(File moduleDirectory, FileFilter exclusionFilter) throws IOException {
		if(exclusionFilter == null)
			exclusionFilter = MetadataImpl.DEFAULT_FILE_FILTER;
		MetadataImpl metadata = (MetadataImpl) ForgeFactory.eINSTANCE.createMetadata();
		metadata.populateFromModuleDir(moduleDirectory, exclusionFilter);
		return metadata;
	}
} // ForgeServiceImpl
