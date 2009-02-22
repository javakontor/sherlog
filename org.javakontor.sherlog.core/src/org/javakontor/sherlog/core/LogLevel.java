package org.javakontor.sherlog.core;

/**
 * <p>
 * Defines the log levels a log event can have.
 * </p>
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public enum LogLevel {

  /** the log level UNDEF */
  UNDEF,

  /** the log level DEBUG */
  DEBUG,

  /** the log level INFO */
  INFO,

  /** the log level WARN */
  WARN,

  /** the log level ERROR */
  ERROR,

  /** the log level FATAL */
  FATAL;

  /**
   * <p>
   * Returns <code>true</code> if this log level is greater or equal to the specified log level, <code>false</code>
   * otherwise.
   * </p>
   *
   * @param otherLevel
   * @return <code>true</code> if this log level is greater or equal to the specified log level, <code>false</code>
   *         otherwise.
   */
  public boolean isGreaterOrEqual(final LogLevel otherLevel) {
    return this.ordinal() >= otherLevel.ordinal();
  }

  /**
   * <p>
   * Returns the finest log level.
   * </p>
   *
   * @return the finest log level.
   */
  public static LogLevel getFinestLogLevel() {
    return LogLevel.values()[0];
  }
}
