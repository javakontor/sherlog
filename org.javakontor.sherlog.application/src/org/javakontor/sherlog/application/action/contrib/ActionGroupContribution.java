package org.javakontor.sherlog.application.action.contrib;

import org.javakontor.sherlog.application.action.ActionAdmin.ActionGroupType;

/**
 * <p>
 * {@link ActionGroupContribution ActionGroups} contain {@link ActionContribution Actions} and/or nested
 * {@link ActionGroupContribution ActionGroups}.
 * </p>
 */
public interface ActionGroupContribution extends ActionGroupElementContribution {

  /**
   * <p>
   * Returns the {@link ActionGroupType}.
   * </p>
   * 
   * @return the {@link ActionGroupType}.
   */
  public ActionGroupType getType();
}
