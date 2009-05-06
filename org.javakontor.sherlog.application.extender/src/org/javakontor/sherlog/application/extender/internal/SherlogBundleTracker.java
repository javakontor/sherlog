package org.javakontor.sherlog.application.extender.internal;

import java.io.IOException;
import java.net.URL;
import java.util.Dictionary;
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

  private static final String     SHERLOG_ACTION = "Sherlog-Action";

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

    _actionContributionAdmin = actionContributionAdmin;
  }

  /**
   * @see org.osgi.util.tracker.BundleTracker#addingBundle(org.osgi.framework.Bundle, org.osgi.framework.BundleEvent)
   */
  @Override
  public Object addingBundle(Bundle bundle, BundleEvent event) {

    List<URL> actionDefintions = getActionDefinitions(bundle);

    if (!getActionDefinitions(bundle).isEmpty()) {

      for (URL url : actionDefintions) {
        ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
        try {
          ActionGroupElementSpecificationContainer actionDescription = mapper.readValue(url.openStream(),
              ActionGroupElementSpecificationContainer.class);

          instantiateActions(bundle, actionDescription);

        } catch (JsonParseException e) {
          e.printStackTrace();
        } catch (JsonMappingException e) {
          e.printStackTrace();
        } catch (IOException e) {
          e.printStackTrace();
        }
      }
    }
    return super.addingBundle(bundle, event);
  }

  private void instantiateActions(Bundle bundle, ActionGroupElementSpecificationContainer container) {

    System.err.println("instantiateActions");

    List<ActionSpecification> actionSpecifications = container.getActionSpecifications();

    for (ActionSpecification spec : actionSpecifications) {
      String className = spec.getActionClass();
      try {
        Class<?> actionClass = bundle.loadClass(className);

        if (!Action.class.isAssignableFrom(actionClass)) {
          // TODO
        }

        Action action = (Action) actionClass.newInstance();

        if (action instanceof BundleContextAware) {
          ((BundleContextAware) action).setBundleContext(bundle.getBundleContext());
        }

        if (action instanceof Lifecycle) {
          ((Lifecycle) action).initialise();
        }

        _actionContributionAdmin.addAction(spec.getId(), spec.getActionGroupId(), spec.getLabel(), spec.getLabel(),
            action);

      } catch (ClassNotFoundException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (InstantiationException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

  }

  /**
   * @see org.osgi.util.tracker.BundleTracker#removedBundle(org.osgi.framework.Bundle, org.osgi.framework.BundleEvent,
   *      java.lang.Object)
   */
  @Override
  public void removedBundle(Bundle bundle, BundleEvent event, Object object) {
    super.removedBundle(bundle, event, object);
  }

  private List<URL> getActionDefinitions(Bundle bundle) {

    List<URL> result = new LinkedList<URL>();

    @SuppressWarnings("unchecked")
    Dictionary<String, String> headers = bundle.getHeaders();

    String actionDefinitionList = headers.get(SHERLOG_ACTION);

    if (actionDefinitionList != null) {
      String[] actionDefinitions = actionDefinitionList.split(",");

      for (String actionDefinition : actionDefinitions) {
        URL actionDefinitionUrl = bundle.getEntry(actionDefinition);
        if (actionDefinitionUrl == null) {
          throw new RuntimeException("TODO");
        }
        result.add(actionDefinitionUrl);
      }
    }

    return result;
  }
}