/**
 * Copyright (c) 2013 Cloudsmith Inc. and other contributors, as listed below.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * Contributors:
 *   Cloudsmith
 * 
 */
package org.cloudsmith.geppetto.ui.editor;

import static org.cloudsmith.geppetto.common.Strings.trimToNull;
import static org.cloudsmith.geppetto.forge.v2.model.ModuleName.checkName;
import static org.cloudsmith.geppetto.forge.v2.model.ModuleName.splitName;
import static org.eclipse.xtext.util.Strings.emptyIfNull;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.cloudsmith.geppetto.diagnostic.Diagnostic;
import org.cloudsmith.geppetto.forge.Forge;
import org.cloudsmith.geppetto.forge.v2.model.Metadata;
import org.cloudsmith.geppetto.forge.v2.model.ModuleName;
import org.cloudsmith.geppetto.semver.Version;
import org.cloudsmith.geppetto.semver.VersionRange;
import org.cloudsmith.geppetto.ui.UIPlugin;
import org.cloudsmith.geppetto.ui.dialog.ModuleListSelectionDialog;
import org.cloudsmith.geppetto.ui.editor.MetadataModel.Dependency;
import org.cloudsmith.geppetto.ui.util.StringUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.TableColumnLayout;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ColumnViewerToolTipSupport;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.ToolTip;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.dialogs.FilteredList;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.IMessage;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.xtext.ui.XtextProjectHelper;

class ModuleDependenciesPage extends GuardedModulePage {
	protected class DependenciesSectionPart extends ModuleSectionPart {

		protected class EditDependencyDialog extends ModuleListSelectionDialog {

			private String initialVersionRequirement;

			private String initialName;

			private Text moduleNameText = null;

			private Text versionRequirementText = null;

			// We need those to keep the value when the widget is disposed
			// After the dialog returns, it's too late to get them from the
			// text widgets
			private String moduleName = null;

			private String versionRequirement = null;

			public EditDependencyDialog(Shell parent, MetadataModel.Dependency dependency) {
				super(parent);
				if(dependency != null) {
					initialName = dependency.getModuleName();
					initialVersionRequirement = dependency.getVersionRequirement();
				}
				setMultipleSelection(false);
				setTitle(UIPlugin.INSTANCE.getString("_UI_EditDependency_title")); //$NON-NLS-1$
				setStatusLineAboveButtons(true);
			}

			@Override
			protected FilteredList createFilteredList(Composite parent) {
				FilteredList filteredList = super.createFilteredList(parent);

				Composite composite = new Composite(parent, SWT.NONE);

				GridLayout gridLayout = new GridLayout(2, false);
				gridLayout.marginHeight = 0;
				gridLayout.marginWidth = 0;

				composite.setLayout(gridLayout);

				GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(composite);

				Label nameLabel = new Label(composite, SWT.NONE);
				nameLabel.setText(UIPlugin.INSTANCE.getString("_UI_ModuleName_label"));

				moduleNameText = new Text(composite, SWT.BORDER);
				colorOfText = moduleNameText.getForeground();
				moduleNameText.addModifyListener(new GuardedModifyListener() {
					@Override
					public void handleEvent(ModifyEvent ev) {
						try {
							new ModuleName(moduleNameText.getText());
							moduleNameText.setForeground(colorOfText);
							updateStatus(OK_WITHOUT_TEXT);
						}
						catch(IllegalArgumentException e) {
							moduleNameText.setForeground(colorOfBadText);
							updateStatus(new Status(IStatus.ERROR, UIPlugin.INSTANCE.getSymbolicName(), e.getMessage()));
						}
						moduleName = moduleNameText.getText();
					}
				});
				moduleNameText.setText(nameWithDashSeparator(initialName));

				GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(moduleNameText);

				Label versionRangeLabel = new Label(composite, SWT.NONE);
				versionRangeLabel.setText(UIPlugin.INSTANCE.getString("_UI_VersionRange_label"));

				versionRequirementText = new Text(composite, SWT.BORDER);
				versionRequirementText.addModifyListener(new GuardedModifyListener() {
					@Override
					public void handleEvent(ModifyEvent ev) {
						try {
							VersionRange.create(versionRequirementText.getText());
							versionRequirementText.setForeground(colorOfText);
							updateStatus(OK_WITHOUT_TEXT);
						}
						catch(IllegalArgumentException e) {
							versionRequirementText.setForeground(colorOfBadText);
							updateStatus(new Status(IStatus.ERROR, UIPlugin.INSTANCE.getSymbolicName(), e.getMessage()));
						}
						versionRequirement = versionRequirementText.getText();
					}
				});

				versionRequirementText.setText(initialVersionRequirement == null
						? ""
						: initialVersionRequirement);

				GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(
					versionRequirementText);
				return filteredList;
			}

