package org.javakontor.sherlog.ui.loadwizard;

import static java.lang.String.format;

import java.io.File;

import org.javakontor.sherlog.core.reader.LogEventFlavour;
import org.javakontor.sherlog.core.reader.LogEventReaderFactory;
import org.lumberjack.application.mvc.AbstractModel;
import org.lumberjack.application.mvc.DefaultReasonForChange;

public class LogFileChooserModel extends AbstractModel<LogFileChooserModel, DefaultReasonForChange> {

  private final LogEventReaderFactory _logEventReaderFactory;

  private LogEventFlavour             _selectedFlavour;

  private String                      _fileName;

  public LogFileChooserModel(LogEventReaderFactory logEventReaderFactory) {
    super();
    this._logEventReaderFactory = logEventReaderFactory;
  }

  public LogEventFlavour[] getSupportedFlavours() {
    return this._logEventReaderFactory.getSupportedLogEventFlavours();
  }

  public LogEventFlavour getSelectedFlavour() {
    return this._selectedFlavour;
  }

  public void setSelectedFlavour(LogEventFlavour selectedFlavour) {
    this._selectedFlavour = selectedFlavour;
    fireModelChangedEvent(DefaultReasonForChange.modelChanged);
  }

  public String getFileName() {
    return this._fileName;
  }

  public void setFileName(String fileName) {
    this._fileName = fileName;
    fireModelChangedEvent(DefaultReasonForChange.modelChanged);
  }

  public ValidationResult validateForm() {

    ValidationResult result = validateSelectedLogFile();
    if (result.hasError()) {
      return result;
    }

    if (this._selectedFlavour == null) {
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
