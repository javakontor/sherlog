package org.javakontor.sherlog.application.extender;

import org.javakontor.sherlog.application.action.AbstractAction;
import org.javakontor.sherlog.application.action.Action;
import org.javakontor.sherlog.application.extender.internal.ServiceTrackerHolder;
import org.osgi.framework.BundleContext;

/**
 * <p>
 * {@link Action} that implements support for service handling.
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractServiceAwareAction extends AbstractAction implements BundleContextAware, Lifecycle {

  /** the service tracker holder */
  private ServiceTrackerHolder _serviceTrackerHolder;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractServiceAwareAction}.
   * </p>
   */
  public AbstractServiceAwareAction(String... filters) {
    super();

    // create a ServiceTrackerHolder
    _serviceTrackerHolder = new ServiceTrackerHolder(filters);
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
   * Returns the service for the specified filter. Can be null.
   * </p>
   *
   * @param filter
   * @return the filter
   */
  public Object getService(String filter) {
    return _serviceTrackerHolder.getService(filter);
  }
}
