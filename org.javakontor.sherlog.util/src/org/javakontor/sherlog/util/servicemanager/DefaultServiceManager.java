package org.javakontor.sherlog.util.servicemanager;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

import org.javakontor.sherlog.util.Assert;

public class DefaultServiceManager<S> implements ServiceManager<S> {

  /** - */
  private Object                          _lock = new Object();

  /** - */
  private Set<S>                          _services;

  /** - */
  private List<ServiceManagerListener<S>> _listeners;

  /**
   *
   */
  public DefaultServiceManager() {
    _services = new CopyOnWriteArraySet<S>();
    _listeners = new CopyOnWriteArrayList<ServiceManagerListener<S>>();
  }

  public Set<S> getServices() {
    return Collections.unmodifiableSet(_services);
  }

  public void addServiceManagerListener(ServiceManagerListener<S> listener) {
    synchronized (_lock) {
      _listeners.add(listener);
    }
    for (S service : _services) {
      listener.serviceAdded(new ServiceManagerEvent<S>(service));
    }
  }

  public void removeServiceManagerListener(ServiceManagerListener<S> listener) {
    synchronized (_lock) {
      _listeners.remove(listener);
    }
    for (S service : _services) {
      listener.serviceRemoved(new ServiceManagerEvent<S>(service));
    }
  }

  /**
   * @param factory
   */
  public void bindService(S service) {
    synchronized (_lock) {
      _services.add(service);
    }
    fireServiceAdded(service);
  }

  /**
   * @param factory
   */
  public void unbindService(S service) {
    synchronized (_lock) {
      _services.remove(service);
    }
    fireServiceRemoved(service);
  }

  /**
   * @param factory
   */
  private final void fireServiceAdded(S service) {
    Assert.notNull(service);

    ServiceManagerEvent<S> event = new ServiceManagerEvent<S>(service);

    for (ServiceManagerListener<S> listener : _listeners) {
      listener.serviceAdded(event);
    }
  }

  /**
   * @param factory
   */
  private final void fireServiceRemoved(S service) {
    Assert.notNull(service);

    ServiceManagerEvent<S> event = new ServiceManagerEvent<S>(service);

    for (ServiceManagerListener<S> listener : _listeners) {
      listener.serviceRemoved(event);
    }
  }
}
