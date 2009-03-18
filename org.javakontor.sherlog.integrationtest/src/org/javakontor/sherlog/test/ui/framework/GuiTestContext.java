package org.javakontor.sherlog.test.ui.framework;

import java.io.File;

import junit.framework.Assert;

import org.osgi.framework.BundleContext;

public class GuiTestContext {

  private final BundleContext _bundleContext;

  private final File          _workspaceLocation;

  public GuiTestContext(BundleContext bundleContext, File workspaceLocation) {
    Assert.assertNotNull(bundleContext);
    Assert.assertNotNull(workspaceLocation);
    _bundleContext = bundleContext;
    _workspaceLocation = workspaceLocation;
  }

  public BundleContext getBundleContext() {
    return _bundleContext;
  }

  public File getWorkspaceLocation() {
    return _workspaceLocation;
  }

  /**
   * Constructs a <code>String</code> with all attributes in name = value format.
   * 
   * @return a <code>String</code> representation of this object.
   */
  @Override
  public String toString() {

    final String retValue = "GuiTestContext ( " // prefix
        + super.toString() // add super attributes
        + ", _bundleContext = '" + this._bundleContext + "'" // _bundleContext
        + ", _workspaceLocation = '" + this._workspaceLocation + "'" // _workspaceLocation
        + " )";

    return retValue;
  }

}
