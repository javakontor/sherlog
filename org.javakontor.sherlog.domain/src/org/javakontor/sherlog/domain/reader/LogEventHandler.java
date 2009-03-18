package org.javakontor.sherlog.domain.reader;

import org.javakontor.sherlog.domain.LogEvent;

/**
 * <p>
 * The {@link LogEventHandler} can be used to receive call backs from a {@link LogEventReader}. Instances of type
 * {@link LogEventHandler} can be registered by calling {@link LogEventReader#addLogEventHandler(LogEventHandler)}.
 * </p>
 */
public interface LogEventHandler {

  /**
   * <p>
   * Invoked before the {@link LogEventReader} starts to read {@link LogEvent LogEvents}.
   * </p>
   */
  public void initialize();

  /**
   * <p>
   * Invoked for each received {@link LogEvent}.
   * </p>
   *
   * @param event
   *          the received {@link LogEvent}.
   */
  public void handle(final LogEvent event);

  /**
   * <p>
   * Invoked if an exception is thrown.
   * </p>
   *
   * @param exception
   */
  public void handleException(final Exception exception);

  /**
   * <p>
   * Invoked if the execution of the {@link LogEventReader} was canceled.
   * </p>
   *
   */
  public void handleCancellation();

  /**
   * <p>
   * Invoked after the {@link LogEventReader} finished to read {@link LogEvent LogEvents}.
   * </p>
   *
   */
  public void dispose();
}
