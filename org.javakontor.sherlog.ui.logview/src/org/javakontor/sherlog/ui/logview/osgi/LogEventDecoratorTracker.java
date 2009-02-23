package org.javakontor.sherlog.ui.logview.osgi;

import org.javakontor.sherlog.ui.logview.decorator.LogEventDecorator;
import org.javakontor.sherlog.ui.logview.tableview.LogEventTableModel;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

public class LogEventDecoratorTracker extends ServiceTracker {

  private final LogEventTableModel _logEventTableModel;

  private LogEventDecoratorTracker(BundleContext bundleContext, LogEventTableModel logEventTableModel) {
    super(bundleContext, LogEventDecorator.class.getName(), null);

    _logEventTableModel = logEventTableModel;
  }

  @Override
  public Object addingService(ServiceReference reference) {
    final LogEventDecorator decorator = (LogEventDecorator) super.addingService(reference);
    GuiExecutor.execute(new Runnable() {

      public void run() {
        _logEventTableModel.addLogEventDecorator(decorator);
      }
    });
    return decorator;

  }

  @Override
  public void removedService(ServiceReference reference, Object service) {
    final LogEventDecorator decorator = (LogEventDecorator) service;
    GuiExecutor.execute(new Runnable() {

      public void run() {
        _logEventTableModel.removeLogEventDecorator(decorator);
      }
    });
  }

}
