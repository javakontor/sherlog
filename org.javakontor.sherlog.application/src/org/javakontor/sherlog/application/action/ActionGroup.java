package org.javakontor.sherlog.application.action;

/**
 * <p>
 * {@link ActionGroup ActionGroups} contain {@link Action Actions} and/or nested {@link ActionGroup ActionGroups}.
 * </p>
 */
public interface ActionGroup extends ActionGroupElement {

  /**
   * <p>
   * Returns the {@link ActionGroupType}.
   * </p>
   * 
   * @return the {@link ActionGroupType}.
   */
  public ActionGroupType getType();

}
