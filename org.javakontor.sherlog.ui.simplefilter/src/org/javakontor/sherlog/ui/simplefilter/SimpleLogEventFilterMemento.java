package org.javakontor.sherlog.ui.simplefilter;

import org.javakontor.sherlog.core.LogLevel;
import org.javakontor.sherlog.core.filter.LogEventFilterMemento;

/**
 *
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class SimpleLogEventFilterMemento implements LogEventFilterMemento {

  /**
   * might be null
   */
  private LogLevel _logLevel;

  private String   _thread;

  private String   _category;

  private String   _message;

  public void setLogLevel(LogLevel logLevel) {
    _logLevel = logLevel;
  }

  public void setThread(String thread) {
    _thread = thread;
  }

  public void setCategory(String category) {
    _category = category;
  }

  public void setMessage(String message) {
    _message = message;
  }

  public SimpleLogEventFilterMemento(LogLevel logLevel, String thread, String category, String message) {
    this._logLevel = logLevel;
    this._thread = thread;
    this._category = category;
    this._message = message;
  }

  public SimpleLogEventFilterMemento() {
    this._logLevel = null;
    this._thread = null;
    this._category = null;
    this._message = null;
  }

  public LogLevel getLogLevel() {
    return this._logLevel;
  }

  public String getThread() {
    return this._thread;
  }

  public String getCategory() {
    return this._category;
  }

  public String getMessage() {
    return this._message;
  }

  public boolean isLogLevelSet() {
    return (this._logLevel != null);
  }

  public boolean isThreadSet() {
    return (this._thread != null);
  }

  public boolean isCategorySet() {
    return (this._category != null);
  }

  public boolean isMessageSet() {
    return (this._message != null);
  }

}
