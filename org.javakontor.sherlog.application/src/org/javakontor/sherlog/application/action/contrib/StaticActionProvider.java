package org.javakontor.sherlog.application.action.contrib;


/**
 * <p>
 * </p>
 */
public interface StaticActionProvider extends ActionGroupContribution {

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
  public ActionContribution[] getActionContributions();
}
