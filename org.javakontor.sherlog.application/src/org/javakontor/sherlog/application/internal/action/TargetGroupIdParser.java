package org.javakontor.sherlog.application.internal.action;

import static java.lang.String.format;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses a "targetActionGroupId" string with format "rootId/targetActionGroupId(format)"
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class TargetGroupIdParser {

  /**
   * Pattern to match actionRoot/targetGroupId(position) where position is optional
   */
  private static Pattern pattern = Pattern.compile("((.*)/)?([^\\(\\)]*)(\\((.*)\\))?");

  private final String   _actionRoot;

  private final String   _targetGroupId;

  private final String   _position;

  public TargetGroupIdParser(String targetGroupId) {
    Matcher matcher = pattern.matcher(targetGroupId);
    if (!matcher.find()) {
      throw new RuntimeException("Invalid targetGroupId '" + targetGroupId + "'");
    }
    // 2: root (optional)
    // 3: targetActionGroupId (wenn 2 == null, dann ist das root)
    // 5 position (optional)

    String actionRoot = matcher.group(2);
    _targetGroupId = matcher.group(3);
    if (actionRoot == null) {
      _actionRoot = _targetGroupId;
    } else {
      _actionRoot = actionRoot;
    }

    String position = null;
    if (matcher.groupCount() == 5) {
      position = matcher.group(5);
      if (position == null || position.trim().length() < 1) {
        position = null;
      }
    }
    _position = position;

  }

  public String getActionRoot() {
    return _actionRoot;
  }

  public String getTargetGroupId() {
    return _targetGroupId;
  }

  public String getPosition() {
    return _position;
  }

  public static void main(String[] args) {
    /*
     * mainFrameMenuBar/file.recent menubar/file
     * 
     * logViewFrame.popup
     */
    try {
      // actionRoot/targetActionGroupId(before:otherTargetGroupId)

      String[] groups = new String[] { "root/menubar(c:d)", "root(c:d)", "root/menubar", "root" };
      // 2: root (optional)
      // 3: targetActionGroupId (wenn 2 == null, dann ist das root)
      // 5 position (optional)
      for (String targetGroupId : groups) {

        System.out.println("===== " + targetGroupId + " ===== ");
        Matcher matcher = pattern.matcher(targetGroupId);
        if (!matcher.find()) {
          throw new RuntimeException("Invalid targetGroupId '" + targetGroupId + "'");
        }

        int groupCount = matcher.groupCount();
        for (int i = 0; i <= groupCount; i++) {
          String group = matcher.group(i);
          System.out.println(format("%d: '%s'", i, group));
        }
      }
    } catch (Exception ex) {
      System.err.println("Exception caught in main: " + ex);
      ex.printStackTrace();
    }
  }

}
