package org.javakontor.sherlog.core.store;

import java.util.List;

import org.javakontor.sherlog.core.LogEvent;
import org.javakontor.sherlog.core.filter.Filterable;

/**
 * <p>
 * A {@link LogEventStore} contains several {@link LogEvent LogEvents}.
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface LogEventStore extends Filterable {

  /**
   * <p>
   * Returns a {@link List} of all {@link LogEvent LogEvents}. The returned list is <b>not</b> filtered through the list
   * of registered Filters.
   * </p>
   *
   * @return a {@link List} of all {@link LogEvent LogEvents}.
   */
  public List<LogEvent> getLogEvents();

  /**
   * <p>
   * Returns the size of the log event list.
   * </p>
   *
   * @return the size of the log event list.
   */
  public int getLogEventCount();

  /**
   * <p>
   * Returns a {@link List} of all {@link LogEvent LogEvents}. The returned list is filtered through the list of
   * registered Filters.
   * </p>
   *
   * @return a {@link List} of all {@link LogEvent LogEvents}.
   */
  public List<LogEvent> getFilteredLogEvents();

  /**
   * <p>
   * Returns the size of the filtered log event list.
   * </p>
   *
   * @return the size of the filtered log event list.
   */
  public int getFilteredLogEventCount();

  /**
   * <p>
   * Returns a {@link List} of all categories.
   * </p>
   *
   * @return a {@link List} of all categories.
   */
  public List<String> getCategories();

  /**
   * <p>
   * Adds the given {@link LogEventStoreListener} to the {@link LogEventStore} .
   * </p>
   *
   * @param listener
   *          the {@link LogEventStoreListener} to add.
   */
  public void addLogStoreListener(final LogEventStoreListener listener);

  /**
   * <p>
   * Removes the given {@link LogEventStoreListener} from the {@link LogEventStore}.
   * </p>
   *
   * @param listener
   *          the {@link LogEventStoreListener} to remove.
   */
  public void removeLogStoreListener(final LogEventStoreListener listener);
}
