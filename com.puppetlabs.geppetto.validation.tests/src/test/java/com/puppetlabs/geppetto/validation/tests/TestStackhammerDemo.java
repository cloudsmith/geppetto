package org.cloudsmith.geppetto.validation.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;
import java.util.Map.Entry;

import org.cloudsmith.geppetto.diagnostic.Diagnostic;
import org.cloudsmith.geppetto.validation.FileType;
import org.cloudsmith.geppetto.validation.ValidationOptions;
import org.cloudsmith.geppetto.validation.ValidationService;
import org.cloudsmith.geppetto.validation.runner.AllModuleReferences;
import org.cloudsmith.geppetto.validation.runner.AllModuleReferences.ClassDescription;
import org.cloudsmith.geppetto.validation.runner.AllModuleReferences.Export;
import org.cloudsmith.geppetto.validation.runner.BuildResult;
import org.eclipse.core.runtime.SubMonitor;
import org.junit.Test;

import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;

public class TestStackhammerDemo extends AbstractValidationTest {
	@Test
	public void test_ValidateStackhammerDemo() throws Exception {
		{
			File root = new File("/Users/henrik/gitrepos/stackhammer-demo");
			// TestDataProvider.getTestFile(new Path(
			// "testData/test-modules/"));
			ValidationService vs = getValidationService();
			Diagnostic chain = new Diagnostic();
			ValidationOptions options = getValidationOptions();
			options.setCheckLayout(true);
			options.setCheckModuleSemantics(true);
			options.setCheckReferences(true);
			options.setFileType(FileType.PUPPET_ROOT);
			BuildResult br = vs.validate(chain, root, options, null, SubMonitor.convert(null));

			// System.err.println(errorsToString(chain));
			StringBuilder builder = new StringBuilder();
			builder.append("Diagnostic: A version should be specified.\n");
			builder.append("Diagnostic: A version should be specified.\n");
			builder.append("Diagnostic: Unknown class: 'lsbprovider'\n");
			builder.append("Diagnostic: Can not determine until runtime if this is a valid class reference\n");
			// builder.append("Diagnostic: Ambiguous reference to: 'service' \n");
			// builder.append("Diagnostic: Ambiguous reference to: 'postfix::pflogsumm' \n");
			// builder.append("Diagnostic: Ambiguous reference to: 'common' \n");
			// builder.append("Diagnostic: Ambiguous reference to: 'snmp::files' \n");

			assertEquals("There should be the expected errors", builder.toString(), errorsToString(chain));

			// Get the exports for nodes
			AllModuleReferences allModuleReferences = br.getAllModuleReferences();

			// Get the map with exports for nodes
			Multimap<String, String> restricted = allModuleReferences.getRestricted();
			int count = 0;
			int numberOfClasses = 0;
			assertEquals("There should be one node", 1, Iterables.size(restricted.keySet()));
			int parameterCount = 0;

			for(String f : restricted.keySet()) {
				count++;
				assertEquals("There should be only 1 restricted container (a node)", 1, count);

				numberOfClasses = Iterables.size(allModuleReferences.getClasses(allModuleReferences.getVisibleExports(new File(
					f))));
				assertEquals("There should be 47 exported classes", 47, numberOfClasses);

				// dumpExports(allModuleReferences.getVisibleExports(new
				// File(f)));

				// API2
				List<AllModuleReferences.ClassDescription> classes = allModuleReferences.getClassDescriptions(allModuleReferences.getVisibleExports(new File(
					f)));
				numberOfClasses = classes.size();
				assertEquals("There should be 47 exported classes", 47, numberOfClasses);

				boolean apacheFound = false;
				for(ClassDescription d : classes) {
					for(Entry<String, Export> x : d.getExportedParameters().entrySet()) {
						parameterCount++;
						assertNotNull("key can not be null", x.getKey());
						assertNotNull("value can not be null", x.getValue());
						assertTrue("Name is set in Export", x.getValue().getName().length() > 0);
					}
					if("apache".equals(d.getExportedClass().getName()))
						apacheFound = true;
				}
				assertTrue("Should have found 'apache' as visible for node", apacheFound);

			}
			assertEquals("There should have been 47 classes", 47, numberOfClasses);
			// actually, in this version of the repo there is 0
			assertTrue("There should be 0 parameters", parameterCount == 0);

		}
	}

