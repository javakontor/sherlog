package org.javakontor.sherlog.application.menu;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.javakontor.sherlog.application.action.Action;
import org.javakontor.sherlog.application.action.ActionContextAware;
import org.javakontor.sherlog.application.action.contrib.ActionContribution;
import org.javakontor.sherlog.application.action.set.ActionSet;
import org.javakontor.sherlog.application.internal.menu.MenuBuilder;
import org.javakontor.sherlog.application.internal.menu.PopupMenu;

/**
 * C: ActionContext ContextMenu --
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class ContextMenu<C> {

  private final JPopupMenu  _popupMenu;

  private final MenuBuilder _menuBuilder;

  private C                 _currentActionContext;

  public ContextMenu() {
    this._popupMenu = new JPopupMenu();
    this._menuBuilder = new ContextMenuBuilder();
  }

  /**
   * Returns the {@link JPopupMenu} in it's current state. It's possible that the menu has not been initialized yet it's
   * context has not been set.
   * 
   * <p>
   * To get a configured, ready-to-show instance of JPopupMenu, use {@link #getPreparedPopupMenu(Object)}
   * 
   * 
   * @return
   */
  public JPopupMenu getPopupMenu() {
    return this._popupMenu;
  }

  public JPopupMenu getPreparedPopupMenu(final ActionSet actionGroupRegistry, final C actionContext) {
    this._currentActionContext = actionContext;
    // TODO!!
    this._menuBuilder.build(actionGroupRegistry);
    return this._popupMenu;
  }

  protected C getCurrentActionContext() {
    return this._currentActionContext;
  }

  class ContextMenuBuilder extends MenuBuilder {

    public ContextMenuBuilder() {
      super(new PopupMenu(ContextMenu.this._popupMenu));
    }

    @SuppressWarnings("unchecked")
    @Override
    protected JMenuItem createMenuItem(final ActionContribution actionContribution, final boolean radioButton) {
      final Action action = actionContribution.getAction();
      if (action instanceof ActionContextAware) {
        final ActionContextAware<C> contextAware = (ActionContextAware<C>) action;
        contextAware.setActionContext(getCurrentActionContext());
      }
      return super.createMenuItem(actionContribution, radioButton);
    }
  }

}
