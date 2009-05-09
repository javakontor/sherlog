package org.javakontor.sherlog.application.extender.internal.model;

import java.util.LinkedList;
import java.util.List;

import org.osgi.framework.Bundle;

/**
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ActionDefinition extends AbstractBundleAwareEntry {

  /** list of all defined actions */
  private List<ActionEntry>      _actionSpecifications;

  /** list of all defined action groups */
  private List<ActionGroupEntry> _actionGroupSpecifications;

  /**
   * <p>
   * Creates a new instance of type {@link ActionDefinition}.
   * </p>
   */
  public ActionDefinition() {
    // the action list
    _actionSpecifications = new LinkedList<ActionEntry>();

    // the action group list
    _actionGroupSpecifications = new LinkedList<ActionGroupEntry>();
  }

  public void setActions(List<ActionEntry> actionSpecifications) {
    setActionSpecifications(actionSpecifications);
  }

  public void setActionGroups(List<ActionGroupEntry> actionGroupSpecifications) {
    setActionGroupSpecifications(actionGroupSpecifications);
  }

  public void setActionSpecifications(List<ActionEntry> actionSpecifications) {
    _actionSpecifications = actionSpecifications;
  }

  public void setActionGroupSpecifications(List<ActionGroupEntry> actionGroupSpecifications) {
    _actionGroupSpecifications = actionGroupSpecifications;
  }

  @Override
  public void setBundle(Bundle bundle) {
    super.setBundle(bundle);

    for (ActionEntry actionEntry : _actionSpecifications) {
      actionEntry.setBundle(bundle);
    }

    for (ActionGroupEntry actionGroupEntry : _actionGroupSpecifications) {
      actionGroupEntry.setBundle(bundle);
    }
  }

  public List<ActionEntry> getActionEntry() {
    return _actionSpecifications;
  }

  public List<ActionGroupEntry> getActionGroupSpecifications() {
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