			protected String getName() {
				return moduleName;
			}

			protected String getVersionRequirement() {
				return versionRequirement;
			}

			@Override
			protected void handleDefaultSelected() {
				setSelectedValuesIfSingle();
				super.handleDefaultSelected();
			}

			@Override
			protected void handleSelectionChanged() {
				setSelectedValuesIfSingle();
				super.handleSelectionChanged();
			}

			private void setSelectedValuesIfSingle() {
				if(initialName != null) {
					// First selection doesn't count since we trust the initial values. Next time around is
					// a different matter
					initialName = null;
					initialVersionRequirement = null;
					return;
				}

				Object[] selection = getSelectedElements();
				if(selection.length == 1) {
					ModuleInfo mi = (ModuleInfo) selection[0];
					moduleNameText.setText(mi.getName().withSeparator('-').toString());
					Version v = mi.getVersion();
					versionRequirementText.setText(v == null
							? ""
							: v.toString());
				}
			}

			@Override
			protected void updateOkState() {
				Button okButton = getOkButton();
				if(okButton != null) {
					String moduleName = trimToNull(moduleNameText.getText());
					String versionRequirement = trimToNull(versionRequirementText.getText());
					if(getSelectedElements().length != 0)
						okButton.setEnabled(true);
					else {
						boolean weHaveData = moduleName != null && !moduleName.equals(initialName) &&
								(versionRequirement == null || !versionRequirement.equals(initialVersionRequirement));
						okButton.setEnabled(weHaveData);
					}
				}
			}
		}

		private TableViewer tableViewer;

		private DependenciesSectionPart(Composite parent, FormToolkit toolkit) {
			super(parent, toolkit, Section.DESCRIPTION | Section.NO_TITLE);

			Section section = getSection();

			section.setDescription(UIPlugin.INSTANCE.getString("_UI_Dependencies_description")); //$NON-NLS-1$

			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(section);

			Composite client = toolkit.createComposite(section);
			client.setLayout(new GridLayout(2, false));
			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(client);

			Composite tableComposite = new Composite(client, SWT.NONE);
			TableColumnLayout tableColumnLayout = new TableColumnLayout();
			tableComposite.setLayout(tableColumnLayout);
			GridData containerLD = new GridData(SWT.FILL, SWT.FILL, true, true);

			// This keeps the table from growing every time the form is reflow()ed
			containerLD.widthHint = 1;

			tableComposite.setLayoutData(containerLD);

			tableViewer = new TableViewer(tableComposite, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION |
					SWT.BORDER);

			tableViewer.setContentProvider(new IStructuredContentProvider() {
				public void dispose() {
					// do nothing
				}

				public Object[] getElements(Object inputElement) {
					List<Dependency> deps;
					if(inputElement instanceof MetadataModel)
						deps = ((MetadataModel) inputElement).getDependencies();
					else
						deps = Collections.emptyList();
					return deps.toArray(new Dependency[deps.size()]);
				}

				public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
					clearMessages();
				}
			});
			ColumnViewerToolTipSupport.enableFor(tableViewer, ToolTip.NO_RECREATE);

			TableViewerColumn nameCol = new TableViewerColumn(tableViewer, SWT.NONE);
			nameCol.getColumn().setText(UIPlugin.INSTANCE.getString("_UI_Name_label"));
			nameCol.setLabelProvider(new ColumnLabelProvider() {
				@Override
				public Image getImage(Object element) {
					String[] parts = splitName(emptyIfNull(getText(element)));
					String owner = parts[0];
					String name = parts[1];
					int row = getRowNumber(element);
					int os = validateName(owner, "o" + row, null, "_UI_Module_owner_missing"); //$NON-NLS-1$
					int is = validateName(name, "n" + row, null, "_UI_Module_name_missing"); //$NON-NLS-1$
					if(os == IMessage.ERROR || is == IMessage.ERROR)
						return error;
					if(os == IMessage.WARNING || is == IMessage.WARNING)
						return warning;
					return null;
				}

				@Override
				public String getText(Object element) {
					return ((MetadataModel.Dependency) element).getModuleName();
				}

				@Override
				public Point getToolTipShift(Object object) {
					return new Point(0, 15);
				}

				@Override
				public String getToolTipText(Object element) {
					String[] parts = splitName(emptyIfNull(getText(element)));
					String owner = parts[0];
					if(owner == null || owner.length() == 0)
						return UIPlugin.INSTANCE.getString("_UI_Module_owner_missing");
					String name = parts[1];
					if(name.length() == 0)
						return UIPlugin.INSTANCE.getString("_UI_Module_name_missing");
					try {
						checkName(owner, true);
					}
					catch(IllegalArgumentException e) {
						return e.getMessage();
					}
					try {
						checkName(name, true);
					}
					catch(IllegalArgumentException e) {
						return e.getMessage();
					}
					return null;
				}
			});
			tableColumnLayout.setColumnData(nameCol.getColumn(), new ColumnWeightData(5));

