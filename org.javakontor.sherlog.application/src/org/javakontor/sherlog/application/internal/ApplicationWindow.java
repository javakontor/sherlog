package org.javakontor.sherlog.application.internal;

import java.awt.Image;

import org.javakontor.sherlog.application.internal.util.WallpaperDesktopPane;
import org.javakontor.sherlog.application.view.ViewContribution;

/**
 * Implements the application window. The application window is able to display dialogs. The application window is also
 * able to display a background image.
 * 
 * @author Gerd Wütherich
 */
public interface ApplicationWindow {
  /**
   * The constant for centering the wallpaper image.
   */
  public static final int CENTER  = WallpaperDesktopPane.CENTER;

  /**
   * The constant for tiling the wallpaper image.
   */
  public static final int TILE    = WallpaperDesktopPane.TILE;

  /**
   * The constant for stretching the wallpaper image.
   */
  public static final int STRETCH = WallpaperDesktopPane.STRETCH;

  /**
   * Adds the specified dialog to the application window.
   * 
   * @param dialog
   *          the dialog to add to the application window.
   */
  public void add(ViewContribution dialog);

  /**
   * (Re-)arragnes all internal frame currently visible
   * 
   * @param style
   */
  public void arrange(int style);

  /**
   * Removes the specified dialog from the application window.
   * 
   * @param dialog
   *          the dialog to remove from the application window.
   */
  public void remove(ViewContribution dialog);

  /**
   * Sets the specified image as the background wallpaper.
   * 
   * @param wallpaper
   *          the image to set as the wallpaper.
   */
  public void setWallpaper(Image wallpaper);

  /**
   * Sets the wallpaper layout style.
   * 
   * @param wallpaperLayoutStyle
   *          the wallpaper layout style.
   */
  public void setWallpaperLayoutStyle(int wallpaperLayoutStyle);

}
