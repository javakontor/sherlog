package org.javakontor.sherlog.application.internal;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import org.javakontor.sherlog.application.internal.heap.HeapPanel;

public class ApplicationStatusBar extends JPanel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private HeapPanel       _memoryPanel;

  public ApplicationStatusBar() {

    // Create components
    this._memoryPanel = new HeapPanel();

    // set layout
    setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    final Border insets = new EmptyBorder(1, 1, 1, 1);
    setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(), insets));
    // add components to panel

    add(Box.createHorizontalGlue());
    add(this._memoryPanel);

  }

  public void dispose() {
    if (this._memoryPanel != null) {
      this._memoryPanel.dispose();
      this._memoryPanel = null;
    }
  }

}
