package org.cloudsmith.geppetto.graph.tests;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

import org.cloudsmith.geppetto.graph.catalog.CatalogServices;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.junit.Test;


public class TestCatalogGraph {

	@Test
	public void catalogGraph() throws Exception {
		// Input stream for sample1.json
		File catalogFile = TestDataProvider.getTestFile(new Path("testData/jsonCatalogs/sample1.json"));
		InputStream catalogStream = new FileInputStream(catalogFile);
		// Write the svg to a file:
		FileOutputStream svgStream = new FileOutputStream(new File(
			TestDataProvider.getTestOutputDir(), "sampleCatalog1.svg"));

		CatalogServices catalogServices = new CatalogServices();
		catalogServices.produceSVGGraph("Sample catalog", catalogStream, svgStream, new NullProgressMonitor(), null);
	}

	@Test
	public void catalogGraph2() throws Exception {
		// Input stream for sample1.json
		File catalogFile = TestDataProvider.getTestFile(new Path("testData/jsonCatalogs/sample2.json"));
		InputStream catalogStream = new FileInputStream(catalogFile);
		// Write the svg to a file:
		FileOutputStream svgStream = new FileOutputStream(new File(
			TestDataProvider.getTestOutputDir(), "sampleCatalog2.svg"));

		CatalogServices catalogServices = new CatalogServices();
		catalogServices.produceSVGGraph("Sample catalog", catalogStream, svgStream, new NullProgressMonitor(), null);
	}

	@Test
	public void catalogGraph3() throws Exception {
		// Input stream for sample1.json
		File catalogFile = TestDataProvider.getTestFile(new Path("testData/jsonCatalogs/sample3.json"));
		InputStream catalogStream = new FileInputStream(catalogFile);
		// Write the svg to a file:
		File outputFolder = TestDataProvider.getTestOutputDir();
		FileOutputStream svgStream = new FileOutputStream(new File(outputFolder, "sampleCatalog3.svg"));
		FileOutputStream dotStream = new FileOutputStream(new File(outputFolder, "sampleCatalog3.dot"));

		CatalogServices catalogServices = new CatalogServices();
		catalogServices.produceDOTGraph("Sample catalog", catalogStream, dotStream, new NullProgressMonitor(), null);
		catalogStream = new FileInputStream(catalogFile);
		catalogServices.produceSVGGraph("Sample catalog", catalogStream, svgStream, new NullProgressMonitor(), null);
	}

	@Test
	public void deltaCatalogGraph() throws Exception {
		// Input stream for sample1.json
		File oldCatalogFile = TestDataProvider.getTestFile(new Path("testData/jsonCatalogs/sample1.json"));
		File newCatalogFile = TestDataProvider.getTestFile(new Path("testData/jsonCatalogs/sample2.json"));
		InputStream oldCatalogStream = new FileInputStream(oldCatalogFile);
		InputStream newCatalogStream = new FileInputStream(newCatalogFile);

		// Write the svg to a file:
		File outputFolder = TestDataProvider.getTestOutputDir();
		FileOutputStream svgStream = new FileOutputStream(new File(outputFolder, "sampleDelta.svg"));
		FileOutputStream dotStream = new FileOutputStream(new File(outputFolder, "sampleDelta.dot"));

		IPath rootPath = new Path("/usr/share/puppet");
		CatalogServices catalogServices = new CatalogServices();
		catalogServices.produceDOTDeltaGraph(
			"Sample catalog", oldCatalogStream, rootPath, newCatalogStream, rootPath, dotStream,
			new NullProgressMonitor());
		oldCatalogStream = new FileInputStream(oldCatalogFile);
		newCatalogStream = new FileInputStream(newCatalogFile);
		catalogServices.produceSVGDeltaGraph(
			"Sample catalog", oldCatalogStream, rootPath, newCatalogStream, rootPath, svgStream,
			new NullProgressMonitor());
	}

	@Test
	public void deltaCatalogGraph_1_3() throws Exception {
		// Input stream for sample1.json
		File oldCatalogFile = TestDataProvider.getTestFile(new Path("testData/jsonCatalogs/sample1.json"));
		File newCatalogFile = TestDataProvider.getTestFile(new Path("testData/jsonCatalogs/sample3.json"));
		InputStream oldCatalogStream = new FileInputStream(oldCatalogFile);
		InputStream newCatalogStream = new FileInputStream(newCatalogFile);

		// Write the svg to a file:
		File outputFolder = TestDataProvider.getTestOutputDir();
		FileOutputStream svgStream = new FileOutputStream(new File(outputFolder, "sampleDelta_1_3.svg"));
		FileOutputStream dotStream = new FileOutputStream(new File(outputFolder, "sampleDelta_1_3.dot"));

		IPath rootPath = new Path("/usr/share/puppet");
		CatalogServices catalogServices = new CatalogServices();
		catalogServices.produceDOTDeltaGraph(
			"Sample catalog", oldCatalogStream, rootPath, newCatalogStream, rootPath, dotStream,
			new NullProgressMonitor());
		oldCatalogStream = new FileInputStream(oldCatalogFile);
		newCatalogStream = new FileInputStream(newCatalogFile);
		catalogServices.produceSVGDeltaGraph(
			"Sample catalog", oldCatalogStream, rootPath, newCatalogStream, rootPath, svgStream,
			new NullProgressMonitor());
	}

