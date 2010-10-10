package org.javakontor.sherlog.test.ui;

import org.javakontor.sherlog.application.action.ActionGroupElement;
import org.javakontor.sherlog.test.ui.cases.DynamicIntegrationTestCases;
import org.javakontor.sherlog.test.ui.framework.GuiTestContext;

public class DynamicIntegrationTest extends AbstractGuiBasedIntegrationTest {

  public DynamicIntegrationTest() {
    super();
  }

  @Override
  protected String getImportPackages() {
    return "org.javakontor.sherlog.application.view," // view
        + "org.javakontor.sherlog.domain,"
        + "org.javakontor.sherlog.domain.impl.reader,"
        + "org.javakontor.sherlog.domain.reader,"
        + "org.javakontor.sherlog.domain.store,"
        + ActionGroupElement.class.getPackage().getName();
  }

  @Override
  protected String[] getTestBundlesNames() {
    return concat(getBaseBundleNames(), new String[] { "null,org.javakontor.sherlog.util,1.0.0",
        "null,org.javakontor.sherlog.util.ui,1.0.0", "null,org.javakontor.sherlog.application,1.0.0",
        "null,org.javakontor.sherlog.application.mvc,1.0.0", "null,org.javakontor.sherlog.domain,1.0.0",
        "null,org.javakontor.sherlog.domain.impl,1.0.0" });
  }

  private DynamicIntegrationTestCases _testCases;

  @Override
  protected void onSetUp() throws Exception {
    super.onSetUp();
    this._testCases = new DynamicIntegrationTestCases(new GuiTestContext(this.bundleContext, getWorkspaceLocation()));
  }

  @Override
  protected void onTearDown() throws Exception {
    this._testCases.dispose();
    this._testCases = null;
    super.onTearDown();
  }

  public void test_dynamicManagementBundle() throws Exception {
    this._testCases.test_dynamicManagementBundle();
  }

  public void test_dynamicLogView() throws Exception {
    this._testCases.test_dynamicLogView();
  }

  public void test_restartDynamicServices() throws Exception {
    this._testCases.test_restartDynamicServices();
  }

  public void test_dynamicColorFilter() throws Exception {
    this._testCases.test_dynamicColorFilter();
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
