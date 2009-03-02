package org.javakontor.sherlog.ui.loadwizard.internal;

import static org.lumberjack.application.menu.MenuConstants.FILE_MENU_ID;
import static org.lumberjack.application.menu.MenuConstants.FILE_MENU_TARGET_ID;

import org.javakontor.sherlog.core.store.ModifiableLogEventStore;
import org.lumberjack.application.action.impl.AbstractAction;

public class ResetLogEventStoreAction extends AbstractAction {

  private final ModifiableLogEventStore _logEventStore;

  public ResetLogEventStoreAction(ModifiableLogEventStore logEventStore) {
    super(FILE_MENU_ID + ".resetLogStore", FILE_MENU_TARGET_ID + "(first)", "&Reset logstore");
    this._logEventStore = logEventStore;
  }

  public void execute() {
    this._logEventStore.reset();
  }

  // @Override
  // public String getDefaultShortcut() {
  // return LoadLogFileWizardMessages.openLogFileWizardShortcut;
  // }

}
