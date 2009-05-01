package org.javakontor.sherlog.application.extender;

import java.util.LinkedList;
import java.util.List;

import org.javakontor.sherlog.application.action.ActionContribution;
import org.javakontor.sherlog.application.action.ActionGroupContribution;

/**
 * <p>
 * Represents a action description.
 * </p>
 * <p>
 *
 * <code><pre>
 * {
 *   &quot;actions&quot; : [ &quot;&lt;full-qualified-action-class-name&gt;&quot;, &quot;&lt;full-qualified-action-class-name&gt;&quot; ],
 *
 *   &quot;actionGroups&quot; : [ &quot;&lt;full-qualified-action-group-class-name&gt;&quot;, &quot;&lt;full-qualified-action-group-class-name&gt;&quot; ]
 * }
 * </pre></code>
 *
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 *
 */
public class ActionDescription {

  /** list of all defined actions */
  private List<String> _actionNames;

  /** list of all defined action groups */
  private List<String> _actionGroupNames;

  /** list of all defined actions */
  private List<ActionContribution> _actions;

  /** list of all defined action groups */
  private List<ActionGroupContribution> _actionGroups;

  /**
   * <p>
   * Creates a new instance of type {@link ActionDescription}.
   * </p>
   */
  public ActionDescription() {
    // the action list
    _actionNames = new LinkedList<String>();

    // the action group list
    _actionGroupNames = new LinkedList<String>();
  }

  /**
   * <p>
   * Returns the list of all actions.
   * </p>
   *
   * @return the list of all actions.
   */
  public List<String> getActionNames() {
    return _actionNames;
  }

  /**
   * <p>
   * Returns the list of all action groups.
   * </p>
   *
   * @return the list of all action groups.
   */
  public List<String> getActionGroupNames() {
    return _actionGroupNames;
  }

  /**
   * <p>
   * Returns the list of all actions.
   * </p>
   *
   * @return the list of all actions.
   */
  public List<ActionContribution> getActions() {
    return _actions;
  }

  /**
   * <p>
   * Returns the list of all action groups.
   * </p>
   *
   * @return the list of all action groups.
   */
  public List<ActionGroupContribution> getActionGroups() {
    return _actionGroups;
  }

  /**
   * <p>
   * Sets the action list.
   * </p>
   *
   * @param actions
   *          the action list.
   */
  void setActions(List<String> actions) {
    _actionNames = actions;
  }

  /**
   * <p>
   * Sets the action group list.
   * </p>
   *
   * @param actionGroups
   *          the action group list
   */
  void setActionGroups(List<String> actionGroups) {
    _actionGroupNames = actionGroups;
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("[ActionDescription:");
    buffer.append(" _actions: ");
    buffer.append(_actionNames);
    buffer.append(" _actionGroups: ");
    buffer.append(_actionGroupNames);
    buffer.append("]");
    return buffer.toString();
  }

}
