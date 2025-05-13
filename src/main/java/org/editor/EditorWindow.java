package org.editor;

import org.editor.panels.DashboardPanel;
import com.formdev.flatlaf.FlatLightLaf;
import com.vlsolutions.swing.docking.DockKey;
import com.vlsolutions.swing.docking.DockView;
import com.vlsolutions.swing.docking.Dockable;
import com.vlsolutions.swing.docking.DockableState;
import com.vlsolutions.swing.docking.DockingConstants;
import com.vlsolutions.swing.docking.DockingDesktop;
import com.vlsolutions.swing.docking.DockingPreferences;
import com.vlsolutions.swing.docking.event.DockableStateWillChangeEvent;
import com.vlsolutions.swing.docking.event.DockableStateWillChangeListener;
import com.vlsolutions.swing.docking.ui.DockingUISettings;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.nio.file.Path;
import java.util.HashMap;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JRootPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JToolBar;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import org.editor.events.Actions;
import org.editor.icons.Icons;
import org.editor.menu.Menus;

import org.fife.rsta.ui.CollapsibleSectionPanel;
//import org.fife.rsta.ui.DocumentMap;
import org.fife.rsta.ui.search.FindDialog;
import org.fife.rsta.ui.search.ReplaceDialog;
import org.fife.rsta.ui.search.SearchEvent;
import org.fife.rsta.ui.search.SearchListener;
import org.fife.ui.rtextarea.SearchContext;
import org.fife.ui.rtextarea.SearchEngine;
import org.fife.ui.rtextarea.SearchResult;

/**
 *
 * @author hexaredecimal
 */
public final class EditorWindow extends JFrame implements SearchListener {

	private static JTabbedPane tabs = new JTabbedPane();
	private static HashMap<Integer, CodeEditor> tabEditors;
	public static EditorWindow win = null;
	public static JLabel current_file = new JLabel();
	public static JLabel line_info = new JLabel();
	public static JLabel line_perc = new JLabel();
	public static JLabel charset = new JLabel();
	public static JProgressBar seekBar = new JProgressBar();
	private DockablePanel dashboard;

	private CollapsibleSectionPanel csp;
	public static FindDialog findDialog;
	public static ReplaceDialog replaceDialog;
	private DockingDesktop desk = new DockingDesktop();
	private static CodeEditor selected = null;

	/**
	 * Returns the singleton instance of the EditorWindow, creating it if necessary.
	 *
	 * @return the single EditorWindow instance
	 */
	public static EditorWindow the() {
		if (win == null) {
			win = new EditorWindow();
		}
		return win;
	}

	public static JRootPane root = null;

