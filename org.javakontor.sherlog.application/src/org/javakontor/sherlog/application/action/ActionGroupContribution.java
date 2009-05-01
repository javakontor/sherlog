package org.javakontor.sherlog.application.action;

/**
 * <p>
 * </p>
 */
public interface ActionGroupContribution extends ActionGroupElementContribution {

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public ActionGroupType getType();

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
  public ActionGroupContribution[] getStaticActionGroupContributions();

  public boolean hasStaticActionGroupContributions();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public ActionContribution[] getStaticActionContributions();

  public boolean hasStaticActionContributions();
}
