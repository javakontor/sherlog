package org.javakontor.sherlog.ui.loadwizard.internal;

import org.javakontor.sherlog.application.action.AbstractAction;
import org.javakontor.sherlog.domain.store.ModifiableLogEventStore;

public class ResetLogEventStoreAction extends AbstractAction {

  private final ModifiableLogEventStore _logEventStore;

  public ResetLogEventStoreAction(ModifiableLogEventStore logEventStore) {
    this._logEventStore = logEventStore;
  }

  public void execute() {
    this._logEventStore.reset();
  }
}
