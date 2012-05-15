package org.cloudsmith.geppetto.junitresult.util;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.cloudsmith.geppetto.junitresult.AbstractAggregatedTest;
import org.cloudsmith.geppetto.junitresult.Error;
import org.cloudsmith.geppetto.junitresult.Failure;
import org.cloudsmith.geppetto.junitresult.JunitResult;
import org.cloudsmith.geppetto.junitresult.JunitresultFactory;
import org.cloudsmith.geppetto.junitresult.NegativeResult;
import org.cloudsmith.geppetto.junitresult.Property;
import org.cloudsmith.geppetto.junitresult.Skipped;
import org.cloudsmith.geppetto.junitresult.Testcase;
import org.cloudsmith.geppetto.junitresult.Testrun;
import org.cloudsmith.geppetto.junitresult.Testsuite;
import org.cloudsmith.geppetto.junitresult.Testsuites;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotSupportedException;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;

public class JunitresultLoader {
	/**
	 * Loader of so called JUnit result format, as first defined by the ANT junit task. Three main formats
	 * exists, but these are not formalized so parsing is based on empirical studies. A result document
	 * may have:
	 * <ul>
	 * <li>single <code>&lt;testsuite&gt;</code> element with optional nested elements of the same type</li>
	 * <li>a <code>&lt;testrun&gt;</code> element with multiple child <code>&lt;testsuite&gt;</code> element, which may also be nested.</li>
	 * <li>a <code>&lt;testsuites&gt;</code> element with multiple child <code>&lt;testsuite&gt;</code> element, which may be nested. When this format
	 * is used, the testsuite elements have an extended attribute set ('id' and 'package') which are not present in the other formats.</li>
	 * </ul>
	 * 
	 * @param f
	 * @return a {@link JunitResult} which is one of {@link Testsuite}, {@link Testrun} or {@link Testsuites}
	 * @throws IOException
	 * @throws RuntimeException
	 *             with nested detail exception if there is an internal error.
	 */
	public static JunitResult loadFromXML(File f) throws IOException {
		try {
			return new JunitresultLoader().loadFromXMLFile(f);
		}
		catch(ParserConfigurationException e) {
			throw new RuntimeException(e);
		}
		catch(SAXException e) {
			throw new RuntimeException(e);
		}
	}

	private int getIntAttributeWith0Default(Element element, String attribute) {
		try {
			return Integer.valueOf(element.getAttribute(attribute));
		}
		catch(NumberFormatException e) {
			// ignore, will return 0
		}
		return 0;
	}

	/**
	 * Returns the node value of a child node of the given element tagged with the given tag.
	 * If no such child element exists <code>null</code> is returned.
	 * 
	 * @param element
	 * @param tag
	 * @return
	 */
	private String getTagValue(Element element, String tag) {
		NodeList children = element.getChildNodes();
		for(int i = 0; i < children.getLength(); i++) {
			Node n = children.item(i);
			if(n.getNodeType() == Node.ELEMENT_NODE && tag.equalsIgnoreCase(n.getNodeName())) {
				NodeList content = n.getChildNodes();
				Node node = content.item(0);
				return node == null
						? null
						: node.getNodeValue();
			}
		}
		return null;
	}

	private List<String> getTagValues(Element element, String tag) {
		List<String> result = Lists.newArrayList();
		NodeList children = element.getChildNodes();
		for(int i = 0; i < children.getLength(); i++) {
			Node n = children.item(i);
			if(n.getNodeType() == Node.ELEMENT_NODE && tag.equalsIgnoreCase(n.getNodeName())) {
				NodeList content = n.getChildNodes();
				Node node = content.item(0);
				if(node != null)
					result.add(node.getNodeValue());
			}
		}
		return result;

	}

	private double getTime(Element element, String attribute) {
		String t = element.getAttribute(attribute);
		if(Strings.isNullOrEmpty(t))
			return 0.0;
		try {
			return Math.abs(Double.parseDouble(t));
		}
		catch(NumberFormatException e) {
			return 0.0; // always return a time
		}
	}

