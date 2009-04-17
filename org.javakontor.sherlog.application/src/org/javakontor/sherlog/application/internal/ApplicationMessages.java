package org.javakontor.sherlog.application.internal;

import org.javakontor.sherlog.util.nls.NLS;
import org.javakontor.sherlog.util.nls.NLSMessage;

public class ApplicationMessages {

  @NLSMessage("Sherlog Log Event Viewer")
  public static String applicationWindowTitle;

  // ~~ "File" menu
  @NLSMessage("&File")
  public static String fileMenuTitle;

  @NLSMessage("&Quit")
  public static String quitMenuTitle;

  @NLSMessage("alt F4")
  public static String quitMenuDefaultShortcut;

  // ~~ "Window" menu
  @NLSMessage("&Window")
  public static String windowMenuTitle;

  @NLSMessage("&Tools")
  public static String toolsMenuTitle;

  @NLSMessage("&Arrange")
  public static String arrangeMenuTitle;

  @NLSMessage("&Horizontal")
  public static String horizontalMenuTitle;

  @NLSMessage("&Vertical")
  public static String verticalMenuTitle;

  @NLSMessage("&Cascade")
  public static String cascadeMenuTitle;

  @NLSMessage("&Arrange")
  public static String arrangeWindowsMenuTitle;

  // ~~ "Quit" dialog
  @NLSMessage("Quit")
  public static String quitDialogTitle;

  @NLSMessage("Really quit Sherlog?")
  public static String reallyQuitSherlog;

  // ~~ "Heap panel" -------------------------------------------------------
  @NLSMessage("Run Garbage Collector")
  public static String runGarbageCollector;

  /**
   * Tool tip that is displayed on the heap usage monitor bar
   * 
   */
  @NLSMessage("<html><div style=\"padding:5px;font-size:8px\"><b>Memory usage</b><p>Currently used: %dMB<p>Currently allocated: %dMB<p>Max available: %dMB</div></html>")
  public static String heapUsageToolTip;
  static {
    NLS.initialize(ApplicationMessages.class);
  }
}
