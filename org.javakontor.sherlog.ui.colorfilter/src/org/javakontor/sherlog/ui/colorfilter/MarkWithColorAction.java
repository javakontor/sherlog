package org.javakontor.sherlog.ui.colorfilter;

import java.awt.Color;

import org.javakontor.sherlog.application.action.ActionContextAware;
import org.javakontor.sherlog.application.action.contrib.ActionContribution;
import org.javakontor.sherlog.domain.LogEvent;
import org.javakontor.sherlog.ui.logview.tableview.LogEventTableModel;

public class MarkWithColorAction extends ActionContribution implements ActionContextAware<LogEventTableModel> {

  private final Color _color;

  private LogEvent[]  _selectedEvents;

  public MarkWithColorAction(final String colorName, final Color color) {
    super("markWithCtxMenuTitle" + colorName, ColorFilterMenus.MARK_WITH_COLOR_TARGET_ACTIONGROUP_ID, String.format(
        ColorFilterMessages.markWithCtxMenuTitle, colorName));
    this._color = color;
  }

  public void execute() {
    for (final LogEvent event : this._selectedEvents) {
      event.setUserDefinedField("COLOR", this._color);
    }
  }

  public void setActionContext(final LogEventTableModel actionContext) {
    this._selectedEvents = actionContext.getSelectedLogEvents();
    // this._selectedEvents = model.getLogEventTableModel().getSelectedLogEvents();

    setEnabled(this._selectedEvents.length > 0);
  }
}