package org.javakontor.sherlog.core.filter;

import java.util.EventObject;

/**
 * <p>
 * Sent if a {@link LogEventFilter} has changed.
 * </p>
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class LogEventFilterChangeEvent extends EventObject {

  /** the serialVersionUID */
  private static final long serialVersionUID = 1L;

  /**
   * <p>
   * Creates a new instance of type {@link LogEventFilterChangeEvent}.
   * </p>
   *
   * @param source
   *          the source
   */
  public LogEventFilterChangeEvent(Object source) {
    super(source);
  }
}
