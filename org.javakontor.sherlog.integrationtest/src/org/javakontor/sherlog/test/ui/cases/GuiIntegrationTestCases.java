package org.javakontor.sherlog.test.ui.cases;

import org.javakontor.sherlog.test.ui.framework.GuiTestContext;
import org.javakontor.sherlog.test.ui.framework.GuiTestSupport;
import org.javakontor.sherlog.test.ui.handler.BundleListViewHandler;
import org.javakontor.sherlog.test.ui.handler.LoadLogFileWizardHandler;
import org.javakontor.sherlog.test.ui.handler.LogEventTableViewHandler;
import org.javakontor.sherlog.test.ui.handler.LogViewHandler;
import org.netbeans.jemmy.operators.JMenuItemOperator;

public class GuiIntegrationTestCases extends AbstractGuiIntegrationTestCases {

  public GuiIntegrationTestCases(GuiTestContext guiTestContext) {
    super(guiTestContext);
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
    final LogEventTableViewHandler logEventTableViewHandler = logViewHandler.getLogEventTableViewHandler();

    // ~ make sure, the two LogEvents are displayed
    GuiTestSupport.assertTrue(new GuiTestSupport.Condition() {

      public boolean isTrue() throws Throwable {
        return logEventTableViewHandler.getLogEventCount() == 2;
      }

      public String getFailMessage() {
        return "LogEventTable should contain exactly two LogEvents";
      }
    });

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
