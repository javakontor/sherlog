package org.javakontor.sherlog.ui.colorfilter;

import java.awt.Color;
import java.util.List;

import org.javakontor.sherlog.application.action.AbstractToggleAction;
import org.javakontor.sherlog.application.action.ActionContextAware;
import org.javakontor.sherlog.domain.filter.LogEventFilter;
import org.javakontor.sherlog.domain.store.LogEventStore;
import org.javakontor.sherlog.ui.logview.tableview.LogEventTableModel;

class ColorFilterAction extends AbstractToggleAction implements ActionContextAware<LogEventTableModel> {

  private final Color _color;

  /** May be null */
  private ColorFilter _colorFilter;

  LogEventStore       _logEventStore;

  ColorFilterAction(final String colorName, final Color color) {
    super("filter" + colorName + "Action", ColorFilterMenus.FILTER_BY_COLOR_TARGET_ACTIONGROUP_ID, String.format(
        ColorFilterMessages.filterByCtxMenuTitle, colorName));
    this._color = color;
  }

  public void execute() {
    // active is already set to the "new" state
    if (!isActive()) {
      this._colorFilter.removeColor(this._color);
    } else {
      this._colorFilter.addColor(this._color);
    }
  }

  public void setActionContext(final LogEventTableModel actionContext) {
    final LogEventTableModel logModel = actionContext;
    this._logEventStore = logModel.getLogEventStore();
    final List<LogEventFilter> filters = this._logEventStore.getLogEventFilters();
    this._colorFilter = null;
    for (final LogEventFilter filter : filters) {
      if (filter instanceof ColorFilter) {
        this._colorFilter = (ColorFilter) filter;
        break;
      }
    }

    setActive((this._colorFilter != null) && this._colorFilter.hasColor(this._color));

  }
}