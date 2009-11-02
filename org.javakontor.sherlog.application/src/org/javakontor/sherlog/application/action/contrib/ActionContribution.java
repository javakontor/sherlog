package org.javakontor.sherlog.application.action.contrib;

import org.javakontor.sherlog.application.action.Action;

/**
 * <p>
 * Abstract implementation of interface {@link ActionContribution}.
 * </p>
 * <p>
 * This class <b>is not intended</b> to be subclassed by clients. To contribute an action or an action group, you can
 * use an instance of type {@link DefaultActionContribution} or {@link DefaultActionGroupContribution}.
 * </p>
 */
public interface ActionContribution extends ActionGroupElementContribution {

  /**
   * <p>
   * Overwrite to add default shortcut
   * </p>
   * 
   * @see org.javakontor.sherlog.application.action.contrib.ActionContribution#getDefaultShortcut()
   */
  public String getDefaultShortcut();

  public Action getAction();
}
