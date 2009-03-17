package org.javakontor.sherlog.application.internal.action;

import org.javakontor.sherlog.application.action.ActionGroupElement;

/**
 * Wraps an {@link ActionGroupElement} together with an {@link ActionLocation} describing the target position of the
 * ActionGroupElement.
 * 
 * <p>
 * A set of LocatableElements can be ordered according to their target positions using a {@link LocatableElementSorter}
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public interface LocatableElement {

  /**
   * Return the wrapped {@link ActionGroupElement}
   * 
   * @return the wrapped {@link ActionGroupElement}
   */
  public ActionGroupElement getElement();

  /**
   * Returns the location of the wrapped ActionGroupElement
   * 
   * @return
   */
  public ActionLocation getLocation();
}
