package org.javakontor.sherlog.application.action.set;

import org.javakontor.sherlog.application.action.contrib.ActionContributionAdmin;

public interface ActionSetManager extends ActionContributionAdmin {

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
