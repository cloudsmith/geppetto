/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Puppet Labs
 */
package com.puppetlabs.geppetto.junitresult.util;

import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import com.puppetlabs.geppetto.junitresult.Error;
import com.puppetlabs.geppetto.junitresult.Failure;
import com.puppetlabs.geppetto.junitresult.JunitResult;
import com.puppetlabs.geppetto.junitresult.JunitresultPackage;
import com.puppetlabs.geppetto.junitresult.NegativeResult;
import com.puppetlabs.geppetto.junitresult.Testcase;
import com.puppetlabs.geppetto.junitresult.Testrun;
import com.puppetlabs.geppetto.junitresult.Testsuite;
import com.puppetlabs.geppetto.junitresult.Testsuites;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.google.common.base.Strings;

/**
 * Serializes a Junitresult model to XML DOM.
 * 
 */
public class JunitresultDomSerializer {

	private Document doc;

	/**
	 * Format time using as many digits as possible for seconds, and always three digits for ms.
	 */
	final private DecimalFormat timeFormat = new DecimalFormat("0.000");

	/**
	 * Appends an element with CDATA content to the given Node n with given elementName and containing
	 * given cdata. If cdata is null or empty, not element is appended to n.
	 * 
	 * @param n
	 * @param cdata
	 * @param elementName
	 */
	private void appendCDATANode(Node n, String cdata, String elementName) {
		if(Strings.isNullOrEmpty(cdata))
			return;
		Element cdataElement = doc.createElement(elementName);
		n.appendChild(cdataElement);
		cdataElement.appendChild(doc.createCDATASection(cdata));
	}

