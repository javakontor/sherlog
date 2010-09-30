package org.javakontor.sherlog.ui.logview;

import org.javakontor.sherlog.util.nls.NLS;
import org.javakontor.sherlog.util.nls.NLSMessage;

public class LogViewMessages {

  /**
   * The (default) messageColumnTitle that is displayed in the statusbar
   */
  @NLSMessage("%d stored messages, %d filtered messages, %d selected messages (%d filter registered)")
  public static String defaultStatusBarMessage;

  // ~~ LogEventTable column titles ---------------------------------------------------
  @NLSMessage("Time")
  public static String timeColumnTitle;

  @NLSMessage("LogLevel")
  public static String logLevelColumnTitle;

  @NLSMessage("Trace")
  public static String traceColumnTitle;

  @NLSMessage("Category")
  public static String categoryColumnTitle;

  @NLSMessage("Message")
  public static String messageColumnTitle;

  /**
   * The format-pattern that is used to render the timestamp of a LogEvent. The string must be a valid expression that
   * can be used by a {@link java.text.SimpleDateFormat} to format the timestamp.
   * 
   * @see java.text.SimpleDateFormat
   */
  @NLSMessage("yyyy-MM-dd HH:mm:ss.SSS")
  public static String defaultDateFormat;

  static {
    NLS.initialize(LogViewMessages.class);
  }
}
