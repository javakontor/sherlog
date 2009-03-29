package org.javakontor.sherlog.ui.colorfilter;

import org.javakontor.sherlog.domain.filter.LogEventFilterFactory;
import org.javakontor.sherlog.ui.logview.decorator.LogEventTableCellDecorator;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {
  ServiceRegistration registerService;

  /**
   * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
   */
  public void start(final BundleContext context) throws Exception {
    context.registerService(LogEventFilterFactory.class.getName(), new ColorFilterFactory(), null);

    final ColorFilterMenus colorFilterMenus = new ColorFilterMenus();
    colorFilterMenus.registerMenus(context);

    this.registerService = context.registerService(LogEventTableCellDecorator.class.getName(), new ColorDecorator(),
        null);
  }

  public void stop(final BundleContext arg0) throws Exception {
    //
  }
}
