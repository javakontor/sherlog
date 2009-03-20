package org.javakontor.sherlog.test.ui.cases;

import java.io.File;

import junit.framework.TestCase;

import org.javakontor.sherlog.test.ui.framework.GuiTestContext;
import org.javakontor.sherlog.test.ui.handler.ApplicationWindowHandler;
import org.javakontor.sherlog.test.ui.handler.BundleListViewHandler;
import org.javakontor.sherlog.test.ui.handler.LoadLogFileWizardHandler;
import org.javakontor.sherlog.test.ui.handler.LogViewHandler;
import org.netbeans.jemmy.operators.JPopupMenuOperator;

public class LoadLogFileWizardGuiTest extends TestCase {

  private final GuiTestContext _guiTestContext;

  ApplicationWindowHandler     _applicationWindowHandler;

  public LoadLogFileWizardGuiTest(GuiTestContext guiTestContext) {
    super();
    _guiTestContext = guiTestContext;
    _applicationWindowHandler = new ApplicationWindowHandler(guiTestContext);
  }

  /**
   * @throws Exception
   */
  public void test_LoadLogFile() throws Exception {

    _applicationWindowHandler.pushFileMenuItem("Load log file...", false);

    LoadLogFileWizardHandler loadLogFileWizardHandler = new LoadLogFileWizardHandler(_guiTestContext,
        _applicationWindowHandler.getApplicationFrameOperator());

    File binaryLogFile = new File(_guiTestContext.getWorkspaceLocation(),
        "org.javakontor.sherlog.domain.impl.test/logs/log_small.bin");
    assertTrue("The binary test-logfile '" + binaryLogFile.getAbsolutePath() + "' must be an existing file",
        binaryLogFile.isFile());
    String testLogFile = binaryLogFile.getAbsolutePath();

    loadLogFileWizardHandler.getLogFileChooserViewHandler().enterFileName(testLogFile);
    loadLogFileWizardHandler.getLogFileChooserViewHandler().selectLogEventFlavour("log4j");

    loadLogFileWizardHandler.getOkButtonOperator().clickMouse();
    loadLogFileWizardHandler.assertClosed();

    LogViewHandler logViewHandler = new LogViewHandler(_guiTestContext, _applicationWindowHandler
        .getApplicationFrameOperator());

    assertEquals(2, logViewHandler.getLogEventTableViewHandler().getLogEventTableTableOperator().getModel()
        .getRowCount());
    logViewHandler.getLogEventTableViewHandler().selectRow(0);
    JPopupMenuOperator contextMenu = logViewHandler.getLogEventTableViewHandler().openContextMenu(0);
    contextMenu.pushMenu("Mark|Mark with red");
    logViewHandler.getLogEventTableViewHandler().openContextMenu(0);
    contextMenu.pushMenu("Filter|Filter by red");
    assertEquals(1, logViewHandler.getLogEventTableViewHandler().getLogEventTableTableOperator().getModel()
        .getRowCount());
    logViewHandler.getLogEventTableViewHandler().selectRow(0);
    contextMenu = logViewHandler.getLogEventTableViewHandler().openContextMenu(0);
    contextMenu.pushMenu("Filter|Filter by red");
    assertEquals(2, logViewHandler.getLogEventTableViewHandler().getLogEventTableTableOperator().getModel()
        .getRowCount());

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
