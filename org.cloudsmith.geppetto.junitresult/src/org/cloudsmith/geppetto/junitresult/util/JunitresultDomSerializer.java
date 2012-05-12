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
package org.cloudsmith.geppetto.junitresult.util;

import java.io.OutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.cloudsmith.geppetto.junitresult.JunitResult;
import org.cloudsmith.geppetto.junitresult.JunitresultPackage;
import org.cloudsmith.geppetto.junitresult.NegativeResult;
import org.cloudsmith.geppetto.junitresult.Testcase;
import org.cloudsmith.geppetto.junitresult.Testrun;
import org.cloudsmith.geppetto.junitresult.Testsuite;
import org.cloudsmith.geppetto.junitresult.Testsuites;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import com.google.common.base.Strings;

/**
 * @author henrik
 * 
 */
public class JunitresultDomSerializer {

	private Document doc;

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

	/**
	 * @param e
	 * @param tc
	 */
	private void processTestcase(Node n, Testcase tc) {
		Element e = doc.createElement("testcase");
		e.setAttribute("name", tc.getName());
		e.setAttribute("time", tc.getTime());
		if(!Strings.isNullOrEmpty(tc.getClassname()))
			e.setAttribute("classname", tc.getClassname());
		NegativeResult negativeResult = tc.getNegativeResult();
		if(negativeResult != null) {
			Element ne = doc.createElement(negativeResultToTag(negativeResult));
			ne.setAttribute("message", negativeResult.getMessage());
			ne.setAttribute("type", negativeResult.getType());
			if(!Strings.isNullOrEmpty(negativeResult.getValue())) {
				CDATASection value = doc.createCDATASection(negativeResult.getValue());
				ne.appendChild(value);
			}
			e.appendChild(ne);
		}
		n.appendChild(e);
	}

	private void processTestsuite(Node n, Testsuite r) {
		Element e = doc.createElement("testsuite");
		e.setAttribute("name", r.getName());
		e.setAttribute("time", r.getTime());
		// TODO: Correct timeformat
		if(r.getTimestamp() != null)
			e.setAttribute("timestamp", r.getTimestamp().toString());
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

		n.appendChild(e);
	}

	/**
	 * Serializes and outputs the result as XML text to the given output stream.
	 * 
	 * @param r
	 * @param stream
	 * @throws TransformerException
	 * @throws ParserConfigurationException
	 */
	public void serialize(JunitResult r, OutputStream stream) throws TransformerException, ParserConfigurationException {
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		DOMSource source = new DOMSource(serializeToDom(r));

		StreamResult result = new StreamResult(stream);
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
		transformer.transform(source, result);
	}

	/**
	 * @param r
	 */
	private void serializeTestrun(Testrun r) {
		// Waiting
	}

	/**
	 * @param r
	 */
	private void serializeTestsuite(Testsuite r) {
		processTestsuite(doc, r);

	}

	/**
	 * @param r
	 */
	private void serializeTestsuites(Testsuites r) {
		// TODO: waiting with this until Testsuites has all the attributes
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
