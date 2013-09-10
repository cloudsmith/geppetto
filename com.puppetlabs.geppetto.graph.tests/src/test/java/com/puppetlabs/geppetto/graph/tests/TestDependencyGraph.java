package com.puppetlabs.geppetto.graph.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.graph.GraphHrefType;
import com.puppetlabs.geppetto.graph.IHrefProducer;
import com.puppetlabs.geppetto.graph.SVGProducer;
import com.puppetlabs.geppetto.graph.dependency.DependencyGraphModule;
import com.puppetlabs.geppetto.validation.FileType;
import com.puppetlabs.geppetto.validation.ValidationOptions;
import com.puppetlabs.geppetto.validation.ValidationService;
import com.puppetlabs.geppetto.validation.runner.AllModuleReferences.Export;
import com.puppetlabs.geppetto.validation.runner.BuildResult;
import org.eclipse.core.runtime.NullProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.ecore.EClass;
import org.junit.Test;

import com.google.inject.Guice;
import com.google.inject.Injector;

public class TestDependencyGraph extends AbstractValidationTest {
	@Test
	public void demoDependencyGraph() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/test-modules/"));
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		ValidationOptions options = getValidationOptions();
		options.setCheckLayout(true);
		options.setCheckModuleSemantics(true);
		options.setCheckReferences(true);
		options.setFileType(FileType.PUPPET_ROOT);

		// Write the dot to a file:
		File outputFolder = TestDataProvider.getTestOutputDir();
		FileOutputStream dotStream = new FileOutputStream(new File(outputFolder, "demoGraphSVG.dot"));

		BuildResult buildResult = vs.validate(chain, root, options, null, SubMonitor.convert(null));
		assertTrue("Validation had errors", chain.getSeverity() < Diagnostic.ERROR);
		getDependencyGraphProducer().produceGraph(
			null, "Demo Modules Dependencies", null, dotStream, buildResult, chain);

		// produce SVG
		SVGProducer svgProducer = getSVGProducer();

		FileInputStream dotInputStream = new FileInputStream(new File(outputFolder, "demoGraphSVG.dot"));

