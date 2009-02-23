package org.javakontor.sherlog.ui.logview.osgi;

import javax.swing.JPanel;

import org.javakontor.sherlog.core.store.LogEventStore;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * Tracks (de)registering of FilterConfigViewFactory.
 * 
 * <p>
 * For each registered FilterConfigViewFactory a FilterconfigView is created and added to the
 * {@link LogEventFilterView} FilterViewTracker --
 * 
 */
public class FilterViewTracker extends ServiceTracker {
  private final LogEventStore _logEventStore;

  public FilterViewTracker(BundleContext bundleContext, LogEventStore store) {
    super(bundleContext, FilterConfigViewFactory.class.getName(), null);
    this._logEventStore = store;
  }

  @Override
  public Object addingService(ServiceReference reference) {
    FilterConfigViewFactory factory = (FilterConfigViewFactory) super.addingService(reference);
    JPanel panel = factory.createFilterPanel(this._logEventStore);
    addFilterConfigView(panel);
    return panel;
  }

  @Override
  public void removedService(ServiceReference reference, Object service) {
    JPanel panel = (JPanel) service;
    removeFilterConfigView(panel);
    super.removedService(reference, service);
  }
}
