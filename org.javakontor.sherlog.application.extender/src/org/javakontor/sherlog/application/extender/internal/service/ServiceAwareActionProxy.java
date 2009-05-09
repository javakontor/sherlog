package org.javakontor.sherlog.application.extender.internal.service;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.javakontor.sherlog.application.action.AbstractAction;
import org.javakontor.sherlog.application.action.Action;
import org.javakontor.sherlog.application.extender.internal.model.ActionEntry;
import org.javakontor.sherlog.application.extender.internal.model.Reference;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.util.tracker.ServiceTracker;

/**
 * <p>
 * {@link Action} that implements support for service handling.
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ServiceAwareActionProxy extends AbstractAction {

  /** service trackers */
  private List<InternalServiceTracker> _serviceTrackers;

  /** - */
  private ActionEntry                  _actionEntry;

  public ServiceAwareActionProxy(ActionEntry actionEntry) {
    super();

    _actionEntry = actionEntry;
  }

  public void execute() {
    _actionEntry.getAction().execute();
  }

  /**
   * @see org.javakontor.sherlog.application.extender.Lifecycle#initialise()
   */
  public void initialise() {

    // create service tracker map
    _serviceTrackers = new LinkedList<InternalServiceTracker>();

    for (Reference reference : _actionEntry.getReferences()) {

      try {
        // create service tracker
        InternalServiceTracker serviceTracker = new InternalServiceTracker(_actionEntry, reference, this);

        // add to list
        _serviceTrackers.add(serviceTracker);
      } catch (InvalidSyntaxException e) {
        e.printStackTrace();
      }
    }

    // open service trackers
    for (ServiceTracker serviceTracker : _serviceTrackers) {
      serviceTracker.open();
    }
  }

  /**
   * @see org.javakontor.sherlog.application.extender.Lifecycle#dispose()
   */
  public void dispose() {
    //
    for (ServiceTracker serviceTracker : _serviceTrackers) {
      serviceTracker.close();
    }

    _serviceTrackers = null;
  }

  @Override
  public void setEnabled(boolean enabled) {
    _actionEntry.getAction().setEnabled(enabled);
  }

  /**
   * <p>
   * </p>
   *
   * @return
   */
  boolean servicesSatisfied() {
    // get service trackers
    List<InternalServiceTracker> trackers = _serviceTrackers;

    // check if satisfied
    for (InternalServiceTracker tracker : trackers) {
      if (tracker.getCount() < 1) {
        return false;
      }
    }

    // return true
    return true;
  }
}