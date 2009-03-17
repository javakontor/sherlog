package org.javakontor.sherlog.application.action.impl;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javakontor.sherlog.application.action.Action;
import org.javakontor.sherlog.application.action.ActionGroup;
import org.javakontor.sherlog.application.action.ActionGroupElement;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class ActionGroupElementServiceHelper {

  private static Log _log;

  public static Log getLogger() {
    if (_log == null) {
      _log = LogFactory.getLog(ActionGroupElementServiceHelper.class);
    }
    return _log;
  }

  private ActionGroupElementServiceHelper() {
    // no instances
  }

  public static Properties createServiceProperties(String id, String targetActionGroup, String label) {
    Properties properties = new Properties();
    properties.setProperty(ActionGroupElement.ID, id);
    properties.setProperty(ActionGroupElement.TARGET_ACTION_GROUP_ID, targetActionGroup);
    if (label != null) {
      properties.setProperty(ActionGroupElement.LABEL, label);
    }

    return properties;
  }

  public static String getLabel(Properties properties) {
    if (properties.containsKey(ActionGroupElement.LABEL)) {
      return properties.getProperty(ActionGroupElement.LABEL);
    }
    return null;
  }

  public static boolean hasLabel(Properties properties) {
    return (properties.containsKey(ActionGroupElement.LABEL));
  }

  public static String getTargetActionGroupId(Properties properties) {
    return properties.getProperty(ActionGroupElement.TARGET_ACTION_GROUP_ID);
  }

  public static String getId(Properties properties) {
    return properties.getProperty(ActionGroupElement.ID);
  }

  public static ServiceRegistration registerActionGroup(BundleContext context, ActionGroup actionGroup) {

    String[] clazzes = new String[] { ActionGroup.class.getName(), ActionGroupElement.class.getName() };

    return context.registerService(clazzes, actionGroup, null);
  }

  public static ServiceRegistration registerAction(BundleContext context, Action action) {

    String[] clazzes = new String[] { Action.class.getName(), ActionGroupElement.class.getName() };

    if (getLogger().isDebugEnabled()) {
      getLogger().debug(" * registerAction: " + action.getId() + " -> " + action.getTargetActionGroupId());
    }
    return context.registerService(clazzes, action, null);
  }

}
