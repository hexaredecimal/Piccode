package org.editor.events;

/**
 *
 * @author hexaredecimal
 */
import java.awt.event.*;
import javax.swing.*;


// Reference: https://stackoverflow.com/questions/4344682/double-click-event-on-jlist-element
public class ListAction implements MouseListener {

	private static final KeyStroke ENTER = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);

	private JList list;
	private KeyStroke keyStroke;

	/**
	 * Binds the specified action to the given JList, triggered by the Enter key or a double-click.
	 *
	 * @param list the JList to which the action will be bound
	 * @param action the Action to trigger on Enter key press or double-click
	 */
	public ListAction(JList list, Action action) {
		this(list, action, ENTER);
	}

	/**
	 * Binds the specified Action to the given JList, triggering it via the provided KeyStroke or a mouse double-click.
	 *
	 * @param list the JList to which the action will be bound
	 * @param action the Action to be triggered
	 * @param keyStroke the KeyStroke that will trigger the action when pressed
	 */
	public ListAction(JList list, Action action, KeyStroke keyStroke) {
		this.list = list;
		this.keyStroke = keyStroke;

		//  Add the KeyStroke to the InputMap
		InputMap im = list.getInputMap();
		im.put(keyStroke, keyStroke);

		//  Add the Action to the ActionMap
		setAction(action);

		//  Handle mouse double click
		list.addMouseListener(this);
	}

	/**
	 * Associates the specified Action with the stored KeyStroke in the JList's ActionMap.
	 *
	 * @param action the Action to be triggered by the configured KeyStroke or mouse double-click
	 */
	public void setAction(Action action) {
		list.getActionMap().put(keyStroke, action);
	}

	/**
	 * Invokes the bound action when the list is double-clicked.
	 *
	 * On a double-click event, retrieves the action associated with the configured key stroke from the list's ActionMap and triggers it as if performed on the list.
	 *
	 * @param e the mouse event
	 */
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			Action action = list.getActionMap().get(keyStroke);

			if (action != null) {
				ActionEvent event = new ActionEvent(
								list,
								ActionEvent.ACTION_PERFORMED,
								"");
				action.actionPerformed(event);
			}
		}
	}

	/****
	 * Invoked when the mouse enters the component; no action is performed.
	 *
	 * @param e the mouse event
	 */
	public void mouseEntered(MouseEvent e) {
	}

	/****
	 * Invoked when the mouse exits the JList component. This implementation performs no action.
	 */
	public void mouseExited(MouseEvent e) {
	}

	/****
	 * Invoked when a mouse button has been pressed on the list.
	 * <p>
	 * This implementation does nothing.
	 */
	public void mousePressed(MouseEvent e) {
	}

	/****
	 * Invoked when a mouse button is released on the list component.
	 * <p>
	 * This implementation does nothing.
	 */
	public void mouseReleased(MouseEvent e) {
	}
}
