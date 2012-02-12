/**
 * Copyright (c) 2012 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   itemis AG http://www.itemis.eu - initial API and implementation.
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.pp.dsl.ui.editor.findrefs;

import org.apache.log4j.Logger;
import org.cloudsmith.geppetto.pp.dsl.ui.editor.findrefs.PPReferenceFinder.IPPQueryData;
import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.ExecutionEvent;
import org.eclipse.core.commands.ExecutionException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.text.ITextSelection;
import org.eclipse.search.ui.NewSearchUI;
import org.eclipse.xtext.resource.EObjectAtOffsetHelper;
import org.eclipse.xtext.resource.IGlobalServiceProvider;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.utils.EditorUtils;
import org.eclipse.xtext.util.concurrent.IUnitOfWork;

import com.google.inject.Inject;
import com.google.inject.Provider;

public class FindReferencesHandler extends AbstractHandler {

	public static class QueryExecutor {
		@Inject
		private Provider<PPReferenceQuery> queryProvider;

		public void execute(final IPPQueryData queryData) {
			if(!queryData.getTargetURIs().isEmpty()) {
				PPReferenceQuery referenceQuery = queryProvider.get();
				referenceQuery.init(queryData);
				NewSearchUI.activateSearchResultView();
				NewSearchUI.runQueryInBackground(referenceQuery);
			}
		}
	}

	@Inject
	protected EObjectAtOffsetHelper eObjectAtOffsetHelper;

	@Inject
	protected IGlobalServiceProvider globalServiceProvider;

	@Inject
	protected FindReferenceQueryDataFactory queryDataFactory;

	private static final Logger LOG = Logger.getLogger(FindReferencesHandler.class);

	protected IPPQueryData createQueryData(XtextResource localResource, ITextSelection selection) {
		EObject element = eObjectAtOffsetHelper.resolveElementAt(localResource, selection.getOffset());
		if(element != null) {
			URI localResourceURI = localResource.getURI();
			IPPQueryData queryData = queryDataFactory.createQueryData(element, localResourceURI);
			return queryData;
		}
		return null;
	}

	public Object execute(ExecutionEvent event) throws ExecutionException {
		try {
			XtextEditor editor = EditorUtils.getActiveXtextEditor(event);
			if(editor != null) {
				final ITextSelection selection = (ITextSelection) editor.getSelectionProvider().getSelection();
				IPPQueryData context = editor.getDocument().readOnly(new IUnitOfWork<IPPQueryData, XtextResource>() {
					public IPPQueryData exec(XtextResource localResource) throws Exception {
						return createQueryData(localResource, selection);
					}
				});
				if(context != null) {
					QueryExecutor queryExecutor = globalServiceProvider.findService(
						context.getLeadElementURI().trimFragment(), QueryExecutor.class);
					if(queryExecutor != null) {
						queryExecutor.execute(context);
					}
				}
			}
		}
		catch(Exception e) {
			LOG.error("Error finding references", e);
		}
		return null;
	}
}
