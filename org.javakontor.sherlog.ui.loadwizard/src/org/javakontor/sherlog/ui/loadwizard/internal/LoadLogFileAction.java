package org.javakontor.sherlog.ui.loadwizard.internal;

import static org.lumberjack.application.menu.MenuConstants.FILE_MENU_ID;
import static org.lumberjack.application.menu.MenuConstants.FILE_MENU_TARGET_ID;

import org.javakontor.sherlog.core.impl.reader.BatchLogEventHandler;
import org.javakontor.sherlog.core.reader.LogEventReader;
import org.javakontor.sherlog.core.reader.LogEventReaderFactory;
import org.javakontor.sherlog.core.store.ModifiableLogEventStore;
import org.javakontor.sherlog.ui.loadwizard.LoadLogFileWizard;
import org.javakontor.sherlog.ui.loadwizard.LoadLogFileWizardMessages;
import org.lumberjack.application.action.impl.AbstractAction;

public class LoadLogFileAction extends AbstractAction {

  private final ModifiableLogEventStore _logEventStore;

  private LogEventReaderFactory         _logEventReaderFactory;

  public LoadLogFileAction(ModifiableLogEventStore logEventStore) {
    super(FILE_MENU_ID + ".loadLogFile", FILE_MENU_TARGET_ID + "(first)", "&Load log file...");
    this._logEventStore = logEventStore;
  }

  public void execute() {
    LogEventReader logEventReader = LoadLogFileWizard.openLoadLogFileWizard(null, getLogEventReaderFactory());
    if (logEventReader != null) {
      logEventReader.addLogEventHandler(new BatchLogEventHandler(this._logEventStore));
      logEventReader.start();
    }
  }

  @Override
  public String getDefaultShortcut() {
    return LoadLogFileWizardMessages.openLogFileWizardShortcut;
  }

  public LogEventReaderFactory getLogEventReaderFactory() {
    return this._logEventReaderFactory;
  }

  public void setLogEventReaderFactory(LogEventReaderFactory logEventReaderFactory) {
    this._logEventReaderFactory = logEventReaderFactory;
    setEnabled((this._logEventReaderFactory != null));
  }

}
