package org.editor;

import com.vlsolutions.swing.docking.DockKey;
import com.vlsolutions.swing.docking.Dockable;
import java.awt.Component;
import java.awt.LayoutManager;
import javax.swing.JPanel;
import org.editor.icons.Icons;

/**
 *
 * @author hexaredecimal
 */
public class DockablePanel extends JPanel implements Dockable {
	private DockKey key;

	/**
		 * Constructs a DockablePanel with the specified layout and a unique DockKey ID.
		 *
		 * @param layout the layout manager to use for this panel
		 */
	public DockablePanel(LayoutManager layout) {
		super(layout);
		key = new DockKey("dock-" + System.nanoTime());
	}

	/**
	 * Constructs a DockablePanel with the specified layout manager and a DockKey identified by the given ID.
	 *
	 * @param layout the layout manager to use for this panel
	 * @param id the unique identifier for the DockKey associated with this panel
	 */
	public DockablePanel(LayoutManager layout, String id) {
		super(layout);
		key = new DockKey(id);
	}
	
	/**
	 * Constructs a DockablePanel with the specified layout, identifier, name, tooltip, and icon.
	 *
	 * @param layout the layout manager to use for this panel
	 * @param id the unique identifier for the dock key
	 * @param name the display name for the dock key
	 * @param tip the tooltip text for the dock key
	 * @param icon the icon identifier used to retrieve the panel's icon
	 */
	public DockablePanel(LayoutManager layout, String id, String name, String tip, String icon) {
		super(layout);
		key = new DockKey(id, name, tip, Icons.getIcon(icon));
	}
	
	/**
	 * Returns the {@link DockKey} associated with this dockable panel.
	 *
	 * @return the DockKey identifying and describing this panel for docking operations
	 */
	@Override
	public DockKey getDockKey() {
		return key;
	}

	/**
	 * Returns this panel as the dockable component.
	 *
	 * @return this panel instance
	 */
	@Override
	public Component getComponent() {
		return this;
	}
	
}
