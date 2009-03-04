package org.javakontor.sherlog.ui.logview.filterview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.javakontor.sherlog.core.filter.Filterable;
import org.javakontor.sherlog.core.filter.FilterableChangeListener;
import org.javakontor.sherlog.core.filter.LogEventFilter;
import org.javakontor.sherlog.core.store.LogEventStore;
import org.javakontor.sherlog.ui.filter.FilterConfigurationEditorFactory;
import org.javakontor.sherlog.ui.filter.manager.FilterConfigurationEditorFactoryManager;
import org.javakontor.sherlog.ui.filter.manager.FilterConfigurationEditorFactoryManagerEvent;
import org.javakontor.sherlog.ui.filter.manager.FilterConfigurationEditorFactoryManagerListener;
import org.javakontor.sherlog.util.Assert;
import org.lumberjack.application.mvc.AbstractModel;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class LogEventFilterModel extends AbstractModel<LogEventFilterModel, LogEventFilterModelReasonForChange> {

  /** the log event store that should be displayed */
  private final Filterable                                           _filterable;

  /** - */
  private final List<LogEventFilter>                                 _logEventFilter;

  /** - */
  private final InnerFilterableChangeListener                        _filterableChangeListener;

  /** - */
  private FilterConfigurationEditorFactoryManager                    _configurationEditorFactoryManager;

  /** - */
  private final Set<FilterConfigurationEditorFactory>                _configurationEditorFactories;

  /** - */
  private final InnerFilterConfigurationEditorFactoryManagerListener _configurationEditorFactoryListener;

  /**
   * <p>
   * Creates a new instance of type {@link LogEventFilterModel}.
   * </p>
   * 
   * @param logEventStore
   *          the {@link LogEventStore}
   */
  public LogEventFilterModel(Filterable filterable) {
    Assert.notNull(filterable);

    // the filterable
    this._filterable = filterable;
    // the log event Filters
    _logEventFilter = new ArrayList<LogEventFilter>();
    // create LogStoreChangeListener
    _filterableChangeListener = new InnerFilterableChangeListener();
    // add the log store listener
    List<LogEventFilter> filters = this._filterable.addFilterableChangeListener(_filterableChangeListener);
    // add the existing filters
    _logEventFilter.addAll(filters);
    // 
    _configurationEditorFactories = new HashSet<FilterConfigurationEditorFactory>();

    _configurationEditorFactoryListener = new InnerFilterConfigurationEditorFactoryManagerListener();
  }

  /**
   * @return
   */
  public List<LogEventFilter> getLogEventFilter() {
    return Collections.unmodifiableList(_logEventFilter);
  }

  /**
   * @return
   */
  public Set<FilterConfigurationEditorFactory> getFilterConfigurationEditorFactories() {
    return Collections.unmodifiableSet(_configurationEditorFactories);
  }

  /**
   * <p>
   * </p>
   * 
   * @param configurationEditorFactoryManager
   */
  public void setConfigurationEditorFactoryManager(FilterConfigurationEditorFactoryManager manager) {

    if (_configurationEditorFactoryManager == null && manager != null) {

      // set factory manager
      _configurationEditorFactoryManager = manager;

      // add listener
      Set<FilterConfigurationEditorFactory> factories = _configurationEditorFactoryManager
          .addFilterConfigurationEditorFactoryListener(_configurationEditorFactoryListener);

      // set all known factories
      _configurationEditorFactories.addAll(factories);

      // added
      fireModelChangedEvent(LogEventFilterModelReasonForChange.factoryManagerAdded);
    }

    else if (_configurationEditorFactoryManager != null && manager == null) {

      //
      _configurationEditorFactoryManager
          .removeFilterConfigurationEditorFactoryListener(_configurationEditorFactoryListener);

      //
      _configurationEditorFactories.clear();

      //
      _configurationEditorFactoryManager = null;

      // removed
      fireModelChangedEvent(LogEventFilterModelReasonForChange.factoryManagerRemoved);
    }

    else {
      throw new UnsupportedOperationException("TODO");
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  private class InnerFilterableChangeListener implements FilterableChangeListener {

    /**
     * @see org.javakontor.sherlog.core.filter.FilterableChangeListener#filterAdded(org.javakontor.sherlog.core.filter.LogEventFilter)
     */
    public void filterAdded(final LogEventFilter logEventFilter) {
      if (_logEventFilter.add(logEventFilter)) {
        fireModelChangedEvent(LogEventFilterModelReasonForChange.filterAdded, logEventFilter);
      }
    }

    /**
     * @see org.javakontor.sherlog.core.filter.FilterableChangeListener#filterRemoved(org.javakontor.sherlog.core.filter.LogEventFilter)
     */
    public void filterRemoved(final LogEventFilter logEventFilter) {
      if (_logEventFilter.remove(logEventFilter)) {
        fireModelChangedEvent(LogEventFilterModelReasonForChange.filterRemoved, logEventFilter);
      }
    }
  }

  /**
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  private final class InnerFilterConfigurationEditorFactoryManagerListener implements
      FilterConfigurationEditorFactoryManagerListener {

    public void factoryAdded(final FilterConfigurationEditorFactoryManagerEvent event) {
      if (_configurationEditorFactories.add(event.getFilterConfigurationEditorFactory())) {
        fireModelChangedEvent(LogEventFilterModelReasonForChange.factoryAdded, event
            .getFilterConfigurationEditorFactory());
      }
    }

    public void factoryRemoved(final FilterConfigurationEditorFactoryManagerEvent event) {
      if (_configurationEditorFactories.remove(event.getFilterConfigurationEditorFactory())) {
        fireModelChangedEvent(LogEventFilterModelReasonForChange.factoryRemoved, event
            .getFilterConfigurationEditorFactory());
      }
    }
  }
}
