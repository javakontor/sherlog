package org.javakontor.sherlog.application.action;

/**
 * <p>
 * Defines an {@link Action} that should be contributed to the UI.
 * </p>
 * <p>
 * To contribute an action, you have to register an instance of type {@link Action} as an OSGi service:
 * 
 * <pre><code>
 * // create instance of type action
 * MyAction action = ...
 * 
 * // initialize service properties
 * Properties actionServiceProperties = new Properties();
 * properties.setProperty(ActionGroupElement.ID, &quot;my.action.id&quot;);
 * properties.setProperty(ActionGroupElement.TARGET_ACTION_GROUP_ID, &quot;my.target.actiongroup.id&quot;);
 * 
 * // register the service
 * bundleContext.registerService(new String[] { Action.class.getName(), ActionGroupElement.class.getName() }, action, actionServiceProperties);
 * </code></pre>
 * 
 * </p>
 */
public interface Action extends ActionGroupElement {

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
  public boolean isEnabled();

  /**
   * <p>
   * </p>
   */
  public void execute();
}
