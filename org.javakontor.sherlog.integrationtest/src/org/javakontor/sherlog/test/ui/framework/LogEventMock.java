package org.javakontor.sherlog.test.ui.framework;

import org.javakontor.sherlog.domain.LogEvent;
import org.javakontor.sherlog.domain.LogEventSource;
import org.javakontor.sherlog.domain.LogLevel;
import org.javakontor.sherlog.domain.impl.reader.AbstractLogEvent;

public class LogEventMock extends AbstractLogEvent {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  private static long       _identifierCount = 0L;

  private String            _category;

  private final long        _identifier;

  private final LogLevel    _logLevel;

  private final String      _message;

  private final String      _threadName;

  private final long        _timestamp;

  private final Throwable   _throwable;

  public LogEventMock(final String category, final String message) {
    super();
    this._category = category;
    this._identifier = ++_identifierCount;
    this._logLevel = LogLevel.INFO;
    setLogEventSource(new LogEventSourceImpl());
    this._message = message;
    this._threadName = "THREAD-1";
    this._throwable = null;
    this._timestamp = System.currentTimeMillis();
  }

  class LogEventSourceImpl implements LogEventSource {
    public String getSource() {
      return "LogEventMock";
    }
  }

  public String getCategory() {
    return this._category;
  }

  @Override
  public long getIdentifier() {
    return this._identifier;
  }

  public LogLevel getLogLevel() {
    return this._logLevel;
  }

  public String getMessage() {
    return this._message;
  }

  public String getThreadName() {
    return this._threadName;
  }

  @Override
  public int compareTo(final LogEvent o) {
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
      final StringBuilder builder = new StringBuilder();
      for (final StackTraceElement element : this._throwable.getStackTrace()) {
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

  public boolean hasNestedDiagnosticContext() {
    // TODO Auto-generated method stub
    return false;
  }

  public void setCategory(final String category) {
    this._category = category;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((this._category == null) ? 0 : this._category.hashCode());
    result = prime * result + (int) (this._identifier ^ (this._identifier >>> 32));
    result = prime * result + ((this._logLevel == null) ? 0 : this._logLevel.hashCode());
    result = prime * result + ((this._message == null) ? 0 : this._message.hashCode());
    result = prime * result + ((this._threadName == null) ? 0 : this._threadName.hashCode());
    result = prime * result + ((this._throwable == null) ? 0 : this._throwable.hashCode());
    result = prime * result + (int) (this._timestamp ^ (this._timestamp >>> 32));
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final LogEventMock other = (LogEventMock) obj;
    if (this._category == null) {
      if (other._category != null) {
        return false;
      }
    } else if (!this._category.equals(other._category)) {
      return false;
    }
    if (this._identifier != other._identifier) {
      return false;
    }
    if (this._logLevel == null) {
      if (other._logLevel != null) {
        return false;
      }
    } else if (!this._logLevel.equals(other._logLevel)) {
      return false;
    }
    if (this._message == null) {
      if (other._message != null) {
        return false;
      }
    } else if (!this._message.equals(other._message)) {
      return false;
    }
    if (this._threadName == null) {
      if (other._threadName != null) {
        return false;
      }
    } else if (!this._threadName.equals(other._threadName)) {
      return false;
    }
    if (this._throwable == null) {
      if (other._throwable != null) {
        return false;
      }
    } else if (!this._throwable.equals(other._throwable)) {
      return false;
    }
    if (this._timestamp != other._timestamp) {
      return false;
    }
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
        + ", _message = '" + this._message + "'" // _message
        + ", _threadName = '" + this._threadName + "'" // _threadName
        + ", _timestamp = '" + this._timestamp + "'" // _timestamp
        + ", _throwable = '" + this._throwable + "'" // _throwable
        + " )";

    return retValue;
  }

}
