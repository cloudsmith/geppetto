/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Itemis AB - (http://www.itemis.eu)
 *   Puppet Labs
 * 
 */
package com.puppetlabs.geppetto.pp.dsl.ui.editor.findrefs;

import static com.google.common.collect.Iterables.contains;
import static com.google.common.collect.Iterables.isEmpty;

import javax.annotation.Nullable;

import org.apache.log4j.Logger;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IStorage;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IStorageEditorInput;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.findrefs.LoadingResourceAccess;
import org.eclipse.xtext.ui.editor.model.IXtextDocument;
import org.eclipse.xtext.ui.resource.IStorage2UriMapper;
import org.eclipse.xtext.ui.util.DisplayRunnableWithResult;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import com.google.inject.Inject;
import com.puppetlabs.geppetto.pp.dsl.ui.editor.findrefs.PPReferenceFinder.ILocalResourceAccess;

/**
 * This is a repackaing of the class from the xtext findrefs package - it implements a package private interface in a
 * class
 * that needed a different implementation.
 * 
 * TODO: Contribute change to Xtext, make the ILocalResourceAccess a public interface. (The implementation is public).
 * 
 * @author Jan Koehnlein - Initial contribution and API
 * 
 * 
 */
public class EditorResourceAccess implements ILocalResourceAccess {

	private static final Logger LOG = Logger.getLogger(EditorResourceAccess.class);

	@Inject(optional = true)
	@Nullable
	private IWorkbench workbench;

	@Inject
	private IStorage2UriMapper storage2UriMapper;

	@Inject
	private LoadingResourceAccess delegate;

	protected IXtextDocument getOpenDocument(final URI targetURI) {
		if(workbench == null)
			return null;
		Iterable<Pair<IStorage, IProject>> storagesToProject = storage2UriMapper.getStorages(targetURI.trimFragment());
		final Iterable<IStorage> storages = Iterables.transform(
			storagesToProject, new Function<Pair<IStorage, IProject>, IStorage>() {
				public IStorage apply(Pair<IStorage, IProject> from) {
					return from.getFirst();
				}
			});
		if(!isEmpty(storages)) {
			return new DisplayRunnableWithResult<IXtextDocument>() {
				@Override
				protected IXtextDocument run() throws Exception {
					IWorkbenchPage activePage = workbench.getActiveWorkbenchWindow().getActivePage();
					if(activePage == null)
						return null;
					for(IEditorReference editorReference : activePage.getEditorReferences()) {
						try {
							IEditorInput editorInput = editorReference.getEditorInput();
							if(editorInput instanceof IStorageEditorInput &&
									contains(storages, ((IStorageEditorInput) editorInput).getStorage())) {
								IEditorPart editor = editorReference.getEditor(true);
								if(editor instanceof XtextEditor) {
									XtextEditor xtextEditor = (XtextEditor) editor;
									return xtextEditor.getDocument();
								}
							}
						}
						catch(Exception e) {
							LOG.error("Error accessing document", e);
						}
					}
					return null;
				}
			}.syncExec();
		}
		return null;
	}

	public <R> R readOnly(final URI targetURI, final IUnitOfWork<R, ResourceSet> work) {
		R result = null;
		IXtextDocument document = getOpenDocument(targetURI.trimFragment());
		if(document != null) {
			result = document.readOnly(new IUnitOfWork<R, XtextResource>() {
				public R exec(XtextResource state) throws Exception {
					ResourceSet localContext = state.getResourceSet();
					if(localContext != null)
						return work.exec(localContext);
					return null;
				}
			});
		}
		return result != null
				? result
				: delegate.readOnly(targetURI, work);
	}
}
