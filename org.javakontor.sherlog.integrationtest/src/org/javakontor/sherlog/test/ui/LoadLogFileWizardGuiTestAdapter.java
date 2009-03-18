package org.javakontor.sherlog.test.ui;

import java.util.jar.Manifest;

import org.javakontor.sherlog.test.ui.cases.LoadLogFileWizardGuiTest;
import org.springframework.osgi.test.internal.AbstractEclipseArtefactLocatorTest;

public class LoadLogFileWizardGuiTestAdapter extends AbstractEclipseArtefactLocatorTest {
  
  
  
  public void test_A() throws Exception {
    LoadLogFileWizardGuiTest test = new LoadLogFileWizardGuiTest(getWorkspaceLocation());
    test.test_A();
  }
  
  
//  
//  protected Manifest getManifest() {
//    // let the testing framework create/load the manifest
//    Manifest mf = super.getManifest();
//    // add Bundle-Classpath:
//    mf.getMainAttributes().putValue(Constants.BUNDLE_CLASSPATH, ".,libs/jemmy.jar");
//    return mf;
//}
//  
//  protected boolean createManifestOnlyFromTestClass() {
//    return false;
//  }
  

  
  @Override
  protected Manifest getManifest() {
    
    Manifest manifest = super.getManifest();
    
    System.err.println(" * * *  MANIFEST: " + manifest.getMainAttributes().getValue("Import-Package"));
    
    return manifest;
    
  }


  protected String[] getTestBundlesNames() {
    return new String[] { "null,org.javakontor.sherlog.core,1.0.0", "null,org.javakontor.sherlog.util,1.0.0",
        "null,org.javakontor.sherlog.core,1.0.0", "null,org.javakontor.sherlog.core.impl,1.0.0",
        "null,org.javakontor.org.netbeans.jemmy,0.0.1",
        "null,org.eclipse.equinox.ds,1.0.0", "null,org.eclipse.equinox.log,1.0.0",
        "null,org.eclipse.equinox.util,1.0.0", "null,org.eclipse.osgi.services,1.0.0",
        "null,org.lumberjack.application,1.0.0", "null,org.lumberjack.application.mvc,1.0.0",
        "null,org.javakontor.com.jgoodies.forms,1.2.1",
        "null,org.javakontor.sherlog.ui.loadwizard,1.0.0",
        "null,org.javakontor.sherlog.ui.logview,1.0.0",
        "null,org.javakontor.sherlog.ui.filter,1.0.0",
        "null,org.javakontor.sherlog.ui.util,1.0.0",
        "null,org.javakontor.sherlog.core.logeventflavour.l4jbinary,1.0.0"
    };
  }



  @Override
  protected String getManifestLocation() {
    return "file:D:/lumberjack/workspace/org.javakontor.sherlog.integrationtest/META-INF/MANIFEST.MF";
  }
  
  


}
