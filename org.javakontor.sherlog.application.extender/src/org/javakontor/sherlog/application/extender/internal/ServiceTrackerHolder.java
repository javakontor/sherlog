package org.javakontor.sherlog.application.extender.internal;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.javakontor.sherlog.application.extender.BundleContextAware;
import org.javakontor.sherlog.application.extender.Lifecycle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ServiceTrackerHolder implements BundleContextAware, Lifecycle {

  /** service trackers */
  private Map<String, ServiceTracker> _serviceTrackers;

  /** the bundle context */
  private BundleContext               _bundleContext;

  /** array of filters */
  private String[]                    _filters;

  /**
   * <p>
   * Creates a new instance of type {@link ServiceTrackerHolder}.
   * </p>
   */
  public ServiceTrackerHolder(String... filters) {
    super();

    _filters = filters;
  }

  /**
   * <p>
   * </p>
   *
   * @return
   */
  public Map<String, ServiceTracker> getServiceTrackers() {
    return Collections.unmodifiableMap(_serviceTrackers);
  }

  /**
   * @see org.javakontor.sherlog.application.extender.BundleContextAware#setBundleContext(org.osgi.framework.BundleContext)
   */
  public void setBundleContext(BundleContext bundleContext) {
    _bundleContext = bundleContext;
  }

  /**
   * @see org.javakontor.sherlog.application.extender.Lifecycle#initialise()
   */
  public void initialise() {
    // create service tracker map
    _serviceTrackers = new HashMap<String, ServiceTracker>();

    for (String filterString : _filters) {
      try {
        Filter filter = _bundleContext.createFilter(filterString);

        ServiceTracker serviceTracker = new ServiceTracker(_bundleContext, filter, null) {

          @Override
          public Object addingService(ServiceReference reference) {
            Object result = super.addingService(reference);
            ServiceTrackerHolder.this.addingService(this, reference);
            return result;
          }

          @Override
          public void removedService(ServiceReference reference, Object service) {
            super.removedService(reference, service);
            ServiceTrackerHolder.this.removedService(this, reference, service);
          }
        };

        _serviceTrackers.put(filterString, serviceTracker);
      } catch (InvalidSyntaxException e) {
        e.printStackTrace();
      }
    }

    for (ServiceTracker serviceTracker : _serviceTrackers.values()) {
      serviceTracker.open();
    }
  }

  protected void removedService(ServiceTracker serviceTracker, ServiceReference reference, Object service) {
    // do nothing...
  }

  protected void addingService(ServiceTracker serviceTracker, ServiceReference reference) {
    // do nothing...
  }

  /**
   * @see org.javakontor.sherlog.application.extender.Lifecycle#dispose()
   */
  public void dispose() {

    for (ServiceTracker serviceTracker : _serviceTrackers.values()) {
      serviceTracker.close();
    }

    _serviceTrackers = null;
  }

  /**
   * <p>
   * </p>
   *
   * @param filter
   * @return
   */
  public Object getService(String filter) {

    if (!_serviceTrackers.containsKey(filter)) {
      throw new RuntimeException("asdasd");
    }

    return _serviceTrackers.get(filter).getService();
  }
}
