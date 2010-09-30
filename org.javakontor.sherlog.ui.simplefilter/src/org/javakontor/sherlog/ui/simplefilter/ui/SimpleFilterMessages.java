package org.javakontor.sherlog.ui.simplefilter.ui;

import org.javakontor.sherlog.util.nls.NLS;
import org.javakontor.sherlog.util.nls.NLSMessage;

public class SimpleFilterMessages {

  @NLSMessage("Level")
  public static String level;

  @NLSMessage("Thread")
  public static String thread;

  @NLSMessage("Category")
  public static String category;

  @NLSMessage("Message")
  public static String message;

  static {
    NLS.initialize(SimpleFilterMessages.class);
  }

}
