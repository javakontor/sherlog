package org.javakontor.sherlog.ui.logview.decorator;

import org.javakontor.sherlog.core.LogEvent;

/**
 * <p>
 * A LogEventTableCellDecorator decorates a component that displays an attribute of a {@link LogEvent}.
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public interface LogEventTableCellDecorator {

  /**
   * <p>
   * </p>
   * 
   * @param context
   */
  public void decorateLogEventTableCell(LogEventTableCellDecoratorContext context);

}
