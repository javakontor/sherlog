package org.javakontor.sherlog.application.internal;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.Image;
import java.beans.PropertyVetoException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;

import org.javakontor.sherlog.application.contrib.ApplicationStatusBarContribution;
import org.javakontor.sherlog.application.internal.util.Arranger;
import org.javakontor.sherlog.application.internal.util.WallpaperDesktopPane;
import org.javakontor.sherlog.application.internal.window.DialogFrame;
import org.javakontor.sherlog.application.internal.window.ModalDialogFrame;
import org.javakontor.sherlog.application.request.Request;
import org.javakontor.sherlog.application.request.RequestHandler;
import org.javakontor.sherlog.application.request.RequestHandlerImpl;
import org.javakontor.sherlog.application.request.SetStatusMessageRequest;
import org.javakontor.sherlog.application.view.ViewContribution;
import org.javakontor.sherlog.util.Assert;
import org.javakontor.sherlog.util.ui.GuiExecutor;

/**
 * The Sherlog main Application Window.
 */
public class ApplicationWindow extends JFrame implements RequestHandler {
  /**
   * The constant for centering the wallpaper image.
   */
  public static final int               CENTER           = WallpaperDesktopPane.CENTER;

  /**
   * The constant for tiling the wallpaper image.
   */
  public static final int               TILE             = WallpaperDesktopPane.TILE;

  /**
   * The constant for stretching the wallpaper image.
   */
  public static final int               STRETCH          = WallpaperDesktopPane.STRETCH;

  /**
   * 
   */
  private static final long             serialVersionUID = 1L;

  /** the internal used desktop pane * */
  private WallpaperDesktopPane          _desktopPane;

  private ApplicationStatusBar          _statusBar;

  private RequestHandler                _requestHandler;

  /**
   * the map of registered dialogs
   * 
   * <p>
   * Value can either be an DialogFrame (in cases ViewContribution is a non-modal represents dialog) or a ModalDialog in
   * cases ViewContribution is modal
   */
  private Map<ViewContribution, Object> _dialogMap;

  /**
   * Creates an instance of type MainWindow.
   * 
   * @param title
   *          -
   * 
   * @throws HeadlessException
   *           -
   */
  public ApplicationWindow(final String title) throws HeadlessException {
    super(title);

    setUp();
  }

  @Override
  public void dispose() {
    if (this._statusBar != null) {
      this._statusBar.dispose();
    }
    super.dispose();
  }

  public void handleRequest(final Request request) {
    this._requestHandler.handleRequest(request);
  }

  public void setSuccessor(final RequestHandler successor) {
    this._requestHandler.setSuccessor(successor);
  }

  /**
   * Adds the specified dialog to the application window.
   * 
   * @param viewContribution
   *          the dialog to add to the application window.
   */
  public void add(final ViewContribution viewContribution) {
    Assert.notNull("Parameter viewContribution has to be set!", viewContribution);

    if (!this._dialogMap.containsKey(viewContribution)) {
      Object view;
      if (viewContribution.getDescriptor().isModal()) {
        view = openModalFrame(viewContribution);
      } else {
        view = openNonModalFrame(viewContribution);
      }
      this._dialogMap.put(viewContribution, view);
      viewContribution.setSuccessor(this);
    }
  }

  /**
   * Removes the specified dialog form the application window.
   * 
   * @param viewContribution
   *          the dialog to remove from the application window.
   */
  public void remove(final ViewContribution viewContribution) {
    Assert.notNull("Parameter dialog has to be set!", viewContribution);

    if (this._dialogMap.containsKey(viewContribution)) {
      final Object view = this._dialogMap.get(viewContribution);
      if (viewContribution.getDescriptor().isModal()) {
        final ModalDialogFrame modalDialogFrame = (ModalDialogFrame) view;
        modalDialogFrame.dispose();
      } else {
        final DialogFrame dialogFrame = (DialogFrame) view;
        this._desktopPane.remove(dialogFrame);
        dialogFrame.dispose();
      }

      this._dialogMap.remove(viewContribution);

      validate();
      repaint();
    }
  }

  public void addStatusBarContribution(final ApplicationStatusBarContribution contribution) {
    Assert.notNull("Parameter contribution has to be set!", contribution);

    this._statusBar.addContribution(contribution);
  }

