package org.javakontor.sherlog.ui.filter.manager;

import java.util.Set;

import org.javakontor.sherlog.ui.filter.FilterConfigurationEditor;
import org.javakontor.sherlog.ui.filter.FilterConfigurationEditorFactory;

/**
 * <p>
 * The {@link FilterConfigurationEditorFactoryManager} allows to create
 * {@link FilterConfigurationEditor FilterConfigurationEditors} for a specific LogEventFilter.
 * </p>
 * <p>
 * The {@link FilterConfigurationEditorFactoryManager} is an OSGi Service and can be requested from the service
 * registry.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface FilterConfigurationEditorFactoryManager {

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Set<FilterConfigurationEditorFactory> getFilterConfigurationEditorFactories();

  /**
   * <p>
   * Adds the specified {@link FilterConfigurationEditorFactoryManagerListener} to this
   * {@link FilterConfigurationEditorFactoryManager}.
   * </p>
   * 
   * @param listener
   *          the {@link FilterConfigurationEditorFactoryManager}.
   * @return
   */
  public Set<FilterConfigurationEditorFactory> addFilterConfigurationEditorFactoryListener(
      FilterConfigurationEditorFactoryManagerListener listener);

  /**
   * <p>
   * Removes the specified {@link FilterConfigurationEditorFactoryManagerListener} from this
   * {@link FilterConfigurationEditorFactoryManager}.
   * </p>
   * 
   * @param listener
   *          the {@link FilterConfigurationEditorFactoryManager}.
   */
  public void removeFilterConfigurationEditorFactoryListener(FilterConfigurationEditorFactoryManagerListener listener);
}
