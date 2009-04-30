package org.javakontor.sherlog.application.action.contrib;

import org.javakontor.sherlog.application.action.ActionAdmin.ActionGroupType;

/**
 * <p>
 * </p>
 * 
 * @author Wuetherich-extern
 * 
 */
public class DefaultActionGroup extends AbstractActionGroupElement implements ActionGroupContribution {

  /** - */
  private final ActionGroupType _type;

  /**
   * Creates a {@link ActionGroupType#simple} ActionGroup with the specified parametes
   * 
   * @param id
   *          The actionGroupId - must be unique within the application
   * @param targetActionGroupId
   *          - the id of the parent ActionGroup
   * @param label
   *          The label or null to create a "flat" ActionGroup
   */
  public DefaultActionGroup(final String id, final String targetActionGroupId, final String label) {
    this(ActionGroupType.simple, id, targetActionGroupId, label);
  }

  public DefaultActionGroup(final ActionGroupType type, final String id, final String targetActionGroupId,
      final String label) {

    super(id, targetActionGroupId, label);

    this._type = type;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.javakontor.sherlog.application.action.ActionGroup#getType()
   */
  public ActionGroupType getType() {
    return this._type;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "[DefaultActionGroup, id: " + getId() + ", type: " + getType() + "]";
  }

}
