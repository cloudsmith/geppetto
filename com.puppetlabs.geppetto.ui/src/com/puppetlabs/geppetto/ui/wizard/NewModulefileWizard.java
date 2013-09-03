package com.puppetlabs.geppetto.ui.wizard;

import static com.puppetlabs.geppetto.forge.Forge.METADATA_JSON_NAME;
import static com.puppetlabs.geppetto.forge.Forge.MODULEFILE_NAME;
import static com.puppetlabs.geppetto.forge.model.Constants.UTF_8;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.lang.reflect.InvocationTargetException;

import com.puppetlabs.geppetto.common.os.StreamUtil.OpenBAStream;
import com.puppetlabs.geppetto.forge.v2.model.ModuleName;
import com.puppetlabs.geppetto.ui.UIPlugin;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Path;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.dialogs.ErrorDialog;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.actions.WorkspaceModifyOperation;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.ide.DialogUtil;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.wizards.newresource.ResourceMessages;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;

/**
 * This is a sample new wizard. Its role is to create a new file
 * resource in the provided container. If the container resource
 * (a folder or a project) is selected in the workspace
 * when the wizard is opened, it will accept it as the target
 * container. The wizard creates one file called &quot;Modulefile&quot;.
 */

public class NewModulefileWizard extends BasicNewResourceWizard implements INewWizard {
	public static class NewModulefileWizardPage extends WizardNewFileCreationPage {

		public NewModulefileWizardPage(IStructuredSelection selection) {
			super("newPuppetModulefilePage", selection);
			setTitle("Puppet Modulefile File");
			setFileName(MODULEFILE_NAME);
			setFileExtension("");
			setDescription("This wizard creates a new Modulefile (meta data for a puppet module).");
		}

		@Override
		protected void createAdvancedControls(Composite parent) {
			// DO NOTHING - Not meaningful to link (user can always use general "create file" if this is wanted.
		}

		@Override
		protected void createLinkTarget() {
			// DO NOTHING - The advanced linked resource section is not created.
		}

		@Override
		protected InputStream getInitialContents() {
			// Cheat by creating content manually here - not really worth the trouble of creating a
			// model to get these empty strings
			//
			IPath path = getContainerFullPath();
			String folderName = path.lastSegment();
			String[] split = ModuleName.splitName(folderName);
			StringBuilder bld = new StringBuilder();
			bld.append("name '");
			if(split[0] != null) {
				bld.append(split[0]);
				bld.append('-');
				bld.append(split[1]);
			}
			bld.append("'\nversion '0.1.0'\n\nauthor ''\nlicense ''\n");
			return new ByteArrayInputStream(bld.toString().getBytes(UTF_8));
		}

		@Override
		protected IStatus validateLinkedResource() {
			return Status.OK_STATUS;
		}

		@Override
		protected boolean validatePage() {
			boolean valid = super.validatePage();
			if(!getFileName().equals(MODULEFILE_NAME)) {
				setErrorMessage("File name must be '" + MODULEFILE_NAME + '\'');
				valid = false;
			}
			return valid;
		}
	}

	// private ISelection selection;

	protected static void ensureMetadataJSONExists(IFile moduleFile, IProgressMonitor monitor) {
		IFile mdjson = moduleFile.getParent().getFile(Path.fromPortableString(METADATA_JSON_NAME));
		if(mdjson.exists())
			return;

		try {
			OpenBAStream oba = new OpenBAStream();
			PrintStream ps = new PrintStream(oba);
			ps.println("{}");
			ps.close();
			mdjson.create(oba.getInputStream(), IResource.DERIVED, monitor);
		}
		catch(CoreException e) {
		}
	}

	private NewModulefileWizardPage page;

	/**
	 * Constructor for NewManifestWizard.
	 */
	public NewModulefileWizard() {
		setNeedsProgressMonitor(true);
	}

	/**
	 * Adding the page to the wizard.
	 */

	@Override
	public void addPages() {
		page = new NewModulefileWizardPage(selection);
		addPage(page);
	}

	@Override
	protected void initializeDefaultPageImageDescriptor() {
		ImageDescriptor desc = UIPlugin.getImageDesc("full/wizban/NewPuppetManifest.png");
		if(desc == null)
			desc = IDEWorkbenchPlugin.getIDEImageDescriptor("wizban/newfile_wiz.png");//$NON-NLS-1$
		setDefaultPageImageDescriptor(desc);
	}

	@Override
	public boolean performFinish() {
		// Open editor on new file.
		try {
			final IFile file = page.createNewFile();
			if(file == null)
				return false;

			selectAndReveal(file);

			getContainer().run(false, false, new WorkspaceModifyOperation() {

				@Override
				protected void execute(IProgressMonitor progressMonitor) throws InvocationTargetException {
					IWorkbenchWindow dw = getWorkbench().getActiveWorkbenchWindow();
					if(dw != null)
						try {
							IWorkbenchPage page = dw.getActivePage();
							if(page != null) {
								// Ensure that the 'metadata.json' file exists prior to opening
								ensureMetadataJSONExists(file, progressMonitor);
								IDE.openEditor(page, file, true);
							}
						}
						catch(Exception e) {
							throw new InvocationTargetException(e);
						}
				}
			});

			return true;
		}
		catch(InvocationTargetException e) {
			Throwable t = e.getTargetException();
			if(t instanceof PartInitException)
				DialogUtil.openError(
					getShell(), ResourceMessages.FileResource_errorMessage, t.getMessage(), (PartInitException) t);
			else if(t instanceof CoreException)
				ErrorDialog.openError(
					getShell(), ResourceMessages.FileResource_errorMessage, t.getMessage(),
					((CoreException) t).getStatus());
			else
				MessageDialog.openError(getShell(), ResourceMessages.FileResource_errorMessage, t.getMessage());
		}
		catch(InterruptedException e) {
		}
		return false;
	}

}
