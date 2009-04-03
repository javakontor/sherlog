package org.javakontor.sherlog.test.ui.cases;

import java.io.File;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javakontor.sherlog.test.ui.framework.GuiTestContext;
import org.javakontor.sherlog.test.ui.framework.GuiTestSupport;
import org.javakontor.sherlog.test.ui.handler.ApplicationWindowHandler;

public class AbstractGuiIntegrationTestCases extends Assert {
  protected final Log              _logger = LogFactory.getLog(getClass());

  protected final GuiTestContext   _guiTestContext;

  protected final GuiTestSupport   _guiTestSupport;

  private ApplicationWindowHandler _applicationWindowHandler;

  public AbstractGuiIntegrationTestCases(final GuiTestContext guiTestContext) {
    super();
    this._guiTestContext = guiTestContext;
    this._applicationWindowHandler = new ApplicationWindowHandler(guiTestContext);
    this._guiTestSupport = new GuiTestSupport(guiTestContext);
  }

  public void dispose() throws Exception {
    this._guiTestSupport.dispose();
  }

  /**
   * returns the applicationWindowHandler.
   * 
   * <p>
   * Note: to gain performance, this method returns a cached reference to the application window handler. If for some
   * reasons the application window had been closed an reopened, you'll need to refresh this reference by calling
   * {@link #findApplicationWindow()}
   * 
   * @return the (cached) handler for the application window - never null
   */
  public ApplicationWindowHandler getApplicationWindowHandler() {
    return this._applicationWindowHandler;
  }

  /**
   * Searchs for the ApplicationWindow and (re-)sets applicationWindowHandler
   * 
   * <p>
   * This method can be used to reset the applicationWindowHandler reference that is returned by
   * {@link #getApplicationWindowHandler()} after the application-bundle or it's services have been restarted
   */
  public void findApplicationWindow() {
    this._applicationWindowHandler = new ApplicationWindowHandler(this._guiTestContext);
  }

  protected String getTestLogFile() {
    final File binaryLogFile = new File(this._guiTestContext.getWorkspaceLocation(),
        "org.javakontor.sherlog.domain.impl.test/logs/log_small.bin");
    assertTrue("The binary test-logfile '" + binaryLogFile.getAbsolutePath() + "' must be an existing file",
        binaryLogFile.isFile());
    final String testLogFile = binaryLogFile.getAbsolutePath();
    if (this._logger.isDebugEnabled()) {
      this._logger.debug(String.format("Using test log file '%s", testLogFile));
    }
    return testLogFile;
  }

}
