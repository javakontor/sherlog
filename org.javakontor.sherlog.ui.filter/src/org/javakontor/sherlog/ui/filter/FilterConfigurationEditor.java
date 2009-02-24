package org.javakontor.sherlog.ui.filter;

import javax.swing.JPanel;

import org.javakontor.sherlog.core.filter.LogEventFilter;

public interface FilterConfigurationEditor {

  public JPanel createFilterConfigurationEditorPanel(LogEventFilter logEventFilter);

  public boolean isSuitableFor(LogEventFilter logEventFilter);
}
