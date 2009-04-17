package org.javakontor.sherlog.ui.logview.tableview;

import java.util.Arrays;

import org.javakontor.sherlog.application.action.ActionSet;
import org.javakontor.sherlog.application.mvc.AbstractModel;
import org.javakontor.sherlog.application.request.SetStatusMessageRequest;
import org.javakontor.sherlog.application.request.StatusMessage;
import org.javakontor.sherlog.domain.LogEvent;
import org.javakontor.sherlog.domain.filter.LogEventFilter;
import org.javakontor.sherlog.domain.filter.RegisteredFilterChangeListener;
import org.javakontor.sherlog.domain.store.LogEventStore;
import org.javakontor.sherlog.domain.store.LogEventStoreEvent;
import org.javakontor.sherlog.domain.store.LogEventStoreListener;
import org.javakontor.sherlog.ui.logview.LogViewMessages;
import org.javakontor.sherlog.ui.logview.decorator.LogEventTableCellDecorator;
import org.javakontor.sherlog.util.Assert;
import org.javakontor.sherlog.util.servicemanager.ServiceManager;
import org.javakontor.sherlog.util.servicemanager.ServiceManagerEvent;
import org.javakontor.sherlog.util.servicemanager.ServiceManagerListener;

/**
 * <p>
 * The model for the log event table view.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class LogEventTableModel extends AbstractModel<LogEventTableModel, LogEventTableModelReasonForChange> {

  /** the log event store that should be displayed */
  private final LogEventStore                                      _logEventStore;

  /** the selected log events */
  private LogEvent[]                                               _selectedLogEvents = new LogEvent[0];

  private ActionSet                                                _actionGroupRegistry;

  /** - */
  private ServiceManager<LogEventTableCellDecorator>               _logEventDecoratorManager;

  /** - */
  private final ServiceManagerListener<LogEventTableCellDecorator> _logEventDecoratorManagerListener;

  /**
   * <p>
   * Creates a new instance of type {@link LogEventTableModel}.
   * </p>
   * 
   * @param logEventStore
   *          the {@link LogEventStore}
   */
  public LogEventTableModel(LogEventStore logEventStore) {
    Assert.notNull(logEventStore);

    _logEventDecoratorManagerListener = new ServiceManagerListener<LogEventTableCellDecorator>() {
      public void serviceAdded(ServiceManagerEvent<LogEventTableCellDecorator> event) {
        fireModelChangedEvent(LogEventTableModelReasonForChange.decoratorAdded, event.getService());
      }

      public void serviceRemoved(ServiceManagerEvent<LogEventTableCellDecorator> event) {
        fireModelChangedEvent(LogEventTableModelReasonForChange.decoratorRemoved, event.getService());
      }
    };

    // the log event store
    this._logEventStore = logEventStore;

    // add a log store listener
    this._logEventStore.addLogStoreListener(new LogEventStoreListener() {

      public void logEventStoreChanged(LogEventStoreEvent event) {
        // since we work directly on the log event list from the log event store,
        // there is no need to set the logEvents here...

        // fire model changed
        fireModelChangedEvent(LogEventTableModelReasonForChange.logEventsChanged);

        // set status messageColumnTitle request
        sendSetStatusMessageRequest();
      }
    });

    this._logEventStore.addRegisteredFilterChangeListener(new RegisteredFilterChangeListener() {

      public void filterRemoved(LogEventFilter logEventFilter) {
        sendSetStatusMessageRequest();
      }

      public void filterAdded(LogEventFilter logEventFilter) {
        sendSetStatusMessageRequest();
      }
    });

    // send a status messageColumnTitle request
    sendSetStatusMessageRequest();
  }

  /**
   * <p>
   * Returns the {@link LogEventStore}.
   * </p>
   * 
   * @return the {@link LogEventStore}.
   */
  public LogEventStore getLogEventStore() {
    return this._logEventStore;
  }

  /**
   * <p>
   * Sets the selected {@link LogEvent LogEvents}.
   * </p>
   * 
   * @param selectedLogEvents
   *          the {@link LogEvent LogEvents}.
   */
  public boolean setSelectedLogEvents(LogEvent[] selectedLogEvents) {

    if (!Arrays.equals(this._selectedLogEvents, selectedLogEvents)) {

      // set the selected events
      this._selectedLogEvents = selectedLogEvents != null ? selectedLogEvents : new LogEvent[0];

      // fire model changed event
      fireModelChangedEvent(LogEventTableModelReasonForChange.selectionChanged);

      // send the status messageColumnTitle request
      sendSetStatusMessageRequest();

      // send the selected log events request
      sendSetSelectedLogEventsRequest();

      // return true
      return true;
    }

    // return false
    return false;
  }

  /**
   * <p>
   * Returns the selected {@link LogEvent LogEvents} or an empty array if no events are selected.
   * <p>
   * 
   * @return the selected {@link LogEvent LogEvents} or an empty array if no events are selected.
   */
  public LogEvent[] getSelectedLogEvents() {
    return this._selectedLogEvents;
  }

  /**
   * <p>
   * Sends a request to set the status messageColumnTitle.
   * </p>
   */
  public final void sendSetStatusMessageRequest() {

    // format the status messageColumnTitle
    String message = String.format(LogViewMessages.defaultStatusBarMessage, this._logEventStore.getLogEventCount(),
        this._logEventStore.getFilteredLogEvents().size(), this._selectedLogEvents.length, this._logEventStore
            .getLogEventFilters().size());

    // set the status messageColumnTitle
    StatusMessage statusMessage = new StatusMessage(message, StatusMessage.INFORMATION);

    // handle the request
    handleRequest(new SetStatusMessageRequest(this, statusMessage));
  }

  public ActionSet getActionGroupRegistry() {
    return _actionGroupRegistry;
  }

  /**
   * Set an ActionSet that should be used to display the context menu
   * 
   */
  public void setContextMenuActionSet(ActionSet actionGroupRegistry) {
    _actionGroupRegistry = actionGroupRegistry;
  }

  public void setLogEventDecoratorManager(ServiceManager<LogEventTableCellDecorator> manager) {

    if (_logEventDecoratorManager == null && manager != null) {

      // set factory manager
      _logEventDecoratorManager = manager;

      // add listener
      _logEventDecoratorManager.addServiceManagerListener(_logEventDecoratorManagerListener);
    }

    else if (_logEventDecoratorManager != null && manager == null) {

      //
      _logEventDecoratorManager.removeServiceManagerListener(_logEventDecoratorManagerListener);

      //
      _logEventDecoratorManager = null;
    }

    else {
      throw new UnsupportedOperationException("TODO");
    }
  }

  /**
   * <p>
   * Sends a request to set the selected {@link LogEvent LogEvents}.
   * </p>
   */
  public final void sendSetSelectedLogEventsRequest() {

    // handle the request
    handleRequest(new SetSelectedLogEventsRequest(this, this._selectedLogEvents));
  }
}
