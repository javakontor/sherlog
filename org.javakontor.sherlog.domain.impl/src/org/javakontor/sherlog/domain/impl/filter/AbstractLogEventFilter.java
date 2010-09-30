package org.javakontor.sherlog.domain.impl.filter;

import java.util.concurrent.CopyOnWriteArraySet;

import org.javakontor.sherlog.domain.filter.LogEventFilter;
import org.javakontor.sherlog.domain.filter.LogEventFilterChangeEvent;
import org.javakontor.sherlog.domain.filter.LogEventFilterListener;
import org.javakontor.sherlog.domain.filter.LogEventFilterMemento;
import org.javakontor.sherlog.util.Assert;

/**
 * <p>
 * Abstract base class for LogFilter implementations. This base class provides basic support for managing a list of
 * registered {@link LogEventFilterListener}
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractLogEventFilter implements LogEventFilter {

  /** a thread-safe list with all registered Listeners */
  private final CopyOnWriteArraySet<LogEventFilterListener> _logFilterListener;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractLogEventFilter}.
   * </p>
   */
  public AbstractLogEventFilter() {
    this._logFilterListener = new CopyOnWriteArraySet<LogEventFilterListener>();
  }

  /**
   * <p>
   * Adds the specified {@link LogEventFilterListener} to this Filter. If the listener has already been registered the
   * listener will <b>not</b> be added twice.
   * </p>
   */
  public void addLogFilterListener(LogEventFilterListener listener) {
    Assert.notNull(listener);

    this._logFilterListener.add(listener);
  }

  /**
   * <p>
   * Removes the specified listener from this Filter. If the specified listener is not registered with this filter, the
   * remove request is ignored
   * </p>
   */
  public void removeLogFilterListener(LogEventFilterListener listener) {
    Assert.notNull(listener);

    this._logFilterListener.remove(listener);
  }

  /**
   * Restores this filter instance from the specified memento.
   * 
   * <p>
   * This implementation delegates to {@link #onRestoreFromMemento(Memento)} which must be implemented in subclasses.
   * <p>
   * After the filter has been restored, a {@link LogEventFilterChangeEvent} is fired
   */
  public synchronized final void restoreFromMemento(LogEventFilterMemento memento) {
    Assert.notNull(memento);

    // delegate to 'onRestoreFromMemento'
    onRestoreFromMemento(memento);

    // fire change event
    fireFilterChange();
  }

  /**
   * Restores this filter from the given - filter specific - memento
   * 
   * @param memento
   */
  protected abstract void onRestoreFromMemento(LogEventFilterMemento memento);

  /**
   * Sends a {@link LogEventFilterChangeEvent} to all registered {@link LogEventFilterListener LogFilterListeners}
   */
  protected void fireFilterChange() {
    final LogEventFilterChangeEvent event = new LogEventFilterChangeEvent(this);

    for (LogEventFilterListener listener : _logFilterListener) {
      listener.filterChanged(event);
    }
  }
}
