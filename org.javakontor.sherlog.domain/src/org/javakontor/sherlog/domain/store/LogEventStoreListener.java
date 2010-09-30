package org.javakontor.sherlog.domain.store;

import java.util.EventListener;

import org.javakontor.sherlog.domain.LogEvent;
import org.javakontor.sherlog.domain.filter.LogEventFilter;

/**
 * <p>
 * An instance of type {@link LogEventStoreListener} can be used to receive events if {@link LogEvent LogEvents} or
 * {@link LogEventFilter LogEventFilters} are added to or removed from the {@link LogEventStore}.
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface LogEventStoreListener extends EventListener {

  /**
   * <p>
   * Invoked when new the LogEventStore has changed.
   * </p>
   * 
   * <p>Examples for a change are: a LogEvent has been added, the LogEventStore has been reset or (re-)filtered
   * </p>
   * @param event
   *          a LogEventStoreEvent   
   */
  public void logEventStoreChanged(LogEventStoreEvent event);
}
