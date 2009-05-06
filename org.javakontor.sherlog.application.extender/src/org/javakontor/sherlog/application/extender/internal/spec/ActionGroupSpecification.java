package org.javakontor.sherlog.application.extender.internal.spec;

import org.javakontor.sherlog.application.action.ActionGroupType;

public class ActionGroupSpecification extends ActionGroupElementSpecificationContainer {
  /** - */
  private String          _id;

  /** - */
  private String          _label;

  /** - */
  private String          _targetActionGroupId;

  private ActionGroupType _type;

  public String getId() {
    return _id;
  }

  public void setId(String id) {
    _id = id;
  }

  public String getLabel() {
    return _label;
  }

  public void setLabel(String label) {
    _label = label;
  }

  public String getActionGroupId() {
    return _targetActionGroupId;
  }

  public void setActionGroupId(String targetActionGroupId) {
    _targetActionGroupId = targetActionGroupId;
  }

  public ActionGroupType getType() {
    return _type;
  }

  public void setType(ActionGroupType type) {
    _type = type;
  }

  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("[ActionGroupSpecification:");/* Inaccessible getter for private field _actionNames */
    /* Inaccessible getter for private field _actionGroupNames */
    buffer.append(" _id: ");
    buffer.append(_id);
    buffer.append(" _label: ");
    buffer.append(_label);
    buffer.append(" _targetActionGroupId: ");
    buffer.append(_targetActionGroupId);
    buffer.append(" _type: ");
    buffer.append(_type);
    buffer.append("]");
    return buffer.toString();
  }

}
