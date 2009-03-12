package org.javakontor.sherlog.test;

import java.io.File;

import org.osgi.framework.Bundle;
import org.springframework.osgi.test.platform.OsgiPlatform;
import org.springframework.osgi.test.provisioning.ArtifactLocator;

/**
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class SimpleOsgiTest extends AbstractEclipseArtefactLocatorTest {

  /** - */
  private EclipseArtifactLocator _eclipseArtifactLocator;

  protected OsgiPlatform createPlatform() {

    File root = new File(getWorkspaceLocation(), "target.platform");

    File[] targetPlatformLocations = new File[6];

    targetPlatformLocations[0] = new File(root, "equinox-3.5/plugins");
    targetPlatformLocations[1] = new File(root, "ebr");
    targetPlatformLocations[2] = new File(root, "codehaus");
    targetPlatformLocations[3] = new File(root, "test/ebr");
    targetPlatformLocations[4] = new File(root, "test/spring-2.5.5");
    targetPlatformLocations[5] = new File(root, "test/spring-dm-1.1.3");

    File workspaceLocation =getWorkspaceLocation();

    this._eclipseArtifactLocator = new EclipseArtifactLocator(workspaceLocation, targetPlatformLocations);

    System.setProperty("osgi.dev", "bin");

    return super.createPlatform();
  }
  
  protected File getWorkspaceLocation() {
    File userDir = new File(System.getProperty("user.dir"));
    File workspaceDir = userDir.getParentFile();
    return workspaceDir;
  }

  protected String getManifestLocation() {
    // TODO Auto-generated method stub

    System.err.println();
    System.err.println();
    System.err.println();
    System.err.println();

    System.err.println("**************************************");
    System.err.println("ManifestLocation: ");
    System.err.println(super.getManifestLocation());
    System.err.println("**************************************");

    System.err.println();
    System.err.println();
    System.err.println();
    System.err.println();

    return super.getManifestLocation();
  }

  public void testOsgiPlatformStarts() throws Exception {

    Bundle bundle = bundleContext.getBundle(2);

    System.err.println(bundle);

    bundle.uninstall();

    assertEquals(bundle.getState(), Bundle.UNINSTALLED);

    assertEquals(bundleContext.getBundle().getSymbolicName(),
        "TestBundle-testOsgiPlatformStarts-org.javakontor.sherlog.test.SimpleOsgiTest");
  }

  protected ArtifactLocator getLocator() {
    return this._eclipseArtifactLocator;
  }

  protected String getRootPath() {
    return "file:bin/";
  }

  protected String[] getTestBundlesNames() {
    return new String[] { "null,org.javakontor.sherlog.core,1.0.0", "null,org.javakontor.sherlog.util,1.0.0",
        "null,org.javakontor.sherlog.core,1.0.0", "null,org.javakontor.sherlog.core.impl,1.0.0",
        "null,org.eclipse.equinox.ds,1.0.0", "null,org.eclipse.equinox.log,1.0.0",
        "null,org.eclipse.equinox.util,1.0.0", "null,org.eclipse.osgi.services,1.0.0" };
  }
}
