package org.javakontor.sherlog.ui.logview.decorator;

import org.javakontor.sherlog.core.LogEvent;

/**
 * <p>
 * A LogEventDecorator decorates a component that displays an attribute of a {@link LogEvent}.
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public interface LogEventDecorator {

  /**
   * <p>
   * </p>
   * 
   * @param context
   */
  public void decorate(LogEventDecoratorContext context);

}
