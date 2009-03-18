package org.javakontor.sherlog.ui.loadwizard.filechooser;

import static java.lang.String.format;

import java.io.File;

import org.javakontor.sherlog.application.mvc.AbstractModel;
import org.javakontor.sherlog.application.mvc.DefaultReasonForChange;
import org.javakontor.sherlog.application.mvc.ModelChangedEvent;
import org.javakontor.sherlog.domain.reader.LogEventFlavour;
import org.javakontor.sherlog.ui.loadwizard.LoadLogFileWizardMessages;
import org.javakontor.sherlog.ui.loadwizard.ValidationResult;

public class LogFileChooserModel extends AbstractModel<LogFileChooserModel, DefaultReasonForChange> {

  private LogEventFlavour   _selectedLogEventFlavour;

  private String            _fileName;

  private LogEventFlavour[] _supportedLogEventFlavours;

  public LogFileChooserModel(LogEventFlavour... supportedFlavours) {
    this._supportedLogEventFlavours = supportedFlavours;
  }

  public LogEventFlavour[] getSupportedLogEventFlavours() {
    return this._supportedLogEventFlavours;
  }

  public void setSupportedLogEventFlavours(LogEventFlavour[] supportedLogEventFlavours) {
    Object oldValue = this._supportedLogEventFlavours;
    this._supportedLogEventFlavours = supportedLogEventFlavours;
    fireModelChangedEvent(oldValue, this._supportedLogEventFlavours);
  }

  public LogEventFlavour getSelectedLogEventFlavour() {
    return this._selectedLogEventFlavour;
  }

  public void setSelectedLogEventFlavour(LogEventFlavour selectedFlavour) {
    Object oldValue = this._selectedLogEventFlavour;
    this._selectedLogEventFlavour = selectedFlavour;
    fireModelChangedEvent(oldValue, this._selectedLogEventFlavour);
  }

  public String getFileName() {
    return this._fileName;
  }

  public void setFileName(String fileName) {
    Object oldValue = this._fileName;
    this._fileName = fileName;
    fireModelChangedEvent(oldValue, this._fileName);
  }

  /**
   * Fires a {@link ModelChangedEvent} only if oldValue differs from newValue
   * 
   * @param oldValue
   * @param newValue
   */
  protected void fireModelChangedEvent(Object oldValue, Object newValue) {
    if ((oldValue == null && newValue == null) || oldValue != null && newValue != null && oldValue.equals(newValue)) {
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
