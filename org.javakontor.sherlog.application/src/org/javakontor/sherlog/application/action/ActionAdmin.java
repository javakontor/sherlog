package org.javakontor.sherlog.application.action;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface ActionAdmin {

  public enum ActionGroupType {

    /** a simple action group */
    simple,

    /** a radio action group */
    radiogroup
  }

  /**
   * <p>
   * </p>
   * 
   * @param id
   * @param actionGroupId
   * @param label
   * @param shortcut
   * @param action
   */
  public void addAction(String id, String actionGroupId, String label, String shortcut, Action action);

  /**
   * <p>
   * </p>
   * 
   * @param id
   */
  public void removeAction(String id);

  /**
   * <p>
   * </p>
   * 
   * @param id
   * @param actionGroupId
   * @param label
   * @param type
   */
  public void addActionGroup(String id, String actionGroupId, String label, ActionGroupType type);

  /**
   * <p>
   * </p>
   * 
   * @param id
   */
  public void removeActionGroup(String id);
}
