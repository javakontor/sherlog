package org.javakontor.sherlog.core.store;

import java.util.EventListener;

import org.javakontor.sherlog.core.LogEvent;
import org.javakontor.sherlog.core.filter.LogEventFilter;

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
   * Invoked when new log events are added to the {@link LogEventStore}.
   * </p>
   * 
   * @param event
   *          the event containing the added events and categories
   */
  public void logEventsAdded(LogEventStoreChangeEvent event);

  /**
   * <p>
   * Invoked when the {@link LogEventStore} is reset.
   * </p>
   */
  public void logEventStoreReset();
}
