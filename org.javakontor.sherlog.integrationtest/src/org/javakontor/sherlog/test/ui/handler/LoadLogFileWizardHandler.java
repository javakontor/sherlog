package org.javakontor.sherlog.test.ui.handler;

import org.javakontor.sherlog.test.ui.framework.AbstractViewContributionHandler;
import org.javakontor.sherlog.test.ui.framework.GuiTestContext;
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

  /**
   * Creates a new LoadLogFileWizardHandler for an already existing and visible "Load LogFile Wizard"
   * 
   * <p>
   * Consider using {@link #openFromMenu(ApplicationWindowHandler)} to open a Load LogFile Wizard via the "File" menu
   * </p>
   * 
   * @param testContext
   * @param parentOperator
   * @throws Exception
   * 
   * 
   */
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

  /**
   * Opens a new Load Log File Wizard using the File menu
   * 
   * @param applicationWindowHandler
   * @return the {@link LoadLogFileWizardHandler} for the wizard that has been opened
   * @throws Exception
   */
  public static LoadLogFileWizardHandler openFromMenu(ApplicationWindowHandler applicationWindowHandler)
      throws Exception {
    assertNotNull(applicationWindowHandler);
    applicationWindowHandler.pushFileMenuItem("Load log file...", true);

    LoadLogFileWizardHandler loadLogFileWizardHandler = new LoadLogFileWizardHandler(applicationWindowHandler
        .getGuiTestContext(), applicationWindowHandler.getApplicationFrameOperator());
    return loadLogFileWizardHandler;
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

  /**
   * Loads the specified testLogFile with the specified logEventFlavour using the LoadLogFileWizard-Dialog.
   * 
   * <p>
   * The dialog will be finished with the OK-Button and this method makes sure that the dialog is closed after the
   * button has been pressed.
   * 
   * @param testLogFile
   *          the name of the logfile to load (will be entered in the Log-File text box)
   * @param logEventFlavour
   *          the name of the logEventFlavor (will be selected from the flavour combo box)
   * @throws Exception
   */
  public void openLogFile(String testLogFile, String logEventFlavour) throws Exception {
    // ~ fill in logfilename and select flavour
    getLogFileChooserViewHandler().enterFileName(testLogFile);
    getLogFileChooserViewHandler().selectLogEventFlavour(logEventFlavour);

    // ~ click on the OK-Button
    getOkButtonOperator().clickMouse();

    // ~ make sure dialog is closed an un-registered from the Service Registry
    assertClosed();
  }

}
