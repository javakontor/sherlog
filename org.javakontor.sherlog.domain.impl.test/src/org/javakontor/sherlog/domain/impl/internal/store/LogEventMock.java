package org.javakontor.sherlog.domain.impl.internal.store;

import java.util.Map;

import org.javakontor.sherlog.domain.LogEvent;
import org.javakontor.sherlog.domain.LogLevel;

public class LogEventMock implements LogEvent {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private String            _category;

  private final long        _identifier;

  private final LogLevel    _logLevel;

  private final String      _logSource;

  private final String      _message;

  private final String      _threadName;

  private final long        _timestamp;

  private final Throwable   _throwable;

  public LogEventMock(String category, long identifier, LogLevel logLevel, String logSource, String message,
      String threadName, Throwable throwable) {
    super();
    this._category = category;
    this._identifier = identifier;
    this._logLevel = logLevel;
    this._logSource = logSource;
    this._message = message;
    this._threadName = threadName;
    this._throwable = throwable;
    this._timestamp = System.currentTimeMillis();
  }

  public String getCategory() {
    return this._category;
  }

  public long getIdentifier() {
    return this._identifier;
  }

  public LogLevel getLogLevel() {
    return this._logLevel;
  }

  public String getLogEventSource() {
    return this._logSource;
  }

  public String getMessage() {
    return this._message;
  }

  public String getThreadName() {
    return this._threadName;
  }

  public int compareTo(LogEvent o) {
    return new Long(this._timestamp).compareTo(o.getTimeStamp());
  }

  public Object getNestedDiagnosticContext() {
    // TODO Auto-generated method stub
    return null;
  }

  public Object getThrowableInformation() {
    return this._throwable;
  }

  public String getThrowableInformationAsString() {
    if (hasThrowableInformation()) {
      StringBuilder builder = new StringBuilder();
      for (StackTraceElement element : this._throwable.getStackTrace()) {
        builder.append(element.toString());
        builder.append('\n');
      }
      return builder.toString();
    }
    return "";
  }

  public boolean hasThrowableInformation() {
    return (this._throwable != null);
  }

  public long getTimeStamp() {
    return this._timestamp;
  }

  public Object getUserDefinedField(String fieldName) {
    return null;
  }

  public String[] getUserDefinedFieldNames() {
    return null;
  }

  public void setUserDefinedField(String fieldName, Object value) {
  }

  public Map<Object, Object> getUserDefinedFields() {
    return null;
  }

  public boolean hasNestedDiagnosticContext() {
    // TODO Auto-generated method stub
    return false;
  }

  public void setCategory(String category) {
    _category = category;
  }

}
