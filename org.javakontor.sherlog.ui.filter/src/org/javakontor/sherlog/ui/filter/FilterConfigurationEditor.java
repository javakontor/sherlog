package org.javakontor.sherlog.ui.filter;

import javax.swing.JPanel;

import org.javakontor.sherlog.core.filter.LogEventFilter;

public interface FilterConfigurationEditor {

  public JPanel createFilterConfigurationEditorPanel();

  public boolean isSuitableFor(LogEventFilter logEventFilter);
}