	private Date getTimestamp(Element element, String attribute) {
		try {
			// jaxb parser has a useful method for parsing a timestamp in ISO8601 format
			Calendar calendar = javax.xml.bind.DatatypeConverter.parseDateTime(element.getAttribute(attribute));
			return calendar.getTime();

		}
		catch(IllegalArgumentException e) {
			// ouch - a date is always expected in ISO8601 form (gregorian)
			// be kind and do not fail - the spec is not formal.
			return new Date(); // pretend it was "now"
		}
	}

	private void loadAbstractAggregatedPart(AbstractAggregatedTest o, Element element) {
		o.setName(element.getAttribute("name"));
		o.setTests(getIntAttributeWith0Default(element, "tests"));
		o.setFailures(getIntAttributeWith0Default(element, "failures"));
		o.setErrors(getIntAttributeWith0Default(element, "errors"));
	}

	public JunitResult loadFromXMLFile(File f) throws ParserConfigurationException, SAXException, IOException {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();

		// set coalescing to true to make text and CDATA in nodes using concatenated into a single
		// text string.
		dbFactory.setCoalescing(true);

		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(f);

		Element docElement = doc.getDocumentElement();

		// There are three types of document elements possible:
		final String rootName = docElement.getNodeName();
		if("testrun".equalsIgnoreCase(rootName)) {
			return loadTestrun(docElement);
		}
		else if("testsuites".equalsIgnoreCase(rootName)) {
			return loadTestSuites(docElement);

		}
		else if("testsuite".equalsIgnoreCase(rootName)) {
			return loadTestSuite(docElement);
		}
		else {
			throw new SAXNotSupportedException("Can only load 'testrun', 'testsuites' or 'testsuite', but got: " +
					rootName);
		}
	}

	private void loadNegativeResult(NegativeResult o, NodeList negativeList) {
		Node negativeNode = negativeList.item(0);
		if(negativeNode != null) {
			Element errorElement = (Element) negativeNode;
			o.setMessage(errorElement.getAttribute("message"));
			o.setType(errorElement.getAttribute("type"));
			// value (if any) is in a child node
			NodeList children = errorElement.getChildNodes();
			Node n = children.item(0);
			if(n != null)
				o.setValue(n.getNodeValue());
		}
	}

	private Testcase loadTestCase(Element element) {
		if(!"testcase".equalsIgnoreCase(element.getNodeName()))
			throw new IllegalArgumentException("Non 'testcase' element passed to #loadTestCase");
		Testcase o = JunitresultFactory.eINSTANCE.createTestcase();

		o.setClassname(element.getAttribute("classname"));
		o.setName(element.getAttribute("name"));
		o.setTime(getTime(element, "time"));

		// Only one of these are allowed - error, failure, skipped
		// let the last win

		{
			// Error element
			NodeList errors = element.getElementsByTagName("error");
			for(int i = 0; i < errors.getLength(); i++) {
				Error error = JunitresultFactory.eINSTANCE.createError();
				loadNegativeResult(error, errors);
				o.getErrors().add(error);
			}
		}
		{
			// Failure element
			NodeList failures = element.getElementsByTagName("failure");
			for(int i = 0; i < failures.getLength(); i++) {
				Failure failure = JunitresultFactory.eINSTANCE.createFailure();
				loadNegativeResult(failure, failures);
				o.getFailures().add(failure);
			}
		}
		{
			// Failure element
			NodeList skipps = element.getElementsByTagName("skipped");
			if(skipps.getLength() > 0) {
				Skipped skipped = JunitresultFactory.eINSTANCE.createSkipped();
				loadNegativeResult(skipped, skipps);
				o.setSkipped(skipped);
			}
		}
		o.getSystem_err().addAll(getTagValues(element, "system-err"));
		o.getSystem_out().addAll(getTagValues(element, "system-out"));
		return o;

	}

	/**
	 * Loads a &lt;testrun&gt; element which is the format used by Eclipse JUnit result export.
	 * 
	 * @param element
	 * @return
	 */
	private Testrun loadTestrun(Element element) {
		if(!"testrun".equalsIgnoreCase(element.getNodeName())) {
			throw new IllegalArgumentException("Non 'testrun' element passed to #loadTestrun");
		}
		Testrun o = JunitresultFactory.eINSTANCE.createTestrun();
		loadAbstractAggregatedPart(o, element);
		o.setProject(element.getAttribute("project"));
		o.setStarted(getIntAttributeWith0Default(element, "started"));
		o.setIgnored(getIntAttributeWith0Default(element, "ignored"));

		// nested test suite(s)
		NodeList children = element.getChildNodes();
		for(int i = 0; i < children.getLength(); i++) {
			Node n = children.item(i);
			if(n.getNodeType() == Node.ELEMENT_NODE && "testsuite".equalsIgnoreCase(n.getNodeName()))
				o.getTestsuites().add(loadTestSuite((Element) n, false));
		}

		return o;
	}

