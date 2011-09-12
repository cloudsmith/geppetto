package org.cloudsmith.geppetto.catalog.test;

import junit.framework.TestCase;

public class TestCatalogCompare extends TestCase {

	public void testCatalogCompare() throws Exception {

		// File f = TestDataProvider.getTestFile(new Path("testData/sample1.json"));
		// Catalog a = CatalogJsonSerializer.load(f);
		// f = TestDataProvider.getTestFile(new Path("testData/sample2.json"));
		// Catalog b = CatalogJsonSerializer.load(f);
		//
		// ResourceSet resourceSet = new ResourceSetImpl();
		// URI fileURI = URI.createFileURI(new File("testOutput/sample1.catalog").getAbsolutePath());
		// Resource targetResource = resourceSet.createResource(fileURI);
		// targetResource.getContents().add(a);
		// fileURI = URI.createFileURI(new File("testOutput/sample2.catalog").getAbsolutePath());
		// targetResource = resourceSet.createResource(fileURI);
		// targetResource.getContents().add(b);
		//
		// CatalogUtils service = new CatalogUtils();
		// DiffModel diff = service.catalogDelta(a, b);
		// assertTrue("There should be differences", diff.getDifferences().size() > 0);
		// for(DiffElement d : diff.getDifferences()) {
		// System.err.printf("Kind=%s diff=%s\n", d.getKind(), d.toString());
		//
		// for(DiffElement d2 : d.getSubDiffElements())
		// System.err.printf("    Kind=%s \n", d2.getKind());
		// }
		// System.err.println("OWNED");
		// for(DiffElement d : diff.getOwnedElements()) {
		// System.err.printf("Kind=%s diff=%s\n", d.getKind(), d.toString());
		//
		// for(DiffElement d2 : d.getSubDiffElements())
		// System.err.printf("    Kind=%s \n", d2.getKind());
		// }

		// System.err.println("DIFF SERIALIZED");
		// System.err.println(ModelUtils.serialize(diff));
	}
}
