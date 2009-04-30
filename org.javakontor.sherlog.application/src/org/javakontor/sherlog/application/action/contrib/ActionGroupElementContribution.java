package org.javakontor.sherlog.application.action.contrib;

/**
 * <p>
 * An {@link ActionGroupElementContribution} is an element that can be inserted into an {@link ActionGroupContribution}.
 * Currently there exist two sub-interfaces of this interface: {@link ActionGroupContribution} and
 * {@link ActionContribution}.
 * </p>
 * <p>
 * To contribute an {@link ActionGroupElementContribution} you have to register an instance of type
 * {@link ActionGroupContribution} or {@link ActionContribution} as an OSGi service.
 * </p>
 * 
 * @see org.javakontor.sherlog.application.action.contrib.ActionContribution
 * @see org.javakontor.sherlog.application.action.contrib.ActionGroupContribution
 */
public interface ActionGroupElementContribution {

  /**
   * <p>
   * Returns the unique identifier of this {@link ActionGroupElementContribution}.
   * </p>
   * 
   * @return the unique identifier of this {@link ActionGroupElementContribution}.
   */
  public String getId();

  /**
   * <p>
   * Returns the targetActionGroupId in the format actionGroupId(position) where (position) is optional
   * </p>
   * 
   * @return
   */
  public String getTargetActionGroupId();

  /**
   * <p>
   * Returns the label for this {@link ActionGroupElementContribution}.
   * </p>
   * 
   * @return the label for this {@link ActionGroupElementContribution}.
   */
  public String getLabel();
}
