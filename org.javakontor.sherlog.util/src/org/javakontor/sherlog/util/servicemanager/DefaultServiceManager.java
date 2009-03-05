package org.javakontor.sherlog.util.servicemanager;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.javakontor.sherlog.util.Assert;

public class DefaultServiceManager<S> implements  ServiceManager<S> {

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
    _services = new HashSet<S>();
    _listeners = new CopyOnWriteArrayList<ServiceManagerListener<S>>();
  }

  public Set<S> getServices() {
    synchronized (_lock) {
      return Collections.unmodifiableSet(_services);
    }
  }

  public Set<S> addServiceManagerListener(ServiceManagerListener<S> listener) {
    synchronized (_lock) {
      _listeners.add(listener);
      return new HashSet<S>(_services);
    }
  }

  public void removeServiceManagerListener(ServiceManagerListener<S> listener) {
    _listeners.remove(listener);
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
  protected final void fireServiceAdded(S service) {
    Assert.notNull(service);

    ServiceManagerEvent<S> event = new ServiceManagerEvent<S>(service);

    for (ServiceManagerListener<S> listener : _listeners) {
      listener.serviceAdded(event);
    }
  }

  /**
   * @param factory
   */
  protected final void fireServiceRemoved(S service) {
    Assert.notNull(service);

    ServiceManagerEvent<S> event = new ServiceManagerEvent<S>(service);

    for (ServiceManagerListener<S> listener : _listeners) {
      listener.serviceRemoved(event);
    }
  }

  /**
   * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
   * 
   * @param <S>
   * @param <L>
   */
  public interface FireManagerEventTemplate<S, L, E> {

    public E createServiceEvent(S service, int type);

    public void notifyListener(L listener, E event, int type);
  }
}
