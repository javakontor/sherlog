package org.javakontor.sherlog.domain.store;

import java.util.List;

import org.javakontor.sherlog.domain.LogEvent;

/**
 * <p>
 * Defines additional methods to modify a {@link LogEventStore}.
 * </p>
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface ModifiableLogEventStore extends LogEventStore {

  /**
   * <p>
   * Adds the specified {@link LogEvent} to the {@link LogEventStore}.
   * </p>
   *
   * @param event
   *          the event to add.
   */
  public void addLogEvent(final LogEvent event);

  /**
   * <p>
   * Adds the specified {@link LogEvent LogEvents} to the {@link LogEventStore}.
   * </p>
   *
   * @param events
   *          the events to add.
   */
  public void addLogEvents(final List<LogEvent> events);

  /**
   * <p>
   * Resets the {@link LogEventStore}. All stored {@link LogEvent LogEvents} will be deleted.
   * </p>
   */
  public void reset();
}
