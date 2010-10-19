package org.javakontor.sherlog.domain.reader;

import org.javakontor.sherlog.domain.LogEvent;
import org.javakontor.sherlog.domain.filter.Filterable;

/**
 * <p>
 * Defines the interface to read {@link LogEvent LogEvents} from a given source.
 * </p>
 * <p>
 * LogEvents that have been read from the source and other events a passed to all registered {@link LogEventHandler
 * LogEventHandlers}.
 * 
 * <p>
 * A LogEventReader implementation should run asynchronously in background.
 * 
 * @see LogEventHandler
 * 
 * @author Gerd Wuetherich (gerd@gerd-wuetherich.de)
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public interface LogEventReader extends Filterable {

  /**
   * <p>
   * Starts the {@link LogEventReader}.
   * </p>
   */
  public void start();

  /**
   * <p>
   * Stops the {@link LogEventReader}.
   * </p>
   */
  public void stop();

  /**
   * <p>
   * Returns <code>true</code> if the {@link LogEventReader} is running.
   * </p>
   * 
   * @return <code>true</code> if the {@link LogEventReader} is running, <code>false</code> otherwise.
   */
  public boolean isRunning();

  /**
   * <p>
   * Adds the specified {@link LogEventHandler}.
   * </p>
   * 
   * @param logEventHandler
   */
  public void addLogEventHandler(LogEventHandler logEventHandler);

  /**
   * <p>
   * Removes the specified {@link LogEventHandler}.
   * </p>
   * 
   * @param logEventHandler
   */
  public void removeLogEventHandler(LogEventHandler logEventHandler);
}
