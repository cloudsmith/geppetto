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
package org.cloudsmith.geppetto.forge.impl;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

import org.cloudsmith.geppetto.forge.Documented;
import org.cloudsmith.geppetto.forge.ForgeFactory;
import org.cloudsmith.geppetto.forge.ForgePackage;
import org.cloudsmith.geppetto.forge.Parameter;
import org.cloudsmith.geppetto.forge.Property;
import org.cloudsmith.geppetto.forge.Provider;
import org.cloudsmith.geppetto.forge.Type;
import org.cloudsmith.geppetto.forge.util.JsonUtils;
import org.cloudsmith.geppetto.forge.util.RubyParserUtils;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
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

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.Expose;

/**
 * <!-- begin-user-doc --> An implementation of the model object ' <em><b>Type</b></em>'. <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.forge.impl.TypeImpl#getParameters <em>Parameters</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.impl.TypeImpl#getProperties <em>Properties</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.impl.TypeImpl#getProviders <em>Providers</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class TypeImpl extends DocumentedImpl implements Type {
	public static class Deserializer extends JsonUtils.ContainerDeserializer<Type> {
		@Override
		public Type deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			Type result = ForgeFactory.eINSTANCE.createType();
			JsonObject jsonObj = json.getAsJsonObject();
			json = jsonObj.get("name");
			if(json != null)
				result.setName(json.getAsString());
			json = jsonObj.get("doc");
			if(json != null)
				result.setDoc(json.getAsString());
			json = jsonObj.get("parameters");
			if(json != null)
				deserializeInto(json, result.getParameters(), ParameterImpl.class, context);
			json = jsonObj.get("properties");
			if(json != null)
				deserializeInto(json, result.getProperties(), PropertyImpl.class, context);
			json = jsonObj.get("providers");
			if(json != null)
				deserializeInto(json, result.getProviders(), ProviderImpl.class, context);
			return result;
		}
	}

	/**
	 * The cached value of the '{@link #getParameters() <em>Parameters</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getParameters()
	 * @generated
	 * @ordered
	 */
	@Expose
	protected EList<Parameter> parameters;

	/**
	 * The cached value of the '{@link #getProperties() <em>Properties</em>}' containment reference list.
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @see #getProperties()
	 * @generated
	 * @ordered
	 */
	@Expose
	protected EList<Property> properties;

	/**
	 * The cached value of the '{@link #getProviders() <em>Providers</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getProviders()
	 * @generated
	 * @ordered
	 */
	@Expose
	protected EList<Provider> providers;

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected TypeImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch(featureID) {
			case ForgePackage.TYPE__PARAMETERS:
				return getParameters();
			case ForgePackage.TYPE__PROPERTIES:
				return getProperties();
			case ForgePackage.TYPE__PROVIDERS:
				return getProviders();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch(featureID) {
			case ForgePackage.TYPE__PARAMETERS:
				return ((InternalEList<?>) getParameters()).basicRemove(otherEnd, msgs);
			case ForgePackage.TYPE__PROPERTIES:
				return ((InternalEList<?>) getProperties()).basicRemove(otherEnd, msgs);
			case ForgePackage.TYPE__PROVIDERS:
				return ((InternalEList<?>) getProviders()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch(featureID) {
			case ForgePackage.TYPE__PARAMETERS:
				return parameters != null && !parameters.isEmpty();
			case ForgePackage.TYPE__PROPERTIES:
				return properties != null && !properties.isEmpty();
			case ForgePackage.TYPE__PROVIDERS:
				return providers != null && !providers.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch(featureID) {
			case ForgePackage.TYPE__PARAMETERS:
				getParameters().clear();
				getParameters().addAll((Collection<? extends Parameter>) newValue);
				return;
			case ForgePackage.TYPE__PROPERTIES:
				getProperties().clear();
				getProperties().addAll((Collection<? extends Property>) newValue);
				return;
			case ForgePackage.TYPE__PROVIDERS:
				getProviders().clear();
				getProviders().addAll((Collection<? extends Provider>) newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch(featureID) {
			case ForgePackage.TYPE__PARAMETERS:
				getParameters().clear();
				return;
			case ForgePackage.TYPE__PROPERTIES:
				getProperties().clear();
				return;
			case ForgePackage.TYPE__PROVIDERS:
				getProviders().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Parameter> getParameters() {
		if(parameters == null) {
			parameters = new EObjectContainmentEList<Parameter>(Parameter.class, this, ForgePackage.TYPE__PARAMETERS);
		}
		return parameters;
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Property> getProperties() {
		if(properties == null) {
			properties = new EObjectContainmentEList<Property>(Property.class, this, ForgePackage.TYPE__PROPERTIES);
		}
		return properties;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Provider> getProviders() {
		if(providers == null) {
			providers = new EObjectContainmentEList<Provider>(Provider.class, this, ForgePackage.TYPE__PROVIDERS);
		}
		return providers;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public void loadProvider(File providerDir) throws IOException {
		providerDir = new File(providerDir, getName());

		File[] providerFiles = providerDir.listFiles();
		if(providerFiles == null || providerFiles.length == 0)
			// That's OK. It's optional
			return;

		for(File providerFile : providerFiles) {
			String providerFileName = providerFile.getName();
			if(!providerFileName.endsWith(".rb"))
				continue;

			for(Node node : RubyParserUtils.findNodes(
				RubyParserUtils.parseFile(providerFile).getBodyNode(), new NodeType[] { NodeType.CALLNODE })) {
				CallNode call = (CallNode) node;
				if(!"provide".equals(call.getName()))
					continue;

				Node receiverNode = call.getReceiverNode();
				if(!(receiverNode instanceof CallNode))
					continue;

				CallNode receiver = (CallNode) receiverNode;
				if(!"type".equals(receiver.getName()))
					continue;
				Node recRecNode = receiver.getReceiverNode();
				if(!(recRecNode instanceof Colon2ConstNode))
					continue;
				Colon2ConstNode recRec = (Colon2ConstNode) recRecNode;
				if(!("Puppet".equals(((ConstNode) recRec.getLeftNode()).getName()) && "Type".equals(recRec.getName())))
					continue;

				// Receiver is Puppet::Type.type
				List<Node> symArgs = RubyParserUtils.findNodes(
					receiver.getArgsNode(), new NodeType[] { NodeType.SYMBOLNODE });
				if(!(symArgs.size() == 1 && getName().equals(((SymbolNode) symArgs.get(0)).getName())))
					// Not this type
					continue;

				symArgs = RubyParserUtils.findNodes(call.getArgsNode(), new NodeType[] { NodeType.SYMBOLNODE });
				if(symArgs.isEmpty())
					continue;

				Provider provider = ForgeFactory.eINSTANCE.createProvider();
				provider.setName(((SymbolNode) symArgs.get(0)).getName());
				getProviders().add(provider);

				List<Node> calls = RubyParserUtils.findNodes(call.getIterNode(), new NodeType[] {
						NodeType.BLOCKNODE, NodeType.FCALLNODE });
				if(calls.isEmpty())
					calls = RubyParserUtils.findNodes(call.getIterNode(), new NodeType[] { NodeType.FCALLNODE });
				if(!calls.isEmpty()) {
					for(Node snode : calls) {
						FCallNode subCall = (FCallNode) snode;
						if("desc".equals(subCall.getName())) {
							List<Node> strArgs = RubyParserUtils.findNodes(
								subCall.getArgsNode(), new NodeType[] { NodeType.STRNODE });
							if(strArgs.size() >= 1)
								provider.setDoc(((StrNode) strArgs.get(0)).getValue());
							break;
						}
					}
				}
			}
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public void loadTypeFile(File typeFile) throws IOException {
		String typeFileStr = typeFile.getAbsolutePath();
		RootNode root = RubyParserUtils.parseFile(typeFile);
		List<Node> nodes = RubyParserUtils.findNodes(root.getBodyNode(), new NodeType[] { NodeType.MODULENODE });
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
			nodes = RubyParserUtils.findNodes(puppetModule.getBodyNode(), new NodeType[] { NodeType.CALLNODE });
			for(Node node : nodes) {
				CallNode call = (CallNode) node;
				if("newtype".equals(call.getName())) {
					Node receiver = call.getReceiverNode();
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
				nodes = RubyParserUtils.findNodes(puppetModule.getBodyNode(), new NodeType[] { NodeType.FCALLNODE });
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
			nodes = RubyParserUtils.findNodes((root).getBodyNode(), new NodeType[] { NodeType.CALLNODE });
			for(Node node : nodes) {
				CallNode call = (CallNode) node;
				if("newtype".equals(call.getName())) {
					Node receiver = call.getReceiverNode();
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
		Node argsNode = ((IArgumentNode) newtypeNode).getArgsNode();
		nodes = RubyParserUtils.findNodes(argsNode, new NodeType[] { NodeType.SYMBOLNODE });
		if(nodes.size() != 1)
			throw new IOException("The newtype call does not take exactly one symbol parameter in " + typeFileStr);

		SymbolNode typeName = (SymbolNode) nodes.get(0);
		setName(typeName.getName());

		// Find the assignment of the @doc instance variable
		Node iterNode = newtypeNode.getIterNode();
		nodes = RubyParserUtils.findNodes(iterNode, new NodeType[] { NodeType.BLOCKNODE, NodeType.INSTASGNNODE });
		if(nodes.isEmpty())
			// No block when there's just one assignment
			nodes = RubyParserUtils.findNodes(iterNode, new NodeType[] { NodeType.INSTASGNNODE });

		for(Node node : nodes) {
			InstAsgnNode asgnNode = (InstAsgnNode) node;
			if(!"@doc".equals(asgnNode.getName()))
				continue;

			Node valueNode = asgnNode.getValueNode();
			if(valueNode instanceof StrNode)
				setDoc(((StrNode) valueNode).getValue());
			break;
		}

		// Find the calls to newparam (receiver is the instance returned by
		// newtype)
		nodes = RubyParserUtils.findNodes(iterNode, new NodeType[] { NodeType.BLOCKNODE, NodeType.FCALLNODE });
		if(nodes.isEmpty())
			// No block when there's just one call
			nodes = RubyParserUtils.findNodes(iterNode, new NodeType[] { NodeType.FCALLNODE });

		for(Node node : nodes) {
			FCallNode callNode = (FCallNode) node;
			boolean isParam = "newparam".equals(callNode.getName());
			if(!isParam && !"newproperty".equals(callNode.getName()))
				continue;

			List<Node> pnodes = RubyParserUtils.findNodes(
				callNode.getArgsNode(), new NodeType[] { NodeType.SYMBOLNODE });
			if(pnodes.size() != 1)
				throw new IOException("A newparam or newproperty call does not take exactly one symbol parameter in " +
						typeFileStr);

			Documented elem;
			if(isParam) {
				Parameter parameter = ForgeFactory.eINSTANCE.createParameter();
				getParameters().add(parameter);
				elem = parameter;
			}
			else {
				Property property = ForgeFactory.eINSTANCE.createProperty();
				getProperties().add(property);
				elem = property;
			}
			elem.setName(((SymbolNode) pnodes.get(0)).getName());
			iterNode = callNode.getIterNode();
			pnodes = RubyParserUtils.findNodes(iterNode, new NodeType[] { NodeType.BLOCKNODE, NodeType.FCALLNODE });
			if(pnodes.isEmpty())
				// No block when there's just one call
				pnodes = RubyParserUtils.findNodes(iterNode, new NodeType[] { NodeType.FCALLNODE });

			for(Node pnode : pnodes) {
				FCallNode pcallNode = (FCallNode) pnode;
				if("desc".equals(pcallNode.getName())) {
					List<Node> dnodes = RubyParserUtils.findNodes(
						pcallNode.getArgsNode(), new NodeType[] { NodeType.STRNODE });
					if(dnodes.size() != 1)
						throw new IOException(
							"A newparam or newproperty desc call does not take exactly one string parameter in " +
									typeFileStr);
					elem.setDoc(((StrNode) dnodes.get(0)).getValue());
					break;
				}
			}
		}
	}

	/**
	 * <!-- begin-user-doc --> <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ForgePackage.Literals.TYPE;
	}

} // TypeImpl
