package org.javakontor.sherlog.domain.filter;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface LogEventFilterFactory {

  /**
   * Returns a human readable description of this factory (to be displa
   * 
   * @return
   */
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
