package org.javakontor.sherlog.ui.histogram;

import org.lumberjack.application.view.ViewContribution;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

  /*
   * (non-Javadoc)
   *
   * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
   */
  public void start(BundleContext context) throws Exception {
    context.registerService(ViewContribution.class.getName(), new LoggraphViewContribution(), null);
  }

  /*
   * (non-Javadoc)
   *
   * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
   */
  public void stop(BundleContext context) throws Exception {
  }
}
