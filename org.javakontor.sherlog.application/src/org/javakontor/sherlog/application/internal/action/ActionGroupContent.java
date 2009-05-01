package org.javakontor.sherlog.application.internal.action;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javakontor.sherlog.application.action.AbstractActionGroupElementContribution;
import org.javakontor.sherlog.application.action.ActionGroupContribution;
import org.javakontor.sherlog.application.action.ActionGroupElementContribution;
import org.javakontor.sherlog.util.Assert;

/**
 * Holds the content of one {@link ActionGroupContribution}, that is all Actions and ActionGroups with the same
 * {@link AbstractActionGroupElementContribution#getTargetActionGroupId() targetActionGroupId}.
 * 
 * <p>
 * Note: The ActionGroupContent can be filled with Actions and ActionGroups <b>before</b> the ActionGroup itself is
 * registered. This is due to the fact that a bundle can register an Action or an ActionGroup for an ActionGroup
 * <b>before</b> a bundle registeres the target ActionGroup itself. Client that use ActionGroupContent might consider to
 * ignore ActionGroupContent instances that have no ActionGroup set.
 * 
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class ActionGroupContent {

  private static final long                          serialVersionUID = 1L;

  protected Log                                      _logger          = LogFactory.getLog(getClass());

  /**
   * The ActionGroup for that this ActionGroupContent contains the content
   * 
   * Maybe <tt>null</tt> if there has been a registration for the ActionGroup but the ActionGroup itself has not been
   * registered yet (i.e. a child has been registered before its parent)
   */
  private ActionGroupContribution                    _actionGroup;

  /**
   * The ActionGroupElements that are registered for the ActionGroup
   */
  private final List<ActionGroupElementContribution> _actionGroupElements;

  /**
   * Determines whether it is allowed to add more content to this ActionGroupContent.
   * 
   */
  private boolean                                    _open;

  public ActionGroupContent() {
    this._actionGroupElements = new LinkedList<ActionGroupElementContribution>();
    this._open = true;
  }

  /**
   * Adds the given actionGroupElement to this content. If this ActionGroupContent is closed, the request will be
   * ignored.
   * 
   * <p>
   * If this ActionGroupContent already contains an ActionGroupElement with the same id as the specified
   * actionGroupElement's id, an IllegalStateException is thrown.
   * 
   * @param actionGroupElement
   *          The element to add
   * @return true if the element has been added. False if this content is closed and the request has been ignored
   */
  public synchronized boolean add(final ActionGroupElementContribution actionGroupElement) {
    Assert.notNull(actionGroupElement);

    if (containsActionGroupElementId(actionGroupElement.getId())) {
      // TODO should we ignore this ?
      throw new IllegalStateException("ActionGroup already contains a child with id '" + actionGroupElement.getId()
          + "'");
    }

    if (!isOpen()) {
      this._logger.warn("ignoring add request to closed ActionGroup '" + this._actionGroup.getId() + "'");
      return false;
    }

    this._actionGroupElements.add(actionGroupElement);
    return true;
  }

  // /**
  // * Removes the given actionGroupElement from this content
  // *
  // * @param menuItem
  // * @return true if this content contained the specified locatableActionGroupElement
  // */
  // public boolean remove(ActionGroupElement actionGroupElement) {
  // return this._actionGroupElements.remove(actionGroupElement);
  // }

  /**
   * Returns true if this ActionGroupContent is "open", that is, it allows adding of more ActionGroupElements
   * 
   * @return
   */
  public boolean isOpen() {
    return this._open;
  }

  /**
   * "Closes" this ActionGroupContent and forbidds adding more items to this ActionGroupContent (All further calls to
   * {@link #add(LocatableActionGroupElement)} will be ignored)
   * 
   * 
   */
  public void close() {
    this._open = false;
  }

  /**
   * Returns true if an ActionGroup is set
   * 
   * @return
   */
  public boolean hasActionGroup() {
    return (this._actionGroup != null);
  }

  /**
   * Returns the ActionGroup or null if no ActionGroup has been set
   * 
   * @return
   */
  public ActionGroupContribution getActionGroup() {
    return this._actionGroup;
  }

  /**
   * Sets the ActionGroup instance this ActionGroupContent holds the content for
   * 
   * <p>
   * Will be called when the ActionGroup has been registered.
   * 
   * @param actionGroup
   */
  public void setActionGroup(final ActionGroupContribution actionGroup) {
    this._actionGroup = actionGroup;
  }

  /**
   * Removes the ActionGroup (will be invoked if the ActionGroup is unregistered)
   * 
   * <p>
   * Removing the ActionGroup leads to an "open" ActionGroupContent even if the ActionGroupContent has been closed
   * before (since no ActionGroup is set anymore the ActionGroup cannot be 'final')
   */
  public void unsetActionGroup() {
    this._actionGroup = null;
    this._open = true;
  }

  public synchronized void removeAll() {
    this._actionGroupElements.clear();
  }

  public synchronized void remove(final ActionGroupElementContribution element) {
    final Iterator<ActionGroupElementContribution> it = this._actionGroupElements.iterator();
    while (it.hasNext()) {
      final ActionGroupElementContribution menuItem = it.next();
      if (menuItem.equals(element)) {
        it.remove();
        break;
      }
    }
  }

  /**
   * Returns <tt>true</tt> if this ActionGroup already contains a child with the specified id
   * 
   * @param id
   *          the id to check
   * @return true if there is already a child with the specified id
   */
  private boolean containsActionGroupElementId(final String id) {
    for (final ActionGroupElementContribution actionGroupElement : this._actionGroupElements) {
      if (id.equals(actionGroupElement.getId())) {
        return true;
      }
    }

    return false;
  }

  public Collection<ActionGroupElementContribution> getAll() {
    return this._actionGroupElements;
  }

}