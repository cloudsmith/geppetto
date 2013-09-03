/**
 * Copyright (c) 2013 Puppet Labs, Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   itemis AG - intial API and implementation
 *   Puppet Labs
 * 
 */
package com.puppetlabs.geppetto.pp.dsl.ui.editor.findrefs;

import java.util.Iterator;
import java.util.List;

import com.puppetlabs.geppetto.pp.dsl.linking.PPResourceDescription;
import com.puppetlabs.geppetto.pp.dsl.ui.editor.findrefs.PPReferenceSearchResultEvents.Added;
import com.puppetlabs.geppetto.pp.dsl.ui.editor.findrefs.PPReferenceSearchResultEvents.Reset;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.SubMonitor;
import org.eclipse.emf.common.util.URI;
import org.eclipse.jface.viewers.ITreeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.search.ui.ISearchResultListener;
import org.eclipse.search.ui.SearchResultEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.progress.UIJob;
import org.eclipse.xtext.resource.IEObjectDescription;
import org.eclipse.xtext.resource.IReferenceDescription;
import org.eclipse.xtext.resource.IResourceDescription;
import org.eclipse.xtext.resource.IResourceDescription.Delta;
import org.eclipse.xtext.resource.IResourceDescription.Event;
import org.eclipse.xtext.resource.IResourceDescriptions;
import org.eclipse.xtext.ui.editor.StatefulResourceDescription;

import com.google.common.base.Predicate;
import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.google.inject.Inject;

/**
 * @author Jan Koehnlein - Initial contribution and API
 */
