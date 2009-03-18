package org.javakontor.sherlog.test.ui.cases;

import java.io.File;

import junit.framework.TestCase;

import org.javakontor.sherlog.test.ui.framework.LoadLogFileWizardHandler;
import org.javakontor.sherlog.test.ui.framework.TracingComponentChooser;
import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JMenuBarOperator;

public class LoadLogFileWizardGuiTest extends TestCase {
  
  private File _workspaceLocation;
  
  public LoadLogFileWizardGuiTest(File workspaceLocation) {
    this._workspaceLocation = workspaceLocation; 
  }

  public void test_A() throws Exception {
    ComponentChooser componentChooser = new TracingComponentChooser(null);
    System.out.println("componentchooser: " + componentChooser);
    
    JFrameOperator operator = new JFrameOperator("Sherlog Log Event Viewer");

    System.err.println(" * * * JEMMY : " + operator);
    JMenuBarOperator menuBarOperator = new JMenuBarOperator(operator);
    menuBarOperator.pushMenuNoBlock("File|Load log file...");
    System.err.println(" === SUCHE DIALOGE ===");

    LoadLogFileWizardHandler loadLogFileWizardHandler = new LoadLogFileWizardHandler(operator);
//    File workspaceDir = new File(System.getProperty("sherlog.workspace"));
    File binaryLogFile = new File(_workspaceLocation,
        "org.javakontor.sherlog.core.impl.test/logs/log_small.bin");
    System.err.println("binaryLogFile: " + binaryLogFile.getAbsolutePath());
    assertTrue("The binary test-logfile '" + binaryLogFile.getAbsolutePath() + "' must be an existing file", binaryLogFile.isFile());
    String testLogFile = binaryLogFile.getAbsolutePath();

    loadLogFileWizardHandler.getLogFileChooserViewHandler().enterFileName(testLogFile);
    loadLogFileWizardHandler.getLogFileChooserViewHandler().selectLogEventFlavour("log4j");

    loadLogFileWizardHandler.getOkButtonOperator().clickMouse();
    
  }
}
