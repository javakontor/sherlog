package org.javakontor.sherlog.application.action.contrib;


/**
 * <p>
 * </p>
 */
public interface StaticActionGroupProvider extends ActionGroupContribution {

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
  public ActionGroupContribution[] getActionGroups();
}
