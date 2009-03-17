package org.javakontor.sherlog.application.action;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javakontor.sherlog.application.internal.action.LocatableActionGroupElement;


/**
 * Holds the content of one {@link ActionGroup}, that is all Actions and ActionGroups with the same
 * {@link ActionGroupElement#getTargetActionGroupId() targetActionGroupId}.
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

  private static final long                       serialVersionUID = 1L;

  protected Log                                _logger          = LogFactory.getLog(getClass());

  /**
   * The ActionGroup for that this ActionGroupContent contains the content
   * 
   * Maybe <tt>null</tt> if there has been a registration for the ActionGroup but the ActionGroup itself has not been
   * registered yet (i.e. a child has been registered before its parent)
   */
  private ActionGroup                             _actionGroup;

  /**
   * The ActionGroupElements that are registered for the ActionGroup
   */
  private final List<LocatableActionGroupElement> _actionGroupElements;

  /**
   * Determines whether it is allowed to add more content to this ActionGroupContent.
   * 
   */
  private boolean                                 _open;

  public ActionGroupContent() {
    this._actionGroupElements = new LinkedList<LocatableActionGroupElement>();
    this._open = true;
  }

  /**
   * Adds the given locatableActionGroupElement to this content. If this ActionGroupContent is closed, the request will
   * be ignored
   * 
   * @param locatableActionGroupElement
   *          The element to add
   * @return true if the element has been added. False if this content is closed and the request has been ignored
   */
  public boolean add(LocatableActionGroupElement locatableActionGroupElement) {
    if (!isOpen()) {
      this._logger.warn("WARN ignoring add request to closed ActionGroup");
      return false;
    }

    this._actionGroupElements.add(locatableActionGroupElement);
    return true;
  }

  /**
   * Removes the given locatableActionGroupElement from this content
   * 
   * @param menuItem
   * @return true if this content contained the specified locatableActionGroupElement
   */
  public boolean remove(LocatableActionGroupElement locatableActionGroupElement) {
    return this._actionGroupElements.remove(locatableActionGroupElement);
  }

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
  public ActionGroup getActionGroup() {
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
  public void setActionGroup(ActionGroup actionGroup) {
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

  public void removeAll() {
    this._actionGroupElements.clear();
  }

  public void remove(ActionGroupElement element) {
    Iterator<LocatableActionGroupElement> it = this._actionGroupElements.iterator();
    while (it.hasNext()) {
      LocatableActionGroupElement menuItem = it.next();
      if (menuItem.getElement().equals(element)) {
        it.remove();
        break;
      }
    }

  }

  public Collection<LocatableActionGroupElement> getAll() {
    return this._actionGroupElements;
  }

}