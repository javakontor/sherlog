package org.javakontor.sherlog.application.action.contrib;

import org.javakontor.sherlog.application.action.Action;

/**
 * <p>
 * Defines an {@link ActionContribution} that can be contributed to the UI.
 * </p>
 * <p>
 * To contribute an action, you have to register an instance of type {@link ActionContribution} as an OSGi service:
 * 
 * <pre>
 * &lt;code&gt;
 * // create instance of type action
 * MyAction action = ...
 * // register the service
 * bundleContext.registerService(new String[] { Action.class.getName(), ActionGroupElement.class.getName() }, action, null);
 * &lt;/code&gt;
 * </pre>
 * 
 * </p>
 */
public interface ActionContribution extends ActionGroupElementContribution {

  /**
   * Returns a default shortcut for this Action or null if the action should have no shortcut per default.
   * 
   * <p>
   * The shortcut must return a string that can be passed to the {@link javax.swing.KeyStroke#getKeyStroke()} method
   * 
   * <p>
   * In next releases it will be possible for users to re-configure the shortcut.
   * 
   * @return a default shortcut or null
   */
  public String getDefaultShortcut();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Action getAction();
}
