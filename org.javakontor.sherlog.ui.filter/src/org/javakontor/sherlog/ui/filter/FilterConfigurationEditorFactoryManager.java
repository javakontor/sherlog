package org.javakontor.sherlog.ui.filter;

import javax.swing.JPanel;

import org.javakontor.sherlog.core.filter.LogEventFilter;

public interface FilterConfigurationEditorFactoryManager {

  public JPanel createFilterConfigurationEditor(LogEventFilter logEventFilter);

  public void addFilterConfigurationEditorFactoryListener(FilterConfigurationEditorFactoryManagerListener listener);

  public void removeFilterConfigurationEditorFactoryListener(FilterConfigurationEditorFactoryManagerListener listener);
}
