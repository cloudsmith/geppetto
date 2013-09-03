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
package com.puppetlabs.geppetto.forge.util;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.puppetlabs.geppetto.forge.v2.model.NamedTypeItem;
import com.puppetlabs.geppetto.forge.v2.model.Type;
import org.jrubyparser.ast.BlockAcceptingNode;
import org.jrubyparser.ast.CallNode;
import org.jrubyparser.ast.Colon2ConstNode;
import org.jrubyparser.ast.ConstNode;
import org.jrubyparser.ast.FCallNode;
import org.jrubyparser.ast.IArgumentNode;
import org.jrubyparser.ast.InstAsgnNode;
import org.jrubyparser.ast.ModuleNode;
import org.jrubyparser.ast.Node;
import org.jrubyparser.ast.NodeType;
import org.jrubyparser.ast.RootNode;
import org.jrubyparser.ast.StrNode;
import org.jrubyparser.ast.SymbolNode;

/**
 * @author thhal
 * 
 */
public class Types {
	public static void loadProvider(Type type, File providerDir) throws IOException {
		providerDir = new File(providerDir, type.getName());

		File[] providerFiles = providerDir.listFiles();
		if(providerFiles == null || providerFiles.length == 0)
			// That's OK. It's optional
			return;

		ArrayList<NamedTypeItem> providers = null;

		for(File providerFile : providerFiles) {
			String providerFileName = providerFile.getName();
			if(!providerFileName.endsWith(".rb"))
				continue;

			for(Node node : RubyParserUtils.findNodes(
				RubyParserUtils.parseFile(providerFile).getBody(), new NodeType[] { NodeType.CALLNODE })) {
				CallNode call = (CallNode) node;
				if(!"provide".equals(call.getName()))
					continue;

				Node receiverNode = call.getReceiver();
				if(!(receiverNode instanceof CallNode))
					continue;

				CallNode receiver = (CallNode) receiverNode;
				if(!"type".equals(receiver.getName()))
					continue;
				Node recRecNode = receiver.getReceiver();
				if(!(recRecNode instanceof Colon2ConstNode))
					continue;
				Colon2ConstNode recRec = (Colon2ConstNode) recRecNode;
				if(!("Puppet".equals(((ConstNode) recRec.getLeftNode()).getName()) && "Type".equals(recRec.getName())))
					continue;

				// Receiver is Puppet::Type.type
				List<Node> symArgs = RubyParserUtils.findNodes(
					receiver.getArgs(), new NodeType[] { NodeType.SYMBOLNODE });
				if(!(symArgs.size() == 1 && type.getName().equals(((SymbolNode) symArgs.get(0)).getName())))
					// Not this type
					continue;

				symArgs = RubyParserUtils.findNodes(call.getArgs(), new NodeType[] { NodeType.SYMBOLNODE });
				if(symArgs.isEmpty())
					continue;

				NamedTypeItem provider = new NamedTypeItem();
				provider.setName(((SymbolNode) symArgs.get(0)).getName());
				if(providers == null)
					providers = new ArrayList<NamedTypeItem>();
				providers.add(provider);

				List<Node> calls = RubyParserUtils.findNodes(call.getIter(), new NodeType[] {
						NodeType.BLOCKNODE, NodeType.FCALLNODE });
				if(calls.isEmpty())
					calls = RubyParserUtils.findNodes(call.getIter(), new NodeType[] { NodeType.FCALLNODE });
				if(!calls.isEmpty()) {
					for(Node snode : calls) {
						FCallNode subCall = (FCallNode) snode;
						if("desc".equals(subCall.getName())) {
							List<Node> strArgs = RubyParserUtils.findNodes(
								subCall.getArgs(), new NodeType[] { NodeType.STRNODE });
							if(strArgs.size() >= 1)
								provider.setDocumentation(((StrNode) strArgs.get(0)).getValue());
							break;
						}
					}
				}
			}
		}
		type.setProviders(providers);
	}

