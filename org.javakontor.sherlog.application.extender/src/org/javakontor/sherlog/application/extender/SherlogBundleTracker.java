package org.javakontor.sherlog.application.extender;

import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.javakontor.sherlog.application.action.Action;
import org.javakontor.sherlog.application.action.ActionGroup;
import org.javakontor.sherlog.application.action.ActionSetManager;
import org.javakontor.sherlog.util.Assert;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.util.tracker.BundleTracker;

/**
 * <p>
 * Implements a custom bundle tracker that instantiates and registers {@link Action Actions} and action groups
 * {@link ActionGroup ActionGroups} based on a JSON description.
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class SherlogBundleTracker extends BundleTracker {

  private static final String META_INF_SHERLOG = "META-INF/sherlog";

  /** the action set manager */
  private ActionSetManager    _actionSetManager;

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
  public SherlogBundleTracker(BundleContext context, ActionSetManager actionSetManager) {
    super(context, Bundle.ACTIVE | Bundle.STARTING, null);

    Assert.notNull("Parameter actionSetManager has to be set!", actionSetManager);

    _actionSetManager = actionSetManager;
  }

  /**
   * @see org.osgi.util.tracker.BundleTracker#addingBundle(org.osgi.framework.Bundle, org.osgi.framework.BundleEvent)
   */
  @Override
  public Object addingBundle(Bundle bundle, BundleEvent event) {
    if (isSherlogJsonBundle(bundle)) {

      Enumeration<String> enumeration = bundle.getEntryPaths(META_INF_SHERLOG);
      if (enumeration != null) {
        while (enumeration.hasMoreElements()) {
          String string = (String) enumeration.nextElement();
          URL url = bundle.getEntry(string);
          ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally
          try {
            ActionDescription actionDescription = mapper.readValue(url.openStream(), ActionDescription.class);
            System.err.println(actionDescription);
          } catch (JsonParseException e) {
            e.printStackTrace();
          } catch (JsonMappingException e) {
            e.printStackTrace();
          } catch (IOException e) {
            e.printStackTrace();
          }
        }
      }
    }
    return super.addingBundle(bundle, event);
  }

  /**
   * @see org.osgi.util.tracker.BundleTracker#removedBundle(org.osgi.framework.Bundle, org.osgi.framework.BundleEvent,
   *      java.lang.Object)
   */
  @Override
  public void removedBundle(Bundle bundle, BundleEvent event, Object object) {
    super.removedBundle(bundle, event, object);
  }

  /**
   * <p>
   * </p>
   *
   * @param bundle
   * @return
   */
  private boolean isSherlogJsonBundle(Bundle bundle) {
    Enumeration<String> enumeration = bundle.getEntryPaths(META_INF_SHERLOG);
    if (enumeration != null) {
      while (enumeration.hasMoreElements()) {
        String string = (String) enumeration.nextElement();
      }
      return true;
    } else {
      return false;
    }
  }
}