package org.javakontor.sherlog.application.extender.internal.model;

import java.util.LinkedList;
import java.util.List;


/**
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ActionGroupElementSpecificationContainer {

  /** list of all defined actions */
  private List<ActionSpecification>      _actionSpecifications;

  /** list of all defined action groups */
  private List<ActionGroupSpecification> _actionGroupSpecifications;

  /**
   * <p>
   * Creates a new instance of type {@link ActionGroupElementSpecificationContainer}.
   * </p>
   */
  public ActionGroupElementSpecificationContainer() {
    // the action list
    _actionSpecifications = new LinkedList<ActionSpecification>();

    // the action group list
    _actionGroupSpecifications = new LinkedList<ActionGroupSpecification>();
  }

  public void setActions(List<ActionSpecification> actionSpecifications) {
    setActionSpecifications(actionSpecifications);
  }

  public void setActionGroups(List<ActionGroupSpecification> actionGroupSpecifications) {
    setActionGroupSpecifications(actionGroupSpecifications);
  }

  public void setActionSpecifications(List<ActionSpecification> actionSpecifications) {
    _actionSpecifications = actionSpecifications;
  }

  public void setActionGroupSpecifications(List<ActionGroupSpecification> actionGroupSpecifications) {
    _actionGroupSpecifications = actionGroupSpecifications;
  }

  public List<ActionSpecification> getActionSpecifications() {
    return _actionSpecifications;
  }

  public List<ActionGroupSpecification> getActionGroupSpecifications() {
    return _actionGroupSpecifications;
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("[ActionDescription:");
    buffer.append(" _actions: ");
    buffer.append(_actionSpecifications);
    buffer.append(" _actionGroups: ");
    buffer.append(_actionGroupSpecifications);
    buffer.append("]");
    return buffer.toString();
  }
}
