package org.javakontor.sherlog.core.filter;

import java.util.EventListener;

/**
 * <p>
 * The listener interface for receiving log event filter change events.
 * </p>
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface LogEventFilterListener extends EventListener {

  /**
   * <p>
   * Invoked when the {@link LogEventFilter} has changed.
   * </p>
   *
   * @param event
   *          the {@link LogEventFilterChangeEvent}.
   */
  public void filterChanged(LogEventFilterChangeEvent event);
}
