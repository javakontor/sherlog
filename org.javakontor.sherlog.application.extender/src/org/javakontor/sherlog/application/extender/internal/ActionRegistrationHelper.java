package org.javakontor.sherlog.application.extender.internal;

import java.util.List;

import org.javakontor.sherlog.application.action.Action;
import org.javakontor.sherlog.application.action.contrib.ActionContributionAdmin;
import org.javakontor.sherlog.application.extender.BundleContextAware;
import org.javakontor.sherlog.application.extender.Lifecycle;
import org.javakontor.sherlog.application.extender.internal.model.ActionDefinition;
import org.javakontor.sherlog.application.extender.internal.model.ActionEntry;
import org.javakontor.sherlog.application.extender.internal.service.ServiceAwareActionProxy;
import org.osgi.framework.Bundle;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 *
 */
public final class ActionRegistrationHelper {

  /**
   * <p>
   * Instantiate all defined actions.
   * </p>
   *
   * @param actionDefinition
   *          the specification container
   */
  public static void createAndRegister(ActionDefinition actionDefinition, ActionContributionAdmin admin) {

    // the action specifications
    List<ActionEntry> actionEntries = actionDefinition.getActionEntry();

    // iterate over all action specifications
    for (ActionEntry actionEntry : actionEntries) {

      // get the class name
      String className = actionEntry.getActionClass();

      // try to instantiate the class name
      try {
        // load the class
        Bundle bundle = actionEntry.getBundle();
        Class<?> actionClass = bundle.loadClass(className);

        // check is class is assignable from class Action
        if (!Action.class.isAssignableFrom(actionClass)) {
          throw new RuntimeException("The specified class '" + className + "' does not implement the interface '"
              + Action.class.getName() + "'!");
        }

        // create a new instance
        Action action = (Action) actionClass.newInstance();
        actionEntry.setAction(action);

        // set bundle context
        if (action instanceof BundleContextAware) {
          ((BundleContextAware) action).setBundleContext(actionEntry.getBundle().getBundleContext());
        }

        // initialize
        if (action instanceof Lifecycle) {
          ((Lifecycle) action).initialise();
        }

        if (actionEntry.hasReferences()) {

          ServiceAwareActionProxy proxy = new ServiceAwareActionProxy(actionEntry);
          proxy.initialise();

          // add the action
          admin.addAction(actionEntry.getId(), actionEntry.getActionGroupId(), actionEntry.getLabel(), actionEntry
              .getShortcut(), proxy);
        } else {
          // add the action
          admin.addAction(actionEntry.getId(), actionEntry.getActionGroupId(), actionEntry.getLabel(), actionEntry
              .getShortcut(), action);
        }
      } catch (ClassNotFoundException e) {
        throw new RuntimeException("The specified class '" + className + "' could not be found!", e);
      } catch (InstantiationException e) {
        throw new RuntimeException("The specified class '" + className + "' could not be instantiated!", e);
      } catch (IllegalAccessException e) {
        throw new RuntimeException("The specified class '" + className + "' could not be instantiated!", e);
      }
    }
  }

  /**
   * <p>
   * </p>
   *
   * @param actionDefinition
   * @param admin
   */
  public static void unregisterAndDestroy(ActionDefinition actionDefinition, ActionContributionAdmin admin) {

    // for all action entries
    for (ActionEntry actionEntry : actionDefinition.getActionEntry()) {

      // remove from action registry
      admin.removeAction(actionEntry.getId());

      // dispose
      if (actionEntry.getAction() instanceof Lifecycle) {
        ((Lifecycle) actionEntry.getAction()).dispose();
      }

      // set bundle context
      if (actionEntry.getAction() instanceof BundleContextAware) {
        ((BundleContextAware) actionEntry.getAction()).setBundleContext(null);
      }
    }
  }
}
