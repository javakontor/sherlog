package org.javakontor.sherlog.application.internal.menu;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * A MenuRoot that sets up a {@link JPopupMenu}
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class PopupMenu implements MenuRoot {

  private final JPopupMenu _popupMenu;

  public PopupMenu(JPopupMenu popupMenu) {
    _popupMenu = popupMenu;
  }

  public void addAll(Iterable<JMenuItem> menuItems) {
    for (JMenuItem item : menuItems) {
      _popupMenu.add(item);
    }
  }

  public void clear() {
    _popupMenu.removeAll();
  }

}
