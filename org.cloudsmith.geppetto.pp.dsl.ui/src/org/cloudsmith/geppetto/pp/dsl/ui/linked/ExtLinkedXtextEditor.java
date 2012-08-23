/**
 * Copyright (c) 2010, 2012 Cloudsmith Inc.
 * The code, documentation and other materials contained herein have been
 * licensed under the Eclipse Public License - v 1.0 by the copyright holder
 * listed above, as the Initial Contributor under such license. The text of
 * such license is available at www.eclipse.org.
 */

package org.cloudsmith.geppetto.pp.dsl.ui.linked;

import java.io.File;

import org.cloudsmith.geppetto.pp.dsl.ui.preferences.data.FormatterGeneralPreferences;
import org.eclipse.core.filesystem.EFS;
import org.eclipse.core.filesystem.IFileStore;
import org.eclipse.core.filesystem.URIUtil;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.QualifiedName;
import org.eclipse.emf.common.ui.URIEditorInput;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.IFileEditorInput;
import org.eclipse.ui.IURIEditorInput;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.FileStoreEditorInput;
import org.eclipse.ui.internal.editors.text.EditorsPlugin;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.ui.texteditor.AbstractDecoratedTextEditorPreferenceConstants;
import org.eclipse.ui.texteditor.IDocumentProvider;
import org.eclipse.ui.texteditor.SourceViewerDecorationSupport;
import org.eclipse.ui.views.contentoutline.IContentOutlinePage;
import org.eclipse.xtext.ui.editor.XtextEditor;
import org.eclipse.xtext.ui.editor.outline.impl.OutlinePage;
import org.eclipse.xtext.ui.editor.preferences.IPreferenceStoreAccess;

import com.google.inject.Inject;

/**
 * This class extends the standard XtextEditor to make it capable of
 * opening and saving external files by managing them as linked resources.
 * 
 * This implementation also supports temporary files than when saved will change to
 * a SaveAs operation (also see {@link TmpFileStoreEditorInput}).
 * 
 * An editor customizer can also be bound and it will receive calls to manage the content
 * of the context menu (see {@link IExtXtextEditorCustomizer}).
 * 
 * Also see {@link ExtLinkedXtextEditorMatchingStrategy} which is required to ensure multiple
 * editors are not opened for the same file.
 * 
 */
public class ExtLinkedXtextEditor extends XtextEditor {

	@Inject
	private IExtXtextEditorCustomizer editorCustomizer;

	/**
	 * Property for last saved location - property stored as persistent property in the
	 * workspace root.
	 * TODO: this should come from the customizer
	 */
	public static final QualifiedName LAST_SAVEAS_LOCATION = new QualifiedName(
		"org.cloudsmith.geppetto.pp.dsl.ui", "lastSaveLocation");

	@Inject
	ISaveActions saveActions;

	/**
	 * Preference key for showing print margin ruler.
	 */
	private final static String PRINT_MARGIN = AbstractDecoratedTextEditorPreferenceConstants.EDITOR_PRINT_MARGIN;

	/**
	 * Preference key for print margin ruler color.
	 */
	private final static String PRINT_MARGIN_COLOR = AbstractDecoratedTextEditorPreferenceConstants.EDITOR_PRINT_MARGIN_COLOR;

	// This is the preference for the default print margin, PP uses the margin set in the formatter preferences
	// /**
	// * Preference key for print margin ruler column.
	// */
	// private final static String PRINT_MARGIN_COLUMN = AbstractDecoratedTextEditorPreferenceConstants.EDITOR_PRINT_MARGIN_COLUMN;
	//
	@Inject
	private IPreferenceStoreAccess preferenceAccess;

	@Inject
	public ExtLinkedXtextEditor() {
		super();
	}

