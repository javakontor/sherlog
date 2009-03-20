package org.javakontor.sherlog.test.ui.handler;

import org.javakontor.sherlog.test.ui.framework.AbstractViewContributionHandler;
import org.javakontor.sherlog.test.ui.framework.GuiTestContext;
import org.netbeans.jemmy.operators.JInternalFrameOperator;
import org.netbeans.jemmy.operators.WindowOperator;

public class LogViewHandler extends AbstractViewContributionHandler {

  private final JInternalFrameOperator   _logViewWindowOperator;

  private final LogEventTableViewHandler _logEventTableViewHandler;

  public LogViewHandler(GuiTestContext guiTestContext, WindowOperator parentWindowOperator) {
    super(guiTestContext);

    this._logViewWindowOperator = new JInternalFrameOperator(parentWindowOperator, "Log");
    this._logEventTableViewHandler = new LogEventTableViewHandler(this._logViewWindowOperator);
  }

  public LogEventTableViewHandler getLogEventTableViewHandler() {
    return _logEventTableViewHandler;
  }

}
