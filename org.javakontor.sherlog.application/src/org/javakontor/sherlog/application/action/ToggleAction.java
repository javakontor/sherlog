package org.javakontor.sherlog.application.action;

/**
 * <p>
 * A {@link ToggleAction} is a stateful {@link Action} that can be activated and deactivated.
 * </p>
 */
public interface ToggleAction extends Action {

  /**
   * <p>
   * Returns <code>true</code>, if the {@link ToggleAction} is active, <code>false</code> otherwise.
   * </p>
   * 
   * @return
   */
  public boolean isActive();

  /**
   * <p>
   * Sets the state of this {@link ToggleAction}.
   * </p>
   * 
   * @param active
   */
  public void setActive(boolean active);
}
