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

import static com.google.common.base.Strings.nullToEmpty;
import static org.cloudsmith.geppetto.common.Strings.emptyToNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.cloudsmith.geppetto.diagnostic.Diagnostic;
import org.cloudsmith.geppetto.forge.util.ModuleUtils;
import org.cloudsmith.geppetto.forge.v2.model.Dependency;
import org.cloudsmith.geppetto.forge.v2.model.Metadata;
import org.cloudsmith.geppetto.forge.v2.model.ModuleName;
import org.cloudsmith.geppetto.semver.Version;
import org.cloudsmith.geppetto.semver.VersionRange;
import org.cloudsmith.geppetto.ui.UIPlugin;
import org.cloudsmith.geppetto.ui.dialog.ModuleListSelectionDialog;
import org.cloudsmith.geppetto.ui.util.ResourceUtil;
import org.cloudsmith.geppetto.ui.util.StringUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IFolder;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceVisitor;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
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
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.dialogs.FilteredList;
import org.eclipse.ui.forms.IFormPart;
import org.eclipse.ui.forms.IManagedForm;
import org.eclipse.ui.forms.SectionPart;
import org.eclipse.ui.forms.editor.FormPage;
import org.eclipse.ui.forms.widgets.ExpandableComposite;
import org.eclipse.ui.forms.widgets.FormToolkit;
import org.eclipse.ui.forms.widgets.Section;
import org.eclipse.xtext.ui.XtextProjectHelper;

class ModuleMetadataOverviewPage extends FormPage {

	protected class DependenciesSectionPart extends SectionPart {

		protected class EditDependencyDialog extends ModuleListSelectionDialog {

			private Text versionRangeField = null;

			private VersionRange initialVersionRange = null;

			private VersionRange versionRange = null;

			public EditDependencyDialog(Shell parent) {
				super(parent);

				setMultipleSelection(false);
				setTitle(UIPlugin.INSTANCE.getString("_UI_EditDependency_title")); //$NON-NLS-1$
				setStatusLineAboveButtons(true);
			}

			@Override
			protected FilteredList createFilteredList(Composite parent) {
				FilteredList filteredList = super.createFilteredList(parent);

				Composite composite = new Composite(parent, SWT.NONE);

				GridLayout gridLayout = new GridLayout(1, false);
				gridLayout.marginHeight = 0;
				gridLayout.marginWidth = 0;

				composite.setLayout(gridLayout);

				GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(composite);

				Label versionRangeLabel = new Label(composite, SWT.NONE);
				versionRangeLabel.setText(UIPlugin.INSTANCE.getString("_UI_VersionRange_label"));

				versionRangeField = new Text(composite, SWT.BORDER);
				versionRangeField.addModifyListener(new ModifyListener() {
					@Override
					public void modifyText(ModifyEvent ev) {
						try {
							versionRange = VersionRange.create(versionRangeField.getText());
							versionRangeField.setForeground(colorOfText);
							updateStatus(Status.OK_STATUS);
						}
						catch(IllegalArgumentException e) {
							versionRangeField.setForeground(colorOfBadText);
							updateStatus(new Status(IStatus.ERROR, UIPlugin.INSTANCE.getSymbolicName(), e.getMessage()));
						}
					}
				});

				versionRangeField.setText(initialVersionRange == null
						? ""
						: initialVersionRange.toString());

				GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(versionRangeField);
				return filteredList;
			}

			protected VersionRange getVersionRange() {
				return versionRange;
			}

			@Override
			protected void handleDefaultSelected() {
				Object[] selection = getSelectedElements();
				if(selection.length > 0)
					versionRange = VersionRange.exact(((ModuleInfo) selection[0]).getVersion());
				super.handleDefaultSelected();
			}

			protected void setInitialVersionRange(VersionRange versioRange) {
				initialVersionRange = versioRange;
			}
		}

		private TableViewer tableViewer;

		private List<Dependency> dependencies = new UniqueEList<Dependency>();

