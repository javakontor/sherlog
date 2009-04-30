package org.javakontor.sherlog.application.internal;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javakontor.sherlog.application.action.contrib.ActionSet;
import org.javakontor.sherlog.application.action.contrib.ActionSetManager;
import org.javakontor.sherlog.application.menu.MenuConstants;
import org.javakontor.sherlog.application.view.ViewContribution;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;

public class ApplicationWindowComponent {

  private final Log                    _logger     = LogFactory.getLog(getClass());

  private ComponentContext             _componentContext;

  private DefaultApplicationWindow     _mainFrame;

  private ActionSetManager             _actionSetManager;

  public ApplicationWindowMenuBar      _applicationWindowMenuBar;

  // private MenuActionTracker _menuActionTracker;

  private FileMenu                     _fileMenu;

  private WindowMenu                   _windowMenu;

  private LookAndFeelMenu              _lafMenu;

  private final Object                 _lock       = new Object();

  private final List<ServiceReference> _dialogList = new LinkedList<ServiceReference>();

  public BundleContext                 _bundleContext;

  /**
   * @param context
   */
  protected void activate(final ComponentContext context) {
    this._componentContext = context;
    this._bundleContext = this._componentContext.getBundleContext();

    SwingUtilities.invokeLater(new Runnable() {

      public void run() {
        try {
          synchronized (ApplicationWindowComponent.this._lock) {
            ApplicationWindowComponent.this._mainFrame = new DefaultApplicationWindow(
                ApplicationMessages.applicationWindowTitle);
            ApplicationWindowComponent.this._mainFrame.addWindowListener(new ApplicationWindowListener());
            ApplicationWindowComponent.this._mainFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

            setMenuBarActionSet();

            for (final ServiceReference dialogReference : ApplicationWindowComponent.this._dialogList) {
              addDialog(dialogReference);
            }
            // for (ServiceReference actionReference : _actionList) {
            // addAction(actionReference);
            // }

            // ApplicationWindowComponent.this._menuActionTracker = new MenuActionTracker(context.getBundleContext(),
            // MenuConstants.MENUBAR_ID, new MenuBar(ApplicationWindowComponent.this._mainFrame.getJMenuBar()));
            createDefaultMenus(context);
            // ApplicationWindowComponent.this._menuActionTracker.open();

            ApplicationWindowComponent.this._mainFrame.setVisible(true);
          }
        } catch (final Exception e) {
          e.printStackTrace();
        }
      }

    });

    // _actionGroupServiceTracker = new ActionGroupServiceTracker(context.getBundleContext());
    // _actionGroupServiceTracker.open();

  }

  private void setMenuBarActionSet() {
    if (this._mainFrame == null) {
      return;
    }

    if ((this._actionSetManager == null) && (this._applicationWindowMenuBar == null)) {
      // no manager and no menu bar -> nothing to do yet
      return;
    }

    // create a new ApplicationWindowMenuBar for the application window if neccessary
    if (this._applicationWindowMenuBar == null) {
      this._applicationWindowMenuBar = new ApplicationWindowMenuBar(this._mainFrame.getJMenuBar());
    }

    ActionSet actionSet = null;

    // receive actionset that holds the content for the menubar
    if (this._actionSetManager != null) {
      actionSet = this._actionSetManager.getActionSet(MenuConstants.MENUBAR_ID);
    }

    // update menubar
    this._applicationWindowMenuBar.setMenuActionSet(actionSet);
  }

  protected void createDefaultMenus(final ComponentContext context) throws Exception {

    this._fileMenu = new FileMenu(this, context.getBundleContext());
    this._windowMenu = new WindowMenu(context.getBundleContext(), this._mainFrame);
    this._lafMenu = new LookAndFeelMenu(this._mainFrame, context.getBundleContext());
  }

  /**
   * Shuts down the application. Before the shutdown is executed a confirmation dialog is presented to the user to make
   * sure the shutdown is really wanted
   * 
   * <p>
   * <b>Note!</b> This methods not only quits the OSGi framework but also the whole Java VM !
   * </p>
   */
  public void shutdownApplication() {
    if (JOptionPane.showConfirmDialog(this._mainFrame, ApplicationMessages.reallyQuitSherlog,
        ApplicationMessages.quitDialogTitle, JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) {
      return;
    }

    final Bundle systemBundle = this._bundleContext.getBundle(0);
    try {
      systemBundle.stop();
    } catch (final BundleException e) {
      e.printStackTrace();
    }
    System.exit(0);

  }

  protected void deactivate(final ComponentContext context) {
    this._componentContext = null;

    // if (this._menuActionTracker != null) {
    // try {
    // this._menuActionTracker.close();
    // } catch (Exception ex) {
    // this._logger.error("Could not dispose menuActionTracker: " + ex, ex);
    // ex.printStackTrace();
    // } finally {
    // this._menuActionTracker = null;
    // }
    // }

    if (this._mainFrame != null) {
      try {
        this._mainFrame.dispose();
      } catch (final Exception ex) {
        this._logger.error("Could not dispose mainFrame: " + ex, ex);
        ex.printStackTrace();
      }
      this._mainFrame = null;
    }

    if (this._applicationWindowMenuBar != null) {
      try {
        this._applicationWindowMenuBar.dispose();
      } catch (final Exception ex) {
        this._logger.error("Exception while disposing WindowMenu: " + ex, ex);
        ex.printStackTrace();
      }
      this._applicationWindowMenuBar = null;
    }

    if (this._fileMenu != null) {
      this._fileMenu.dispose();
      this._fileMenu = null;
    }

    if (this._windowMenu != null) {
      this._windowMenu.dispose();
      this._windowMenu = null;
    }

    if (this._lafMenu != null) {
      this._lafMenu.dispose();
      this._lafMenu = null;
    }
  }

  protected void setViewContribution(final ServiceReference dialogReference) {

    synchronized (this._lock) {
      if (this._mainFrame != null) {
        addDialog(dialogReference);
      } else {
        this._dialogList.add(dialogReference);
      }
    }
  }

  /**
   * @param logEventProvider
   */
  protected void unsetViewContribution(final ServiceReference dialogReference) {
    synchronized (this._lock) {
      if (this._mainFrame != null) {
        removeDialog(dialogReference);
      } else {
        this._dialogList.remove(dialogReference);
      }
    }
  }

  public void bindActionSetManager(final ActionSetManager actionSetManager) {
    this._actionSetManager = actionSetManager;
    setMenuBarActionSet();
  }

  public void unbindActionSetManager(final ActionSetManager actionSetManager) {
    this._actionSetManager = null;
    setMenuBarActionSet();
  }

  private void addDialog(final ServiceReference dialogReference) {
    final ViewContribution dialog = (ViewContribution) this._componentContext.locateService("dialogs", dialogReference);
    this._mainFrame.add(dialog);
  }

  private void removeDialog(final ServiceReference dialogReference) {
    final ViewContribution dialog = (ViewContribution) this._componentContext.locateService("dialogs", dialogReference);
    if (dialog != null) {
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          if (ApplicationWindowComponent.this._mainFrame != null) {
            ApplicationWindowComponent.this._mainFrame.remove(dialog);
          }

        };
      });
    }

  }

  /**
   * A WindowListener that shuts down the application (OSGi framework and Java VM) when the window is closing
   */
  class ApplicationWindowListener extends WindowAdapter {
    @Override
    public void windowClosing(final WindowEvent e) {
      shutdownApplication();
    }
  }
}
