package org.javakontor.sherlog.ui.loadwizard.internal;

import static org.javakontor.sherlog.application.menu.MenuConstants.FILE_MENU_ID;
import static org.javakontor.sherlog.application.menu.MenuConstants.FILE_MENU_TARGET_ID;

import org.javakontor.sherlog.application.action.impl.AbstractAction;
import org.javakontor.sherlog.domain.store.ModifiableLogEventStore;

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
