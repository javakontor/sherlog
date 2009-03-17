package org.javakontor.sherlog.application.action;

/**
 * <p>
 * </p>
 */
public interface StaticActionProvider {

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public boolean isFinal();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public Action[] getActions();
}
