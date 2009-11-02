package org.javakontor.sherlog.ui.filter;

import org.javakontor.sherlog.domain.filter.LogEventFilter;

/**
 * <p>
 * To provide an editor for a specific filter configuration, you have to implement an instance of type
 * {@link FilterConfigurationEditor} and register it with the OSGi service registry.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface FilterConfigurationEditorFactory {

  /**
   * @param logEventFilter
   * @return
   */
  public boolean isSuitableFor(LogEventFilter logEventFilter);

  /**
   * @param logEventFilter
   * @return
   */
  public FilterConfigurationEditor createFilterConfigurationEditor(LogEventFilter logEventFilter);
}
