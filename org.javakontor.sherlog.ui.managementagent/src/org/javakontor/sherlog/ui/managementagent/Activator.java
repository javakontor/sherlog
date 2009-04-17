package org.javakontor.sherlog.ui.managementagent;

import static org.javakontor.sherlog.application.menu.MenuConstants.TOOLS_MENU_ID;
import static org.javakontor.sherlog.application.menu.MenuConstants.TOOLS_MENU_TARGET_ID;

import org.javakontor.sherlog.application.action.impl.AbstractToggleAction;
import org.javakontor.sherlog.application.action.impl.ActionGroupElementServiceHelper;
import org.javakontor.sherlog.application.mvc.AbstractMvcViewContribution;
import org.javakontor.sherlog.application.view.DefaultViewContributionDescriptor;
import org.javakontor.sherlog.application.view.ViewContribution;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

  private BundleContext            _bundleContext;

  private ShowBundleListViewAction _showBundleListAction;

  private ServiceRegistration      _registration;

  public ServiceRegistration getRegistration() {
    return _registration;
  }

  public boolean hasRegistration() {
    return _registration != null;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
   */
  public void start(BundleContext context) throws Exception {

    this._bundleContext = context;
    _showBundleListAction = new ShowBundleListViewAction(TOOLS_MENU_ID + ".bundlelist", TOOLS_MENU_TARGET_ID,
        BundleListMessages.bundleListMenuTitle);
    ActionGroupElementServiceHelper.registerAction(context, _showBundleListAction);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
   */
  public void stop(BundleContext context) throws Exception {
  }

  public void registerBundleListView() {
    if (hasRegistration()) {
      return;
    }
    BundleListViewContribution dialog = new BundleListViewContribution(_bundleContext);
    _registration = _bundleContext.registerService(ViewContribution.class.getName(), dialog, null);

    updateMenuState();
  }

  public void unregisterBundleListView() {
    if (!hasRegistration()) {
      return;
    }

    _registration.unregister();
    _registration = null;

    updateMenuState();
  }

  protected void updateMenuState() {
    // update menu "active" state
    if (_showBundleListAction != null) {
      _showBundleListAction.setActive(hasRegistration());
    }
  }

  class ShowBundleListViewAction extends AbstractToggleAction {

    public ShowBundleListViewAction(String actionId, String targetActionGroupId, String label) {
      super(actionId, targetActionGroupId, label);
    }

    public void execute() {
      if (isActive() || _registration == null) {
        registerBundleListView();
      } else {
        unregisterBundleListView();
      }
    }

    @Override
    public String getDefaultShortcut() {
      return BundleListMessages.bundleListMenuDefaultShortcut;
    }

  }

  class BundleListViewContribution extends
      AbstractMvcViewContribution<ManagementAgentModel, ManagementAgentView, ManagementAgentController> {

    /**
     * @param bundleContext
     */
    public BundleListViewContribution(BundleContext bundleContext) {
      super(new DefaultViewContributionDescriptor(BundleListMessages.bundleListWindowTitle, false, true, true, true,
          false), new ManagementAgentModel(bundleContext), ManagementAgentView.class, ManagementAgentController.class);
    }

    @Override
    public void viewEventOccured(ViewEvent viewEvent) {

      if (viewEvent == ViewEvent.windowClosing) {
        unregisterBundleListView();
      }
    }
  }
}
