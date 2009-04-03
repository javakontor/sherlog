package org.javakontor.sherlog.application.action.impl;

import java.util.Hashtable;
import java.util.Map;

import org.javakontor.sherlog.application.action.Action;
import org.javakontor.sherlog.application.action.ActionGroup;
import org.javakontor.sherlog.application.action.ActionGroupElement;
import org.javakontor.sherlog.application.action.ActionSet;
import org.javakontor.sherlog.application.action.ActionSetManager;
import org.javakontor.sherlog.application.internal.action.TargetGroupIdParser;

public class ActionSetComponent implements ActionSetManager {

  private final Map<String, ActionSet> _actionSets;

  public ActionSetComponent() {
    this._actionSets = new Hashtable<String, ActionSet>();
  }

  public void addAction(Action action) {
    ActionSet registry = getActionSet(getActionSetId(action));
    registry.addAction(action);
  }

  public void addActionGroup(ActionGroup actionGroup) {
    ActionSet registry = getActionSet(getActionSetId(actionGroup));
    registry.addActionGroup(actionGroup);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.javakontor.sherlog.application.action.ActionSetManager#getActionSet(java.lang.String)
   */
  public ActionSet getActionSet(String actionSetId) {
    return getActionSet(actionSetId, true);
  }

  /**
   * @param rootId
   * @param createNew
   * @return
   */
  protected ActionSet getActionSet(String rootId, boolean createNew) {
    ActionSet actionSet;
    if (!this._actionSets.containsKey(rootId)) {
      actionSet = new ActionSetImpl(rootId);
      this._actionSets.put(rootId, actionSet);
    } else {
      actionSet = this._actionSets.get(rootId);
    }

    return actionSet;
  }

  protected String getActionSetId(ActionGroupElement actionGroupElement) {
    String targetId = actionGroupElement.getTargetActionGroupId();
    TargetGroupIdParser parser = new TargetGroupIdParser(targetId);
    return parser.getActionRoot();
  }

  public void removeAction(Action action) {
    ActionSet actionSet = getActionSet(getActionSetId(action), false);
    if (actionSet != null) {
      actionSet.removeAction(action);
    }
  }

  public void removeActionGroup(ActionGroup actionGroup) {
    ActionSet actionSet = getActionSet(getActionSetId(actionGroup), false);
    if (actionSet != null) {
      actionSet.removeActionGroup(actionGroup);
    }
  }

}
