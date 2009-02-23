package org.javakontor.sherlog.ui.logview;

import javax.swing.JPanel;

import org.javakontor.sherlog.core.store.LogEventStore;
import org.javakontor.sherlog.core.store.ModifiableLogEventStore;
import org.javakontor.sherlog.ui.logview.osgi.GuiExecutor;
import org.lumberjack.application.mvc.AbstractMvcViewContribution;
import org.osgi.framework.BundleContext;

public class LogViewContribution extends AbstractMvcViewContribution<LogModel, LogView, LogController> {

  public LogViewContribution(BundleContext bundleContext, LogEventStore logEventStore) {
    super(new DescriptorImpl("Log", false, true, false, true, true), new LogModel(
        (ModifiableLogEventStore) logEventStore), LogView.class, LogController.class);
  }

  @Override
  public void viewEventOccured(ViewEvent viewEvent) {
    if (viewEvent.equals(ViewEvent.windowActivated)) {
      getModel().getLogEventTableModel().sendSetStatusMessageRequest();
    }
  }

  public void addFilterConfigView(final JPanel filterConfigView) {
    GuiExecutor.execute(new Runnable() {

      public void run() {
        getView().getLogEventFilterView().addPanel(filterConfigView);
      }
    });
  }

  public void removeFilterConfigView(final JPanel filterConfigView) {
    GuiExecutor.execute(new Runnable() {
      public void run() {
        getView().getLogEventFilterView().removePanel(filterConfigView);
      }
    });
  }
}
