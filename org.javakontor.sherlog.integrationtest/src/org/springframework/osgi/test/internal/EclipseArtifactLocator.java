package org.springframework.osgi.test.internal;

import java.io.File;

import org.ant4eclipse.core.Ant4EclipseConfigurator;
import org.ant4eclipse.pde.tools.TargetPlatform;
import org.ant4eclipse.pde.tools.TargetPlatformConfiguration;
import org.ant4eclipse.pde.tools.TargetPlatformDefinition;
import org.ant4eclipse.pde.tools.TargetPlatformRegistry;
import org.ant4eclipse.platform.model.resource.Workspace;
import org.ant4eclipse.platform.model.resource.workspaceregistry.DefaultEclipseWorkspaceDefinition;
import org.ant4eclipse.platform.model.resource.workspaceregistry.WorkspaceDefinition;
import org.ant4eclipse.platform.model.resource.workspaceregistry.WorkspaceRegistry;
import org.eclipse.osgi.service.resolver.BundleDescription;
import org.eclipse.osgi.service.resolver.State;
import org.osgi.framework.Version;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.osgi.test.provisioning.ArtifactLocator;

public class EclipseArtifactLocator implements ArtifactLocator {

  private State          _state;

  private TargetPlatform _targetPlatform;

  private File           _workspaceLocation;

  private File[]         _targetPlatformLocations;

  public EclipseArtifactLocator(File workspaceLocation, File[] targetPlatformLocations) {

    this._workspaceLocation = workspaceLocation;
    this._targetPlatformLocations = targetPlatformLocations;

    initialise();
  }

  /**
   * 
   */
  public void initialise() {
    
    Ant4EclipseConfigurator.configureAnt4Eclipse();
    
    WorkspaceRegistry workspaceRegistry = WorkspaceRegistry.Helper.getRegistry();
    TargetPlatformRegistry targetPlatformRegistry = TargetPlatformRegistry.Helper.getRegistry();
    
    WorkspaceDefinition workspaceDefinition = new DefaultEclipseWorkspaceDefinition(_workspaceLocation);
    Workspace workspace = workspaceRegistry.registerWorkspace("test-workspace", workspaceDefinition);
    
    TargetPlatformDefinition definition = new TargetPlatformDefinition();
    for (File file : _targetPlatformLocations) {
      definition.addLocation(file);
    }
    targetPlatformRegistry.addTargetPlatformDefinition("test-targetplatform", definition);
    
    _targetPlatform = targetPlatformRegistry.getInstance(workspace, "test-targetplatform", new TargetPlatformConfiguration());

    this._state = _targetPlatform.getState();

    BundleDescription[] bundleDescriptions = _state.getBundles();

    for (int i = 0; i < bundleDescriptions.length; i++) {
      BundleDescription bundleDescription = bundleDescriptions[i];

      System.out.println(bundleDescription.getBundleId() + " - " + bundleDescription.getSymbolicName() + "_"
          + bundleDescription.getVersion() + " : " + bundleDescription.isResolved());
    }
    
    //
    // BundleSet bundleSet = _targetPlatform.getBundleProjectSet();
    //
    // BundleDescription[] descriptions = bundleSet.getAllBundleDescriptions();
    //
    // for (int i = 0; i < descriptions.length; i++) {
    // BundleSource bundleSource = (BundleSource) descriptions[i].getUserObject();
    // JavaProjectRole javaProjectRole = (JavaProjectRole) bundleSource.getAsEclipseProject().getRole(
    // JavaProjectRole.class);
    //
    // System.err.println(descriptions[i].getSymbolicName() + "=" + javaProjectRole.getDefaultOutputFolder().getPath());
    //    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.springframework.osgi.test.provisioning.ArtifactLocator#locateArtifact(java.lang.String, java.lang.String,
   *      java.lang.String)
   */
  public Resource locateArtifact(final String group, final String id, final String version) {

    String useId = id;

    if (useId.equals("junit.osgi")) {
      useId = "org.springframework.osgi.junit.osgi";
    } else if (useId.equals("asm.osgi")) {
      useId = "org.springframework.osgi.asm.osgi";
    } else if (useId.equals("spring-aop")) {
      useId = "org.springframework.bundle.spring.aop";
    } else if (useId.equals("spring-beans")) {
      useId = "org.springframework.bundle.spring.beans";
    } else if (useId.equals("spring-context")) {
      useId = "org.springframework.bundle.spring.context";
    } else if (useId.equals("spring-core")) {
      useId = "org.springframework.bundle.spring.core";
    } else if (useId.equals("spring-test")) {
      useId = "org.springframework.bundle.spring.test";
    } else if (useId.equals("spring-osgi-annotation")) {
      useId = "org.springframework.bundle.osgi.extensions.annotations";
    } else if (useId.equals("spring-osgi-core")) {
      useId = "org.springframework.bundle.osgi.core";
    } else if (useId.equals("spring-osgi-extender")) {
      useId = "org.springframework.bundle.osgi.extender";
    } else if (useId.equals("spring-osgi-io")) {
      useId = "org.springframework.bundle.osgi.io";
    } else if (useId.equals("spring-osgi-test")) {
      useId = "org.springframework.bundle.osgi.test";
    } else if (useId.equals("log4j.osgi")) {
      useId = "com.springsource.org.apache.log4j";
    } else if (useId.equals("slf4j-api")) {
      useId = "com.springsource.slf4j.api";
    } else if (useId.equals("slf4j-log4j12")) {
      useId = "com.springsource.slf4j.log4j";
    } else if (useId.equals("jcl104-over-slf4j")) {
      useId = "com.springsource.slf4j.org.apache.commons.logging";
    } else if (useId.equals("aopalliance.osgi")) {
      useId = "com.springsource.org.aopalliance";
    }

    BundleDescription bundleDescription = null;
    try {
      bundleDescription = this._state.getBundle(useId, null);
    } catch (RuntimeException e) {
    }

    if (bundleDescription == null) {
      System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
      System.out.println(id + " : " + version);
      System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
      throw new RuntimeException("Bundle '" + id + "' (" + version + ") not found");
    }

    // System.out.println("***************************");
    // System.out.println("group: '" + group + "'");
    // System.out.println("id: '" + id + "'");
    // System.out.println("version: '" + version + "'");
    System.out.println(bundleDescription.getLocation());
    return new FileSystemResource(bundleDescription.getLocation());
  }

  public Resource locateArtifact(String group, String id, String version, String type) {

    System.out.println(this._state.getBundle(id, new Version(version)));

    // System.out.println("***************************");
    // System.out.println("group: '" + group + "'");
    // System.out.println("id: '" + id + "'");
    // System.out.println("version: '" + version + "'");
    // System.out.println("type: '" + type + "'");
    return null;
  }

}
