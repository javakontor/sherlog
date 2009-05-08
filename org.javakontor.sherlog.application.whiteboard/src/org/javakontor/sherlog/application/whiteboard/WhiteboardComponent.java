package org.javakontor.sherlog.application.whiteboard;

import org.javakontor.sherlog.application.action.contrib.ActionContribution;
import org.javakontor.sherlog.application.action.contrib.ActionContributionAdmin;
import org.javakontor.sherlog.application.action.contrib.ActionGroupContribution;

/**
 * <p>
 * Implements a 'whiteboard component'. The whiteboard component is responsible for tracking and registering all
 * {@link ActionContribution Actions} and {@link ActionGroupContribution ActionGroups} that are registered with the OSGi
 * service registry.
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class WhiteboardComponent {

  /** the action set manager */
  private ActionContributionAdmin                         _admin           = null;

  /**
   * <p>
   * Called if an {@link ActionContributionAdmin} is added.
   * </p>
   *
   * @param actionContributionAdmin
   *          the added {@link ActionContributionAdmin}
   */
  public void addActionContributionAdmin(ActionContributionAdmin actionContributionAdmin) {
    // set the admin
    _admin = actionContributionAdmin;
  }

  /**
   * <p>
   * </p>
   *
   * @param actionSetManager
   */
  public void removeActionContributionAdmin(ActionContributionAdmin actionContributionAdmin) {
    // unset the admin
    _admin = null;
  }

  /**
   * <p>
   * Called if an {@link ActionContribution} is added.
   * </p>
   *
   * @param action
   *          the added {@link ActionContribution}
   */
  public void addActionContribution(ActionContribution action) {
    _admin.addActionContribution(action);
  }

  /**
   * <p>
   * Called if an {@link ActionContribution} is removed.
   * </p>
   *
   * @param action
   *          the removed {@link ActionContribution}
   */
  public void removeActionContribution(ActionContribution action) {
    _admin.removeActionContribution(action);
  }

  /**
   * <p>
   * Called if an {@link ActionGroupContribution} is added.
   * </p>
   *
   * @param actionGroup
   *          the added {@link ActionGroupContribution}
   */
  public void addActionGroupContribution(ActionGroupContribution actionGroup) {
    _admin.addActionGroupContribution(actionGroup);
  }

  /**
   * <p>
   * Called if an {@link ActionGroupContribution} is removed.
   * </p>
   *
   * @param actionGroup
   *          the removed {@link ActionGroupContribution}
   */
  public void removeActionGroupContribution(ActionGroupContribution actionGroup) {
    _admin.removeActionGroupContribution(actionGroup);
  }
}
