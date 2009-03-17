package org.javakontor.sherlog.application.internal;

import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;

import org.javakontor.sherlog.application.action.Action;
import org.javakontor.sherlog.application.action.ActionGroup;
import org.javakontor.sherlog.application.action.ActionGroupType;
import org.javakontor.sherlog.application.action.StaticActionGroupProvider;
import org.javakontor.sherlog.application.action.StaticActionProvider;
import org.javakontor.sherlog.application.action.impl.AbstractAction;
import org.javakontor.sherlog.application.action.impl.ActionGroupElementServiceHelper;
import org.javakontor.sherlog.application.action.impl.DefaultActionGroup;
import org.javakontor.sherlog.application.internal.util.Arranger;
import org.javakontor.sherlog.application.view.ViewContribution;
import org.osgi.framework.BundleContext;
import static org.javakontor.sherlog.application.menu.MenuConstants.*;
public class WindowMenu {
  public static final String WINDOW_ARRANGE_MENU_ID        = WINDOW_MENU_ID + ".arrange";

  public static final String WINDOW_ARRANGE_MENU_TARGET_ID = MENUBAR_ID + "/" + WINDOW_ARRANGE_MENU_ID;

  ApplicationWindow          _applicationWindow;

  public WindowMenu(BundleContext context, ApplicationWindow applicationWindow) {
    this._applicationWindow = applicationWindow;

    // Create 'Window' menu in menubar
    WindowMenuActionGroup windowMenuActionGroup = new WindowMenuActionGroup();
    ActionGroupElementServiceHelper.registerActionGroup(context, windowMenuActionGroup);
  }

  /**
   * An ActionGroup containing:
   * <ul>
   * <li>an <b>arrange<b> ActionGroup that allows a user to re-arrange the visible windows</li>
   * <li>a dynamic <b>window list</b> that list all opened window</li>
   * <li>an empty <b>tools</b> submenu</li>
   * <ul>
   * 
   * @author Nils Hartmann (nils@nilshartmann.net)
   */
  class WindowMenuActionGroup extends DefaultActionGroup implements StaticActionGroupProvider {

    private final ActionGroup[] _staticActionGroups;

    public WindowMenuActionGroup() {
      super(ActionGroupType.simple, WINDOW_MENU_ID, MENUBAR_ID + "(last)", ApplicationMessages.windowMenuTitle);

      this._staticActionGroups = new ActionGroup[] { new ArrangeActionGroup(),
          new DefaultActionGroup(TOOLS_MENU_ID, WINDOW_MENU_TARGET_ID, ApplicationMessages.toolsMenuTitle) };
    }

    public ActionGroup[] getActionGroups() {
      return this._staticActionGroups;
    }

    public boolean isFinal() {
      return false;
    }
  }

  class ArrangeActionGroup extends DefaultActionGroup implements StaticActionProvider {

    private final Action[] _actions;

    public ArrangeActionGroup() {
      super(ActionGroupType.simple, WINDOW_ARRANGE_MENU_ID, WINDOW_MENU_TARGET_ID + "(first)",
          ApplicationMessages.arrangeMenuTitle);

      this._actions = new Action[] { new ArrangeAction(ApplicationMessages.horizontalMenuTitle, Arranger.HORIZONTAL),
          new ArrangeAction(ApplicationMessages.verticalMenuTitle, Arranger.VERTICAL),
          new ArrangeAction(ApplicationMessages.arrangeWindowsMenuTitle, Arranger.ARRANGE),
          new ArrangeAction(ApplicationMessages.cascadeMenuTitle, Arranger.CASCADE) };
    }

    public Action[] getActions() {
      return this._actions;
    }

    public boolean isFinal() {
      return false;
    }

  }

  class ArrangeAction extends AbstractAction {

    private final int _arrangeMode;

    public ArrangeAction(String label, int arrangeMode) {
      super(WINDOW_ARRANGE_MENU_ID + "." + arrangeMode, WINDOW_ARRANGE_MENU_TARGET_ID, label);

      this._arrangeMode = arrangeMode;
    }

    public void execute() {
      WindowMenu.this._applicationWindow.arrange(this._arrangeMode);
    }
  }

  /**
   * An {@link Action}, that brings the window of a {@link ViewContribution} to front
   * 
   */
  class OpenWindowAction extends AbstractAction {

    private final ViewContribution _viewContribution;

    public OpenWindowAction(String actionId, String targetActionGroupId, String label, ViewContribution viewContribution) {
      super(actionId, targetActionGroupId, label);
      this._viewContribution = viewContribution;
    }

    public void execute() {
      // Find the JInternalFrame that holds the ViewContribution
      JInternalFrame window = (JInternalFrame) SwingUtilities.getAncestorOfClass(JInternalFrame.class,
          this._viewContribution.getPanel());

      // bring it to front
      if (window != null) {
        window.toFront();
        window.requestFocusInWindow();
      }
    }
  }

}
