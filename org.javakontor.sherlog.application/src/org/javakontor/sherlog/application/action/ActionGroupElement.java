package org.javakontor.sherlog.application.action;


/**
 * <p>
 * An {@link ActionGroupElement} is an element that can be inserted into an {@link ActionGroup}. Currently there exist
 * two sub-interfaces of this interface: {@link ActionGroup} and {@link Action}.
 * </p>
 * <p>
 * To contribute an {@link ActionGroupElement} you have to register an instance of type {@link ActionGroup} or
 * {@link Action} as an OSGi service.
 * </p>
 * 
 * @see org.javakontor.sherlog.application.action.Action
 * @see org.javakontor.sherlog.application.action.ActionGroup
 */
public interface ActionGroupElement extends IPropertyChangeSupport {

  public static final String LABEL                  = "actionGroupElement.Label";

  public static final String ID                     = "actionGroupElement.Id";

  public static final String TARGET_ACTION_GROUP_ID = "actionGroupElement.targetActionGroupId";

  /**
   * <p>
   * Returns the unique identifier of this {@link ActionGroupElement}.
   * </p>
   * 
   * @return the unique identifier of this {@link ActionGroupElement}.
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
   * Returns the label for this {@link ActionGroupElement}.
   * </p>
   * 
   * @return the label for this {@link ActionGroupElement}.
   */
  public String getLabel();
}
