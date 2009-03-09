package org.javakontor.sherlog.ui.loadwizard;

import java.net.MalformedURLException;
import java.net.URL;

import org.javakontor.sherlog.core.impl.reader.BatchLogEventHandler;
import org.javakontor.sherlog.core.reader.LogEventFlavour;
import org.javakontor.sherlog.core.reader.LogEventReader;
import org.javakontor.sherlog.core.reader.LogEventReaderFactory;
import org.javakontor.sherlog.core.store.ModifiableLogEventStore;
import org.javakontor.sherlog.ui.loadwizard.filechooser.LogFileChooserModel;
import org.lumberjack.application.mvc.AbstractModel;
import org.lumberjack.application.mvc.DefaultReasonForChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadLogFileWizardModel extends AbstractModel<LoadLogFileWizardModel, DefaultReasonForChange> {
  Logger                                _logger = LoggerFactory.getLogger(getClass());

  private final LogEventReaderFactory   _logEventReaderFactory;

  private final ModifiableLogEventStore _logEventStore;

  private LogFileChooserModel           _logFileChooserModel;

  public LoadLogFileWizardModel(ModifiableLogEventStore logEventStore, LogEventReaderFactory logEventReaderFactory) {
    this._logEventStore = logEventStore;
    this._logEventReaderFactory = logEventReaderFactory;

    this._logFileChooserModel = new LogFileChooserModel(logEventReaderFactory.getSupportedLogEventFlavours());
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

  public void loadLogFile() throws MalformedURLException {
    try {
      URL url = getLogFileChooserModel().getSelectedLogFile().toURL();
      LogEventFlavour logEventFlavour = getLogFileChooserModel().getSelectedLogEventFlavour();

      LogEventReader logEventReader = this._logEventReaderFactory.getLogEventReader(url, logEventFlavour);
      if (logEventReader != null) {
        logEventReader.addLogEventHandler(new BatchLogEventHandler(this._logEventStore));
        logEventReader.start();
      }

    } catch (MalformedURLException ex) {
      this._logger.error("Could not load log file: " + ex, ex);
      throw ex;
    }

  }

}
