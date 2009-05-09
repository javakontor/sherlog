package org.javakontor.sherlog.application.extender.internal;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.javakontor.sherlog.application.action.Action;
import org.javakontor.sherlog.application.action.contrib.ActionContribution;
import org.javakontor.sherlog.application.action.contrib.ActionContributionAdmin;
import org.javakontor.sherlog.application.action.contrib.ActionGroupContribution;
import org.javakontor.sherlog.application.extender.BundleContextAware;
import org.javakontor.sherlog.application.extender.Lifecycle;
import org.javakontor.sherlog.application.extender.internal.spec.ActionGroupElementSpecificationContainer;
import org.javakontor.sherlog.application.extender.internal.spec.ActionSpecification;
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

  /** the constant SHERLOG_ACTION */
  private static final String     SHERLOG_ACTION_BUNDLE_MANIFEST_HEADER = "Sherlog-Action";

  /** the action set manager */
  private ActionContributionAdmin _actionContributionAdmin;

  private ObjectMapper            _mapper;

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

    // create
    _mapper = new ObjectMapper();
  }

  /**
   * @see org.osgi.util.tracker.BundleTracker#addingBundle(org.osgi.framework.Bundle, org.osgi.framework.BundleEvent)
   */
  @Override
  public Object addingBundle(Bundle bundle, BundleEvent event) {

    // calls super.addingBundle(bundle, event)
    Object result = super.addingBundle(bundle, event);

    // get the action definitions
    List<URL> actionDefintions = getActionDefinitions(bundle);

    // handle action definitions
    if (!getActionDefinitions(bundle).isEmpty()) {

      for (URL url : actionDefintions) {

        try {
          ActionGroupElementSpecificationContainer actionDescription = _mapper.readValue(url.openStream(),
              ActionGroupElementSpecificationContainer.class);

          return instantiateActions(bundle, actionDescription);

        } catch (JsonParseException e) {
          e.printStackTrace();
        } catch (JsonMappingException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }

    // return the result
    return result;
  }

  /**
   * @see org.osgi.util.tracker.BundleTracker#removedBundle(org.osgi.framework.Bundle, org.osgi.framework.BundleEvent,
   *      java.lang.Object)
   */
  @Override
  public void removedBundle(Bundle bundle, BundleEvent event, Object object) {
    super.removedBundle(bundle, event, object);

    if (object instanceof ActionGroupElementSpecificationContainer) {
      ActionGroupElementSpecificationContainer container = (ActionGroupElementSpecificationContainer) object;

      List<ActionSpecification> specifications = container.getActionSpecifications();

      for (ActionSpecification spec : specifications) {
        // remove from action registry
        _actionContributionAdmin.removeAction(spec.getId());

        // set bundle context
        if (spec.getAction() instanceof BundleContextAware) {
          ((BundleContextAware) spec.getAction()).setBundleContext(bundle.getBundleContext());
        }

        // initialize
        if (spec.getAction() instanceof Lifecycle) {
          ((Lifecycle) spec.getAction()).initialise();
        }
      }
    }
  }

  /**
   * <p>
   * Returns a list with the URLs of all defined action definitions.
   * </p>
   *
   * @param bundle
   *          the bundle
   * @return a list with the URLs of all defined action definitions.
   */
  private List<URL> getActionDefinitions(Bundle bundle) {

    // create the result list
    List<URL> result = new LinkedList<URL>();

    // get the content of the SHERLOG_ACTION header
    String actionDefinitionList = (String) bundle.getHeaders().get(SHERLOG_ACTION_BUNDLE_MANIFEST_HEADER);

    // handle action definition
    if (actionDefinitionList != null) {

      // split the entries
      String[] actionDefinitions = actionDefinitionList.split(",");

      // load the action definitions
      for (String actionDefinition : actionDefinitions) {
        // the action definition URL
        URL actionDefinitionUrl = bundle.getEntry(actionDefinition.trim());

        // throw exception
        if (actionDefinitionUrl == null) {
          throw new RuntimeException("The specified entry " + actionDefinitionUrl + " does not exist in bundle '"
              + bundle.getLocation() + "'!");
        }

        // add the result
        result.add(actionDefinitionUrl);
      }
    }

    // return result
    return result;
  }

  /**
   * <p>
   * Instantiate all defined actions.
   * </p>
   *
   * @param bundle
   *          the bundle
   * @param container
   *          the specification container
   */
  private ActionGroupElementSpecificationContainer instantiateActions(Bundle bundle,
      ActionGroupElementSpecificationContainer container) {

    // the action specifications
    List<ActionSpecification> actionSpecifications = container.getActionSpecifications();

    // iterate over all action specifications
    for (ActionSpecification spec : actionSpecifications) {

      // get the class name
      String className = spec.getActionClass();

      // try to instantiate the class name
      try {
        // load the class
        Class<?> actionClass = bundle.loadClass(className);

        // check is class is assignable from class Action
        if (!Action.class.isAssignableFrom(actionClass)) {
          throw new RuntimeException("The specified class '" + className + "' does not implement the interface '"
              + Action.class.getName() + "'!");
        }

        // create a new instance
        Action action = (Action) actionClass.newInstance();

        // set bundle context
        if (action instanceof BundleContextAware) {
          ((BundleContextAware) action).setBundleContext(bundle.getBundleContext());
        }

        // initialize
        if (action instanceof Lifecycle) {
          ((Lifecycle) action).initialise();
        }

        // add the action
        _actionContributionAdmin.addAction(spec.getId(), spec.getActionGroupId(), spec.getLabel(), spec.getShortcut(),
            action);

        spec.setAction(action);

      } catch (ClassNotFoundException e) {
        throw new RuntimeException("The specified class '" + className + "' could not be found!", e);
      } catch (InstantiationException e) {
        throw new RuntimeException("The specified class '" + className + "' could not be instantiated!", e);
      } catch (IllegalAccessException e) {
        throw new RuntimeException("The specified class '" + className + "' could not be instantiated!", e);
      }
    }
    // return the action specification
    return container;
  }
}