package org.javakontor.sherlog.test.ui.framework;

import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javakontor.sherlog.application.view.ViewContribution;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.springframework.osgi.util.OsgiBundleUtils;
import org.springframework.osgi.util.OsgiStringUtils;

public class GuiTestSupport extends Assert {

  Log                          _logger = LogFactory.getLog(GuiTestSupport.class);

  private final GuiTestContext _testContext;

  public GuiTestSupport(GuiTestContext testContext) {
    assertNotNull(testContext);
    _testContext = testContext;
  }

  protected BundleContext getBundleContext() {
    return _testContext.getBundleContext();
  }

  /**
   * Installs the specified plug-in project to the OSGi platform
   * 
   * <p>
   * Installing bundles from the target platform is not supported!
   * </p>
   * 
   * 
   * @param bundleProjectName
   *          the name of the bundle project inside the workspace
   * @return
   * @throws Exception
   */
  public Bundle installBundle(String bundleProjectName) throws Exception {
    File bundleProjectLocation = new File(_testContext.getWorkspaceLocation(), bundleProjectName);
    assertTrue("Bundle-Project '" + bundleProjectName + "' not found at location '" + bundleProjectLocation + "'",
        bundleProjectLocation.exists());

    Bundle bundle = installBundle(bundleProjectLocation);
    assertNotNull(bundle);
    assertTrue(bundle.getState() == Bundle.RESOLVED);
    return bundle;
  }

  /**
   * Starts the specified bundle and prints a nice logging message in case of failure.
   * 
   * <p>
   * Copied from org.springframework.osgi.test.AbstractOsgiTests
   * </p>
   * 
   * @param bundle
   * @return
   * @throws BundleException
   */
  public void startBundle(Bundle bundle) throws BundleException {
    boolean debug = _logger.isDebugEnabled();
    String info = "[" + OsgiStringUtils.nullSafeNameAndSymName(bundle) + "|" + bundle.getLocation() + "]";

    if (!OsgiBundleUtils.isFragment(bundle)) {
      if (debug)
        _logger.debug("Starting " + info);
      try {
        bundle.start();
      } catch (BundleException ex) {
        _logger.error("cannot start bundle " + info, ex);
        throw ex;
      }
    }

    else if (debug)
      _logger.debug(info + " is a fragment; start not invoked");
  }

  /**
   * Installs an OSGi bundle from the given location.
   * 
   * <p>
   * Copied from org.springframework.osgi.test.AbstractOsgiTests
   * </p>
   * 
   * @param location
   * @return
   * @throws Exception
   */
  protected Bundle installBundle(File location) throws Exception {
    Assert.assertNotNull(location);
    if (_logger.isDebugEnabled())
      _logger.debug("Installing bundle from location " + location);

    String bundleLocation;

    try {
      bundleLocation = URLDecoder.decode(location.toURL().toExternalForm(), "UTF-8");
    } catch (Exception ex) {
      // the URL cannot be created, fall back to the description
      bundleLocation = location.getAbsolutePath();
    }

    if (location.isFile()) {
      return _testContext.getBundleContext().installBundle(bundleLocation, new FileInputStream(location));
    } else {
      return _testContext.getBundleContext().installBundle(bundleLocation);
    }
  }

  public void assertNoViewContributionRegistered(String dialogName) throws InvalidSyntaxException {
    ServiceReference[] serviceReferences = getBundleContext().getServiceReferences(ViewContribution.class.getName(),
        null);

    for (ServiceReference serviceReference : serviceReferences) {
      ViewContribution viewContribution = (ViewContribution) getBundleContext().getService(serviceReference);
      if (viewContribution != null) {
        assertFalse("There should be no ViewContribution with name '" + dialogName + "' registered", dialogName
            .equals(viewContribution.getDescriptor().getName()));
      }
    }
  }

  /**
   * Asserts that exactly one ViewContribution with the given dialogName is currently registered at the Service Registry
   * 
   * @param dialogName
   * @throws InvalidSyntaxException
   */
  public void assertViewContributionRegistered(String dialogName) throws InvalidSyntaxException {
    ServiceReference[] serviceReferences = getBundleContext().getServiceReferences(ViewContribution.class.getName(),
        null);

    ViewContribution matchingContribution = null;

    for (ServiceReference serviceReference : serviceReferences) {
      ViewContribution viewContribution = (ViewContribution) getBundleContext().getService(serviceReference);
      if (viewContribution != null && dialogName.equals(viewContribution.getDescriptor().getName())) {
        if (matchingContribution == null) {
          matchingContribution = viewContribution;
        } else {
          // more than one contribution with same name -> error
          fail("There should be exactly one ViewContribution registered with dialogname '" + dialogName + "'");
        }
      }
    }

    assertNotNull("There should be a ViewContribution registered with dialogName '" + dialogName + "'",
        matchingContribution);
  }

}
