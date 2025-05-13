package org.editor.events;

import java.awt.event.ActionEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.text.BadLocationException;
import org.editor.CodeEditor;
import org.editor.EditorWindow;
import org.editor.dialogs.AboutDialog;
import org.editor.fs.FileFilter;
import org.fife.rsta.ui.GoToDialog;
import org.fife.ui.rsyntaxtextarea.FileLocation;

/**
 *
 * @author hexaredecimal
 */
public class MenuEvents {

	public static void gotoLineEvent(ActionEvent e) {
		var findDialog = EditorWindow.findDialog;
		var replaceDialog = EditorWindow.replaceDialog;
		if (findDialog.isVisible()) {
			findDialog.setVisible(false);
		}
		if (replaceDialog.isVisible()) {
			replaceDialog.setVisible(false);
		}
		GoToDialog dialog = new GoToDialog(EditorWindow.win);
		var textArea = EditorWindow.getSelectedEditor().textArea;
		dialog.setMaxLineNumberAllowed(textArea.getLineCount());
		dialog.setVisible(true);
		int line = dialog.getLineNumber();
		if (line > 0) {
			try {
				textArea.setCaretPosition(textArea.getLineStartOffset(line - 1));
			} catch (BadLocationException ble) { // Never happens
				UIManager.getLookAndFeel().provideErrorFeedback(textArea);
				ble.printStackTrace();
			}
		}
	}

	static void replaceEvent(ActionEvent e) {
		var replaceDialog = EditorWindow.replaceDialog;
		var findDialog = EditorWindow.findDialog;
		if (findDialog.isVisible()) {
			findDialog.setVisible(false);
		}
		replaceDialog.setVisible(true);
	}

	/**
	 * Displays the find dialog and hides the replace dialog if it is currently visible.
	 *
	 * @param e the action event triggering the find operation
	 */
	static void findEvent(ActionEvent e) {
		var replaceDialog = EditorWindow.replaceDialog;
		var findDialog = EditorWindow.findDialog;
		if (replaceDialog.isVisible()) {
			replaceDialog.setVisible(false);
		}
		findDialog.setVisible(true);
	}

	/**
	 * Displays the About dialog for the editor application.
	 */
	public static void aboutDialog(ActionEvent e) {
		var  _ = new AboutDialog(EditorWindow.win);
	}

	/**
	 * Closes the currently active editor tab.
	 */
	public static void closeTab(ActionEvent e) {
		EditorWindow.removeTab();
	}

	/**
	 * Closes all open editor tabs in the application.
	 */
	public static void closeAllTabs(ActionEvent e) {
		EditorWindow.removeAllTabs();
	}

	/**
	 * Opens a file chooser dialog for the user to select a file and adds a new editor tab for the chosen file.
	 *
	 * The dialog starts in the current directory and applies filters for markdown and picture files. If the user selects a file and approves, a new tab is created in the editor for that file.
	 */
	static void openFile(ActionEvent e) {
		// TODO: Use the System object to get the current pwd
		var fileChooser = new JFileChooser(".");
		fileChooser.setFileFilter(FileFilter.mdFilter);
		fileChooser.setFileFilter(FileFilter.picsFilter);

		int status = fileChooser.showOpenDialog(EditorWindow.win);
		if (status != JFileChooser.APPROVE_OPTION) {
			return;
		}

		var fp = fileChooser.getSelectedFile();
		var path = fp.toPath();
		EditorWindow.addTab(path, null);
	}

	/**
	 * Saves the currently selected file if more than one tab is open.
	 *
	 * Does nothing if only one tab is present.
	 */
	static void saveFile(ActionEvent e) {
		if (EditorWindow.tabsCount() == 1) {
			return;
		}
		var ed = EditorWindow.getSelectedEditor();
		ed.saveFile();
	}

	/**
	 * Saves the currently selected file under a new name using a "Save As" dialog, if more than one tab is open.
	 */
	static void saveFileAs(ActionEvent e) {
		if (EditorWindow.tabsCount() == 1) {
			return;
		}
		var ed = EditorWindow.getSelectedEditor();
		ed.saveFileAs();
	}

	/**
	 * Saves all open files in the editor.
	 *
	 * Calls the save operation for every open tab, ensuring all changes are written to disk.
	 */
	static void saveAllFiles(ActionEvent e) {
		EditorWindow.saveAll();
	}

	/**
	 * Closes the currently active editor tab.
	 *
	 * This method is an alias for {@link #closeTab(ActionEvent)}.
	 */
	static void closeFile(ActionEvent e) {
		closeTab(e);
	}

	static void quit(ActionEvent e) {
		closeAllTabs(e);
		System.exit(0);
	}
}
