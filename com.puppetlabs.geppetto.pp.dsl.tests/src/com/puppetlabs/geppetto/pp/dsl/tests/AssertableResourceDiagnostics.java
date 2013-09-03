package com.puppetlabs.geppetto.pp.dsl.tests;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

import org.eclipse.emf.ecore.resource.Resource.Diagnostic;
import org.eclipse.xtext.diagnostics.AbstractDiagnostic;

import com.google.common.base.Joiner;
import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;

/**
 * Provides convenient predicates and assertions for testing Resource.Diganostic.
 * Use one instance of this class for {@link Resource#getErrors()}, and another for {@link Resource#getWarnings()}.
 * 
 */
public class AssertableResourceDiagnostics {

	public interface DiagnosticPredicate extends Predicate<Diagnostic> {

	}

	protected static class Pred implements DiagnosticPredicate {
		protected String issueCode;

		protected String msg;

		public Pred(String issueCode, String msg) {
			super();
			this.issueCode = issueCode;
			this.msg = msg;
		}

		public boolean apply(Diagnostic d) {
			if(msg != null && d.getMessage() != null && !d.getMessage().contains(msg))
				return false;

			if(!(d instanceof AbstractDiagnostic))
				return false;
			if(issueCode != null && !((AbstractDiagnostic) d).getCode().equals(issueCode))
				return false;
			return true;
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

	public static Pred diagnostic(String issueCode) {
		return new Pred(issueCode, null);
	}

	public static Pred diagnostic(String issueCode, String messageFragment) {
		return new Pred(issueCode, messageFragment);
	}

	protected List<Diagnostic> diag;

	public AssertableResourceDiagnostics(List<Diagnostic> diagnostics) {
		this.diag = diagnostics;
	}

	public void assertAll(DiagnosticPredicate... predicates) {
		HashMap<DiagnosticPredicate, Boolean> consumed = new HashMap<DiagnosticPredicate, Boolean>();
		for(DiagnosticPredicate p : predicates)
			consumed.put(p, Boolean.FALSE);
		for(Diagnostic d : getAllDiagnostics()) {
			boolean found = false;
			for(Entry<DiagnosticPredicate, Boolean> e : consumed.entrySet())
				if(!e.getValue() && e.getKey().apply(d)) {
					consumed.put(e.getKey(), Boolean.TRUE);
					found = true;
					break;
				}
			if(!found) {
				if(predicates.length == 1)
					fail("Predicate " + predicates[0] + " does not match " + d);
				else
					fail("No predicate in " + Arrays.toString(predicates) + " matches " + d);
			}
		}
		ArrayList<DiagnosticPredicate> unconsumed = new ArrayList<DiagnosticPredicate>();
		for(Entry<DiagnosticPredicate, Boolean> e : consumed.entrySet())
			if(!e.getValue())
				unconsumed.add(e.getKey());
		if(unconsumed.size() != 0)
			fail("There are diagnostics missing for theses predicates: " + unconsumed);
	}

	public AssertableResourceDiagnostics assertAny(DiagnosticPredicate... predicates) {
		for(DiagnosticPredicate predicate : predicates)
			if(Iterables.any(getAllDiagnostics(), predicate))
				return this;
		fail("predicate not found");
		return this;
	}

	public void assertDiagnostic(String issueCode) {
		assertAll(diagnostic(issueCode, null));
	}

	public void assertDiagnostic(String issueCode, String messageFragment) {
		assertAll(diagnostic(issueCode, messageFragment));
	}

	public void assertDiagnosticMsg(String messageFragment) {
		assertAll(diagnostic(null, messageFragment));
	}

	public AssertableResourceDiagnostics assertDiagnosticsCount(int size) {
		int count = Iterables.size(getAllDiagnostics());
		if(count == size)
			return this;
		fail("There are " + count + " diagnostics, but " + size + " are expected.");
		return this;
	}

	public void assertOK() {
		if(diag.size() != 0)
			fail("There are expected to be no diagnostics.");
	}

	public void fail(String message) {
		throw new AssertionError(message);
	}

	public Iterable<Diagnostic> getAllDiagnostics() {
		return new Iterable<Diagnostic>() {

			@Override
			public Iterator<Diagnostic> iterator() {
				return diag.iterator();
			}

		};
	}

	protected void printDiagnostic(StringBuffer out, String prefix, Diagnostic d) {
		// very simplistic...
		out.append(prefix);
		out.append(d);
		out.append("\n");
	}

	@Override
	public String toString() {
		StringBuffer b = new StringBuffer();
		for(Diagnostic d : diag)
			printDiagnostic(b, "", d);
		return b.toString();
	}
}
