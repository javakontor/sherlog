package org.javakontor.sherlog.ui.logview.filterview;

import java.util.HashSet;
import java.util.Set;

import org.javakontor.sherlog.core.filter.LogEventFilter;
import org.javakontor.sherlog.core.store.LogEventStore;
import org.javakontor.sherlog.core.store.LogEventStoreAdapter;
import org.javakontor.sherlog.ui.logview.osgi.GuiExecutor;
import org.lumberjack.application.mvc.AbstractModel;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class LogEventFilterModel extends AbstractModel<LogEventFilterModel, LogEventFilterModelReasonForChange> {

  /** the log event store that should be displayed */
  private final LogEventStore       _logEventStore;

  /** - */
  private final Set<LogEventFilter> _logEventFilter;
  
  /**
   * <p>
   * Creates a new instance of type {@link LogEventFilterModel}.
   * </p>
   * 
   * @param logEventStore
   *          the {@link LogEventStore}
   */
  public LogEventFilterModel(LogEventStore logEventStore) {

    // the log event store
    this._logEventStore = logEventStore;

    // the log event Filters
    _logEventFilter = new HashSet<LogEventFilter>();

    // add the log store listener
    this._logEventStore.addLogStoreListener(new LogEventStoreAdapter() {

      @Override
      public void filterAdded(final LogEventFilter logEventFilter) {
        GuiExecutor.execute(new Runnable() {
          public void run() {
            if (_logEventFilter.add(logEventFilter)) {
              fireModelChangedEvent(LogEventFilterModelReasonForChange.filterAdded, logEventFilter);
            }
          }
        });
      }

      @Override
      public void filterRemoved(final LogEventFilter logEventFilter) {
        GuiExecutor.execute(new Runnable() {
          public void run() {
            if (_logEventFilter.remove(logEventFilter)) {
              fireModelChangedEvent(LogEventFilterModelReasonForChange.filterRemoved, logEventFilter);
            }
          }
        });
      }
    });

    // add the existing filters
    _logEventFilter.addAll(_logEventStore.getLogEventFilters());
  }
}
