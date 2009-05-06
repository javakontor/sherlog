package org.javakontor.sherlog.application.extender;

import org.osgi.framework.BundleContext;

public interface BundleContextAware {

  public void setBundleContext(BundleContext bundleContext);
}
