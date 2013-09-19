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
package com.puppetlabs.geppetto.pp.dsl.tests.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.validation.AbstractValidationDiagnostic;
import org.junit.ComparisonFailure;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

/**
 * Helper class making it easier to assert content of a Resource.Diagnostic
 * 
 */
public class DiagnosticsAsserter {

	public interface DiagnosticPredicate extends Predicate<Diagnostic> {
		public DiagnosticPredicate greedy();

		public boolean isGreedy();

		public boolean isRequired();

		public DiagnosticPredicate optional();

	}

	protected static class IssuePredicate implements DiagnosticPredicate {
		protected String issueCode;

		protected boolean optional;

		protected boolean greedy;

		protected String msg;

		public IssuePredicate(String issueCode) {
			this(issueCode, null);
		}

		public IssuePredicate(String issueCode, String msg) {
			this.issueCode = issueCode;
			this.msg = msg;
			this.optional = false;
			this.greedy = false;
		}

		public boolean apply(Diagnostic d) {
			if(msg != null && d.getMessage() != null && !d.getMessage().contains(msg))
				return false;
			AbstractValidationDiagnostic issue = null;
			if(d instanceof AbstractValidationDiagnostic)
				issue = (AbstractValidationDiagnostic) d;

			if(issueCode != null && (issue == null || !issue.getIssueCode().equals(issueCode)))
				return false;
			return true;
		}

		public IssuePredicate greedy() {
			this.greedy = true;
			return this;
		}

		public boolean isGreedy() {
			return greedy;
		}

		public boolean isRequired() {
			return !optional;
		}

		public IssuePredicate optional() {
			this.optional = true;
			return this;
		}

		@Override
		public String toString() {
			List<String> r = new ArrayList<String>();
			if(issueCode != null)
				r.add("issueCode=" + issueCode);
			if(msg != null)
				r.add("msgFragment='" + msg + "'");
			return "(" + Joiner.on(" ").join(r) + ")";
		}
	}

	private XtextResource resource;

	public DiagnosticsAsserter(XtextResource resource) {
		this.resource = resource;
	}

	public void assertAll(DiagnosticPredicate... predicates) {
		assertAll(getAllDiagnostics(), predicates);
	}

	public void assertAll(Iterable<Diagnostic> asserted, DiagnosticPredicate predicates[]) {
		HashMap<DiagnosticPredicate, Boolean> consumed = new HashMap<DiagnosticPredicate, Boolean>();
		for(DiagnosticPredicate p : predicates)
			consumed.put(p, Boolean.FALSE);
		for(Diagnostic d : asserted) {
			boolean found = false;
			for(Entry<DiagnosticPredicate, Boolean> e : consumed.entrySet())
				if((!e.getValue() || e.getKey().isGreedy()) && e.getKey().apply(d)) {
					consumed.put(e.getKey(), Boolean.TRUE);
					found = true;
					break;
				}
			if(!found) {
				if(predicates.length == 1)
					throw new ComparisonFailure(
						"Predicate does not match", predicates[0].toString(), diagnosticsToString(d));
				throw new ComparisonFailure(
					"No predicate in expected matches", Arrays.toString(predicates), diagnosticsToString(d));
			}
		}
		ArrayList<DiagnosticPredicate> unconsumed = new ArrayList<DiagnosticPredicate>();
		for(Entry<DiagnosticPredicate, Boolean> e : consumed.entrySet())
			if(!e.getValue() && e.getKey().isRequired())
				unconsumed.add(e.getKey());
		if(unconsumed.size() != 0)
			throw new ComparisonFailure(
				"Missing diagnostics for required predicates", Arrays.toString(unconsumed.toArray()),
				diagnosticsToString(asserted));
	}

	public void assertAny(Iterable<Diagnostic> asserted, DiagnosticPredicate... predicates) {
		for(DiagnosticPredicate predicate : predicates)
			if(Iterables.any(asserted, predicate))
				return;
		throw new ComparisonFailure(
			"No predicate (any expected) matches diagnostics", Arrays.toString(predicates),
			diagnosticsToString(asserted));

	}

	/**
	 * Warnings and/or errors must be present.
	 */
	public void assertErrors() {
		List<Diagnostic> diagnostics = resource.getErrors();
		if(diagnostics.size() < 1)
			throw new ComparisonFailure("Error Diagnostics expected", "> 0 Diagnostics", "");
	}

	public void assertErrors(DiagnosticPredicate... predicates) {
		assertAll(resource.getErrors(), predicates);
	}

	/**
	 * No errors or warnings accepted.
	 * 
	 * @param message
	 * @param diag
	 */
	public void assertOk() {
		Iterable<Diagnostic> all = getAllDiagnostics();
		if(Iterables.size(all) > 0)
			throw new ComparisonFailure("No diagnostics expected", "No diagnostics", diagnosticsToString(all));
	}

	public void assertWarnings() {
		List<Diagnostic> diagnostics = resource.getWarnings();
		if(diagnostics.size() < 1)
			throw new ComparisonFailure("Warning Diagnostics expected", "> 0 Diagnostics", "");
	}

	public void assertWarnings(DiagnosticPredicate... predicates) {
		assertAll(resource.getWarnings(), predicates);
	}

	public String diagnosticsToString(Diagnostic d) {
		StringBuilder builder = new StringBuilder();
		builder.append(d.toString());
		builder.append("\n");
		return builder.toString();
	}

	public String diagnosticsToString(Iterable<Diagnostic> diag) {
		StringBuilder builder = new StringBuilder();
		for(Diagnostic d : diag) {
			AbstractValidationDiagnostic issue = null;
			if(d instanceof AbstractValidationDiagnostic)
				issue = (AbstractValidationDiagnostic) d;

			if(issue != null) {
				// ResourceExceptions, do not have severity
				// if(d.getSeverity() == Diagnostic.ERROR)
				// builder.append("ERROR ");
				// else
				// builder.append("WARNING ");
				AbstractValidationDiagnostic detail = issue;
				builder.append("issueCode=");
				builder.append(detail.getIssueCode());
				builder.append(" ");
				builder.append("msg='");
				builder.append(d.getMessage());
				builder.append("'");
			}
			else
				builder.append(d.toString());
			builder.append("\n");
		}
		return builder.toString();
	}

	public Iterable<Diagnostic> getAllDiagnostics() {
		return Iterables.concat(resource.getWarnings(), resource.getErrors());
	}

	public DiagnosticPredicate issue(String issueCode) {
		return new IssuePredicate(issueCode, null);
	}

	public DiagnosticPredicate issue(String issueCode, String messageFragment) {
		return new IssuePredicate(issueCode, messageFragment);
	}

	public DiagnosticPredicate messageFragment(String messageFragment) {
		return new IssuePredicate(null, messageFragment);
	}

}
