package org.javakontor.sherlog.test.ui;

import java.io.File;

import org.javakontor.sherlog.test.ui.cases.GuiIntegrationTest;
import org.javakontor.sherlog.test.ui.framework.GuiTestContext;
import org.springframework.osgi.test.internal.AbstractEclipseArtefactLocatorTest;

public class GuiIntegrationTestAdapter extends AbstractEclipseArtefactLocatorTest {

  public void test_LoadFilterResetLogFile() throws Exception {
    GuiIntegrationTest test = new GuiIntegrationTest(new GuiTestContext(bundleContext,
        getWorkspaceLocation()));
    test.test_LoadFilterResetLogFile();

  }

  public void test_BundleView() throws Exception {
    GuiIntegrationTest test = new GuiIntegrationTest(new GuiTestContext(bundleContext,
        getWorkspaceLocation()));
    test.test_BundleView();

  }

  @Override
  protected String[] getTestBundlesNames() {
    return new String[] { "null,org.javakontor.sherlog.util,1.0.0", "null,org.javakontor.sherlog.domain,1.0.0",
        "null,org.javakontor.sherlog.domain.impl,1.0.0", "null,org.javakontor.org.netbeans.jemmy,0.0.1",
        "null,org.eclipse.equinox.ds,1.0.0", "null,org.eclipse.equinox.log,1.0.0",
        "null,org.eclipse.equinox.util,1.0.0", "null,org.eclipse.osgi.services,1.0.0",
        "null,org.javakontor.sherlog.application,1.0.0", "null,org.javakontor.sherlog.application.mvc,1.0.0",
        "null,org.javakontor.com.jgoodies.forms,1.2.1", "null,org.javakontor.sherlog.ui.loadwizard,1.0.0",
        "null,org.javakontor.sherlog.ui.logview,1.0.0", "null,org.javakontor.sherlog.ui.filter,1.0.0",
        "null,org.javakontor.sherlog.util.ui,1.0.0", "null,org.lumberjack.ui.logview.colorfilter,1.0.0",
        "null,org.javakontor.sherlog.domain.logeventflavour.l4jbinary,1.0.0",
        "null,org.javakontor.sherlog.ui.managementagent,1.0.0", };
  }

  @Override
  protected String getManifestLocation() {
    File mfLocation = new File(getWorkspaceLocation(), "org.javakontor.sherlog.integrationtest/META-INF/MANIFEST.MF");
    return "file:" + mfLocation.getAbsolutePath();// D:/lumberjack/workspace/org.javakontor.sherlog.integrationtest/META-INF/MANIFEST.MF";
  }

}
