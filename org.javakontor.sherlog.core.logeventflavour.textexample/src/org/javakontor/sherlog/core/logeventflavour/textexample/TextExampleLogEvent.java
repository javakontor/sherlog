package org.javakontor.sherlog.core.logeventflavour.textexample;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.javakontor.sherlog.core.LogLevel;
import org.javakontor.sherlog.core.impl.reader.AbstractLogEvent;
import org.javakontor.sherlog.util.Assert;

public class TextExampleLogEvent extends AbstractLogEvent {

  static final long          serialVersionUID = 1L;

  transient SimpleDateFormat timeformat       = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");

  private final String       _loggingEventString;

  private Long               _timestamp;

  private String             _category;

  private String             _threadName;

  private LogLevel           _logLevel;

  private String             _renderedMessage;

  public TextExampleLogEvent(final Object loggingEventString) {
    Assert.instanceOf("loggingEventString", loggingEventString, String.class);
    this._loggingEventString = (String) loggingEventString;
    parseString();
  }

  private void parseString() {
    try {
      // |timestamp|[Thread]LOGLEVEL| category message
      // System.out.println(myEvent);
      String tmp = this._loggingEventString;
      int index = tmp.substring(1).indexOf('|');
      final String tsString = tmp.substring(1, index);
      final Date ts = this.timeformat.parse(tsString);
      this._timestamp = new Long(ts.getTime());
      index = tmp.indexOf(']');
      this._threadName = tmp.substring(tmp.indexOf('[') + 1, index);
      tmp = tmp.substring(index + 1).trim();
      index = tmp.indexOf(' ');
      this._logLevel = LogLevel.valueOf(tmp.substring(0, index));
      tmp = tmp.substring(index).trim();
      index = tmp.indexOf(' ');
      this._category = tmp.substring(0, index);
      tmp = tmp.substring(index).trim();
      this._renderedMessage = tmp;
    } catch (final ParseException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
  }

  public Object getNestedDiagnosticContext() {
    return null;
  }

  public boolean hasNestedDiagnosticContext() {
    return false;
  }

  public long getTimeStamp() {
    return this._timestamp.longValue();
  }

  public String getCategory() {
    return this._category;
  }

  public LogLevel getLogLevel() {
    return this._logLevel;
  }

  public String getThreadName() {
    return this._threadName;
  }

  public String getMessage() {
    return _renderedMessage;
  }

  public Object getThrowableInformation() {
    return null;
  }

  public Object getNDC() {
    return null;
  }

  public String[] getThrowableStrRep() {
    return null;
  }

  @Override
  public boolean equals(final Object o) {
    return this._loggingEventString.equals(((TextExampleLogEvent) o)._loggingEventString);
  }

  @Override
  public int hashCode() {
    return this._loggingEventString.hashCode();
  }

  public Object getInternalRepraesentation() {
    return this._loggingEventString;
  }

  @Override
  public String toString() {
    return _loggingEventString;
  }
}
