package org.javakontor.sherlog.application.whiteboard;

import org.javakontor.sherlog.application.action.ActionContribution;
import org.javakontor.sherlog.application.action.ActionGroupContribution;
import org.javakontor.sherlog.application.action.contrib.ActionSetManager;
import org.javakontor.sherlog.util.servicemanager.DefaultServiceManager;
import org.javakontor.sherlog.util.servicemanager.ServiceManagerEvent;
import org.javakontor.sherlog.util.servicemanager.ServiceManagerListener;

/**
 * <p>
 * Implements a 'whiteboard component'. The whiteboard component is responsible for tracking and registering all
 * {@link ActionContribution Actions} and {@link ActionGroupContribution ActionGroups} that are registered with the OSGi
 * service registry.
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class WhiteboardComponent {

  /** the service manager for actions */
  private DefaultServiceManager<ActionContribution>       _actionServiceManager              = null;

  /** the service manager listener for actions */
  private ServiceManagerListener<ActionContribution>      _actionServiceManagerListener      = null;

  /** the service manager for action groups */
  private DefaultServiceManager<ActionGroupContribution>  _actionGroupServiceManager         = null;

  /** the service manager listener for action groups */
  private ServiceManagerListener<ActionGroupContribution> _actionGroupServiceManagerListener = null;

  /** the action set manager */
  private ActionSetManager                                _actionSetManager                  = null;

  /**
   * <p>
   * Creates a new instance of type {@link WhiteboardComponent}.
   * </p>
   */
  public WhiteboardComponent() {

    // create the ServiceManager
    _actionServiceManager = new DefaultServiceManager<ActionContribution>();
    _actionGroupServiceManager = new DefaultServiceManager<ActionGroupContribution>();

    // create the ServiceManagerListener
    _actionServiceManagerListener = new ServiceManagerListener<ActionContribution>() {
      public void serviceAdded(ServiceManagerEvent<ActionContribution> event) {
        ActionContribution con = event.getService();

        _actionSetManager.addAction(con.getId(), con.getTargetActionGroupId(), con.getLabel(),
            con.getDefaultShortcut(), con.getAction());
      }

      public void serviceRemoved(ServiceManagerEvent<ActionContribution> event) {
        _actionSetManager.removeAction(event.getService());
      }
    };

    // remove the ServiceManagerListener
    _actionGroupServiceManagerListener = new ServiceManagerListener<ActionGroupContribution>() {
      public void serviceRemoved(ServiceManagerEvent<ActionGroupContribution> event) {
        _actionSetManager.removeActionGroup(event.getService());
      }

      public void serviceAdded(ServiceManagerEvent<ActionGroupContribution> event) {
        _actionSetManager.addActionGroup(event.getService());
      }
    };
  }

  /**
   * <p>
   * Called if an {@link ActionSetManager} is added.
   * </p>
   *
   * @param actionSetManager
   *          the added {@link ActionSetManager}
   */
  public void addActionSetManager(ActionSetManager actionSetManager) {
    // set the manager
    _actionSetManager = actionSetManager;

    // add the ServiceManagerListeners
    _actionServiceManager.addServiceManagerListener(_actionServiceManagerListener);
    _actionGroupServiceManager.addServiceManagerListener(_actionGroupServiceManagerListener);
  }

  /**
   * <p>
   * </p>
   *
   * @param actionSetManager
   */
  public void removeActionSetManager(ActionSetManager actionSetManager) {
    // remove the ServiceManagerListener
    _actionServiceManager.removeServiceManagerListener(_actionServiceManagerListener);
    _actionGroupServiceManager.removeServiceManagerListener(_actionGroupServiceManagerListener);

    // unset the manager
    _actionSetManager = null;
  }

  /**
   * <p>
   * Called if an {@link ActionContribution} is added.
   * </p>
   *
   * @param action
   *          the added {@link ActionContribution}
   */
  public void addActionContribution(ActionContribution action) {
    _actionServiceManager.bindService(action);
  }

  /**
   * <p>
   * Called if an {@link ActionContribution} is removed.
   * </p>
   *
   * @param action
   *          the removed {@link ActionContribution}
   */
  public void removeActionContribution(ActionContribution action) {
    _actionServiceManager.unbindService(action);
  }

  /**
   * <p>
   * Called if an {@link ActionGroupContribution} is added.
   * </p>
   *
   * @param actionGroup
   *          the added {@link ActionGroupContribution}
   */
  public void addActionGroupContribution(ActionGroupContribution actionGroup) {
    _actionGroupServiceManager.bindService(actionGroup);
  }

  /**
   * <p>
   * Called if an {@link ActionGroupContribution} is removed.
   * </p>
   *
   * @param actionGroup
   *          the removed {@link ActionGroupContribution}
   */
  public void removeActionGroupContribution(ActionGroupContribution actionGroup) {
    _actionGroupServiceManager.unbindService(actionGroup);
  }
}
