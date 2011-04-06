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
package org.cloudsmith.geppetto.ui.editor;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.cloudsmith.geppetto.forge.Dependency;
import org.cloudsmith.geppetto.forge.Forge;
import org.cloudsmith.geppetto.forge.ForgeFactory;
import org.cloudsmith.geppetto.forge.Metadata;
import org.cloudsmith.geppetto.forge.ModuleInfo;
import org.cloudsmith.geppetto.ui.UIPlugin;
import org.cloudsmith.geppetto.ui.dialog.ModuleListSelectionDialog;
import org.cloudsmith.geppetto.ui.util.ResourceUtil;
import org.cloudsmith.geppetto.ui.util.StringUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.UniqueEList;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.viewers.ColumnWeightData;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableLayout;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.xtext.ui.XtextProjectHelper;

class ModuleMetadataOverviewPage extends FormPage {

	protected class DependenciesSectionPart extends SectionPart {

		protected Object[] moduleChoices;

		protected TableViewer tableViewer;

		protected List<Dependency> dependencies = new UniqueEList<Dependency>();;

		protected Forge forge;

		protected DependenciesSectionPart(Composite parent, FormToolkit toolkit) {
			super(parent, toolkit, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);

			Section section = getSection();

			section.setText(UIPlugin.INSTANCE.getString("_UI_Dependencies_title")); //$NON-NLS-1$
			section.setDescription(UIPlugin.INSTANCE.getString("_UI_Dependencies_description")); //$NON-NLS-1$

			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(section);

			Composite client = toolkit.createComposite(section);
			client.setLayout(new GridLayout(2, false));

			Table dependenciesTable = toolkit.createTable(client, SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI);

			TableLayout tableLayout = new TableLayout();
			tableLayout.addColumnData(new ColumnWeightData(3));
			tableLayout.addColumnData(new ColumnWeightData(1));

			dependenciesTable.setLayout(tableLayout);

			GridDataFactory.fillDefaults().grab(true, true).applyTo(dependenciesTable);

			dependenciesTable.setHeaderVisible(true);
			dependenciesTable.setLinesVisible(true);

			TableColumn nameColumn = new TableColumn(dependenciesTable, SWT.NONE);
			nameColumn.setText(UIPlugin.INSTANCE.getString("_UI_Name_label")); //$NON-NLS-1$

			TableColumn versionColumn = new TableColumn(dependenciesTable, SWT.NONE);
			versionColumn.setText(UIPlugin.INSTANCE.getString("_UI_Version_label")); //$NON-NLS-1$

			tableViewer = new TableViewer(dependenciesTable);
			tableViewer.setContentProvider(new IStructuredContentProvider() {
				public void dispose() {
					// do nothing
				}

				@SuppressWarnings("unchecked")
				public Object[] getElements(Object inputElement) {
					return ((List<Dependency>) inputElement).toArray();
				}

				public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
					// do nothing
				}
			});
			tableViewer.setLabelProvider(new ITableLabelProvider() {
				public void addListener(ILabelProviderListener listener) {
					// do nothing
				}

				public void dispose() {
					// do nothing
				}

				public Image getColumnImage(Object element, int columnIndex) {
					return null;
				}

				public String getColumnText(Object element, int columnIndex) {
					Dependency dependency = (Dependency) element;
					return columnIndex == 0
							? dependency.getName()
							: dependency.getVersionRequirement();
				}

				public boolean isLabelProperty(Object element, String property) {
					return false;
				}

				public void removeListener(ILabelProviderListener listener) {
					// do nothing
				}
			});

			Composite buttonsComposite = toolkit.createComposite(client);
			GridLayout gridLayout = new GridLayout(1, true);
			gridLayout.marginHeight = 0;
			gridLayout.marginWidth = 0;
			buttonsComposite.setLayout(gridLayout);

			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).applyTo(buttonsComposite);

