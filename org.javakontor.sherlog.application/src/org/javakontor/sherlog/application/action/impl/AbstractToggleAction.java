package org.javakontor.sherlog.application.action.impl;

import org.javakontor.sherlog.application.action.ToggleAction;

/**
 * <p>
 * </p>
 */
public abstract class AbstractToggleAction extends AbstractAction implements ToggleAction {

  /** - */
  public final static String ACTIVE_PROPERTY = "active";

  /** - */
  private boolean            _active         = false;

  /**
   * Creates a new AbstractToggleAction with the specified parameters
   * 
   * @param actionId
   * @param targetActionGroupId
   * @param label
   */
  public AbstractToggleAction(final String actionId, final String targetActionGroupId, final String label) {
    super(actionId, targetActionGroupId, label);
  }

  /**
   * @see org.javakontor.sherlog.application.action.ToggleAction#isActive()
   */
  public boolean isActive() {
    return this._active;
  }

  /**
   * @see org.javakontor.sherlog.application.action.ToggleAction#setActive(boolean)
   */
  public void setActive(final boolean active) {
    final boolean oldValue = this._active;
    this._active = active;
    firePropertyChangeEvent(ACTIVE_PROPERTY, oldValue, this._active);
  }
}
