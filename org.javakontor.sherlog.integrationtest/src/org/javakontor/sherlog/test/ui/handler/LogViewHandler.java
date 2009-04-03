package org.javakontor.sherlog.test.ui.handler;

import org.javakontor.sherlog.test.ui.framework.AbstractViewContributionHandler;
import org.javakontor.sherlog.test.ui.framework.GuiTestContext;
import org.netbeans.jemmy.operators.JInternalFrameOperator;
import org.netbeans.jemmy.operators.WindowOperator;

public class LogViewHandler extends AbstractViewContributionHandler {

  public static final String             LOG_VIEWCONTRIBUTION_NAME = "Log";

  private final JInternalFrameOperator   _logViewWindowOperator;

  private final LogEventTableViewHandler _logEventTableViewHandler;

  public LogViewHandler(final GuiTestContext guiTestContext, final WindowOperator parentWindowOperator) {
    super(guiTestContext);

    this._logViewWindowOperator = new JInternalFrameOperator(parentWindowOperator, LOG_VIEWCONTRIBUTION_NAME);
    this._logEventTableViewHandler = new LogEventTableViewHandler(this._logViewWindowOperator);
  }

  public LogEventTableViewHandler getLogEventTableViewHandler() {
    return this._logEventTableViewHandler;
  }

}
