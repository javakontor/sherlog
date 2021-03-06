package org.javakontor.sherlog.application.action.contrib;

import org.javakontor.sherlog.application.action.Action;

/**
 * <p>
 * Abstract implementation of interface {@link DefaultActionContribution}.
 * </p>
 * <p>
 * This class <b>is not intended</b> to be subclassed by clients. To contribute an action or an action group, you can
 * use an instance of type {@link DefaultActionContribution} or {@link DefaultActionGroupContribution}.
 * </p>
 */
public class DefaultActionContribution extends AbstractActionGroupElementContribution implements ActionContribution {

  private Action _action;

  private String _defaultShortcut;

  /**
   * <p>
   * Creates a new instance of type {@link DefaultActionContribution}.
   * </p>
   * 
   * @param actionId
   *          the identifier of this action
   * @param targetActionGroupId
   *          the identifier of the target action group
   * @param label
   *          the label of the action
   */
  public DefaultActionContribution(final String actionId, final String targetActionGroupId, final String label,
      final String defaultShortcut, final Action action) {
    super(actionId, targetActionGroupId, label);

    this._action = action;

    this._defaultShortcut = defaultShortcut;
  }

  public DefaultActionContribution() {
    super();
    // TODO Auto-generated constructor stub
  }

  public void setDefaultShortcut(final String defaultShortcut) {
    this._defaultShortcut = defaultShortcut;
  }

  public void setAction(final Action action) {
    this._action = action;
  }

  /**
   * <p>
   * Overwrite to add default shortcut
   * </p>
   * 
   * @see org.javakontor.sherlog.application.action.contrib.DefaultActionContribution#getDefaultShortcut()
   */
  public String getDefaultShortcut() {
    return this._defaultShortcut;
  }

  public Action getAction() {
    return this._action;
  }

}
