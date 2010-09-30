package org.javakontor.sherlog.application.action;

/**
 * <p>
 * </p>
 * <p>
 * This class <b>is intended<b> to be subclassed by clients.
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public interface ActionContextAware<C> {

  /**
   * <p>
   * </p>
   * 
   * @param actionContext
   */
  public void setActionContext(C actionContext);
}
