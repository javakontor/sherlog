package org.javakontor.sherlog.ui.logview.filterview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.javakontor.sherlog.application.mvc.AbstractModel;
import org.javakontor.sherlog.domain.filter.Filterable;
import org.javakontor.sherlog.domain.filter.FilterableChangeListener;
import org.javakontor.sherlog.domain.filter.LogEventFilter;
import org.javakontor.sherlog.domain.store.LogEventStore;
import org.javakontor.sherlog.ui.filter.FilterConfigurationEditorFactory;
import org.javakontor.sherlog.util.Assert;
import org.javakontor.sherlog.util.servicemanager.ServiceManager;
import org.javakontor.sherlog.util.servicemanager.ServiceManagerEvent;
import org.javakontor.sherlog.util.servicemanager.ServiceManagerListener;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class LogEventFilterModel extends AbstractModel<LogEventFilterModel, LogEventFilterModelReasonForChange> {

  /** the log event store that should be displayed */
  private final Filterable                                               _filterable;

  /** - */
  private final List<LogEventFilter>                                     _logEventFilter;

  /** - */
  private final InnerFilterableChangeListener                            _filterableChangeListener;

  /** - */
  private ServiceManager<FilterConfigurationEditorFactory>               _serviceManager;

  /** - */
  private final ServiceManagerListener<FilterConfigurationEditorFactory> _configurationEditorFactoryListener;

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

    _configurationEditorFactoryListener = new ServiceManagerListener<FilterConfigurationEditorFactory>() {

      public void serviceAdded(ServiceManagerEvent<FilterConfigurationEditorFactory> event) {
        fireModelChangedEvent(LogEventFilterModelReasonForChange.factoryAdded, event.getService());
      }

      public void serviceRemoved(ServiceManagerEvent<FilterConfigurationEditorFactory> event) {
        fireModelChangedEvent(LogEventFilterModelReasonForChange.factoryRemoved, event.getService());
      }
    };
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
    return _serviceManager != null ? _serviceManager.getServices() : new HashSet<FilterConfigurationEditorFactory>();
  }

  /**
   * <p>
   * </p>
   * 
   * @param configurationEditorFactoryManager
   */
  public void setConfigurationEditorFactoryManager(ServiceManager<FilterConfigurationEditorFactory> manager) {

    if (_serviceManager == null && manager != null) {

      // set factory manager
      _serviceManager = manager;

      // add listener
      _serviceManager.addServiceManagerListener(_configurationEditorFactoryListener);

      // added
      fireModelChangedEvent(LogEventFilterModelReasonForChange.factoryManagerAdded);
    }

    else if (_serviceManager != null && manager == null) {

      //
      _serviceManager.removeServiceManagerListener(_configurationEditorFactoryListener);

      //
      _serviceManager = null;

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
        System.err.println("filterAdded(" + logEventFilter + ")");
        fireModelChangedEvent(LogEventFilterModelReasonForChange.filterAdded, logEventFilter);
      }
    }

    /**
     * @see org.javakontor.sherlog.core.filter.FilterableChangeListener#filterRemoved(org.javakontor.sherlog.core.filter.LogEventFilter)
     */
    public void filterRemoved(final LogEventFilter logEventFilter) {
      if (_logEventFilter.remove(logEventFilter)) {
        System.err.println("filterRemoved(" + logEventFilter + ")");
        fireModelChangedEvent(LogEventFilterModelReasonForChange.filterRemoved, logEventFilter);
      }
    }
  }
}
