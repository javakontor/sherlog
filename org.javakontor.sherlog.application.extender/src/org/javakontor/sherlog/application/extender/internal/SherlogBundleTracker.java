package org.javakontor.sherlog.application.extender.internal;

import java.util.List;

import org.javakontor.sherlog.application.action.contrib.ActionContribution;
import org.javakontor.sherlog.application.action.contrib.ActionContributionAdmin;
import org.javakontor.sherlog.application.action.contrib.ActionGroupContribution;
import org.javakontor.sherlog.application.extender.internal.model.ActionDefinition;
import org.javakontor.sherlog.util.Assert;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.util.tracker.BundleTracker;

/**
 * <p>
 * Implements a custom bundle tracker that instantiates and registers {@link ActionContribution Actions} and action
 * groups {@link ActionGroupContribution ActionGroups} based on a JSON description.
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class SherlogBundleTracker extends BundleTracker {

  /** the action set manager */
  private ActionContributionAdmin _actionContributionAdmin;

  /**
   * <p>
   * Creates a new instance of type {@link SherlogBundleTracker}
   * </p>
   *
   * @param context
   *          the bundle context
   * @param actionSetManager
   *          the action set manager
   */
  public SherlogBundleTracker(BundleContext context, ActionContributionAdmin actionContributionAdmin) {
    super(context, Bundle.ACTIVE | Bundle.STARTING, null);

    Assert.notNull("Parameter actionContributionAdmin has to be set!", actionContributionAdmin);

    // the action contribution admin
    _actionContributionAdmin = actionContributionAdmin;
  }

  /**
   * @see org.osgi.util.tracker.BundleTracker#addingBundle(org.osgi.framework.Bundle, org.osgi.framework.BundleEvent)
   */
  @Override
  public Object addingBundle(Bundle bundle, BundleEvent event) {

    // call super
    super.addingBundle(bundle, event);

    // get the action definitions
    List<ActionDefinition> actionDefintions = ActionDefinitionReader.readActionDefinitions(bundle);

    // create and register
    for (ActionDefinition actionDefinition : actionDefintions) {
      ActionRegistrationHelper.createAndRegister(actionDefinition, _actionContributionAdmin);
    }

    // return the result
    return actionDefintions;
  }

  /**
   * @see org.osgi.util.tracker.BundleTracker#removedBundle(org.osgi.framework.Bundle, org.osgi.framework.BundleEvent,
   *      java.lang.Object)
   */
  @Override
  public void removedBundle(Bundle bundle, BundleEvent event, Object object) {
    super.removedBundle(bundle, event, object);

    @SuppressWarnings("unchecked")
    List<ActionDefinition> actionDefinitions = (List<ActionDefinition>) object;

    for (ActionDefinition actionDefinition : actionDefinitions) {
      ActionRegistrationHelper.unregisterAndDestroy(actionDefinition, _actionContributionAdmin);
    }
  }
}