	@Test
	public void deltaCatalogMissingResourceGraph_1_2() throws Exception {
		// Input stream for sample1.json
		File oldCatalogFile = TestDataProvider.getTestFile(new Path("testData/jsonCatalogs/missingResource.json"));
		File newCatalogFile = TestDataProvider.getTestFile(new Path("testData/jsonCatalogs/missingResource2.json"));
		InputStream oldCatalogStream = new FileInputStream(oldCatalogFile);
		InputStream newCatalogStream = new FileInputStream(newCatalogFile);

		// Write the svg to a file:
		File outputFolder = TestDataProvider.getTestOutputDir();
		FileOutputStream svgStream = new FileOutputStream(new File(outputFolder, "missingResourceDelta.svg"));
		FileOutputStream dotStream = new FileOutputStream(new File(outputFolder, "missingResourceDelta.dot"));

		IPath rootPath = new Path("/usr/share/puppet");
		CatalogServices catalogServices = new CatalogServices();
		catalogServices.produceDOTDeltaGraph(
			"Sample catalog", oldCatalogStream, rootPath, newCatalogStream, rootPath, dotStream,
			new NullProgressMonitor());
		oldCatalogStream = new FileInputStream(oldCatalogFile);
		newCatalogStream = new FileInputStream(newCatalogFile);
		catalogServices.produceSVGDeltaGraph(
			"Sample catalog", oldCatalogStream, rootPath, newCatalogStream, rootPath, svgStream,
			new NullProgressMonitor());
	}

	/**
	 * In which it is tested what happens when a required resource missing in old is added in new.
	 * 
	 * @throws Exception
	 */
	@Test
	public void deltaCatalogMissingResourceGraph_1_3() throws Exception {
		// Input stream for sample1.json
		File oldCatalogFile = TestDataProvider.getTestFile(new Path("testData/jsonCatalogs/missingResource.json"));
		File newCatalogFile = TestDataProvider.getTestFile(new Path("testData/jsonCatalogs/missingResource3.json"));
		InputStream oldCatalogStream = new FileInputStream(oldCatalogFile);
		InputStream newCatalogStream = new FileInputStream(newCatalogFile);

		// Write the svg to a file:
		File outputFolder = TestDataProvider.getTestOutputDir();
		FileOutputStream svgStream = new FileOutputStream(new File(outputFolder, "missingResourceDelta_1_3.svg"));
		FileOutputStream dotStream = new FileOutputStream(new File(outputFolder, "missingResourceDelta_1_3.dot"));

		IPath rootPath = new Path("/usr/share/puppet");
		CatalogServices catalogServices = new CatalogServices();
		catalogServices.produceDOTDeltaGraph(
			"Sample catalog", oldCatalogStream, rootPath, newCatalogStream, rootPath, dotStream,
			new NullProgressMonitor());
		oldCatalogStream = new FileInputStream(oldCatalogFile);
		newCatalogStream = new FileInputStream(newCatalogFile);
		catalogServices.produceSVGDeltaGraph(
			"Sample catalog", oldCatalogStream, rootPath, newCatalogStream, rootPath, svgStream,
			new NullProgressMonitor());
	}

	/**
	 * In which it is tested what happens when a resource that is required is missing in new.
	 * 
	 * @throws Exception
	 */
	@Test
	public void deltaCatalogMissingResourceGraph_3_1() throws Exception {
		// Input stream for sample1.json
		File oldCatalogFile = TestDataProvider.getTestFile(new Path("testData/jsonCatalogs/missingResource3.json"));
		File newCatalogFile = TestDataProvider.getTestFile(new Path("testData/jsonCatalogs/missingResource.json"));
		InputStream oldCatalogStream = new FileInputStream(oldCatalogFile);
		InputStream newCatalogStream = new FileInputStream(newCatalogFile);

		// Write the svg to a file:
		File outputFolder = TestDataProvider.getTestOutputDir();
		FileOutputStream svgStream = new FileOutputStream(new File(outputFolder, "missingResourceDelta_3_1.svg"));
		FileOutputStream dotStream = new FileOutputStream(new File(outputFolder, "missingResourceDelta_3_1.dot"));

		IPath rootPath = new Path("/usr/share/puppet");
		CatalogServices catalogServices = new CatalogServices();
		catalogServices.produceDOTDeltaGraph(
			"Sample catalog", oldCatalogStream, rootPath, newCatalogStream, rootPath, dotStream,
			new NullProgressMonitor());
		oldCatalogStream = new FileInputStream(oldCatalogFile);
		newCatalogStream = new FileInputStream(newCatalogFile);
		catalogServices.produceSVGDeltaGraph(
			"Sample catalog", oldCatalogStream, rootPath, newCatalogStream, rootPath, svgStream,
			new NullProgressMonitor());
	}

	@Test
	public void missingDependencyGraph() throws Exception {
		// Input stream for sample1.json
		File catalogFile = TestDataProvider.getTestFile(new Path("testData/jsonCatalogs/missingResource.json"));
		InputStream catalogStream = new FileInputStream(catalogFile);
		// Write the svg to a file:
		File outputFolder = TestDataProvider.getTestOutputDir();
		FileOutputStream svgStream = new FileOutputStream(new File(outputFolder, "missingResource.svg"));
		FileOutputStream dotStream = new FileOutputStream(new File(outputFolder, "missingResource.dot"));

		CatalogServices catalogServices = new CatalogServices();
		catalogServices.produceDOTGraph("Sample catalog", catalogStream, dotStream, new NullProgressMonitor(), null);
		catalogStream = new FileInputStream(catalogFile);
		catalogServices.produceSVGGraph("Sample catalog", catalogStream, svgStream, new NullProgressMonitor(), null);
	}
}
