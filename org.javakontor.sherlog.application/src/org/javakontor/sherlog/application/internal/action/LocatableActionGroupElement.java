package org.javakontor.sherlog.application.internal.action;

import org.javakontor.sherlog.application.action.Action;
import org.javakontor.sherlog.application.action.ActionGroup;
import org.javakontor.sherlog.application.action.ActionGroupElement;

/**
 * A {@link LocatableActionGroupElement} implements a {@link LocatableElement} for an ActionGroupElement
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class LocatableActionGroupElement implements LocatableElement {

  private final ActionGroupElement _element;

  private final ActionLocation     _location;

  /**
   * Contstructs a new LocatableActionGroupElement instance for the given ActionGroupElement. The target location for
   * the ActionGroupElement will be determined from the ActionGroupElements
   * {@link ActionGroupElement#getTargetActionGroupId() targetActionGroupId}
   * 
   * @param element
   *          The ActionGroupElement
   */
  public LocatableActionGroupElement(ActionGroupElement element) {
    super();
    _element = element;
    _location = new ActionLocation(element);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.javakontor.sherlog.application.internal.menu.LocatableElement#getElement()
   */
  public ActionGroupElement getElement() {
    return _element;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.javakontor.sherlog.application.internal.menu.LocatableElement#getLocation()
   */
  public ActionLocation getLocation() {
    return _location;
  }

  /**
   * Returns true if the ActionGroupElement wrapped by this {@link LocatableActionGroupElement} is an instance of
   * {@link Action}
   * 
   * @return
   */
  public boolean isAction() {
    return (this._element instanceof Action);
  }

  /**
   * Returns true if the ActionGroupElement wrapped by this {@link LocatableActionGroupElement} is an instance of
   * {@link ActionGroup}
   * 
   * @return
   */
  public boolean isActionGroup() {
    return (this._element instanceof ActionGroup);
  }

  /**
   * Convenience method: Returns the wrapped ActionGroupElement casted to an {@link Action}
   * 
   * @throws ClassCastException
   *           if the wrapped ActionGroupElement is not an Action
   * 
   * @see #isAction()
   * @return the ActionGroupElement casted to an Action
   */
  public Action getAction() {
    return (Action) this._element;
  }

  /**
   * Convenience method: Returns the wrapped ActionGroupElement casted to an {@link ActionGroup}
   * 
   * @throws ClassCastException
   *           if the wrapped ActionGroupElement is not an ActionGroup
   * 
   * @see #isActionGroup()
   * @return the ActionGroupElement casted to an Action
   */
  public ActionGroup getActionGroup() {
    return (ActionGroup) this._element;
  }

}
