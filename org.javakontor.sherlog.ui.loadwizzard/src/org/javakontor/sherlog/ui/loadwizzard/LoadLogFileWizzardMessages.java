package org.javakontor.sherlog.ui.loadwizzard;

import org.javakontor.sherlog.util.nls.NLS;
import org.javakontor.sherlog.util.nls.NLSMessage;

public class LoadLogFileWizzardMessages {

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

  static {
    NLS.initialize(LoadLogFileWizzardMessages.class);
  }

}
