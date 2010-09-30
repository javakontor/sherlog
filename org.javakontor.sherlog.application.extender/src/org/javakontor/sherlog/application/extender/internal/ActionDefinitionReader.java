package org.javakontor.sherlog.application.extender.internal;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.javakontor.sherlog.application.extender.internal.model.ActionDefinition;
import org.osgi.framework.Bundle;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public final class ActionDefinitionReader {

  /** the constant SHERLOG_ACTION */
  private static final String SHERLOG_ACTION_BUNDLE_MANIFEST_HEADER = "Sherlog-Actions";

  /** - */
  private static ObjectMapper _mapper;

  /**
   * <p>
   * </p>
   *
   * @param bundle
   * @return
   */
  public static List<ActionDefinition> readActionDefinitions(Bundle bundle) {

    // get the action definitions
    List<URL> actionDefintionURLs = getActionDefinitionURLs(bundle);

    // result list
    List<ActionDefinition> result = new LinkedList<ActionDefinition>();

    // return if null
    if (actionDefintionURLs.isEmpty()) {
      return result;
    }

    // Read action definitions
    for (URL url : actionDefintionURLs) {
      try {
        ActionDefinition actionDefinition = getObjectMapper().readValue(url.openStream(), ActionDefinition.class);
        actionDefinition.setBundle(bundle);
        result.add(actionDefinition);
      } catch (Throwable e) {
        e.printStackTrace();
      }
    }

    return result;
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
  private static List<URL> getActionDefinitionURLs(Bundle bundle) {

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
   * </p>
   *
   * @return
   */
  private static ObjectMapper getObjectMapper() {

    if (_mapper == null) {
      // create
      _mapper = new ObjectMapper();
    }

    return _mapper;
  }
}
