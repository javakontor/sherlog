package org.javakontor.sherlog.application.internal.menu;

import java.awt.Window;

import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;

/**
 * A {@link MenuRoot} that is based on a {@link JMenuBar}
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class MenuBar implements MenuRoot {

  private final JMenuBar _menuBar;

  public MenuBar(JMenuBar menuBar) {
    _menuBar = menuBar;
  }

  public void add(Iterable<JMenuItem> submenuItems) {
    for (JMenuItem menuItem : submenuItems) {
      _menuBar.add(menuItem);
    }

    // redraw window to make changes visible
    SwingUtilities.invokeLater(new Runnable() {

      public void run() {
        Window window = (Window) SwingUtilities.getAncestorOfClass(Window.class, _menuBar);
        window.validate();
        window.repaint();
      }
    });

  }

  public void clear() {
    // ~ clean whole menubar
    _menuBar.removeAll();
  }

}
