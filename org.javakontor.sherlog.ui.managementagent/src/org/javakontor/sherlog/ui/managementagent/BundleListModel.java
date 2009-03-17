package org.javakontor.sherlog.ui.managementagent;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javakontor.sherlog.application.mvc.AbstractModel;
import org.javakontor.sherlog.application.request.SetStatusMessageRequest;
import org.javakontor.sherlog.application.request.StatusMessage;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleEvent;
import org.osgi.framework.BundleException;
import org.osgi.framework.BundleListener;
import org.osgi.framework.ServiceReference;

/**
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BundleListModel extends AbstractModel<BundleListModel, BundleListModelReasonForChange> {

  private final Log           _logger = LogFactory.getLog(getClass());

  private final BundleContext _bundleContext;

  private Map<Long, Bundle>   _bundles;

  private Bundle              _selectedBundle;

  private BundleListener      _bundleListener;

  public BundleListModel(BundleContext bundleContext) {
    super();

    this._bundleContext = bundleContext;

    this._bundles = new HashMap<Long, Bundle>();

    this._selectedBundle = null;

    this._bundleListener = new BundleListener() {

      public void bundleChanged(final BundleEvent event) {

        GuiExecutor.execute(new Runnable() {
          public void run() {

            switch (event.getType()) {
            case BundleEvent.INSTALLED:
              addBundle(event.getBundle());
              fireModelChangedEvent(BundleListModelReasonForChange.bundleListChanged);
              break;
            case BundleEvent.UNINSTALLED:
              removeBundle(event.getBundle());
              fireModelChangedEvent(BundleListModelReasonForChange.bundleListChanged);
              break;
            default:
              fireModelChangedEvent(BundleListModelReasonForChange.bundleStateChanged, getBundles().indexOf(
                  event.getBundle()));
              break;
            }
          }
        });
      }
    };

    this._bundleContext.addBundleListener(this._bundleListener);

    Bundle[] bundles = this._bundleContext.getBundles();
    for (Bundle bundle : bundles) {
      addBundle(bundle);
    }
  }

  @Override
  public void dispose() {
    super.dispose();

    this._bundleContext.removeBundleListener(this._bundleListener);
  }

  public Bundle getSelectedBundle() {
    return _selectedBundle;
  }

  public void setSelectedBundle(Bundle selectedBundle) {
    _selectedBundle = selectedBundle;
    fireModelChangedEvent(BundleListModelReasonForChange.selectionChanged);

    if (_selectedBundle != null) {
      ServiceReference[] registeredServices = _selectedBundle.getRegisteredServices();
      int registeredServiceCount = (registeredServices == null ? 0 : registeredServices.length);
      ServiceReference[] servicesInUse = _selectedBundle.getServicesInUse();
      int servicesInUseCount = (servicesInUse == null ? 0 : servicesInUse.length);

      sendInfoStatusMessage(String.format(BundleListMessages.defaultStatusMessage, _selectedBundle.getSymbolicName(),
          registeredServiceCount, servicesInUseCount));
    } else {
      sendInfoStatusMessage("No bundle selected");
    }
  }

  public boolean hasSelectedBundle() {
    return _selectedBundle != null;
  }

  public List<Bundle> getBundles() {
    return new ArrayList<Bundle>(this._bundles.values());
  }

  public boolean canStartSelectedBundle() {
    return hasSelectedBundle()
        && (_selectedBundle.getState() == Bundle.INSTALLED || _selectedBundle.getState() == Bundle.RESOLVED);
  }

  public boolean canStopSelectedBundle() {
    return hasSelectedBundle() && (_selectedBundle.getState() == Bundle.ACTIVE);
  }

  public boolean canUpdateSelectedBundle() {
    return hasSelectedBundle() && (_selectedBundle.getState() != Bundle.UNINSTALLED);
  }

  public boolean canUninstallSelectedBundle() {
    return hasSelectedBundle() && (_selectedBundle.getState() != Bundle.UNINSTALLED);
  }

  public void startSelectedBundle() {
    if (!hasSelectedBundle()) {
      return;
    }

    try {
      _selectedBundle.start();
      sendInfoStatusMessage("Bundle " + _selectedBundle.getSymbolicName() + " installed");
    } catch (BundleException e) {
      _logger.error("Bundle start failed: ", e);
      sendErrorStatusMessage("Bundle start failed: " + e);
    }
  }

  public void stopSelectedBundle() {
    if (!hasSelectedBundle()) {
      return;
    }

    try {
      _selectedBundle.stop();
    } catch (BundleException e) {
      _logger.error("Bundle stop failed: " + e, e);
      sendErrorStatusMessage("Bundle stop failed: " + e);
    }
  }

  public void updateSelectedBundle() {
    if (!hasSelectedBundle()) {
      return;
    }

    try {
      _selectedBundle.update();
    } catch (BundleException e) {
      _logger.error("Bundle update failed: " + e, e);
      sendErrorStatusMessage("Bundle update failed: " + e);
    }
  }

  public void uninstallSelectedBundle() {
    if (!hasSelectedBundle()) {
      return;
    }

    try {
      _selectedBundle.uninstall();
    } catch (BundleException e) {
      _logger.error("Bundle uninstall failed: " + e, e);
      sendErrorStatusMessage("Bundle uninstall failed: " + e);
    }
  }

  public void installNewBundle(File file) {
    URI uri = file.toURI();
    try {
      _bundleContext.installBundle(uri.toString());
    } catch (BundleException ex) {
      _logger.error("Could not install bundle from '" + uri.toString() + "': " + ex, ex);
      sendErrorStatusMessage("Could not install bundle: " + ex);
    }
  }

  private void addBundle(Bundle bundle) {
    _bundles.put(bundle.getBundleId(), bundle);
  }

  private void removeBundle(Bundle bundle) {
    _bundles.remove(bundle.getBundleId());

    if (bundle.equals(_selectedBundle)) {
      _selectedBundle = null;
    }
  }

  public void sendInfoStatusMessage(String message) {
    // create StatusMessage
    StatusMessage statusMessage = new StatusMessage(message, StatusMessage.INFORMATION);

    // handle the request
    handleRequest(new SetStatusMessageRequest(this, statusMessage));
  }

  public void sendErrorStatusMessage(String message) {
    // create StatusMessage
    StatusMessage statusMessage = new StatusMessage(message, StatusMessage.ERROR);

    // handle the request
    handleRequest(new SetStatusMessageRequest(this, statusMessage));

  }
}
