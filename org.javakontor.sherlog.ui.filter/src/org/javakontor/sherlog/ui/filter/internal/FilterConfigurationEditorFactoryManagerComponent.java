package org.javakontor.sherlog.ui.filter.internal;

import java.util.HashSet;
import java.util.Set;

import javax.swing.JPanel;

import org.javakontor.sherlog.core.filter.LogEventFilter;
import org.javakontor.sherlog.ui.filter.FilterConfigurationEditorFactory;
import org.javakontor.sherlog.ui.filter.FilterConfigurationEditorFactoryManager;
import org.javakontor.sherlog.ui.filter.FilterConfigurationEditorFactoryManagerListener;

public class FilterConfigurationEditorFactoryManagerComponent implements FilterConfigurationEditorFactoryManager {

  private Set<FilterConfigurationEditorFactory> _filterConfigurationEditorFactories;

  public FilterConfigurationEditorFactoryManagerComponent() {
    _filterConfigurationEditorFactories = new HashSet<FilterConfigurationEditorFactory>();
  }

  public JPanel createFilterConfigurationEditor(LogEventFilter logEventFilter) {
    System.err.println("*******************************");
    System.err.println(logEventFilter);
    for (FilterConfigurationEditorFactory factory : _filterConfigurationEditorFactories) {
      if (factory.isSuitableFor(logEventFilter)) {

        JPanel panel = factory.createFilterConfigurationEditorPanel(logEventFilter);
        System.err.println(panel);
        System.err.println("*******************************");
        return panel;
      }
    }

    return null;
  }

  public void addFilterConfigurationEditorFactoryListener(FilterConfigurationEditorFactoryManagerListener listener) {
    // TODO
  }

  public void removeFilterConfigurationEditorFactoryListener(FilterConfigurationEditorFactoryManagerListener listener) {
    // TODO
  }

  public void addFilterConfigurationEditorFactory(FilterConfigurationEditorFactory factory) {
    _filterConfigurationEditorFactories.add(factory);
  }

  public void removeFilterConfigurationEditorFactory(FilterConfigurationEditorFactory factory) {
    _filterConfigurationEditorFactories.remove(factory);
  }
}
