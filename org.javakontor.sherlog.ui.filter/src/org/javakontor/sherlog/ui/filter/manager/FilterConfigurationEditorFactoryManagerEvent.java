package org.javakontor.sherlog.ui.filter.manager;

import java.util.EventObject;

import org.javakontor.sherlog.ui.filter.FilterConfigurationEditorFactory;

/**
 * <p>
 * An instance of type {@link FilterConfigurationEditorFactoryManagerEvent} is fired if a
 * {@link FilterConfigurationEditorFactory} is registered with or unregistered from the OSGi service registry.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class FilterConfigurationEditorFactoryManagerEvent extends EventObject {

  /** serialVersionUID */
  private static final long serialVersionUID = 1L;

  /**
   * <p>
   * Creates a new instance of type {@link FilterConfigurationEditorFactoryManagerEvent}.
   * </p>
   * 
   * @param source
   *          the {@link FilterConfigurationEditorFactory}
   */
  public FilterConfigurationEditorFactoryManagerEvent(FilterConfigurationEditorFactory factory) {
    super(factory);
  }

  /**
   * <p>
   * Returns the {@link FilterConfigurationEditorFactory}.
   * </p>
   * 
   * @return the {@link FilterConfigurationEditorFactory}.
   */
  public FilterConfigurationEditorFactory getFilterConfigurationEditorFactory() {
    return (FilterConfigurationEditorFactory) getSource();
  }
}
