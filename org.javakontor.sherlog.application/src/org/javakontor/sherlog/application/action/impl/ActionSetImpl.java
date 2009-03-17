package org.javakontor.sherlog.application.action.impl;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import org.javakontor.sherlog.application.action.Action;
import org.javakontor.sherlog.application.action.ActionGroup;
import org.javakontor.sherlog.application.action.ActionGroupContent;
import org.javakontor.sherlog.application.action.ActionGroupElement;
import org.javakontor.sherlog.application.action.ActionSet;
import org.javakontor.sherlog.application.action.ActionSetChangeListener;
import org.javakontor.sherlog.application.action.StaticActionGroupProvider;
import org.javakontor.sherlog.application.action.StaticActionProvider;
import org.javakontor.sherlog.application.internal.action.ActionLocation;
import org.javakontor.sherlog.application.internal.action.LocatableActionGroupElement;
import org.javakontor.sherlog.application.internal.action.TargetGroupIdParser;

public class ActionSetImpl implements ActionSet {

  /**
   * The actual content of this ActionSet grouped by its ActionGroups.
   * 
   * <p>
   * Key: actionGroupId
   * </p>
   * <p>
   * Value: the {@link ActionGroupContent} holding the ActionGroup instance and its content
   * </p>
   */
  private final Map<String, ActionGroupContent>              _actionGroups;

  /**
   * The id of the "root" element of this ActionSet. There is exactly one ActionGroup that has no parent (i.e. the
   * menubar)
   */
  private final String                                       _rootId;

  private final CopyOnWriteArraySet<ActionSetChangeListener> _actionSetChangeListeners;

  /**
   * 
   * @param rootId
   */
  public ActionSetImpl(String rootId) {
    this._rootId = rootId;
    this._actionSetChangeListeners = new CopyOnWriteArraySet<ActionSetChangeListener>();
    this._actionGroups = new Hashtable<String, ActionGroupContent>();
    this._actionGroups.put(rootId, new ActionGroupContent());
  }

  /**
   * return the rootActionGroupContent. never null.
   * 
   * @return
   */
  public ActionGroupContent getRootActionGroupContent() {
    return this._actionGroups.get(this._rootId);
  }

  /**
   * Returns the ActionGroupContent for the ActionGroup with the specified id.
   * 
   * <p>
   * This method returns <tt>null</tt> if no ActionGroupContent with the specified id has been registered or the
   * ActionGroupContent has no ActionGroup set (in case Actions or ActionGroups have been registered for the
   * ActionGroup, but the ActionGroup itself has not been registered)
   * 
   * @param actionGroupId
   *          the id of the ActionGroup
   * @return the ActionGroupContent or null if no ActionGroup has been registered for the id
   */
  public ActionGroupContent getActionGroupContent(String actionGroupId) {

    ActionGroupContent actionGroupContent = this._actionGroups.get(actionGroupId);
    if ((actionGroupContent != null) && actionGroupContent.hasActionGroup()) {
      return actionGroupContent;
    }

    // no ActionGroup has been registered for this id, don't render it
    return null;
  }

  /**
   * Adds the specified Action to registry.
   * 
   * <p>
   * An action can be registered even if the targetActionGroup of the action has not been registered yet
   * 
   * @param action
   */
  public void addAction(Action action) {
    addActionInternal(action);
    fireActionSetChangeEvent();
  }

  protected void addActionInternal(Action action) {
    ActionGroupContent target = getTarget(action);
    target.add(new LocatableActionGroupElement(action));
  }

  /**
   * Adds the specified ActionGroup to the registry.
   * 
   * <p>
   * An ActionGroup can be registered even if the targetActionGroup of the ActionGroup has not been registered yet
   * 
   * 
   * <p>
   * Note that only on ActionSetChangeEvent is fired, even if more than one Action or ActionGroups (due to
   * StaticActionGroupProvider or StaticActionProvider) are added
   * 
   * @param actionGroup
   *          the ActionGroup to add
   */
  public void addActionGroup(ActionGroup actionGroup) {
    addActionGroupInternal(actionGroup);
    fireActionSetChangeEvent();
  }

