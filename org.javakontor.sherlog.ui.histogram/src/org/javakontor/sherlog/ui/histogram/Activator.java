package org.javakontor.sherlog.ui.histogram;

import org.javakontor.sherlog.application.view.ViewContribution;
import org.javakontor.sherlog.domain.store.LogEventStore;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * <p>
 * The activator for the histogram bundle.
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Activator implements BundleActivator {

	/** the histogram view contribution */
	private HistogramViewContribution _viewContribution;

	/** the service tracker */
	private ServiceTracker _serviceTracker;

	/**
	 * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {

		// create new HistogramViewContribution
		_viewContribution = new HistogramViewContribution();

		// register HistogramViewContribution as an OSGi service
		context.registerService(ViewContribution.class.getName(),
				_viewContribution, null);

		// create and open LogEventStoreServiceTracker
		_serviceTracker = new LogEventStoreServiceTracker(context);
		_serviceTracker.open();
	}

	/**
	 * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		// close service tracker
		_serviceTracker.close();
	}

	/**
	 * <p>
	 * The {@link LogEventStoreServiceTracker} is responsible for tracking the
	 * log event store service.
	 * </p>
	 *
	 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
	 */
	private class LogEventStoreServiceTracker extends ServiceTracker {

		/**
		 * <p>
		 * Creates a new instance of type {@link LogEventStoreServiceTracker}.
		 * </p>
		 *
		 * @param context
		 *            the bundle context
		 */
		public LogEventStoreServiceTracker(BundleContext context) {
			super(context, LogEventStore.class.getName(), null);
		}

		/**
		 * @see org.osgi.util.tracker.ServiceTracker#addingService(org.osgi.framework.ServiceReference)
		 */
		@Override
		public Object addingService(ServiceReference reference) {
			// get the log event store
			LogEventStore logEventStore = (LogEventStore) super
					.addingService(reference);

			// bind log event store to the histogram view contribution
			Activator.this._viewContribution.bindLogEventStore(logEventStore);

			// return log event store
			return logEventStore;
		}

		/**
		 * @see org.osgi.util.tracker.ServiceTracker#removedService(org.osgi.framework.ServiceReference,
		 *      java.lang.Object)
		 */
		@Override
		public void removedService(ServiceReference reference, Object service) {
			// unbind log event store from the histogram view contribution
			Activator.this._viewContribution.unbindLogEventStore(null);

			// call super method
			super.removedService(reference, service);
		}
	}
}
