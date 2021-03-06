package org.javakontor.sherlog.ui.loadwizard;

import java.net.MalformedURLException;
import java.net.URL;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javakontor.sherlog.application.mvc.AbstractModel;
import org.javakontor.sherlog.application.mvc.DefaultReasonForChange;
import org.javakontor.sherlog.domain.impl.reader.BatchLogEventHandler;
import org.javakontor.sherlog.domain.impl.reader.UrlLogEventReaderInputSource;
import org.javakontor.sherlog.domain.reader.LogEventFlavour;
import org.javakontor.sherlog.domain.reader.LogEventReader;
import org.javakontor.sherlog.domain.reader.LogEventReaderFactory;
import org.javakontor.sherlog.domain.store.ModifiableLogEventStore;
import org.javakontor.sherlog.ui.loadwizard.filechooser.LogFileChooserModel;

public class LoadLogFileWizardModel extends AbstractModel<LoadLogFileWizardModel, DefaultReasonForChange> {
  private final Log                     _logger = LogFactory.getLog(getClass());

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

      LogEventReader logEventReader = this._logEventReaderFactory.getLogEventReader(new UrlLogEventReaderInputSource(
          url), logEventFlavour);
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
