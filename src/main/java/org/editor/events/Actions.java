package org.editor.events;

import javax.swing.Action;
import org.editor.CanvasFrame;
import org.editor.EditorWindow;
import org.editor.util.It;
import org.fife.ui.rtextarea.RTextArea;

/**
 *
 * @author hexaredecimal
 */
public class Actions {

	public static Action compileAction,
					renderAction,
					commitAction,
					exportAction,
					newProjectAction,
					newFileAction,
					openProjectAction,
					closeProjectAction,
					openFileAction,
					closeFileAction,
					saveAction,
					saveAsAction,
					saveAllAction,
					exitAction,
					undoAction,
					redoAction,
					cutAction,
					copyAction,
					pasteAction,
					deleteAction,
					selectAllAction,
					gotoTabAction,
					addTabAction,
					removeTabAction,
					removeAllTabsAction,
					gotoFileAction,
					runOptionsAction,
					AIAction,
					pluginsAction,
					optionsAction,
					docsAction,
					websiteAction,
					licenseAction,
					aboutAction,
					showFileTreeAction,
					searchAction,
					communityAction,
					normalAction,
					gridAction,
					pointAction,
					rulerAction,
					snapAction,
					brushAction,
					thickBrushAction,
					bucketToolAction,
					paintBucketAction,
					effectsAction,
					navBottom,
					navTop,
					navLeft,
					navRight,
					findAction,
					gotoLine,
					replaceAction;

