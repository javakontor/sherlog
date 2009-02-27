package org.javakontor.sherlog.ui.logview.tableview;

import java.util.Arrays;

import org.javakontor.sherlog.core.LogEvent;
import org.javakontor.sherlog.core.filter.FilterableChangeListener;
import org.javakontor.sherlog.core.filter.LogEventFilter;
import org.javakontor.sherlog.core.store.LogEventStore;
import org.javakontor.sherlog.core.store.LogEventStoreChangeEvent;
import org.javakontor.sherlog.core.store.LogEventStoreListener;
import org.javakontor.sherlog.util.Assert;
import org.lumberjack.application.mvc.AbstractModel;
import org.lumberjack.application.request.SetStatusMessageRequest;
import org.lumberjack.application.request.StatusMessage;

/**
 * <p>
 * The model for the log event table view.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class LogEventTableModel extends AbstractModel<LogEventTableModel, LogEventTableModelReasonForChange> {

  /** the log event store that should be displayed */
  private final LogEventStore _logEventStore;

  /** the selected log events */
  private LogEvent[]          _selectedLogEvents = new LogEvent[0];

  /**
   * <p>
   * Creates a new instance of type {@link LogEventTableModel}.
   * </p>
   * 
   * @param logEventStore
   *          the {@link LogEventStore}
   */
  public LogEventTableModel(LogEventStore logEventStore) {
    super();

    Assert.notNull(logEventStore);

    // the log event store
    this._logEventStore = logEventStore;

    // add a log store listener
    this._logEventStore.addLogStoreListener(new LogEventStoreListener() {

      public void logEventsAdded(LogEventStoreChangeEvent event) {
        // since we work directly on the log event list from the log event store,
        // there is no need to set the logEvents here...

        // fire model changed
        fireModelChangedEvent(LogEventTableModelReasonForChange.logEventsAdded);

        // set status message request
        sendSetStatusMessageRequest();
      }

      public void logEventStoreReset() {
        // since we work directly on the log event list from the log event store,
        // there is no need to reset the logEvents here...

        // fire model changed
        fireModelChangedEvent(LogEventTableModelReasonForChange.reset);

        // set status message request
        sendSetStatusMessageRequest();

      }
    });

    this._logEventStore.addFilterableChangeListener(new FilterableChangeListener() {

      public void logEventFilterRemoved(LogEventFilter logEventFilter) {
        sendSetStatusMessageRequest();
      }

      public void logEventFilterAdded(LogEventFilter logEventFilter) {
        sendSetStatusMessageRequest();
      }
    });

    // send a status message request
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

      // send the status message request
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
   * Sends a request to set the status message.
   * </p>
   */
  public final void sendSetStatusMessageRequest() {

    // format the status message
    String message = String.format(
        "%d stored messages, %d filtered messages, %d selected messages (%d filter registered)", this._logEventStore
            .getLogEventCount(), this._logEventStore.getFilteredLogEvents().size(), this._selectedLogEvents.length,
        this._logEventStore.getLogEventFilters().size());

    // set the status message
    StatusMessage statusMessage = new StatusMessage(message, StatusMessage.INFORMATION);

    // handle the request
    handleRequest(new SetStatusMessageRequest(this, statusMessage));
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