	/**
	 * Constructs the main application window for the code editor, initializing the docking framework, UI components, toolbars, status bars, and dockable panels.
	 *
	 * <p>
	 * Sets up the main window layout with a docking desktop for managing code editors and panels, configures toolbars for file and project actions, and initializes status indicators. Integrates dashboard, rendering, quick access, and access panels as dockable components. Installs look-and-feel, customizes docking UI, and sets up listeners to handle editor closing events with prompts for unsaved changes.
	 * </p>
	 */
	public EditorWindow() {
		super("Piccode - DashBoard");
		var  _ =new CodeEditor();
		root = getRootPane();
		Icons.loadIcons();
		tabEditors = new HashMap<>();
		CodeEditor.createTemplateManager();
		initSearchDialogs();

		DockingUISettings.getInstance().installUI();
		customizeDock();

		try {
			UIManager.setLookAndFeel(new FlatLightLaf());
		} catch (Exception ex) {
			System.err.println("Failed to initialize LaF");
		}

		int width = 900;
		int height = 600;

		desk.addDockableStateWillChangeListener(event -> {
			var current = event.getCurrentState();

			if (current == null) {
				return;
			}
			
			if (current.getDockable() instanceof CodeEditor ed) {
				if (event.getFutureState().isClosed()) {
					if (removeIfDirty(ed.tabIndex, ed) == false) {
						event.cancel();
					}
				}
			}
		});

		JPanel main_panel = new JPanel(new BorderLayout());
		//main_panel.add(desk, BorderLayout.CENTER);

		JToolBar tool_bar = makeToolBar(
						Actions.newProjectAction,
						Actions.newFileAction,
						Actions.openFileAction,
						null,
						Actions.saveAction,
						null,
						Actions.undoAction,
						Actions.redoAction,
						Actions.compileAction,
						Actions.renderAction,
						null,
						Actions.exitAction
		);
		main_panel.add(tool_bar, BorderLayout.PAGE_START);

		current_file = new JLabel("[NONE]");
		line_info = new JLabel();
		line_perc = new JLabel();
		charset = new JLabel();
		seekBar = new JProgressBar();
		seekBar.setValue(50);

		JToolBar bottom_bar = makeLRToolBar(
						new Component[]{current_file, null},
						new Component[]{line_info, line_perc, seekBar, null, charset}
		);
		main_panel.add(bottom_bar, BorderLayout.PAGE_END);

		Action[] app_actions = {
			Actions.showFileTreeAction,
			Actions.searchAction,
			Actions.commitAction,
			Actions.exportAction,
			Actions.AIAction,
			Actions.communityAction,
			Actions.pluginsAction
		};

		var bar = makeCoolbar(height, app_actions);

		JMenuBar menu_bar = new JMenuBar();
		this.setJMenuBar(menu_bar);

		Actions.loadActions();
		Menus.addMenus(menu_bar);
		this.setIconImage(Icons.getIcon("appicon").getImage());

		// Canvas and Access Panels
		var canvas_panel = CanvasFrame.the();
		var access_panel = new DockablePanel(new BorderLayout(), "Run");
		access_panel.add(new AccessFrame(width));

		var render_panel = new DockablePanel(new BorderLayout(), "Render");
		render_panel.add(canvas_panel, BorderLayout.CENTER);
		Action[] render_actions = {
			Actions.normalAction,
			Actions.gridAction,
			Actions.pointAction,
			Actions.rulerAction,
			Actions.snapAction,
			Actions.brushAction,
			Actions.thickBrushAction,
			Actions.paintBucketAction,
			Actions.effectsAction,};

		var short_cuts = makeCoolbar(canvas_panel.getHeight(), render_actions);
		short_cuts.setBorder(BorderFactory.createEmptyBorder());
		render_panel.add(short_cuts, BorderLayout.EAST);
		render_panel.add(new JScrollPane(canvas_panel), BorderLayout.CENTER);

		var cool_bar = new DockablePanel(new BorderLayout(), "Quick Access");
		cool_bar.add(bar, BorderLayout.CENTER);

		DockingPreferences.setDottedDesktopStyle();
		getContentPane().add(desk, BorderLayout.CENTER);
		getContentPane().add(render_panel, BorderLayout.WEST);
		getContentPane().add(access_panel, BorderLayout.SOUTH);
		getContentPane().add(cool_bar, BorderLayout.EAST);
		getContentPane().add(tool_bar, BorderLayout.PAGE_START);
		getContentPane().add(bottom_bar, BorderLayout.PAGE_END);

		dashboard = new DockablePanel(new BorderLayout(), "Piccasso DashBoard", "DashBoard", "Home page", "file");
		dashboard.add(new JScrollPane(new DashboardPanel()), BorderLayout.CENTER);
		getContentPane().add(access_panel, BorderLayout.EAST);
		
		desk.addDockable(dashboard);
		desk.addDockable(cool_bar);
		desk.setAutoHide(cool_bar, true);

		desk.split(dashboard, render_panel, DockingConstants.SPLIT_RIGHT, 0.7);
		desk.split(render_panel, access_panel, DockingConstants.SPLIT_BOTTOM);

		win = this;

		this.setSize(width, height);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	/**
	 * Initializes the find and replace dialogs for text search operations, ensuring they share a common search context.
	 */
	private void initSearchDialogs() {
		findDialog = new FindDialog(this, this);
		replaceDialog = new ReplaceDialog(this, this);

		SearchContext context = findDialog.getSearchContext();
		replaceDialog.setSearchContext(context);
	}

	/**
	 * Adds a new empty code editor as a dockable tab in the main window.
	 *
	 * Creates a new {@code CodeEditor} instance, assigns it a unique index, updates the UI to reflect the new editor, and docks it within the docking desktop. The first editor is docked relative to the dashboard; subsequent editors are added as tabs alongside the first editor.
	 */
	public static void addTab(ActionEvent e) {
		int index = tabEditors.size();
		CodeEditor editor = new CodeEditor();
		editor.tabIndex = index;
		Path file = editor.file;
		editor.requestFocusInWindow();
		current_file.setText(file != null ? file.toString() : "[NONE]");
		tabEditors.put(index, editor);

		// Add first editor normally
		if (index == 0) {
			win.desk.addDockable(editor);
			win.desk.createTab(win.dashboard, editor, 2);
		} else {
			// Add to same container as first editor
			CodeEditor firstEditor = tabEditors.get(0);
			win.desk.createTab(firstEditor, editor, 2);
		}
	}

	/**
	 * Opens a file in a new code editor dockable and adds it to the docking desktop.
	 *
	 * @param path the path to the file to open in the new editor
	 */
	public static void addTab(Path path, Void e) {
		var index = tabEditors.size();
		var editor = new CodeEditor(path);
		editor.tabIndex = index;
		editor.load(path.toFile());
		editor.requestFocusInWindow();
		tabEditors.put(index, editor);

		// Add first editor normally
		if (index == 0) {
			win.desk.createTab(win.dashboard, editor, 2);
		} else {
			// Add to same container as first editor
			CodeEditor firstEditor = tabEditors.get(0);
			win.desk.createTab(firstEditor, editor, 1);
		}
	}

	/**
	 * Sets the currently selected code editor in the main window.
	 *
	 * @param ed the CodeEditor instance to set as selected
	 */
	public static void setSelectedEditor(CodeEditor ed) {
		selected = ed;
	}

	/**
	 * Returns the currently selected code editor.
	 *
	 * If no editor is explicitly selected, returns the editor whose text area has focus.
	 * If none are focused, returns the first available editor as a fallback.
	 *
	 * @return the selected or focused {@link CodeEditor}, or the first editor if none are selected or focused
	 */
	public static CodeEditor getSelectedEditor() {
		if (selected != null) {
			return selected;
		}

		for (var editor : tabEditors.values()) {
			if (editor.textArea.isFocusOwner()) {
				return editor;
			}
		}
		return tabEditors.values().toArray(CodeEditor[]::new)[0]; // fallback if nothing has focus
	}

	/**
	 * Adds a special "+" tab to the provided tabbed pane, allowing users to create new editor tabs.
	 *
	 * The "+" tab displays a button that, when clicked, triggers the addition of a new editor tab.
	 *
	 * @param tabs the JTabbedPane to which the "+" tab will be added
	 */
	private static void addPlusTab(JTabbedPane tabs) {
		var dashb = new JPanel(new BorderLayout());
		dashb.add(new DashboardPanel(), BorderLayout.CENTER);
		tabs.addTab("", new JScrollPane(dashb));

		JButton plusBtn = new JButton();
		plusBtn.setIcon(Icons.getIcon("add"));
		plusBtn.setMargin(new Insets(0, 8, 0, 8));
		plusBtn.setBorder(BorderFactory.createEmptyBorder());
		plusBtn.setContentAreaFilled(false);
		plusBtn.setFocusPainted(false);
		plusBtn.addActionListener(EditorWindow::addTab);
		tabs.setTabComponentAt(tabs.getTabCount() - 1, plusBtn);
	}

	/**
	 * Creates a custom tab header component with a title label and a close button for use in a tabbed pane.
	 *
	 * The close button triggers removal of the associated tab, prompting to save if the editor is modified.
	 *
	 * @param tabs the tabbed pane to which the header belongs
	 * @param title the title to display on the tab header
	 * @return a component representing the tab header with a close button
	 */
	private static Component makeTabHeader(JTabbedPane tabs, String title) {
		JPanel tabHeader = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
		tabHeader.setOpaque(false);

		JLabel titleLabel = new JLabel(title + " ");
		JButton closeButton = new JButton(Icons.getIcon("close"));

		closeButton.setMargin(new Insets(0, 5, 0, 5));
		closeButton.setFocusable(false);
		closeButton.setBorderPainted(false);
		closeButton.setContentAreaFilled(false);
		closeButton.setOpaque(false);
		closeButton.setForeground(Color.RED);
		closeButton.setFont(closeButton.getFont().deriveFont(Font.BOLD, 12));

		closeButton.addActionListener(e -> {
			int index = tabs.indexOfTabComponent(tabHeader);
			if (index != -1) {
				var ed = tabEditors.get(index);
				removeIfDirty(index, ed);
			}
		});

		tabHeader.add(titleLabel);
		tabHeader.add(closeButton);

		return tabHeader;
	}

	/**
	 * Removes the currently selected code editor tab, prompting to save changes if the editor is modified.
	 *
	 * If only one editor is open or no editor is selected, the method does nothing.
	 */
	public static void removeTab() {
		if (tabEditors.size() <= 1) {
			return;
		}

		CodeEditor selected = getSelectedEditor();
		if (selected == null) {
			return;
		}

		Integer index = getEditorIndex(selected);
		if (index == null) {
			return;
		}

		removeIfDirty(index, selected);
	}

	/**
	 * Removes all open code editor tabs, prompting to save any with unsaved changes before closing.
	 */
	public static void removeAllTabs() {
		var editors = new HashMap<>(tabEditors); // Copy to avoid ConcurrentModificationException
		for (var entry : editors.entrySet()) {
			removeIfDirty(entry.getKey(), entry.getValue());
		}
	}

	/**
	 * Returns the number of open code editor instances currently managed by the window.
	 *
	 * @return the count of open code editors
	 */
	public static int tabsCount() {
		return tabEditors.size();
	}

	/**
	 * Removes the specified code editor from the docking layout, prompting to save changes if the editor is modified.
	 *
	 * @param index the index of the editor in the internal map
	 * @param ed the code editor to remove
	 * @return true if the editor was removed (and saved if modified), false if the removal was canceled
	 */
	private static boolean removeIfDirty(Integer index, CodeEditor ed) {
		if (ed.textArea.isDirty()) {
			int result = JOptionPane.showConfirmDialog(win, "File " + ed.filePathTruncated() + " is modified. Save?");
			if (result == JOptionPane.OK_OPTION) {
				return ed.saveFile();
			}
		}
		win.desk.remove((Dockable) ed); // Actual removal from docking layout
		tabEditors.remove(index);
		migrateIndexes();
		
		return true;
	}

	/**
	 * Determines whether the specified dockable component is currently docked in the docking desktop.
	 *
	 * @param d the dockable component to check
	 * @return true if the dockable is present in the docking desktop; false otherwise
	 */
	private static boolean isDocked(Dockable d) {
		for (var state: win.desk.getDockables()) {
			var dockable = state.getDockable();
			if (dockable == d || dockable.equals(d)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns the index associated with the specified code editor in the tab editors map.
	 *
	 * @param ed the code editor to look up
	 * @return the index of the editor if found, or null if not present
	 */
	private static Integer getEditorIndex(CodeEditor ed) {
		for (var entry : tabEditors.entrySet()) {
			if (entry.getValue() == ed) {
				return entry.getKey();
			}
		}
		return null;
	}

	/**
	 * Reindexes the tabEditors map to ensure editor indexes are sequential starting from zero.
	 *
	 * This method rebuilds the tabEditors map after editor removals to maintain contiguous integer keys.
	 */
	private static void migrateIndexes() {
		HashMap<Integer, CodeEditor> newMap = new HashMap<>();
		int idx = 0;
		for (CodeEditor ed : tabEditors.values()) {
			newMap.put(idx++, ed);
		}
		tabEditors = newMap;
	}

	/**
	 * Saves all open code editors by invoking their save operation.
	 */
	public static void saveAll() {
		for (var kv : tabEditors.entrySet()) {
			var editor = kv.getValue();
			editor.saveFile();
		}
	}

	/**
	 * Sets the title of the currently selected tab in the tabbed pane.
	 *
	 * @param title the new title for the selected tab
	 */
	public static void setSeletedTabTitle(String title) {
		int index = tabs.getSelectedIndex();
		tabs.setTitleAt(index, title);
	}

	/**
	 * Creates a vertical toolbar panel with action buttons and a settings button.
	 *
	 * @param height the preferred height of the toolbar panel
	 * @param actions the actions to be added as buttons at the top of the toolbar
	 * @return a JPanel containing the vertical toolbar with action buttons and a settings button at the bottom
	 */
	private JPanel makeCoolbar(int height, Action... actions) {
		var cool_bar = new JPanel(new BorderLayout());
		cool_bar.setPreferredSize(new Dimension(50, height));
		JPanel top_buttons = new JPanel(new GridLayout(10, 1));
		top_buttons.setOpaque(false); // transparent to inherit background

		for (var action : actions) {
			JButton btn = new JButton(action);
			btn.setText("");
			top_buttons.add(btn);
		}

		JPanel bottom_button = new JPanel(new BorderLayout());
		bottom_button.setOpaque(false);

		JButton settingsBtn = new JButton("", Icons.getIcon("settings"));
		settingsBtn.setToolTipText("Settings");

		bottom_button.add(settingsBtn, BorderLayout.SOUTH);
		cool_bar.add(top_buttons, BorderLayout.NORTH);
		cool_bar.add(bottom_button, BorderLayout.SOUTH);

		return cool_bar;
	}

	private JToolBar makeToolBar(Action... actions) {
		JToolBar cool_bar = new JToolBar();
		for (var action : actions) {
			if (action == null) {
				var sep = new JSeparator(JSeparator.VERTICAL);
				cool_bar.add(sep);
				continue;
			}
			JButton btn = new JButton(action);
			btn.setText("");
			cool_bar.add(btn);
		}
		return cool_bar;
	}

	private JToolBar makeLRToolBar(Component[] llbls, Component[] rlbls) {
		JToolBar toolBar = new JToolBar();
		toolBar.setFloatable(false);
		toolBar.setLayout(new BorderLayout());

		// LEFT panel
		JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
		for (var lbl : llbls) {
			if (lbl == null) {
				leftPanel.add(new JSeparator(SwingConstants.VERTICAL));
			} else {
				leftPanel.add(lbl);
			}
		}

		// RIGHT panel
		JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
		for (var lbl : rlbls) {
			if (lbl == null) {
				rightPanel.add(new JSeparator(SwingConstants.VERTICAL));
			} else {
				rightPanel.add(lbl);
			}
		}

		// Add them to the toolbar using BorderLayout
		toolBar.add(leftPanel, BorderLayout.WEST);
		toolBar.add(rightPanel, BorderLayout.EAST);

		return toolBar;
	}

	@Override
	public void searchEvent(SearchEvent se) {
		SearchEvent.Type type = se.getType();
		SearchContext context = se.getSearchContext();
		SearchResult result;
		var textArea = getSelectedEditor().textArea;

		switch (type) {
			default: // Prevent FindBugs warning later
			case MARK_ALL:
				result = SearchEngine.markAll(textArea, context);
				break;
			case FIND:
				result = SearchEngine.find(textArea, context);
				if (!result.wasFound() || result.isWrapped()) {
					UIManager.getLookAndFeel().provideErrorFeedback(textArea);
				}
				break;
			case REPLACE:
				result = SearchEngine.replace(textArea, context);
				if (!result.wasFound() || result.isWrapped()) {
					UIManager.getLookAndFeel().provideErrorFeedback(textArea);
				}
				break;
			case REPLACE_ALL:
				result = SearchEngine.replaceAll(textArea, context);
				JOptionPane.showMessageDialog(null, result.getCount()
								+ " occurrences replaced.");
				break;
		}
	}

	/**
	 * Returns the currently selected text from the active code editor.
	 *
	 * @return the selected text, or {@code null} if no text is selected
	 */
	@Override
	public String getSelectedText() {
		return getSelectedEditor().textArea.getSelectedText();
	}

	/**
	 * Customizes the appearance and behavior of docking UI components.
	 *
	 * Sets custom icons for close buttons, enables the float button on dock view title bars, and adjusts the title font size for docked panels.
	 */
	private void customizeDock() {
		UIManager.put("DockViewTitleBar.close", (Icon) Icons.getIcon("close"));
		UIManager.put("DockTabbedPane.close", (Icon) Icons.getIcon("close"));
		UIManager.put("DockViewTitleBar.isFloatButtonDisplayed", true);

		var font = (Font)UIManager.get("DockViewTitleBar.titleFont");
		UIManager.put("DockViewTitleBar.titleFont", new Font(font.getName(), font.getStyle(), 11));
	}

}
