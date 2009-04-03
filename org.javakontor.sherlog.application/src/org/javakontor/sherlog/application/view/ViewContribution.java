package org.javakontor.sherlog.application.view;

import javax.swing.JPanel;

import org.javakontor.sherlog.application.request.RequestHandler;

/**
 * <p>
 * </p>
 */
public interface ViewContribution extends RequestHandler {

  /**
   * <p>
   * Returns the {@link ViewContributionDescriptor} for this {@link ViewContribution}.
   * </p>
   * 
   * @return the {@link ViewContributionDescriptor} for this {@link ViewContribution}.
   */
  public ViewContributionDescriptor getDescriptor();

  /**
   * <p>
   * Returns the {@link JPanel} for this {@link ViewContribution}.
   * </p>
   */
  public JPanel getPanel();

  /**
   * <p>
   * Is called if a {@link ViewEvent} occurred.
   * </p>
   * 
   * @param viewEvent
   *          the {@link ViewEvent}.
   */
  public void viewEventOccured(ViewEvent viewEvent);

  /**
   * <p>
   * </p>
   */
  public enum ViewEvent {
    /** - */
    windowActivated,

    /** - */
    windowDeactivated,

    /** - */
    windowOpened,

    /** - */
    windowClosing
  }
}
