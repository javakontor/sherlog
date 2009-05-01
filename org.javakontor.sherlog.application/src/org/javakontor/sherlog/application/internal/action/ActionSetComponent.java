package org.javakontor.sherlog.application.internal.action;

import java.util.Hashtable;
import java.util.Map;

import org.javakontor.sherlog.application.action.Action;
import org.javakontor.sherlog.application.action.ActionAdmin;
import org.javakontor.sherlog.application.action.ActionContribution;
import org.javakontor.sherlog.application.action.ActionGroupContribution;
import org.javakontor.sherlog.application.action.ActionGroupElementContribution;
import org.javakontor.sherlog.application.action.ActionGroupType;
import org.javakontor.sherlog.application.action.DefaultActionContribution;
import org.javakontor.sherlog.application.action.DefaultActionGroupContribution;
import org.javakontor.sherlog.application.action.contrib.ActionSet;
import org.javakontor.sherlog.application.action.contrib.ActionSetManager;

public class ActionSetComponent implements ActionSetManager, ActionAdmin {

  private final Map<String, ActionSet> _actionSets;

  public ActionSetComponent() {
    this._actionSets = new Hashtable<String, ActionSet>();
  }

  public void addAction(final String id, final String actionGroupId, final String label, final String shortcut,
      final Action action) {

    final DefaultActionContribution contribution = new DefaultActionContribution();
    contribution.setId(id);
    contribution.setTargetActionGroupId(actionGroupId);
    contribution.setLabel(label);
    contribution.setDefaultShortcut(shortcut);
    contribution.setAction(action);

    addAction(contribution);
  }

  public void addActionGroup(final String id, final String actionGroupId, final String label,
      final ActionGroupType type, final String[] staticActionGroupIds, final Action[] staticActions) {

    final DefaultActionGroupContribution contribution = new DefaultActionGroupContribution();
    contribution.setId(id);
    contribution.setTargetActionGroupId(actionGroupId);
    contribution.setLabel(label);
    contribution.setType(type);

    addActionGroup(contribution);
  }

  public void addActionGroup(final String id, final String actionGroupId, final String label, final ActionGroupType type) {
    addActionGroup(id, actionGroupId, label, type, null, null);
  }

  public void removeAction(final String id) {

  }

  public void removeActionGroup(final String id) {

  }

  public void addAction(final ActionContribution action) {
    final ActionSetImpl registry = (ActionSetImpl) getActionSet(getActionSetId(action));
    registry.addAction(action);
  }

  public void addActionGroup(final ActionGroupContribution actionGroup) {
    final ActionSetImpl registry = (ActionSetImpl) getActionSet(getActionSetId(actionGroup));
    registry.addActionGroup(actionGroup);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.javakontor.sherlog.application.action.ActionSetManager#getActionSet(java.lang.String)
   */
  public ActionSet getActionSet(final String actionSetId) {
    return getActionSet(actionSetId, true);
  }

  /**
   * @param rootId
   * @param createNew
   * @return
   */
  protected ActionSet getActionSet(final String rootId, final boolean createNew) {
    ActionSet actionSet;
    if (!this._actionSets.containsKey(rootId)) {
      actionSet = new ActionSetImpl(rootId);
      this._actionSets.put(rootId, actionSet);
    } else {
      actionSet = this._actionSets.get(rootId);
    }

    return actionSet;
  }

  protected String getActionSetId(final ActionGroupElementContribution actionGroupElement) {
    final String targetId = actionGroupElement.getTargetActionGroupId();
    final TargetGroupIdParser parser = new TargetGroupIdParser(targetId);
    return parser.getActionRoot();
  }

  public void removeAction(final ActionContribution action) {
    final ActionSetImpl actionSet = (ActionSetImpl) getActionSet(getActionSetId(action), false);
    if (actionSet != null) {
      actionSet.removeAction(action);
    }
  }

  public void removeActionGroup(final ActionGroupContribution actionGroup) {
    final ActionSetImpl actionSet = (ActionSetImpl) getActionSet(getActionSetId(actionGroup), false);
    if (actionSet != null) {
      actionSet.removeActionGroup(actionGroup);
    }
  }

}
