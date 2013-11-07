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
package com.puppetlabs.geppetto.puppetdb.ui.views;

import static com.puppetlabs.geppetto.puppetdb.ui.views.Util.getPuppetDBConnections;
import static org.eclipse.swt.SWT.LEFT;
import static org.eclipse.swt.SWT.RIGHT;

import java.util.concurrent.Callable;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.Path;
import org.eclipse.emf.edit.ui.provider.ExtendedImageRegistry;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.layout.TreeColumnLayout;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.util.OpenStrategy;
import org.eclipse.jface.viewers.*;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.jface.window.Window;
import org.eclipse.jface.wizard.WizardDialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Tree;
import org.eclipse.swt.widgets.TreeColumn;
import org.eclipse.ui.*;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.part.ViewPart;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.ITextEditor;

import com.google.inject.Inject;
import com.puppetlabs.geppetto.puppetdb.PuppetDBConnectionPreferences;
import com.puppetlabs.geppetto.puppetdb.PuppetDBManager;
import com.puppetlabs.geppetto.puppetdb.ui.UIPlugin;
import com.puppetlabs.geppetto.puppetdb.ui.treenode.TreeNodeContentProvider;
import com.puppetlabs.geppetto.puppetdb.ui.treenode.TreeNodeLabelProvider;
import com.puppetlabs.puppetdb.javaclient.model.Resource;

public class PuppetResourceEventsView extends ViewPart implements ISelectionChangedListener, IDoubleClickListener, KeyListener {
	@Inject
	private PuppetDBManager puppetDBManager;

	private TreeViewer viewer;

	private Action gotoAction;

	private Action removeAction;

	// @fmtOff
	public static RegexpSubstitution[] DEFAULT_WORKSPACE_MAPPINGS = new RegexpSubstitution[] {
		new RegexpSubstitution(".*/modules/(.+)$", "$1"),
		new RegexpSubstitution(".*/([^/]+/manifests/.+)$",  "$1"),
	};
	// @fmtOn

	/**
	 * Creates the actions for the viewsite action bars
	 */
	private void createActions(Tree tree) {
		IActionBars bars = getViewSite().getActionBars();
		IToolBarManager toolbarManager = bars.getToolBarManager();

		final Action newAction = createNewAction();
		toolbarManager.add(createNewAction());

		removeAction = createRemoveAction();
		toolbarManager.add(removeAction);

		final Action refreshAction = createRefreshAction();
		toolbarManager.add(refreshAction);

		gotoAction = createGoToAction();

		MenuManager popupMenuManager = new MenuManager("#PopupMenu"); //$NON-NLS-1$
		IMenuListener listener = new IMenuListener() {
			@Override
			public void menuAboutToShow(IMenuManager manager) {
				manager.add(gotoAction);
				manager.add(new Separator());
				manager.add(newAction);
				manager.add(removeAction);
				manager.add(new Separator());
				manager.add(refreshAction);
				manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
			}
		};
		popupMenuManager.addMenuListener(listener);
		popupMenuManager.setRemoveAllWhenShown(true);
		getSite().registerContextMenu(popupMenuManager, getSite().getSelectionProvider());
		Menu menu = popupMenuManager.createContextMenu(tree);
		tree.setMenu(menu);
	}

	private TreeViewerColumn createColumn(String key, TreeColumnLayout layout, int weight, CellLabelProvider provider, boolean resizable,
			int style) {
		TreeViewerColumn viewerCol = new TreeViewerColumn(viewer, style);
		viewerCol.setLabelProvider(provider);
		TreeColumn col = viewerCol.getColumn();
		col.setText(UIPlugin.getLocalString(key));
		col.setWidth(weight);
		col.setResizable(false); // Prevent user from resizing and moving as it doesn't play well with auto layout
		col.setMoveable(false);
		layout.setColumnData(col, new ColumnWeightData(weight, 50, resizable));
		return viewerCol;
	}

	private Action createGoToAction() {
		Action action = new Action(UIPlugin.getLocalString("_UI_Go_to_file")) {
			@Override
			public void run() {
				handleOpen(viewer.getSelection());
			}
		};
		action.setEnabled(false);
		return action;
	}

