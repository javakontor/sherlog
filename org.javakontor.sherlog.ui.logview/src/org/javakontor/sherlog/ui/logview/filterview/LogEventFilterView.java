package org.javakontor.sherlog.ui.logview.filterview;

import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.javakontor.sherlog.core.filter.LogEventFilter;
import org.javakontor.sherlog.ui.filter.FilterConfigurationEditorFactory;
import org.lumberjack.application.mvc.AbstractView;
import org.lumberjack.application.mvc.ModelChangedEvent;

public class LogEventFilterView extends AbstractView<LogEventFilterModel, LogEventFilterModelReasonForChange> {

  /** - */
  private static final long                serialVersionUID = 1L;

  private FilterConfigurationEditorFactory _configurationEditorFactory;

  public LogEventFilterView(LogEventFilterModel model) {
    super(model);
  }

  @Override
  protected void setUp() {
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    // TODO layout (Apply filter-Button should be last on view)

    // add(new LogEventFilterPanel());
    // add(new JButton("Apply Filter"));
  }

  public void modelChanged(ModelChangedEvent<LogEventFilterModel, LogEventFilterModelReasonForChange> event) {

    switch (event.getReasonForChange()) {
    case filterAdded:
      LogEventFilter logEventFilter = (LogEventFilter) event.getObjects()[0];
      createFilterConfigurationEditor(logEventFilter);
      break;
    default:
      // ignore
      break;
    }
  }

  private void createFilterConfigurationEditor(LogEventFilter logEventFilter) {
    if (_configurationEditorFactory != null
        && _configurationEditorFactory.canCreateFilterConfigurationEditor(logEventFilter)) {

      JPanel editorPanel = _configurationEditorFactory.createFilterConfigurationEditor(logEventFilter);
      add(editorPanel);
      Box.createVerticalGlue();
    }
  }

  public void removePanel(JPanel filterConfigView) {
    remove(filterConfigView);
    // TODO remove glue
  }

  public void setFilterConfigurationEditorFactory(FilterConfigurationEditorFactory factory) {
    _configurationEditorFactory = factory;

    if (factory != null) {
      Set<LogEventFilter> filters = getModel().getLogEventFilter();
      for (LogEventFilter logEventFilter : filters) {
        createFilterConfigurationEditor(logEventFilter);
      }
    }
  }
}
