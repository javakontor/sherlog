package org.javakontor.sherlog.ui.logview.osgi;

import java.util.Dictionary;
import java.util.LinkedList;
import java.util.List;

import org.javakontor.sherlog.core.store.LogEventStore;
import org.javakontor.sherlog.ui.filter.manager.FilterConfigurationEditorFactoryManager;
import org.javakontor.sherlog.ui.logview.LogViewContribution;
import org.javakontor.sherlog.ui.logview.decorator.LogEventDecorator;
import org.lumberjack.application.action.ActionSetManager;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class LogViewContributionHolder {

  /** - */
  private LogEventStore                           _logEventStore;

  /** - */
  private LogViewContribution                     _logViewContribution;

  private ServiceRegistration                     _logViewContributionRegistration;

  /** - */
  private FilterConfigurationEditorFactoryManager _filterConfigurationEditorFactoryManager;

  /** - */
  private ActionSetManager                        _actionSetManager;

  /** - */
  private final List<LogEventDecorator>           _decorators;

  /**
   */
  public LogViewContributionHolder() {
    _decorators = new LinkedList<LogEventDecorator>();
  }

  public void activate(ComponentContext context) {
    Dictionary dictionary = context.getProperties();

    _logEventStore = (LogEventStore) dictionary.get("logEventStore");

    System.err.println("************************************************");
    System.err.println("activate Contribution for " + _logEventStore);
    System.err.println(_filterConfigurationEditorFactoryManager);
    System.err.println(_actionSetManager);
    System.err.println("************************************************");

    _logViewContribution = new LogViewContribution(_logEventStore);

    _logViewContribution.getModel().getLogEventTableModel().setContextMenuActionSet(
        _actionSetManager.getActionSet("logEventView.contextMenu"));

    _logViewContribution.getModel().getLogEventFilterModel().setConfigurationEditorFactoryManager(
        _filterConfigurationEditorFactoryManager);

    _logViewContributionRegistration = context.getBundleContext().registerService(
        "org.lumberjack.application.view.ViewContribution", _logViewContribution, null);
  }

  public void deactivate(ComponentContext context) {
    Dictionary dictionary = context.getProperties();

    System.err.println("deactivate Contribution for " + dictionary.get("logEventStore"));

    _logViewContribution.getModel().getLogEventFilterModel().setConfigurationEditorFactoryManager(null);

    _logViewContributionRegistration.unregister();
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
  public void bindFilterConfigurationEditorFactoryManager(FilterConfigurationEditorFactoryManager factory) {
    _filterConfigurationEditorFactoryManager = factory;
  }

  public void unbindFilterConfigurationEditorFactoryManager(FilterConfigurationEditorFactoryManager factory) {
    _filterConfigurationEditorFactoryManager = null;
  }

  public void bindLogEventDecorator(LogEventDecorator decorator) {
    _decorators.add(decorator);
    System.err.println("bind " + decorator);
  }

  public void unbindLogEventDecorator(LogEventDecorator decorator) {
    _decorators.remove(decorator);
    System.err.println("unbind " + decorator);
  }
}