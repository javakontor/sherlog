package org.javakontor.sherlog.application.extender;

import java.util.HashMap;
import java.util.Map;

import org.javakontor.sherlog.application.action.AbstractToggleAction;
import org.javakontor.sherlog.application.action.ToggleAction;
import org.javakontor.sherlog.application.extender.internal.ServiceTrackerHolder;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * <p>
 * {@link ToggleAction} that implements support for service handling.
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class AbstractServiceAwareToggleAction extends AbstractToggleAction implements BundleContextAware, Lifecycle {

  /** the service tracker holder */
  private ServiceTrackerHolder _serviceTrackerHolder;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractServiceAwareToggleAction}.
   * </p>
   */
  public AbstractServiceAwareToggleAction(String... filters) {
    super();

    // create customized ServiceTrackerHolder
    _serviceTrackerHolder = new ServiceTrackerHolder(filters) {

      private Map<ServiceTracker, Integer> _count = new HashMap<ServiceTracker, Integer>();

      @Override
      public void addingService(ServiceTracker serviceTracker, ServiceReference reference) {
        int count = _count.containsKey(serviceTracker) ? _count.get(serviceTracker) : 0;
        _count.put(serviceTracker, count + 1);
        setEnabled(servicesSatisfied());
      }

      @Override
      public void removedService(ServiceTracker serviceTracker, ServiceReference reference, Object service) {
        int count = _count.containsKey(serviceTracker) ? _count.get(serviceTracker) : 0;
        _count.put(serviceTracker, count - 1);
        setEnabled(servicesSatisfied());
      }

      /**
       * <p>
       * </p>
       *
       * @return
       */
      private boolean servicesSatisfied() {
        // get service trackers
        Map<String, ServiceTracker> map = getServiceTrackers();

        // check if satisfied
        for (ServiceTracker tracker : map.values()) {
          if (!_count.containsKey(tracker) || _count.get(tracker) < 1) {
            return false;
          }
        }

        // return true
        return true;
      }
    };
  }

  /**
   * @see org.javakontor.sherlog.application.extender.Lifecycle#initialise()
   */
  public void initialise() {
    _serviceTrackerHolder.initialise();
  }

  /**
   * @see org.javakontor.sherlog.application.extender.Lifecycle#dispose()
   */
  public void dispose() {
    _serviceTrackerHolder.dispose();
  }

  /**
   * @see org.javakontor.sherlog.application.extender.BundleContextAware#setBundleContext(org.osgi.framework.BundleContext)
   */
  public void setBundleContext(BundleContext bundleContext) {
    _serviceTrackerHolder.setBundleContext(bundleContext);
  }

  /**
   * <p>
   * </p>
   *
   * @param filter
   * @return
   */
  public Object getService(String filter) {
    return _serviceTrackerHolder.getService(filter);
  }
}
