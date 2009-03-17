package org.javakontor.sherlog.ui.logview.tableview;

import org.javakontor.sherlog.core.LogEvent;
import org.javakontor.sherlog.application.request.Request;

/**
 * <p>
 * A {@link Request} to set selected {@link LogEvent LogEvents}.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class SetSelectedLogEventsRequest extends Request {

  /** the selected log events */
  private final LogEvent[] _logEvents;

  /**
   * <p>
   * Creates a new instance of type {@link SetSelectedLogEventsRequest}.
   * </p>
   * 
   * @param sender
   * @param logEvents
   */
  public SetSelectedLogEventsRequest(Object sender, LogEvent[] logEvents) {
    super(sender);

    this._logEvents = logEvents != null ? logEvents : new LogEvent[0];
  }

  /**
   * <p>
   * Returns the selected {@link LogEvent LogEvents}.
   * </p>
   * 
   * @return the selected {@link LogEvent LogEvents}.
   */
  public LogEvent[] getLogEvents() {
    return this._logEvents;
  }
}
