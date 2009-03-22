package org.javakontor.sherlog.test.ui.cases;

import java.io.File;

import junit.framework.TestCase;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javakontor.sherlog.test.ui.framework.GuiTestContext;
import org.javakontor.sherlog.test.ui.handler.ApplicationWindowHandler;
import org.javakontor.sherlog.test.ui.handler.BundleListViewHandler;
import org.javakontor.sherlog.test.ui.handler.LoadLogFileWizardHandler;
import org.javakontor.sherlog.test.ui.handler.LogEventTableViewHandler;
import org.javakontor.sherlog.test.ui.handler.LogViewHandler;
import org.netbeans.jemmy.operators.JMenuItemOperator;

public class GuiIntegrationTest extends TestCase {
  private final Log            _logger = LogFactory.getLog(GuiIntegrationTest.class);

  private final GuiTestContext _guiTestContext;

  ApplicationWindowHandler     _applicationWindowHandler;

  public GuiIntegrationTest(GuiTestContext guiTestContext) {
    super();
    _guiTestContext = guiTestContext;
    _applicationWindowHandler = new ApplicationWindowHandler(guiTestContext);
  }

  protected String getTestLogFile() {
    File binaryLogFile = new File(_guiTestContext.getWorkspaceLocation(),
        "org.javakontor.sherlog.domain.impl.test/logs/log_small.bin");
    assertTrue("The binary test-logfile '" + binaryLogFile.getAbsolutePath() + "' must be an existing file",
        binaryLogFile.isFile());
    String testLogFile = binaryLogFile.getAbsolutePath();
    if (_logger.isDebugEnabled()) {
      _logger.debug(String.format("Using test log file '%s", testLogFile));
    }
    return testLogFile;
  }

  /**
   * @throws Exception
   */
  public void test_LoadFilterResetLogFile() throws Exception {

    // ~ get handler to the Log View
    // (the Log View should always be open, even if we have no Log File loaded yet)
    LogViewHandler logViewHandler = new LogViewHandler(_guiTestContext, _applicationWindowHandler
        .getApplicationFrameOperator());

    // Open LoadLogFile-Wizard
    LoadLogFileWizardHandler loadLogFileWizardHandler = LoadLogFileWizardHandler
        .openFromMenu(_applicationWindowHandler);

    // ~ open a Binary Log file with two LogEvents
    loadLogFileWizardHandler.openLogFile(getTestLogFile(), "log4j");

    // ~ Get the handler for the LogEventTableView
    LogEventTableViewHandler logEventTableViewHandler = logViewHandler.getLogEventTableViewHandler();

    // ~ make sure, the two LogEvents are displayed
    assertEquals(2, logEventTableViewHandler.getLogEventCount());

    // ~ mark first LogEvent with 'red'
    logEventTableViewHandler.pushContextMenu(0, "Mark|Mark with red");

    // ~ filter only 'red' Log Events
    JMenuItemOperator filterByRedMenuItem = logEventTableViewHandler.pushContextMenu(0, "Filter|Filter by red");
    assertTrue(filterByRedMenuItem.isSelected());
    assertEquals(1, logEventTableViewHandler.getLogEventCount());

    // ~ remove filter and make sure all Log Events are visible again
    filterByRedMenuItem = logEventTableViewHandler.pushContextMenu(0, "Filter|Filter by red");
    assertFalse(filterByRedMenuItem.isSelected());
    assertEquals(2, logEventTableViewHandler.getLogEventCount());

    // ~ reset the log event store
    _applicationWindowHandler.pushFileMenuItem("Reset logstore", true);
    assertEquals(0, logEventTableViewHandler.getLogEventCount());

  }

  public void test_BundleView() throws Exception {

    // open Bundle-List via menu
    BundleListViewHandler bundleListViewHandler = BundleListViewHandler.openFromMenu(_applicationWindowHandler);
    assertNotNull(bundleListViewHandler);

    // make sure, rows displayed in the table are equal to the number of installed bundles
    int rows = bundleListViewHandler.getBundleListTableOperator().getModel().getRowCount();
    assertEquals(_guiTestContext.getBundleContext().getBundles().length, rows);
  }

}
