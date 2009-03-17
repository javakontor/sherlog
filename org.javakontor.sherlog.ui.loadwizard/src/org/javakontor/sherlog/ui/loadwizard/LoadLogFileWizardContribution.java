package org.javakontor.sherlog.ui.loadwizard;

import org.javakontor.sherlog.application.mvc.AbstractMvcViewContribution;
import org.javakontor.sherlog.application.view.DefaultViewContributionDescriptor;
import org.javakontor.sherlog.core.reader.LogEventReaderFactory;
import org.javakontor.sherlog.core.store.ModifiableLogEventStore;

public class LoadLogFileWizardContribution extends
    AbstractMvcViewContribution<LoadLogFileWizardModel, LoadLogFileWizardView, LoadLogFileWizardController> {

  public LoadLogFileWizardContribution(ModifiableLogEventStore logEventStore,
      LogEventReaderFactory logEventReaderFactory) {
    super(
        new DefaultViewContributionDescriptor(LoadLogFileWizardMessages.loadLogFile, true, true, false, false, false),
        new LoadLogFileWizardModel(logEventStore, logEventReaderFactory), LoadLogFileWizardView.class,
        LoadLogFileWizardController.class);
  }

  @Override
  public void viewEventOccured(ViewEvent viewEvent) {
  }

}
