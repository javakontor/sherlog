package org.javakontor.sherlog.ui.filter;

import javax.swing.JPanel;

/**
 * <p>
 * Represents an editor for a specific filter configuration.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface FilterConfigurationEditor {

  /**
   * <p>
   * Returns the panel for this configuration editor.
   * </p>
   * 
   * @return
   */
  public JPanel getPanel();

  /**
   * <p>
   * Returns the {@link FilterConfigurationEditorFactory} that created this configuration editor.
   * </p>
   * 
   * @return the {@link FilterConfigurationEditorFactory} that created this configuration editor.
   */
  public FilterConfigurationEditorFactory getFilterConfigurationEditorFactory();
}
