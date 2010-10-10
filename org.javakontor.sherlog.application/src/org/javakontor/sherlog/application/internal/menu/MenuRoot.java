package org.javakontor.sherlog.application.internal.menu;

import javax.swing.JMenuItem;

/**
 * Abstracts the concept of different menu "roots".
 * 
 * <p>
 * A "menu root" can be for example a JMenuBar or a JPopupMenu
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public interface MenuRoot {
  /**
   * Clears the whole menu (including all submenus)
   */
  public void clear();

  /**
   * Adds the specified JMenuItems to this menu root.
   * 
   * <p>
   * The menuItems given are configured and "ready to use"
   * 
   * <p>
   * A MenuRoot must decide if it must redraw it's parent after adding the items (a JMenuBar typically have to be
   * redrawn)
   * 
   * @param menuItems
   *          the items to be added
   */
  public void addAll(Iterable<JMenuItem> menuItems);
}
