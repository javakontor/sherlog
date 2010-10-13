package org.javakontor.sherlog.ui.timefilter;

import org.javakontor.sherlog.util.nls.NLS;
import org.javakontor.sherlog.util.nls.NLSMessage;

public class TimeFilterMessages {

  @NLSMessage("Filter all after this event")
  public static String timeFilterAfter;

  @NLSMessage("Filter all before this event")
  public static String timeFilterBefore;

  static {
    NLS.initialize(TimeFilterMessages.class);
  }

}
