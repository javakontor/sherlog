package org.javakontor.sherlog.ui.managementagent;

import org.javakontor.sherlog.util.nls.NLS;
import org.javakontor.sherlog.util.nls.NLSMessage;

public class BundleListMessages {

  // ~~~ Menu items
  @NLSMessage("&Management Agent")
  public static String bundleListMenuTitle;

  @NLSMessage("Management Agent")
  public static String bundleListWindowTitle;

  @NLSMessage("control shift M")
  public static String bundleListMenuDefaultShortcut;

  // ~~ BundleListTable column names
  @NLSMessage("Bundle-Id")
  public static String bundleIdColumnName;

  @NLSMessage("Symbolic name")
  public static String symbolicNameColumnName;

  @NLSMessage("Version")
  public static String versionColumnName;

  @NLSMessage("State")
  public static String stateColumnName;

  @NLSMessage("Location")
  public static String locationColumnName;

  // ~~ Context Menu items
  @NLSMessage("Start Bundle")
  public static String startBundleCtxMenuTitle;

  @NLSMessage("Stop Bundle")
  public static String stopBundleCtxMenuTitle;

  @NLSMessage("Update Bundle")
  public static String updateBundleCtxMenuTitle;

  @NLSMessage("Uninstall Bundle")
  public static String uninstallBundleCtxMenuTitle;

  @NLSMessage("Copy Location to Clipboard")
  public static String copyBundleLocationCtxMenuTitle;

  // ~~~ StatusBar
  @NLSMessage("%s (%d services registered, %d services in use)")
  public static String defaultStatusMessage;

  static {
    NLS.initialize(BundleListMessages.class);
  }

}