	/**
	 * Loads a &lt;testsuite&gt; element in the form used by Eclipse testrun format, and when a testsuite is
	 * the document root. (This implies that the extended attributes found in testsuite elements embedded in the
	 * junitreport format are not parsed).
	 * 
	 * @param element
	 * @return
	 */
	private Testsuite loadTestSuite(Element element) {
		return loadTestSuite(element, false);
	}

	/**
	 * Loads a &lt;testsuite&gt; element in one of two alternate forms as directed by the parameter <code>extendedForm</code>.
	 * The extended form should be used when the element is part of a &lt;testsuites&gt; element as
	 * generated by junitreport.
	 * 
	 * @param element
	 * @param extendedForm
	 * @return
	 */
	private Testsuite loadTestSuite(Element element, boolean extendedForm) {
		Testsuite o = JunitresultFactory.eINSTANCE.createTestsuite();
		// super class part
		loadAbstractAggregatedPart(o, element);

		// attributes
		o.setSystem_err(getTagValue(element, "system-err"));
		o.setSystem_out(getTagValue(element, "system-out"));
		o.setHostname(element.getAttribute("hostname"));
		o.setTime(getTime(element, "time"));
		o.setTimestamp(getTimestamp(element, "timestamp"));

		// JUnit 4 - (?)
		o.setDisabled(getIntAttributeWith0Default(element, "disabled"));
		o.setSkipped(getIntAttributeWith0Default(element, "skipped"));

		// when embedded in a junitreport result where <testsuites> is the document root these two
		// attributes are present in each nested testsuite.
		//
		if(extendedForm) {
			o.setId(getIntAttributeWith0Default(element, "id"));
			o.setPackage(element.getAttribute("package"));
		}

		// child test suites (nested) & test cases
		NodeList children = element.getChildNodes();
		for(int i = 0; i < children.getLength(); i++) {
			Node n = children.item(i);
			if(n.getNodeType() == Node.ELEMENT_NODE)
				if("testsuite".equalsIgnoreCase(n.getNodeName()))
					o.getTestsuites().add(loadTestSuite((Element) n, extendedForm));
				else if("testcase".equalsIgnoreCase(n.getNodeName()))
					o.getTestcases().add(loadTestCase((Element) n));
				else if("properties".equalsIgnoreCase(n.getNodeName())) {
					NodeList properties = ((Element) n).getElementsByTagName("property");
					for(int j = 0; j < properties.getLength(); j++) {
						Node pn = properties.item(j);
						if(pn.getNodeType() == Node.ELEMENT_NODE) {
							Element propertyElement = (Element) pn;
							Property p = JunitresultFactory.eINSTANCE.createProperty();
							p.setName(propertyElement.getAttribute("name"));
							p.setValue(propertyElement.getAttribute("value"));
							o.getProperties().add(p);
						}
					}
				}
		}

		return o;
	}

	/**
	 * Loads a &lt;testsuites&gt; element as found in the result from a junitreport. All nested
	 * &lt;testsuite&gt; elements have extended attributes.
	 * 
	 * @param element
	 * @return
	 */
	private Testsuites loadTestSuites(Element element) {
		Testsuites o = JunitresultFactory.eINSTANCE.createTestsuites();
		loadAbstractAggregatedPart(o, element);
		o.setTime(getTime(element, "time"));
		o.setDisabled(getIntAttributeWith0Default(element, "disabled"));

		NodeList children = element.getChildNodes();
		for(int i = 0; i < children.getLength(); i++) {
			Node n = children.item(i);
			if(n.getNodeType() == Node.ELEMENT_NODE && "testsuite".equalsIgnoreCase(n.getNodeName()))
				o.getTestsuites().add(loadTestSuite((Element) n, true));
		}
		return o;
	}
}