	/**
	 * Initializes and assigns all static action fields for the editor's user interface.
	 *
	 * This method configures each action with its name, icon, tooltip, keyboard shortcut, and event handler,
	 * enabling features such as file operations, editing commands, navigation, tool selection, and UI toggles.
	 * Actions are linked to their respective handlers or marked as TODO where implementation is pending.
	 */
	public static void loadActions() {
		compileAction
						= AppAction
										.create("Compile")
										.icon("run")
										.tooltip("Compile the current script")
										.shortcut("F5")
										.handler(AccessEvents::compile)
										.build();

		renderAction
						= AppAction
										.create("Render")
										.icon("panorama")
										.tooltip("Render current script with timer")
										.shortcut("control shift F5")
										.handler(AccessEvents::compileAndRender)
										.build();

		commitAction
						= AppAction
										.create("Commit")
										.icon("compare-git")
										.tooltip("Commit changes")
										.shortcut("control shift G")
										.handler(e -> It.todo())
										.build();

		exportAction
						= AppAction
										.create("Export")
										.icon("export")
										.tooltip("Export output to file")
										.shortcut("F6")
										.handler(e -> It.todo())
										.build();

		newProjectAction
						= AppAction
										.create("New Project")
										.icon("project-setup")
										.tooltip("Initialize a new graphic project")
										.shortcut("control shift N")
										.handler(e -> It.todo())
										.build();

		newFileAction
						= AppAction
										.create("New File")
										.icon("add-file")
										.tooltip("Create a new file")
										.shortcut("control N")
										.handler(e -> It.todo())
										.build();

		openProjectAction
						= AppAction
										.create("Open Project")
										.icon("code-folder")
										.tooltip("Open a project from the filesystem")
										.shortcut("control shift O")
										.handler(e -> It.todo())
										.build();

		closeProjectAction
						= AppAction
										.create("Close Project")
										.icon("xxx-folder")
										.tooltip("Close the currently selected project")
										.handler(e -> It.todo())
										.build();

		openFileAction
						= AppAction
										.create("Open File")
										.icon("file")
										.tooltip("Open a file from the filesystem")
										.shortcut("control O")
										.handler(MenuEvents::openFile)
										.build();

		closeFileAction
						= AppAction
										.create("Close File")
										.icon("close")
										.tooltip("Close the current file")
										.handler(MenuEvents::closeFile)
										.build();

		saveAction
						= AppAction
										.create("Save")
										.icon("save")
										.tooltip("Save")
										.shortcut("control S")
										.handler(MenuEvents::saveFile)
										.build();

		saveAsAction
						= AppAction
										.create("Save As...")
										.icon("save-as")
										.tooltip("Save")
										.handler(MenuEvents::saveFileAs)
										.build();

		saveAllAction
						= AppAction
										.create("Save All")
										.icon("save-all")
										.tooltip("Save All")
										.shortcut("control shift S")
										.handler(MenuEvents::saveAllFiles)
										.build();

		exitAction
						= AppAction
										.create("Exit")
										.icon("logout")
										.shortcut("control Q")
										.tooltip("Quit")
										.handler(MenuEvents::quit)
										.build();

		undoAction = AppAction
						.from(RTextArea.getAction(RTextArea.UNDO_ACTION), "undo");
		redoAction = AppAction
						.from(RTextArea.getAction(RTextArea.REDO_ACTION), "redo");
		cutAction = AppAction
						.from(RTextArea.getAction(RTextArea.CUT_ACTION), "cut");
		copyAction = AppAction
						.from(RTextArea.getAction(RTextArea.COPY_ACTION), "copy-to-clipboard");
		pasteAction = AppAction
						.from(RTextArea.getAction(RTextArea.PASTE_ACTION), "paste");
		deleteAction = AppAction
						.from(RTextArea.getAction(RTextArea.DELETE_ACTION), "clear-symbol");
		selectAllAction = AppAction
						.from(RTextArea.getAction(RTextArea.SELECT_ALL_ACTION), "select-all");

		findAction
						= AppAction
										.create("Find")
										.icon("find")
										.shortcut("control F")
										.tooltip("Search for a specific term")
										.handler(MenuEvents::findEvent)
										.build();

		replaceAction
						= AppAction
										.create("Replace")
										.icon("find-and-replace")
										.shortcut("control H")
										.tooltip("Replace occurances of a sstring")
										.handler(MenuEvents::replaceEvent)
										.build();

		gotoLine
						= AppAction
										.create("Goto Line")
										.icon("line")
										.shortcut("control L")
										.tooltip("Jump to a specific line")
										.handler(MenuEvents::gotoLineEvent)
										.build();

		gotoTabAction
						= AppAction
										.create("Goto Tab")
										.icon("shortcut")
										.shortcut("control shift J")
										.tooltip("Jump to specific tab")
										.handler(e -> It.todo())
										.build();

		addTabAction = AppAction
						.create("Add Tab")
						.icon("add")
						.shortcut("control shift T")
						.tooltip("Add a new tab")
						.handler(EditorWindow::addTab)
						.build();

		removeTabAction = AppAction
						.create("Remove Tab")
						.icon("close")
						.shortcut("control R")
						.tooltip("Remove current tab")
						.handler(MenuEvents::closeTab)
						.build();

		removeAllTabsAction = AppAction
						.create("Remove All Tabs")
						.icon("delete-document")
						.shortcut("control shift R")
						.tooltip("Remove all tabs")
						.handler(MenuEvents::closeAllTabs)
						.build();

		gotoFileAction = AppAction
						.create("Goto file")
						.icon("hot-article")
						.shortcut("control shift P")
						.tooltip("Goto file")
						.handler(e -> It.todo())
						.build();

		runOptionsAction = AppAction
						.create("Run Options")
						.icon("automatic")
						.tooltip("Configure the runtime")
						.handler(e -> It.todo())
						.build();

		AIAction = AppAction
						.create("AI")
						.icon("chatbot")
						.shortcut("control shift L")
						.tooltip("Chat with an LLM")
						.handler(e -> It.todo())
						.build();

		pluginsAction = AppAction
						.create("Plugins")
						.icon("plugin")
						.shortcut("control R")
						.tooltip("Browse/Install community plugins")
						.handler(e -> It.todo())
						.build();

		optionsAction = AppAction
						.create("Options")
						.icon("options")
						.tooltip("Configure the system")
						.handler(e -> It.todo())
						.build();

		docsAction = AppAction
						.create("Documentation")
						.icon("book")
						.shortcut("F1")
						.tooltip("Read the documentation")
						.handler(e -> It.todo())
						.build();

		websiteAction = AppAction
						.create("Website")
						.icon("open-in-browser")
						.shortcut("F2")
						.tooltip("Open the product site")
						.handler(e -> It.todo())
						.build();

		licenseAction = AppAction
						.create("License")
						.icon("license")
						.shortcut("F4")
						.tooltip("License")
						.handler(e -> It.todo())
						.build();

		aboutAction = AppAction
						.create("About")
						.icon("about")
						.tooltip("About the product")
						.handler(MenuEvents::aboutDialog)
						.build();

		showFileTreeAction = AppAction
						.create("Show File Tree")
						.icon("folder-tree")
						.tooltip("Toggle showing the file tree")
						.handler(e -> It.todo())
						.build();

		searchAction = AppAction
						.create("Search...")
						.icon("search")
						.tooltip("Seach for a file or text...")
						.handler(e -> It.todo())
						.build();

		communityAction = AppAction
						.create("Community")
						.icon("store")
						.tooltip("Find community packages")
						.handler(e -> It.todo())
						.build();

		normalAction = AppAction
						.create("Normal")
						.icon("pointer")
						.tooltip("Activate normal mode")
						.shortcut("control I")
						.handler(e -> It.todo())
						.build();

		gridAction = AppAction
						.create("Grid")
						.icon("grid")
						.tooltip("Toogle grid")
						.shortcut("control G")
						.handler(e -> CanvasFrame.the().showGrid = !CanvasFrame.the().showGrid)
						.build();

		pointAction = AppAction
						.create("Point")
						.icon("point")
						.tooltip("Toogle pointer")
						.shortcut("control P")
						.handler(e -> CanvasFrame.the().showHighlight = !CanvasFrame.the().showHighlight)
						.build();

		rulerAction = AppAction
						.create("Ruler")
						.icon("ruler")
						.shortcut("control M")
						.tooltip("Toggle measurements")
						.handler(e -> CanvasFrame.the().showRuler = !CanvasFrame.the().showRuler)
						.build();

		snapAction = AppAction
						.create("Snap")
						.icon("add-row")
						.shortcut("control N")
						.tooltip("Toogle snap to grid")
						.handler(e -> CanvasFrame.the().snapToGrid = !CanvasFrame.the().snapToGrid)
						.build();

		brushAction = AppAction
						.create("Brush")
						.icon("brush")
						.shortcut("control B")
						.tooltip("Select paint brush")
						.handler(e -> It.todo())
						.build();

		thickBrushAction = AppAction
						.create("ThickBrush")
						.icon("brush-fat")
						.shortcut("control shift H")
						.tooltip("Adjust paint brush")
						.handler(e -> It.todo())
						.build();

		paintBucketAction = AppAction
						.create("PaintBucket")
						.icon("paint-bucket")
						.shortcut("control shift F")
						.tooltip("Bucket tool")
						.handler(e -> It.todo())
						.build();

		effectsAction = AppAction
						.create("Effects")
						.icon("visual-effects")
						.shortcut("control E")
						.tooltip("Apply visual effects")
						.handler(e -> It.todo())
						.build();

		navBottom = AppAction
						.create("Botton")
						.icon("navigation-toolbar-bottom")
						.tooltip("Toogle the bottom view")
						.handler(e -> It.todo())
						.build();

		navTop = AppAction
						.create("Top")
						.icon("navigation-toolbar-top")
						.tooltip("Toogle the top view")
						.handler(e -> It.todo())
						.build();

		navLeft = AppAction
						.create("Left")
						.icon("navigation-toolbar-left")
						.tooltip("Toogle the left view")
						.handler(e -> It.todo())
						.build();

		navRight = AppAction
						.create("Right")
						.icon("right-navigation-toolbar")
						.tooltip("Toogle the right view")
						.handler(e -> It.todo())
						.build();

	}
}
