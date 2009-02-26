package org.javakontor.sherlog.ui.loadwizard;

import org.javakontor.sherlog.util.nls.NLS;
import org.javakontor.sherlog.util.nls.NLSMessage;

public class LoadLogFileWizardMessages {

  @NLSMessage("Load log file")
  public static String loadLogFile;

  @NLSMessage("No flavour selected")
  public static String noFlavourSelected;

  @NLSMessage("The selected file '%s' is not a valid file")
  public static String selectedFileIsNotAFile;

  @NLSMessage("No file selected")
  public static String noFileSelected;

  @NLSMessage("Could not load log file")
  public static String couldNotLoadLogFile;

  @NLSMessage("control shift L")
  public static String openLogFileWizardShortcut;

  static {
    NLS.initialize(LoadLogFileWizardMessages.class);
  }

}
