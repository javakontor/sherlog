package org.javakontor.sherlog.application.action.impl;

import org.javakontor.sherlog.application.action.ActionGroupElement;

/**
 * <p>
 * Abstract implementation of interface {@link ActionGroupElement}.
 * </p>
 */
public abstract class AbstractActionGroupElement extends AbstractPropertyChangeSupport implements ActionGroupElement {

  /** - */
  private final String _id;

  /** - */
  private final String _label;

  /** - */
  private final String _targetActionGroupId;

  /**
   * @param id
   * @param targetActionGroupId
   * @param label
   */
  public AbstractActionGroupElement(final String id, final String targetActionGroupId, final String label) {
    super();
    this._id = id;
    this._label = label;
    this._targetActionGroupId = targetActionGroupId;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.javakontor.sherlog.application.action.ActionGroupElement#getId()
   */
  public String getId() {
    return this._id;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.javakontor.sherlog.application.action.ActionGroupElement#getLabel()
   */
  public String getLabel() {
    return this._label;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.javakontor.sherlog.application.action.ActionGroupElement#getTargetActionGroupId()
   */
  public String getTargetActionGroupId() {
    return this._targetActionGroupId;
  }
}