			TableViewerColumn vrCol = new TableViewerColumn(tableViewer, SWT.NONE);
			vrCol.getColumn().setText(UIPlugin.INSTANCE.getString("_UI_Version_label"));
			vrCol.setLabelProvider(new ColumnLabelProvider() {

				@Override
				public Image getImage(Object element) {
					String versionRequirement = emptyIfNull(getText(element));
					int vs = validateVersionRequirement(versionRequirement, "v" + getRowNumber(element));
					if(vs == IMessage.ERROR)
						return error;
					if(vs == IMessage.WARNING)
						return warning;
					return null;
				}

				@Override
				public String getText(Object element) {
					return ((MetadataModel.Dependency) element).getVersionRequirement();
				}
			});
			tableColumnLayout.setColumnData(vrCol.getColumn(), new ColumnWeightData(3));

			Table table = tableViewer.getTable();
			table.setHeaderVisible(true);
			table.setLinesVisible(true);
			table.getBorderWidth();

			Composite buttonsComposite = toolkit.createComposite(client);
			GridLayout gridLayout = new GridLayout(1, false);
			gridLayout.marginHeight = 0;
			gridLayout.marginWidth = 0;
			buttonsComposite.setLayout(gridLayout);

			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).applyTo(buttonsComposite);

			final Button addButton = toolkit.createButton(
				buttonsComposite, UIPlugin.INSTANCE.getString("_UI_Add_label"), SWT.PUSH); //$NON-NLS-1$
			final Button editButton = toolkit.createButton(
				buttonsComposite, UIPlugin.INSTANCE.getString("_UI_Edit_label"), SWT.PUSH); //$NON-NLS-1$
			final Button removeButton = toolkit.createButton(
				buttonsComposite, UIPlugin.INSTANCE.getString("_UI_Remove_label"), SWT.PUSH); //$NON-NLS-1$

