package org.javakontor.sherlog.application.internal;

import javax.swing.JMenuBar;

import org.javakontor.sherlog.application.action.contrib.ActionSet;
import org.javakontor.sherlog.application.action.contrib.ActionSetChangeListener;
import org.javakontor.sherlog.application.internal.menu.MenuBar;
import org.javakontor.sherlog.application.internal.menu.MenuBuilder;

public class ApplicationWindowMenuBar {

  private final JMenuBar                 _menuBar;

  private final MenuBuilder              _menuBuilder;

  private final MenuBarActionSetListener _actionSetListener;

  private ActionSet                      _menuActionSet;

  public ApplicationWindowMenuBar(JMenuBar menuBar) {
    super();
    this._menuBar = menuBar;
    this._menuBuilder = new MenuBuilder(new MenuBar(this._menuBar));
    this._actionSetListener = new MenuBarActionSetListener();
  }

  public ActionSet getMenuActionSet() {
    return this._menuActionSet;
  }

  public void dispose() {
    if (this._menuActionSet != null) {
      this._menuActionSet.removeActionSetChangeListener(this._actionSetListener);
      this._menuActionSet = null;
    }
  }

  public void setMenuActionSet(ActionSet menuActionSet) {
    // remove listener from previous actionSet
    if (this._menuActionSet != null) {
      this._menuActionSet.removeActionSetChangeListener(this._actionSetListener);
    }

    if (menuActionSet != null) {
      menuActionSet.addActionSetChangeListener(this._actionSetListener);
    }

    this._menuActionSet = menuActionSet;
    rebuildMenu();
  }

  public void rebuildMenu() {
    this._menuBuilder.build(this._menuActionSet);
  }

  class MenuBarActionSetListener implements ActionSetChangeListener {
    public void actionSetChange() {
      rebuildMenu();
    }
  }
}
