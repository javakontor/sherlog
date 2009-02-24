package org.javakontor.sherlog.core.filter;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface LogEventFilterFactory {

  public String getDescription();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public LogEventFilter createLogEventFilter();

  /**
   * <p>
   * </p>
   * 
   * @param memento
   * @return
   */
  public LogEventFilter createLogEventFilter(LogEventFilterMemento memento);
}
