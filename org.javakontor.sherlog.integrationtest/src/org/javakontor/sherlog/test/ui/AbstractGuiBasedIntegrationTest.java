package org.javakontor.sherlog.test.ui;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.jar.Manifest;

import org.springframework.osgi.test.internal.AbstractEclipseArtefactLocatorTest;
import org.springframework.util.StringUtils;

public abstract class AbstractGuiBasedIntegrationTest extends AbstractEclipseArtefactLocatorTest {

  // @Override
  // protected String getManifestLocation() {
  // final File mfLocation = new File(getWorkspaceLocation(),
  // "org.javakontor.sherlog.integrationtest/META-INF/MANIFEST.MF");
  // return "file:" + mfLocation.getAbsolutePath();
  // }

  protected String[] concat(final String[]... stringArrays) {
    final List<String> list = new LinkedList<String>();
    for (final String[] stringArray : stringArrays) {
      list.addAll(Arrays.asList(stringArray));
    }

    return list.toArray(new String[list.size()]);
  }

  @Override
  protected Manifest getManifest() {
    final Manifest manifest = super.getManifest();

    final String importPackages = getImportPackages();
    String importPackageHeader = getBasePackageImports();
    if (StringUtils.hasText(importPackages)) {
      importPackageHeader += "," + importPackages;
    }
    this.logger.info("USING IMPORT_PACKAGE-HEADER: " + importPackageHeader);

    manifest.getMainAttributes().putValue("Import-Package", importPackageHeader);
    return manifest;
  }

  protected abstract String getImportPackages();

  /**
   * @return
   */
  protected String[] getBaseBundleNames() {
    return new String[] { "null,org.javakontor.org.netbeans.jemmy,0.0.1", //
        "null,org.eclipse.equinox.ds,1.0.0",//
        "null,org.eclipse.equinox.log,1.0.0", // 
        "null,org.eclipse.equinox.util,1.0.0",//
        "null,org.eclipse.osgi.services,1.0.0", // 
        "null,org.javakontor.com.jgoodies.forms,1.2.1"//
    };
  }

  protected String getBasePackageImports() {
    return "junit.framework;version=\"3.8.2\"," //
        + "org.apache.commons.logging;version=\"1.1.1\","//
        + "org.netbeans.jemmy,"//
        + "org.netbeans.jemmy.operators,"//
        + "org.netbeans.jemmy.util,"//
        + "org.osgi.framework,"//
        + "org.slf4j,"//
        + "org.springframework.osgi.test,"//
        + "org.springframework.osgi.test.platform,"//
        + "org.springframework.osgi.test.provisioning,"//
        + "org.springframework.osgi.util;version=\"1.1.3\"";
  }
}