	@Test
	public void test_ValidateStackhammerDemoFiltered() throws Exception {
		{
			File root = new File("/Users/henrik/gitrepos/stackhammer-demo");
			// TestDataProvider.getTestFile(new Path(
			// "testData/test-modules/"));
			ValidationService vs = getValidationService();
			Diagnostic chain = new Diagnostic();
			ValidationOptions options = getValidationOptions();
			options.setCheckLayout(true);
			options.setCheckModuleSemantics(true);
			options.setCheckReferences(true);
			options.setFileType(FileType.PUPPET_ROOT);
			BuildResult br = vs.validate(chain, root, options, new File[] { new File(
				"/Users/henrik/gitrepos/stackhammer-demo/nodes/foo/") }, SubMonitor.convert(null));

			// System.err.println(errorsToString(chain));
			StringBuilder builder = new StringBuilder();
			builder.append("Diagnostic: A version should be specified.\n");
			// builder.append("Diagnostic: Unknown class: 'lsbprovider'\n");
			// builder.append("Diagnostic: Can not determine until runtime if this is a valid class reference\n");
			// builder.append("Diagnostic: Ambiguous reference to: 'service' \n");
			// builder.append("Diagnostic: Ambiguous reference to: 'postfix::pflogsumm' \n");
			// builder.append("Diagnostic: Ambiguous reference to: 'common' \n");
			// builder.append("Diagnostic: Ambiguous reference to: 'snmp::files' \n");

			assertEquals("There should be the expected errors", builder.toString(), errorsToString(chain));

			// Get the exports for nodes
			AllModuleReferences allModuleReferences = br.getAllModuleReferences();

			// Get the map with exports for nodes
			Multimap<String, String> restricted = allModuleReferences.getRestricted();
			int count = 0;
			int numberOfClasses = 0;
			assertEquals("There should be one node", 1, Iterables.size(restricted.keySet()));
			int parameterCount = 0;

			for(String f : restricted.keySet()) {
				count++;
				assertEquals("There should be only 1 restricted container (a node)", 1, count);

				numberOfClasses = Iterables.size(allModuleReferences.getClasses(allModuleReferences.getVisibleExports(new File(
					f))));
				assertEquals("There should be 47 exported classes", 47, numberOfClasses);

				// dumpExports(allModuleReferences.getVisibleExports(new
				// File(f)));

				// API2
				List<AllModuleReferences.ClassDescription> classes = allModuleReferences.getClassDescriptions(allModuleReferences.getVisibleExports(new File(
					f)));
				numberOfClasses = classes.size();
				assertEquals("There should be 47 exported classes", 47, numberOfClasses);

				boolean apacheFound = false;
				for(ClassDescription d : classes) {
					for(Entry<String, Export> x : d.getExportedParameters().entrySet()) {
						parameterCount++;
						assertNotNull("key can not be null", x.getKey());
						assertNotNull("value can not be null", x.getValue());
						assertTrue("Name is set in Export", x.getValue().getName().length() > 0);
					}
					if("apache".equals(d.getExportedClass().getName()))
						apacheFound = true;
				}
				assertTrue("Should have found 'apache' as visible for node", apacheFound);

			}
			assertEquals("There should have been 47 classes", 47, numberOfClasses);
			// actually, in this version of the repo there is 0
			assertTrue("There should be 0 parameters", parameterCount == 0);

		}
	}

	@Test
	public void test_ValidateStackhammerPuppetConfDemo() throws Exception {
		{
			File root = new File("/Users/henrik/git/khussey-puppetconf-demo");
			// TestDataProvider.getTestFile(new Path(
			// "testData/test-modules/"));
			ValidationService vs = getValidationService();
			Diagnostic chain = new Diagnostic();
			ValidationOptions options = getValidationOptions();
			options.setCheckLayout(true);
			options.setCheckModuleSemantics(true);
			options.setCheckReferences(true);
			options.setFileType(FileType.PUPPET_ROOT);
			BuildResult br = vs.validate(chain, root, options, null, SubMonitor.convert(null));

			// System.err.println(errorsToString(chain));
			// StringBuilder builder = new StringBuilder();

			assertEquals("There should be the expected errors", "", errorsToString(chain));

			// Get the exports for nodes
			AllModuleReferences allModuleReferences = br.getAllModuleReferences();

			// Get the map with exports for nodes
			Multimap<String, String> restricted = allModuleReferences.getRestricted();
			int count = 0;
			int numberOfClasses = 0;
			assertEquals("There should be one node", 1, Iterables.size(restricted.keySet()));
			int parameterCount = 0;

			for(String f : restricted.keySet()) {
				count++;
				assertEquals("There should be only 1 restricted container (a node)", 1, count);

				numberOfClasses = Iterables.size(allModuleReferences.getClasses(allModuleReferences.getVisibleExports(new File(
					f))));
				assertEquals("There should be 47 exported classes", 47, numberOfClasses);

				// dumpExports(allModuleReferences.getVisibleExports(new
				// File(f)));

				// API2
				List<AllModuleReferences.ClassDescription> classes = allModuleReferences.getClassDescriptions(allModuleReferences.getVisibleExports(new File(
					f)));
				numberOfClasses = classes.size();
				assertEquals("There should be 47 exported classes", 47, numberOfClasses);

				boolean apacheFound = false;
				for(ClassDescription d : classes) {
					for(Entry<String, Export> x : d.getExportedParameters().entrySet()) {
						parameterCount++;
						assertNotNull("key can not be null", x.getKey());
						assertNotNull("value can not be null", x.getValue());
						assertTrue("Name is set in Export", x.getValue().getName().length() > 0);
					}
					if("apache".equals(d.getExportedClass().getName()))
						apacheFound = true;
				}
				assertTrue("Should have found 'apache' as visible for node", apacheFound);

			}
			assertEquals("There should have been 47 classes", 47, numberOfClasses);
			// actually, in this version of the repo there is 0
			assertTrue("There should be 0 parameters", parameterCount == 0);

		}
	}
}
