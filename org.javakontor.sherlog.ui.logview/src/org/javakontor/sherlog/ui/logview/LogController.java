package org.javakontor.sherlog.ui.logview;

import java.awt.Container;
import java.awt.event.ContainerEvent;
import java.awt.event.ContainerListener;

import javax.swing.JSplitPane;

import org.javakontor.sherlog.application.mvc.AbstractController;
import org.javakontor.sherlog.application.mvc.DefaultReasonForChange;
import org.javakontor.sherlog.application.request.Request;
import org.javakontor.sherlog.application.request.RequestHandler;
import org.javakontor.sherlog.ui.logview.detailview.LogEventDetailController;
import org.javakontor.sherlog.ui.logview.filterview.LogEventFilterController;
import org.javakontor.sherlog.ui.logview.tableview.LogEventTableController;
import org.javakontor.sherlog.ui.logview.tableview.SetSelectedLogEventsRequest;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class LogController extends AbstractController<LogModel, LogView, DefaultReasonForChange> {

  private final LogEventTableController  _logEventTableController;

  private final LogEventDetailController _logEventDetailController;

  private final LogEventFilterController _logEventFilterController;

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

    // Add a Listener to the LogEventFilterView that allows us to reset the size of
    // the horizontal splitpane to make sure, the splitpane is wide enough to
    // show the new filter configuration panels
    view.getLogEventFilterView().addContainerListener(new LogEventFilterContainerListener());
  }

  /**
   * @see org.javakontor.sherlog.application.mvc.AbstractController#canHandleRequest(org.javakontor.sherlog.application.request.Request)
   */
  @Override
  public boolean canHandleRequest(Request request) {
    return (request instanceof SetSelectedLogEventsRequest) || super.canHandleRequest(request);
  }

  /**
   * @see org.javakontor.sherlog.application.mvc.AbstractController#doHandleRequest(org.javakontor.sherlog.application.request.Request)
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

  /**
   * A {@link ContainerListener} that resets the {@link LogView}'s horizontal {@link JSplitPane} to it's preferred size
   * when a component has been added to the observed {@link Container}
   * 
   */
  class LogEventFilterContainerListener implements ContainerListener {

    public void componentAdded(ContainerEvent e) {
      getView().resetHorizontalSplitPane();
    }

    public void componentRemoved(ContainerEvent e) {
      // getView().resetHorizontalSplitPane();
    }
  }
}
