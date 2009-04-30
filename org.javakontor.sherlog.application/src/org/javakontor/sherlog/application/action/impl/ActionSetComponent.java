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

  public void addAction(final Action action) {
    final ActionSetImpl registry = (ActionSetImpl) getActionSet(getActionSetId(action));
    registry.addAction(action);
  }

  public void addActionGroup(final ActionGroup actionGroup) {
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

  protected String getActionSetId(final ActionGroupElement actionGroupElement) {
    final String targetId = actionGroupElement.getTargetActionGroupId();
    final TargetGroupIdParser parser = new TargetGroupIdParser(targetId);
    return parser.getActionRoot();
  }

  public void removeAction(final Action action) {
    final ActionSetImpl actionSet = (ActionSetImpl) getActionSet(getActionSetId(action), false);
    if (actionSet != null) {
      actionSet.removeAction(action);
    }
  }

  public void removeActionGroup(final ActionGroup actionGroup) {
    final ActionSetImpl actionSet = (ActionSetImpl) getActionSet(getActionSetId(actionGroup), false);
    if (actionSet != null) {
      actionSet.removeActionGroup(actionGroup);
    }
  }

}
