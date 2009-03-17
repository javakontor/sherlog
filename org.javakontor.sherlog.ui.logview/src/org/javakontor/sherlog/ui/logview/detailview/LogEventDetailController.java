package org.javakontor.sherlog.ui.logview.detailview;

import org.javakontor.sherlog.application.mvc.AbstractController;
import org.javakontor.sherlog.application.request.RequestHandler;

/**
 * <p>
 * The controller for the log event detail view.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class LogEventDetailController extends AbstractController<LogEventDetailModel, LogEventDetailView> {

  /**
   * <p>
   * Creates a new instance of type {@link LogEventDetailController}.
   * </p>
   * 
   * @param model
   *          the model
   * @param view
   *          the view
   * @param successor
   *          the successor in the chain of responsibility
   */
  public LogEventDetailController(LogEventDetailModel model, LogEventDetailView view, RequestHandler successor) {
    super(model, view, successor);
  }
}
