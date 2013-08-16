package org.cloudsmith.geppetto.ui.wizard;

import org.cloudsmith.geppetto.ui.UIPlugin;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IPath;
import org.eclipse.jface.resource.ImageDescriptor;
import org.eclipse.jface.viewers.IStructuredSelection;
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

/**
 * This is a sample new wizard. Its role is to create a new file
 * resource in the provided container. If the container resource
 * (a folder or a project) is selected in the workspace
 * when the wizard is opened, it will accept it as the target
 * container. The wizard creates one file with the extension
 * "pp". If a sample multi-page editor (also available
 * as a template) is registered for the same extension, it will
 * be able to open it.
 */

public class NewManifestWizard extends BasicNewResourceWizard implements INewWizard {
	public static class NewManifestWizardPage extends WizardNewFileCreationPage {

		public NewManifestWizardPage(IStructuredSelection selection) {
			super("newPuppetManifestPage", selection);
			setTitle("Puppet Manifest File");
			setFileExtension("pp");
			setDescription("This wizard creates a new file with *.pp extension that can be opened by the Puppet Manifest Editor.");
		}

		@Override
		protected IFile createFileHandle(IPath filePath) {
			if(!"pp".equals(filePath.getFileExtension())) {
				filePath = filePath.addFileExtension("pp");
			}
			return super.createFileHandle(filePath);
		}

	}

	// private ISelection selection;

	private NewManifestWizardPage page;

	// /**
	// * The worker method. It will find the container, create the
	// * file if missing or just replace its contents, and open
	// * the editor on the newly created file.
	// */
	//
	// private void doFinish(String containerName, String fileName, IProgressMonitor monitor) throws CoreException {
	// // create a sample file
	// monitor.beginTask("Creating " + fileName, 2);
	// IWorkspaceRoot root = ResourcesPlugin.getWorkspace().getRoot();
	// IResource resource = root.findMember(new Path(containerName));
	// if(!resource.exists() || !(resource instanceof IContainer)) {
	// throwCoreException("Container \"" + containerName + "\" does not exist.");
	// }
	// IContainer container = (IContainer) resource;
	// final IFile file = container.getFile(new Path(fileName));
	// try {
	// InputStream stream = openContentStream();
	// if(file.exists()) {
	// file.setContents(stream, true, true, monitor);
	// }
	// else {
	// file.create(stream, true, monitor);
	// }
	// stream.close();
	// }
	// catch(IOException e) {
	// }
	// monitor.worked(1);
	// monitor.setTaskName("Opening file for editing...");
	// getShell().getDisplay().asyncExec(new Runnable() {
	// public void run() {
	// IWorkbenchPage page = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
	// try {
	// IDE.openEditor(page, file, true);
	// }
	// catch(PartInitException e) {
	// }
	// }
	// });
	// monitor.worked(1);
	// }

	// /**
	// * We will accept the selection in the workbench to see if
	// * we can initialize from it.
	// *
	// * @see IWorkbenchWizard#init(IWorkbench, IStructuredSelection)
	// */
	// @Override
	// public void init(IWorkbench workbench, IStructuredSelection selection) {
	// this.selection = selection;
	// }

	/**
	 * Constructor for NewManifestWizard.
	 */
	public NewManifestWizard() {
		super();
		setNeedsProgressMonitor(true);
	}

	/**
	 * Adding the page to the wizard.
	 */

	@Override
	public void addPages() {
		page = new NewManifestWizardPage(selection);
		addPage(page);
	}

	/*
	 * (non-Javadoc)
	 * Method declared on BasicNewResourceWizard.
	 */
	@Override
	protected void initializeDefaultPageImageDescriptor() {
		ImageDescriptor desc = UIPlugin.getImageDesc("icons/full/wizban/NewPuppetManifest.png");
		if(desc == null)
			desc = IDEWorkbenchPlugin.getIDEImageDescriptor("wizban/newfile_wiz.png");//$NON-NLS-1$
		setDefaultPageImageDescriptor(desc);
	}

	/*
	 * (non-Javadoc)
	 * Method declared on IWizard.
	 */
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
