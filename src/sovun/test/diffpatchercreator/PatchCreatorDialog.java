/*
 * Project        NDS Anaconda
 * (c) copyright  2013
 * Company        HARMAN Automotive Systems GmbH
 *        All rights reserved
 *
 * Secrecy Level  STRICTLY CONFIDENTIAL
 *
 * File           PatchCreatorDialog.java
 * Creation date  2013-07-04
 */
package sovun.test.diffpatchercreator;

import java.io.File;
import java.io.IOException;

import org.eclipse.jface.dialogs.Dialog;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

/*******************************************************************************
 * DB diff patch creator main window.
 * 
 * @author AVasilkov
 * @since 4.3
 ******************************************************************************/
public class PatchCreatorDialog extends Dialog {
	private String sourceFileName;
	private String targetFileName;
	private String patchFileName;
	private Text srcText, targetText, patchText;

	public PatchCreatorDialog(Shell shell) {
		super(shell);
		setShellStyle(getShellStyle() | SWT.RESIZE);
	}

	protected Control createContents(Composite container) {
		LauncherConfig config = null;
		try {
			config = LauncherConfig.getInstance();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		Composite parent = (Composite) super.createDialogArea(container);

		Composite composite = new Composite(parent, SWT.NONE);
		composite.getShell().setText("Diff patch creator");
		composite.setLayout(new GridLayout(3, false));
		GridData gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		composite.setLayoutData(gridData);

		Label srcLabel = new Label(composite, SWT.LEFT);
		srcLabel.setText("Source DB");
		srcText = new Text(composite, SWT.BORDER);
		gridData = new GridData(SWT.FILL, SWT.FILL, true, false);
		gridData.grabExcessHorizontalSpace = true;
		gridData.minimumWidth = 400;
		srcText.setLayoutData(gridData);
		srcText.setText(config.get("source", ""));
		Button srcButton = new Button(composite, SWT.RIGHT);
		srcButton.setText("Browse...");
		srcButton.addSelectionListener(new OpenFileDialog(srcText, SWT.OPEN));

		Label targetLabel = new Label(composite, SWT.LEFT);
		targetLabel.setText("Target DB");
		targetText = new Text(composite, SWT.BORDER);
		targetText.setLayoutData(gridData);
		targetText.setText(config.get("target", ""));
		Button targetButton = new Button(composite, SWT.RIGHT);
		targetButton.setText("Browse...");
		targetButton.addSelectionListener(new OpenFileDialog(targetText,
				SWT.OPEN));

		Label patchLabel = new Label(composite, SWT.LEFT);
		patchLabel.setText("Save patch");
		patchText = new Text(composite, SWT.BORDER);
		patchText.setLayoutData(gridData);
		patchText.setText(config.get("patch", ""));
		Button patchButton = new Button(composite, SWT.RIGHT);
		patchButton.setText("Browse...");
		patchButton
				.addSelectionListener(new OpenFileDialog(patchText, SWT.SAVE));

		composite = new Composite(parent, SWT.NONE);
		GridLayout layout = new GridLayout(1, false);
		gridData = new GridData(SWT.RIGHT, SWT.FILL, true, true);
		composite.setLayout(layout);
		composite.setLayoutData(gridData);
		createButtonBar(composite);

		setShellStyle(getShellStyle() | SWT.RESIZE);

		// initListeners();
		return parent;
	}

	protected void okPressed() {
		sourceFileName = srcText.getText();
		targetFileName = targetText.getText();
		patchFileName = patchText.getText();
		LauncherConfig config = null;
		try {
			config = LauncherConfig.getInstance();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		config.set("source", srcText.getText());
		config.set("target", targetText.getText());
		config.set("patch", patchText.getText());
		
		File sourceFile = new File(this.getSourceFileName());
		File targetFile = new File(this.getTargetFileName());
		File patchFile = new File(this.getPatchFileName());
//		Display.getCurrent().dispose();
		int result = 1;
		try {
			result = new PatcherMakerLauncher(sourceFile, targetFile, patchFile)
					.execute();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		MessageBox msg;
		String title = "Patch maker";
		if(result == 0) {
			msg = new MessageBox(this.getShell(),SWT.ICON_INFORMATION); 
			msg.setMessage("Successful");
			msg.setText(title);
		} else {
			msg = new MessageBox(this.getShell(),SWT.ICON_ERROR); 
			msg.setMessage("Patch wasn't create successful");
			msg.setText(title);
		}
		msg.open();
	}

	class OpenFileDialog implements SelectionListener {
		private Text textField;
		private int dialogMode;

		public OpenFileDialog(Text textField, int dialogMode) {
			this.textField = textField;
			this.dialogMode = dialogMode;
		}

		public void widgetSelected(SelectionEvent event) {
			FileDialog fd = new FileDialog(getShell(), dialogMode);
			fd.setText("Open");
			fd.setFilterPath("C:/");
			String[] filterExt = { "*.*", "*.NDS" };
			fd.setFilterExtensions(filterExt);
			String selected = fd.open();
			if (selected != null) {
				this.textField.setText(selected);
			}
		}

		public void widgetDefaultSelected(SelectionEvent event) {
			// this.widgetSelected(event);
		}
	}

	public String getSourceFileName() {
		return sourceFileName;
	}

	public String getTargetFileName() {
		return targetFileName;
	}

	public String getPatchFileName() {
		return patchFileName;
	}
}
