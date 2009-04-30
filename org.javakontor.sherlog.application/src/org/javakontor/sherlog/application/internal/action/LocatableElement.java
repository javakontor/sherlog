package org.javakontor.sherlog.application.internal.action;

import org.javakontor.sherlog.application.action.contrib.ActionGroupElementContribution;

/**
 * Wraps an {@link ActionGroupElementContribution} together with an {@link ActionLocation} describing the target position of the
 * ActionGroupElement.
 * 
 * <p>
 * A set of LocatableElements can be ordered according to their target positions using a {@link LocatableElementSorter}
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public interface LocatableElement {

  /**
   * Return the wrapped {@link ActionGroupElementContribution}
   * 
   * @return the wrapped {@link ActionGroupElementContribution}
   */
  public ActionGroupElementContribution getElement();

  /**
   * Returns the location of the wrapped ActionGroupElement
   * 
   * @return
   */
  public ActionLocation getLocation();
}
