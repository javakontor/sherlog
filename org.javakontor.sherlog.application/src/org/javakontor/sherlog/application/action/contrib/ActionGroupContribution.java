package org.javakontor.sherlog.application.action.contrib;

import org.javakontor.sherlog.application.action.ActionGroupType;

/**
 * <p>
 * An {@link ActionGroupContribution} represents an action group that is registered with the action registry.
 * </p>
 * <p>
 * This class <b>is not intended</b> to be subclassed by clients. To contribute an action or an action group, you can
 * use an instance of type {@link DefaultActionContribution} or {@link DefaultActionGroupContribution}.
 * </p>
 */
public interface ActionGroupContribution extends ActionGroupElementContribution {

  /**
   * <p>
   * Returns the type of the action group.
   * </p>
   * 
   * @return the type of the action group.
   */
  public ActionGroupType getType();

  /**
   * <p>
   * Returns if the action group contribution has static {@link ActionGroupContribution ActionGroupContributions}.
   * </p>
   * 
   * @return <code>true</code>, if this {@link ActionGroupContribution} contains static static
   *         {@link ActionGroupContribution ActionGroupContributions}.
   */
  public boolean hasStaticActionGroupContributions();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public ActionGroupContribution[] getStaticActionGroupContributions();

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public ActionContribution[] getStaticActionContributions();

  /**
   * <p>
   * Returns if the action group contribution contains static {@link ActionContribution ActionContributions}.
   * </p>
   * 
   * @return <code>true</code>, if this {@link ActionGroupContribution} contains static static
   *         {@link ActionContribution ActionContributions}.
   */
  public boolean hasStaticActionContributions();

  /**
   * <p>
   * Returns <code>true</code> if this {@link ActionGroupContribution} is final (that means, <b>no</b>
   * {@link ActionGroupElementContribution ActionGroupElementContributions} can be added to the action group.
   * </p>
   * 
   * @return code>true</code> if this {@link ActionGroupContribution} is final.
   */
  public boolean isFinal();
}
