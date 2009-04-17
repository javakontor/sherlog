package org.javakontor.sherlog.application.action;

/**
 * <p>
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
