package org.javakontor.sherlog.application.action.contrib;


public interface ActionSetManager {

  public void addAction(ActionContribution action);

  public void removeAction(ActionContribution action);

  public void addActionGroup(ActionGroupContribution actionGroup);

  public void removeActionGroup(ActionGroupContribution actionGroup);

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
