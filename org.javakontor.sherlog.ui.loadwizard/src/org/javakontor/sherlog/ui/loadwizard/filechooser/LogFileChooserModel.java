package org.javakontor.sherlog.ui.loadwizard.filechooser;

import static java.lang.String.format;

import java.io.File;

import org.javakontor.sherlog.core.reader.LogEventFlavour;
import org.javakontor.sherlog.ui.loadwizard.LoadLogFileWizardMessages;
import org.javakontor.sherlog.ui.loadwizard.ValidationResult;
import org.lumberjack.application.mvc.AbstractModel;
import org.lumberjack.application.mvc.DefaultReasonForChange;

public class LogFileChooserModel extends AbstractModel<LogFileChooserModel, DefaultReasonForChange> {

  private LogEventFlavour   _selectedLogEventFlavour;

  private String            _fileName;

  private LogEventFlavour[] _supportedLogEventFlavours;

  public LogFileChooserModel(LogEventFlavour[] supportedFlavours) {
    this._supportedLogEventFlavours = supportedFlavours;
  }

  public LogEventFlavour[] getSupportedLogEventFlavours() {
    return _supportedLogEventFlavours;
  }

  public void setSupportedLogEventFlavours(LogEventFlavour[] supportedLogEventFlavours) {
    Object oldValue = _supportedLogEventFlavours;
    _supportedLogEventFlavours = supportedLogEventFlavours;
    fireModelChangedEvent(oldValue, _supportedLogEventFlavours);
  }

  public LogEventFlavour getSelectedLogEventFlavour() {
    return this._selectedLogEventFlavour;
  }

  public void setSelectedLogEventFlavour(LogEventFlavour selectedFlavour) {
    Object oldValue = _selectedLogEventFlavour;
    this._selectedLogEventFlavour = selectedFlavour;
    fireModelChangedEvent(oldValue, _selectedLogEventFlavour);
  }

  public String getFileName() {
    return this._fileName;
  }

  public void setFileName(String fileName) {
    Object oldValue = _fileName;
    this._fileName = fileName;
    fireModelChangedEvent(oldValue, _fileName);
  }

  protected void fireModelChangedEvent(Object oldValue, Object newValue) {
    if (oldValue != null && newValue != null && oldValue.equals(newValue)) {
      return;
    }
    fireModelChangedEvent(DefaultReasonForChange.modelChanged);

  }

  public ValidationResult validateForm() {

    ValidationResult result = validateSelectedLogFile();
    if (result.hasError()) {
      return result;
    }

    if (this._selectedLogEventFlavour == null) {
      return new ValidationResult(LoadLogFileWizardMessages.noFlavourSelected);
    }

    return new ValidationResult();
  }

  public ValidationResult validateSelectedLogFile() {
    File file = getSelectedLogFile();
    if (file == null) {
      return new ValidationResult(LoadLogFileWizardMessages.noFileSelected);
    }
    if (!file.isFile()) {
      return new ValidationResult(format(LoadLogFileWizardMessages.selectedFileIsNotAFile, file.getAbsolutePath()));
    }

    return new ValidationResult();
  }

  public File getSelectedLogFile() {
    if ((this._fileName == null) || (this._fileName.trim().length() < 1)) {
      return null;
    }

    return new File(this._fileName);
  }
}