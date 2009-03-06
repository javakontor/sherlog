package org.javakontor.sherlog.util.servicemanager;

import java.util.Set;

public interface ServiceManager<S> {

  public Set<S> getServices();

  public void addServiceManagerListener(ServiceManagerListener<S> listener);

  public void removeServiceManagerListener(ServiceManagerListener<S> listener);
}