			addButton.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent se) {
					if(allowModification()) {
						EditDependencyDialog dialog = new EditDependencyDialog(getEditor().getSite().getShell(), null);

						dialog.setMultipleSelection(true);
						dialog.setElements(getModuleChoices(null));

						if(dialog.open() == Window.OK) {
							MetadataModel model = getModel();
							Object[] results = dialog.getResult();
							if(results.length > 1) {
								for(Object result : results) {
									ModuleInfo module = (ModuleInfo) result;
									model.addOrUpdateDependency(
										module.getName().withSeparator('-').toString(), module.getVersion().toString());
								}
							}
							else
								model.addOrUpdateDependency(dialog.getName(), dialog.getVersionRequirement());
							refresh();
						}
					}
					else {
						addButton.setEnabled(false);
						editButton.setEnabled(false);
						removeButton.setEnabled(false);
					}
				}
			});
			addButton.setEnabled(getEditor().getSourcePage().isEditable());

			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).applyTo(addButton);

			editButton.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent se) {
					if(allowModification()) {
						MetadataModel.Dependency dependency = (MetadataModel.Dependency) ((IStructuredSelection) tableViewer.getSelection()).getFirstElement();
						EditDependencyDialog dialog = new EditDependencyDialog(
							getEditor().getSite().getShell(), dependency);
						dialog.setElements(getModuleChoices(dependency.getModuleName()));
						dialog.setFilter(dependency.getModuleName());

						if(dialog.open() == Window.OK) {
							getModel().addOrUpdateDependency(dialog.getName(), dialog.getVersionRequirement());
							refresh();
						}
					}
					else {
						addButton.setEnabled(false);
						editButton.setEnabled(false);
						removeButton.setEnabled(false);
					}
				}
			});
			editButton.setEnabled(false);

			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).applyTo(editButton);

			removeButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent se) {
					if(allowModification()) {
						@SuppressWarnings("unchecked")
						Iterator<MetadataModel.Dependency> selections = ((IStructuredSelection) tableViewer.getSelection()).iterator();
						while(selections.hasNext())
							getModel().removeDependency(selections.next());
						refresh();
					}
					else {
						addButton.setEnabled(false);
						editButton.setEnabled(false);
						removeButton.setEnabled(false);
					}
				}
			});
			removeButton.setEnabled(false);

			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BOTTOM).applyTo(removeButton);

			tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
				public void selectionChanged(SelectionChangedEvent sce) {
					IStructuredSelection selection = (IStructuredSelection) sce.getSelection();
					if(getEditor().getSourcePage().isEditable()) {
						editButton.setEnabled(selection.size() == 1);
						removeButton.setEnabled(selection.size() > 0);
					}
					else {
						addButton.setEnabled(false);
						editButton.setEnabled(false);
						removeButton.setEnabled(false);
					}
				}
			});
			section.setClient(client);
		}

		private void addDependencyIfNotPresent(Metadata metadata, Map<String, ModuleInfo> moduleInfos,
				ModuleName toBeEdited) throws IOException {
			ModuleName moduleName = metadata.getName();
			if(toBeEdited == null || !toBeEdited.equals(moduleName))
				for(MetadataModel.Dependency dependency : getModel().getDependencies())
					if(moduleName.withSeparator('-').toString().equals(
						nameWithDashSeparator(dependency.getModuleName())))
						return;

			ModuleInfo module = new ModuleInfo(moduleName, metadata.getVersion());
			moduleInfos.put(StringUtil.getModuleText(module), module);
		}

		private Object[] getModuleChoices(String toBeEdited) {

			Forge forge = getEditor().getForge();

			Map<String, ModuleInfo> modules = new HashMap<String, ModuleInfo>();
			IProject current = getCurrentProject();
			Diagnostic chain = new Diagnostic();
			ModuleName always = null;
			if(toBeEdited != null)
				try {
					always = new ModuleName(toBeEdited);
				}
				catch(IllegalArgumentException e) {
					// Keep always as null since a match is impossible
				}

			for(IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {

				try {
					if(current == null || project.getName().equals(current.getName()))
						continue;

					if(!project.hasNature(XtextProjectHelper.NATURE_ID))
						continue;

					for(File moduleRoot : forge.findModuleRoots(project.getLocation().toFile(), null)) {
						Metadata metadata = forge.createFromModuleDirectory(moduleRoot, false, null, null, chain);
						addDependencyIfNotPresent(metadata, modules, always);
					}
				}
				catch(Exception e) {
					UIPlugin.INSTANCE.log(e);
				}
			}
			EList<Object> choices = new UniqueEList.FastCompare<Object>(modules.values());
			return choices.toArray();
		}

		int getRowNumber(Object element) {
			List<Dependency> deps = ((MetadataModel) tableViewer.getInput()).getDependencies();
			int idx = deps.size();
			while(--idx >= 0)
				if(deps.get(idx) == element)
					break;
			return idx;
		}

		@Override
		public void refresh() {
			refresh = true;
			try {
				tableViewer.setInput(getModel());
				super.refresh();
			}
			finally {
				refresh = false;
			}
		}
	}

	private static final IStatus OK_WITHOUT_TEXT = new Status(IStatus.OK, UIPlugin.INSTANCE.getSymbolicName(), "", null);

	static String nameWithDashSeparator(String moduleName) {
		if(moduleName == null)
			return "";

		String[] qname = ModuleName.splitName(moduleName);
		if(qname[0] == null)
			return qname[1];

		return qname[0] + '-' + qname[1];
	};

	private Color colorOfText;

	private Color colorOfBadText;

	private Image error;

	private Image warning;

	public ModuleDependenciesPage(ModuleMetadataEditor editor, String id, String title) {
		super(editor, id, title);
	}

	@Override
	protected void createFormContent(final IManagedForm managedForm) {
		super.createFormContent(managedForm);
		managedForm.getForm().setText(UIPlugin.INSTANCE.getString("_UI_Dependencies_title"));

		ISharedImages sharedImages = PlatformUI.getWorkbench().getSharedImages();
		error = sharedImages.getImage(ISharedImages.IMG_OBJS_ERROR_TSK);
		warning = sharedImages.getImage(ISharedImages.IMG_OBJS_WARN_TSK);

		Composite body = managedForm.getForm().getBody();

		Display display = body.getDisplay();
		colorOfBadText = display.getSystemColor(SWT.COLOR_RED);

		body.setLayout(new GridLayout(1, true));

		FormToolkit toolkit = managedForm.getToolkit();
		managedForm.addPart(new DependenciesSectionPart(body, toolkit));
	}

	@Override
	IFile getCurrentFile() {
		return getEditor().getFile();
	}

	private IProject getCurrentProject() {
		IFile adapter = getCurrentFile();
		return adapter == null
				? null
				: adapter.getProject();
	}

	@Override
	MetadataModel getModel() {
		return getEditor().getOverviewPage().getModel();
	}
}
