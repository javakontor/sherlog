package org.javakontor.sherlog.application.internal;

import static org.javakontor.sherlog.application.action.MenuConstants.MENUBAR_ID;
import static org.javakontor.sherlog.application.action.MenuConstants.WINDOW_MENU_TARGET_ID;

import java.util.LinkedList;
import java.util.List;

import javax.swing.LookAndFeel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javakontor.sherlog.application.action.AbstractToggleAction;
import org.javakontor.sherlog.application.action.ActionGroupType;
import org.javakontor.sherlog.application.action.contrib.ActionContribution;
import org.javakontor.sherlog.application.action.contrib.ActionGroupContribution;
import org.javakontor.sherlog.application.action.contrib.DefaultActionContribution;
import org.javakontor.sherlog.application.action.contrib.DefaultActionGroupContribution;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

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

  private final ServiceRegistration      _serviceRegistration;

  private final ApplicationWindow _mainFrame;

  public LookAndFeelMenu(final ApplicationWindow mainFrame, final BundleContext bundleContext) {
    super();
    this._mainFrame = mainFrame;

    final LookAndFeelActionGroup lookAndFeelActionGroup = new LookAndFeelActionGroup();
    this._serviceRegistration = bundleContext.registerService(ActionGroupContribution.class.getName(),
        lookAndFeelActionGroup, null);
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
  class LookAndFeelActionGroup extends DefaultActionGroupContribution {

    private final ActionContribution[] _initialActions;

    public LookAndFeelActionGroup() {
      super(LAF_MENU_ID, WINDOW_MENU_TARGET_ID, "&Look and feel", ActionGroupType.radiogroup);

      // determine available LaFs
      final LookAndFeelInfo[] installedLookAndFeels = UIManager.getInstalledLookAndFeels();

      // create an Action for each LAF
      final List<ActionContribution> actions = new LinkedList<ActionContribution>();
      for (final LookAndFeelInfo info : installedLookAndFeels) {

        final DefaultActionContribution contribution = new DefaultActionContribution(
            LAF_MENU_ID + "." + info.getName(), LAF_MENU_TARGET_ID, info.getName(), null, new LookAndFeelAction(info));

        actions.add(contribution);
      }

      //

      this._initialActions = actions.toArray(new ActionContribution[0]);
    }

    @Override
    public ActionContribution[] getStaticActionContributions() {
      return this._initialActions;
    }
  }

  class LookAndFeelAction extends AbstractToggleAction {

    private final LookAndFeelInfo _lookAndFeelInfo;

    public LookAndFeelAction(final LookAndFeelInfo lookAndFeelInfo) {
      this._lookAndFeelInfo = lookAndFeelInfo;
    }

    @Override
    public boolean isActive() {
      final LookAndFeel lookAndFeel = UIManager.getLookAndFeel();

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
        } catch (final Exception ex) {
          ex.printStackTrace();
        }
      }
      try {
        SwingUtilities.updateComponentTreeUI(LookAndFeelMenu.this._mainFrame);
      } catch (final Exception ex) {
        ex.printStackTrace();
      }
    }
  }

  public void dispose() {
    this._serviceRegistration.unregister();
  }

}
