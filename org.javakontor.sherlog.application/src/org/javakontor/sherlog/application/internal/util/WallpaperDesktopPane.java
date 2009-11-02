package org.javakontor.sherlog.application.internal.util;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JDesktopPane;

/**
 * Implements a desktop pane with a wallpaper image.
 */
public class WallpaperDesktopPane extends JDesktopPane {

  /**
   * 
   */
  private static final long serialVersionUID     = 1L;

  /**
   * The constant for centering the wallpaper image.
   */
  public static final int   CENTER               = 1;

  /**
   * The constant for tiling the wallpaper image.
   */
  public static final int   TILE                 = 2;

  /**
   * The constant for stretching the wallpaper image.
   */
  public static final int   STRETCH              = 3;

  /**
   * The wallpaper image.
   */
  private Image             wallpaper;

  /**
   * The layout style of the wallpaper image.
   */
  private int               wallpaperLayoutStyle = CENTER;

  /**
   * Creates an instance of type DesktopPane.
   */
  public WallpaperDesktopPane() {
    super.setOpaque(false);
  }

  /**
   * Sets the wallpaper.
   */
  public void setWallpaper(Image wallpaper) {
    this.wallpaper = wallpaper;
    repaint();
  }

  /**
   * Returns the current wallpaper image.
   */
  public Image getWallpaper() {
    return wallpaper;
  }

  /**
   * Sets the wallpaper layout style. Possible values are {@link #CENTER}, {@link #STRETCH} and {@link #TILE}.
   */
  public void setWallpaperLayoutStyle(int wallpaperLayoutStyle) {
    switch (wallpaperLayoutStyle) {
    case CENTER:
    case TILE:
    case STRETCH:
      break;

    default:
      throw new IllegalArgumentException("Illegal wallpaper layout style: " + wallpaperLayoutStyle);
    }

    this.wallpaperLayoutStyle = wallpaperLayoutStyle;
    repaint();
  }

  /**
   * Returns the current wallpaper layout style.
   */
  public int getWallpaperLayoutStyle() {
    return wallpaperLayoutStyle;
  }

  /**
   * Paints each of the components in this container.
   * 
   * @param g
   *          the graphics context.
   * @see java.awt.Component#paint
   * @see java.awt.Component#paintAll
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);

    Dimension size = this.getSize();

    if (wallpaper != null) {
      if (wallpaperLayoutStyle == STRETCH) {
        g.drawImage(wallpaper, 0, 0, size.width, size.height, this);
      } else if (wallpaperLayoutStyle == CENTER) {
        int imageWidth = wallpaper.getWidth(this);
        int imageHeight = wallpaper.getHeight(this);
        int x = (size.width - imageWidth) / 2;
        int y = (size.height - imageHeight) / 2;
        g.drawImage(wallpaper, x, y, this);
      } else if (wallpaperLayoutStyle == TILE) {
        int imageWidth = wallpaper.getWidth(this);
        int imageHeight = wallpaper.getHeight(this);

        for (int x = 0; x < size.width; x += imageWidth) {
          for (int y = 0; y < size.height; y += imageHeight) {
            g.drawImage(wallpaper, x, y, this);
          }
        }
      }
    }

    // //super.paintBorder(g);
    // super.paintComponents(g);
    // super.paintChildren(g);
  }
}