		private DependenciesSectionPart(Composite parent, FormToolkit toolkit) {
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
			tableLayout.addColumnData(new ColumnWeightData(2));

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

					if(columnIndex == 0) {
						return dependency.getName().withSeparator('-').toString();
					}

					VersionRange versionRequirement = dependency.getVersionRequirement();

					return versionRequirement == null
							? ""
							: versionRequirement.toString();
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
					dialog.setElements(getModuleChoices(null));

					if(dialog.open() == Window.OK) {

						for(Object result : dialog.getResult()) {
							ModuleInfo module = (ModuleInfo) result;

							Dependency dependency = new Dependency();
							dependency.setName(module.getName());
							VersionRange vr = VersionRange.exact(module.getVersion());
							dependency.setVersionRequirement(vr);
							dependencies.add(dependency);
						}

						markDirty();

						tableViewer.setInput(dependencies);
					}
				}
			});

			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.TOP).applyTo(addButton);

			final Button editButton = toolkit.createButton(
				buttonsComposite, UIPlugin.INSTANCE.getString("_UI_Edit_label"), SWT.PUSH); //$NON-NLS-1$
			editButton.addSelectionListener(new SelectionAdapter() {

				@Override
				public void widgetSelected(SelectionEvent se) {
					Dependency dependency = (Dependency) ((IStructuredSelection) tableViewer.getSelection()).getFirstElement();

					EditDependencyDialog dialog = new EditDependencyDialog(getEditor().getSite().getShell());

					VersionRange versionRequirement = dependency.getVersionRequirement();

					if(versionRequirement != null)
						dialog.setInitialVersionRange(versionRequirement);

					dialog.setElements(getModuleChoices(dependency.getName()));
					dialog.setFilter(dependency.getName().withSeparator('-').toString());

					if(dialog.open() == Window.OK) {
						dependency.setName(((ModuleInfo) dialog.getFirstResult()).getName());
						VersionRange oldRange = dependency.getVersionRequirement();
						VersionRange newRange = dialog.getVersionRange();
						if(oldRange == null
								? newRange == null
								: oldRange.equals(newRange))
							return;

						dependency.setVersionRequirement(newRange);
						markDirty();
						tableViewer.setInput(dependencies);
					}
				}
			});
			editButton.setEnabled(false);

			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.CENTER).applyTo(editButton);

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

			GridDataFactory.fillDefaults().align(SWT.FILL, SWT.BOTTOM).applyTo(removeButton);

			tableViewer.addSelectionChangedListener(new ISelectionChangedListener() {
				public void selectionChanged(SelectionChangedEvent sce) {
					IStructuredSelection selection = (IStructuredSelection) sce.getSelection();

					editButton.setEnabled(selection.size() == 1);
					removeButton.setEnabled(selection.size() > 0);
				}
			});

			GridDataFactory.fillDefaults().grab(true, true).applyTo(dependenciesTable);
			section.setClient(client);
		}

		private void addDependencyIfNotPresent(IFile moduleFile, Map<String, ModuleInfo> moduleInfos,
				ModuleName toBeEdited) throws IOException {
			Metadata metadata = new Metadata();
			ModuleUtils.parseModulefile(moduleFile.getLocation().toFile(), metadata, new Diagnostic());

			ModuleName moduleName = metadata.getName();
			if(toBeEdited == null || !toBeEdited.equals(moduleName))
				for(Dependency dependency : dependencies)
					if(dependency.getName().equals(moduleName))
						return;

			ModuleInfo module = new ModuleInfo(moduleName, metadata.getVersion());
			moduleInfos.put(StringUtil.getModuleText(module), module);
		}

		@Override
		public void commit(boolean onSave) {
			Metadata metadata = getMetadata();
			metadata.getDependencies().clear();
			metadata.getDependencies().addAll(dependencies);
			getMetadataEditor().refreshFromOverviewPage();
			super.commit(onSave);
		}

		protected Object[] getModuleChoices(final ModuleName toBeEdited) {

			EList<Object> choices = new UniqueEList.FastCompare<Object>();

			final Map<String, ModuleInfo> modules = new HashMap<String, ModuleInfo>();

			final IProject current = getCurrentProject();
			final IFile currentModuleFile = getCurrentFile();

			for(IProject project : ResourcesPlugin.getWorkspace().getRoot().getProjects()) {

				try {
					if(!project.hasNature(XtextProjectHelper.NATURE_ID))
						continue;

					IFile moduleFile = ResourceUtil.getFile(project.getFullPath().append("Modulefile")); //$NON-NLS-1$
					if(moduleFile.equals(currentModuleFile))
						continue; // not meaningful to include dependency on itself

					if(moduleFile.exists()) {
						addDependencyIfNotPresent(moduleFile, modules, toBeEdited);
						continue;
					}

					if(current == null || !project.getName().equals(current.getName()))
						continue;

					// Also add all embedded modules from current project
					final IFolder modulesFolder = project.getFolder("modules");
					if(!modulesFolder.exists())
						continue;

					modulesFolder.accept(new IResourceVisitor() {

						@Override
						public boolean visit(IResource resource) throws CoreException {
							if(resource.equals(modulesFolder))
								return true;
							try {
								if(resource instanceof IFolder) {
									IFile moduleFile = ResourceUtil.getFile(resource.getFullPath().append("Modulefile"));
									if(moduleFile.equals(currentModuleFile))
										return false; // not meaningful to include dependency on itself
									if(moduleFile.exists())
										addDependencyIfNotPresent(moduleFile, modules, toBeEdited);
								}
							}
							catch(Exception e) {
								UIPlugin.INSTANCE.log(e);
							}

							return false;
						}
					}, IResource.DEPTH_ONE, false);
				}
				catch(Exception e) {
					UIPlugin.INSTANCE.log(e);
				}
			}

			choices.addAll(modules.values());

			return choices.toArray();
		}

		@Override
		public void markDirty() {
			ModuleMetadataOverviewPage.this.markDirty();
			super.markDirty();
		}

		@Override
		public void refresh() {
			Metadata metadata = getMetadata();
			dependencies.clear();
			dependencies.addAll(metadata.getDependencies());
			tableViewer.setInput(dependencies);
			super.refresh();
		}
	}

	protected class DetailsSectionPart extends SectionPart implements ModifyListener {

		protected Text sourceText;

		protected Text projectPageText;

		protected Text summaryText;

		protected Text descriptionText;

		private boolean refreshing;

		protected DetailsSectionPart(Composite parent, FormToolkit toolkit) {
			super(parent, toolkit, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);

			Section section = getSection();

			section.setText(UIPlugin.INSTANCE.getString("_UI_Details_title")); //$NON-NLS-1$
			section.setDescription(UIPlugin.INSTANCE.getString("_UI_Details_description")); //$NON-NLS-1$

			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).span(2, 1).applyTo(section);

			Composite client = toolkit.createComposite(section);
			client.setLayout(new GridLayout(2, false));

			toolkit.createLabel(client, UIPlugin.INSTANCE.getString("_UI_Source_label")); //$NON-NLS-1$

			sourceText = toolkit.createText(client, null);
			sourceText.addModifyListener(this);

			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(sourceText);

			toolkit.createLabel(client, UIPlugin.INSTANCE.getString("_UI_ProjectPage_label")); //$NON-NLS-1$

			projectPageText = toolkit.createText(client, null);
			projectPageText.addModifyListener(this);

			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(projectPageText);

			toolkit.createLabel(client, UIPlugin.INSTANCE.getString("_UI_Summary_label")); //$NON-NLS-1$

			summaryText = toolkit.createText(client, null);
			summaryText.addModifyListener(this);

			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(summaryText);

			toolkit.createLabel(client, UIPlugin.INSTANCE.getString("_UI_Description_label")); //$NON-NLS-1$

			descriptionText = toolkit.createText(client, null, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
			descriptionText.addModifyListener(this);

			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL).grab(true, true).applyTo(descriptionText);

			section.setClient(client);
		}

		@Override
		public void commit(boolean onSave) {
			Metadata metadata = getMetadata();
			metadata.setSource(emptyToNull(sourceText.getText()));
			metadata.setProjectPage(emptyToNull(projectPageText.getText()));
			metadata.setSummary(emptyToNull(summaryText.getText()));
			metadata.setDescription(emptyToNull(descriptionText.getText()));
			getMetadataEditor().refreshFromOverviewPage();
			super.commit(onSave);
		}

		@Override
		public void markDirty() {
			ModuleMetadataOverviewPage.this.markDirty();
			super.markDirty();
		}

		@Override
		public void modifyText(ModifyEvent me) {
			if(!refreshing)
				ModuleMetadataOverviewPage.this.markDirty();
		}

		@Override
		public void refresh() {
			refreshing = true;
			Metadata metadata = getMetadata();
			sourceText.setText(nullToEmpty(metadata.getSource()));
			projectPageText.setText(nullToEmpty(metadata.getProjectPage()));
			summaryText.setText(nullToEmpty(metadata.getSummary()));
			descriptionText.setText(nullToEmpty(metadata.getDescription()));
			super.refresh();
			refreshing = false;
		}
	}

	protected class GeneralInformationSectionPart extends SectionPart implements ModifyListener {

		private Text userText;

		private Text nameText;

		private Text versionText;

		private Text authorText;

		private Text licenseText;

		private boolean refreshing;

		protected GeneralInformationSectionPart(Composite parent, FormToolkit toolkit) {
			super(parent, toolkit, ExpandableComposite.TITLE_BAR | Section.DESCRIPTION);

			Section section = getSection();

			section.setText(UIPlugin.INSTANCE.getString("_UI_GeneralInformation_title")); //$NON-NLS-1$
			section.setDescription(UIPlugin.INSTANCE.getString("_UI_GeneralInformation_description")); //$NON-NLS-1$

			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(section);

			Composite client = toolkit.createComposite(section);
			client.setLayout(new GridLayout(4, false));

			toolkit.createLabel(client, UIPlugin.INSTANCE.getString("_UI_Name_label")); //$NON-NLS-1$

			userText = toolkit.createText(client, null);
			userText.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent ev) {
					try {
						ModuleName.checkName(userText.getText());
						userText.setForeground(colorOfText);
					}
					catch(IllegalArgumentException e) {
						userText.setForeground(colorOfBadText);
					}
					GeneralInformationSectionPart.this.modifyText(ev);
				}
			});
			userText.addVerifyListener(nameCharsVerifier);
			colorOfText = userText.getForeground();

			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(userText);

			toolkit.createLabel(client, "-"); //$NON-NLS-1$

			nameText = toolkit.createText(client, null);
			nameText.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent ev) {
					try {
						ModuleName.checkName(nameText.getText());
						nameText.setForeground(colorOfText);
					}
					catch(IllegalArgumentException e) {
						nameText.setForeground(colorOfBadText);
					}
					GeneralInformationSectionPart.this.modifyText(ev);
				}
			});
			nameText.addVerifyListener(nameCharsVerifier);

			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).applyTo(nameText);

			toolkit.createLabel(client, UIPlugin.INSTANCE.getString("_UI_Version_label")); //$NON-NLS-1$

			versionText = toolkit.createText(client, null);
			versionText.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent ev) {
					try {
						Version.create(versionText.getText());
						versionText.setForeground(colorOfText);
					}
					catch(IllegalArgumentException e) {
						versionText.setForeground(colorOfBadText);
					}
					GeneralInformationSectionPart.this.modifyText(ev);
				}
			});
			versionText.addVerifyListener(versionCharsVerifier);

			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).span(3, 1).applyTo(versionText);

			toolkit.createLabel(client, UIPlugin.INSTANCE.getString("_UI_Author_label")); //$NON-NLS-1$

			authorText = toolkit.createText(client, null);
			authorText.addModifyListener(this);

			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).span(3, 1).applyTo(authorText);

			toolkit.createLabel(client, UIPlugin.INSTANCE.getString("_UI_License_label")); //$NON-NLS-1$

			licenseText = toolkit.createText(client, null);
			licenseText.addModifyListener(this);

			GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).span(3, 1).applyTo(licenseText);

			section.setClient(client);
		}

		@Override
		public void commit(boolean onSave) {
			Metadata metadata = getMetadata();
			String user = emptyToNull(userText.getText());
			String name = emptyToNull(nameText.getText());
			if(user == null && name == null)
				metadata.setName(null);
			else {
				try {
					metadata.setName(new ModuleName(user, name));
				}
				catch(IllegalArgumentException e) {
				}
			}

			String version = versionText.getText();
			if(version == null) {
				metadata.setVersion(null);
			}
			else {
				try {
					metadata.setVersion(Version.create(versionText.getText()));
				}
				catch(IllegalArgumentException e) {
				}
			}

			metadata.setAuthor(emptyToNull(authorText.getText()));
			metadata.setLicense(emptyToNull(licenseText.getText()));
			getMetadataEditor().refreshFromOverviewPage();
			super.commit(onSave);
		}

		@Override
		public void markDirty() {
			ModuleMetadataOverviewPage.this.markDirty();
			super.markDirty();
		}

		@Override
		public void modifyText(ModifyEvent me) {
			if(!refreshing)
				markDirty();
		}

		@Override
		public void refresh() {
			refreshing = true;
			Metadata metadata = getMetadata();
			ModuleName qname = metadata.getName();
			if(qname == null) {
				userText.setText("");
				nameText.setText("");
			}
			else {
				userText.setText(nullToEmpty(qname.getOwner()));
				nameText.setText(nullToEmpty(qname.getName()));
			}
			Version version = metadata.getVersion();
			versionText.setText(version == null
					? ""
					: version.toString());
			authorText.setText(nullToEmpty(metadata.getAuthor()));
			licenseText.setText(nullToEmpty(metadata.getLicense()));
			super.refresh();
			refreshing = false;
		}
	}

	private static VerifyListener nameCharsVerifier = new VerifyListener() {
		@Override
		public void verifyText(VerifyEvent ev) {
			// Verify that the typed character is valid in a module name. This does not include the owner/name separator
			// since owner and name are typed in separate fields
			char c = ev.character;
			ev.doit = c < 0x20 || c == '_' || c >= '0' && c <= '9' || c >= 'a' && c <= 'z';
		}
	};

	private static VerifyListener versionCharsVerifier = new VerifyListener() {
		@Override
		public void verifyText(VerifyEvent ev) {
			// Verify that the typed character is valid in a module name. This does not include the owner/name separator
			// since owner and name are typed in separate fields
			char c = ev.character;
			// @fmtOff
			ev.doit =
				   c < 0x20
				|| c == '_'
				|| c == '-'
				|| c == '.'
				|| c >= '0' && c <= '9'
				|| c >= 'a' && c <= 'z'
				|| c >= 'A' && c <= 'Z';
			// @fmtOn
		}
	};

	private Color colorOfText;

	private Color colorOfBadText;

	public ModuleMetadataOverviewPage(ModuleMetadataEditor editor, String id, String title) {
		super(editor, id, title);
	}

	@Override
	protected void createFormContent(final IManagedForm managedForm) {
		super.createFormContent(managedForm);
		Composite body = managedForm.getForm().getBody();

		Display display = body.getDisplay();
		colorOfBadText = display.getSystemColor(SWT.COLOR_RED);

		body.setLayout(new GridLayout(2, true));

		FormToolkit toolkit = managedForm.getToolkit();

		managedForm.addPart(new GeneralInformationSectionPart(body, toolkit));

		managedForm.addPart(new DependenciesSectionPart(body, toolkit));

		managedForm.addPart(new DetailsSectionPart(body, toolkit));
	}

	protected IFile getCurrentFile() {
		return (IFile) getEditor().getEditorInput().getAdapter(IFile.class);
	}

	protected IProject getCurrentProject() {
		IFile adapter = (IFile) getEditor().getEditorInput().getAdapter(IFile.class);
		if(adapter == null)
			return null;
		return adapter.getProject();
	}

	protected Metadata getMetadata() {
		return getMetadataEditor().getMetadata();
	}

	private ModuleMetadataEditor getMetadataEditor() {
		return (ModuleMetadataEditor) getEditor();
	}

	void markDirty() {
		getMetadataEditor().overviewInCharge();
	}

	void markStale() {
		for(IFormPart part : getManagedForm().getParts())
			if(part instanceof SectionPart)
				((SectionPart) part).markStale();
	}
}
