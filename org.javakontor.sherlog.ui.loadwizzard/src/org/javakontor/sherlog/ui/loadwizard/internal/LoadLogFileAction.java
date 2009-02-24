package org.javakontor.sherlog.ui.loadwizard.internal;

import static org.lumberjack.application.menu.MenuConstants.*;

import org.javakontor.sherlog.core.reader.BatchLogEventHandler;
import org.javakontor.sherlog.core.reader.LogEventReader;
import org.javakontor.sherlog.core.reader.LogEventReaderFactory;
import org.javakontor.sherlog.core.store.ModifiableLogEventStore;
import org.javakontor.sherlog.ui.loadwizard.LoadLogFileWizard;
import org.lumberjack.application.action.impl.AbstractAction;

public class LoadLogFileAction extends AbstractAction {

  private final ModifiableLogEventStore _logEventStore;

  private LogEventReaderFactory         _logEventReaderFactory;

  public LoadLogFileAction(ModifiableLogEventStore logEventStore) {
    super(FILE_MENU_ID + ".loadLogFile", FILE_MENU_TARGET_ID + "(first)", "&Load log file...");
    _logEventStore = logEventStore;
  }

  public void execute() {
    LogEventReader logEventReader = LoadLogFileWizard.openLogFileDialog(null, getLogEventReaderFactory());
    if (logEventReader != null) {
      logEventReader.addLogEventHandler(new BatchLogEventHandler(_logEventStore));
    }
  }

  public LogEventReaderFactory getLogEventReaderFactory() {
    return _logEventReaderFactory;
  }

  public void setLogEventReaderFactory(LogEventReaderFactory logEventReaderFactory) {
    _logEventReaderFactory = logEventReaderFactory;
    setEnabled((_logEventReaderFactory != null));
  }

}
