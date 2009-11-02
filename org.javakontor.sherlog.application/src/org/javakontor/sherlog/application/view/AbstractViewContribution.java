package org.javakontor.sherlog.application.view;

import org.javakontor.sherlog.application.request.RequestHandlerImpl;

/**
 * <p>
 * Abstract base class of interface {@link ViewContribution}.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractViewContribution extends RequestHandlerImpl implements ViewContribution {

  /**
   * @see org.javakontor.sherlog.application.view.ViewContribution#viewEventOccured(org.javakontor.sherlog.application.view
   *      .ViewContribution.ViewEvent)
   */
  public void viewEventOccured(final ViewEvent viewEvent) {
    // empty implementation
  }
}
