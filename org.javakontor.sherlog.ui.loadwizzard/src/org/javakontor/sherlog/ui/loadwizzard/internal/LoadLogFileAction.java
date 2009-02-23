package org.javakontor.sherlog.ui.loadwizzard.internal;

import static org.lumberjack.application.menu.MenuConstants.FILE_MENU_ID;
import static org.lumberjack.application.menu.MenuConstants.FILE_MENU_TARGET_ID;

import javax.swing.SwingUtilities;

import org.javakontor.sherlog.core.reader.LogEventReader;
import org.javakontor.sherlog.core.store.ModifiableLogEventStore;
import org.lumberjack.application.action.impl.AbstractAction;

public class LoadLogFileAction extends AbstractAction {

  private final ModifiableLogEventStore _logEventStore;

  private LoadLogFileAction(ModifiableLogEventStore logEventStore) {
    super(FILE_MENU_ID + ".loadLogFile", FILE_MENU_TARGET_ID + "(first)", "&Load log file...");
    _logEventStore = logEventStore;
  }

  public void execute() {

    LogEventReader logEventReader = LoadLogFileDialog.openLogFileDialog(SwingUtilities.getWindowAncestor(LogView.this),
        getModel().getLogEventReaderFactory());

  }

}
