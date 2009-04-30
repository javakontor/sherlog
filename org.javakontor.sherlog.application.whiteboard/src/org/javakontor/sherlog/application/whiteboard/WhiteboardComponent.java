package org.javakontor.sherlog.application.whiteboard;

import org.javakontor.sherlog.application.action.Action;
import org.javakontor.sherlog.application.action.ActionGroup;
import org.javakontor.sherlog.application.action.ActionSetManager;
import org.javakontor.sherlog.util.servicemanager.DefaultServiceManager;
import org.javakontor.sherlog.util.servicemanager.ServiceManagerEvent;
import org.javakontor.sherlog.util.servicemanager.ServiceManagerListener;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class WhiteboardComponent {

  /** - */
  private DefaultServiceManager<Action>       _actionServiceManager              = null;

  /** - */
  private ServiceManagerListener<Action>      _actionServiceManagerListener      = null;

  /** - */
  private DefaultServiceManager<ActionGroup>  _actionGroupServiceManager         = null;

  /** - */
  private ServiceManagerListener<ActionGroup> _actionGroupServiceManagerListener = null;

  /** - */
  private ActionSetManager                    _actionSetManager                  = null;

  /**
   * <p>
   * </p>
   */
  public WhiteboardComponent() {
    _actionServiceManager = new DefaultServiceManager<Action>();
    _actionGroupServiceManager = new DefaultServiceManager<ActionGroup>();

    _actionServiceManagerListener = new ServiceManagerListener<Action>() {
      public void serviceRemoved(ServiceManagerEvent<Action> event) {
        _actionSetManager.removeAction(event.getService());
      }

      public void serviceAdded(ServiceManagerEvent<Action> event) {
        _actionSetManager.addAction(event.getService());
      }
    };

    _actionGroupServiceManagerListener = new ServiceManagerListener<ActionGroup>() {
      public void serviceRemoved(ServiceManagerEvent<ActionGroup> event) {
        _actionSetManager.removeActionGroup(event.getService());
      }

      public void serviceAdded(ServiceManagerEvent<ActionGroup> event) {
        _actionSetManager.addActionGroup(event.getService());
      }
    };
  }

  public void addActionSetManager(ActionSetManager actionSetManager) {
    _actionSetManager = actionSetManager;
    _actionServiceManager.addServiceManagerListener(_actionServiceManagerListener);
    _actionGroupServiceManager.addServiceManagerListener(_actionGroupServiceManagerListener);
  }

  public void removeActionSetManager(ActionSetManager actionSetManager) {
    _actionSetManager = actionSetManager;
    _actionServiceManager.removeServiceManagerListener(_actionServiceManagerListener);
    _actionGroupServiceManager.removeServiceManagerListener(_actionGroupServiceManagerListener);
  }

  public void addAction(Action action) {
    _actionServiceManager.bindService(action);
  }

  public void removeAction(Action action) {
    _actionServiceManager.unbindService(action);
  }

  public void addActionGroup(ActionGroup actionGroup) {
    _actionGroupServiceManager.bindService(actionGroup);
  }

  public void removeActionGroup(ActionGroup actionGroup) {
    _actionGroupServiceManager.unbindService(actionGroup);
  }
}
