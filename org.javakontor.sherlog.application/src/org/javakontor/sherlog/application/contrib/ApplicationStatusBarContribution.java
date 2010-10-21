package org.javakontor.sherlog.application.contrib;

import javax.swing.JComponent;

/**
 * Represents a UI contribution for the statusbar inside the application window
 * 
 * <p>
 * To add a GUI component to the status bar, register an instance of {@link ApplicationStatusBarContribution} to the
 * OSGi service registry.
 * 
 * @author nils (nils@nilshartmann.net)
 * 
 */
public interface ApplicationStatusBarContribution {

  /**
   * The {@link JComponent} that should be added to the status bar
   * 
   * @return
   */
  JComponent getComponent();

}
