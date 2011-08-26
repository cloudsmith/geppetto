package org.cloudsmith.geppetto.catalog.test;

import java.io.File;

import junit.framework.TestCase;

import org.cloudsmith.geppetto.catalog.Catalog;
import org.cloudsmith.geppetto.catalog.util.CatalogJsonSerializer;
import org.eclipse.core.runtime.Path;

public class TestJsonLoad extends TestCase {

	public void testLoad() throws Exception {

		File f = TestDataProvider.getTestFile(new Path("testData/sample2.json"));
		Catalog c = CatalogJsonSerializer.load(f);

		assertEquals("Should have the expected name", "testcentos.pilsen.cloudsmith.com", c.getName());

	}
}
