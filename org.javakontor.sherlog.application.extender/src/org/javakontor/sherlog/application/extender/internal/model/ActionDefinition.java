package org.javakontor.sherlog.application.extender.internal.model;

import java.util.LinkedList;
import java.util.List;

import org.osgi.framework.Bundle;

/**
 * <p>
 * Encapsulates the action definitions defined in a JSON file.
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ActionDefinition extends AbstractBundleAwareEntry {

  /** list of all defined actions */
  private List<ActionEntry>      _actionEntries;

  /** list of all defined action groups */
  private List<ActionGroupEntry> _actionGroupEntries;

  /**
   * <p>
   * Creates a new instance of type {@link ActionDefinition}.
   * </p>
   */
  public ActionDefinition() {
    // the action list
    _actionEntries = new LinkedList<ActionEntry>();

    // the action group list
    _actionGroupEntries = new LinkedList<ActionGroupEntry>();
  }

  /**
   * <p>
   * Sets the list with all {@link ActionEntry ActionEntries}.
   * </p>
   *
   * @param actions
   */
  public void setActions(List<ActionEntry> actions) {
    _actionEntries = actions;
  }

  /**
   * <p>
   * Returns the list with all {@link ActionEntry ActionEntries}.
   * </p>
   *
   * @return the list with all {@link ActionEntry ActionEntries}.
   */
  public List<ActionEntry> getActionEntry() {
    return _actionEntries;
  }

  /**
   * <p>
   * Sets the list with all {@link ActionGroupEntry ActionGroupEntries}.
   * </p>
   *
   * @param actionGroupEntries the list with all {@link ActionGroupEntry ActionGroupEntries}.
   */
  public void setActionGroups(List<ActionGroupEntry> actionGroupEntries) {
    _actionGroupEntries = actionGroupEntries;
  }

  /**
   * @see org.javakontor.sherlog.application.extender.internal.model.AbstractBundleAwareEntry#setBundle(org.osgi.framework.Bundle)
   */
  @Override
  public void setBundle(Bundle bundle) {
    // call super
    super.setBundle(bundle);

    // set bundle for action entries
    for (ActionEntry actionEntry : _actionEntries) {
      actionEntry.setBundle(bundle);
    }

    // set bundle for action group entries
    for (ActionGroupEntry actionGroupEntry : _actionGroupEntries) {
      actionGroupEntry.setBundle(bundle);
    }
  }



  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("[ActionDescription:");
    buffer.append(" _actions: ");
    buffer.append(_actionEntries);
    buffer.append(" _actionGroups: ");
    buffer.append(_actionGroupEntries);
    buffer.append("]");
    return buffer.toString();
  }
}
