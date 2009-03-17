package org.javakontor.sherlog.application.action.impl;

import java.util.Properties;

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
   * <p>
   * </p>
   * 
   * @param serviceProperties
   */
  public AbstractToggleAction(Properties serviceProperties) {
    super(serviceProperties);
  }

  /**
   * Creates a new AbstractToggleAction with the specified parameters
   * 
   * @param actionId
   * @param targetActionGroupId
   * @param label
   */
  public AbstractToggleAction(String actionId, String targetActionGroupId, String label) {
    super(actionId, targetActionGroupId, label);
    // TODO Auto-generated constructor stub
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
  public void setActive(boolean active) {
    boolean oldValue = this._active;
    this._active = active;
    firePropertyChangeEvent(ACTIVE_PROPERTY, oldValue, this._active);
  }
}
