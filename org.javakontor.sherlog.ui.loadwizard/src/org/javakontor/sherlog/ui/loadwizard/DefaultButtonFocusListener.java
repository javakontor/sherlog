package org.javakontor.sherlog.ui.loadwizard;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JButton;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

/**
 * A FocusListener that can be added to JButtons in order to make them the default button when they receive the focus to
 * allow users selecting the button by pressing "enter". When the button loses focus, the previous default button (from
 * the JRootPane) gets restored.
 * 
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class DefaultButtonFocusListener implements FocusListener {

  private JButton _defaultButton = null;

  public void focusGained(FocusEvent e) {
    Object source = e.getSource();
    if (!(source instanceof JButton)) {
      return;
    }
    JButton button = (JButton) source;
    JRootPane rootPane = SwingUtilities.getRootPane(button);
    if (rootPane != null) {
      this._defaultButton = rootPane.getDefaultButton();
      rootPane.setDefaultButton(button);
    }
  }

  public void focusLost(FocusEvent e) {
    Object source = e.getSource();
    if (!(source instanceof JButton)) {
      return;
    }

    JButton button = (JButton) source;
    JRootPane rootPane = (JRootPane) SwingUtilities.getAncestorOfClass(JRootPane.class, button);
    if (rootPane != null && this._defaultButton != null) {
      rootPane.setDefaultButton(this._defaultButton);
      this._defaultButton = null;
    }
  }
}
