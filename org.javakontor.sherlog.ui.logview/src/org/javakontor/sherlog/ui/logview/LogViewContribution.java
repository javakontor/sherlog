package org.javakontor.sherlog.ui.logview;

import org.javakontor.sherlog.core.store.LogEventStore;
import org.javakontor.sherlog.core.store.ModifiableLogEventStore;
import org.lumberjack.application.mvc.AbstractMvcViewContribution;

public class LogViewContribution extends AbstractMvcViewContribution<LogModel, LogView, LogController> {

  public LogViewContribution(LogEventStore logEventStore) {
    super(new DescriptorImpl("Log", false, true, false, true, true), new LogModel(
        (ModifiableLogEventStore) logEventStore), LogView.class, LogController.class);
  }

  @Override
  public void viewEventOccured(ViewEvent viewEvent) {
    if (viewEvent.equals(ViewEvent.windowActivated)) {
      getModel().getLogEventTableModel().sendSetStatusMessageRequest();
    }
  }
}
