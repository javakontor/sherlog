package org.javakontor.sherlog.application.internal;

import java.util.LinkedList;
import java.util.List;

import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javakontor.sherlog.application.action.Action;
import org.javakontor.sherlog.application.action.ActionGroupType;
import org.javakontor.sherlog.application.action.StaticActionProvider;
import org.javakontor.sherlog.application.action.impl.AbstractToggleAction;
import org.javakontor.sherlog.application.action.impl.ActionGroupElementServiceHelper;
import org.javakontor.sherlog.application.action.impl.DefaultActionGroup;
import org.osgi.framework.BundleContext;

import static org.javakontor.sherlog.application.menu.MenuConstants.*;

public class LookAndFeelMenu {

  private final Log                      _logger            = LogFactory.getLog(getClass());

  /**
   * The id of the look and feel menu
   */
  public final static String             LAF_MENU_ID        = "lafMenu";

  /**
   * Use to place items in the "Look and feel" menu
   */
  public final static String             LAF_MENU_TARGET_ID = MENUBAR_ID + "/" + LAF_MENU_ID;

  private final DefaultApplicationWindow _mainFrame;

  public LookAndFeelMenu(DefaultApplicationWindow mainFrame, BundleContext bundleContext) {
    super();
    this._mainFrame = mainFrame;

    LookAndFeelActionGroup lookAndFeelActionGroup = new LookAndFeelActionGroup();
    ActionGroupElementServiceHelper.registerActionGroup(bundleContext, lookAndFeelActionGroup);

  }

  /**
   * An ActionGroup containing all available look and feeld
   * 
   * <p>
   * On startup the ActionGroup gets populated with all installed LAFs.
   * <p>
   * At runtime other bundles can contribute new look and feels to this group
   * 
   */
  class LookAndFeelActionGroup extends DefaultActionGroup implements StaticActionProvider {

    private final Action[] _initialActions;

    public LookAndFeelActionGroup() {
      super(ActionGroupType.radiogroup, LAF_MENU_ID, WINDOW_MENU_TARGET_ID, "&Look and feel");

      // determine available LaFs
      LookAndFeelInfo[] installedLookAndFeels = UIManager.getInstalledLookAndFeels();

      // create an Action for each LAF
      final List<LookAndFeelAction> actions = new LinkedList<LookAndFeelAction>();
      for (LookAndFeelInfo info : installedLookAndFeels) {
        actions.add(new LookAndFeelAction(info));
      }
      this._initialActions = actions.toArray(new Action[0]);
    }

    public Action[] getActions() {
      return this._initialActions;
    }

    public boolean isFinal() {
      return false;
    }
  }

  class LookAndFeelAction extends AbstractToggleAction {

    private final LookAndFeelInfo _lookAndFeelInfo;

    public LookAndFeelAction(LookAndFeelInfo lookAndFeelInfo) {
      super(LAF_MENU_ID + "." + lookAndFeelInfo.getName(), LAF_MENU_TARGET_ID, lookAndFeelInfo.getName());
      this._lookAndFeelInfo = lookAndFeelInfo;
    }

    @Override
    public boolean isActive() {
      LookAndFeel lookAndFeel = UIManager.getLookAndFeel();

      return ((lookAndFeel != null) && lookAndFeel.getClass().getName().equals(this._lookAndFeelInfo.getClassName()));
    }

    public void execute() {

      LookAndFeelMenu.this._logger.info("Set lookAndFeel " + this._lookAndFeelInfo.getClassName());
      try {
        UIManager.setLookAndFeel(this._lookAndFeelInfo.getClassName());
      } catch (final Throwable e) {
        e.printStackTrace();
        try {
          UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
      try {
        SwingUtilities.updateComponentTreeUI(LookAndFeelMenu.this._mainFrame);
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
  }

}
