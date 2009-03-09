package org.javakontor.sherlog.ui.loadwizard;

import org.javakontor.sherlog.core.reader.LogEventReaderFactory;
import org.javakontor.sherlog.core.store.ModifiableLogEventStore;
import org.lumberjack.application.mvc.AbstractMvcViewContribution;

public class LoadLogFileWizardContribution extends
    AbstractMvcViewContribution<LoadLogFileWizardModel, LoadLogFileWizardView, LoadLogFileWizardController> {

  public LoadLogFileWizardContribution(ModifiableLogEventStore logEventStore,
      LogEventReaderFactory logEventReaderFactory) {
    super(new DescriptorImpl(LoadLogFileWizardMessages.loadLogFile, true, true, false, false, false),
        new LoadLogFileWizardModel(logEventStore, logEventReaderFactory), LoadLogFileWizardView.class,
        LoadLogFileWizardController.class);
  }

  @Override
  public void viewEventOccured(ViewEvent viewEvent) {
    System.err.println("viewEvent: " + viewEvent);
  }

}
