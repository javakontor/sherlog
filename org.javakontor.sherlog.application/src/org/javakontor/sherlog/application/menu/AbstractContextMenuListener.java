package org.javakontor.sherlog.application.menu;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPopupMenu;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;

import org.javakontor.sherlog.application.action.ActionContextAware;
import org.javakontor.sherlog.application.action.contrib.ActionSet;

/**
 * A {@link MouseAdapter} that opens a (dynamic) {@link ContextMenu}
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public abstract class AbstractContextMenuListener<C> extends MouseAdapter {

  private final ContextMenu<C> _contextMenu;

  /**
   * Creates a new listener instance that opens the specified {@link ContextMenu} when apropriate mouse button is
   * clicked
   * 
   * @param contextMenu
   */
  public AbstractContextMenuListener(ContextMenu<C> contextMenu) {
    this._contextMenu = contextMenu;
    this._contextMenu.getPopupMenu().addPopupMenuListener(new AfterMenuListener());
  }

  @Override
  public void mousePressed(final MouseEvent e) {
    showPopup(e);
  }

  @Override
  public void mouseReleased(final MouseEvent e) {
    showPopup(e);
  }

  /**
   * Opens the context menu when the event is a {@link MouseEvent#isPopupTrigger() popup trigger}
   * 
   * <p>
   * Before the menu is opened an {@link #getActionContext() ActionContext} is applied to all registered Actions, that
   * implements {@link ActionContextAware}
   * 
   * @param e
   */
  private void showPopup(final MouseEvent e) {
    if (e.isPopupTrigger() && showPopupMenu()) {
      JPopupMenu menu = this._contextMenu.getPreparedPopupMenu(getActionGroupRegistry(), getActionContext());
      menu.show(e.getComponent(), e.getX(), e.getY());
    }
  }

  /**
   * Returns the Popup-specific ActionContext, that gets applied to all Actions before the context menu is shown
   * 
   * @return
   */
  protected abstract C getActionContext();

  protected abstract ActionSet getActionGroupRegistry();

  protected boolean showPopupMenu() {
    return true;
  }

  /**
   * This method is called after the menu has been made invisible again.
   * 
   * <p>
   * It can be overridden in subclasses to execute logic after the menu has finished
   */
  protected void afterMenu() {

  }

  /**
   * A {@link PopupMenuListener} that calls afterMenu after the popup menu has been closed
   */
  private class AfterMenuListener implements PopupMenuListener {

    public void popupMenuCanceled(PopupMenuEvent e) {
    }

    public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
      afterMenu();
    }

    public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
    }

  };

}