	@Override
	protected void configureSourceViewerDecorationSupport(SourceViewerDecorationSupport support) {
		super.configureSourceViewerDecorationSupport(support);
		support.setMarginPainterPreferenceKeys(
			PRINT_MARGIN, PRINT_MARGIN_COLOR, FormatterGeneralPreferences.FORMATTER_MAXWIDTH);

		// support.setCharacterPairMatcher(characterPairMatcher);
		// support.setMatchingCharacterPainterPreferenceKeys(BracketMatchingPreferencesInitializer.IS_ACTIVE_KEY,
		// BracketMatchingPreferencesInitializer.COLOR_KEY);
	}

	/**
	 * When editor is disposed the unlinking behavior is triggered (depending on reason of dispose, the linked file
	 * may be unlinked).
	 * See {@link ExtLinkedFileHelper#unlinkInput(IFileEditorInput)} for more info,
	 */
	@Override
	public void dispose() {
		// Unlink the input if it was linked
		IEditorInput input = getEditorInput();
		if(input instanceof IFileEditorInput)
			ExtLinkedFileHelper.unlinkInput((IFileEditorInput) input);
		super.dispose();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.ui.editor.XtextEditor#doSave(org.eclipse.core.runtime.IProgressMonitor)
	 */
	@Override
	public void doSave(IProgressMonitor progressMonitor) {

		// If document is a temporary / untitled document, change "save" to "saveAs"
		final IEditorInput input = getEditorInput();
		if(input instanceof IFileEditorInput &&
				((IFileEditorInput) input).getFile().isLinked() &&
				((IFileEditorInput) input).getFile().getProject().getName().equals(
					ExtLinkedFileHelper.AUTOLINK_PROJECT_NAME)) {
			String val;
			try {
				val = ((FileEditorInput) input).getFile().getPersistentProperty(
					TmpFileStoreEditorInput.UNTITLED_PROPERTY);
			}
			catch(CoreException e) {
				// Don't know what to do here - this is really bad, but doSave does not expect
				// any errors.
				throw new WrappedException(e);
			}
			if(val != null && "true".equals(val)) {
				doSaveAs();
				return;
			}
		}
		saveActions.perform(getResource(), getDocument());
		super.doSave(progressMonitor);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.xtext.ui.editor.XtextEditor#doSaveAs()
	 */
	@Override
	public void doSaveAs() {
		saveActions.perform(getResource(), getDocument());
		super.doSaveAs();
		// make sure the outline is fully refreshed
		IContentOutlinePage outlinePage = (IContentOutlinePage) getAdapter(IContentOutlinePage.class);
		if(outlinePage instanceof OutlinePage)
			((OutlinePage) outlinePage).scheduleRefresh();
	}

	/**
	 * Allows customization of the editor title.
	 * 
	 * 
	 * @see org.eclipse.xtext.ui.editor.XtextEditor#doSetInput(org.eclipse.ui.IEditorInput)
	 */
	@Override
	protected void doSetInput(IEditorInput input) throws CoreException {
		super.doSetInput(input);
		String customTitle = editorCustomizer.customEditorTitle(input);
		if(customTitle != null)
			this.setPartName(customTitle);
	}

	// @Override
	// protected void initializeEditor() {
	// setPreferenceStore(preferenceAccess.getPreferenceStore());
	// // setPreferenceStore(EditorsPlugin.getDefault().getPreferenceStore());
	// }

	/**
	 * Overridden to allow customization of editor context menu via injected handler
	 * 
	 * @see org.eclipse.ui.editors.text.TextEditor#editorContextMenuAboutToShow(org.eclipse.jface.action.IMenuManager)
	 */
	@Override
	protected void editorContextMenuAboutToShow(IMenuManager menu) {
		super.editorContextMenuAboutToShow(menu);
		editorCustomizer.customizeEditorContextMenu(menu);

	}

	private IFile getWorkspaceFile(IFileStore fileStore) {
		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		IFile[] files = workspaceRoot.findFilesForLocationURI(fileStore.toURI());
		if(files != null && files.length == 1)
			return files[0];
		return null;
	}

	/**
	 * Translates an incoming IEditorInput being an FilestoreEditorInput, or IURIEditorInput
	 * that is not also a IFileEditorInput.
	 * FilestoreEditorInput is used when opening external files in an IDE environment.
	 * The result is that the regular XtextEditor gets an IEFSEditorInput which is also an
	 * IStorageEditorInput.
	 */
	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		// Can't do this until this time due to stupid callbacks from constructor before
		// injections have taken place.

		// ISourceViewer sourceViewer = getSourceViewer();
		// SourceViewerDecorationSupport decorationSupport = getSourceViewerDecorationSupport(sourceViewer);
		// decorationSupport.setMarginPainterPreferenceKeys(
		// PRINT_MARGIN, PRINT_MARGIN_COLOR, PPPreferenceConstants.FORMATTER_MAXWIDTH);

		// THE ISSUE HERE:
		// In the IDE, the File Open Dialog (and elsewhere) uses a FilestoreEditorInput class
		// which is an IDE specific implementation.
		// The state at this point:
		// 1. When creating a file, the IEditorInput is an IURIEditorInput
		// 2. The only (non IDE specific) interface implemented by FilestoreEditorInput is IURIEditorInput
		// 3. The creation of a file is however also an IFileEditorInput
		//
		// Remedy:
		if(input instanceof IURIEditorInput && !(input instanceof IFileEditorInput)) {
			java.net.URI uri = ((IURIEditorInput) input).getURI();
			String name = ((IURIEditorInput) input).getName();
			// Check if this is linkable input
			if(uri.getScheme().equals("file")) {
				IFile linkedFile = null;
				// check and process tmp file input (untitled)
				if(input instanceof TmpFileStoreEditorInput)
					try {
						linkedFile = ExtLinkedFileHelper.obtainLink(uri, true);
						linkedFile.setPersistentProperty(TmpFileStoreEditorInput.UNTITLED_PROPERTY, "true");
					}
					catch(CoreException e) {
						throw new PartInitException(e.getStatus());
					}
				else {
					linkedFile = ExtLinkedFileHelper.obtainLink(uri, false);
				}
				IFileEditorInput linkedInput = new FileEditorInput(linkedFile);
				super.init(site, linkedInput);

			}
			else {
				// use EMF URI (readonly) input - will open without validation though...
				// (Could be improved, i.e. perform a download, make readonly, and keep in project,
				// or stored in tmp, and processed as the other linked resources..
				URIEditorInput uriInput = new URIEditorInput(URI.createURI(uri.toString()), name);
				super.init(site, uriInput);
				return;
			}
			return;
		}
		super.init(site, input);

	}

	// SaveAs support for linked files - saves them on local disc, not to workspace if file is in special
	// hidden external file link project.
	@Override
	protected void performSaveAs(IProgressMonitor progressMonitor) {

		Shell shell = getSite().getShell();
		final IEditorInput input = getEditorInput();

		// Customize save as if the file is linked, and it is in the special external link project
		//
		if(input instanceof IFileEditorInput &&
				((IFileEditorInput) input).getFile().isLinked() &&
				((IFileEditorInput) input).getFile().getProject().getName().equals(
					ExtLinkedFileHelper.AUTOLINK_PROJECT_NAME)) {
			final IEditorInput newInput;
			IDocumentProvider provider = getDocumentProvider();

			// 1. If file is "untitled" suggest last save location
			// 2. ...otherwise use the file's location (i.e. likely to be a rename in same folder)
			// 3. If a "last save location" is unknown, use user's home
			//
			String suggestedName = null;
			String suggestedPath = null;
			{
				// is it "untitled"
				java.net.URI uri = ((IURIEditorInput) input).getURI();
				String tmpProperty = null;
				try {
					tmpProperty = ((IFileEditorInput) input).getFile().getPersistentProperty(
						TmpFileStoreEditorInput.UNTITLED_PROPERTY);
				}
				catch(CoreException e) {
					// ignore - tmpProperty will be null
				}
				boolean isUntitled = tmpProperty != null && "true".equals(tmpProperty);

				// suggested name
				IPath oldPath = URIUtil.toPath(uri);
				// TODO: input.getName() is probably always correct
				suggestedName = isUntitled
						? input.getName()
						: oldPath.lastSegment();

				// suggested path
				try {
					suggestedPath = isUntitled
							? ((IFileEditorInput) input).getFile().getWorkspace().getRoot().getPersistentProperty(
								LAST_SAVEAS_LOCATION)
							: oldPath.toOSString();
				}
				catch(CoreException e) {
					// ignore, suggestedPath will be null
				}

				if(suggestedPath == null) {
					// get user.home
					suggestedPath = System.getProperty("user.home");
				}
			}
			FileDialog dialog = new FileDialog(shell, SWT.SAVE);
			if(suggestedName != null)
				dialog.setFileName(suggestedName);
			if(suggestedPath != null)
				dialog.setFilterPath(suggestedPath);

			dialog.setFilterExtensions(new String[] { "*.pp", "*.*" });
			String path = dialog.open();
			if(path == null) {
				if(progressMonitor != null)
					progressMonitor.setCanceled(true);
				return;
			}

			// Check whether file exists and if so, confirm overwrite
			final File localFile = new File(path);
			if(localFile.exists()) {
				MessageDialog overwriteDialog = new MessageDialog(shell, "Save As", null, path +
						" already exists.\nDo you want to replace it?", MessageDialog.WARNING, new String[] {
						IDialogConstants.YES_LABEL, IDialogConstants.NO_LABEL }, 1); // 'No' is the default
				if(overwriteDialog.open() != Window.OK) {
					if(progressMonitor != null) {
						progressMonitor.setCanceled(true);
						return;
					}
				}
			}

			IFileStore fileStore;
			try {
				fileStore = EFS.getStore(localFile.toURI());
			}
			catch(CoreException ex) {
				EditorsPlugin.log(ex.getStatus());
				String title = "Problems During Save As...";
				String msg = "Save could not be completed. " + ex.getMessage();
				MessageDialog.openError(shell, title, msg);
				return;
			}

			IFile file = getWorkspaceFile(fileStore);
			if(file != null)
				newInput = new FileEditorInput(file);
			else {
				IURIEditorInput uriInput = new FileStoreEditorInput(fileStore);
				java.net.URI uri = uriInput.getURI();
				IFile linkedFile = ExtLinkedFileHelper.obtainLink(uri, false);

				newInput = new FileEditorInput(linkedFile);
			}

			if(provider == null) {
				// editor has been closed while the dialog was open
				return;
			}

			boolean success = false;
			try {

				provider.aboutToChange(newInput);
				provider.saveDocument(progressMonitor, newInput, provider.getDocument(input), true);
				success = true;

			}
			catch(CoreException x) {
				final IStatus status = x.getStatus();
				if(status == null || status.getSeverity() != IStatus.CANCEL) {
					String title = "Problems During Save As...";
					String msg = "Save could not be completed. " + x.getMessage();
					MessageDialog.openError(shell, title, msg);
				}
			}
			finally {
				provider.changed(newInput);
				if(success)
					setInput(newInput);
				// the linked file must be removed (esp. if it is an "untitled" link).
				ExtLinkedFileHelper.unlinkInput(((IFileEditorInput) input));
				// remember last saveAs location
				String lastLocation = URIUtil.toPath(((FileEditorInput) newInput).getURI()).toOSString();
				try {
					((FileEditorInput) newInput).getFile().getWorkspace().getRoot().setPersistentProperty(
						LAST_SAVEAS_LOCATION, lastLocation);
				}
				catch(CoreException e) {
					// ignore
				}
			}

			if(progressMonitor != null)
				progressMonitor.setCanceled(!success);

			return;
		}

		super.performSaveAs(progressMonitor);
	}

}