	private String dateToString(Date d) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d);

		return javax.xml.bind.DatatypeConverter.printDateTime(cal);

	}

	private String formatTime(double d) {
		return timeFormat.format(d);
	}

	/**
	 * @param negativeResult
	 * @return
	 */
	private String negativeResultToTag(NegativeResult negativeResult) {
		switch(negativeResult.eClass().getClassifierID()) {
			case JunitresultPackage.ERROR:
				return "error";
			case JunitresultPackage.FAILURE:
				return "failure";
			case JunitresultPackage.SKIPPED:
				return "skipped";
			default:
				throw new IllegalArgumentException("Not a supported negative result type");
		}
	}

	private void processNegativeResult(Node n, NegativeResult negativeResult) {
		if(negativeResult != null) {
			Element ne = doc.createElement(negativeResultToTag(negativeResult));
			ne.setAttribute("message", negativeResult.getMessage());
			ne.setAttribute("type", negativeResult.getType());
			if(!Strings.isNullOrEmpty(negativeResult.getValue())) {
				CDATASection value = doc.createCDATASection(negativeResult.getValue());
				ne.appendChild(value);
			}
			n.appendChild(ne);
		}
	}

	private void processSystemErr(Node n, String s) {
		appendCDATANode(n, s, "system-err");
	}

	private void processSystemOut(Node n, String s) {
		appendCDATANode(n, s, "system-out");
	}

	/**
	 * @param e
	 * @param tc
	 */
	private void processTestcase(Node n, Testcase tc) {
		Element e = doc.createElement("testcase");
		e.setAttribute("name", tc.getName());
		e.setAttribute("time", formatTime(tc.getTime()));
		if(!Strings.isNullOrEmpty(tc.getClassname()))
			e.setAttribute("classname", tc.getClassname());
		for(Error error : tc.getErrors())
			processNegativeResult(e, error);
		for(Failure failure : tc.getFailures())
			processNegativeResult(e, failure);
		if(tc.getSkipped() != null)
			processNegativeResult(e, tc.getSkipped());

		for(String s : tc.getSystem_out())
			processSystemOut(e, s);
		for(String s : tc.getSystem_err())
			processSystemErr(e, s);

		n.appendChild(e);
	}

	private void processTestsuite(Node n, Testsuite r) {
		Element e = doc.createElement("testsuite");
		e.setAttribute("name", r.getName());
		e.setAttribute("time", formatTime(r.getTime()));
		if(r.getTimestamp() != null)
			e.setAttribute("timestamp", dateToString(r.getTimestamp()));
		e.setAttribute("tests", Integer.toString(r.getTests()));
		e.setAttribute("errors", Integer.toString(r.getErrors()));
		e.setAttribute("failures", Integer.toString(r.getFailures()));
		// TODO: don't know what the correct choice is for ignore/disabled/skipped
		e.setAttribute("skipped", Integer.toString(r.getSkipped()));
		e.setAttribute("disabled", Integer.toString(r.getDisabled()));
		for(Testcase tc : r.getTestcases())
			processTestcase(e, tc);
		for(Testsuite ts : r.getTestsuites())
			processTestsuite(e, ts);

		processSystemOut(e, r.getSystem_out());
		processSystemErr(e, r.getSystem_err());

		n.appendChild(e);
	}

	/**
	 * Serializes and outputs the result as XML text to the given output stream using
	 * the default encoding.
	 * 
	 * @param r
	 * @param stream
	 * @throws TransformerException
	 * @throws ParserConfigurationException
	 */
	public void serialize(JunitResult r, OutputStream stream) throws TransformerException, ParserConfigurationException {
		serialize(r, stream, null);
	}

	/**
	 * Serializes and outputs the result as XML text to the given output stream.
	 * 
	 * @param r
	 * @param stream
	 * @param encoding
	 *            The encoding to use for the transformation or <code>null</code> for default
	 * @throws TransformerException
	 * @throws ParserConfigurationException
	 */
	public void serialize(JunitResult r, OutputStream stream, String encoding) throws TransformerException,
			ParserConfigurationException {
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(serializeToDom(r));

		StreamResult result = new StreamResult(stream);
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		if(encoding != null)
			transformer.setOutputProperty(OutputKeys.ENCODING, encoding);
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		transformer.transform(source, result);
	}

	/**
	 * Serializes a testrun as the root of the document.
	 * 
	 * @param r
	 */
	private void serializeTestrun(Testrun r) {
		Element e = doc.createElement("testrun");
		e.setAttribute("name", r.getName());
		e.setAttribute("tests", Integer.toString(r.getTests()));
		e.setAttribute("errors", Integer.toString(r.getErrors()));
		e.setAttribute("failures", Integer.toString(r.getFailures()));
		e.setAttribute("ignored", Integer.toString(r.getIgnored()));
		e.setAttribute("started", Integer.toString(r.getStarted()));
		for(Testsuite ts : r.getTestsuites())
			processTestsuite(e, ts);

		doc.appendChild(e);
	}

	/**
	 * Serializes a testsuite as the root of the document.
	 * 
	 * @param r
	 */
	private void serializeTestsuite(Testsuite r) {
		processTestsuite(doc, r);

	}

	/**
	 * Serializes a testsuites as the root of the document
	 * 
	 * @param r
	 */
	private void serializeTestsuites(Testsuites r) {
		Element e = doc.createElement("testsuites");
		e.setAttribute("name", r.getName());
		e.setAttribute("time", formatTime(r.getTime()));
		e.setAttribute("tests", Integer.toString(r.getTests()));
		e.setAttribute("errors", Integer.toString(r.getErrors()));
		e.setAttribute("failures", Integer.toString(r.getFailures()));
		e.setAttribute("disabled", Integer.toString(r.getDisabled()));
		for(Testsuite ts : r.getTestsuites())
			processTestsuite(e, ts);

		doc.appendChild(e);
	}

	public Document serializeToDom(JunitResult r) throws ParserConfigurationException {
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

		// root elements
		doc = docBuilder.newDocument();
		doc.setXmlStandalone(true); // informal, no validation should be performed

		switch(r.eClass().getClassifierID()) {
			case JunitresultPackage.TESTSUITE:
				serializeTestsuite((Testsuite) r);
				break;
			case JunitresultPackage.TESTRUN:
				serializeTestrun((Testrun) r);
				break;
			case JunitresultPackage.TESTSUITES:
				serializeTestsuites((Testsuites) r);
				break;
			default:
				throw new IllegalArgumentException("given JUnitResult is not one of the expected types.");
		}
		return doc;
	}

}
