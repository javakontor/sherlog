package org.javakontor.sherlog.domain.reader;

import org.javakontor.sherlog.domain.LogEvent;

/**
 * <p>
 * An abstract adapter class for receiving log event reader events. The methods in this class are empty. This class
 * exists as convenience for creating log event handler objects.
 * </p>
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DefaultLogEventHandler implements LogEventHandler {

  /**
   * {@inheritDoc}
   */
  public void dispose() {
    // empty implementation...
  }

  /**
   * {@inheritDoc}
   */
  public void handle(LogEvent event) {
    // empty implementation...
  }

  /**
   * {@inheritDoc}
   */
  public void handleCancellation() {
    // empty implementation...
  }

  /**
   * {@inheritDoc}
   */
  public void handleException(Exception exception) {
    // empty implementation...
  }

  /**
   * {@inheritDoc}
   */
  public void initialize() {
    // empty implementation...
  }
}