		// turn the produced dot around and send it to the SVG producer.
		// create a stream to collect the SVG output
		FileOutputStream svgStream = new FileOutputStream(new File(outputFolder, "demoGraphSVG.svg"));
		svgProducer.produceSVG(new BufferedInputStream(dotInputStream), svgStream, false, new NullProgressMonitor());

	}

	@Test
	public void dependencyGraph() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/graphTestData/"));
		Diagnostic chain = new Diagnostic();
		ValidationOptions options = getValidationOptions();
		options.setCheckLayout(true);
		options.setCheckModuleSemantics(true);
		options.setCheckReferences(true);
		options.setFileType(FileType.PUPPET_ROOT);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		ValidationService vs = getValidationService();
		BuildResult result = vs.validate(chain, root, options, null, SubMonitor.convert(null));
		getDependencyGraphProducer().produceGraph(
			null, "Module dependencies for graphTestData", null, stream, result, chain);

		assertTrue("Stream contains data", stream.size() > 10);
		String output = stream.toString();
		System.err.println(output);

	}

	/**
	 * 
	 */
	@Test
	public void dependencyGraph_Include() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/graphDataIncludeRequire/include/"));
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		ValidationOptions options = getValidationOptions();
		options.setCheckLayout(true);
		options.setCheckModuleSemantics(true);
		options.setCheckReferences(true);
		options.setFileType(FileType.PUPPET_ROOT);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		BuildResult result = vs.validate(chain, root, options, null, SubMonitor.convert(null));
		getDependencyGraphProducer().produceGraph(
			null, "Module dependencies for graphDataIncludeRequire/include", null, stream, result, chain);

		//		dumpImports(result.getAllModuleReferences());

		// TODO: assert that expected exports and imports are present
		// Currently found ok by dump or visual inspection.

		assertTrue("Stream contains data", stream.size() > 10);
		String output = stream.toString();
		System.err.println(output);

	}

	@Test
	public void dependencyGraph_Limited() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/graphTestData/"));
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		ValidationOptions options = getValidationOptions();
		options.setCheckLayout(true);
		options.setCheckModuleSemantics(true);
		options.setCheckReferences(true);
		options.setFileType(FileType.PUPPET_ROOT);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();
		File[] modules = new File[] { new File(root, "modules/B/Modulefile") };
		BuildResult buildResult = vs.validate(chain, root, options, null, SubMonitor.convert(null));
		// dumpExports(buildResult.getExportsForAll());
		getDependencyGraphProducer().produceGraph(
			null, "Module dependencies for graphTestData", modules, stream, buildResult, chain);

		String output = stream.toString();
		System.err.println(output);
		assertTrue("Stream should contain data", stream.size() > 10);
		assertFalse("xfunc should not be included", output.contains("xfunc"));
		assertTrue("cfunc should  be included", output.contains("cfunc"));

	}

	@Test
	public void dependencyGraph_SVG() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/graphTestData/"));
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		ValidationOptions options = getValidationOptions();
		options.setCheckLayout(true);
		options.setCheckModuleSemantics(true);
		options.setCheckReferences(true);
		options.setFileType(FileType.PUPPET_ROOT);
		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		BuildResult buildResult = vs.validate(chain, root, options, null, SubMonitor.convert(null));
		getDependencyGraphProducer().produceGraph(
			null, "Module dependencies for graphTestData", null, stream, buildResult, chain);

		assertTrue("Stream should contain data", stream.size() > 10);

		// produce SVG
		SVGProducer svgProducer = getSVGProducer();

		// turn the produced dot around and send it to the SVG producer.
		ByteArrayInputStream dotStream = new ByteArrayInputStream(stream.toByteArray());

		// Write the dot to a file:
		File outputFolder = TestDataProvider.getTestOutputDir();
		FileOutputStream tmp = new FileOutputStream(new File(outputFolder, "dependecyGraphSVG.dot"));
		tmp.write(stream.toByteArray());
		tmp.close();

		// create a stream to collect the SVG output
		ByteArrayOutputStream svgStream = new ByteArrayOutputStream();
		svgProducer.produceSVG(dotStream, svgStream, false, new NullProgressMonitor());

		// Write the svg to a file:
		tmp = new FileOutputStream(new File(outputFolder, "dependecyGraphSVG.svg"));
		tmp.write(svgStream.toByteArray());
		tmp.close();

		String svgString = svgStream.toString();
		// System.err.println(svgString);
		assertTrue("SVG should contain the string 'xfunc'", svgString.contains("xfunc"));

		assertFalse("SVG should not contain the string \"\\L\"", svgString.contains("\"\\L\""));

		assertTrue(
			"Stream has expected size  (>20000) bytes - differs per run. Was:" + svgStream.size(),
			svgStream.size() > 20000);
	}

	@Test
	public void forgeDependencyGraph() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/test-modules/"));
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		ValidationOptions options = getValidationOptions();
		options.setCheckLayout(true);
		options.setCheckModuleSemantics(true);
		options.setCheckReferences(true);
		options.setFileType(FileType.PUPPET_ROOT);

		// Write the dot to a file:
		File outputFolder = TestDataProvider.getTestOutputDir();
		FileOutputStream dotStream = new FileOutputStream(new File(outputFolder, "forgeGraphSVG.dot"));
		BuildResult buildResult = vs.validate(chain, root, options, null, SubMonitor.convert(null));
		getDependencyGraphProducer().produceGraph(
			null, "Forge Modules Dependencies", null, dotStream, buildResult, chain);

		// produce SVG
		SVGProducer svgProducer = getSVGProducer();

		FileInputStream dotInputStream = new FileInputStream(new File(outputFolder, "forgeGraphSVG.dot"));

		// turn the produced dot around and send it to the SVG producer.
		// create a stream to collect the SVG output
		FileOutputStream svgStream = new FileOutputStream(new File(outputFolder, "forgeGraphSVG.svg"));
		svgProducer.produceSVG(new BufferedInputStream(dotInputStream), svgStream, false, new NullProgressMonitor());

	}

	@Test
	public void githubHrefProducer() {
		Injector injector = Guice.createInjector(new DependencyGraphModule(
			GraphHrefType.GITHUB.getHrefProducerClass(), "https://github.com/puppetlabs/geppetto/master"));
		IHrefProducer producer = injector.getInstance(IHrefProducer.class);

		Export fakeExport = new Export() {

			private static final long serialVersionUID = 1L;

			@Override
			public String getDefaultValueText() {
				return null;
			}

			@Override
			public EClass getEClass() {
				return null;
			}

			@Override
			public File getFile() {
				return new File("/foo/bar/about.html");
			}

			@Override
			public String getLastNameSegment() {
				return ".html";
			}

			@Override
			public int getLength() {
				return -1;
			}

			@Override
			public int getLine() {
				return 23;
			}

			@Override
			public String getName() {
				return null;
			}

			@Override
			public String getNameWithoutLastSegment() {
				return null;
			}

			@Override
			public String getParentName() {
				return null;
			}

			@Override
			public int getStart() {
				return -1;
			}

		};
		String result = producer.href(fakeExport, new File("/foo/bar/"));
		assertEquals("https://github.com/puppetlabs/geppetto/master/about.html#L23", result);

	}

	@Test
	public void karelsGraph() throws Exception {
		File root = TestDataProvider.getTestFile(new Path("testData/test-modules/"));
		ValidationService vs = getValidationService();
		Diagnostic chain = new Diagnostic();
		ValidationOptions options = getValidationOptions();
		options.setCheckLayout(true);
		options.setCheckModuleSemantics(true);
		options.setCheckReferences(true);
		options.setFileType(FileType.PUPPET_ROOT);

		// Write the dot to a file:
		File outputFolder = TestDataProvider.getTestOutputDir();
		FileOutputStream dotStream = new FileOutputStream(new File(outputFolder, "karelGraphSVG.dot"));

		BuildResult buildResult = vs.validate(chain, root, options, null, SubMonitor.convert(null));
		getDependencyGraphProducer().produceGraph(
			null, "Karel Modules Dependencies", null, dotStream, buildResult, chain);

		// produce SVG
		SVGProducer svgProducer = getSVGProducer();

		FileInputStream dotInputStream = new FileInputStream(new File(outputFolder, "karelGraphSVG.dot"));

		// turn the produced dot around and send it to the SVG producer.
		// create a stream to collect the SVG output
		FileOutputStream svgStream = new FileOutputStream(new File(outputFolder, "karelGraphSVG.svg"));
		svgProducer.produceSVG(new BufferedInputStream(dotInputStream), svgStream, false, new NullProgressMonitor());

	}
}
