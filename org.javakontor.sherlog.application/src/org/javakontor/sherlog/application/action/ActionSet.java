package org.javakontor.sherlog.application.action;

/**
 * Holds all ActionGroupElements that have been registered (either via the ServiceRegistry or from
 * StaticActionGroupProviders or StaticActionProviders) to a specific ActionSet
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public interface ActionSet {
  /**
   * Adds the specified Action to this action set.
   * 
   * <p>
   * An action can be registered even if the targetActionGroup of the action has not been registered yet
   * 
   * @param action
   */
  public void addAction(Action action);

  /**
   * Adds the specified ActionGroup to this ActionSet.
   * 
   * <p>
   * An ActionGroup can be registered even if the targetActionGroup of the ActionGroup has not been registered yet
   * 
   * @param actionGroup
   *          the ActionGroup to add
   */
  public void addActionGroup(ActionGroup actionGroup);

  /**
   * Removed the specified ActionGroup from this ActionSet.
   * 
   * @param actionGroup
   */
  public void removeActionGroup(ActionGroup actionGroup);

  /**
   * Removes the specified Action from this ActionSet
   * 
   * @param action
   */
  public void removeAction(Action action);

  public void addActionSetChangeListener(ActionSetChangeListener actionSetChangeListener);

  public void removeActionSetChangeListener(ActionSetChangeListener actionSetChangeListener);

  /**
   * TODO should not be public API
   * 
   * @param id
   * @return
   */
  public ActionGroupContent getActionGroupContent(String id);

  /**
   * TODO should not be public API
   * 
   * @return
   */
  public ActionGroupContent getRootActionGroupContent();
}
