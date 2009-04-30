package org.javakontor.sherlog.application.extender;

import org.javakontor.sherlog.application.action.ActionSetManager;
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
  private ActionSetManager _actionSetManager;

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
    _bundleTracker = new SherlogBundleTracker(componentContext.getBundleContext(), _actionSetManager);
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
   * Adds the action set manager.
   * </p>
   *
   * @param actionSetManager
   */
  public void addActionSetManager(ActionSetManager actionSetManager) {
    _actionSetManager = actionSetManager;
  }

  /**
   * <p>
   * Removes the action set manager.
   * </p>
   *
   * @param actionSetManager
   */
  public void removeActionSetManager(ActionSetManager actionSetManager) {
    _actionSetManager = null;
  }
}
