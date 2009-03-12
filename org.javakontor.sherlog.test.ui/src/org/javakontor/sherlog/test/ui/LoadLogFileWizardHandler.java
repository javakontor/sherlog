package org.javakontor.sherlog.test.ui;

import org.netbeans.jemmy.operators.AbstractButtonOperator;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JDialogOperator;
import org.netbeans.jemmy.operators.WindowOperator;

public class LoadLogFileWizardHandler extends AbstractViewHandler {

  private final WindowOperator            _loadLogFileWizardOperator;

  private final JButtonOperator           _okButtonOperator;

  private final JButtonOperator           _cancelButtonOperator;

  private final LogFileChooserViewHandler _logFileChooserViewHandler;

  public LoadLogFileWizardHandler(WindowOperator parentOperator) {
    this._loadLogFileWizardOperator = new JDialogOperator(parentOperator, "Load log file");

    // TODO NLS ???
    this._okButtonOperator = new JButtonOperator(_loadLogFileWizardOperator,
        wrapTracingComponentChooser(new AbstractButtonOperator.AbstractButtonByLabelFinder("OK")));
    this._cancelButtonOperator = new JButtonOperator(_loadLogFileWizardOperator, "Cancel");

    this._logFileChooserViewHandler = new LogFileChooserViewHandler(_loadLogFileWizardOperator);
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

}
