package org.javakontor.sherlog.application.extender;

import java.util.HashMap;
import java.util.Map;

import org.javakontor.sherlog.application.action.AbstractToggleAction;
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
public class AbstractServiceAwareToggleAction extends AbstractToggleAction implements BundleContextAware, Lifecycle {

  /** service trackers */
  private Map<String, ServiceTracker> _serviceTrackers;

  /** the bundle context */
  private BundleContext               _bundleContext;

  /** array of filters */
  private String[]                    _filters;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractServiceAwareToggleAction}.
   * </p>
   */
  public AbstractServiceAwareToggleAction(String... filters) {
    super();

    _filters = filters;
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

          private int _count;

          @Override
          public Object addingService(ServiceReference reference) {
            _count++;
            setEnabled(_count > 0);
            return super.addingService(reference);
          }

          @Override
          public void removedService(ServiceReference reference, Object service) {
            _count--;
            setEnabled(_count > 0);
            super.removedService(reference, service);
          }
        };

        _serviceTrackers.put(filterString, serviceTracker);
      } catch (InvalidSyntaxException e) {
        // TODO
        e.printStackTrace();
      }
    }

    for (ServiceTracker serviceTracker : _serviceTrackers.values()) {
      serviceTracker.open();
    }
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

  public Object getService(String filter) {

    if (!_serviceTrackers.containsKey(filter)) {
      throw new RuntimeException("asdasd");
    }

    return _serviceTrackers.get(filter).getService();
  }
}
