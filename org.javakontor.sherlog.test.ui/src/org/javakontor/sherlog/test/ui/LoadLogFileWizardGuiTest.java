package org.javakontor.sherlog.test.ui;

import java.io.File;

import org.javakontor.sherlog.test.AbstractEclipseArtefactLocatorTest;
import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JMenuBarOperator;
import org.osgi.framework.Bundle;

public class LoadLogFileWizardGuiTest extends AbstractEclipseArtefactLocatorTest {
  
  public void test_A() throws Exception {
//    Bundle bundle = bundleContext.getBundle(2);
//
//    System.err.println(bundle);
//
//    bundle.uninstall();
//
//    assertEquals(bundle.getState(), Bundle.UNINSTALLED);

    ComponentChooser componentChooser = null;
    System.out.println("componentchooser: " + componentChooser);
    
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

    
//    Bundle[] bundles = bundleContext.getBundles();
//    
//    for (Bundle bundle : bundles) {
//      if (bundle.getSymbolicName().startsWith("org.javakontor.sherlog.ui")) {
//        bundle.stop();
//      }
//    }
    
  }
//  
//  protected Manifest getManifest() {
//    // let the testing framework create/load the manifest
//    Manifest mf = super.getManifest();
//    // add Bundle-Classpath:
//    mf.getMainAttributes().putValue(Constants.BUNDLE_CLASSPATH, ".,libs/jemmy.jar");
//    return mf;
//}
  
  protected String[] getTestBundlesNames() {
    return new String[] { "null,org.javakontor.sherlog.core,1.0.0", "null,org.javakontor.sherlog.util,1.0.0",
        "null,org.javakontor.sherlog.core,1.0.0", "null,org.javakontor.sherlog.core.impl,1.0.0",
        "null,jemmy,1.0.0",
        "null,org.eclipse.equinox.ds,1.0.0", "null,org.eclipse.equinox.log,1.0.0",
        "null,org.eclipse.equinox.util,1.0.0", "null,org.eclipse.osgi.services,1.0.0",
        "null,org.lumberjack.application,1.0.0", "null,org.lumberjack.application.mvc,1.0.0",
        "null,org.javakontor.sherlog.jgoodies,1.0.0",
        "null,org.javakontor.sherlog.ui.loadwizard,1.0.0",
        "null,org.javakontor.sherlog.ui.logview,1.0.0",
        "null,org.javakontor.sherlog.ui.filter,1.0.0",
        "null,org.javakontor.sherlog.ui.util,1.0.0",
    };
  }


}