  protected void addActionGroupInternal(ActionGroup actionGroup) {
    ActionGroupContent target = getTarget(actionGroup);

    target.add(new LocatableActionGroupElement(actionGroup));
    ActionGroupContent list;
    if (this._actionGroups.containsKey(actionGroup.getId())) {
      list = this._actionGroups.get(actionGroup.getId());
      if (list.hasActionGroup()) {
        throw new RuntimeException("ActionGroup with id '" + actionGroup.getId() + "' already registered");
      }
    } else {
      list = new ActionGroupContent();
      this._actionGroups.put(actionGroup.getId(), list);
    }

    boolean isFinal = isFinal(actionGroup);
    if (isFinal) {
      // make sure only contribution from either StaticActionGroupProvider or StaticActionProvider (see below)
      // are added to the list
      list.removeAll();
    }

    if (actionGroup instanceof StaticActionGroupProvider) {
      StaticActionGroupProvider staticActionGroupProvider = (StaticActionGroupProvider) actionGroup;
      for (ActionGroup staticGroup : staticActionGroupProvider.getActionGroups()) {
        addActionGroupInternal(staticGroup);
      }
    }

    if (actionGroup instanceof StaticActionProvider) {
      StaticActionProvider staticActionProvider = (StaticActionProvider) actionGroup;
      for (Action staticAction : staticActionProvider.getActions()) {
        addActionInternal(staticAction);
      }
    }

    list.setActionGroup(actionGroup);
    if (isFinal) {
      // make sure no additional contributions are accepted for this ActionGroup anymore
      list.close();
    }

  }

  public void removeAction(Action action) {
    removeActionInternal(action);
    fireActionSetChangeEvent();
  }

  protected void removeActionInternal(Action action) {
    removeActionGroupElement(action);
  }

  public void removeActionGroup(final ActionGroup actionGroup) {
    removeActionGroupInternal(actionGroup);
    fireActionSetChangeEvent();
  }

  protected void removeActionGroupInternal(final ActionGroup actionGroup) {
    // remove ActionGroup from ActionSet
    removeActionGroupElement(actionGroup);

    // remove static Actions and ActionGroups as well
    ActionGroupContent menuItems = this._actionGroups.get(actionGroup.getId());
    menuItems.unsetActionGroup();
    if (actionGroup instanceof StaticActionGroupProvider) {
      StaticActionGroupProvider staticActionGroupProvider = (StaticActionGroupProvider) actionGroup;
      for (ActionGroup staticActionGroup : staticActionGroupProvider.getActionGroups()) {
        removeActionGroupInternal(staticActionGroup);
      }
    }
    if (actionGroup instanceof StaticActionProvider) {
      StaticActionProvider staticActionProvider = (StaticActionProvider) actionGroup;
      for (Action staticAction : staticActionProvider.getActions()) {
        removeActionInternal(staticAction);
      }
    }
  }

  public void addActionSetChangeListener(ActionSetChangeListener actionSetChangeListener) {
    this._actionSetChangeListeners.add(actionSetChangeListener);
  }

  public void removeActionSetChangeListener(ActionSetChangeListener actionSetChangeListener) {
    this._actionSetChangeListeners.remove(actionSetChangeListener);
  }

  protected void fireActionSetChangeEvent() {
    for (ActionSetChangeListener actionSetChangeListener : this._actionSetChangeListeners) {
      try {
        actionSetChangeListener.actionSetChange();
      } catch (RuntimeException ex) {
        ex.printStackTrace();
      }
    }
  }

  /**
   * Removes the specified ActionGroupElement from the registry.
   * 
   * @param element
   */
  public void removeActionGroupElement(ActionGroupElement element) {
    ActionLocation location = new ActionLocation(element);
    // Get the ActionGroupConent that contains the specified ActionGroupElement
    ActionGroupContent list = this._actionGroups.get(location.getTargetActionGroupId());
    if (list != null) {
      // remove the specified element from the content
      list.remove(element);
    }
  }

  /**
   * Returns the target ActionGroupContent for the specified ActionGroupElement. If no target ActionGroupContent has
   * been registered for the ACtionGroupElement, an new - empty - ActionGroupContent will be created and registered
   * under the ActionGroupElement's targetActionGroupId.
   * 
   * @param element
   * @return
   */
  protected ActionGroupContent getTarget(ActionGroupElement element) {
    // TODO make sure the specified AGE really belongs to this ActionSet
    TargetGroupIdParser parser = new TargetGroupIdParser(element.getTargetActionGroupId());
    if (!this._actionGroups.containsKey(parser.getTargetGroupId())) {
      // No content yet -> register a new
      ActionGroupContent list = new ActionGroupContent();
      this._actionGroups.put(parser.getTargetGroupId(), list);
    }

    return this._actionGroups.get(parser.getTargetGroupId());
  }

  /**
   * Returns true if the ActionGroup is an instance of StaticActionGroupProvider or StaticActionProvider and is marked
   * as <tt>final</tt>.
   * 
   * @see StaticActionGroupProvider#isFinal()
   * @see StaticActionProvider#isFinal()
   * @param actionGroup
   * @return
   */
  protected boolean isFinal(ActionGroup actionGroup) {
    if ((actionGroup instanceof StaticActionGroupProvider) && ((StaticActionGroupProvider) actionGroup).isFinal()) {
      return true;
    }

    if ((actionGroup instanceof StaticActionProvider) && ((StaticActionProvider) actionGroup).isFinal()) {
      return true;
    }
    return false;
  }

}