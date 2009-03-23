package org.javakontor.sherlog.application.action;

import java.util.Collection;

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
   * Returns the ActionGroupContent for the ActionGroup with the specified id.
   * 
   * <p>
   * This method returns <tt>null</tt> if no ActionGroupContent with the specified id has been registered or the
   * ActionGroupContent has no ActionGroup set (in case Actions or ActionGroups have been registered for the
   * ActionGroup, but the ActionGroup itself has not been registered)
   * 
   * @param actionGroupId
   *          the id of the ActionGroup
   * @return the elements of the actiongroup or null if no ActionGroup has been registered for the id
   */
  public Collection<ActionGroupElement> getActionGroupContent(String id);

  /**
   * return the rootActionGroupContent. never null.
   * 
   * @return
   */
  public Collection<ActionGroupElement> getRootActionGroupContent();
}
