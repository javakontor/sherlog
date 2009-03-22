package org.javakontor.sherlog.test.ui;

import org.javakontor.sherlog.test.ui.cases.DynamicIntegrationTestCases;
import org.javakontor.sherlog.test.ui.framework.GuiTestContext;

public class DynamicIntegrationTest extends AbstractGuiBasedIntegrationTest {

  public DynamicIntegrationTest() {
    super();
  }

  @Override
  protected String getImportPackages() {
    return "org.javakontor.sherlog.application.view";
  }

  @Override
  protected String[] getTestBundlesNames() {
    return concat(getBaseBundleNames(), new String[] { "null,org.javakontor.sherlog.util,1.0.0",
        "null,org.javakontor.sherlog.util.ui,1.0.0", "null,org.javakontor.sherlog.application,1.0.0",
        "null,org.javakontor.sherlog.application.mvc,1.0.0", "null,org.javakontor.sherlog.domain,1.0.0" });
  }

  public void test_dynamicManagementBundle() throws Exception {
    final DynamicIntegrationTestCases dynamicIntegrationTestCases = new DynamicIntegrationTestCases(new GuiTestContext(
        this.bundleContext, getWorkspaceLocation()));
    dynamicIntegrationTestCases.test_dynamicManagementBundle();
  }

  public void test_dynamicLogView() throws Exception {
    final DynamicIntegrationTestCases dynamicIntegrationTestCases = new DynamicIntegrationTestCases(new GuiTestContext(
        this.bundleContext, getWorkspaceLocation()));
    dynamicIntegrationTestCases.test_B();
  }
  //      
  //      
  // "null,org.javakontor.sherlog.util,1.0.0", "null,org.javakontor.sherlog.domain,1.0.0",
  // "null,org.javakontor.sherlog.domain.impl,1.0.0", "null,org.javakontor.org.netbeans.jemmy,0.0.1",
  // "null,org.eclipse.equinox.ds,1.0.0", "null,org.eclipse.equinox.log,1.0.0",
  // "null,org.eclipse.equinox.util,1.0.0", "null,org.eclipse.osgi.services,1.0.0",
  // "null,org.javakontor.sherlog.application,1.0.0", "null,org.javakontor.sherlog.application.mvc,1.0.0",
  // "null,org.javakontor.com.jgoodies.forms,1.2.1", "null,org.javakontor.sherlog.ui.loadwizard,1.0.0",
  // "null,org.javakontor.sherlog.ui.logview,1.0.0", "null,org.javakontor.sherlog.ui.filter,1.0.0",
  // "null,org.javakontor.sherlog.util.ui,1.0.0", "null,org.lumberjack.ui.logview.colorfilter,1.0.0",
  // "null,org.javakontor.sherlog.domain.logeventflavour.l4jbinary,1.0.0",
  // "null,org.javakontor.sherlog.ui.managementagent,1.0.0", };
  // }

}
