package org.javakontor.sherlog.ui.timefilter;

import java.util.List;

import org.javakontor.sherlog.application.action.AbstractAction;
import org.javakontor.sherlog.application.action.ActionContextAware;
import org.javakontor.sherlog.domain.filter.LogEventFilter;
import org.javakontor.sherlog.domain.store.LogEventStore;
import org.javakontor.sherlog.ui.logview.tableview.LogEventTableModel;

class ResetTimeFilterAction extends AbstractAction implements ActionContextAware<LogEventTableModel> {

  /**
   * The TimeFilter. Might be null.
   */
  private TimeFilter _timeFilter;

  public void execute() {

    if (_timeFilter == null) {
      return;
    }

    _timeFilter.reset();
  }

  public void setActionContext(final LogEventTableModel actionContext) {
    this._timeFilter = null;

    LogEventStore logEventStore = actionContext.getLogEventStore();
    final List<LogEventFilter> filters = logEventStore.getLogEventFilters();
    for (final LogEventFilter filter : filters) {
      if (filter instanceof TimeFilter) {
        this._timeFilter = (TimeFilter) filter;
        break;
      }
    }

    setEnabled(_timeFilter != null);

  }
}