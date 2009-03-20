package org.javakontor.sherlog.domain.impl.internal.store;

import java.util.Hashtable;
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

  public Object getUserDefinedField(Object key) {
    return this._userDefinedFields.get(key);
  }

  public Map<Object, Object> getUserDefinedFields() {
    // TODO Auto-generated method stub
    return null;
  }

  public boolean hasNestedDiagnosticContext() {
    // TODO Auto-generated method stub
    return false;
  }

  public void setUserDefinedField(Object key, Object value) {
    if (value == null) {
      this._userDefinedFields.remove(key);
    } else {
      this._userDefinedFields.put(key, value);
    }
  }

  public void setCategory(String category) {
    _category = category;
  }

  private final Map<Object, Object> _userDefinedFields = new Hashtable<Object, Object>();

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((_category == null) ? 0 : _category.hashCode());
    result = prime * result + (int) (_identifier ^ (_identifier >>> 32));
    result = prime * result + ((_logLevel == null) ? 0 : _logLevel.hashCode());
    result = prime * result + ((_logSource == null) ? 0 : _logSource.hashCode());
    result = prime * result + ((_message == null) ? 0 : _message.hashCode());
    result = prime * result + ((_threadName == null) ? 0 : _threadName.hashCode());
    result = prime * result + ((_throwable == null) ? 0 : _throwable.hashCode());
    result = prime * result + (int) (_timestamp ^ (_timestamp >>> 32));
    result = prime * result + ((_userDefinedFields == null) ? 0 : _userDefinedFields.hashCode());
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
    LogEventMock other = (LogEventMock) obj;
    if (_category == null) {
      if (other._category != null)
        return false;
    } else if (!_category.equals(other._category))
      return false;
    if (_identifier != other._identifier)
      return false;
    if (_logLevel == null) {
      if (other._logLevel != null)
        return false;
    } else if (!_logLevel.equals(other._logLevel))
      return false;
    if (_logSource == null) {
      if (other._logSource != null)
        return false;
    } else if (!_logSource.equals(other._logSource))
      return false;
    if (_message == null) {
      if (other._message != null)
        return false;
    } else if (!_message.equals(other._message))
      return false;
    if (_threadName == null) {
      if (other._threadName != null)
        return false;
    } else if (!_threadName.equals(other._threadName))
      return false;
    if (_throwable == null) {
      if (other._throwable != null)
        return false;
    } else if (!_throwable.equals(other._throwable))
      return false;
    if (_timestamp != other._timestamp)
      return false;
    if (_userDefinedFields == null) {
      if (other._userDefinedFields != null)
        return false;
    } else if (!_userDefinedFields.equals(other._userDefinedFields))
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

    final String retValue = "LogEventMock ( " // prefix
        + super.toString() // add super attributes
        + ", _category = '" + this._category + "'" // _category
        + ", _identifier = '" + this._identifier + "'" // _identifier
        + ", _logLevel = '" + this._logLevel + "'" // _logLevel
        + ", _logSource = '" + this._logSource + "'" // _logSource
        + ", _message = '" + this._message + "'" // _message
        + ", _threadName = '" + this._threadName + "'" // _threadName
        + ", _timestamp = '" + this._timestamp + "'" // _timestamp
        + ", _throwable = '" + this._throwable + "'" // _throwable
        + ", _userDefinedFields = '" + this._userDefinedFields + "'" // _userDefinedFields
        + " )";

    return retValue;
  }

}