  public void removeStatusBarContribution(final ApplicationStatusBarContribution contribution) {
    Assert.notNull("Parameter contribution has to be set!", contribution);

    this._statusBar.removeContribution(contribution);
  }

  /**
   * Sets the specified image as the wallpaper.
   * 
   * @param wallpaper
   *          the image to set as the wallpaper.
   */
  public void setWallpaper(final Image wallpaper) {
    this._desktopPane.setWallpaper(wallpaper);
  }

  /**
   * Sets the layout style.
   * 
   * @param wallpaperLayoutStyle
   *          the layout style.
   */
  public void setWallpaperLayoutStyle(final int wallpaperLayoutStyle) {
    this._desktopPane.setWallpaperLayoutStyle(wallpaperLayoutStyle);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.javakontor.sherlog.application.internal.window.ApplicationWindow#arrange(int)
   */
  public void arrange(final int style) {
    Arranger.tileFrames(style, this._desktopPane);
  }

  /**
   * Set up the main window.
   */
  private void setUp() {

    this._requestHandler = new RequestHandlerImpl() {

      @Override
      public boolean canHandleRequest(final Request request) {
        return request instanceof SetStatusMessageRequest;
      }

      @Override
      public void doHandleRequest(final Request request) {
        if (ApplicationWindow.this._dialogMap.containsKey(request.sender())) {
          GuiExecutor.execute(new Runnable() {
            public void run() {
              final Object view = ApplicationWindow.this._dialogMap.get(request.sender());
              // SetStatusMessageRequest is only allowed for DialogFrames (i.e. non-modal dialogs)
              if (view instanceof DialogFrame) {
                final DialogFrame dialogFrame = (DialogFrame) view;
                dialogFrame.setStatusMessage(((SetStatusMessageRequest) request).getStatusMessage());
              }
            }
          });
        }
      }
    };

    this._dialogMap = new HashMap<ViewContribution, Object>();

    this._desktopPane = new WallpaperDesktopPane();
    // this._desktopPane.setDragMode(JDesktopPane.OUTLINE_DRAG_MODE);

    getContentPane().setLayout(new BorderLayout());
    getContentPane().add(this._desktopPane, BorderLayout.CENTER);
    this._statusBar = new ApplicationStatusBar();
    getContentPane().add(this._statusBar, java.awt.BorderLayout.SOUTH);

    this.getGlassPane().setVisible(true);
    final Image image = new ImageIcon(getClass().getResource("background.jpg")).getImage();

    setWallpaperLayoutStyle(WallpaperDesktopPane.STRETCH);
    setWallpaper(image);

    setJMenuBar(new JMenuBar());

    setSize(new Dimension(800, 600));
    setLocationRelativeTo(null);

  }

  /**
   * Creates and opens a modal frame for the specified viewContribution.
   * 
   * @param viewContribution
   *          the view contribution
   * @return the {@link ModalDialogFrame} that has been opened for the specified ViewContribution
   */
  protected ModalDialogFrame openModalFrame(final ViewContribution viewContribution) {

    // Create a new ModalDialogFrame instance for the ViewContribution
    final ModalDialogFrame modalDialogFrame = new ModalDialogFrame(this, viewContribution);

    // Position the frame and make it visible on swing EDT
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        modalDialogFrame.setLocationRelativeTo(ApplicationWindow.this);
        modalDialogFrame.setVisible(true);
      }
    });

    // return the instance
    return modalDialogFrame;
  }

  /**
   * Creates and opens a non-modal frame for the specified viewContribution
   * 
   * @param viewContribution
   *          the view contribution
   * @return the DialogFrame that has been opened for the specified ViewContribution
   */
  protected DialogFrame openNonModalFrame(final ViewContribution viewContribution) {
    // Create new DialogFrame instance
    final DialogFrame dialogFrame = new DialogFrame(this, viewContribution);

    // add Frame to desktop pane
    this._desktopPane.add(dialogFrame);

    // maximize if requested
    if (viewContribution.getDescriptor().openMaximized()) {
      try {
        dialogFrame.setMaximum(true);
      } catch (final PropertyVetoException e) {
        //
      }
    }

    // make the frame visible on swing EDT
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        dialogFrame.toFront();
        dialogFrame.setVisible(true);
      }
    });

    return dialogFrame;
  }

}
