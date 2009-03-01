package org.javakontor.sherlog.ui.logview.osgi;

import java.util.Hashtable;
import java.util.Map;

import org.javakontor.sherlog.core.store.LogEventStore;
import org.javakontor.sherlog.ui.filter.FilterConfigurationEditorFactoryManager;
import org.javakontor.sherlog.ui.logview.LogView;
import org.javakontor.sherlog.ui.logview.LogViewContribution;
import org.javakontor.sherlog.ui.logview.decorator.LogEventDecorator;
import org.javakontor.sherlog.util.Assert;
import org.lumberjack.application.action.ActionSet;
import org.lumberjack.application.action.ActionSetManager;
import org.lumberjack.application.view.ViewContribution;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;

/**
 * <p>
 * The {@link LogViewComponent} manages a {@link LogView} for all registered {@link LogEventStore}s
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class LogViewComponent {

  /** the component context */
  private ComponentContext                                    _componentContext;

  private FilterConfigurationEditorFactoryManager             _filterConfigurationEditorFactoryManager;

  /** - */
  private final Map<LogEventStore, LogViewContributionHolder> _registeredContributions;

  /**
   * <p>
   * </p>
   */
  public LogViewComponent() {
    this._registeredContributions = new Hashtable<LogEventStore, LogViewContributionHolder>();
  }

  /**
   * <p>
   * </p>
   * 
   * @param context
   */
  protected void activate(ComponentContext context) {
    // set the component context
    this._componentContext = context;

    // register LogViewContributions
    for (LogViewContributionHolder logViewContributionHolder : this._registeredContributions.values()) {
      logViewContributionHolder.registerLogViewContribution(this._componentContext.getBundleContext());
      logViewContributionHolder.setActionSets(this._actionSetManager);
    }

  }

  /**
   * <p>
   * </p>
   * 
   * @param context
   */
  protected void deactivate(ComponentContext context) {

    // unregister LogViewContributions
    for (LogEventStore logEventStore : this._registeredContributions.keySet()) {
      LogViewContributionHolder logViewContributionHolder = this._registeredContributions.remove(logEventStore);
      logViewContributionHolder.unregisterLogViewContribution(this._componentContext.getBundleContext());
    }

    // set the component context to null
    this._componentContext = null;
  }

  /**
   * <p>
   * <p>
   * 
   * @param logEventStore
   */
  public void addLogEventStore(LogEventStore logEventStore) {

    // the LogViewContributionHolder
    LogViewContributionHolder logViewContributionHolder = new LogViewContributionHolder(logEventStore);

    logViewContributionHolder.setFilterConfigurationEditorFactoryManager(_filterConfigurationEditorFactoryManager);

    // add to contribution set
    _registeredContributions.put(logEventStore, logViewContributionHolder);

    // register LogViewContribution
    if (this._componentContext != null) {
      logViewContributionHolder.registerLogViewContribution(this._componentContext.getBundleContext());
    }
  }

  public void removeLogEventStore(LogEventStore logEventStore) {

    // unregister LogViewContributions
    if (this._registeredContributions.containsKey(logEventStore)) {
      LogViewContributionHolder viewContributionHolder = this._registeredContributions.remove(logEventStore);
      viewContributionHolder.unregisterLogViewContribution(this._componentContext.getBundleContext());
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param logEventDecorator
   */
  public void addLogEventDecorator(final LogEventDecorator logEventDecorator) {
    GuiExecutor.execute(new Runnable() {

      public void run() {
        System.err.println("Add " + logEventDecorator);
        // _logEventTableModel.addLogEventDecorator(logEventDecorator);
      }
    });
  }

  /**
   * <p>
   * </p>
   * 
   * @param logEventDecorator
   */
  public void removeLogEventDecorator(final LogEventDecorator logEventDecorator) {
    GuiExecutor.execute(new Runnable() {

      public void run() {
        System.err.println("Remove " + logEventDecorator);
        // _logEventTableModel.removeLogEventDecorator(logEventDecorator);
      }
    });
  }

  /**
   * <p>
   * </p>
   * 
   * @param factory
   */
  public void bindFilterConfigurationEditorFactoryManager(FilterConfigurationEditorFactoryManager factory) {

    System.err.println("bindFilterConfigurationEditorFactoryManager(" + factory + ")");

    _filterConfigurationEditorFactoryManager = factory;

    for (LogViewContributionHolder logViewContributionHolder : this._registeredContributions.values()) {
      logViewContributionHolder.setFilterConfigurationEditorFactoryManager(_filterConfigurationEditorFactoryManager);
    }
  }

  /**
   * <p>
   * </p>
   * 
   * @param factory
   */
  public void unbindFilterConfigurationEditorFactoryManager(FilterConfigurationEditorFactoryManager factory) {
    System.err.println("unbindFilterConfigurationEditorFactoryManager(" + factory + ")");

    _filterConfigurationEditorFactoryManager = null;

    for (LogViewContributionHolder logViewContributionHolder : this._registeredContributions.values()) {
      logViewContributionHolder.setFilterConfigurationEditorFactoryManager(_filterConfigurationEditorFactoryManager);
    }
  }

  private ActionSetManager _actionSetManager;

  public void bindActionSetManager(ActionSetManager component) {
    _actionSetManager = component;
    for (LogViewContributionHolder logViewContributionHolder : this._registeredContributions.values()) {
      logViewContributionHolder.setActionSets(component);
    }
  }

  public void unbindActionSetManager(ActionSetManager actionSetManager) {
    this._actionSetManager = null;
    for (LogViewContributionHolder logViewContributionHolder : this._registeredContributions.values()) {
      if (logViewContributionHolder.hasLogViewContribution()) {
        logViewContributionHolder.setActionSets(null);
      }
    }

  }

  /**
   * <p>
   * </p>
   * 
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   */
  public static class LogViewContributionHolder {

    /** - */
    private final LogEventStore                     _logEventStore;

    /** - */
    private LogViewContribution                     _logViewContribution;

    /** - */
    private ServiceRegistration                     _serviceRegistration;

    /** - */
    private FilterConfigurationEditorFactoryManager _filterConfigurationEditorFactoryManager;

    /**
     * @param logEventStore
     */
    public LogViewContributionHolder(LogEventStore logEventStore) {
      Assert.notNull(logEventStore);

      _logEventStore = logEventStore;
    }

    /**
     * <p>
     * </p>
     * 
     * @param logViewContribution
     * @param serviceRegistration
     */
    public void registerLogViewContribution(BundleContext bundleContext) {
      Assert.notNull(bundleContext);

      if (hasLogViewContribution()) {
        return;
      }

      _logViewContribution = new LogViewContribution(bundleContext, _logEventStore);

      if (_filterConfigurationEditorFactoryManager != null) {
        _logViewContribution.getView().getLogEventFilterView().setFilterConfigurationEditorFactoryManager(
            _filterConfigurationEditorFactoryManager);
      }
      _serviceRegistration = bundleContext
          .registerService(ViewContribution.class.getName(), _logViewContribution, null);
    }

    public void setActionSets(ActionSetManager actionSetManager) {
      if (!hasLogViewContribution()) {
        return;
      }

      ActionSet actionSet = null;
      if (actionSetManager != null) {
        actionSet = actionSetManager.getActionSet("logEventView.contextMenu");
      }
      _logViewContribution.getModel().getLogEventTableModel().setContextMenuActionSet(actionSet);

    }

    /**
     * <p>
     * </p>
     * 
     * @param bundleContext
     */
    public void unregisterLogViewContribution(BundleContext bundleContext) {
      Assert.notNull(bundleContext);

      if (!hasLogViewContribution()) {
        return;
      }

      if (_filterConfigurationEditorFactoryManager != null) {
        _logViewContribution.getView().getLogEventFilterView().unsetFilterConfigurationEditorFactoryManager(
            _filterConfigurationEditorFactoryManager);
      }

      _serviceRegistration.unregister();
    }

    /**
     * @param factory
     */
    public void setFilterConfigurationEditorFactoryManager(FilterConfigurationEditorFactoryManager factory) {

      if (hasLogViewContribution()) {

        if (factory != null) {
          _logViewContribution.getView().getLogEventFilterView().setFilterConfigurationEditorFactoryManager(factory);
        } else {
          _logViewContribution.getView().getLogEventFilterView().unsetFilterConfigurationEditorFactoryManager(
              _filterConfigurationEditorFactoryManager);
        }
      }

      _filterConfigurationEditorFactoryManager = factory;
    }

    /**
     * <p>
     * </p>
     * 
     * @return
     */
    public boolean hasLogViewContribution() {
      return _logViewContribution != null;
    }
  }
}