public class ReferenceSearchResultContentProvider implements ITreeContentProvider, ISearchResultListener,
		IResourceDescription.Event.Listener {

	private class UIUpdater extends UIJob {

		public UIUpdater() {
			super("PPReferenceSearchViewUpdater");
			setSystem(true);
		}

		@Override
		public IStatus runInUIThread(IProgressMonitor monitor) {
			isUIUpdateScheduled = false;
			List<SearchResultEvent> events;
			synchronized(batchedSearchResultEvents) {
				events = Lists.newArrayList(batchedSearchResultEvents);
				batchedSearchResultEvents.clear();
			}
			SubMonitor progress = SubMonitor.convert(monitor, events.size());
			for(SearchResultEvent event : events) {
				if(event instanceof Added) {
					addReference(((Added) event).getReferenceDescription(), true);
				}
				else if(event instanceof Reset) {
					if(rootNodes != null && !rootNodes.isEmpty()) {
						synchronized(viewer) {
							viewer.remove(
								viewer.getInput(), Iterables.toArray(rootNodes, ReferenceSearchViewTreeNode.class));
							rootNodes = null;
						}
					}
				}
				progress.worked(1);
			}
			viewer.refresh();
			viewer.expandToLevel(1);
			return Status.OK_STATUS;
		}
	}

	private IResourceDescriptions resourceDescriptions;

	private List<ReferenceSearchViewTreeNode> rootNodes;

	private TreeViewer viewer;

	private List<SearchResultEvent> batchedSearchResultEvents;

	private volatile boolean isUIUpdateScheduled;

	@Inject
	public ReferenceSearchResultContentProvider(IResourceDescriptions resourceDescriptions) {
		batchedSearchResultEvents = Lists.newArrayList();
		this.resourceDescriptions = resourceDescriptions;
		if(resourceDescriptions instanceof PPResourceDescription.Event.Source) {
			((PPResourceDescription.Event.Source) resourceDescriptions).addListener(this);
		}
	}

	private void addReference(IReferenceDescription referenceDescription, boolean isUpdateViewer) {
		URI containerEObjectURI = referenceDescription.getContainerEObjectURI();

		// IEObjectDescription sourceContainer = referenceDescription.getSourceContainer(); // getContainerEObjectURI();

		// URI containerEObjectURI = sourceContainer == null
		// ? null
		// : sourceContainer.getEObjectURI();

		final URI eObjectURI = (containerEObjectURI == null)
				? referenceDescription.getSourceEObjectUri()
				: containerEObjectURI;
		IResourceDescription resourceDescription = resourceDescriptions.getResourceDescription(eObjectURI.trimFragment());
		if(resourceDescription != null) {
			ReferenceSearchViewTreeNode resourceNode = resourceNode(resourceDescription, isUpdateViewer);
			ReferenceSearchViewTreeNode referenceNode = null;
			for(IEObjectDescription eObjectDescription : resourceDescription.getExportedObjects()) {
				if(eObjectDescription.getEObjectURI().equals(eObjectURI)) {
					referenceNode = new ReferenceSearchViewTreeNode(
						resourceNode, referenceDescription, eObjectDescription);
					break;
				}
			}
			if(referenceNode == null)
				referenceNode = new ReferenceSearchViewTreeNode(
					resourceNode, referenceDescription, referenceDescription);
		}
	}

	public void descriptionsChanged(final Event event) {
		Display.getDefault().asyncExec(new Runnable() {
			public void run() {
				if(rootNodes != null) {
					for(Delta delta : event.getDeltas()) {
						if(!(delta.getNew() instanceof StatefulResourceDescription)) {
							for(Iterator<ReferenceSearchViewTreeNode> i = rootNodes.iterator(); i.hasNext();) {
								ReferenceSearchViewTreeNode rootNode = i.next();
								if(((IResourceDescription) rootNode.getDescription()).getURI().equals(delta.getUri())) {
									if(delta.getNew() == null) {
										i.remove();
										viewer.remove(rootNode);
										break;
									}
									Iterable<IReferenceDescription> newReferenceDescriptions = delta.getNew().getReferenceDescriptions();
									List<ReferenceSearchViewTreeNode> removedReferenceNodes = Lists.newArrayList();
									for(ReferenceSearchViewTreeNode referenceNode : rootNode.getChildren()) {
										final IReferenceDescription refDesc = ((IReferenceDescription) referenceNode.getDescription());
										final URI referenceSourceURI = refDesc.getSourceEObjectUri();
										final URI referenceTargetURI = refDesc.getTargetEObjectUri();
										if(Iterables.isEmpty(Iterables.filter(
											newReferenceDescriptions, new Predicate<IReferenceDescription>() {
												public boolean apply(IReferenceDescription input) {
													return input.getSourceEObjectUri().equals(referenceSourceURI) &&
															input.getTargetEObjectUri().equals(referenceTargetURI);
												}
											}))) {
											removedReferenceNodes.add(referenceNode);
										}
									}
									for(ReferenceSearchViewTreeNode removedReferenceNode : removedReferenceNodes) {
										rootNode.removeChild(removedReferenceNode);
									}
									if(rootNode.getChildren().isEmpty()) {
										i.remove();
										viewer.remove(rootNode);
										break;
									}
									viewer.remove(
										rootNode,
										Iterables.toArray(removedReferenceNodes, ReferenceSearchViewTreeNode.class));
								}
							}
						}
					}
				}
			}
		});
	}

	public void dispose() {
		rootNodes = null;
	}

	public Object[] getChildren(Object parentElement) {
		if(parentElement instanceof ReferenceSearchViewTreeNode) {
			return Iterables.toArray(
				((ReferenceSearchViewTreeNode) parentElement).getChildren(), ReferenceSearchViewTreeNode.class);
		}
		return null;
	}

	public Object[] getElements(Object inputElement) {
		if(rootNodes == null || rootNodes.isEmpty()) {
			return new Object[0];
		}
		return Iterables.toArray(rootNodes, ReferenceSearchViewTreeNode.class);
	}

	public Object getParent(Object element) {
		if(element instanceof ReferenceSearchViewTreeNode) {
			return ((ReferenceSearchViewTreeNode) element).getParent();
		}
		return null;
	}

	public boolean hasChildren(Object element) {
		if(element instanceof ReferenceSearchViewTreeNode) {
			return !((ReferenceSearchViewTreeNode) element).getChildren().isEmpty();
		}
		return false;
	}

	public void inputChanged(final Viewer viewer, Object oldInput, Object newInput) {
		synchronized(viewer) {
			if(rootNodes != null) {
				rootNodes.clear();
			}
			if(oldInput instanceof PPReferenceSearchResult) {
				((PPReferenceSearchResult) oldInput).removeListener(this);
			}
			if(newInput instanceof PPReferenceSearchResult && viewer instanceof TreeViewer) {
				((PPReferenceSearchResult) newInput).addListener(this);
				this.viewer = (TreeViewer) viewer;
				for(IReferenceDescription referenceDescription : ((PPReferenceSearchResult) newInput).getMatchingReferences()) {
					addReference(referenceDescription, false);
				}
			}
		}
	}

	private ReferenceSearchViewTreeNode resourceNode(IResourceDescription resourceDescription, boolean isUpdateViewer) {
		if(rootNodes == null) {
			rootNodes = Lists.newArrayList();
		}
		for(ReferenceSearchViewTreeNode node : rootNodes) {
			Object nodeDescription = node.getDescription();
			if(nodeDescription instanceof IResourceDescription) {
				if(((IResourceDescription) nodeDescription).getURI().equals(resourceDescription.getURI())) {
					return node;
				}
			}
		}
		ReferenceSearchViewTreeNode node = new ReferenceSearchViewTreeNode(
			null, resourceDescription, resourceDescription);
		rootNodes.add(node);
		if(isUpdateViewer) {
			viewer.add(viewer.getInput(), node);
		}
		return node;
	}

	public void searchResultChanged(final SearchResultEvent e) {
		synchronized(batchedSearchResultEvents) {
			batchedSearchResultEvents.add(e);
		}
		if(!isUIUpdateScheduled) {
			isUIUpdateScheduled = true;
			new UIUpdater().schedule();
		}
	}
}
