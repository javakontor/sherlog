package org.javakontor.sherlog.application.internal.util;

import java.awt.Dimension;
import java.beans.PropertyVetoException;
import java.util.Vector;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

public class Arranger {

  public static void tileFrames(int style, JDesktopPane desktopPane) {
    Dimension deskDim = desktopPane.getSize();
    int deskWidth = deskDim.width;
    int deskHeight = deskDim.height;
    JInternalFrame frames[] = desktopPane.getAllFrames();
    int frameCount = frames.length;
    int frameWidth = 0;
    int frameHeight = 0;
    int xpos = 0;
    int ypos = 0;
    double scale = 0.59999999999999998D;
    int spacer = 30;
    int frameCounter = 0;
    Vector<JInternalFrame> frameVec = new Vector<JInternalFrame>(1, 1);
    boolean areIcons = false;
    for (int i = 0; i < frameCount; i++) {
      if (!frames[i].isResizable() && frames[i].isMaximum())
        try {
          frames[i].setMaximum(false);
        } catch (PropertyVetoException propertyvetoexception) {
        }
      if (frames[i].isVisible() && !frames[i].isIcon() && frames[i].isResizable()) {
        frameVec.addElement(frames[i]);
        frameCounter++;
      } else if (frames[i].isIcon())
        areIcons = true;
    }

    if (areIcons)
      deskHeight -= 50;
    switch (style) {
    default:
      break;

    case 1: // '\001'
      for (int i = 0; i < frameCounter; i++) {
        JInternalFrame temp = frameVec.elementAt(i);
        frameWidth = deskWidth;
        frameHeight = deskHeight / frameCounter;
        temp.reshape(xpos, ypos, frameWidth, frameHeight);
        ypos += frameHeight;
        temp.moveToFront();
      }

      break;

    case 2: // '\002'
      for (int i = 0; i < frameCounter; i++) {
        JInternalFrame temp = frameVec.elementAt(i);
        frameWidth = deskWidth / frameCounter;
        frameHeight = deskHeight;
        if (temp.isResizable())
          temp.reshape(xpos, ypos, frameWidth, frameHeight);
        else
          temp.setLocation(xpos, ypos);
        xpos += frameWidth;
        temp.moveToFront();
      }

      break;

    case 3: // '\003'
      for (int i = 0; i < frameCounter; i++) {
        JInternalFrame temp = frameVec.elementAt(i);
        frameWidth = (int) (deskWidth * scale);
        frameHeight = (int) (deskHeight * scale);
        if (temp.isResizable())
          temp.reshape(xpos, ypos, frameWidth, frameHeight);
        else
          temp.setLocation(xpos, ypos);
        temp.moveToFront();
        xpos += spacer;
        ypos += spacer;
        if (xpos + frameWidth > deskWidth || ypos + frameHeight > deskHeight - 50) {
          xpos = 0;
          ypos = 0;
        }
      }

      break;

    case 4: // '\004'
      int row = (new Long(Math.round(Math.sqrt((new Integer(frameCounter)).doubleValue())))).intValue();
      if (row == 0)
        break;
      int col = frameCounter / row;
      if (col == 0)
        break;
      int rem = frameCounter % row;
      int rowCount = 1;
      frameWidth = deskWidth / col;
      frameHeight = deskHeight / row;
      for (int i = 0; i < frameCounter; i++) {
        JInternalFrame temp = frameVec.elementAt(i);
        if (rowCount <= row - rem) {
          if (temp.isResizable())
            temp.reshape(xpos, ypos, frameWidth, frameHeight);
          else
            temp.setLocation(xpos, ypos);
          if (xpos + 10 < deskWidth - frameWidth) {
            xpos += frameWidth;
          } else {
            ypos += frameHeight;
            xpos = 0;
            rowCount++;
          }
        } else {
          frameWidth = deskWidth / (col + 1);
          if (temp.isResizable())
            temp.reshape(xpos, ypos, frameWidth, frameHeight);
          else
            temp.setLocation(xpos, ypos);
          if (xpos + 10 < deskWidth - frameWidth) {
            xpos += frameWidth;
          } else {
            ypos += frameHeight;
            xpos = 0;
          }
        }
      }

      break;
    }
  }

  public static final int HORIZONTAL = 1;

  public static final int VERTICAL   = 2;

  public static final int CASCADE    = 3;

  public static final int ARRANGE    = 4;
}
