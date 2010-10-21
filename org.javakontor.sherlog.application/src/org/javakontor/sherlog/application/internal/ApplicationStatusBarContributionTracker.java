package org.javakontor.sherlog.application.internal;

import javax.swing.SwingUtilities;

import org.javakontor.sherlog.application.contrib.ApplicationStatusBarContribution;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * A {@link ServiceTracker} that tracks {@link ApplicationStatusBarContribution ApplicationStatusBarContributions} and
 * add them to the {@link ApplicationWindow}
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * 
 */
public class ApplicationStatusBarContributionTracker extends ServiceTracker {

  private final ApplicationWindow _applicationWindow;

  public ApplicationStatusBarContributionTracker(final BundleContext context, final ApplicationWindow applicationWindow) {
    super(context, ApplicationStatusBarContribution.class.getName(), null);
    this._applicationWindow = applicationWindow;
  }

  @Override
  public Object addingService(final ServiceReference reference) {
    final ApplicationStatusBarContribution contribution = (ApplicationStatusBarContribution) super
        .addingService(reference);
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        ApplicationStatusBarContributionTracker.this._applicationWindow.addStatusBarContribution(contribution);
      }
    });

    return contribution;
  }

  @Override
  public void removedService(final ServiceReference reference, final Object service) {
    super.removedService(reference, service);

    final ApplicationStatusBarContribution contribution = (ApplicationStatusBarContribution) service;

    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        ApplicationStatusBarContributionTracker.this._applicationWindow.removeStatusBarContribution(contribution);
      }
    });

  }
}
