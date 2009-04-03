package org.javakontor.sherlog.ui.filter;

import javax.swing.JPanel;

import org.javakontor.sherlog.util.Assert;

public class DefaultFilterConfigurationEditor implements FilterConfigurationEditor {

  private FilterConfigurationEditorFactory _factory;

  private JPanel                           _panel;

  public DefaultFilterConfigurationEditor(FilterConfigurationEditorFactory factory, JPanel panel) {
    Assert.notNull(factory);
    Assert.notNull(panel);

    _factory = factory;
    _panel = panel;
  }

  public FilterConfigurationEditorFactory getFilterConfigurationEditorFactory() {
    return _factory;
  }

  public JPanel getPanel() {
    return _panel;
  }

}
