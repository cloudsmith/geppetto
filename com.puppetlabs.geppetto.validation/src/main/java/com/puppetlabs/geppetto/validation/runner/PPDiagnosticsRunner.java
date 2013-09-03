/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Itemis AB (http://www.itemis.eu) - initial API and implementation
 *   Puppet Labs - adpated for validation
 * 
 */
package org.cloudsmith.geppetto.validation.runner;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.cloudsmith.geppetto.pp.dsl.PPDSLConstants;
import org.cloudsmith.geppetto.pp.dsl.adapters.PPImportedNamesAdapter;
import org.cloudsmith.geppetto.pp.dsl.adapters.PPImportedNamesAdapterFactory;
import org.cloudsmith.geppetto.pp.dsl.linking.DiagnosticConsumerBasedMessageAcceptor;
import org.cloudsmith.geppetto.pp.dsl.linking.IMessageAcceptor;
import org.cloudsmith.geppetto.pp.dsl.linking.PPResourceLinker;
import org.cloudsmith.geppetto.pp.dsl.linking.PPSearchPath;
import org.cloudsmith.geppetto.pp.dsl.linking.PPSearchPath.IConfigurableProvider;
import org.cloudsmith.geppetto.pp.dsl.linking.PPSearchPath.ISearchPathProvider;
import org.cloudsmith.geppetto.pp.dsl.parser.antlr.PPParser;
import org.cloudsmith.geppetto.pp.dsl.validation.IPotentialProblemsAdvisor;
import org.cloudsmith.geppetto.pp.dsl.validation.IValidationAdvisor;
import org.cloudsmith.geppetto.pp.dsl.validation.PPJavaValidator;
import org.cloudsmith.geppetto.ruby.resource.PptpRubyResourceFactory;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.Resource.Factory;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.xtext.ParserRule;
import org.eclipse.xtext.diagnostics.Severity;
import org.eclipse.xtext.linking.lazy.LazyLinkingResource;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;
import org.eclipse.xtext.nodemodel.ICompositeNode;
import org.eclipse.xtext.nodemodel.util.NodeModelUtils;
import org.eclipse.xtext.parser.IParseResult;
import org.eclipse.xtext.resource.IContainer;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.resource.IResourceServiceProvider;
import org.eclipse.xtext.resource.SaveOptions;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.resource.XtextResourceSet;
import org.eclipse.xtext.resource.containers.DelegatingIAllContainerAdapter;
import org.eclipse.xtext.resource.containers.IAllContainersState;
import org.eclipse.xtext.resource.impl.ListBasedDiagnosticConsumer;
import org.eclipse.xtext.serializer.ISerializer;
import org.eclipse.xtext.util.CancelIndicator;
import org.eclipse.xtext.util.StringInputStream;
import org.eclipse.xtext.validation.IResourceValidator;

import com.google.common.base.Function;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Provider;
import com.google.inject.TypeLiteral;

/**
 * Runner/Helper that can perform diagnostics/validation of PP files.
 * 
 */
public class PPDiagnosticsRunner {

	public static final class Keys {
		private static final TypeLiteral<Provider<XtextResourceSet>> resourceSetLiteral = new TypeLiteral<Provider<XtextResourceSet>>() {
		};

		public static final Key<Provider<XtextResourceSet>> RESOURCE_SET_KEY = Key.get(resourceSetLiteral);
	}

	private class ModuleExport implements AllModuleReferences.Export {

		private static final long serialVersionUID = 1L;

		final private File file;

		final private int startOffset;

		final private int length;

		final private int line;

		final private IEObjectDescription desc;

		public ModuleExport(File f, IEObjectDescription desc, int offset, int line, int length) {
			this.file = f;
			this.desc = desc;
			this.startOffset = offset;
			this.line = line;
			this.length = length;
		}

		@Override
		public String getDefaultValueText() {
			String text = desc.getUserData(PPDSLConstants.DEFAULT_EXPRESSION_DATA);
			if(text != null)
				return text.trim();
			return text;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.cloudsmith.geppetto.validation.runner.IExportsPerModule.Export #getEClass()
		 */
		@Override
		public EClass getEClass() {
			return desc.getEClass();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.cloudsmith.geppetto.validation.runner.ExportsPerModule.Export #getFile()
		 */
		@Override
		public File getFile() {
			return file;
		}

		@Override
		public String getLastNameSegment() {
			return desc.getName().getLastSegment();
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.cloudsmith.geppetto.validation.runner.ExportsPerModule.Export #getLength()
		 */
		@Override
		public int getLength() {
			return length;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.cloudsmith.geppetto.validation.runner.ExportsPerModule.Export #getLine()
		 */
		@Override
		public int getLine() {
			return line;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.cloudsmith.geppetto.validation.runner.IExportsPerModule.Export #getName()
		 */
		@Override
		public String getName() {
			return PPDiagnosticsRunner.this.converter.toString(desc.getName());
		}

		@Override
		public String getNameWithoutLastSegment() {
			return PPDiagnosticsRunner.this.converter.toString(desc.getName().skipLast(1));
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.cloudsmith.geppetto.validation.runner.IExportsPerModule.Export #getParentName()
		 */
		@Override
		public String getParentName() {
			return desc.getUserData(PPDSLConstants.PARENT_NAME_DATA);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see org.cloudsmith.geppetto.validation.runner.ExportsPerModule.Export #getStart()
		 */
		@Override
		public int getStart() {
			return startOffset;
		}

	}

	private Injector injector;

	private XtextResourceSet resourceSet;

	/**
	 * The linker that performs the "special" PP linking. Normally used/called from the PPLinker.
	 */
	private PPResourceLinker resourceLinker;

	private PPDiagnosticsSetup instance;

	private IResourceServiceProvider pptpRubyResourceServiceProvider;

	private IResourceServiceProvider ppResourceServiceProvider;

	private IResourceServiceProvider resourceServiceProvider;

	private org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider indexProvider;

	private IQualifiedNameConverter converter;

	Map<String, File> pathToFileMap;

	public static final String PPTPCONTAINER = "_pptp";

	private String ROOTCONTAINER = null;

	private Multimap<String, String> restricted = null;

	private IEncodingProvider encodingProvider;

	private ISearchPathProvider searchPathProvider;

	private final Function<QualifiedName, String> fQualifiedToString = new Function<QualifiedName, String>() {

		@Override
		public String apply(QualifiedName from) {
			return converter.toString(from);
		}
	};

	public PPDiagnosticsRunner() {
		pathToFileMap = Maps.newHashMap();
	}

	private void _configureTransitiveClosure(Set<MetadataInfo> processed, final IPath rootModule,
			final Multimap<String, String> restricted, final MetadataInfo mi) {
		if(processed.contains(mi))
			return; // circular !
		for(MetadataInfo.Resolution r : mi.getResolvedDependencies()) {
			MetadataInfo dep = r.metadata;
			IPath dp = new Path(dep.getFile().getAbsolutePath()).removeLastSegments(1);
			restricted.put(rootModule.toString(), dp.toString());
			processed.add(mi);
			_configureTransitiveClosure(processed, rootModule, restricted, dep);
		}
	}

	/**
	 * Configure containers if something else than "everything is visible to everything" is wanted. This method must be
	 * called before resources are loaded.
	 * 
	 * @param root
	 *            - where non modular content is contained
	 * @param moduleInfo
	 *            - information about modules and their dependencies
	 * @param resources
	 *            - URI's to all resources to be loaded
	 */
	public void configureContainers(File root, Collection<MetadataInfo> moduleInfo, Iterable<URI> resources) {
		List<String> allContainers = Lists.newArrayList();
		Multimap<String, URI> containedResources = HashMultimap.create();
		restricted = HashMultimap.create();

		ROOTCONTAINER = root.getAbsolutePath();
		allContainers.add(ROOTCONTAINER);
		allContainers.add(PPTPCONTAINER);

		final List<IPath> modulePaths = Lists.newArrayList();

		for(MetadataInfo mi : moduleInfo) {
			File f = mi.getFile();
			// get path to directory (the moduleinfo file is for the metadata
			// file itself
			IPath p = new Path(f.getAbsolutePath()).removeLastSegments(1);
			modulePaths.add(p);
			allContainers.add(p.toString());
			if(mi.isRole()) {
				// This means the dependencies are restricted to the transitive
				// closure of the module's'
				// dependencies + the pptp
				configureTransitiveClosure(p, restricted, mi);
				restricted.put(p.toString(), PPTPCONTAINER);
			}
		}

		// sort uri's into respective container
		for(URI uri : resources) {

			// pptp is special
			if("pptp".equals(uri.fileExtension())) {
				containedResources.put(PPTPCONTAINER, uri);
				continue;
			}

			// if path starts with a module's prefix it is in that container
			Path p = new Path(uri.toFileString());
			IPath modulePath = null;
			for(IPath prefix : modulePaths)
				if(prefix.isPrefixOf(p))
					modulePath = prefix;

			// if not in any module, it is in the root container
			if(modulePath == null)
				containedResources.put(ROOTCONTAINER, uri);
			else
				containedResources.put(modulePath.toString(), uri);
		}

		// Create the "all state" and set it in as resourceset adapter
		// (This is where Xtext will find it later)
		ValidationContainersStateFactory factory = new ValidationContainersStateFactory();
		IAllContainersState allState = factory.getContainersState(allContainers, containedResources, restricted);
		resourceSet.eAdapters().add(new DelegatingIAllContainerAdapter(allState));
	}

	public void configureEncoding(IEncodingProvider provider) {
		if(provider == null)
			provider = new DefaultEncodingProvider();
		this.encodingProvider = provider;
	}

	public void configureSearchPath(File root, String searchPath, String environment) {
		if(searchPathProvider instanceof IConfigurableProvider)
			((IConfigurableProvider) searchPathProvider).configure(
				URI.createFileURI(root.getAbsolutePath()), searchPath, environment);
	}

	private void configureTransitiveClosure(final IPath rootModule, final Multimap<String, String> restricted,
			final MetadataInfo mi) {
		Set<MetadataInfo> processed = Sets.newHashSet();
		restricted.put(rootModule.toString(), rootModule.toString()); // it can
																		// see
																		// itself
		_configureTransitiveClosure(processed, rootModule, restricted, mi);
	}

	private ModuleExport createExport(IEObjectDescription desc) {
		// String name = converter.toString(desc.getName());
		File f = uri2File(desc.getEObjectURI());
		ICompositeNode node = NodeModelUtils.getNode(desc.getEObjectOrProxy());
		int line = -1;
		int offset = -1;
		int length = 0;
		if(node != null) {
			line = node.getStartLine();
			offset = node.getOffset();
			length = node.getLength();
		}
		ModuleExport me = new ModuleExport(f, desc, offset, line, length);
		return me;
	}

	/**
	 * Get instance of class via the PP RT injector.
	 * 
	 * @param <T>
	 * @param clazz
	 * @return
	 */
	public <T> T get(Class<T> clazz) {
		if(injector == null)
			injector = Guice.createInjector();
		return injector.getInstance(clazz);
	}

	/**
	 * Translates all Exports and Imports and stores this in an ExportsPerModule.
	 * 
	 * @return
	 */
	public AllModuleReferences getAllModulesState() {
		final AllModuleReferences result = new AllModuleReferences();

		if(resourceSet == null || resourceSet.getResources().size() < 1)
			return result;

		// The container manager knows about resource <-> container mapping
		final ValidationStateBasedContainerManager validationContainerManager = getContainerManager();

		// ResourceDescriptions is an index of all exports
		IResourceDescriptions descriptionIndex = getResourceDescriptions();

		// translate all exports and create a map from IEObjectDescription to
		// Export
		Map<IEObjectDescription, ModuleExport> exports = Maps.newHashMap();
		for(IResourceDescription rdesc : descriptionIndex.getAllResourceDescriptions()) {
			String handle = validationContainerManager.getContainerHandle(rdesc, descriptionIndex);

			if(handle == null)
				continue;
			// TODO: This is a leap of faith, handles are all paths, except the
			// special "_pptp" path
			File moduleDir = new File(handle);

			for(IEObjectDescription desc : rdesc.getExportedObjects()) {
				ModuleExport me = createExport(desc);
				exports.put(desc, me);
				result.addExport(moduleDir, me);
			}
		}
		for(Resource r : resourceSet.getResources()) {
			// get module (i.e. container handle) of importing container
			File importingModuleDir = getContainerHandle(r.getURI(), descriptionIndex, validationContainerManager);
			if(importingModuleDir == null)
				continue;

			// get the imports recorded during linking
			PPImportedNamesAdapter importedAdapter = PPImportedNamesAdapterFactory.eINSTANCE.adapt(r);
			for(IEObjectDescription desc : importedAdapter.getResolvedDescriptions()) {
				// get the container (e.g. a module) of the the desc
				File moduleDir = getContainerHandle(desc.getEObjectURI(), descriptionIndex, validationContainerManager);
				if(moduleDir == null)
					continue;
				ModuleExport me = exports.get(desc);
				if(me == null)
					me = searchMissing(importingModuleDir, moduleDir, exports, desc);
				result.addImport(importingModuleDir, moduleDir, me);
			}
			// get the ambiguities recording during linking
			for(IEObjectDescription desc : importedAdapter.getAmbiguousDescriptions()) {
				// get the container (e.g. a module) of the the desc
				File moduleDir = getContainerHandle(desc.getEObjectURI(), descriptionIndex, validationContainerManager);
				if(moduleDir == null)
					continue;
				ModuleExport me = exports.get(desc);
				if(me == null)
					me = searchMissing(importingModuleDir, moduleDir, exports, desc);
				result.addAmbiguity(importingModuleDir, moduleDir, me);
			}
			// TODO: RECORD BOTH NAME FILE, AND LOCATIONS FOR THAT NAME
			result.addUnresolved(importingModuleDir, r.getURI(), importedAdapter.getUnresolved(), fQualifiedToString);
			// result.addAllUnresolvedNames(
			// importingModuleDir,
			// Iterables.transform(importedAdapter.getUnresolvedNames(), new
			// Function<QualifiedName, String>() {
			//
			// @Override
			// public String apply(QualifiedName from) {
			// return converter.toString(from);
			// }
			// }));

		}

		result.setRestricted(restricted);
		return result;

	}

	private File getContainerHandle(URI uri, IResourceDescriptions index, ValidationStateBasedContainerManager manager) {
		IResourceDescription resourceDescription = index.getResourceDescription(uri.trimFragment());
		String containerHandle = manager.getContainerHandle(resourceDescription, index);
		if(containerHandle == null)
			return null;
		File moduleDir = new File(containerHandle);
		return moduleDir;

	}

	private ValidationStateBasedContainerManager getContainerManager() {
		// The container manager knows about resource <-> container mapping
		// An overridden manager is expected with API extensions
		IContainer.Manager manager = resourceServiceProvider.getContainerManager();
		if(!(manager instanceof ValidationStateBasedContainerManager))
			throw new IllegalStateException(
				"Guice Configuration Error: container manager must be a ValidationStateBasedContainerManager");

		return (ValidationStateBasedContainerManager) manager;
	}

	public PPSearchPath getDefaultSearchPath() {
		// NOTE: This is a bit ugly, but it is known to return the default
		// search path since the
		// configuration has a search path provider that only returns the
		// default, and the parameter
		// EMF Resource is not used.
		return searchPathProvider.get(null);
	}

	// private PPGrammarAccess getGrammarAccess() {
	// return get(PPGrammarAccess.class);
	// }

	private IEncodingProvider getEncodingProvider() {
		if(encodingProvider == null)
			encodingProvider = new DefaultEncodingProvider();
		return encodingProvider;
	}

	IQualifiedNameConverter getIQualifiedNameConverter() {
		return get(IQualifiedNameConverter.class);
	}

	/**
	 * Access to the 'pp' services (container management and more).
	 */
	private IResourceServiceProvider getIResourceServiceProvider() {
		return get(IResourceServiceProvider.class);
	}

	private PPParser getParser() {
		return get(PPParser.class);
	}

	public IResourceValidator getPPResourceValidator() {
		return get(IResourceValidator.class);
	}

	/**
	 * Get the (total) description index The Xtext API requires a resource, but since everything is in the same resource
	 * set, it does not matter which resource that is picked (pick first). Note - caller must check if the resource set
	 * is empty.
	 */
	private IResourceDescriptions getResourceDescriptions() {
		return indexProvider.getResourceDescriptions(resourceSet.getResources().get(0));
	}

	/**
	 * Access to the global index maintained by Xtext, is made via a special (non guice) provider that is aware of the
	 * context (builder, dirty editors, etc.). It is used to obtain the index for a particular resource. This special
	 * provider is obtained here.
	 */
	private org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider getResourceDescriptionsProvider() {
		return get(org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider.class);
	}

	public EList<Resource> getResources() {
		return resourceSet.getResources();
	}

	/**
	 * 
	 */
	private ISearchPathProvider getSearchPathProvider() {
		return get(ISearchPathProvider.class);
	}

	private ISerializer getSerializer() {
		return get(ISerializer.class);
	}

	/**
	 * Loads a .pp, .pptp or .rb resource using the resource factory configured for the extension. Returns null for a
	 * .rb resource that is not expected to contribute anything to the pptp. All non null resources are added to the
	 * resource set.
	 */
	public Resource loadResource(InputStream in, URI uri) throws Exception {
		// Lookup the factory to use for the resource
		Factory factory = Resource.Factory.Registry.INSTANCE.getFactory(uri);
		// // UGLY AS HELL HACK
		// if(factory instanceof XtextResourceFactory)
		// factory = injector.getInstance(XtextResourceFactory.class);

		// Avoid loading lots of empty resources for rb files that do not
		// contribute
		if(factory instanceof PptpRubyResourceFactory && !pptpRubyResourceServiceProvider.canHandle(uri))
			return null;

		Map<String, String> options = Maps.newHashMap();
		options.put(XtextResource.OPTION_ENCODING, getEncodingProvider().getEncoding(uri));

		Resource r = factory.createResource(uri);
		resourceSet.getResources().add(r);
		r.load(in, options);

		return r;
	}

	/**
	 * Load a resource from a String. The URI must be well formed for the language being the content of the given
	 * sourceString (the uri determined the factory to use and the encoding via an IEncodingProvider).
	 * 
	 * @param sourceString
	 * @param uri
	 * @return
	 * @throws Exception
	 */
	public final Resource loadResource(String sourceString, URI uri) throws Exception {
		return loadResource(new StringInputStream(sourceString, getEncodingProvider().getEncoding(uri)), uri);
	}

	public Resource loadResource(URI uri) throws IOException {
		Resource resource = resourceSet.createResource(uri);
		Map<String, String> options = Maps.newHashMap();
		options.put(XtextResource.OPTION_ENCODING, getEncodingProvider().getEncoding(uri));

		resource.load(options);
		resourceSet.getResources().add(resource);
		return resource;

	}

	/**
	 * Loads a resource to the resource set. The intended use is to load a .pptp resource.
	 * 
	 * @param uri
	 *            uri to a .pptp resource (typically).
	 * 
	 * @return the resource
	 */
	public Resource loadResource1(URI uri) {
		Resource resource = resourceSet.getResource(uri, true);
		resourceSet.getResources().add(resource);
		return resource;

	}

	public IParseResult parseString(ParserRule rule, String s) throws PPSyntaxErrorException {
		PPParser parser = getParser();
		IParseResult result = parser.parse(rule, new StringReader(s));
		if(result.hasSyntaxErrors())
			throw new PPSyntaxErrorException(result);
		return result;
	}

	public void resolveCrossReferences(Resource resource, boolean profileThis, final IProgressMonitor monitor) {
		if(resource instanceof LazyLinkingResource) {
			// The default linking
			long before = System.currentTimeMillis();
			final CancelIndicator cancelMonitor = new CancelIndicator() {
				public boolean isCanceled() {
					return monitor.isCanceled();
				}
			};

			((LazyLinkingResource) resource).resolveLazyCrossReferences(cancelMonitor);
			long afterLazy = System.currentTimeMillis();
			if(profileThis)
				System.err.printf("LazyLinkingResource.resolveLazyCrossReferences: (%s)\n", afterLazy - before);

			// just in case some other crazy thing is sent here check that it is
			// a pp resource
			// (pp resource linking is not relevant on anything but .pp
			// resources).
			if(ppResourceServiceProvider.canHandle(resource.getURI())) {
				// The PP resource linking (normally done by PP Linker (but
				// without documentation association)
				//
				final ListBasedDiagnosticConsumer consumer = new ListBasedDiagnosticConsumer();
				IMessageAcceptor acceptor = new DiagnosticConsumerBasedMessageAcceptor(consumer);
				resourceLinker.link(
					((LazyLinkingResource) resource).getParseResult().getRootASTElement(), acceptor, profileThis);
				resource.getErrors().addAll(consumer.getResult(Severity.ERROR));
				resource.getWarnings().addAll(consumer.getResult(Severity.WARNING));
			}
			if(profileThis) {
				long afterPP = System.currentTimeMillis();
				System.err.printf("PP linker: (%s)\n", afterPP - afterLazy);
			}
		}
		else {
			long before = System.currentTimeMillis();
			EcoreUtil.resolveAll(resource);
			long after = System.currentTimeMillis();
			if(profileThis)
				System.err.printf("EcoreUtil.resolveAll: (%s)\n", after - before);
		}

	}

	/**
	 * Perform an equals scan of exports (instead of using identity). (Pity that IEObjectDescription does not have an
	 * equals method). For some reason some descriptions change identify (but are otherwise compatible) - don't know
	 * why. TODO: Figure out what is going on.
	 * 
	 * @param importingModuleDir
	 * @param moduleDir
	 * @param exports
	 * @param desc
	 */
	private ModuleExport searchMissing(File importingModuleDir, File moduleDir,
			Map<IEObjectDescription, ModuleExport> exports, IEObjectDescription desc) {

		List<IEObjectDescription> matching = Lists.newArrayList();
		for(IEObjectDescription d2 : exports.keySet()) {
			if(!d2.getName().equals(desc.getName()))
				continue;
			if(!d2.getEObjectURI().equals(desc.getEObjectURI()))
				continue;
			if(!d2.getEClass().equals(desc.getEClass()))
				continue;
			matching.add(d2);
		}
		if(matching.size() == 1)
			return exports.get(matching.get(0));
		return null;
	}

	public String serialize(EObject obj) {
		SaveOptions options = SaveOptions.newBuilder().getOptions();
		return getSerializer().serialize(obj, options);
	}

	public String serializeFormatted(EObject obj) {
		return getSerializer().serialize(obj, SaveOptions.newBuilder().format().getOptions());
	}

	/**
	 * Must be called prior to calling any other methods. Creates an injector and sets things up.
	 * 
	 * @throws Exception
	 */
	public void setUp(IValidationAdvisor.ComplianceLevel complianceLevel, IPotentialProblemsAdvisor problemsAdvisor)
			throws Exception {
		// Setup with overrides
		instance = new PPDiagnosticsSetup(complianceLevel, problemsAdvisor);
		injector = instance.createInjectorAndDoEMFRegistration();
		resourceSet = get(XtextResourceSet.class);
		resourceSet.setClasspathURIContext(getClass());

		ppResourceServiceProvider = injector.getInstance(IResourceServiceProvider.class);
		pptpRubyResourceServiceProvider = instance.getPptpRubyInjector().getInstance(IResourceServiceProvider.class);

		resourceLinker = injector.getInstance(PPResourceLinker.class);

		resourceServiceProvider = getIResourceServiceProvider();

		indexProvider = getResourceDescriptionsProvider();
		converter = getIQualifiedNameConverter();
		searchPathProvider = getSearchPathProvider();

		// Force the PPJavaValidator instance to be created early (in case it
		// was not registered with eager creation.
		injector.getInstance(PPJavaValidator.class);
	}

	public void tearDown() {
		injector = null;
		instance = null;
	}

	private File uri2File(URI uri) {

		String path = uri.isFile()
				? uri.toFileString()
				: uri.path();
		File f = pathToFileMap.get(path);
		if(f != null)
			return f;
		f = new File(path);
		pathToFileMap.put(path, f);
		return f;
	}

}
