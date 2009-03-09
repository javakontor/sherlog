package org.javakontor.sherlog.ui.loadwizard;

import org.javakontor.sherlog.core.reader.LogEventFlavour;
import org.lumberjack.application.mvc.AbstractMvcViewContribution;

public class LoadLogFileWizardContribution extends
    AbstractMvcViewContribution<LoadLogFileWizardModel, LoadLogFileWizardView, LoadLogFileWizardController> {

  public LoadLogFileWizardContribution(LogEventFlavour[] supportedFlavours) {
    super(new DescriptorImpl("Load log file", true, true, false, false, false), new LoadLogFileWizardModel(
        supportedFlavours), LoadLogFileWizardView.class, LoadLogFileWizardController.class);
  }

  @Override
  public void viewEventOccured(ViewEvent viewEvent) {
    System.err.println("viewEvent: " + viewEvent);
  }

}
