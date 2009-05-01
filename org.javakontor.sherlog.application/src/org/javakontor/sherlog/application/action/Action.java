package org.javakontor.sherlog.application.action;

import org.javakontor.sherlog.util.IPropertyChangeSupport;

/**
 * <p>
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

  /**
   * <p>
   * </p>
   */
  public void execute();
}
