package org.javakontor.sherlog.application.action.impl;

import org.javakontor.sherlog.application.action.Action;

/**
 * <p>
 * Abstract implementation of interface {@link Action}.
 * </p>
 */
public abstract class AbstractAction extends AbstractActionGroupElement implements Action {

  /** the name of the 'enabled' property */
  public static final String ENABLED_PROPERTY = "enabled";

  /** the 'enabled' property */
  private boolean            _enabled         = true;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractAction}.
   * </p>
   * 
   * @param actionId
   *          the identifier of this action
   * @param targetActionGroupId
   *          the identifier of the target action group
   * @param label
   *          the label of the action
   */
  public AbstractAction(final String actionId, final String targetActionGroupId, final String label) {
    super(actionId, targetActionGroupId, label);
  }

  /**
   * @see org.javakontor.sherlog.application.action.Action#isEnabled()
   */
  public boolean isEnabled() {
    return this._enabled;
  }

  /**
   * <p>
   * Overwrite to add default shortcut
   * </p>
   * 
   * @see org.javakontor.sherlog.application.action.Action#getDefaultShortcut()
   */
  public String getDefaultShortcut() {
    return null;
  }

  /**
   * <p>
   * Sets the 'enabled' property of the action.
   * </p>
   * 
   * @param enabled
   */
  public void setEnabled(final boolean enabled) {
    final Boolean oldValue = isEnabled();
    this._enabled = enabled;
    firePropertyChangeEvent(ENABLED_PROPERTY, oldValue, this._enabled);
  }
}
