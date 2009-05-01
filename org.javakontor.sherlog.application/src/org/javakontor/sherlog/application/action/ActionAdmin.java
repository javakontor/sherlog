package org.javakontor.sherlog.application.action;

/**
 * <p>
 * The {@link ActionAdmin} service allows you to dynamically register and unregister actions and action groups at
 * runtime.
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface ActionAdmin {

  /**
   * <p>
   * Registers the specified {@link Action} with the action registry.
   * </p>
   * 
   * @param id
   *          the (unique) id for {@link Action}
   * @param actionGroupId
   *          the id of the action group
   * @param label
   *          the label of the action
   * @param shortcut
   *          the shortcut of this action
   * @param action
   *          the action
   */
  public void addAction(String id, String actionGroupId, String label, String shortcut, Action action);

  /**
   * <p>
   * Registers the specified {@link ActionContribution} with the action registry.
   * </p>
   * 
   * @param actionContribution
   *          the action contribution
   */
  public void addAction(ActionContribution actionContribution);

  /**
   * <p>
   * Removes the {@link Action} with the specified (unique) id
   * </p>
   * 
   * @param id
   *          the (unique) id of the {@link Action}
   */
  public void removeAction(String id);

  /**
   * <p>
   * Removes the action contribution
   * </p>
   * 
   * @param action
   */
  public void removeAction(ActionContribution action);

  /**
   * <p>
   * Registers an action group with the action registry.
   * </p>
   * 
   * @param id
   *          the (unique) id for {@link Action}
   * @param actionGroupId
   *          the id of the action group
   * @param label
   *          the label of the action
   * @param type
   *          the action group type
   */
  public void addActionGroup(String id, String actionGroupId, String label, ActionGroupType type);

  /**
   * <p>
   * Registers an action group with the action registry.
   * </p>
   * 
   * @param id
   *          the (unique) id for {@link Action}
   * @param actionGroupId
   *          the id of the action group
   * @param label
   *          the label of the action
   * @param type
   *          the action group type
   * @param staticActionGroupIds
   *          array of ids of static action groups that belong to this action group
   * @param staticActions
   *          array of actions
   * 
   */
  public void addActionGroup(String id, String actionGroupId, String label, ActionGroupType type,
      ActionGroupContribution[] staticActionGroupIds, ActionContribution[] staticActions);

  /**
   * <p>
   * Removes the {@link Action} with the specified (unique) id
   * </p>
   * 
   * @param id
   *          the (unique) id of the action group
   */
  public void removeActionGroup(String id);

  public void addActionGroup(ActionGroupContribution actionGroup);

  public void removeActionGroup(ActionGroupContribution actionGroup);
}
