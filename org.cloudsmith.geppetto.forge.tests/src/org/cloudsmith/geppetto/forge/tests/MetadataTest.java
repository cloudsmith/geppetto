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
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;

import junit.framework.TestCase;
import junit.textui.TestRunner;

import org.cloudsmith.geppetto.forge.ForgeFactory;
import org.cloudsmith.geppetto.forge.MatchRule;
import org.cloudsmith.geppetto.forge.Metadata;
import org.cloudsmith.geppetto.forge.VersionRequirement;
import org.cloudsmith.geppetto.forge.impl.MetadataImpl;
import org.cloudsmith.geppetto.forge.util.JsonUtils;
import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil.EqualityHelper;
import org.eclipse.emf.ecore.util.FeatureMap;
import org.eclipse.emf.ecore.util.FeatureMapUtil;

import com.google.gson.Gson;

/**
 * <!-- begin-user-doc --> A test case for the model object ' <em><b>Metadata</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are tested:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.forge.Metadata#getFullName() <em>Full Name</em>}</li>
 * </ul>
 * </p>
 * <p>
 * The following operations are tested:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.forge.Metadata#loadModuleFile(java.io.File) <em>Load Module File</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.Metadata#loadTypeFiles(java.io.File) <em>Load Type Files</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.Metadata#loadChecksums(java.io.File) <em>Load Checksums</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.Metadata#saveJSONMetadata(java.io.File) <em>Save JSON Metadata</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.Metadata#saveModulefile(java.io.File) <em>Save Modulefile</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.Metadata#parseVersionRequirement(java.lang.String) <em>Parse Version Requirement</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class MetadataTest extends TestCase {

	private static Object countLines(String str) {
		if(str == null || str.length() == 0)
			return null;
		int cnt = 1;
		int idx = str.length();
		while(--idx >= 0)
			if(str.charAt(idx) == '\n')
				cnt++;
		return cnt;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(MetadataTest.class);
	}

	/**
	 * The fixture for this Metadata test case.
	 * <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @generated
	 */
	protected Metadata fixture = null;

	/**
	 * Constructs a new Metadata test case with the given name. <!--
	 * begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	public MetadataTest(String name) {
		super(name);
	}

	/**
	 * Returns the fixture for this Metadata test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected Metadata getFixture() {
		return fixture;
	}

	private void performJsonSerialization(String module) {
		populateFromModule(module);
		Gson gson = JsonUtils.getGSon();
		String json1 = gson.toJson(fixture);
		Metadata mi = gson.fromJson(json1, MetadataImpl.class);
		assertEquals("JSON output differ", json1, gson.toJson(mi));

		@SuppressWarnings("serial")
		EqualityHelper cmp = new EqualityHelper() {
			@Override
			protected boolean haveEqualAttribute(EObject eObject1, EObject eObject2, EAttribute attribute) {

				Object value1 = eObject1.eGet(attribute);
				Object value2 = eObject2.eGet(attribute);

				// If the first value is null, the second value must be null.
				//
				if(value1 == null) {
					return value2 == null;
				}

				// Since the first value isn't null, if the second one is, they
				// aren't equal.
				//
				if(value2 == null) {
					return false;
				}

				// If this is a feature map...
				//
				if(FeatureMapUtil.isFeatureMap(attribute)) {
					// The feature maps must be equal.
					//
					FeatureMap featureMap1 = (FeatureMap) value1;
					FeatureMap featureMap2 = (FeatureMap) value2;
					return equalFeatureMaps(featureMap1, featureMap2);
				}

				// The values must be Java equal.
				//
				if("checksums".equals(attribute.getName())) {
					@SuppressWarnings("unchecked")
					Map<String, byte[]> c1 = (Map<String, byte[]>) value1;
					@SuppressWarnings("unchecked")
					Map<String, byte[]> c2 = (Map<String, byte[]>) value2;
					if(c1.size() != c2.size())
						return false;

					for(Map.Entry<String, byte[]> entry : c1.entrySet()) {
						byte[] b1 = entry.getValue();
						byte[] b2 = c2.get(entry.getKey());
						if(b2 == null || b2 == null) {
							if(b1 == b2)
								continue;
							return false;
						}
						if(!Arrays.equals(b1, b2))
							return false;
					}
					return true;
				}
				return value1.equals(value2);
			}

			@Override
			protected boolean haveEqualFeature(EObject eObject1, EObject eObject2, EStructuralFeature feature) {
				return "location".equals(feature.getName())
						? true
						: super.haveEqualFeature(eObject1, eObject2, feature);
			}
		};
		assertTrue("Original and JSON serialized/deserialized instance differs", cmp.equals(fixture, mi));
	}

	private void populateFromModule(String module) {
		try {
			((MetadataImpl) fixture).populateFromModuleDir(Activator.getTestData(module));
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Sets the fixture for this Metadata test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected void setFixture(Metadata fixture) {
		this.fixture = fixture;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(ForgeFactory.eINSTANCE.createMetadata());
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
	 * Tests the '{@link org.cloudsmith.geppetto.forge.Metadata#getFullName()
	 * <em>Full Name</em>}' feature getter. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.Metadata#getFullName()
	 * @generated NOT
	 */
	public void testGetFullName() {
		fixture.setUser("some");
		fixture.setName("name");
		assertEquals("Full name not set correctly", "some-name", fixture.getFullName());
	}

	public void testJSonSerialization() throws Exception {
		performJsonSerialization("bobsh-iptables");
	}

	public void testJSonSerialization2() throws Exception {
		performJsonSerialization("DavidSchmitt-collectd");
	}

	public void testJSonSerialization3() throws Exception {
		performJsonSerialization("ghoneycutt-rsync");
	}

	public void testJSonSerialization4() throws Exception {
		performJsonSerialization("lab42-common");
	}

	public void testJSonSerialization5() throws Exception {
		performJsonSerialization("puppetlabs-apache");
	}

	/**
	 * Tests the ' {@link org.cloudsmith.geppetto.forge.Metadata#loadChecksums(java.io.File)
	 * <em>Load Checksums</em>}' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.Metadata#loadChecksums(java.io.File)
	 * @generated NOT
	 */
	public void testLoadChecksums__File() {
		try {
			assertTrue(fixture.getChecksums().isEmpty());
			fixture.loadChecksums(Activator.getTestData("puppetlabs-apache"));
			assertEquals("Incorrect number of checksums", fixture.getChecksums().size(), 18);
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Tests the ' {@link org.cloudsmith.geppetto.forge.Metadata#loadModuleFile(java.io.File)
	 * <em>Load Module File</em>}' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.Metadata#loadModuleFile(java.io.File)
	 * @generated NOT
	 */
	public void testLoadModuleFile__File() {
		try {
			fixture.loadModuleFile(Activator.getTestData("puppetlabs-apache/Modulefile"));
			assertEquals("Unexpected module name", "puppetlabs-apache", fixture.getFullName());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Tests the ' {@link org.cloudsmith.geppetto.forge.Metadata#loadTypeFiles(java.io.File)
	 * <em>Load Type Files</em>}' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.Metadata#loadTypeFiles(java.io.File)
	 * @generated NOT
	 */
	public void testLoadTypeFiles__File() {
		try {
			fixture.loadTypeFiles(Activator.getTestData("puppetlabs-apache/lib/puppet"));
			assertEquals("Expected 1 type", 1, fixture.getTypes().size());
			assertEquals("Unexpected type name", "a2mod", fixture.getTypes().get(0).getName());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Tests the '{@link org.cloudsmith.geppetto.forge.Metadata#parseVersionRequirement(java.lang.String) <em>Parse Version Requirement</em>}'
	 * operation.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.Metadata#parseVersionRequirement(java.lang.String)
	 * @generated NOT
	 */
	public void testParseVersionRequirement__String() {
		VersionRequirement vq = fixture.parseVersionRequirement(">=1.2.3");
		assertEquals(MatchRule.GREATER_OR_EQUAL, vq.getMatchRule());
		assertEquals("1.2.3", vq.getVersion());

		vq = fixture.parseVersionRequirement("==1");
		assertEquals(MatchRule.PERFECT, vq.getMatchRule());
		assertEquals("1", vq.getVersion());

		vq = fixture.parseVersionRequirement("~=3.2beta");
		assertEquals(MatchRule.EQUIVALENT, vq.getMatchRule());
		assertEquals("3.2beta", vq.getVersion());

		vq = fixture.parseVersionRequirement("~~1.2.3-alpha");
		assertEquals(MatchRule.COMPATIBLE, vq.getMatchRule());
		assertEquals("1.2.3-alpha", vq.getVersion());
	}

	/**
	 * Tests the ' {@link org.cloudsmith.geppetto.forge.Metadata#saveJSONMetadata(java.io.File)
	 * <em>Save JSON Metadata</em>}' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.Metadata#saveJSONMetadata(java.io.File)
	 * @generated NOT
	 */
	public void testSaveJSONMetadata__File() {
		try {
			File outputDir = ForgeTests.getTestOutputFolder("json-ouput", true);
			populateFromModule("puppetlabs-apache");
			File jsonFile = new File(outputDir, "metadata.json");
			fixture.saveJSONMetadata(jsonFile);
			assertTrue("No readable metadata.json file was generated", jsonFile.canRead());
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Tests the ' {@link org.cloudsmith.geppetto.forge.Metadata#saveModulefile(java.io.File)
	 * <em>Save Modulefile</em>}' operation. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.Metadata#saveModulefile(java.io.File)
	 * @generated NOT
	 */
	public void testSaveModulefile__File() {
		try {
			File outputDir = ForgeTests.getTestOutputFolder("modulefile-ouput", true);
			populateFromModule("ghoneycutt-rsync");
			File moduleFile = new File(outputDir, "Modulefile");
			fixture.saveModulefile(moduleFile);
			assertTrue("No readable Modulefile file was generated", moduleFile.canRead());

			Metadata tst = ForgeFactory.eINSTANCE.createMetadata();
			tst.loadModuleFile(moduleFile);
			assertEquals("Expected 2 dependencies", 2, tst.getDependencies().size());
			assertEquals("Expected 3 lines of text", 3, countLines(tst.getDescription()));
			assertEquals("Expected 4 lines of text", 4, countLines(tst.getSummary()));
			assertEquals("Expected 5 lines of text", 5, countLines(tst.getLicense()));
		}
		catch(IOException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Tests the ' {@link org.cloudsmith.geppetto.forge.Metadata#setFullName(java.lang.String)
	 * <em>Full Name</em>}' feature setter. <!-- begin-user-doc --> <!--
	 * end-user-doc -->
	 * 
	 * @see org.cloudsmith.geppetto.forge.Metadata#setFullName(java.lang.String)
	 * @generated NOT
	 */
	public void testSetFullName() {
		try {
			fixture.setFullName("some/name");
			assertEquals("User was not set correctly", "some", fixture.getUser());
			assertEquals("Name was not set correctly", "name", fixture.getName());
			assertEquals("Canonical full name was not set correctly", "some-name", fixture.getFullName());
		}
		catch(IllegalArgumentException e) {
			fail(e.getMessage());
		}
	}
} // MetadataTest
