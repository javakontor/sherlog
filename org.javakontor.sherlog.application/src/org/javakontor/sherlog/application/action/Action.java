package org.javakontor.sherlog.application.action;

import org.javakontor.sherlog.util.IPropertyChangeSupport;

/**
 * <p>
 * </p>
 * <p>
 * This class <b>is intended<b> to be subclassed by clients.
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface Action extends IPropertyChangeSupport {

  /** the name of the 'enabled' property */
  public static final String ENABLED_PROPERTY = "enabled";

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public boolean isEnabled();

  public void setEnabled(boolean enabled);

  /**
   * <p>
   * </p>
   */
  public void execute();
}
