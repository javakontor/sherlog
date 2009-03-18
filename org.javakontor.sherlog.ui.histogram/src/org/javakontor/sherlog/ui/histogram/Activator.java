package org.javakontor.sherlog.ui.histogram;

import org.javakontor.sherlog.application.view.ViewContribution;
import org.javakontor.sherlog.domain.store.LogEventStore;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

public class Activator implements BundleActivator {

  private ServiceTracker           _serviceTracker;

  private LoggraphViewContribution _viewContribution;

  /*
   * (non-Javadoc)
   * 
   * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
   */
  public void start(BundleContext context) throws Exception {

    _viewContribution = new LoggraphViewContribution();

    context.registerService(ViewContribution.class.getName(), _viewContribution, null);

    _serviceTracker = new LogEventStoreServiceTracker(context);
    _serviceTracker.open();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
   */
  public void stop(BundleContext context) throws Exception {
    _serviceTracker.close();
  }

  /**
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  private class LogEventStoreServiceTracker extends ServiceTracker {

    public LogEventStoreServiceTracker(BundleContext context) {
      super(context, LogEventStore.class.getName(), null);
    }

    @Override
    public Object addingService(ServiceReference reference) {
      LogEventStore logEventStore = (LogEventStore) super.addingService(reference);

      Activator.this._viewContribution.bindLogEventStore(logEventStore);

      return logEventStore;
    }

    @Override
    public void removedService(ServiceReference reference, Object service) {
      Activator.this._viewContribution.bindLogEventStore(null);
      super.removedService(reference, service);
    }
  }
}
