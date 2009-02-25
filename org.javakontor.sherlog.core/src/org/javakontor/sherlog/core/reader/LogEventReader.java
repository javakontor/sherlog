package org.javakontor.sherlog.core.reader;

import org.javakontor.sherlog.core.LogEvent;
import org.javakontor.sherlog.core.filter.Filterable;

/**
 * <p>
 * Defines the interface to read {@link LogEvent LogEvents} from a given source.
 * </p>
 * 
 * @author Gerd Wuetherich (gerd@gerd-wuetherich.de)
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