			Button addButton = toolkit.createButton(
				buttonsComposite, UIPlugin.INSTANCE.getString("_UI_Add_label"), SWT.PUSH); //$NON-NLS-1$
			addButton.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent se) {
					ModuleListSelectionDialog dialog = new ModuleListSelectionDialog(getEditor().getSite().getShell());

					dialog.setMultipleSelection(true);
					dialog.setElements(getModuleChoices());

					if(dialog.open() == Window.OK) {

						for(Object result : dialog.getResult()) {
							ModuleInfo module = (ModuleInfo) result;

							Dependency dependency = ForgeFactory.eINSTANCE.createDependency();
							dependency.setName(module.getFullName());
							dependency.setVersionRequirement(">= " + module.getVersion()); //$NON-NLS-1$

							dependencies.add(dependency);
						}

						markDirty();

						tableViewer.setInput(dependencies);
					}
				}
			});

			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).applyTo(addButton);

			final Button removeButton = toolkit.createButton(
				buttonsComposite, UIPlugin.INSTANCE.getString("_UI_Remove_label"), SWT.PUSH); //$NON-NLS-1$
			removeButton.addSelectionListener(new SelectionAdapter() {
				@Override
				public void widgetSelected(SelectionEvent se) {
					ISelection selection = tableViewer.getSelection();

					for(Iterator<?> selections = ((IStructuredSelection) selection).iterator(); selections.hasNext();) {
						dependencies.remove(selections.next());
					}

					markDirty();

					tableViewer.setInput(dependencies);
				}
			});
			removeButton.setEnabled(false);

			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).applyTo(removeButton);

			tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
				public void selectionChanged(SelectionChangedEvent sce) {
					IStructuredSelection selection = (IStructuredSelection) sce.getSelection();

					removeButton.setEnabled(selection.size() > 0);
				}
			});

			GridDataFactory.fillDefaults().grab(true, true).applyTo(dependenciesTable);

			Metadata metadata = getMetadata();

			if(metadata != null) {
				dependencies.addAll(metadata.getDependencies());
			}

			tableViewer.setInput(dependencies);

			section.setClient(client);
		}

		@Override
		public void commit(boolean onSave) {
			Metadata metadata = getMetadata();

			if(metadata != null) {
				metadata.getDependencies().clear();
				metadata.getDependencies().addAll(dependencies);
			}

			super.commit(onSave);
		}

		protected Forge getForge() {

			if(forge == null) {
				forge = ForgeFactory.eINSTANCE.createForgeService().createForge(
					java.net.URI.create("http://forge.puppetlabs.com")); //$NON-NLS-1$
			}

			return forge;
		}

		protected Object[] getModuleChoices() {

			if(moduleChoices == null) {
				EList<Object> choices = new UniqueEList.FastCompare<Object>();

				Map<String, ModuleInfo> modules = new HashMap<String, ModuleInfo>();

				// try {
				// for(ModuleInfo module : getForge().search(null)) {
				// modules.put(StringUtil.getModuleText(module), module);
				// }
				//
				// }
				// catch(IOException ioe) {
				// ioe.printStackTrace();
				// }

				for(IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {

					try {
						if(project.hasNature(XtextProjectHelper.NATURE_ID)) {
							IFile moduleFile = ResourceUtil.getFile(project.getFullPath().append("Modulefile")); //$NON-NLS-1$

							if(moduleFile.exists()) {
								Metadata metadata = ForgeFactory.eINSTANCE.createMetadata();
								metadata.loadModuleFile(moduleFile.getLocation().toFile());

								ModuleInfo module = ForgeFactory.eINSTANCE.createModuleInfo();
								module.setFullName(metadata.getUser() + '/' + metadata.getName());
								module.setVersion(metadata.getVersion());

								modules.put(StringUtil.getModuleText(module), module);
							}
						}
					}
					catch(Exception e) {
						UIPlugin.INSTANCE.log(e);
					}
				}

				choices.addAll(modules.values());

				moduleChoices = choices.toArray();
			}

			return moduleChoices;
		}
	}

	protected class DetailsSectionPart extends SectionPart implements ModifyListener {

		protected Text sourceText;

		protected Text projectPageText;

		protected Text summaryText;

		protected Text descriptionText;

		protected DetailsSectionPart(Composite parent, FormToolkit toolkit) {
			super(parent, toolkit, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);

			Section section = getSection();

			section.setText(UIPlugin.INSTANCE.getString("_UI_Details_title")); //$NON-NLS-1$
			section.setDescription(UIPlugin.INSTANCE.getString("_UI_Details_description")); //$NON-NLS-1$

			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).span(2, 1).applyTo(section);

			Composite client = toolkit.createComposite(section);
			client.setLayout(new GridLayout(2, false));

			Metadata metadata = getMetadata();

			toolkit.createLabel(client, UIPlugin.INSTANCE.getString("_UI_Source_label")); //$NON-NLS-1$

			sourceText = toolkit.createText(client, metadata != null
					? metadata.getSource()
					: null);
			sourceText.addModifyListener(this);

			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(sourceText);

			toolkit.createLabel(client, UIPlugin.INSTANCE.getString("_UI_ProjectPage_label")); //$NON-NLS-1$

			URI projectPage = metadata != null
					? metadata.getProjectPage()
					: null;

			projectPageText = toolkit.createText(client, projectPage != null
					? projectPage.toString()
					: null);
			projectPageText.addModifyListener(this);

			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(projectPageText);

			toolkit.createLabel(client, UIPlugin.INSTANCE.getString("_UI_Summary_label")); //$NON-NLS-1$

			summaryText = toolkit.createText(client, metadata != null
					? metadata.getSummary()
					: null);
			summaryText.addModifyListener(this);

			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(summaryText);

			toolkit.createLabel(client, UIPlugin.INSTANCE.getString("_UI_Description_label")); //$NON-NLS-1$

			descriptionText = toolkit.createText(client, metadata != null
					? metadata.getDescription()
					: null, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
			descriptionText.addModifyListener(this);

			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(descriptionText);

			section.setClient(client);
		}

		@Override
		public void commit(boolean onSave) {
			Metadata metadata = getMetadata();

			if(metadata != null) {
				metadata.setSource(sourceText.getText());

				try {
					metadata.setProjectPage(new URI(projectPageText.getText()));
				}
				catch(URISyntaxException urise) {
					UIPlugin.INSTANCE.log(urise);
				}

				metadata.setSummary(summaryText.getText());
				metadata.setDescription(descriptionText.getText());
			}

			super.commit(onSave);
		}

		@Override
		public void modifyText(ModifyEvent me) {
			markDirty();
		}
	}

	protected class GeneralInformationSectionPart extends SectionPart implements ModifyListener {

		protected Text userText;

		protected Text nameText;

		protected Text versionText;

		protected Text authorText;

		protected Text licenseText;

		protected GeneralInformationSectionPart(Composite parent, FormToolkit toolkit) {
			super(parent, toolkit, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);

			Section section = getSection();

			section.setText(UIPlugin.INSTANCE.getString("_UI_GeneralInformation_title")); //$NON-NLS-1$
			section.setDescription(UIPlugin.INSTANCE.getString("_UI_GeneralInformation_description")); //$NON-NLS-1$

			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(section);

			Composite client = toolkit.createComposite(section);
			client.setLayout(new GridLayout(4, false));

			Metadata metadata = getMetadata();

			toolkit.createLabel(client, UIPlugin.INSTANCE.getString("_UI_Name_label")); //$NON-NLS-1$

			userText = toolkit.createText(client, metadata != null
					? metadata.getUser()
					: null);
			userText.addModifyListener(this);

			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(userText);

			toolkit.createLabel(client, "-"); //$NON-NLS-1$

			nameText = toolkit.createText(client, metadata != null
					? metadata.getName()
					: null);
			nameText.addModifyListener(this);

			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(nameText);

			toolkit.createLabel(client, UIPlugin.INSTANCE.getString("_UI_Version_label")); //$NON-NLS-1$

			versionText = toolkit.createText(client, metadata != null
					? metadata.getVersion()
					: null);
			versionText.addModifyListener(this);

			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).span(3, 1).applyTo(versionText);

			toolkit.createLabel(client, UIPlugin.INSTANCE.getString("_UI_Author_label")); //$NON-NLS-1$

			authorText = toolkit.createText(client, metadata != null
					? metadata.getAuthor()
					: null);
			authorText.addModifyListener(this);

			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).span(3, 1).applyTo(authorText);

			toolkit.createLabel(client, UIPlugin.INSTANCE.getString("_UI_License_label")); //$NON-NLS-1$

			licenseText = toolkit.createText(client, metadata != null
					? metadata.getLicense()
					: null);
			licenseText.addModifyListener(this);

			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).span(3, 1).applyTo(licenseText);

			section.setClient(client);
		}

		@Override
		public void commit(boolean onSave) {
			Metadata metadata = getMetadata();

			if(metadata != null) {
				metadata.setUser(userText.getText());
				metadata.setName(nameText.getText());
				metadata.setVersion(versionText.getText());
				metadata.setAuthor(authorText.getText());
				metadata.setLicense(licenseText.getText());
			}

			super.commit(onSave);
		}

		@Override
		public void modifyText(ModifyEvent me) {
			markDirty();
		}
	}

	public ModuleMetadataOverviewPage(ModuleMetadataEditor editor, String id, String title) {
		super(editor, id, title);
	}

	@Override
	protected void createFormContent(final IManagedForm managedForm) {
		super.createFormContent(managedForm);

		Composite body = managedForm.getForm().getBody();
		body.setLayout(new GridLayout(2, true));

		FormToolkit toolkit = managedForm.getToolkit();

		managedForm.addPart(new GeneralInformationSectionPart(body, toolkit));

		managedForm.addPart(new DependenciesSectionPart(body, toolkit));

		managedForm.addPart(new DetailsSectionPart(body, toolkit));
	}

	protected Metadata getMetadata() {
		return ((ModuleMetadataEditor) getEditor()).getMetadata();
	}
}
