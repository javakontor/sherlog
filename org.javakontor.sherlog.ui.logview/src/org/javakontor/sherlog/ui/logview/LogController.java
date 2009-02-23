package org.javakontor.sherlog.ui.logview;

import org.javakontor.sherlog.ui.logview.detailview.LogEventDetailController;
import org.javakontor.sherlog.ui.logview.filterview.LogEventFilterController;
import org.javakontor.sherlog.ui.logview.tableview.LogEventTableController;
import org.javakontor.sherlog.ui.logview.tableview.SetSelectedLogEventsRequest;
import org.lumberjack.application.mvc.AbstractController;
import org.lumberjack.application.request.Request;
import org.lumberjack.application.request.RequestHandler;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class LogController extends AbstractController<LogModel, LogView> {

  private LogEventTableController  _logEventTableController;

  private LogEventDetailController _logEventDetailController;

  private LogEventFilterController _logEventFilterController;

  /**
   * <p>
   * </p>
   * 
   * @param model
   * @param view
   * @param successor
   */
  public LogController(LogModel model, LogView view, RequestHandler successor) {
    super(model, view, successor);

    // create the LogEventTableController
    _logEventTableController = new LogEventTableController(model.getLogEventTableModel(), view.getLogEventTableView(),
        this);

    // create the LogEventDetailController
    _logEventDetailController = new LogEventDetailController(model.getLogEventDetailModel(), view
        .getLogEventDetailView(), this);

    // create the LogEventFilterController
    _logEventFilterController = new LogEventFilterController(model.getLogEventFilterModel(), view
        .getLogEventFilterView(), this);
  }

  /**
   * @see org.lumberjack.application.mvc.AbstractController#canHandleRequest(org.lumberjack.application.request.Request)
   */
  @Override
  public boolean canHandleRequest(Request request) {
    return (request instanceof SetSelectedLogEventsRequest) || super.canHandleRequest(request);
  }

  /**
   * @see org.lumberjack.application.mvc.AbstractController#doHandleRequest(org.lumberjack.application.request.Request)
   */
  @Override
  public void doHandleRequest(Request request) {
    if (request instanceof SetSelectedLogEventsRequest) {

      SetSelectedLogEventsRequest setSelectedLogEventsRequest = (SetSelectedLogEventsRequest) request;

      if (setSelectedLogEventsRequest.getLogEvents().length > 0) {
        this.getModel().getLogEventDetailModel().setLogEvent(setSelectedLogEventsRequest.getLogEvents()[0]);
      }
    } else {
      super.doHandleRequest(request);
    }
  }

  public LogEventTableController getLogEventTableController() {
    return _logEventTableController;
  }

  public LogEventDetailController getLogEventDetailController() {
    return _logEventDetailController;
  }

  public LogEventFilterController getLogEventFilterController() {
    return _logEventFilterController;
  }
}
