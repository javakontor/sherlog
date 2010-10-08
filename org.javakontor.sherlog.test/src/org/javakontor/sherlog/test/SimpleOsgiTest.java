package org.javakontor.sherlog.test;

import org.osgi.framework.Bundle;

/**
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class SimpleOsgiTest extends AbstractEclipseArtefactLocatorTest {


  public void testOsgiPlatformStarts() throws Exception {

    Bundle bundle = bundleContext.getBundle(2);

    System.err.println(bundle);

    bundle.uninstall();

    assertEquals(bundle.getState(), Bundle.UNINSTALLED);

    assertEquals(bundleContext.getBundle().getSymbolicName(),
        "TestBundle-testOsgiPlatformStarts-org.javakontor.sherlog.test.SimpleOsgiTest");
  }

  protected String[] getTestBundlesNames() {
    return new String[] { "null,org.javakontor.sherlog.util,1.0.0",
        "null,org.javakontor.sherlog.domain,1.0.0", "null,org.javakontor.sherlog.domain.impl,1.0.0",
        "null,org.eclipse.equinox.ds,1.0.0", "null,org.eclipse.equinox.log,1.0.0",
        "null,org.eclipse.equinox.util,1.0.0", "null,org.eclipse.osgi.services,1.0.0" };
  }
}
