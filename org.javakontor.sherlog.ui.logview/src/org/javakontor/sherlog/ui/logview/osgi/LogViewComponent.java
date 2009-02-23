package org.javakontor.sherlog.ui.logview.osgi;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.javakontor.sherlog.core.store.LogEventStore;
import org.javakontor.sherlog.ui.logview.LogViewContribution;
import org.lumberjack.application.view.ViewContribution;
import org.lumberjack.ui.dialog.LogDialog;
import org.lumberjack.ui.dialog.LogView;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;

/**
 * The {@link LogViewComponent} manages a {@link LogView} for all registered {@link LogEventStore}s
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class LogViewComponent {

  private ComponentContext                              _componentContext;

  private final List<LogEventStore>                     _logEventStores;

  private final Map<LogEventStore, ServiceRegistration> _registeredContributions;

  private LogViewContribution                           _logViewContribution;

  public LogViewComponent() {
    this._registeredContributions = new Hashtable<LogEventStore, ServiceRegistration>();
    this._logEventStores = new LinkedList<LogEventStore>();
  }

  protected void activate(ComponentContext context) {
    this._componentContext = context;
    for (LogEventStore store : this._logEventStores) {
      addLogEventStore(store);
    }
  }

  /**
   * @param context
   */
  protected void deactivate(ComponentContext context) {
    this._componentContext = null;
  }

  public void addLogEventStore(LogEventStore logEventStore) {

    if (this._componentContext == null) {
      this._logEventStores.add(logEventStore);
      return;
    }

    _logViewContribution = new LogViewContribution(this._componentContext.getBundleContext(), logEventStore);
    ServiceRegistration serviceRegistration = this._componentContext.getBundleContext().registerService(
        ViewContribution.class.getName(), logDialog, null);
    this._registeredContributions.put(logEventStore, serviceRegistration);

    // TODO
    _logViewContribution.getView().getLogEventFilterView().setFilterConfigurationEditorFactory();

    // TODO
    _logViewContribution.getModel().get;
  }

  public void removeLogEventStore(LogEventStore logEventStore) {
    ServiceRegistration registration = this._registeredContributions.remove(logEventStore);
    registration.unregister();
  }

}
