package org.javakontor.sherlog.test.ui.framework;

import org.netbeans.jemmy.operators.AbstractButtonOperator;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JDialogOperator;
import org.netbeans.jemmy.operators.WindowOperator;

public class LoadLogFileWizardHandler extends AbstractViewContributionHandler {

  public final static String              LOAD_LOGFILE_WIZARD_NAME = "Load log file";

  private final WindowOperator            _loadLogFileWizardOperator;

  private final JButtonOperator           _okButtonOperator;

  private final JButtonOperator           _cancelButtonOperator;

  private final LogFileChooserViewHandler _logFileChooserViewHandler;

  public LoadLogFileWizardHandler(GuiTestContext testContext, WindowOperator parentOperator) throws Exception {
    super(testContext);
    this._loadLogFileWizardOperator = new JDialogOperator(parentOperator, LOAD_LOGFILE_WIZARD_NAME);
    getGuiTestSupport().assertViewContributionRegistered("Load log file");

    // TODO NLS ???
    this._okButtonOperator = new JButtonOperator(_loadLogFileWizardOperator,
        wrapTracingComponentChooser(new AbstractButtonOperator.AbstractButtonByLabelFinder("OK")));
    this._cancelButtonOperator = new JButtonOperator(_loadLogFileWizardOperator, "Cancel");

    this._logFileChooserViewHandler = new LogFileChooserViewHandler(testContext, _loadLogFileWizardOperator);
  }

  public WindowOperator getLoadLogFileWizardOperator() {
    return _loadLogFileWizardOperator;
  }

  public JButtonOperator getOkButtonOperator() {
    return _okButtonOperator;
  }

  public JButtonOperator getCancelButtonOperator() {
    return _cancelButtonOperator;
  }

  public LogFileChooserViewHandler getLogFileChooserViewHandler() {
    return _logFileChooserViewHandler;
  }

  public void assertClosed() throws Exception {
    assertFalse(_loadLogFileWizardOperator.isVisible());
    getGuiTestSupport().assertNoViewContributionRegistered(LOAD_LOGFILE_WIZARD_NAME);
  }

}
