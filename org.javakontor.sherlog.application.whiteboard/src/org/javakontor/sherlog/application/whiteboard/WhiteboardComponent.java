package org.javakontor.sherlog.application.whiteboard;

import org.javakontor.sherlog.application.action.Action;
import org.javakontor.sherlog.application.action.ActionGroup;
import org.javakontor.sherlog.application.action.ActionSetManager;
import org.javakontor.sherlog.util.servicemanager.DefaultServiceManager;
import org.javakontor.sherlog.util.servicemanager.ServiceManagerEvent;
import org.javakontor.sherlog.util.servicemanager.ServiceManagerListener;

/**
 * <p>
 * Implements a 'whiteboard component'. The whiteboard component is responsible for tracking and registering all
 * {@link Action Actions} and {@link ActionGroup ActionGroups} that are registered with the OSGi service registry.
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class WhiteboardComponent {

  /** the service manager for actions */
  private DefaultServiceManager<Action>       _actionServiceManager              = null;

  /** the service manager listener for actions */
  private ServiceManagerListener<Action>      _actionServiceManagerListener      = null;

  /** the service manager for action groups */
  private DefaultServiceManager<ActionGroup>  _actionGroupServiceManager         = null;

  /** the service manager listener for action groups */
  private ServiceManagerListener<ActionGroup> _actionGroupServiceManagerListener = null;

  /** the action set manager */
  private ActionSetManager                    _actionSetManager                  = null;

  /**
   * <p>
   * Creates a new instance of type {@link WhiteboardComponent}.
   * </p>
   */
  public WhiteboardComponent() {

    // create the ServiceManager
    _actionServiceManager = new DefaultServiceManager<Action>();
    _actionGroupServiceManager = new DefaultServiceManager<ActionGroup>();

    // create the ServiceManagerListener
    _actionServiceManagerListener = new ServiceManagerListener<Action>() {
      public void serviceRemoved(ServiceManagerEvent<Action> event) {
        _actionSetManager.removeAction(event.getService());
      }

      public void serviceAdded(ServiceManagerEvent<Action> event) {
        _actionSetManager.addAction(event.getService());
      }
    };

    // remove the ServiceManagerListener
    _actionGroupServiceManagerListener = new ServiceManagerListener<ActionGroup>() {
      public void serviceRemoved(ServiceManagerEvent<ActionGroup> event) {
        _actionSetManager.removeActionGroup(event.getService());
      }

      public void serviceAdded(ServiceManagerEvent<ActionGroup> event) {
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
   * Called if an {@link Action} is added.
   * </p>
   *
   * @param action
   *          the added {@link Action}
   */
  public void addAction(Action action) {
    _actionServiceManager.bindService(action);
  }

  /**
   * <p>
   * Called if an {@link Action} is removed.
   * </p>
   *
   * @param action
   *          the removed {@link Action}
   */
  public void removeAction(Action action) {
    _actionServiceManager.unbindService(action);
  }

  /**
   * <p>
   * Called if an {@link ActionGroup} is added.
   * </p>
   *
   * @param actionGroup
   *          the added {@link ActionGroup}
   */
  public void addActionGroup(ActionGroup actionGroup) {
    _actionGroupServiceManager.bindService(actionGroup);
  }

  /**
   * <p>
   * Called if an {@link ActionGroup} is removed.
   * </p>
   *
   * @param actionGroup
   *          the removed {@link ActionGroup}
   */
  public void removeActionGroup(ActionGroup actionGroup) {
    _actionGroupServiceManager.unbindService(actionGroup);
  }
}
