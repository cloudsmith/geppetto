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
package org.cloudsmith.geppetto.forge.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.cloudsmith.geppetto.forge.v2.model.Dependency;
import org.cloudsmith.geppetto.forge.v2.model.Metadata;
import org.cloudsmith.geppetto.forge.v2.model.ModuleName;
import org.cloudsmith.geppetto.forge.v2.model.NamedTypeItem;
import org.cloudsmith.geppetto.forge.v2.model.Type;
import org.cloudsmith.geppetto.semver.Version;
import org.cloudsmith.geppetto.semver.VersionRange;

/**
 * A metadata.json parser that only accepts strict entries and adds them
 * to a Metadata instance
 */
public class StrictMetadataJsonParser extends MetadataJsonParser {

	private static ModuleName createModuleName(String name) {
		if(name == null)
			return null;
		try {
			return new ModuleName(name, false);
		}
		catch(IllegalArgumentException e) {
			return null;
		}
	}

	private static int hexToByte(char c) {
		return c >= 'a'
				? c - ('a' - 10)
				: c - '0';
	}

	private final Metadata md;

	public StrictMetadataJsonParser(Metadata md) {
		this.md = md;
	}

	@Override
	protected void call(CallSymbol key, int line, int offset, int length, List<JElement> args) {
		switch(key) {
			case types: {
				List<Type> types = new ArrayList<Type>(args.size());
				for(JElement jsonType : args) {
					Type type = createType(jsonType);
					if(type != null)
						types.add(type);
				}
				md.setTypes(types);
				break;
			}
			case dependencies: {
				List<Dependency> deps = new ArrayList<Dependency>(args.size());
				for(JElement jsonDep : args) {
					Dependency dep = createDependency(jsonDep);
					if(dep != null)
						deps.add(dep);
				}
				md.setDependencies(deps);
				break;
			}
			case checksums:
				if(args.size() == 1) {
					JObject jsonChecksums = (JObject) args.get(0);
					Map<String, byte[]> checksums = new HashMap<String, byte[]>();
					for(JEntry jsonChecksum : jsonChecksums.getEntries()) {
						byte[] checksum = createChecksum(jsonChecksum.getElement());
						if(checksum != null)
							checksums.put(jsonChecksum.getKey(), checksum);
					}
					md.setChecksums(checksums);
				}
				break;

			default:
				if(args.size() != 1)
					return;

				String arg = args.get(0).toStringOrNull();
				switch(key) {
					case author:
						md.setAuthor(arg);
						break;
					case description:
						md.setDescription(arg);
						break;
					case license:
						md.setLicense(arg);
						break;
					case name:
						md.setName(createModuleName(arg));
						break;
					case project_page:
						md.setProjectPage(arg);
						break;
					case source:
						md.setSource(arg);
						break;
					case summary:
						md.setSummary(arg);
						break;
					case version:
						try {
							md.setVersion(Version.create(arg));
						}
						catch(IllegalArgumentException e) {
						}
				}
				break;
		}
	}

	private byte[] createChecksum(JElement value) {
		String hexString = value.toStringOrNull();
		if(hexString != null && hexString.length() > 4) {
			int top = hexString.length() / 2;
			byte[] bytes = new byte[top];
			for(int idx = 0, cidx = 0; idx < top; ++idx) {
				int val = hexToByte(hexString.charAt(cidx++)) << 4;
				val |= hexToByte(hexString.charAt(cidx++));
				bytes[idx] = (byte) (val & 0xff);
			}
			return bytes;
		}
		return null;
	}

	private Dependency createDependency(JElement jsonDep) {
		if(!(jsonDep instanceof JObject))
			return null;

		String name = null;
		String vreq = null;
		for(JEntry entry : ((JObject) jsonDep).getEntries()) {
			String key = entry.getKey();
			if("name".equals(key))
				name = entry.getElement().toStringOrNull();
			else if("version_requirement".equals(key) || "versionRequirement".equals(key))
				vreq = entry.getElement().toStringOrNull();
		}
		ModuleName mname = createModuleName(name);
		if(mname == null)
			return null;

		Dependency dep = new DependencyWithPosition(jsonDep);
		dep.setName(mname);
		try {
			dep.setVersionRequirement(VersionRange.create(vreq));
		}
		catch(IllegalArgumentException e) {
		}
		return dep;
	}

	private NamedTypeItem createNamedTypeItem(JElement jsonItem) {
		if(!(jsonItem instanceof JObject))
			return null;

		String name = null;
		String doc = null;
		for(JEntry entry : ((JObject) jsonItem).getEntries()) {
			String key = entry.getKey();
			if("name".equals(key))
				name = entry.getElement().toStringOrNull();
			else if("doc".equals(key))
				doc = entry.getElement().toStringOrNull();
		}
		if(name != null) {
			NamedTypeItem item = new NamedTypeItem();
			item.setName(name);
			item.setDocumentation(doc);
			return item;
		}
		return null;
	}

	private List<NamedTypeItem> createNamedTypeItemList(JElement element) {
		if(!(element instanceof JArray))
			return null;

		ArrayList<NamedTypeItem> items = new ArrayList<NamedTypeItem>();
		for(JElement item : ((JArray) element).getValues())
			items.add(createNamedTypeItem(item));
		return items;
	}

	private Type createType(JElement jsonType) {
		if(!(jsonType instanceof JObject))
			return null;

		String name = null;
		String doc = null;
		List<NamedTypeItem> parameters = null;
		List<NamedTypeItem> providers = null;
		List<NamedTypeItem> properties = null;
		for(JEntry entry : ((JObject) jsonType).getEntries()) {
			String key = entry.getKey();
			JElement val = entry.getElement();
			if("name".equals(key))
				name = val.toStringOrNull();
			else if("doc".equals(key))
				doc = val.toStringOrNull();
			else if("parameters".equals(key))
				parameters = createNamedTypeItemList(val);
			else if("properties".equals(key))
				properties = createNamedTypeItemList(val);
			else if("providers".equals(key))
				providers = createNamedTypeItemList(val);
		}
		if(name == null)
			return null;

		Type type = new Type();
		type.setName(name);
		type.setDocumentation(doc);
		type.setParameters(parameters);
		type.setProperties(properties);
		type.setProviders(providers);
		return type;
	}
}
