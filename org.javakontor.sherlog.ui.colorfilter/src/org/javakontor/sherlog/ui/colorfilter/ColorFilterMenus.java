package org.javakontor.sherlog.ui.colorfilter;

import java.awt.Color;
import java.util.LinkedList;
import java.util.List;

import org.javakontor.sherlog.application.action.contrib.ActionContribution;
import org.javakontor.sherlog.application.action.contrib.ActionGroupContribution;
import org.javakontor.sherlog.application.action.contrib.DefaultActionContribution;
import org.javakontor.sherlog.application.action.contrib.DefaultActionGroupContribution;
import org.javakontor.sherlog.ui.logview.LogViewConstants;
import org.osgi.framework.BundleContext;

/**
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
    final ActionContribution[] filterByColorAction = new DefaultActionContribution[this._colorNames.length];

    for (int i = 0; i < this._colorNames.length; i++) {
      final String colorName = this._colorNames[i];
      markWithColorActions.add(new DefaultActionContribution("markWithCtxMenuTitle" + colorName,
          ColorFilterMenus.MARK_WITH_COLOR_TARGET_ACTIONGROUP_ID, String.format(
              ColorFilterMessages.markWithCtxMenuTitle, colorName), null, new MarkWithColorAction(this._colorNames[i],
              this._colors[i])));
      filterByColorAction[i] = new DefaultActionContribution("filter" + colorName + "Action",
          ColorFilterMenus.FILTER_BY_COLOR_TARGET_ACTIONGROUP_ID, String.format(
              ColorFilterMessages.filterByCtxMenuTitle, colorName), null, new ColorFilterAction(this._colorNames[i],
              this._colors[i]));
    }
    // super("unmark", ,

    final DefaultActionContribution unmarkContribution = new DefaultActionContribution("unmark",
        ColorFilterMenus.MARK_WITH_COLOR_TARGET_ACTIONGROUP_ID + "(last)", ColorFilterMessages.removeColorMarker, null,
        new UnmarkAction());

    // ColorFilterMessages.removeColorMarker);
    markWithColorActions.add(unmarkContribution);

    final DefaultActionGroupContribution markWithColorActionGroup = new DefaultActionGroupContribution(
        MARK_WITH_COLOR_ACTIONGROUP_ID, LogViewConstants.CONTEXT_MENU_ID, ColorFilterMessages.markCtxMenuTitle);
    markWithColorActionGroup.setStaticActionContributions(markWithColorActions.toArray(new ActionContribution[0]));

    final DefaultActionGroupContribution filterByColorActionGroup = new DefaultActionGroupContribution(
        FILTER_BY_COLOR_ACTIONGROUP_ID, LogViewConstants.CONTEXT_MENU_ID, ColorFilterMessages.filterCtxMenuTitle);
    filterByColorActionGroup.setStaticActionContributions(filterByColorAction);

    bundleContext.registerService(ActionGroupContribution.class.getName(), markWithColorActionGroup, null);
    bundleContext.registerService(ActionGroupContribution.class.getName(), filterByColorActionGroup, null);
  }

}
