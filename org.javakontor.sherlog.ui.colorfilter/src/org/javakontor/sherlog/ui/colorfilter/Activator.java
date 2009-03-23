package org.javakontor.sherlog.ui.colorfilter;

import org.javakontor.sherlog.ui.logview.decorator.LogEventTableCellDecorator;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

  /**
   * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
   */
  public void start(BundleContext context) throws Exception {

    ColorFilterMenus colorFilterMenus = new ColorFilterMenus();
    colorFilterMenus.registerMenus(context);
    
    context.registerService(LogEventTableCellDecorator.class.getName(), new ColorDecorator(), null);
  }

  public void stop(BundleContext arg0) throws Exception {
    //
  }
}
