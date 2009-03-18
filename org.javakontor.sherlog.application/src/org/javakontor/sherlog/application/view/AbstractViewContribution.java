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
  
  public void viewEventOccured(ViewEvent viewEvent) {
    // empty implementation
  }
}
