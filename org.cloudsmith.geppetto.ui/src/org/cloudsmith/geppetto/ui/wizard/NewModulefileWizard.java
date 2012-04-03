package org.cloudsmith.geppetto.ui.wizard;

import java.io.InputStream;

import org.cloudsmith.geppetto.ui.UIPlugin;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.INewWizard;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.IWorkbenchWindow;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.dialogs.WizardNewFileCreationPage;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.internal.ide.DialogUtil;
import org.eclipse.ui.internal.ide.IDEWorkbenchPlugin;
import org.eclipse.ui.internal.wizards.newresource.ResourceMessages;
import org.eclipse.ui.wizards.newresource.BasicNewResourceWizard;
import org.eclipse.xtext.util.StringInputStream;

/**
 * This is a sample new wizard. Its role is to create a new file
 * resource in the provided container. If the container resource
 * (a folder or a project) is selected in the workspace
 * when the wizard is opened, it will accept it as the target
 * container. The wizard creates one file called "Modulefile".
 */

public class NewModulefileWizard extends BasicNewResourceWizard implements INewWizard {
	public static class NewModulefileWizardPage extends WizardNewFileCreationPage {

		public NewModulefileWizardPage(IStructuredSelection selection) {
			super("newPuppetModulefilePage", selection);
			setTitle("Puppet Modulefile File");
			setFileName("Modulefile");
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
			return new StringInputStream("name ''\nversion ''\n\nauthor ''\nlicense ''\n");
		}

		@Override
		protected IStatus validateLinkedResource() {
			return Status.OK_STATUS;
		}

		@Override
		protected boolean validatePage() {
			boolean valid = super.validatePage();
			if(!getFileName().equals("Modulefile")) {
				setErrorMessage("File name must be 'Modulefile'");
				valid = false;
			}
			return valid;
		}
	}

	// private ISelection selection;

	private NewModulefileWizardPage page;

	/**
	 * Constructor for NewManifestWizard.
	 */
	public NewModulefileWizard() {
		super();
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
		ImageDescriptor desc = UIPlugin.Implementation.imageDescriptorFromPlugin(
			UIPlugin.INSTANCE.getSymbolicName(), "icons/full/wizban/NewPuppetManifest.png");
		if(desc == null)
			desc = IDEWorkbenchPlugin.getIDEImageDescriptor("wizban/newfile_wiz.png");//$NON-NLS-1$
		setDefaultPageImageDescriptor(desc);
	}

	@Override
	public boolean performFinish() {
		IFile file = page.createNewFile();
		if(file == null) {
			return false;
		}

		selectAndReveal(file);

		// Open editor on new file.
		IWorkbenchWindow dw = getWorkbench().getActiveWorkbenchWindow();
		try {
			if(dw != null) {
				IWorkbenchPage page = dw.getActivePage();
				if(page != null) {
					IDE.openEditor(page, file, true);
				}
			}
		}
		catch(PartInitException e) {
			DialogUtil.openError(dw.getShell(), ResourceMessages.FileResource_errorMessage, e.getMessage(), e);
		}

		return true;
	}

}
