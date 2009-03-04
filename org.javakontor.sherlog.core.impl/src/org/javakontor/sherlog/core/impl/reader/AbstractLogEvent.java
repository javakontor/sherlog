package org.javakontor.sherlog.core.impl.reader;

import java.util.Hashtable;
import java.util.Map;

import org.javakontor.sherlog.core.LogEvent;

public abstract class AbstractLogEvent implements LogEvent {

  private static int          _instanceCounter   = 0;

  /**
   * 
   */
  private static final long   serialVersionUID   = 1L;

  private final int           _logId;

  private String              _logEventSource;

  private Map<Object, Object> _userDefinedFields = null;

  public AbstractLogEvent() {
    this._logId = _instanceCounter++;
  }

  public Map<Object, Object> getUserDefinedFields() {
    if (_userDefinedFields == null) {
      _userDefinedFields = new Hashtable<Object, Object>();
    }
    return _userDefinedFields;
  }

  public String getLogEventSource() {
    return _logEventSource;
  }

  public void setLogEventSource(String logEventSource) {
    _logEventSource = logEventSource;
  }

  public long getIdentifier() {
    return this._logId;
  }

  public Object getUserDefinedField(Object key) {
    return getUserDefinedFields().get(key);
  }

  public void setUserDefinedField(Object key, Object value) {
    if (value == null) {
      getUserDefinedFields().remove(key);
    } else {
      getUserDefinedFields().put(key, value);
    }
  }

  public int compareTo(final LogEvent o) {
    if (equals(o)) {
      return 0;
    }
    final LogEvent secondEvent = o;
    int ret = (int) (getTimeStamp() - secondEvent.getTimeStamp());
    if (ret == 0) {
      ret = (int) (getIdentifier() - secondEvent.getIdentifier());
    }
    return ret;
  }
}
