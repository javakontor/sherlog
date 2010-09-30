package org.javakontor.sherlog.ui.colorfilter;

import org.javakontor.sherlog.util.nls.NLS;
import org.javakontor.sherlog.util.nls.NLSMessage;

public class ColorFilterMessages {

  @NLSMessage("&Mark")
  public static String markCtxMenuTitle;

  @NLSMessage("&Filter")
  public static String filterCtxMenuTitle;

  @NLSMessage("&Filter by %s")
  public static String filterByCtxMenuTitle;

  @NLSMessage("&Mark with %s")
  public static String markWithCtxMenuTitle;

  @NLSMessage("Remove &color marker")
  public static String removeColorMarker;

  @NLSMessage("&red")
  public static String red;

  @NLSMessage("&gray")
  public static String gray;

  @NLSMessage("&blue")
  public static String blue;

  static {
    NLS.initialize(ColorFilterMessages.class);
  }

}
