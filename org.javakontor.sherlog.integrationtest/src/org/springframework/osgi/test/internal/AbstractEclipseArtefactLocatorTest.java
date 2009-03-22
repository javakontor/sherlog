package org.springframework.osgi.test.internal;

import java.io.File;

import org.springframework.osgi.test.AbstractConfigurableBundleCreatorTests;
import org.springframework.osgi.test.platform.OsgiPlatform;
import org.springframework.osgi.test.provisioning.ArtifactLocator;

public abstract class AbstractEclipseArtefactLocatorTest extends AbstractConfigurableBundleCreatorTests {

  /** - */
  private EclipseArtifactLocator _eclipseArtifactLocator;

  protected File[] getTargetPlatformLocations() {
    File root = new File(getWorkspaceLocation(), "target.platform");
    File[] targetPlatformLocations = new File[8];

    targetPlatformLocations[0] = new File(root, "equinox-3.5/plugins");
    targetPlatformLocations[1] = new File(root, "ebr");
    targetPlatformLocations[2] = new File(root, "javakontor");
    targetPlatformLocations[3] = new File(root, "test/jemmy");
    targetPlatformLocations[4] = new File(root, "codehaus");
    targetPlatformLocations[5] = new File(root, "test/ebr");
    targetPlatformLocations[6] = new File(root, "test/spring-2.5.5");
    targetPlatformLocations[7] = new File(root, "test/spring-dm-1.1.3");

    return targetPlatformLocations;
  }

  protected EclipseArtifactLocator createArtifactLocator() {
    File[] targetPlatformLocations = getTargetPlatformLocations();
    File workspaceLocation = getWorkspaceLocation();
    return new EclipseArtifactLocator(workspaceLocation, targetPlatformLocations);
  }

  @Override
  protected OsgiPlatform createPlatform() {
    this._eclipseArtifactLocator = createArtifactLocator();

    System.setProperty("osgi.dev", "bin");

    return super.createPlatform();
  }

  protected File getWorkspaceLocation() {
    File userDir = new File(System.getProperty("user.dir"));
    File workspaceDir = userDir.getParentFile();
    return workspaceDir;
  }

  @Override
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

  @Override
  protected ArtifactLocator getLocator() {
    return this._eclipseArtifactLocator;
  }

  @Override
  protected String getRootPath() {
    return "file:bin/";
  }

}
