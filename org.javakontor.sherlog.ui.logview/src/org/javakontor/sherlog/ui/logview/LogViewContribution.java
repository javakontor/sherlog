package org.javakontor.sherlog.ui.logview;

import org.javakontor.sherlog.application.action.contrib.ActionSetManager;
import org.javakontor.sherlog.application.mvc.AbstractMvcViewContribution;
import org.javakontor.sherlog.application.view.DefaultViewContributionDescriptor;
import org.javakontor.sherlog.domain.store.ModifiableLogEventStore;
import org.javakontor.sherlog.ui.filter.FilterConfigurationEditorFactory;
import org.javakontor.sherlog.ui.logview.decorator.LogEventTableCellDecorator;
import org.javakontor.sherlog.util.servicemanager.DefaultServiceManager;
import org.osgi.service.component.ComponentContext;

/**
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class LogViewContribution extends AbstractMvcViewContribution<LogModel, LogView, LogController> {

  private ModifiableLogEventStore                                       _logEventStore;

  /** - */
  private final DefaultServiceManager<FilterConfigurationEditorFactory> _filterConfigurationEditorFactoryManager;

  /** - */
  private final DefaultServiceManager<LogEventTableCellDecorator>       _decoratorManager;

  /** - */
  private ActionSetManager                                              _actionSetManager;

  public LogViewContribution() {
    super(new DefaultViewContributionDescriptor("Log", false, true, false, true, true), LogView.class,
        LogController.class);

    _decoratorManager = new DefaultServiceManager<LogEventTableCellDecorator>();

    _filterConfigurationEditorFactoryManager = new DefaultServiceManager<FilterConfigurationEditorFactory>();
  }

  /**
   * @see org.javakontor.sherlog.application.mvc.AbstractMvcViewContribution#viewEventOccured(org.javakontor.sherlog.application.view.ViewContribution.ViewEvent)
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
  public void bindFilterConfigurationEditorFactory(FilterConfigurationEditorFactory factory) {
    _filterConfigurationEditorFactoryManager.bindService(factory);
  }

  public void unbindFilterConfigurationEditorFactory(FilterConfigurationEditorFactory factory) {
    _filterConfigurationEditorFactoryManager.unbindService(factory);
  }

  public void bindLogEventDecorator(LogEventTableCellDecorator decorator) {
    _decoratorManager.bindService(decorator);
  }

  public void unbindLogEventDecorator(LogEventTableCellDecorator decorator) {
    _decoratorManager.unbindService(decorator);
  }

}
