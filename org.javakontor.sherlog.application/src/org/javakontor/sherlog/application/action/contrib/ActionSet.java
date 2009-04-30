package org.javakontor.sherlog.application.action.contrib;

import java.util.Collection;


/**
 * Holds all ActionGroupElements that have been registered (either via the ServiceRegistry or from
 * StaticActionGroupProviders or StaticActionProviders) to a specific ActionSet
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public interface ActionSet {

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
  public Collection<ActionGroupElementContribution> getActionGroupContent(String id);

  /**
   * return the rootActionGroupContent. never null.
   * 
   * @return
   */
  public Collection<ActionGroupElementContribution> getRootActionGroupContent();
}
