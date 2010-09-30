package org.javakontor.sherlog.application.extender.internal.model;

import java.util.LinkedList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.javakontor.sherlog.application.action.Action;

/**
 * <p>
 * Represents the specification of an action.
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ActionEntry extends AbstractBundleAwareEntry {

  /** the id */
  private String          _id;

  /** the action group id */
  private String          _actionGroupId;

  /** the label */
  private String          _label;

  /** the short cut */
  private String          _shortcut;

  /** the implementing class */
  private String          _actionClass;

  /** the action - set through the framework */
  private Action          _action;

  /** list of all services */
  private List<Reference> _serviceFilter = new LinkedList<Reference>();

  /**
   * <p>
   * Returns the id of the action.
   * </p>
   *
   * @return the id of the action.
   */
  public String getId() {
    return _id;
  }

  /**
   * <p>
   * Returns the id of the action.
   * </p>
   *
   * @param id
   */
  public void setId(String id) {
    _id = id;
  }

  /**
   * <p>
   * Returns the action group id.
   * </p>
   *
   * @return
   */
  public String getActionGroupId() {
    return _actionGroupId;
  }

  /**
   * <p>
   * Sets the action group id.
   * </p>
   *
   * @param actionGroupId
   */
  public void setActionGroupId(String actionGroupId) {
    _actionGroupId = actionGroupId;
  }

  /**
   * <p>
   * Sets the label.
   * </p>
   *
   * @return the label.
   */
  public String getLabel() {
    return _label;
  }

  /**
   * <p>
   * Sets the label.
   * </p>
   *
   * @param label
   */
  public void setLabel(String label) {
    _label = label;
  }

  /**
   * <p>
   * Returns the shortcut.
   * </p>
   *
   * @return the shortcut.
   */
  public String getShortcut() {
    return _shortcut;
  }

  /**
   * <p>
   * Sets the shortcut.
   * </p>
   *
   * @param shortcut
   */
  public void setShortcut(String shortcut) {
    _shortcut = shortcut;
  }

  /**
   * <p>
   * Returns the action class.
   * </p>
   *
   * @return the action class.
   */
  public String getActionClass() {
    return _actionClass;
  }

  public void setActionClass(String actionClass) {
    _actionClass = actionClass;
  }

  public List<Reference> getReferences() {
    return _serviceFilter;
  }

  public void setReferences(List<Reference> serviceFilter) {
    _serviceFilter = serviceFilter;
  }

  public boolean hasReferences() {
    return _serviceFilter != null && !_serviceFilter.isEmpty();
  }

  public Action getAction() {
    return _action;
  }

  @JsonIgnore
  public void setAction(Action action) {
    _action = action;
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("[ActionSpecification:");
    buffer.append(" _id: ");
    buffer.append(_id);
    buffer.append(" _actionGroupId: ");
    buffer.append(_actionGroupId);
    buffer.append(" _label: ");
    buffer.append(_label);
    buffer.append(" _shortcut: ");
    buffer.append(_shortcut);
    buffer.append(" _actionClass: ");
    buffer.append(_actionClass);
    buffer.append(" _action: ");
    buffer.append(_action);
    buffer.append("]");
    return buffer.toString();
  }

}