	public static void loadTypeFile(Type type, File typeFile) throws IOException {
		String typeFileStr = typeFile.getAbsolutePath();
		RootNode root = RubyParserUtils.parseFile(typeFile);
		List<Node> nodes = RubyParserUtils.findNodes(root.getBody(), new NodeType[] { NodeType.MODULENODE });
		ModuleNode puppetModule = null;
		for(Node node : nodes) {
			ModuleNode module = (ModuleNode) node;
			if("Puppet".equals(module.getCPath().getName())) {
				puppetModule = module;
				break;
			}
		}

		BlockAcceptingNode newtypeNode = null;
		if(puppetModule != null) {
			// Find the newtype call
			nodes = RubyParserUtils.findNodes(puppetModule.getBody(), new NodeType[] { NodeType.CALLNODE });
			for(Node node : nodes) {
				CallNode call = (CallNode) node;
				if("newtype".equals(call.getName())) {
					Node receiver = call.getReceiver();
					if(receiver instanceof ConstNode && "Type".equals(((ConstNode) receiver).getName())) {
						newtypeNode = call;
						break;
					}
				}
			}
			if(newtypeNode == null) {
				// Try syntax found in iptables.rb. Not sure it's correct
				// but it seems to be parsed
				// OK by the puppet-tool
				nodes = RubyParserUtils.findNodes(puppetModule.getBody(), new NodeType[] { NodeType.FCALLNODE });
				for(Node node : nodes) {
					FCallNode call = (FCallNode) node;
					if("newtype".equals(call.getName())) {
						newtypeNode = call;
						break;
					}
				}
			}
		}
		else {
			// The call might be a CallNode at the top level
			nodes = RubyParserUtils.findNodes((root).getBody(), new NodeType[] { NodeType.CALLNODE });
			for(Node node : nodes) {
				CallNode call = (CallNode) node;
				if("newtype".equals(call.getName())) {
					Node receiver = call.getReceiver();
					if(receiver instanceof Colon2ConstNode) {
						Colon2ConstNode c2cNode = (Colon2ConstNode) receiver;
						if("Type".equals(c2cNode.getName()) && c2cNode.getLeftNode() instanceof ConstNode &&
								"Puppet".equals(((ConstNode) c2cNode.getLeftNode()).getName())) {
							newtypeNode = call;
							break;
						}
					}
				}
			}
		}

		if(newtypeNode == null)
			throw new IOException("Unable to find newtype call in " + typeFileStr);

		// Find the parameter that is passed in the call to newtype. It must
		// be one
		// single parameter in the form of a Symbol. This Symbol denotes the
		// name of
		// the new type.
		Node argsNode = ((IArgumentNode) newtypeNode).getArgs();
		nodes = RubyParserUtils.findNodes(argsNode, new NodeType[] { NodeType.SYMBOLNODE });
		if(nodes.size() != 1)
			throw new IOException("The newtype call does not take exactly one symbol parameter in " + typeFileStr);

		SymbolNode typeName = (SymbolNode) nodes.get(0);
		type.setName(typeName.getName());

		// Find the assignment of the @doc instance variable
		Node iterNode = newtypeNode.getIter();
		nodes = RubyParserUtils.findNodes(iterNode, new NodeType[] { NodeType.BLOCKNODE, NodeType.INSTASGNNODE });
		if(nodes.isEmpty())
			// No block when there's just one assignment
			nodes = RubyParserUtils.findNodes(iterNode, new NodeType[] { NodeType.INSTASGNNODE });

		for(Node node : nodes) {
			InstAsgnNode asgnNode = (InstAsgnNode) node;
			if(!"@doc".equals(asgnNode.getName()))
				continue;

			Node valueNode = asgnNode.getValue();
			if(valueNode instanceof StrNode)
				type.setDocumentation(((StrNode) valueNode).getValue());
			break;
		}

		// Find the calls to newparam (receiver is the instance returned by
		// newtype)
		nodes = RubyParserUtils.findNodes(iterNode, new NodeType[] { NodeType.BLOCKNODE, NodeType.FCALLNODE });
		if(nodes.isEmpty())
			// No block when there's just one call
			nodes = RubyParserUtils.findNodes(iterNode, new NodeType[] { NodeType.FCALLNODE });

		ArrayList<NamedTypeItem> parameters = null;
		ArrayList<NamedTypeItem> properties = null;

		for(Node node : nodes) {
			FCallNode callNode = (FCallNode) node;
			boolean isParam = "newparam".equals(callNode.getName());
			if(!isParam && !"newproperty".equals(callNode.getName()))
				continue;

			List<Node> pnodes = RubyParserUtils.findNodes(callNode.getArgs(), new NodeType[] { NodeType.SYMBOLNODE });
			if(pnodes.size() != 1)
				throw new IOException("A newparam or newproperty call does not take exactly one symbol parameter in " +
						typeFileStr);

			NamedTypeItem elem = new NamedTypeItem();
			if(isParam) {
				if(parameters == null)
					parameters = new ArrayList<NamedTypeItem>();
				parameters.add(elem);
			}
			else {
				if(properties == null)
					properties = new ArrayList<NamedTypeItem>();
				properties.add(elem);
			}

			elem.setName(((SymbolNode) pnodes.get(0)).getName());
			iterNode = callNode.getIter();
			pnodes = RubyParserUtils.findNodes(iterNode, new NodeType[] { NodeType.BLOCKNODE, NodeType.FCALLNODE });
			if(pnodes.isEmpty())
				// No block when there's just one call
				pnodes = RubyParserUtils.findNodes(iterNode, new NodeType[] { NodeType.FCALLNODE });

			for(Node pnode : pnodes) {
				FCallNode pcallNode = (FCallNode) pnode;
				if("desc".equals(pcallNode.getName())) {
					List<Node> args = pcallNode.getArgs().childNodes();
					if(args.size() != 1)
						throw new IOException(
							"A newparam or newproperty desc call does not take exactly one parameter in " + typeFileStr);
					elem.setDocumentation(RubyParserUtils.stringValue(args.get(0)));
					break;
				}
			}
		}
		type.setParameters(parameters);
		type.setProperties(properties);
	}

	public static List<Type> loadTypes(File puppetDir, FileFilter exclusionFilter) throws IOException {
		if(exclusionFilter == null)
			exclusionFilter = ModuleUtils.DEFAULT_FILE_FILTER;
		File[] typeFiles = new File(puppetDir, "type").listFiles(exclusionFilter);
		if(typeFiles == null || typeFiles.length == 0)
			return Collections.emptyList();

		List<Type> typeList = new ArrayList<Type>();
		for(File typeFile : typeFiles) {
			if(!typeFile.getName().endsWith(".rb"))
				continue;
			Type type = new Type();
			loadTypeFile(type, typeFile);
			typeList.add(type);
		}

		if(typeList.isEmpty())
			return Collections.emptyList();

		File providerDir = new File(puppetDir, "provider");
		for(Type type : typeList)
			loadProvider(type, providerDir);
		return typeList;
	}

}
