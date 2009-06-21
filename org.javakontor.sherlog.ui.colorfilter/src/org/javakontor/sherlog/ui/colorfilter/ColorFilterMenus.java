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
 * Creates the context menu entries for the Color filter.
 * 
 * <p>
 * For each color out of a set of defined colors both a 'Mark with ...' and 'Filter by ...' menu item will be added to
 * the LogEventView's context menu
 * 
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

  /**
   * The names of the colors that should be available for marking and filtering
   */
  private final String[]     _colorNames                           = new String[] { ColorFilterMessages.red,
      ColorFilterMessages.gray, ColorFilterMessages.blue,         };

  /**
   * The RGB colors that should be available for marking and filtering
   */
  private final Color[]      _colors                               = new Color[] { Color.RED, Color.GRAY, Color.BLUE };

  /**
   * Registers the ColorFilter-Actions for the context menu.
   * 
   * <p>
   * The actions are added to two ActionGroupContributions (one for 'Mark...' and one for 'Filter...' actions). The
   * ActionGroupContributions will be registered at the Service Registry.
   * 
   * @param bundleContext
   *          The bundle context that should be used to register the ActionGroupContributions
   * @throws Exception
   */
  public void registerMenus(final BundleContext bundleContext) throws Exception {
    final List<ActionContribution> markWithColorActions = new LinkedList<ActionContribution>();
    final ActionContribution[] filterByColorAction = new DefaultActionContribution[this._colorNames.length];

    // Create Action and ActionContribution for both the 'mark with...' and 'filter by...' actions for
    // each color
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

    // Create the "Remove color Marker" action
    final DefaultActionContribution unmarkContribution = new DefaultActionContribution("unmark",
        ColorFilterMenus.MARK_WITH_COLOR_TARGET_ACTIONGROUP_ID + "(last)", ColorFilterMessages.removeColorMarker, null,
        new UnmarkAction());

    markWithColorActions.add(unmarkContribution);

    // Create the ActionGroupContribution for 'Mark with...' actions
    final DefaultActionGroupContribution markWithColorActionGroup = new DefaultActionGroupContribution(
        MARK_WITH_COLOR_ACTIONGROUP_ID, LogViewConstants.CONTEXT_MENU_ID, ColorFilterMessages.markCtxMenuTitle);
    markWithColorActionGroup.setStaticActionContributions(markWithColorActions.toArray(new ActionContribution[0]));

    // Create the ActionGroupContribution for 'Filter by...' actions
    final DefaultActionGroupContribution filterByColorActionGroup = new DefaultActionGroupContribution(
        FILTER_BY_COLOR_ACTIONGROUP_ID, LogViewConstants.CONTEXT_MENU_ID, ColorFilterMessages.filterCtxMenuTitle);
    filterByColorActionGroup.setStaticActionContributions(filterByColorAction);

    // register ActionGroupContributions at Service Registry
    bundleContext.registerService(ActionGroupContribution.class.getName(), markWithColorActionGroup, null);
    bundleContext.registerService(ActionGroupContribution.class.getName(), filterByColorActionGroup, null);
  }

}
