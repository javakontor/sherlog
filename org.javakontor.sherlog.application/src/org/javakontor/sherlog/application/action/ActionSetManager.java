package org.javakontor.sherlog.application.action;

public interface ActionSetManager {

  public void addAction(Action action);

  public void removeAction(Action action);

  public void addActionGroup(ActionGroup actionGroup);

  public void removeActionGroup(ActionGroup actionGroup);

  /**
   * Returns the {@link ActionSet} for the specified actionSetId.
   * 
   * <p>
   * If there is no ActionSet with this actionSetId registered, a new, empty, ActionSet will be created and returned.
   * 
   * @param actionSetId
   * @return The (existing or new) ActionSet. Never null.
   */
  public ActionSet getActionSet(String actionSetId);
}
