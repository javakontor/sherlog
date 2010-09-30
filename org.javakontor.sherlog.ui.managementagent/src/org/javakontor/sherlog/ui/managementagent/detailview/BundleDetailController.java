package org.javakontor.sherlog.ui.managementagent.detailview;

import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetListener;

import org.javakontor.sherlog.application.mvc.AbstractController;
import org.javakontor.sherlog.application.mvc.DefaultReasonForChange;
import org.javakontor.sherlog.application.request.RequestHandler;

/**
 * <p>
 * The controller for the bundle detail view. * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BundleDetailController extends AbstractController<BundleDetailModel, BundleDetailView, DefaultReasonForChange> {

  /**
   * <p>
   * Creates a new instance of type {@link BundleDetailController}.
   * </p>
   *
   * @param model
   *          the model
   * @param view
   *          the view
   * @param successor
   *          the successor in the chain of responsibility
   */
  public BundleDetailController(BundleDetailModel model, BundleDetailView view, RequestHandler successor) {
    super(model, view, successor);
  }

  public void addDropTargetListener(DropTargetListener dropTargetListener) {
    new DropTarget(getView().getDetailsPane(), dropTargetListener);
  }
}
