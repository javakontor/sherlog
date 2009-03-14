package org.javakontor.sherlog.ui.test.internal;

import java.io.File;

import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JMenuBarOperator;
import org.netbeans.jemmy.operators.WindowOperator;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

  public void start(BundleContext context) throws Exception {

    JFrameOperator operator = new JFrameOperator("Sherlog Log Event Viewer");

    System.err.println(" * * * JEMMY : " + operator);
    JMenuBarOperator menuBarOperator = new JMenuBarOperator(operator);
    menuBarOperator.pushMenuNoBlock("File|Load log file...");
    System.err.println(" === SUCHE DIALOGE ===");

    LoadLogFileWizardHandler loadLogFileWizardHandler = new LoadLogFileWizardHandler(operator);
    String testLogFile = new File(System.getProperty("sherlog.workspace"),
        "org.javakontor.sherlog.core.impl.test/logs/log_small.bin").getAbsolutePath();

    loadLogFileWizardHandler.getLogFileChooserViewHandler().enterFileName(testLogFile);
    loadLogFileWizardHandler.getLogFileChooserViewHandler().selectLogEventFlavour("log4j");

    loadLogFileWizardHandler.getOkButtonOperator().clickMouse();
  }

  public static WindowOperator findDialog(String title) {
    return new WindowOperator(WindowOperator.waitWindow(new TracingComponentChooser(
        new ByTitleActiveJDialogComponentChooser(title))));
  }

  public void stop(BundleContext context) throws Exception {
    // TODO Auto-generated method stub

  }

}