	private Action createNewAction() {
		Action action = new Action(UIPlugin.getLocalString("_UI_New_PuppetDB_Connection")) {
			@Override
			public void run() {

				Shell shell = getSite().getShell();
				NewPuppetDBClientWizard wizard = new NewPuppetDBClientWizard(puppetDBManager);
				wizard.init(getSite().getWorkbenchWindow().getWorkbench(), null);
				WizardDialog dialog = new WizardDialog(shell, wizard);
				dialog.create();
				if(dialog.open() == Window.OK)
					viewer.setInput(getPuppetDBConnections(viewer, puppetDBManager));
			}
		};
		action.setToolTipText(UIPlugin.getLocalString("_UI_New_PuppetDB_Connection_tooltip"));
		action.setImageDescriptor(ExtendedImageRegistry.INSTANCE.getImageDescriptor(UIPlugin.getInstance().getImage("database_add.png")));
		action.setEnabled(true);
		return action;
	}

	@Override
	public void createPartControl(Composite parent) {
		Composite treeComp = new Composite(parent, SWT.NONE);
		viewer = new TreeViewer(treeComp, SWT.VIRTUAL | SWT.V_SCROLL);
		viewer.setContentProvider(new TreeNodeContentProvider(viewer));
		viewer.addDoubleClickListener(this);
		viewer.getTree().addKeyListener(this);
		ColumnViewerToolTipSupport.enableFor(viewer, ToolTip.NO_RECREATE);

		new OpenAndLinkWithEditorHelper(viewer) {

			@Override
			protected void activate(ISelection selection) {
				final int currentMode = OpenStrategy.getOpenMethod();
				try {
					OpenStrategy.setOpenMethod(OpenStrategy.DOUBLE_CLICK);
					handleOpen(selection);
				}
				finally {
					OpenStrategy.setOpenMethod(currentMode);
				}
			}

			@Override
			protected void linkToEditor(ISelection selection) {
				// not supported by this part
			}

			@Override
			protected void open(ISelection selection, boolean activate) {
				handleOpen(selection);
			}

		};
		viewer.addSelectionChangedListener(this);

		Tree tree = viewer.getTree();

		TreeColumnLayout layout = new TreeColumnLayout();
		treeComp.setLayout(layout);

		tree.setLinesVisible(true);
		tree.setHeaderVisible(true);
		createColumn("_UI_Description_title", layout, 600, new TreeNodeLabelProvider(0), true, LEFT);
		createColumn("_UI_Type_title", layout, 100, new TreeNodeLabelProvider(1), false, LEFT);
		createColumn("_UI_Title_title", layout, 150, new TreeNodeLabelProvider(2), true, LEFT);
		createColumn("_UI_Path_title", layout, 300, new TreeNodeLabelProvider(3), true, LEFT);
		createColumn("_UI_Line_title", layout, 50, new TreeNodeLabelProvider(4), false, RIGHT);
		createColumn("_UI_Timestamp_title", layout, 130, new TreeNodeLabelProvider(5), false, LEFT);
		createActions(tree);
		viewer.setInput(getPuppetDBConnections(viewer, puppetDBManager));
	}

	private Action createRefreshAction() {
		Action action = new Action(UIPlugin.getLocalString("_UI_Refresh_Connections")) {
			@Override
			public void run() {
				viewer.setInput(getPuppetDBConnections(viewer, puppetDBManager));
			}
		};
		action.setToolTipText(UIPlugin.getLocalString("_UI_Refresh_Connections_tooltip"));
		action.setImageDescriptor(ExtendedImageRegistry.INSTANCE.getImageDescriptor(UIPlugin.getInstance().getImage("database_refresh.png")));
		return action;
	}

	private Action createRemoveAction() {
		Action action = new Action(UIPlugin.getLocalString("_UI_Remove_PuppetDB_Connection")) {
			@Override
			public void run() {
				Object first = ((IStructuredSelection) viewer.getSelection()).getFirstElement();
				if(first instanceof PuppetDBConnection) {
					removeClient((PuppetDBConnection) first);
				}
			}
		};
		action.setToolTipText(UIPlugin.getLocalString("_UI_Remove_PuppetDB_Connection_tooltip"));
		action.setImageDescriptor(ExtendedImageRegistry.INSTANCE.getImageDescriptor(UIPlugin.getInstance().getImage("database_delete.png")));
		action.setEnabled(false);
		return action;
	}

