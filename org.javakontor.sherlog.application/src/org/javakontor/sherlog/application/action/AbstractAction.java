package org.javakontor.sherlog.application.action;

import org.javakontor.sherlog.util.AbstractPropertyChangeSupport;


public abstract class AbstractAction extends AbstractPropertyChangeSupport implements Action {

  /** the 'enabled' property */
  private boolean _enabled = true;

  /**
   * <p>
   * </p>
   */
  public AbstractAction() {
    super();
  }

  /**
   * @see org.javakontor.sherlog.application.action.contrib.ActionContribution#isEnabled()
   */
  public boolean isEnabled() {
    return this._enabled;
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
