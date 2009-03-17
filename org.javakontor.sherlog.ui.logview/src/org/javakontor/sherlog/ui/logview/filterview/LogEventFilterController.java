package org.javakontor.sherlog.ui.logview.filterview;

import org.javakontor.sherlog.application.mvc.AbstractController;
import org.javakontor.sherlog.application.request.RequestHandler;

public class LogEventFilterController extends AbstractController<LogEventFilterModel, LogEventFilterView> {

  public LogEventFilterController(LogEventFilterModel model, LogEventFilterView view, RequestHandler successor) {
    super(model, view, successor);
  }

  public LogEventFilterController(LogEventFilterModel model, LogEventFilterView view) {
    super(model, view);
  }
}
