package org.javakontor.sherlog.application.action;

import org.javakontor.sherlog.application.action.contrib.ActionContribution;

/**
 * <p>
 * A {@link ToggleAction} is a stateful {@link ActionContribution} that can be activated and deactivated.
 * </p>
 * <p>
 * This class <b>is intended<b> to be subclassed by clients.
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface ToggleAction extends Action {

  /** - */
  public final static String ACTIVE_PROPERTY = "active";

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
