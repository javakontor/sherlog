package org.javakontor.sherlog.application.action.impl;

import java.util.Properties;

import org.javakontor.sherlog.application.action.ActionGroup;
import org.javakontor.sherlog.application.action.ActionGroupType;

/**
 * <p>
 * </p>
 * 
 * @author Wuetherich-extern
 * 
 */
public class DefaultActionGroup extends AbstractActionGroupElement implements ActionGroup {

  /** - */
  private final ActionGroupType _type;

  /**
   * Creates a {@link ActionGroupType#simple} ActionGroup with the specified parametes
   * 
   * @param id
   *          The actionGroupId - must be unique within the application
   * @param targetActionGroupId -
   *          the id of the parent ActionGroup
   * @param label
   *          The label or null to create a "flat" ActionGroup
   */
  public DefaultActionGroup(String id, String targetActionGroupId, String label) {
    this(ActionGroupType.simple, id, targetActionGroupId, label);
  }

  public DefaultActionGroup(ActionGroupType type, String id, String targetActionGroupId, String label) {
    this(type, ActionGroupElementServiceHelper.createServiceProperties(id, targetActionGroupId, label));
  }

  public DefaultActionGroup(ActionGroupType type, Properties serviceProperties) {
    super(serviceProperties);
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
