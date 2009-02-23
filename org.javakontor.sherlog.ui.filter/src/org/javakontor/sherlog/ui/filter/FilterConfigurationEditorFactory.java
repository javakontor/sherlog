package org.javakontor.sherlog.ui.filter;

import org.javakontor.sherlog.core.filter.LogEventFilter;

public interface FilterConfigurationEditorFactory {

  public FilterConfigurationEditor createFilterConfigurationEditor(LogEventFilter logEventFilter);
}
