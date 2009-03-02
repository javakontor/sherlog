package org.javakontor.sherlog.ui.filter.internal;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.javakontor.sherlog.ui.filter.FilterConfigurationEditorFactory;
import org.javakontor.sherlog.ui.filter.manager.FilterConfigurationEditorFactoryManager;
import org.javakontor.sherlog.ui.filter.manager.FilterConfigurationEditorFactoryManagerEvent;
import org.javakontor.sherlog.ui.filter.manager.FilterConfigurationEditorFactoryManagerListener;
import org.javakontor.sherlog.util.Assert;

public class FilterConfigurationEditorFactoryManagerComponent implements FilterConfigurationEditorFactoryManager {

  /** - */
  private Object                                                _lock = new Object();

  /** - */
  private Set<FilterConfigurationEditorFactory>                 _filterConfigurationEditorFactories;

  /** - */
  private List<FilterConfigurationEditorFactoryManagerListener> _filterConfigurationEditorFactoryManagerListeners;

  /**
   * 
   */
  public FilterConfigurationEditorFactoryManagerComponent() {
    _filterConfigurationEditorFactories = new HashSet<FilterConfigurationEditorFactory>();
    _filterConfigurationEditorFactoryManagerListeners = new CopyOnWriteArrayList<FilterConfigurationEditorFactoryManagerListener>();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.javakontor.sherlog.ui.filter.manager.FilterConfigurationEditorFactoryManager#getFilterConfigurationEditorFactories()
   */
  public Set<FilterConfigurationEditorFactory> getFilterConfigurationEditorFactories() {
    synchronized (_lock) {
      return Collections.unmodifiableSet(_filterConfigurationEditorFactories);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.javakontor.sherlog.ui.filter.manager.FilterConfigurationEditorFactoryManager#addFilterConfigurationEditorFactoryListener(org.javakontor.sherlog.ui.filter.manager.FilterConfigurationEditorFactoryManagerListener)
   */
  public Set<FilterConfigurationEditorFactory> addFilterConfigurationEditorFactoryListener(
      FilterConfigurationEditorFactoryManagerListener listener) {

    synchronized (_lock) {
      _filterConfigurationEditorFactoryManagerListeners.add(listener);
      return new HashSet<FilterConfigurationEditorFactory>(_filterConfigurationEditorFactories);
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.javakontor.sherlog.ui.filter.manager.FilterConfigurationEditorFactoryManager#removeFilterConfigurationEditorFactoryListener(org.javakontor.sherlog.ui.filter.manager.FilterConfigurationEditorFactoryManagerListener)
   */
  public void removeFilterConfigurationEditorFactoryListener(FilterConfigurationEditorFactoryManagerListener listener) {
    _filterConfigurationEditorFactoryManagerListeners.remove(listener);
  }

  /**
   * @param factory
   */
  public void addFilterConfigurationEditorFactory(FilterConfigurationEditorFactory factory) {
    synchronized (_lock) {
      _filterConfigurationEditorFactories.add(factory);
    }
    fireManagerAdded(factory);
  }

  /**
   * @param factory
   */
  public void removeFilterConfigurationEditorFactory(FilterConfigurationEditorFactory factory) {
    synchronized (_lock) {
      _filterConfigurationEditorFactories.remove(factory);
    }
    fireManagerRemoved(factory);
  }

  /**
   * @param factory
   */
  protected final void fireManagerAdded(FilterConfigurationEditorFactory factory) {
    Assert.notNull(factory);

    FilterConfigurationEditorFactoryManagerEvent event = new FilterConfigurationEditorFactoryManagerEvent(factory);
    for (FilterConfigurationEditorFactoryManagerListener modelListener : _filterConfigurationEditorFactoryManagerListeners) {
      if (modelListener != null) {
        modelListener.factoryAdded(event);
      }
    }
  }

  /**
   * @param factory
   */
  protected final void fireManagerRemoved(FilterConfigurationEditorFactory factory) {
    Assert.notNull(factory);

    FilterConfigurationEditorFactoryManagerEvent event = new FilterConfigurationEditorFactoryManagerEvent(factory);

    for (FilterConfigurationEditorFactoryManagerListener modelListener : _filterConfigurationEditorFactoryManagerListeners) {
      if (modelListener != null) {
        modelListener.factoryRemoved(event);
      }
    }
  }
}
