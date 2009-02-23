package org.javakontor.sherlog.ui.logview.detailview;

import org.javakontor.sherlog.core.LogEvent;
import org.javakontor.sherlog.util.Assert;
import org.lumberjack.application.mvc.AbstractModel;
import org.lumberjack.application.mvc.DefaultReasonForChange;

/**
 * <p>
 * The model for the log event detail view.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class LogEventDetailModel extends AbstractModel<LogEventDetailModel, DefaultReasonForChange> {

  /** the log event */
  private LogEvent _logEvent;

  /**
   * <p>
   * Returns the log event.
   * </p>
   * 
   * @return the log event.
   */
  public LogEvent getLogEvent() {
    return this._logEvent;
  }

  /**
   * <p>
   * Sets the log event.
   * </p>
   * 
   * @param logEvent
   */
  public void setLogEvent(LogEvent logEvent) {
    Assert.notNull(logEvent);
    
    // set the log event
    this._logEvent = logEvent;

    // fire model changed event
    fireModelChangedEvent(DefaultReasonForChange.modelChanged);
  }
}
