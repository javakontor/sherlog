package org.javakontor.sherlog.ui.filter;

import javax.swing.JPanel;

import org.javakontor.sherlog.core.filter.LogEventFilter;

public interface FilterConfigurationEditorFactory {

  public boolean canCreateFilterConfigurationEditor(LogEventFilter logEventFilter);

  public JPanel createFilterConfigurationEditor(LogEventFilter logEventFilter);

  public void addFilterConfigurationEditorFactoryListener(FilterConfigurationEditorFactoryListener listener);

  public void removeFilterConfigurationEditorFactoryListener(FilterConfigurationEditorFactoryListener listener);
}
