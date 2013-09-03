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
package com.puppetlabs.geppetto.validation.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import junit.framework.ComparisonFailure;

import com.puppetlabs.geppetto.diagnostic.DetailedFileDiagnostic;
import com.puppetlabs.geppetto.diagnostic.Diagnostic;
import com.puppetlabs.geppetto.diagnostic.FileDiagnostic;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

/**
 * Helper class making it easier to assert content of a BasicDiagnostic
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
			return issueCode == null || issueCode.equals(d.getIssue());
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

	private Diagnostic diagnostics;

	public DiagnosticsAsserter(Diagnostic diag) {
		diagnostics = diag;
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
	public void assertDiagnostics() {
		if(diagnostics.getChildren().size() < 1)
			throw new ComparisonFailure("Diagnostics expected", "> 0 Diagnostics", "");
	}

	public void assertErrors(DiagnosticPredicate... predicates) {
		assertAll(getErrorDiagnostics(), predicates);
	}

	/**
	 * No errors or warnings accepted.
	 * 
	 * @param message
	 * @param diag
	 */
	public void assertOk() {
		if(diagnostics.getChildren().size() > 0)
			throw new ComparisonFailure(
				"No diagnostics expected", "No diagnostics", diagnosticsToString(diagnostics.getChildren()));
	}

	public void assertWarnings(DiagnosticPredicate... predicates) {
		assertAll(getWarningDiagnostics(), predicates);
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
			builder.append("issueCode=");
			builder.append(d.getIssue());
			builder.append(" msg='");
			builder.append(d.getMessage());
			builder.append('\'');
			if(d instanceof FileDiagnostic) {
				FileDiagnostic fd = (FileDiagnostic) d;
				builder.append(" file=");
				builder.append(fd.getFile());
				builder.append(" line=");
				builder.append(fd.getLineNumber());
				if(fd instanceof DetailedFileDiagnostic) {
					DetailedFileDiagnostic dfd = (DetailedFileDiagnostic) fd;
					builder.append(" offset=");
					builder.append(dfd.getOffset());
					builder.append(" length=");
					builder.append(dfd.getLength());
				}
			}
			else
				builder.append(d.toString());
			builder.append("\n");
		}
		return builder.toString();
	}

	public Iterable<Diagnostic> getAllDiagnostics() {
		return diagnostics;
	}

	public Iterable<Diagnostic> getErrorDiagnostics() {
		return severityIterable(Diagnostic.ERROR, diagnostics);
	}

	public Iterable<Diagnostic> getWarningDiagnostics() {
		return severityIterable(Diagnostic.WARNING, diagnostics);
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

	private Iterable<Diagnostic> severityIterable(final int severity, Diagnostic diag) {
		return Iterables.filter(diag, new Predicate<Diagnostic>() {

			@Override
			public boolean apply(Diagnostic input) {
				return input.getSeverity() == severity;
			}

		});
	}
}
