package org.javakontor.sherlog.domain;

import java.io.Serializable;

/**
 * <p>
 * Represents a log event.
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 */
public interface LogEvent extends Serializable, Comparable<LogEvent> {

  /**
   * <p>
   * Returns the {@link LogEventSource} for this {@link LogEvent}.
   * </p>
   * 
   * @return the LogEventSource for this {@link LogEvent}.
   */
  public LogEventSource getLogEventSource();

  /**
   * <p>
   * Returns the unique identifier of this {@link LogEvent}.
   * </p>
   * 
   * @return the unique identifier of this {@link LogEvent}.
   */
  public long getIdentifier();

  /**
   * <p>
   * Returns the time stamp of this {@link LogEvent}.
   * </p>
   * 
   * @return the time stamp of this {@link LogEvent}.
   */
  public long getTimeStamp();

  /**
   * <p>
   * Returns the category of this {@link LogEvent}.
   * </p>
   * 
   * @return the category of this {@link LogEvent}.
   */
  public String getCategory();

  /**
   * <p>
   * Returns the log level of this {@link LogEvent}.
   * </p>
   * 
   * @return the log level of this {@link LogEvent}.
   */
  public LogLevel getLogLevel();

  /**
   * <p>
   * Returns the messages of this {@link LogEvent}.
   * </p>
   * 
   * @return the messages of this {@link LogEvent}.
   */
  public String getMessage();

  /**
   * <p>
   * Returns the name of the thread that created this {@link LogEvent}.
   * </p>
   * 
   * @return the name of the thread that created this {@link LogEvent}.
   */
  public String getThreadName();

  /**
   * <p>
   * Returns <code>true</code>, if this {@link LogEvent} contains a nested diagnostics context (NDC).
   * </p>
   * 
   * @return <code>true</code>, if this {@link LogEvent} contains a nested diagnostics context (NDC).
   */
  public boolean hasNestedDiagnosticContext();

  /**
   * <p>
   * Returns the nested diagnostics context (NDC).
   * </p>
   * 
   * @return the nested diagnostics context (NDC).
   */
  public Object getNestedDiagnosticContext();

  /**
   * <p>
   * Returns the throwable information.
   * </p>
   * 
   * @return the throwable information.
   */
  public Object getThrowableInformation();

  /**
   * Returns <tt>true if there is a {@link #getThrowableInformation() ThrowableInformation} object
   * attaced to this LogEvent
   * 
   * @return true if this LogEvent has a ThrowableInformation
   */
  public boolean hasThrowableInformation();

  /**
   * Returns the throwable information as a String or an empty string if there is no throwable information attached
   * 
   * @return the throwable information or an empty string - never null
   */
  public String getThrowableInformationAsString();

  /**
   * <p>
   * Sets an user defined field with the given value and the given key.
   * </p>
   * <p>
   * To <b>remove</b> a user defined field, pass <tt>null</tt> as value to this method
   * 
   * @param fieldName
   *          the key of the user defined field. Must not be <tt>null</tt>
   * @param value
   *          the value of the user defined field or <tt>null</tt> to remove the value
   */
  public void setUserDefinedField(String fieldName, Object value);

  /**
   * <p>
   * Returns the value of the user defined field with the given key.
   * </p>
   * 
   * @param fieldName
   *          the key of the user defined field.
   * @return the value of the user defined field with the given key.
   */
  public Object getUserDefinedField(String fieldName);

  /**
   * <p>
   * Returns an array with all user-defined field names that are currently set on this {@link LogEvent}.
   * </p>
   * 
   * @return an array with all user-defined field names that are currently set on this {@link LogEvent}.
   */
  public String[] getUserDefinedFieldNames();
}
