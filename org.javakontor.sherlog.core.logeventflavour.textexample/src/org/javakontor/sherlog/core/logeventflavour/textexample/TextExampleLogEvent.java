package org.javakontor.sherlog.core.logeventflavour.textexample;

import static java.lang.String.format;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.javakontor.sherlog.core.LogLevel;
import org.javakontor.sherlog.core.impl.reader.AbstractLogEvent;
import org.javakontor.sherlog.util.Assert;

public class TextExampleLogEvent extends AbstractLogEvent {

  static final long          serialVersionUID = 1L;

  /**
   * {@link Pattern} used to parse a text-logevent
   */
  static Pattern             pattern          = Pattern.compile("\\|(.*) \\[(.*)\\] \\<(.*)\\> ([^\\s]*) ((.*))",
                                                  Pattern.DOTALL);

  transient SimpleDateFormat timeformat       = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss,SSS");

  private final String       _logEventAsString;

  private Long               _timestamp;

  private String             _category;

  private String             _threadName;

  private LogLevel           _logLevel;

  private String             _renderedMessage;

  public TextExampleLogEvent(final Object loggingEventString) {
    Assert.instanceOf("loggingEventString", loggingEventString, String.class);
    this._logEventAsString = (String) loggingEventString;
    parseString();
  }

  private void parseString() {
    try {
      Matcher matcher = pattern.matcher(_logEventAsString);
      if (!matcher.find()) {
        // TODO ignore log event?
        throw new RuntimeException("Invalid log '" + _logEventAsString + "'");
      }

      String timestampString = matcher.group(1);
      final Date ts = this.timeformat.parse(timestampString);
      this._timestamp = new Long(ts.getTime());

      this._threadName = matcher.group(2);
      this._logLevel = LogLevel.valueOf(matcher.group(3));
      this._category = matcher.group(4);
      this._renderedMessage = matcher.group(5);
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
    return this._logEventAsString.equals(((TextExampleLogEvent) o)._logEventAsString);
  }

  @Override
  public int hashCode() {
    return this._logEventAsString.hashCode();
  }

  public Object getInternalRepraesentation() {
    return this._logEventAsString;
  }

  @Override
  public String toString() {
    return _logEventAsString;
  }

  public static void main(String[] args) {
    try {
      // |timestamp [Thread] <LOGLEVEL> category message
      Pattern pattern = Pattern.compile("\\|(.*) \\[(.*)\\] \\<(.*)\\> ([^\\s]*) ((.*))");
      String log = "|2009-03-01 18:28:31,765 [Log Event Dispatcher] <INFO> org.javakontor.sherlog.core.logeventflavour.textexample ServiceEvent REGISTERED";
      Matcher matcher = pattern.matcher(log);
      if (!matcher.find()) {
        throw new RuntimeException("Invalid log '" + log + "'");
      }

      matcher.groupCount();
      for (int i = 0; i < matcher.groupCount(); i++) {
        System.out.println(format("%d: '%s'", i, matcher.group(i)));
      }

    } catch (Exception ex) {
      System.err.println("Exception caught in main: " + ex);
      ex.printStackTrace();
    }
  }
}
