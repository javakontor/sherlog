package org.javakontor.sherlog.ui.managementagent;

import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetListener;

import org.javakontor.sherlog.application.mvc.AbstractController;
import org.javakontor.sherlog.application.request.Request;
import org.javakontor.sherlog.application.request.RequestHandler;
import org.javakontor.sherlog.ui.managementagent.detailview.BundleDetailController;
import org.javakontor.sherlog.ui.managementagent.tableview.BundleListController;
import org.javakontor.sherlog.ui.managementagent.tableview.SetSelectedBundleRequest;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ManagementAgentController extends AbstractController<ManagementAgentModel, ManagementAgentView> {

  private final BundleListController   _bundleListController;

  private final BundleDetailController _bundleDetailController;

  /**
   * <p>
   * </p>
   * 
   * @param model
   * @param view
   * @param successor
   */
  public ManagementAgentController(ManagementAgentModel model, ManagementAgentView view, RequestHandler successor) {
    super(model, view, successor);

    DropTargetListener dropTargetListener = new BundleInstallDropTargetListener(getModel().getBundleListModel());
    
    _bundleListController = new BundleListController(model.getBundleListModel(), view.getBundleListView(), this);
    _bundleListController.addDropTargetListener(dropTargetListener);
    
    _bundleDetailController = new BundleDetailController(model.getBundleDetailModel(), view.getBundleDetailView(), this);
    _bundleDetailController.addDropTargetListener(dropTargetListener);
    
    new DropTarget(getView(), dropTargetListener);
  }

  /**
   * @see org.javakontor.sherlog.application.mvc.AbstractController#canHandleRequest(org.javakontor.sherlog.application.request.Request)
   */
  @Override
  public boolean canHandleRequest(Request request) {
    return (request instanceof SetSelectedBundleRequest) || super.canHandleRequest(request);
  }

  /**
   * @see org.javakontor.sherlog.application.mvc.AbstractController#doHandleRequest(org.javakontor.sherlog.application.request.Request)
   */
  @Override
  public void doHandleRequest(Request request) {
    if (request instanceof SetSelectedBundleRequest) {

      SetSelectedBundleRequest setSelectedBundleRequest = (SetSelectedBundleRequest) request;

      if (setSelectedBundleRequest.hasBundle()) {
        this.getModel().getBundleDetailModel().setBundle(setSelectedBundleRequest.getBundle());
      }
    } else {
      super.doHandleRequest(request);
    }
  }

  public BundleListController getBundleListController() {
    return _bundleListController;
  }

  public BundleDetailController getBundleDetailController() {
    return _bundleDetailController;
  }
}
