package org.javakontor.sherlog.application.action.impl;

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

  public static ServiceRegistration registerActionGroup(final BundleContext context, final ActionGroup actionGroup) {

    final String[] clazzes = new String[] { ActionGroup.class.getName(), ActionGroupElement.class.getName() };

    return context.registerService(clazzes, actionGroup, null);
  }

  public static ServiceRegistration registerAction(final BundleContext context, final Action action) {

    final String[] clazzes = new String[] { Action.class.getName(), ActionGroupElement.class.getName() };

    if (getLogger().isDebugEnabled()) {
      getLogger().debug(" * registerAction: " + action.getId() + " -> " + action.getTargetActionGroupId());
    }
    return context.registerService(clazzes, action, null);
  }

}
