package org.javakontor.sherlog.application.action;

/**
 * <p>
 * </p>
 */
public interface StaticActionGroupProvider {

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
  public ActionGroup[] getActionGroups();
}
