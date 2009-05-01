package org.javakontor.sherlog.application.action.contrib;

import org.javakontor.sherlog.application.action.Action;
import org.javakontor.sherlog.application.action.ActionAdmin;
import org.javakontor.sherlog.application.action.ActionGroupType;

/**
 * <p>
 * The {@link ActionContributionAdmin} service allows you to dynamically register and unregister actions and action
 * groups at runtime.
 * </p>
 * <p>
 * This class <b>is not intended<b> to be subclassed by clients.
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface ActionContributionAdmin extends ActionAdmin {

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
   * Removes the action contribution
   * </p>
   * 
   * @param action
   */
  public void removeAction(ActionContribution action);

  /**
   * <p>
   * </p>
   * 
   * @param actionGroup
   */
  public void addActionGroup(ActionGroupContribution actionGroup);

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
   * </p>
   * 
   * @param actionGroup
   */
  public void removeActionGroup(ActionGroupContribution actionGroup);
}
