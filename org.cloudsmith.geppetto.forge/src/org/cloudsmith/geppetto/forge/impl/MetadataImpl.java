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

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.Writer;
import java.net.URI;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

import org.cloudsmith.geppetto.common.os.StreamUtil;
import org.cloudsmith.geppetto.forge.Dependency;
import org.cloudsmith.geppetto.forge.ForgeFactory;
import org.cloudsmith.geppetto.forge.ForgePackage;
import org.cloudsmith.geppetto.forge.Metadata;
import org.cloudsmith.geppetto.forge.Type;
import org.cloudsmith.geppetto.forge.VersionRequirement;
import org.cloudsmith.geppetto.forge.util.JsonUtils;
import org.cloudsmith.geppetto.forge.util.RubyParserUtils;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.util.EObjectContainmentEList;
import org.eclipse.emf.ecore.util.InternalEList;
import org.jrubyparser.ast.FCallNode;
import org.jrubyparser.ast.IArgumentNode;
import org.jrubyparser.ast.ListNode;
import org.jrubyparser.ast.NilNode;
import org.jrubyparser.ast.Node;
import org.jrubyparser.ast.NodeType;
import org.jrubyparser.ast.RootNode;
import org.jrubyparser.ast.StrNode;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Metadata</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 * <li>{@link org.cloudsmith.geppetto.forge.impl.MetadataImpl#getName <em>Name</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.impl.MetadataImpl#getUser <em>User</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.impl.MetadataImpl#getFullName <em>Full Name</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.impl.MetadataImpl#getVersion <em>Version</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.impl.MetadataImpl#getLocation <em>Location</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.impl.MetadataImpl#getSource <em>Source</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.impl.MetadataImpl#getAuthor <em>Author</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.impl.MetadataImpl#getLicense <em>License</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.impl.MetadataImpl#getTypes <em>Types</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.impl.MetadataImpl#getSummary <em>Summary</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.impl.MetadataImpl#getDescription <em>Description</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.impl.MetadataImpl#getProjectPage <em>Project Page</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.impl.MetadataImpl#getChecksums <em>Checksums</em>}</li>
 * <li>{@link org.cloudsmith.geppetto.forge.impl.MetadataImpl#getDependencies <em>Dependencies</em>}</li>
 * </ul>
 * </p>
 * 
 * @generated
 */
public class MetadataImpl extends EObjectImpl implements Metadata {
	public static class JsonAdapter extends JsonUtils.ContainerDeserializer<Metadata> implements
			JsonSerializer<Metadata> {

		private static java.lang.reflect.Type listOfTypesType = new TypeToken<EList<Type>>() {
		}.getType();

		private static java.lang.reflect.Type listOfDependenciesType = new TypeToken<EList<Dependency>>() {
		}.getType();

		private static String getString(JsonObject jsonObj, String key) {
			JsonElement json = jsonObj.get(key);
			if(json == null)
				return null;
			String value = json.getAsString();

			// For some reason, the puppet-module tool generates unset values in
			// the metadata as the string "UNKNOWN". We don't want this string.
			return "UNKNOWN".equals(value) || value.length() == 0
					? null
					: value;
		}

		private static void putString(JsonObject jsonObj, String key, String value) {
			if(value == null)
				value = "";
			jsonObj.addProperty(key, value);
		}

		@Override
		public Metadata deserialize(JsonElement json, java.lang.reflect.Type typeOfT, JsonDeserializationContext context)
				throws JsonParseException {
			Metadata result = ForgeFactory.eINSTANCE.createMetadata();
			JsonObject jsonObj = json.getAsJsonObject();
			result.setFullName(getString(jsonObj, "name"));
			result.setAuthor(getString(jsonObj, "author"));
			result.setDescription(getString(jsonObj, "description"));
			result.setLicense(getString(jsonObj, "license"));
			String uriStr = getString(jsonObj, "project_page");
			if(uriStr != null)
				result.setProjectPage(URI.create(uriStr));
			result.setSource(getString(jsonObj, "source"));
			result.setSummary(getString(jsonObj, "summary"));
			result.setVersion(getString(jsonObj, "version"));

			json = jsonObj.get("checksums");
			if(json != null) {
				Map<String, byte[]> checksums = context.deserialize(json, JsonUtils.ChecksumMapAdapter.TYPE);
				result.getChecksums().putAll(checksums);
			}
			json = jsonObj.get("types");
			if(json != null)
				deserializeInto(json, result.getTypes(), TypeImpl.class, context);
			json = jsonObj.get("dependencies");
			if(json != null)
				deserializeInto(json, result.getDependencies(), DependencyImpl.class, context);
			return result;
		}

		@Override
		public JsonElement serialize(Metadata src, java.lang.reflect.Type typeOfSrc, JsonSerializationContext context) {
			JsonObject result = new JsonObject();
			MetadataImpl md = (MetadataImpl) src;
			putString(result, "name", md.getFullName());
			putString(result, "author", md.getAuthor());
			putString(result, "description", md.getDescription());
			putString(result, "license", md.getLicense());
			putString(result, "project_page", md.getProjectPage() == null
					? null
					: md.getProjectPage().toString());
			putString(result, "source", md.getSource());
			putString(result, "summary", md.getSummary());
			putString(result, "version", md.getVersion());
			if(md.checksums != null)
				result.add("checksums", context.serialize(md.checksums, JsonUtils.ChecksumMapAdapter.TYPE));
			if(md.types != null)
				result.add("types", context.serialize(md.types, listOfTypesType));
			if(md.dependencies != null)
				result.add("dependencies", context.serialize(md.dependencies, listOfDependenciesType));
			return result;
		}
	}

	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	// Directory names that should not be checksummed or copied.
	static final Pattern ARTIFACTS = Pattern.compile("^(?:[\\.~#].*|pkg|coverage)$");

	private static void addKeyValueNode(PrintWriter out, String key, String... strs) throws IOException {
		if(strs.length == 0)
			return;

		out.print(key);
		out.print(' ');
		switch(strs.length) {
			case 0:
				break;
			case 1:
				printRubyString(out, strs[0]);
				break;
			default:
				printRubyString(out, strs[0]);
				for(int idx = 1; idx < strs.length; ++idx) {
					out.append(", ");
					printRubyString(out, strs[idx]);
				}
		}
		out.println();
	}

	public static byte[] computeChecksum(File file, MessageDigest md) throws IOException {
		InputStream input = new FileInputStream(file);
		md.reset();
		try {
			byte[] buf = new byte[0x1000];
			int cnt;
			while((cnt = input.read(buf)) > 0)
				md.update(buf, 0, cnt);

		}
		finally {
			StreamUtil.close(input);
		}
		return md.digest();
	}

	private static MessageDigest getMessageDigest() {
		try {
			return MessageDigest.getInstance("MD5");
		}
		catch(NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	private static List<String> getStringArguments(IArgumentNode callNode) throws IllegalArgumentException {
		Node argsNode = callNode.getArgsNode();
		if(!(argsNode instanceof ListNode))
			throw new IllegalArgumentException("IArgumentNode expected");
		ListNode args = (ListNode) argsNode;
		int top = args.size();
		ArrayList<String> stringArgs = new ArrayList<String>(top);
		for(int idx = 0; idx < top; ++idx) {
			Node argNode = args.get(idx);
			if(argNode instanceof StrNode)
				stringArgs.add(((StrNode) argNode).getValue());
			else if(argNode instanceof NilNode)
				stringArgs.add(null);
			else
				throw new IllegalArgumentException("Can't make a string from a " + argNode.getNodeType());
		}
		return stringArgs;
	}

	private static boolean isChecksumCandidate(File file) {
		String filename = file.getName();
		return !("metadata.json".equals(filename) || "REVISION".equals(filename) || ARTIFACTS.matcher(filename).matches());
	}

	private static IllegalArgumentException noResponse(String key) {
		return new IllegalArgumentException("'" + key + "' is not a metadata value");
	}

	private static void printRubyString(Writer out, String str) throws IOException {
		if(str == null)
			return;

		out.append('\'');
		int top = str.length();
		for(int idx = 0; idx < top; ++idx) {
			char c = str.charAt(idx);
			switch(c) {
				case '\\':
				case '\'':
					out.append('\\');
					out.append(c);
					break;
				default:
					out.append(c);
			}
		}
		out.append('\'');
	}

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	@Expose
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getUser() <em>User</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getUser()
	 * @generated
	 * @ordered
	 */
	protected static final String USER_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getUser() <em>User</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getUser()
	 * @generated
	 * @ordered
	 */
	protected String user = USER_EDEFAULT;

	/**
	 * The default value of the '{@link #getFullName() <em>Full Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getFullName()
	 * @generated
	 * @ordered
	 */
	protected static final String FULL_NAME_EDEFAULT = null;

	/**
	 * The default value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	protected static final String VERSION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getVersion() <em>Version</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getVersion()
	 * @generated
	 * @ordered
	 */
	@Expose
	protected String version = VERSION_EDEFAULT;

	/**
	 * The default value of the '{@link #getLocation() <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
	protected static final File LOCATION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLocation() <em>Location</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getLocation()
	 * @generated
	 * @ordered
	 */
	protected File location = LOCATION_EDEFAULT;

	/**
	 * The default value of the '{@link #getSource() <em>Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	protected static final String SOURCE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSource() <em>Source</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getSource()
	 * @generated
	 * @ordered
	 */
	@Expose
	protected String source = SOURCE_EDEFAULT;

	/**
	 * The default value of the '{@link #getAuthor() <em>Author</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getAuthor()
	 * @generated
	 * @ordered
	 */
	protected static final String AUTHOR_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getAuthor() <em>Author</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getAuthor()
	 * @generated
	 * @ordered
	 */
	@Expose
	protected String author = AUTHOR_EDEFAULT;

	/**
	 * The default value of the '{@link #getLicense() <em>License</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getLicense()
	 * @generated
	 * @ordered
	 */
	protected static final String LICENSE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getLicense() <em>License</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getLicense()
	 * @generated
	 * @ordered
	 */
	@Expose
	protected String license = LICENSE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getTypes() <em>Types</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getTypes()
	 * @generated
	 * @ordered
	 */
	@Expose
	protected EList<Type> types;

	/**
	 * The default value of the '{@link #getSummary() <em>Summary</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getSummary()
	 * @generated
	 * @ordered
	 */
	protected static final String SUMMARY_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getSummary() <em>Summary</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getSummary()
	 * @generated
	 * @ordered
	 */
	@Expose
	protected String summary = SUMMARY_EDEFAULT;

	/**
	 * The default value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	protected static final String DESCRIPTION_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getDescription() <em>Description</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getDescription()
	 * @generated
	 * @ordered
	 */
	@Expose
	protected String description = DESCRIPTION_EDEFAULT;

	/**
	 * The default value of the '{@link #getProjectPage() <em>Project Page</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getProjectPage()
	 * @generated
	 * @ordered
	 */
	protected static final URI PROJECT_PAGE_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getProjectPage() <em>Project Page</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getProjectPage()
	 * @generated
	 * @ordered
	 */
	@Expose
	@SerializedName("project_page")
	protected URI projectPage = PROJECT_PAGE_EDEFAULT;

	/**
	 * The cached value of the '{@link #getChecksums() <em>Checksums</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getChecksums()
	 * @generated
	 * @ordered
	 */
	@Expose
	protected Map<String, byte[]> checksums;

	/**
	 * The cached value of the '{@link #getDependencies() <em>Dependencies</em>}' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @see #getDependencies()
	 * @generated
	 * @ordered
	 */
	@Expose
	protected EList<Dependency> dependencies;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	protected MetadataImpl() {
		super();
	}

	void appendChangedFiles(File file, List<File> result) throws IOException {
		appendChangedFiles(file, getMessageDigest(), file.getAbsolutePath().length() + 1, result);
	}

	private void appendChangedFiles(File file, MessageDigest md, int baseDirLen, List<File> result) throws IOException {
		if(!isChecksumCandidate(file))
			return;

		File[] children = file.listFiles();
		if(children != null) {
			for(File child : children)
				appendChangedFiles(child, md, baseDirLen, result);
			return;
		}

		byte[] oldChecksum = getChecksums().get(file.getAbsolutePath().substring(baseDirLen));
		if(oldChecksum == null)
			result.add(file);
		else {
			byte[] newChecksum = computeChecksum(file, md);
			if(!Arrays.equals(oldChecksum, newChecksum))
				result.add(file);
		}
	}

	private void call(String key, String value) {
		if("name".equals(key))
			setFullName(value);
		else if("author".equals(key))
			setAuthor(value);
		else if("description".equals(key))
			setDescription(value);
		else if("license".equals(key))
			setLicense(value);
		else if("project_page".equals(key))
			setProjectPage(URI.create(value));
		else if("source".equals(key))
			setSource(value);
		else if("summary".equals(key))
			setSummary(value);
		else if("version".equals(key))
			setVersion(value);
		else if("dependency".equals(key))
			call(key, value, null, null);
		else
			throw noResponse(key);
	}

	private void call(String key, String value1, String value2) {
		if("dependency".equals(key))
			call(key, value1, value2, null);
		else
			throw noResponse(key);
	}

	private void call(String key, String value1, String value2, String value3) {
		if("dependency".equals(key)) {
			Dependency dep = ForgeFactory.eINSTANCE.createDependency();
			dep.setName(value1);
			if(value2 != null)
				dep.setVersionRequirement(parseVersionRequirement(value2));
			if(value3 != null)
				dep.setRepository(URI.create(value3));
			getDependencies().add(dep);
		}
		else
			throw noResponse(key);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch(featureID) {
			case ForgePackage.METADATA__NAME:
				return getName();
			case ForgePackage.METADATA__USER:
				return getUser();
			case ForgePackage.METADATA__FULL_NAME:
				return getFullName();
			case ForgePackage.METADATA__VERSION:
				return getVersion();
			case ForgePackage.METADATA__LOCATION:
				return getLocation();
			case ForgePackage.METADATA__SOURCE:
				return getSource();
			case ForgePackage.METADATA__AUTHOR:
				return getAuthor();
			case ForgePackage.METADATA__LICENSE:
				return getLicense();
			case ForgePackage.METADATA__TYPES:
				return getTypes();
			case ForgePackage.METADATA__SUMMARY:
				return getSummary();
			case ForgePackage.METADATA__DESCRIPTION:
				return getDescription();
			case ForgePackage.METADATA__PROJECT_PAGE:
				return getProjectPage();
			case ForgePackage.METADATA__CHECKSUMS:
				return getChecksums();
			case ForgePackage.METADATA__DEPENDENCIES:
				return getDependencies();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, NotificationChain msgs) {
		switch(featureID) {
			case ForgePackage.METADATA__TYPES:
				return ((InternalEList<?>) getTypes()).basicRemove(otherEnd, msgs);
			case ForgePackage.METADATA__DEPENDENCIES:
				return ((InternalEList<?>) getDependencies()).basicRemove(otherEnd, msgs);
		}
		return super.eInverseRemove(otherEnd, featureID, msgs);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch(featureID) {
			case ForgePackage.METADATA__NAME:
				return NAME_EDEFAULT == null
						? name != null
						: !NAME_EDEFAULT.equals(name);
			case ForgePackage.METADATA__USER:
				return USER_EDEFAULT == null
						? user != null
						: !USER_EDEFAULT.equals(user);
			case ForgePackage.METADATA__FULL_NAME:
				return FULL_NAME_EDEFAULT == null
						? getFullName() != null
						: !FULL_NAME_EDEFAULT.equals(getFullName());
			case ForgePackage.METADATA__VERSION:
				return VERSION_EDEFAULT == null
						? version != null
						: !VERSION_EDEFAULT.equals(version);
			case ForgePackage.METADATA__LOCATION:
				return LOCATION_EDEFAULT == null
						? location != null
						: !LOCATION_EDEFAULT.equals(location);
			case ForgePackage.METADATA__SOURCE:
				return SOURCE_EDEFAULT == null
						? source != null
						: !SOURCE_EDEFAULT.equals(source);
			case ForgePackage.METADATA__AUTHOR:
				return AUTHOR_EDEFAULT == null
						? author != null
						: !AUTHOR_EDEFAULT.equals(author);
			case ForgePackage.METADATA__LICENSE:
				return LICENSE_EDEFAULT == null
						? license != null
						: !LICENSE_EDEFAULT.equals(license);
			case ForgePackage.METADATA__TYPES:
				return types != null && !types.isEmpty();
			case ForgePackage.METADATA__SUMMARY:
				return SUMMARY_EDEFAULT == null
						? summary != null
						: !SUMMARY_EDEFAULT.equals(summary);
			case ForgePackage.METADATA__DESCRIPTION:
				return DESCRIPTION_EDEFAULT == null
						? description != null
						: !DESCRIPTION_EDEFAULT.equals(description);
			case ForgePackage.METADATA__PROJECT_PAGE:
				return PROJECT_PAGE_EDEFAULT == null
						? projectPage != null
						: !PROJECT_PAGE_EDEFAULT.equals(projectPage);
			case ForgePackage.METADATA__CHECKSUMS:
				return checksums != null;
			case ForgePackage.METADATA__DEPENDENCIES:
				return dependencies != null && !dependencies.isEmpty();
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void eSet(int featureID, Object newValue) {
		switch(featureID) {
			case ForgePackage.METADATA__NAME:
				setName((String) newValue);
				return;
			case ForgePackage.METADATA__USER:
				setUser((String) newValue);
				return;
			case ForgePackage.METADATA__FULL_NAME:
				setFullName((String) newValue);
				return;
			case ForgePackage.METADATA__VERSION:
				setVersion((String) newValue);
				return;
			case ForgePackage.METADATA__SOURCE:
				setSource((String) newValue);
				return;
			case ForgePackage.METADATA__AUTHOR:
				setAuthor((String) newValue);
				return;
			case ForgePackage.METADATA__LICENSE:
				setLicense((String) newValue);
				return;
			case ForgePackage.METADATA__TYPES:
				getTypes().clear();
				getTypes().addAll((Collection<? extends Type>) newValue);
				return;
			case ForgePackage.METADATA__SUMMARY:
				setSummary((String) newValue);
				return;
			case ForgePackage.METADATA__DESCRIPTION:
				setDescription((String) newValue);
				return;
			case ForgePackage.METADATA__PROJECT_PAGE:
				setProjectPage((URI) newValue);
				return;
			case ForgePackage.METADATA__DEPENDENCIES:
				getDependencies().clear();
				getDependencies().addAll((Collection<? extends Dependency>) newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ForgePackage.Literals.METADATA;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch(featureID) {
			case ForgePackage.METADATA__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ForgePackage.METADATA__USER:
				setUser(USER_EDEFAULT);
				return;
			case ForgePackage.METADATA__FULL_NAME:
				setFullName(FULL_NAME_EDEFAULT);
				return;
			case ForgePackage.METADATA__VERSION:
				setVersion(VERSION_EDEFAULT);
				return;
			case ForgePackage.METADATA__SOURCE:
				setSource(SOURCE_EDEFAULT);
				return;
			case ForgePackage.METADATA__AUTHOR:
				setAuthor(AUTHOR_EDEFAULT);
				return;
			case ForgePackage.METADATA__LICENSE:
				setLicense(LICENSE_EDEFAULT);
				return;
			case ForgePackage.METADATA__TYPES:
				getTypes().clear();
				return;
			case ForgePackage.METADATA__SUMMARY:
				setSummary(SUMMARY_EDEFAULT);
				return;
			case ForgePackage.METADATA__DESCRIPTION:
				setDescription(DESCRIPTION_EDEFAULT);
				return;
			case ForgePackage.METADATA__PROJECT_PAGE:
				setProjectPage(PROJECT_PAGE_EDEFAULT);
				return;
			case ForgePackage.METADATA__DEPENDENCIES:
				getDependencies().clear();
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getAuthor() {
		return author;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public synchronized Map<String, byte[]> getChecksums() {
		if(checksums == null)
			checksums = new TreeMap<String, byte[]>();
		return checksums;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Dependency> getDependencies() {
		if(dependencies == null) {
			dependencies = new EObjectContainmentEList<Dependency>(
				Dependency.class, this, ForgePackage.METADATA__DEPENDENCIES);
		}
		return dependencies;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getDescription() {
		return description;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public String getFullName() {
		return getUser() + '-' + getName();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getLicense() {
		return license;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public File getLocation() {
		return location;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public URI getProjectPage() {
		return projectPage;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getSource() {
		return source;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getSummary() {
		return summary;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public EList<Type> getTypes() {
		if(types == null) {
			types = new EObjectContainmentEList<Type>(Type.class, this, ForgePackage.METADATA__TYPES);
		}
		return types;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getUser() {
		return user;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String getVersion() {
		return version;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public void loadChecksums(File moduleDir) throws IOException {
		loadChecksums(getMessageDigest(), moduleDir, moduleDir.getAbsolutePath().length() + 1);
	}

	private void loadChecksums(MessageDigest md, File file, int basedirLen) throws IOException {
		if(!isChecksumCandidate(file))
			return;

		File[] children = file.listFiles();
		if(children == null)
			getChecksums().put(file.getAbsolutePath().substring(basedirLen), computeChecksum(file, md));
		else {
			for(File child : children)
				loadChecksums(md, child, basedirLen);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public void loadModuleFile(File moduleFile) throws IOException {
		RootNode root = RubyParserUtils.parseFile(moduleFile);
		for(Node node : RubyParserUtils.findNodes(root.getBodyNode(), new NodeType[] { NodeType.FCALLNODE })) {
			FCallNode call = (FCallNode) node;
			String key = call.getName();
			List<String> args = getStringArguments(call);
			if(args.size() == 1)
				call(key, args.get(0));
			else if(args.size() == 2)
				call(key, args.get(0), args.get(1));
			else if(args.size() == 3)
				call(key, args.get(0), args.get(1), args.get(2));
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public void loadTypeFiles(File puppetDir) throws IOException {
		File[] typeFiles = new File(puppetDir, "type").listFiles();
		if(typeFiles == null || typeFiles.length == 0)
			return;

		List<Type> typeList = getTypes();
		typeList.clear();
		for(File typeFile : typeFiles) {
			if(!typeFile.getName().endsWith(".rb"))
				continue;
			Type type = ForgeFactory.eINSTANCE.createType();
			type.loadTypeFile(typeFile);
			typeList.add(type);
		}

		if(!typeList.isEmpty()) {
			File providerDir = new File(puppetDir, "provider");
			for(Type type : typeList)
				type.loadProvider(providerDir);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	public VersionRequirement parseVersionRequirement(String versionRequirement) {
		return DependencyImpl.parseVersionRequirement(versionRequirement);
	}

	public void populateFromModuleDir(File moduleDirectory) throws IOException {
		loadModuleFile(new File(moduleDirectory, "Modulefile"));
		loadTypeFiles(new File(moduleDirectory, "lib/puppet"));
		loadChecksums(moduleDirectory);
		location = moduleDirectory;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public void saveJSONMetadata(File jsonFile) throws IOException {
		Writer writer = new BufferedWriter(new FileWriter(jsonFile));
		try {
			Gson gson = JsonUtils.getGSon();
			gson.toJson(this, writer);
		}
		finally {
			StreamUtil.close(writer);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public void saveModulefile(File moduleFile) throws IOException {
		PrintWriter out = new PrintWriter(moduleFile);
		try {
			addKeyValueNode(out, "name", getFullName());
			if(getVersion() != null)
				addKeyValueNode(out, "version", getVersion());
			out.println();
			if(getAuthor() != null)
				addKeyValueNode(out, "author", getAuthor());
			if(getLicense() != null)
				addKeyValueNode(out, "license", getLicense());
			if(getProjectPage() != null)
				addKeyValueNode(out, "project_page", getProjectPage().toString());
			if(getSource() != null)
				addKeyValueNode(out, "source", getSource());
			if(getSummary() != null)
				addKeyValueNode(out, "summary", getSummary());
			if(getDescription() != null)
				addKeyValueNode(out, "description", getDescription());
			if(dependencies != null && dependencies.size() > 0) {
				for(Dependency dep : dependencies) {
					String depName = dep.getName();
					VersionRequirement ver = dep.getVersionRequirement();
					URI repo = dep.getRepository();
					if(ver != null) {
						if(repo != null)
							addKeyValueNode(out, "dependency", depName, ver.toString(), repo.toString());
						else
							addKeyValueNode(out, "dependency", depName, ver.toString());
					}
					else
						addKeyValueNode(out, "dependency", depName);
				}
			}
		}
		finally {
			StreamUtil.close(out);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setAuthor(String newAuthor) {
		String oldAuthor = author;
		author = newAuthor;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ForgePackage.METADATA__AUTHOR, oldAuthor, author));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setDescription(String newDescription) {
		String oldDescription = description;
		description = newDescription;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, ForgePackage.METADATA__DESCRIPTION, oldDescription, description));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated NOT
	 */
	@Override
	public void setFullName(String fullName) {
		if(fullName == null) {
			setUser(null);
			setName(null);
		}
		else {
			String[] sp = ForgeServiceImpl.userAndModuleFrom(fullName);
			setUser(sp[0]);
			setName(sp[1]);
		}
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setLicense(String newLicense) {
		String oldLicense = license;
		license = newLicense;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ForgePackage.METADATA__LICENSE, oldLicense, license));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ForgePackage.METADATA__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setProjectPage(URI newProjectPage) {
		URI oldProjectPage = projectPage;
		projectPage = newProjectPage;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(
				this, Notification.SET, ForgePackage.METADATA__PROJECT_PAGE, oldProjectPage, projectPage));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setSource(String newSource) {
		String oldSource = source;
		source = newSource;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ForgePackage.METADATA__SOURCE, oldSource, source));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setSummary(String newSummary) {
		String oldSummary = summary;
		summary = newSummary;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ForgePackage.METADATA__SUMMARY, oldSummary, summary));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setUser(String newUser) {
		String oldUser = user;
		user = newUser;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ForgePackage.METADATA__USER, oldUser, user));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public void setVersion(String newVersion) {
		String oldVersion = version;
		version = newVersion;
		if(eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ForgePackage.METADATA__VERSION, oldVersion, version));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * 
	 * @generated
	 */
	@Override
	public String toString() {
		if(eIsProxy())
			return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", user: ");
		result.append(user);
		result.append(", version: ");
		result.append(version);
		result.append(", location: ");
		result.append(location);
		result.append(", source: ");
		result.append(source);
		result.append(", author: ");
		result.append(author);
		result.append(", license: ");
		result.append(license);
		result.append(", summary: ");
		result.append(summary);
		result.append(", description: ");
		result.append(description);
		result.append(", projectPage: ");
		result.append(projectPage);
		result.append(", checksums: ");
		result.append(checksums);
		result.append(')');
		return result.toString();
	}

} // MetadataImpl
