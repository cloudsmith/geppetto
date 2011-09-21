package org.cloudsmith.geppetto.catalog.test;

import java.io.File;

import junit.framework.TestCase;

import org.cloudsmith.geppetto.catalog.Catalog;
import org.cloudsmith.geppetto.catalog.util.CatalogJsonSerializer;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

public class TestJsonLoad extends TestCase {

	public void testLoadSample1() throws Exception {

		File f = TestDataProvider.getTestFile(new Path("testData/sample1.json"));
		Catalog c = CatalogJsonSerializer.load(f);

		assertEquals("Should have the expected name", "testcentos.pilsen.cloudsmith.com", c.getName());

		// Save the TargetEntry as a loadable resource
		ResourceSet resourceSet = new ResourceSetImpl();
		URI fileURI = URI.createFileURI(new File("testOutput/sample1.catalog").getAbsolutePath());
		Resource targetResource = resourceSet.createResource(fileURI);
		targetResource.getContents().add(c);
		targetResource.save(null);
		System.err.println("Target saved to: " + fileURI.toString());

	}

	public void testLoadSample2() throws Exception {

		File f = TestDataProvider.getTestFile(new Path("testData/sample2.json"));
		Catalog c = CatalogJsonSerializer.load(f);

		assertEquals("Should have the expected name", "testcentos.pilsen.cloudsmith.com", c.getName());
		// Save the TargetEntry as a loadable resource
		ResourceSet resourceSet = new ResourceSetImpl();
		URI fileURI = URI.createFileURI(new File("testOutput/sample2.catalog").getAbsolutePath());
		Resource targetResource = resourceSet.createResource(fileURI);
		targetResource.getContents().add(c);
		targetResource.save(null);
		System.err.println("Target saved to: " + fileURI.toString());

	}

	public void testLoadSample3() throws Exception {

		File f = TestDataProvider.getTestFile(new Path("testData/sample3.json"));
		Catalog c = CatalogJsonSerializer.load(f);

		assertEquals("Should have the expected name", "backend.i-fcda579c", c.getName());
		// Save the TargetEntry as a loadable resource
		ResourceSet resourceSet = new ResourceSetImpl();
		URI fileURI = URI.createFileURI(new File("testOutput/sample3.catalog").getAbsolutePath());
		Resource targetResource = resourceSet.createResource(fileURI);
		targetResource.getContents().add(c);
		targetResource.save(null);
		System.err.println("Target saved to: " + fileURI.toString());

	}

}
