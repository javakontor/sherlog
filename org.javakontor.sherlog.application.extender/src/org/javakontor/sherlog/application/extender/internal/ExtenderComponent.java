package org.javakontor.sherlog.application.extender.internal;

import org.javakontor.sherlog.application.action.contrib.ActionContributionAdmin;
import org.osgi.service.component.ComponentContext;
import org.osgi.util.tracker.BundleTracker;

/**
 * <p>
 * The extender component.
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ExtenderComponent {

  /** the bundle tracker */
  private BundleTracker    _bundleTracker;

  /** the action set manager */
  private ActionContributionAdmin _actionContributionAdmin;

  /**
   * <p>
   * Called when the component is activated.
   * </p>
   *
   * @param componentContext
   *          the component context
   */
  public void activate(ComponentContext componentContext) {
    // create and open the bundle tracker
    _bundleTracker = new SherlogBundleTracker(componentContext.getBundleContext(), _actionContributionAdmin);
    _bundleTracker.open();
  }

  /**
   * <p>
   * Called when the component is deactivated.
   * </p>
   *
   * @param componentContext
   *          the component context
   */
  public void deactivate(ComponentContext componentContext) {
    // close the bundle tracker
    _bundleTracker.close();
  }

  /**
   * <p>
   * Binds the action contribution admin.
   * </p>
   *
   * @param actionContributionAdmin
   */
  public void bindActionContributionAdmin(ActionContributionAdmin actionContributionAdmin) {
    _actionContributionAdmin = actionContributionAdmin;
  }

  /**
   * <p>
   * Unbinds the action contribution admin.
   * </p>
   *
   * @param actionContributionAdmin
   */
  public void unbindActionContributionAdmin(ActionContributionAdmin actionContributionAdmin) {
    _actionContributionAdmin = null;
  }
}
