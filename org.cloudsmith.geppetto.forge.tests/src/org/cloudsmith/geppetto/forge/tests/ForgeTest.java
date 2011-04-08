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
package org.cloudsmith.geppetto.forge.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.List;
import java.util.zip.GZIPInputStream;

import junit.framework.TestCase;
import junit.textui.TestRunner;

import org.cloudsmith.geppetto.common.os.FileUtils;
import org.cloudsmith.geppetto.common.os.StreamUtil;
import org.cloudsmith.geppetto.forge.Forge;
import org.cloudsmith.geppetto.forge.ForgeFactory;
import org.cloudsmith.geppetto.forge.ForgeService;
import org.cloudsmith.geppetto.forge.IncompleteException;
import org.cloudsmith.geppetto.forge.Metadata;
import org.cloudsmith.geppetto.forge.ModuleInfo;
import org.cloudsmith.geppetto.forge.ReleaseInfo;
import org.cloudsmith.geppetto.forge.util.TarUtils;

/**
 * <!-- begin-user-doc --> A test case for the model object ' <em><b>Forge</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following operations are tested:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.forge.Forge#build(java.io.File, java.io.File) <em>Build</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.Forge#changes(java.io.File) <em>Changes</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.Forge#generate(java.io.File, org.cloudsmith.geppetto.forge.Metadata) <em>Generate</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.Forge#install(java.lang.String, java.io.File, boolean, boolean) <em>Install</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.Forge#search(java.lang.String) <em>Search</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.Forge#getRelease(java.lang.String) <em>Get Release</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class ForgeTest extends TestCase {

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(ForgeTest.class);
	}

	/**
	 * The fixture for this Forge test case.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	protected Forge fixture = null;

	/**
	 * Constructs a new Forge test case with the given name.
	 * <!-- begin-user-doc
	 * --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public ForgeTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Forge test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected Forge getFixture() {
		return fixture;
	}

	/**
	 * Sets the fixture for this Forge test case.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	protected void setFixture(Forge fixture) {
		this.fixture = fixture;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see junit.framework.TestCase#setUp()
	 * @generated NOT
	 */
	@Override
	protected void setUp() throws Exception {
		ForgeService forgeService = ForgeFactory.eINSTANCE.createForgeService();
		setFixture(forgeService.createForge(
			URI.create("http://forge.puppetlabs.com"), ForgeTests.getTestOutputFolder("cache", false)));
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

	/**
	 * Tests the ' {@link org.cloudsmith.geppetto.forge.Forge#build(java.io.File, java.io.File)
	 * <em>Build</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.Forge#build(java.io.File, java.io.File)
	 * @generated NOT
	 */
	public void testBuild__File_File() {
		try {
			File installFolder = ForgeTests.getTestOutputFolder("apache-install-here", true);
			File resultFolder = ForgeTests.getTestOutputFolder("apache-build-result", true);
			FileUtils.cpR(
				Activator.getTestData("puppetlabs-apache"), installFolder, FileUtils.DEFAULT_EXCLUDES, false, true);
			Metadata md = fixture.build(installFolder, resultFolder);
			String archiveName = md.getFullName() + '-' + md.getVersion();
			File builtArchive = new File(resultFolder, archiveName + ".tar.gz");
			assertTrue("Build did not build any archive", builtArchive.canRead());
			File unpackFolder = ForgeTests.getTestOutputFolder("apache-unpack-result", true);
			InputStream input = new GZIPInputStream(new FileInputStream(builtArchive));
			try {
				TarUtils.unpack(input, unpackFolder, false);
			}
			finally {
				StreamUtil.close(input);
			}
			assertTrue(
				"Archive doesn't contain the expected top folder", new File(unpackFolder, archiveName).isDirectory());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
		catch(IncompleteException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Tests the ' {@link org.cloudsmith.geppetto.forge.Forge#changes(java.io.File)
	 * <em>Changes</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.Forge#changes(java.io.File)
	 * @generated NOT
	 */
	public void testChanges__File() {
		try {
			File installFolder = ForgeTests.getTestOutputFolder("test-changes", true);
			File resultFolder = ForgeTests.getTestOutputFolder("test-changes-result", true);
			FileUtils.cpR(
				Activator.getTestData("puppetlabs-apache"), installFolder, FileUtils.DEFAULT_EXCLUDES, false, true);
			fixture.build(installFolder, resultFolder);
			List<File> changes = fixture.changes(installFolder);
			assertTrue("Unexpected changes", changes.isEmpty());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
		catch(IncompleteException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Tests the ' {@link org.cloudsmith.geppetto.forge.Forge#generate(java.io.File, org.cloudsmith.geppetto.forge.Metadata)
	 * <em>Generate</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.Forge#generate(java.io.File, org.cloudsmith.geppetto.forge.Metadata)
	 * @generated NOT
	 */
	public void testGenerate__File_Metadata() {
		try {
			ForgeService service = ForgeFactory.eINSTANCE.createForgeService();
			Metadata metadata = service.createMetadata("cloudsmith/testmodule");
			File installFolder = ForgeTests.getTestOutputFolder("testmodule-install", true);
			installFolder.delete();
			fixture.generate(installFolder, metadata);
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Tests the ' {@link org.cloudsmith.geppetto.forge.Forge#getRelease(java.lang.String)
	 * <em>Get Release</em>}' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.Forge#getRelease(java.lang.String)
	 * @generated NOT
	 */
	public void testGetRelease__String() {
		try {
			ReleaseInfo info = fixture.getRelease("puppetlabs-apache");
			assertTrue("Unexpeced file", info.getFile().startsWith("/system/releases/p/puppetlabs/puppetlabs-apache-"));
			assertNotNull(info.getVersion());
		}
		catch(FileNotFoundException e) {
			fail("No release found matching 'puppetlabs-apache'");
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Tests the ' {@link org.cloudsmith.geppetto.forge.Forge#install(java.lang.String, java.io.File, boolean, boolean)
	 * <em>Install</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.Forge#install(java.lang.String, java.io.File, boolean, boolean)
	 * @generated NOT
	 */
	public void testInstall__String_File_boolean_boolean() {
		try {
			File installFolder = ForgeTests.getTestOutputFolder("rsync-install", true);
			fixture.install("ghoneycutt/rsync", installFolder, false, true);
			File found = new File(installFolder, "rsync");
			assertTrue("Installation did not produce the expected result", found.isDirectory());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Tests the ' {@link org.cloudsmith.geppetto.forge.Forge#search(java.lang.String)
	 * <em>Search</em>}' operation. <!-- begin-user-doc --> <!-- end-user-doc
	 * -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.Forge#search(java.lang.String)
	 * @generated NOT
	 */
	public void testSearch__String() {
		try {
			List<ModuleInfo> hits = fixture.search("rsync");
			assertFalse("No modules found matching 'rsync'", hits.isEmpty());
			for(ModuleInfo mi : hits)
				System.out.println(mi);
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

} // ForgeTest
