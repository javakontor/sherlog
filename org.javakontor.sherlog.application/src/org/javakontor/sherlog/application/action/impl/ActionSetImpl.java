package org.javakontor.sherlog.application.action.impl;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import org.javakontor.sherlog.application.action.Action;
import org.javakontor.sherlog.application.action.ActionGroup;
import org.javakontor.sherlog.application.action.ActionGroupElement;
import org.javakontor.sherlog.application.action.ActionSet;
import org.javakontor.sherlog.application.action.ActionSetChangeListener;
import org.javakontor.sherlog.application.action.StaticActionGroupProvider;
import org.javakontor.sherlog.application.action.StaticActionProvider;
import org.javakontor.sherlog.application.internal.action.ActionLocation;
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
  public ActionSetImpl(final String rootId) {
    this._rootId = rootId;
    this._actionSetChangeListeners = new CopyOnWriteArraySet<ActionSetChangeListener>();
    this._actionGroups = new Hashtable<String, ActionGroupContent>();
    this._actionGroups.put(rootId, new ActionGroupContent());
  }

  public Collection<ActionGroupElement> getRootActionGroupContent() {
    return this._actionGroups.get(this._rootId).getAll();
  }

  public Collection<ActionGroupElement> getActionGroupContent(final String actionGroupId) {

    final ActionGroupContent actionGroupContent = this._actionGroups.get(actionGroupId);
    if ((actionGroupContent != null) && actionGroupContent.hasActionGroup()) {
      return actionGroupContent.getAll();
    }

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
  public void addAction(final Action action) {
    addActionInternal(action);
    fireActionSetChangeEvent();
  }

  protected void addActionInternal(final Action action) {
    final ActionGroupContent target = getTarget(action);
    target.add(action);
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
  public void addActionGroup(final ActionGroup actionGroup) {
    addActionGroupInternal(actionGroup);
    fireActionSetChangeEvent();
  }

  protected void addActionGroupInternal(final ActionGroup actionGroup) {
    final ActionGroupContent target = getTarget(actionGroup);

    target.add(actionGroup);
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

    final boolean isFinal = isFinal(actionGroup);
    if (isFinal) {
      // make sure only contribution from either StaticActionGroupProvider or StaticActionProvider (see below)
      // are added to the list
      list.removeAll();
    }

    if (actionGroup instanceof StaticActionGroupProvider) {
      final StaticActionGroupProvider staticActionGroupProvider = (StaticActionGroupProvider) actionGroup;
      for (final ActionGroup staticGroup : staticActionGroupProvider.getActionGroups()) {
        addActionGroupInternal(staticGroup);
      }
    }

    if (actionGroup instanceof StaticActionProvider) {
      final StaticActionProvider staticActionProvider = (StaticActionProvider) actionGroup;
      for (final Action staticAction : staticActionProvider.getActions()) {
        addActionInternal(staticAction);
      }
    }

    list.setActionGroup(actionGroup);
    if (isFinal) {
      // make sure no additional contributions are accepted for this ActionGroup anymore
      list.close();
    }

  }

  public void removeAction(final Action action) {
    removeActionInternal(action);
    fireActionSetChangeEvent();
  }

  protected void removeActionInternal(final Action action) {
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
    final ActionGroupContent menuItems = this._actionGroups.get(actionGroup.getId());
    menuItems.unsetActionGroup();
    if (actionGroup instanceof StaticActionGroupProvider) {
      final StaticActionGroupProvider staticActionGroupProvider = (StaticActionGroupProvider) actionGroup;
      for (final ActionGroup staticActionGroup : staticActionGroupProvider.getActionGroups()) {
        removeActionGroupInternal(staticActionGroup);
      }
    }
    if (actionGroup instanceof StaticActionProvider) {
      final StaticActionProvider staticActionProvider = (StaticActionProvider) actionGroup;
      for (final Action staticAction : staticActionProvider.getActions()) {
        removeActionInternal(staticAction);
      }
    }
  }

  public void addActionSetChangeListener(final ActionSetChangeListener actionSetChangeListener) {
    this._actionSetChangeListeners.add(actionSetChangeListener);
  }

  public void removeActionSetChangeListener(final ActionSetChangeListener actionSetChangeListener) {
    this._actionSetChangeListeners.remove(actionSetChangeListener);
  }

  protected void fireActionSetChangeEvent() {
    for (final ActionSetChangeListener actionSetChangeListener : this._actionSetChangeListeners) {
      try {
        actionSetChangeListener.actionSetChange();
      } catch (final RuntimeException ex) {
        ex.printStackTrace();
      }
    }
  }

  /**
   * Removes the specified ActionGroupElement from the registry.
   * 
   * @param element
   */
  public void removeActionGroupElement(final ActionGroupElement element) {
    final ActionLocation location = new ActionLocation(element);
    // Get the ActionGroupConent that contains the specified ActionGroupElement
    final ActionGroupContent list = this._actionGroups.get(location.getTargetActionGroupId());
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
  protected ActionGroupContent getTarget(final ActionGroupElement element) {
    final TargetGroupIdParser parser = new TargetGroupIdParser(element.getTargetActionGroupId());
    if (!this._rootId.equals(parser.getActionRoot())) {
      throw new IllegalStateException("Invalid ActionGroupElement. Action-Root '" + parser.getActionRoot()
          + "' doesn't belong to this ActionSet with root-id '" + this._rootId + "'");
    }
    if (!this._actionGroups.containsKey(parser.getTargetGroupId())) {
      // No content yet -> register a new
      final ActionGroupContent list = new ActionGroupContent();
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
  protected boolean isFinal(final ActionGroup actionGroup) {
    if ((actionGroup instanceof StaticActionGroupProvider) && ((StaticActionGroupProvider) actionGroup).isFinal()) {
      return true;
    }

    if ((actionGroup instanceof StaticActionProvider) && ((StaticActionProvider) actionGroup).isFinal()) {
      return true;
    }
    return false;
  }

}
