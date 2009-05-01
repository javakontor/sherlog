package org.javakontor.sherlog.application.internal;

import static org.javakontor.sherlog.application.action.MenuConstants.FILE_MENU_ID;
import static org.javakontor.sherlog.application.action.MenuConstants.FILE_MENU_TARGET_ID;
import static org.javakontor.sherlog.application.action.MenuConstants.MENUBAR_ID;

import org.javakontor.sherlog.application.action.AbstractAction;
import org.javakontor.sherlog.application.action.ActionContribution;
import org.javakontor.sherlog.application.action.ActionGroupContribution;
import org.javakontor.sherlog.application.action.DefaultActionContribution;
import org.javakontor.sherlog.application.action.DefaultActionGroupContribution;
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

    final DefaultActionContribution contribution = new DefaultActionContribution();
    contribution.setAction(new QuitAction(applicationWindowComponent));
    contribution.setId(FILE_MENU_ID + ".quit");
    contribution.setTargetActionGroupId(FILE_MENU_TARGET_ID + "(last)");
    contribution.setLabel(ApplicationMessages.quitMenuTitle);
    contribution.setDefaultShortcut(ApplicationMessages.quitMenuDefaultShortcut);

    final FileMenuActionGroup fileActionGroup = new FileMenuActionGroup(contribution);

    this._registration = bundleContext.registerService(ActionGroupContribution.class.getName(), fileActionGroup, null);
  }

  class FileMenuActionGroup extends DefaultActionGroupContribution {

    private final ActionContribution[] _defaultActions;

    public FileMenuActionGroup(final ActionContribution... defaultActions) {
      super(FILE_MENU_ID, MENUBAR_ID + "(first)", ApplicationMessages.fileMenuTitle);
      this._defaultActions = defaultActions;

    }

    @Override
    public ActionContribution[] getStaticActionContributions() {
      return this._defaultActions;
    }
  }

  class QuitAction extends AbstractAction {

    private final ApplicationWindowComponent _applicationWindowComponent;

    public QuitAction(final ApplicationWindowComponent applicationWindowComponent) {
      super();
      this._applicationWindowComponent = applicationWindowComponent;
    }

    public void execute() {
      this._applicationWindowComponent.shutdownApplication();
    }
  }

  public void dispose() {
    this._registration.unregister();
  }
}
