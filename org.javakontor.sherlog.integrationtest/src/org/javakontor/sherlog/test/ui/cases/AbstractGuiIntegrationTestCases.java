package org.javakontor.sherlog.test.ui.cases;

import java.io.File;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javakontor.sherlog.test.ui.framework.GuiTestContext;
import org.javakontor.sherlog.test.ui.framework.GuiTestSupport;
import org.javakontor.sherlog.test.ui.handler.ApplicationWindowHandler;

public class AbstractGuiIntegrationTestCases extends Assert {
  protected final Log            _logger = LogFactory.getLog(getClass());

  protected final GuiTestContext _guiTestContext;

  protected final GuiTestSupport _guiTestSupport;

  ApplicationWindowHandler       _applicationWindowHandler;

  public AbstractGuiIntegrationTestCases(GuiTestContext guiTestContext) {
    super();
    _guiTestContext = guiTestContext;
    _applicationWindowHandler = new ApplicationWindowHandler(guiTestContext);
    _guiTestSupport = new GuiTestSupport(guiTestContext);
  }

  protected String getTestLogFile() {
    File binaryLogFile = new File(_guiTestContext.getWorkspaceLocation(),
        "org.javakontor.sherlog.domain.impl.test/logs/log_small.bin");
    assertTrue("The binary test-logfile '" + binaryLogFile.getAbsolutePath() + "' must be an existing file",
        binaryLogFile.isFile());
    String testLogFile = binaryLogFile.getAbsolutePath();
    if (_logger.isDebugEnabled()) {
      _logger.debug(String.format("Using test log file '%s", testLogFile));
    }
    return testLogFile;
  }

}
