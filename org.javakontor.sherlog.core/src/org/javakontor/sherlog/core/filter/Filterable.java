package org.javakontor.sherlog.core.filter;

import java.util.List;

import org.javakontor.sherlog.core.store.LogEventStore;

/**
 * <p>
 * Defines methods to add and remove {@link LogEventFilter LogEventFilters} to resp. from the {@link LogEventStore}.
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface Filterable {

  /**
   * <p>
   * Returns all {@link LogEventFilter LogEventFilters} that have been added to the {@link LogEventStore}.
   * </p>
   * 
   * @return all {@link LogEventFilter LogEventFilters} that have been added to the {@link LogEventStore}.
   */
  public List<LogEventFilter> getLogEventFilters();

  /**
   * <p>
   * Adds the specified {@link LogEventFilter} if this Filterable doesn't contain it already
   * </p>
   * 
   * @param logEventFilter
   *          the {@link LogEventFilter} to add.
   */
  public boolean addLogEventFilter(LogEventFilter logEventFilter);

  /**
   * <p>
   * Removes the specified {@link LogEventFilter}
   * </p>
   * 
   * @param logEventFilter
   *          the {@link LogEventFilter} to remove.
   * @return true if the logEventFilter has been removed
   */
  public boolean removeLogEventFilter(LogEventFilter logEventFilter);

  public List<LogEventFilter> addFilterableChangeListener(FilterableChangeListener filterableChangeListener);

  public boolean removeFilterableChangeListener(FilterableChangeListener filterableChangeListener);
}
