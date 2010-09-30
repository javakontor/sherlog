package org.javakontor.sherlog.application.extender;

import org.osgi.framework.BundleContext;

/**
 * <p>
 * Call back interface for the usage with the application (action) extender.
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface BundleContextAware {

  /**
   * <p>
   * Sets the bundle context.
   * </p>
   *
   * @param bundleContext
   *          the bundle context
   */
  public void setBundleContext(BundleContext bundleContext);
}