	@Override
	public void dispose() {
		viewer.removeDoubleClickListener(this);
		viewer.removeSelectionChangedListener(this);
		super.dispose();
	}

	@Override
	public void doubleClick(DoubleClickEvent event) {
		TreeViewer viewer = (TreeViewer) event.getViewer();
		Object selectedNode = ((IStructuredSelection) event.getSelection()).getFirstElement();
		viewer.setExpandedState(selectedNode, !viewer.getExpandedState(selectedNode));
	}

	private IFile getExistingWorkspaceFile(ISelection selection) {
		if(!(selection instanceof IStructuredSelection))
			return null;
		IStructuredSelection ss = (IStructuredSelection) selection;
		Object elem = ss.getFirstElement();
		if(!(elem instanceof ResourceEvent))
			return null;

		Resource resource = ((ResourceEvent) elem).getResource();
		if(resource == null)
			return null;

		return getExistingWorkspaceFile(resource.getFile());
	}

	private IFile getExistingWorkspaceFile(String pathStr) {
		if(pathStr == null)
			return null;

		// External path might be windows so convert backslash to forward slash
		if(pathStr.indexOf('\\') >= 0)
			pathStr = pathStr.replace('\\', '/');

		for(RegexpSubstitution rxSubst : DEFAULT_WORKSPACE_MAPPINGS) {
			String path = rxSubst.replaceOrNull(pathStr);
			if(path != null) {
				IFile file = ResourcesPlugin.getWorkspace().getRoot().getFile(Path.fromPortableString(path));
				if(file.isAccessible())
					return file;
			}
		}
		return null;
	}

	private void handleOpen(ISelection selection) {
		IFile file = getExistingWorkspaceFile(selection);
		if(file == null)
			return;

		IWorkbench wb = PlatformUI.getWorkbench();
		IWorkbenchPage page = wb.getActiveWorkbenchWindow().getActivePage();
		IEditorDescriptor desc = wb.getEditorRegistry().getDefaultEditor(file.getName());
		try {
			IEditorPart part = page.openEditor(new FileEditorInput(file), desc.getId());
			ITextEditor editor = (ITextEditor) part.getAdapter(ITextEditor.class);
			if(editor != null) {
				Resource resource = ((ResourceEvent) ((IStructuredSelection) selection).getFirstElement()).getResource();
				IDocumentProvider documentProvider = editor.getDocumentProvider();
				IDocument document = documentProvider.getDocument(editor.getEditorInput());
				int line = resource.getLine() - 1;
				if(line < 0)
					line = 0;
				editor.selectAndReveal(document.getLineOffset(line), document.getLineLength(line));
			}
		}
		catch(Exception e) {
			UIPlugin.logException("Unable to open editor", e);
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if(e.keyCode == SWT.DEL) {
			Object first = ((IStructuredSelection) viewer.getSelection()).getFirstElement();
			if(first instanceof PuppetDBConnection) {
				removeClient((PuppetDBConnection) first);
			}
		}
	}

	private void removeClient(PuppetDBConnection client) {
		final PuppetDBConnectionPreferences prefs = client.getPreferences();
		Shell shell = viewer.getControl().getShell();
		if(MessageDialog.openConfirm(
			shell, UIPlugin.getLocalString("_UI_Confirm_Removal"),
			UIPlugin.getLocalString("_UI_Is_remove_of_client_ok", prefs.getIdentifier()))) {
			Util.alterPreferences(shell, puppetDBManager, new Callable<Void>() {
				@Override
				public Void call() throws Exception {
					prefs.remove();
					return null;
				}
			});
			viewer.setInput(getPuppetDBConnections(viewer, puppetDBManager));
		}
	}

	@Override
	public void selectionChanged(SelectionChangedEvent event) {
		ISelection selection = event.getSelection();
		if(selection instanceof IStructuredSelection) {
			IStructuredSelection ss = (IStructuredSelection) selection;
			removeAction.setEnabled(ss.getFirstElement() instanceof PuppetDBConnection);
			gotoAction.setEnabled(getExistingWorkspaceFile(selection) != null);
		}
		else {
			removeAction.setEnabled(false);
			gotoAction.setEnabled(false);
		}
	}

	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}
