package org.javakontor.sherlog.core.store;

import java.util.Collection;

import org.javakontor.sherlog.core.LogEvent;
import org.javakontor.sherlog.core.filter.LogEventFilter;

/**
 * <p>
 * An abstract adapter class for receiving log event store events. The methods in this class are empty. This class
 * exists as convenience for creating listener objects.
 * </p>
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 *
 */
public class LogEventStoreAdapter implements LogEventStoreListener {

  /**
   * {@inheritDoc}
   */
  public void categoriesAdded(Collection<String> categories) {
    // empty implementation
  }

  /**
   * {@inheritDoc}
   */
  public void categoriesRemoved(Collection<String> categories) {
    // empty implementation
  }

  /**
   * {@inheritDoc}
   */
  public void logEventsAdded(Collection<LogEvent> logEvents) {
    // empty implementation
  }

  /**
   * {@inheritDoc}
   */
  public void logEventsRemoved(Collection<LogEvent> logEvents) {
    // empty implementation
  }

  /**
   * {@inheritDoc}
   */
  public void filterAdded(LogEventFilter logEventFilter) {
    // empty implementation
  }

  /**
   * {@inheritDoc}
   */
  public void filterRemoved(LogEventFilter logEventFilter) {
    // empty implementation
  }

  /**
   * {@inheritDoc}
   */
  public void logEventStoreReset() {
    // empty implementation
  }
}
