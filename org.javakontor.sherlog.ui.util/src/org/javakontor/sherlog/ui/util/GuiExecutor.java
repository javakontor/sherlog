package org.javakontor.sherlog.ui.util;

import javax.swing.SwingUtilities;

public class GuiExecutor {

  /**
   * Executes the specified runnable on the Event Dispatcher Thread (EDT)
   * 
   * <p>
   * If this method is invoked on the EDT already, the {@link Runnable#run()} method will be invoked directly. Otherwise
   * the runnable is executed using {@link SwingUtilities#invokeLater(Runnable)}
   * </p>
   */
  public static void execute(Runnable runnable) {
    if (SwingUtilities.isEventDispatchThread()) {
      runnable.run();
    } else {
      SwingUtilities.invokeLater(runnable);
    }
  }

}
