package sovun.test.diffpatchercreator;

import java.io.IOException;

import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

/*******************************************************************************
 * Opens GUI to get source / target files and the patch file name, then launches
 * patch maker.
 * 
 * @author AVasilkov
 * @since 4.3
 ******************************************************************************/
public class PatcherLauncher {
	public static void main(String[] args) throws IOException {
		Display display = new Display();
		Shell shell = new Shell(display);
		Monitor primary = display.getPrimaryMonitor();
		Rectangle bounds = primary.getBounds();
		PatchCreatorDialog dialog = new PatchCreatorDialog(null);
		dialog.setBlockOnOpen(true);
		Rectangle rect = shell.getBounds();
		int x = bounds.x + (bounds.width - rect.width) / 2;
		int y = bounds.y + (bounds.height - rect.height) / 2;
		shell.setLocation(x, y);
		if (dialog.open() == 0) {

		}

	}
}
