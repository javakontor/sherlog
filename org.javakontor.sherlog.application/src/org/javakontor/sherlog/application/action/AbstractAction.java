package org.javakontor.sherlog.application.action;

import org.javakontor.sherlog.util.AbstractPropertyChangeSupport;

/**
 * <p>
 * Abstract implementation of the {@link Action} interface.
 * </p>
 * <p>
 * This class <b>is intended<b> to be subclassed by clients.
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractAction extends AbstractPropertyChangeSupport implements Action {

  /** the 'enabled' property */
  private boolean _enabled = true;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractAction}.
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
    // save old value
    final Boolean oldValue = isEnabled();
    // set new value
    this._enabled = enabled;
    // fire property change
    firePropertyChangeEvent(ENABLED_PROPERTY, oldValue, this._enabled);
  }
}
