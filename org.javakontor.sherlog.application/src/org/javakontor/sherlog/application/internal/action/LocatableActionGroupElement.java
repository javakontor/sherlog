package org.javakontor.sherlog.application.internal.action;

import org.javakontor.sherlog.application.action.contrib.ActionContribution;
import org.javakontor.sherlog.application.action.contrib.ActionGroupContribution;
import org.javakontor.sherlog.application.action.contrib.ActionGroupElementContribution;

/**
 * A {@link LocatableActionGroupElement} implements a {@link LocatableElement} for an ActionGroupElement
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class LocatableActionGroupElement implements LocatableElement {

  private final ActionGroupElementContribution _element;

  private final ActionLocation     _location;

  /**
   * Contstructs a new LocatableActionGroupElement instance for the given ActionGroupElement. The target location for
   * the ActionGroupElement will be determined from the ActionGroupElements
   * {@link ActionGroupElementContribution#getTargetActionGroupId() targetActionGroupId}
   * 
   * @param element
   *          The ActionGroupElement
   */
  public LocatableActionGroupElement(ActionGroupElementContribution element) {
    super();
    _element = element;
    _location = new ActionLocation(element);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.javakontor.sherlog.application.internal.menu.LocatableElement#getElement()
   */
  public ActionGroupElementContribution getElement() {
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
   * {@link ActionContribution}
   * 
   * @return
   */
  public boolean isAction() {
    return (this._element instanceof ActionContribution);
  }

  /**
   * Returns true if the ActionGroupElement wrapped by this {@link LocatableActionGroupElement} is an instance of
   * {@link ActionGroupContribution}
   * 
   * @return
   */
  public boolean isActionGroup() {
    return (this._element instanceof ActionGroupContribution);
  }

  /**
   * Convenience method: Returns the wrapped ActionGroupElement casted to an {@link ActionContribution}
   * 
   * @throws ClassCastException
   *           if the wrapped ActionGroupElement is not an Action
   * 
   * @see #isAction()
   * @return the ActionGroupElement casted to an Action
   */
  public ActionContribution getAction() {
    return (ActionContribution) this._element;
  }

  /**
   * Convenience method: Returns the wrapped ActionGroupElement casted to an {@link ActionGroupContribution}
   * 
   * @throws ClassCastException
   *           if the wrapped ActionGroupElement is not an ActionGroup
   * 
   * @see #isActionGroup()
   * @return the ActionGroupElement casted to an Action
   */
  public ActionGroupContribution getActionGroup() {
    return (ActionGroupContribution) this._element;
  }

}
