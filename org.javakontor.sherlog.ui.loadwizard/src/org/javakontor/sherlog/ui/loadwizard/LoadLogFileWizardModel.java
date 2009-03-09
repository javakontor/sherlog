package org.javakontor.sherlog.ui.loadwizard;

import org.javakontor.sherlog.core.reader.LogEventFlavour;
import org.javakontor.sherlog.ui.loadwizard.filechooser.LogFileChooserModel;
import org.lumberjack.application.mvc.AbstractModel;
import org.lumberjack.application.mvc.DefaultReasonForChange;

public class LoadLogFileWizardModel extends AbstractModel<LoadLogFileWizardModel, DefaultReasonForChange> {

  private LogFileChooserModel _logFileChooserModel;

  public LoadLogFileWizardModel(LogEventFlavour[] supportedLogEventFlavours) {
    this._logFileChooserModel = new LogFileChooserModel(supportedLogEventFlavours);
  }

  public LogFileChooserModel getLogFileChooserModel() {
    return this._logFileChooserModel;
  }

  public void setLogFileChooserModel(LogFileChooserModel logFileChooserModel) {
    this._logFileChooserModel = logFileChooserModel;
  }

  public ValidationResult validateForm() {
    return getLogFileChooserModel().validateForm();
  }

}
