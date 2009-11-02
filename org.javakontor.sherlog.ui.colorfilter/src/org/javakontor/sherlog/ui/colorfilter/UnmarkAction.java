package org.javakontor.sherlog.ui.colorfilter;

import org.javakontor.sherlog.application.action.AbstractAction;
import org.javakontor.sherlog.application.action.ActionContextAware;
import org.javakontor.sherlog.domain.LogEvent;
import org.javakontor.sherlog.ui.logview.tableview.LogEventTableModel;

class UnmarkAction extends AbstractAction implements ActionContextAware<LogEventTableModel> {

  private LogEvent[] _selectedEvents;

  public void execute() {
    for (final LogEvent event : this._selectedEvents) {
      event.setUserDefinedField("COLOR", null);
    }
  }

  public void setActionContext(final LogEventTableModel actionContext) {
    this._selectedEvents = actionContext.getSelectedLogEvents();

    boolean enabled = false;

    // only enable if at least one selected log event is marked with a color
    for (final LogEvent event : this._selectedEvents) {
      if (event.getUserDefinedField("COLOR") != null) {
        enabled = true;
        break;
      }
    }

    setEnabled(enabled);

  }
}