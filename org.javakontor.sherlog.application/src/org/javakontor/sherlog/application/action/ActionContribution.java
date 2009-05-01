package org.javakontor.sherlog.application.action;

/**
 * <p>
 * Abstract implementation of interface {@link ActionContribution}.
 * </p>
 */
public interface ActionContribution extends ActionGroupElementContribution {

  /**
   * <p>
   * Overwrite to add default shortcut
   * </p>
   * 
   * @see org.javakontor.sherlog.application.action.ActionContribution#getDefaultShortcut()
   */
  public String getDefaultShortcut();

  public Action getAction();
}
