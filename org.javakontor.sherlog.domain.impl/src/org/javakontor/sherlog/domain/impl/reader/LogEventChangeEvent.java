package org.javakontor.sherlog.domain.impl.reader;

import java.util.EventObject;

import org.javakontor.sherlog.domain.LogEvent;

public class LogEventChangeEvent extends EventObject {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public LogEventChangeEvent(final LogEvent logEvent) {
    super(logEvent);
  }

  public LogEvent getLogEvent() {
    return (LogEvent) getSource();
  }
}
