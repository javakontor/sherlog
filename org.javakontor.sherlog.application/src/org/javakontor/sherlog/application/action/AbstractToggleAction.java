package org.javakontor.sherlog.application.action;

/**
 * <p>
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractToggleAction extends AbstractAction implements ToggleAction {

  /** - */
  private boolean _active = false;

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
