package org.javakontor.sherlog.ui.timefilter;

import java.util.List;

import org.javakontor.sherlog.application.action.AbstractAction;
import org.javakontor.sherlog.application.action.ActionContextAware;
import org.javakontor.sherlog.domain.LogEvent;
import org.javakontor.sherlog.domain.filter.LogEventFilter;
import org.javakontor.sherlog.domain.store.LogEventStore;
import org.javakontor.sherlog.ui.logview.tableview.LogEventTableModel;

class TimeFilterAction extends AbstractAction implements ActionContextAware<LogEventTableModel> {

  /**
   * The TimeFilter this action should operate on. Might be null.
   */
  private TimeFilter         _timeFilter;

  private final boolean      _beforeFilter;

  /**
   * The current {@link LogEventTableModel}. Might be null
   */
  private LogEventTableModel _logEventTableModel;

  TimeFilterAction(boolean beforeFilter) {
    _beforeFilter = beforeFilter;
  }

  public void execute() {

    if (_timeFilter == null || _logEventTableModel == null) {
      return;
    }

    LogEvent[] selectedLogEvents = _logEventTableModel.getSelectedLogEvents();
    if (selectedLogEvents.length > 0) {
      if (_beforeFilter) {
        _timeFilter.setBeforeTimestamp(selectedLogEvents[0].getTimeStamp());
      } else {
        _timeFilter.setAfterTimestamp(selectedLogEvents[0].getTimeStamp());
      }
    }

  }

  public void setActionContext(final LogEventTableModel actionContext) {
    _logEventTableModel = actionContext;
    LogEventStore logEventStore = _logEventTableModel.getLogEventStore();
    final List<LogEventFilter> filters = logEventStore.getLogEventFilters();
    this._timeFilter = null;
    for (final LogEventFilter filter : filters) {
      if (filter instanceof TimeFilter) {
        this._timeFilter = (TimeFilter) filter;
        break;
      }
    }

    updateEnabledState();

  }

  private void updateEnabledState() {
    setEnabled(_timeFilter != null && _logEventTableModel != null
        && _logEventTableModel.getSelectedLogEvents().length > 0);

  }
}