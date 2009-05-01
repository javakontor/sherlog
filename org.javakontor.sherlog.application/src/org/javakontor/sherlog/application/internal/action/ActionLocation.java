package org.javakontor.sherlog.application.internal.action;

import org.javakontor.sherlog.application.action.AbstractActionGroupElementContribution;
import org.javakontor.sherlog.application.action.ActionGroupElementContribution;
import org.javakontor.sherlog.util.Assert;

/**
 * Represents a location for an action
 * 
 * <p>
 * A location for an action consist of a targetGroupId and an optional position the specifies a relative order where to
 * place the ActionGroupElement inside the ActionGroup.
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class ActionLocation {

  /**
   * The target location as specified in the ActionGroupElement (not parsed)
   */
  private final String _rawString;

  /**
   * The system-wide unique actionRoot
   */
  private final String _actionRoot;

  /** The id of the ActionGroupElement */
  private final String _id;

  /**
   * The id of the target group without the position qualifier
   */
  private final String _targetGroupId;

  /**
   * The 'position' qualifier (first, before, after, last) or null
   */
  private final String _position;

  public ActionLocation(final String id, final String targetGroupId) {
    Assert.notNull(id);
    Assert.notNull(targetGroupId);
    this._rawString = targetGroupId;
    this._id = id;
    final TargetGroupIdParser parser = new TargetGroupIdParser(targetGroupId);
    this._actionRoot = parser.getActionRoot();
    this._targetGroupId = parser.getTargetGroupId();
    this._position = parser.getPosition();
  }

  public String getActionRoot() {
    return this._actionRoot;
  }

  /**
   * Constructs a new ActionLocation for the given {@link AbstractActionGroupElementContribution}
   * 
   * @param actionGroupElement
   */
  public ActionLocation(final ActionGroupElementContribution actionGroupElement) {
    this(actionGroupElement.getId(), actionGroupElement.getTargetActionGroupId());
  }

  /**
   * Returns true if the original targetGroupId contains a position qualifier
   * 
   * @return true if a position qualifier is set
   */
  public boolean hasPosition() {
    return (this._position != null);
  }

  /**
   * Returns the targetActionGroupId without the position
   * 
   * @return
   */
  public String getTargetActionGroupId() {
    return this._targetGroupId;
  }

  /**
   * Returns the position qualifier that is part of the orginal targetGroupId or null
   * 
   * @return
   */
  public String getPosition() {
    return this._position;
  }

  /**
   * Returns <tt>true</tt> if this ActionLocation represents a "first" location
   * 
   * @return
   */
  public boolean isFirst() {
    return "first".equals(this._position);
  }

  /**
   * Returns <tt>true</tt> if this ActionLocation represents a "last" location
   * 
   * @return
   */
  public boolean isLast() {
    return "last".equals(this._position);
  }

  /**
   * Returns <tt>true</tt> if this ActionLocation represents a "before" location
   * 
   * @return
   */
  public boolean isBefore() {
    return ((this._position != null) && this._position.startsWith("before:"));
  }

  /**
   * Returns <tt>true</tt> if this ActionLocation represents a "after" location
   * 
   * @return
   */
  public boolean isAfter() {
    return ((this._position != null) && this._position.startsWith("after:"));
  }

  /**
   * return the "before" position qualifier (an actionGroupId)
   * 
   * @throws An
   *           Exception if this ActionLocation doesn't have "before" position qualifier
   * @return the "before" position qualifier
   */
  public String getBefore() {
    return this._position.substring("before:".length());
  }

  /**
   * return the "after" position qualifier (an actionGroupId)
   * 
   * @throws An
   *           Exception if this ActionLocation doesn't have "before" position qualifier
   * @return the "before" position qualifier
   */
  public String getAfter() {
    return this._position.substring("after:".length());
  }

  //
  public String getId() {
    return this._id;
  }

  /**
   * Returns the full, original, location i.e the targetActionGroupId and the position qualifier (if any)
   * 
   * @return
   */
  public String getFullLocation() {
    return this._rawString;
  }

  @Override
  public String toString() {
    return "[id: " + this._id + ", target: " + this._targetGroupId + ", _position: " + this._position + "]";
  }

}