package org.javakontor.sherlog.ui.colorfilter;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import org.javakontor.sherlog.application.action.contrib.ActionContribution;
import org.javakontor.sherlog.application.action.contrib.ActionGroupContribution;
import org.javakontor.sherlog.application.action.contrib.DefaultActionGroup;
import org.javakontor.sherlog.application.action.contrib.StaticActionProvider;
import org.javakontor.sherlog.ui.logview.LogViewConstants;
import org.osgi.framework.BundleContext;

/**
 * TODO own bundle ?
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class ColorFilterMenus {

  public final static String MARK_WITH_COLOR_ACTIONGROUP_ID        = "markWithColor";

  public final static String MARK_WITH_COLOR_TARGET_ACTIONGROUP_ID = LogViewConstants.CONTEXT_MENU_ID + "/"
                                                                       + MARK_WITH_COLOR_ACTIONGROUP_ID;

  public final static String FILTER_BY_COLOR_ACTIONGROUP_ID        = "filterByColor";

  public final static String FILTER_BY_COLOR_TARGET_ACTIONGROUP_ID = LogViewConstants.CONTEXT_MENU_ID + "/"
                                                                       + "filterByColor";

  private final String[]     _colorNames                           = new String[] { ColorFilterMessages.red,
      ColorFilterMessages.gray, ColorFilterMessages.blue,         };

  private final Color[]      _colors                               = new Color[] { Color.RED, Color.GRAY, Color.BLUE };

  public void registerMenus(final BundleContext bundleContext) throws Exception {
    final List<ActionContribution> markWithColorActions = new LinkedList<ActionContribution>();
    final ActionContribution[] filterByColorAction = new ColorFilterAction[this._colorNames.length];

    for (int i = 0; i < this._colorNames.length; i++) {
      markWithColorActions.add(new MarkWithColorAction(this._colorNames[i], this._colors[i]));
      filterByColorAction[i] = new ColorFilterAction(this._colorNames[i], this._colors[i]);
    }
    // super("unmark", ColorFilterMenus.MARK_WITH_COLOR_TARGET_ACTIONGROUP_ID + "(last)",
    // ColorFilterMessages.removeColorMarker);
    markWithColorActions.add(new UnmarkAction());

    final StaticActionGroup markWithColorActionGroup = new StaticActionGroup(MARK_WITH_COLOR_ACTIONGROUP_ID,
        ColorFilterMessages.markCtxMenuTitle, markWithColorActions.toArray(new ActionContribution[0]));
    final StaticActionGroup filterByColorActionGroup = new StaticActionGroup(FILTER_BY_COLOR_ACTIONGROUP_ID,
        ColorFilterMessages.filterCtxMenuTitle, filterByColorAction);

    bundleContext.registerService(ActionGroupContribution.class.getName(), markWithColorActionGroup, null);
    bundleContext.registerService(ActionGroupContribution.class.getName(), filterByColorActionGroup, null);
  }

  class StaticActionGroup extends DefaultActionGroup implements StaticActionProvider {

    private final ActionContribution[] _actions;

    public StaticActionGroup(final String id, final String label, final ActionContribution... actions) {
      super(id, LogViewConstants.CONTEXT_MENU_ID, label);
      this._actions = actions;
    }

    public ActionContribution[] getActionContributions() {
      return this._actions;
    }

    public boolean isFinal() {
      return false;
    }

  }

}
