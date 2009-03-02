package org.javakontor.sherlog.ui.filter.manager;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface FilterConfigurationEditorFactoryManagerListener {

  /**
   * <p>
   * </p>
   * 
   * @param event
   */
  public void factoryAdded(FilterConfigurationEditorFactoryManagerEvent event);

  /**
   * <p>
   * </p>
   * 
   * @param event
   */
  public void factoryRemoved(FilterConfigurationEditorFactoryManagerEvent event);
}
