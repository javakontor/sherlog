package org.javakontor.sherlog.domain.logeventflavour.l4jbinary;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.javakontor.sherlog.domain.LogLevel;
import org.javakontor.sherlog.domain.impl.reader.AbstractLogEvent;
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

  public String getThrowableInformationAsString() {
    if (hasThrowableInformation()) {
      String[] stringRep = this._loggingEvent.getThrowableInformation().getThrowableStrRep();
      StringBuilder builder = new StringBuilder();
      for (String rep : stringRep) {
        builder.append(rep);
        builder.append('\n');
      }
      return builder.toString();
    }
    return "";
  }

  public boolean hasThrowableInformation() {
    return (this._loggingEvent.getThrowableInformation() != null);
  }

  public Object getNestedDiagnosticContext() {
    return this._loggingEvent.getNDC();
  }

  public boolean hasNestedDiagnosticContext() {
    return this._loggingEvent.getNDC() != null;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this._loggingEvent == null) ? 0 : this._loggingEvent.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    L4JBinaryLogEvent other = (L4JBinaryLogEvent) obj;
    if (this._loggingEvent == null) {
      if (other._loggingEvent != null)
        return false;
    } else if (!this._loggingEvent.equals(other._loggingEvent))
      return false;
    return true;
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
