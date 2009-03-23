package org.javakontor.sherlog.application.internal;

import static org.javakontor.sherlog.application.menu.MenuConstants.*;

import org.javakontor.sherlog.application.action.Action;
import org.javakontor.sherlog.application.action.StaticActionProvider;
import org.javakontor.sherlog.application.action.impl.AbstractAction;
import org.javakontor.sherlog.application.action.impl.ActionGroupElementServiceHelper;
import org.javakontor.sherlog.application.action.impl.DefaultActionGroup;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

/**
 * Represents the initial File menu.
 * 
 * <p>
 * Clients can contribute actions to the file menu using <tt>sherlog.menu.file</tt> as targetGroupId
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class FileMenu {

  private final ServiceRegistration _registration;

  public FileMenu(final ApplicationWindowComponent applicationWindowComponent, final BundleContext bundleContext) {
    final QuitAction quitAction = new QuitAction(applicationWindowComponent);
    final FileMenuActionGroup fileActionGroup = new FileMenuActionGroup(quitAction);
    this._registration = ActionGroupElementServiceHelper.registerActionGroup(bundleContext, fileActionGroup);
  }

  class FileMenuActionGroup extends DefaultActionGroup implements StaticActionProvider {

    private final Action[] _defaultActions;

    public FileMenuActionGroup(final Action... defaultActions) {
      super(FILE_MENU_ID, MENUBAR_ID + "(first)", ApplicationMessages.fileMenuTitle);
      this._defaultActions = defaultActions;

    }

    public Action[] getActions() {
      return this._defaultActions;
    }

    public boolean isFinal() {
      return false;
    }

  }

  class QuitAction extends AbstractAction {

    private final ApplicationWindowComponent _applicationWindowComponent;

    public QuitAction(final ApplicationWindowComponent applicationWindowComponent) {
      super(FILE_MENU_ID + ".quit", FILE_MENU_TARGET_ID + "(last)", ApplicationMessages.quitMenuTitle);
      this._applicationWindowComponent = applicationWindowComponent;
    }

    public void execute() {
      this._applicationWindowComponent.shutdownApplication();
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.javakontor.sherlog.application.action.AbstractAction#getDefaultShortcut()
     */
    @Override
    public String getDefaultShortcut() {
      return ApplicationMessages.quitMenuDefaultShortcut;
    }

  }

  public void dispose() {
    this._registration.unregister();
  }
}
