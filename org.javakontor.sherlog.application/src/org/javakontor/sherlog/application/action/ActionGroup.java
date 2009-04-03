package org.javakontor.sherlog.application.action;

/**
 * <p>
 * An {@link ActionGroup} contains one or more {@link ActionGroupElement ActionGroupElements}.
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
  public ActionGroupType getType();;

}
