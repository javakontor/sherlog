package org.javakontor.sherlog.core.logeventflavour.l4jbinary;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.javakontor.sherlog.core.LogLevel;
import org.javakontor.sherlog.core.impl.reader.AbstractLogEvent;
import org.javakontor.sherlog.util.Assert;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
class L4JBinaryLogEvent extends AbstractLogEvent {

  /** - */
  static final long          serialVersionUID = 1L;

  /** - */
  private final LoggingEvent _loggingEvent;

  /**
   * 
   * @param loggingEventObject
   *          a {@link LoggingEvent}
   */
  public L4JBinaryLogEvent(final Object loggingEventObject) {
    Assert.instanceOf("LoggingEventObject", loggingEventObject, LoggingEvent.class);
    this._loggingEvent = (LoggingEvent) loggingEventObject;
  }

  public long getTimeStamp() {
    return this._loggingEvent.timeStamp;
  }

  public String getRenderedMessage() {
    return this._loggingEvent.getRenderedMessage() != null ? this._loggingEvent.getRenderedMessage() : "";
  }

  public String getCategory() {
    return this._loggingEvent.getLoggerName();
  }

  public LogLevel getLogLevel() {
    if (this._loggingEvent.getLevel().equals(Level.ERROR)) {
      return LogLevel.ERROR;
    }
    if (this._loggingEvent.getLevel().equals(Level.WARN)) {
      return LogLevel.WARN;
    }
    if (this._loggingEvent.getLevel().equals(Level.INFO)) {
      return LogLevel.INFO;
    }
    if (this._loggingEvent.getLevel().equals(Level.FATAL)) {
      return LogLevel.FATAL;
    }
    if (this._loggingEvent.getLevel().equals(Level.DEBUG)) {
      return LogLevel.DEBUG;
    }
    return LogLevel.UNDEF;
  }

  public String getThreadName() {
    return this._loggingEvent.getThreadName();
  }

  public String getMessage() {
    return this._loggingEvent.getRenderedMessage();
  }

  public Object getThrowableInformation() {
    return this._loggingEvent.getThrowableInformation();
  }

  public Object getNestedDiagnosticContext() {
    return this._loggingEvent.getNDC();
  }

  public boolean hasNestedDiagnosticContext() {
    return this._loggingEvent.getNDC() != null;
  }

  @Override
  public boolean equals(final Object o) {
    return this._loggingEvent.equals(((L4JBinaryLogEvent) o)._loggingEvent);
  }

  @Override
  public int hashCode() {
    return this._loggingEvent.hashCode();
  }

  /**
   * Constructs a <code>String</code> with all attributes in name = value format.
   * 
   * @return a <code>String</code> representation of this object.
   */
  @Override
  public String toString() {

    final String retValue = "L4JBinaryLogEvent ( " // prefix
        + super.toString() // add super attributes
        + ", _loggingEvent = '" + this._loggingEvent + "'" // _loggingEvent
        + " )";

    return retValue;
  }

}
