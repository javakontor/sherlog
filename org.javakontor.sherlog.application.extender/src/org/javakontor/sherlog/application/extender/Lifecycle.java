package org.javakontor.sherlog.application.extender;

import org.javakontor.sherlog.application.action.Action;

/**
 * <p>
 * Lifecycle interface for the usage with the application (action) extender.
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface Lifecycle {

  /**
   * <p>
   * Initialise the {@link Action}.
   * </p>
   */
  public void initialise();

  /**
   * <p>
   * Dispose the {@link Action}.
   * </p>
   */
  public void dispose();
}
