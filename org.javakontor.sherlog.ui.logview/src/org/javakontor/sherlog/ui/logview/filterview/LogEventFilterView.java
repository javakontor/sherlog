package org.javakontor.sherlog.ui.logview.filterview;

import java.util.Set;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.javakontor.sherlog.core.filter.LogEventFilter;
import org.javakontor.sherlog.ui.filter.FilterConfigurationEditorFactoryManager;
import org.lumberjack.application.mvc.AbstractView;
import org.lumberjack.application.mvc.ModelChangedEvent;

public class LogEventFilterView extends AbstractView<LogEventFilterModel, LogEventFilterModelReasonForChange> {

  /** - */
  private static final long                       serialVersionUID = 1L;

  private FilterConfigurationEditorFactoryManager _configurationEditorFactoryManager;

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

    // assert that the FilterConfigurationEditorFactoryManager is set
    if (_configurationEditorFactoryManager != null) {

      JPanel editorPanel = _configurationEditorFactoryManager.createFilterConfigurationEditor(logEventFilter);

      if (editorPanel != null) {
        add(editorPanel);
        Box.createVerticalGlue();
        repaint();
      }
    }
  }

  public void removePanel(JPanel filterConfigView) {
    remove(filterConfigView);
    // TODO remove glue
  }

  public void setFilterConfigurationEditorFactoryManager(FilterConfigurationEditorFactoryManager factory) {
    _configurationEditorFactoryManager = factory;

    // createFilterConfigurationEditor(null);

    if (factory != null) {
      Set<LogEventFilter> filters = getModel().getLogEventFilter();
      for (LogEventFilter logEventFilter : filters) {
        createFilterConfigurationEditor(logEventFilter);
      }
    }
  }
}
