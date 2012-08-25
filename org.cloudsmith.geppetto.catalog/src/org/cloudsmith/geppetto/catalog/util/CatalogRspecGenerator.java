/**
 * Copyright (c) 2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.catalog.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map.Entry;
import java.util.SortedSet;
import java.util.TreeMap;

import org.cloudsmith.geppetto.catalog.Catalog;
import org.cloudsmith.geppetto.catalog.CatalogResource;
import org.cloudsmith.geppetto.catalog.CatalogResourceParameter;
import org.cloudsmith.geppetto.common.CharSequences;
import org.cloudsmith.geppetto.common.stats.IntegerCluster;

import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.collect.Iterables;
import com.google.common.collect.Maps;
import com.google.common.collect.TreeMultimap;

/**
 * Generates a Catalog in puppet-rspec "form" (essentially an executable asserter that a catalog for a host is
 * equal to a baseline catalog).
 * 
 */
public class CatalogRspecGenerator {

	private static class TitleComparator implements Comparator<CatalogResource> {

		@Override
		public int compare(CatalogResource a, CatalogResource b) {
			return a.getTitle().compareToIgnoreCase(b.getTitle());
		}

	}

	private static class TypeComparator implements Comparator<String> {

		@Override
		public int compare(String a, String b) {
			return a.compareToIgnoreCase(b);
		}

	}

	private static Function<String, String> fJavaStrToRubyStr = new Function<String, String>() {
		public String apply(String s) {
			StringBuilder builder = new StringBuilder(s.length() + 2);
			builder.append("'");
			builder.append(s);
			builder.append("'");
			return builder.toString();
		}
	};

	private String classNameOfResource(CatalogResource r) {
		if(!"Class".equals(r.getType()))
			throw new IllegalArgumentException("Can not produce classname from a resource of non Class type");
		return initialLowerCase(r.getTitle());
	}

	public void generate(Catalog catalog, Appendable out) throws IOException {
		headerBlurb(catalog, out);
		requirements(out);

		// Describe a host - that is what the entire catalog is related to, the "it" in Rspec tests
		// refers to a catalog compiled for this instance (don't want to do it over and over again)
		//
		out.append("describe '").append(catalog.getName()).append("', :type => :host do\n");
		// set empty facts and expect them to be injected - must be set to something to get them injected
		// TODO: this looks really stupid, how about having a magic "inject" fact ?
		//
		out.append(indent(1)).append("# Set facts to empty hash, and expect them to be injected\n");
		out.append(indent(1)).append("let(:facts) { { } }\n");
		out.append("\n");
		out.append(indent(1)).append("it {\n");
		generateAll(catalog, out);
		out.append(indent(1)).append("}\n");
		out.append("end\n");
	}

	public void generateAll(Catalog catalog, Appendable out) throws IOException {
		TreeMultimap<String, CatalogResource> sorted = TreeMultimap.create(new TypeComparator(), new TitleComparator());
		for(CatalogResource r : catalog.getResources()) {
			// transform these per type (sorted on type)
			sorted.put(r.getType(), r);
		}
		// classes are processed separately
		SortedSet<CatalogResource> classes = sorted.get("Class");
		generateClasses(classes, out);
		sorted.removeAll("Class");
		generateResources(sorted, out);

	}

	private void generateClasses(SortedSet<CatalogResource> classes, Appendable out) throws IOException {
		CharSequence indent = indent(2);
		out.append(indent).append("# Classes (in alphabetical order)\n");
		for(CatalogResource r : classes) {
			out.append(indent).append("should include_class('").append(classNameOfResource(r)).append("')\n");
		}
		out.append("\n");
	}

	/**
	 * @param sorted
	 * @param out
	 */
	private void generateResources(TreeMultimap<String, CatalogResource> sorted, Appendable out) throws IOException {
		out.append(indent(2)).append("# Resources per type and title (alphabetically)\n");

		boolean first = true;
		for(String type : sorted.keySet()) {
			if(!first) {
				out.append("\n");
				first = false;
			}
			String matcher = "contain_" + typeToMatcherName(type);
			for(CatalogResource r : sorted.get(type)) {
				out.append(indent(2)).append("should ").append(matcher).append("('").append(r.getTitle()).append("')");

				if(r.getParameters().size() < 1) {
					// TODO: There is no way to check that there are no additional parameters set
					// without explicitly listing all that should not be set
					out.append("\n");
				}
				else {
					// prepare data, a cluster for padding calculation, and a treemap for sorting parameters
					IntegerCluster cluster = new IntegerCluster(20);
					TreeMap<String, List<String>> sortedParameters = Maps.newTreeMap();
					for(CatalogResourceParameter p : r.getParameters()) {
						cluster.add(p.getName().length());
						sortedParameters.put(p.getName(), p.getValue());
					}
					out.append(".with(\n");

					for(Entry<String, List<String>> entry : sortedParameters.entrySet()) {
						String name = entry.getKey();
						out.append(indent(3)).append("'").append(name).append("'");
						out.append(padding(name, cluster)).append("=> ");
						List<String> values = entry.getValue();
						if(values.size() == 1) {
							out.append(fJavaStrToRubyStr.apply(values.get(0))).append(",\n");
						}
						else {
							// values is an array
							// TODO: It can also be a hash - need to see how that is encoded
							out.append("[");
							Joiner.on(',').appendTo(out, Iterables.transform(values, fJavaStrToRubyStr));
							out.append("],\n");
						}
					}
					out.append(indent(2)).append(")\n");
				}
			}
		}
	}

	public void headerBlurb(Catalog catalog, Appendable out) throws IOException {
		out.append("# Puppet RSpec Tests asserting that a catalog for a given host is compliant with\n");
		out.append("# rules derived from a baseline catalog obtained for host: '").append(catalog.getName()).append(
			"'\n");
		out.append("# Generated by: org.cloudsmith.geppetto.catalog.util.CatalogRspecGenerator\n");
		out.append("# Generated at: ").append(SimpleDateFormat.getDateInstance().format(new Date())).append("\n");
		out.append("\n");
	}

	private CharSequence indent(int nIndents) {
		return CharSequences.spaces(nIndents * 2);
	}

	private String initialLowerCase(String s) {
		StringBuilder builder = new StringBuilder(s);
		builder.setCharAt(0, Character.toLowerCase(builder.charAt(0)));
		return builder.toString();
	}

	/**
	 * Returns a sequences of spaces that pads the string s to it's cluster's max + 1
	 * 
	 * @param s
	 * @param cluster
	 * @return
	 */
	private CharSequence padding(String s, IntegerCluster cluster) {
		return CharSequences.spaces(cluster.clusterMax(s.length()) - s.length() + 1);
	}

	private void requirements(Appendable out) throws IOException {
		out.append("require 'spec_helper'\n");
		out.append("\n");
	}

	private String typeToMatcherName(String type) {
		return initialLowerCase(type.replaceAll("::", "__"));
	}
}
