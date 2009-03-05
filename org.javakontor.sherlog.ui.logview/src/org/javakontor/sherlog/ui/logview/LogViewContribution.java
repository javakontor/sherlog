package org.javakontor.sherlog.ui.logview;

import org.javakontor.sherlog.core.store.ModifiableLogEventStore;
import org.javakontor.sherlog.ui.filter.FilterConfigurationEditorFactory;
import org.javakontor.sherlog.ui.logview.decorator.LogEventTableCellDecorator;
import org.javakontor.sherlog.util.servicemanager.ServiceManager;
import org.lumberjack.application.action.ActionSetManager;
import org.lumberjack.application.mvc.AbstractMvcViewContribution;
import org.osgi.service.component.ComponentContext;

/**
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class LogViewContribution extends AbstractMvcViewContribution<LogModel, LogView, LogController> {

  private ModifiableLogEventStore                          _logEventStore;

  /** - */
  private ServiceManager<FilterConfigurationEditorFactory> _filterConfigurationEditorFactoryManager;

  /** - */
  private ServiceManager<LogEventTableCellDecorator>                _decoratorManager;

  /** - */
  private ActionSetManager                                 _actionSetManager;

  public LogViewContribution() {
    super(new DescriptorImpl("Log", false, true, false, true, true), LogView.class, LogController.class);
  }

  /**
   * @see org.lumberjack.application.mvc.AbstractMvcViewContribution#viewEventOccured(org.lumberjack.application.view.ViewContribution.ViewEvent)
   */
  @Override
  public void viewEventOccured(ViewEvent viewEvent) {
    if (viewEvent.equals(ViewEvent.windowActivated)) {
      getModel().getLogEventTableModel().sendSetStatusMessageRequest();
    }
  }

  public void activate(ComponentContext context) {
    setModel(new LogModel(_logEventStore));

    initialize();

    getModel().getLogEventTableModel().setContextMenuActionSet(
        _actionSetManager.getActionSet("logEventView.contextMenu"));

    getModel().getLogEventFilterModel().setConfigurationEditorFactoryManager(_filterConfigurationEditorFactoryManager);

    getModel().getLogEventTableModel().setLogEventDecoratorManager(_decoratorManager);
  }

  public void bindLogEventStore(ModifiableLogEventStore logEventStore) {
    _logEventStore = logEventStore;
  }

  public void unbindLogEventStore(ModifiableLogEventStore logEventStore) {
    _logEventStore = null;
  }

  public void bindActionSetManager(ActionSetManager actionSetManager) {
    _actionSetManager = actionSetManager;
  }

  public void unbindActionSetManager(ActionSetManager actionSetManager) {
    _actionSetManager = null;
  }

  /**
   * @param factory
   */
  public void bindFilterConfigurationEditorFactoryManager(ServiceManager<FilterConfigurationEditorFactory> factory) {
    _filterConfigurationEditorFactoryManager = factory;
  }

  public void unbindFilterConfigurationEditorFactoryManager(ServiceManager<FilterConfigurationEditorFactory> factory) {
    _filterConfigurationEditorFactoryManager = null;
  }

  public void bindLogEventDecoratorManager(ServiceManager<LogEventTableCellDecorator> manager) {
    _decoratorManager = manager;
  }

  public void unbindLogEventDecoratorManager(ServiceManager<LogEventTableCellDecorator> manager) {
    _decoratorManager = null;
  }

}
