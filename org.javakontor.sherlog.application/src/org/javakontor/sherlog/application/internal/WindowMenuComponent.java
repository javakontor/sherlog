package org.javakontor.sherlog.application.internal;

import static org.javakontor.sherlog.application.action.MenuConstants.MENUBAR_ID;
import static org.javakontor.sherlog.application.action.MenuConstants.WINDOW_MENU_ID;
import static org.javakontor.sherlog.application.action.MenuConstants.WINDOW_MENU_TARGET_ID;

import java.util.Hashtable;
import java.util.Map;

import javax.swing.JInternalFrame;
import javax.swing.SwingUtilities;

import org.javakontor.sherlog.application.action.AbstractAction;
import org.javakontor.sherlog.application.action.ActionContribution;
import org.javakontor.sherlog.application.action.ActionGroupContribution;
import org.javakontor.sherlog.application.action.DefaultActionContribution;
import org.javakontor.sherlog.application.action.DefaultActionGroupContribution;
import org.javakontor.sherlog.application.view.ViewContribution;
import org.javakontor.sherlog.util.servicemanager.DefaultServiceManager;
import org.javakontor.sherlog.util.servicemanager.ServiceManagerEvent;
import org.javakontor.sherlog.util.servicemanager.ServiceManagerListener;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;

public class WindowMenuComponent {

  public static final String                               WINDOW_WINDOWLIST_MENU_ID        = WINDOW_MENU_ID
                                                                                                + ".windowlist";

  public static final String                               WINDOW_WINDOWLIST_MENU_TARGET_ID = MENUBAR_ID
                                                                                                + "/"
                                                                                                + WINDOW_WINDOWLIST_MENU_ID;

  private BundleContext                                    _bundleContext;

  private final DefaultServiceManager<ViewContribution>    _viewContributionServiceManager;

  private final Map<ViewContribution, ServiceRegistration> _registrations;

  private final ViewContributionServiceListener            _viewContributionServiceListener;

  private long                                             _counter                         = 1;

  private ServiceRegistration                              _windowListActionGroupRegistration;

  public WindowMenuComponent() {
    this._registrations = new Hashtable<ViewContribution, ServiceRegistration>();
    this._viewContributionServiceListener = new ViewContributionServiceListener();
    this._viewContributionServiceManager = new DefaultServiceManager<ViewContribution>();
  }

  public void activate(final ComponentContext componentContext) {
    this._bundleContext = componentContext.getBundleContext();

    final ActionGroupContribution windowListActionGroup = new DefaultActionGroupContribution(WINDOW_WINDOWLIST_MENU_ID,
        WINDOW_MENU_TARGET_ID + "(last)", null);
    this._windowListActionGroupRegistration = this._bundleContext.registerService(ActionGroupContribution.class
        .getName(), windowListActionGroup, null);

    this._viewContributionServiceManager.addServiceManagerListener(this._viewContributionServiceListener);

  }

  public void deactivate(final ComponentContext componentContext) {
    this._viewContributionServiceManager.removeServiceManagerListener(this._viewContributionServiceListener);
    this._windowListActionGroupRegistration.unregister();
    this._bundleContext = null;
  }

  // public void bindViewContributionServiceManager(ServiceManager<ViewContribution> viewContributionServiceManager) {
  //
  // // remove listener from existing service manager
  // // this automatically removes all registrations too
  // if (this._viewContributionServiceManager != null) {
  // this._viewContributionServiceManager.removeServiceManagerListener(this._viewContributionServiceListener);
  // }
  //
  // // set new service manager
  // this._viewContributionServiceManager = viewContributionServiceManager;
  //
  // // add listener to new service manager
  // if (this._viewContributionServiceManager != null) {
  // this._viewContributionServiceManager.addServiceManagerListener(this._viewContributionServiceListener);
  // }
  // }

  public void bindViewContribution(final ViewContribution viewContribution) {
    this._viewContributionServiceManager.bindService(viewContribution);
  }

  public void unbindViewContribution(final ViewContribution viewContribution) {
    this._viewContributionServiceManager.unbindService(viewContribution);
  }

  /**
   * An {@link ActionContribution}, that brings the window of a {@link ViewContribution} to front
   * 
   */
  class OpenWindowAction extends AbstractAction {

    private final ViewContribution _viewContribution;

    public OpenWindowAction(final ViewContribution viewContribution) {
      this._viewContribution = viewContribution;
    }

    public void execute() {
      // Find the JInternalFrame that holds the ViewContribution
      final JInternalFrame window = (JInternalFrame) SwingUtilities.getAncestorOfClass(JInternalFrame.class,
          this._viewContribution.getPanel());

      // bring it to front
      if (window != null) {
        window.toFront();
        window.requestFocusInWindow();
      }
    }
  }

  class ViewContributionServiceListener implements ServiceManagerListener<ViewContribution> {
    public void serviceAdded(final ServiceManagerEvent<ViewContribution> event) {
      final ViewContribution viewContribution = event.getService();
      final String serviceId = "vc-" + (WindowMenuComponent.this._counter++);
      // Properties serviceProperties =
      // ActionGroupElementServiceHelper.createServiceProperties(WINDOW_WINDOWLIST_MENU_ID
      // + "." + serviceId, WINDOW_WINDOWLIST_MENU_TARGET_ID, viewContribution.getDescriptor().getDialogName());

      final ActionContribution contribution = new DefaultActionContribution(
          WINDOW_WINDOWLIST_MENU_ID + "." + serviceId, WINDOW_WINDOWLIST_MENU_TARGET_ID, viewContribution
              .getDescriptor().getName(), null, new OpenWindowAction(viewContribution));

      final ServiceRegistration registration = WindowMenuComponent.this._bundleContext.registerService(
          ActionContribution.class.getName(), contribution, null);
      WindowMenuComponent.this._registrations.put(viewContribution, registration);
    }

    public void serviceRemoved(final ServiceManagerEvent<ViewContribution> event) {
      final ViewContribution viewContribution = event.getService();
      final ServiceRegistration serviceRegistration = WindowMenuComponent.this._registrations.remove(viewContribution);
      if (serviceRegistration != null) {
        serviceRegistration.unregister();
      }
    }
  }

}
