package org.javakontor.sherlog.ui.simplefilter.ui;

import org.javakontor.sherlog.domain.filter.LogEventFilter;
import org.javakontor.sherlog.ui.filter.DefaultFilterConfigurationEditor;
import org.javakontor.sherlog.ui.filter.FilterConfigurationEditor;
import org.javakontor.sherlog.ui.filter.FilterConfigurationEditorFactory;
import org.javakontor.sherlog.ui.simplefilter.SimpleLogEventFilter;

public class SimpleFilterConfigurationEditorFactory implements FilterConfigurationEditorFactory {

  public FilterConfigurationEditor createFilterConfigurationEditor(LogEventFilter logEventFilter) {
    SimpleFilterConfigurationModel model = new SimpleFilterConfigurationModel((SimpleLogEventFilter) logEventFilter);
    SimpleFilterConfigurationView view = new SimpleFilterConfigurationView(model);
    new SimpleFilterConfigurationController(model, view);
    return new DefaultFilterConfigurationEditor(this, view);
  }

  /**
   * @see org.javakontor.sherlog.ui.filter.FilterConfigurationEditorFactory#isSuitableFor(org.javakontor.sherlog.core.filter.LogEventFilter)
   */
  public boolean isSuitableFor(LogEventFilter logEventFilter) {
    return logEventFilter instanceof SimpleLogEventFilter;
  }
}
