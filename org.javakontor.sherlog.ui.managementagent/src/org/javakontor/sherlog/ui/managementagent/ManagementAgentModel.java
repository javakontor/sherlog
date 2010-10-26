package org.javakontor.sherlog.ui.managementagent;

import org.javakontor.sherlog.application.mvc.AbstractModel;
import org.javakontor.sherlog.application.mvc.DefaultReasonForChange;
import org.javakontor.sherlog.application.mvc.Model;
import org.javakontor.sherlog.application.request.RequestHandler;
import org.javakontor.sherlog.ui.managementagent.detailview.BundleDetailModel;
import org.javakontor.sherlog.ui.managementagent.tableview.BundleListModel;
import org.javakontor.sherlog.util.Assert;
import org.osgi.framework.BundleContext;

/**
 * <p>
 * The {@link Model} for the {@link LogViewContribution}.
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ManagementAgentModel extends AbstractModel<ManagementAgentModel, DefaultReasonForChange> implements
    RequestHandler {

  private final BundleListModel   _bundleListModel;

  private final BundleDetailModel _bundleDetailModel;

  public ManagementAgentModel(BundleContext bundleContext) {
    Assert.notNull(bundleContext);

    this._bundleListModel = new BundleListModel(bundleContext);
    this._bundleDetailModel = new BundleDetailModel();
  }

  public BundleListModel getBundleListModel() {
    return _bundleListModel;
  }

  public BundleDetailModel getBundleDetailModel() {
    return _bundleDetailModel;
  }
}
