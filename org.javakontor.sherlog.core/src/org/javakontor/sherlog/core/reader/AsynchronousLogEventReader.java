package org.javakontor.sherlog.core.reader;

/**
 * <p>
 * Defines the interface for an asynchronous {@link LogEventReader}.
 * </p>
 *
 * @author Gerd Wuetherich (gerd@gerd-wuetherich.de)
 */
public interface AsynchronousLogEventReader extends LogEventReader {

  /**
   * <p>
   * Stops the {@link AsynchronousLogEventReader}.
   * </p>
   */
  public void stop();

  /**
   * <p>
   * Returns <code>true</code> if the {@link AsynchronousLogEventReader} is running.
   * </p>
   *
   * @return <code>true</code> if the {@link AsynchronousLogEventReader} is running, <code>false</code> otherwise.
   */
  public boolean isRunning();
}
