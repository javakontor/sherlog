package org.javakontor.sherlog.util.servicemanager;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface ServiceManagerListener<S> {

  /**
   * <p>
   * </p>
   * 
   * @param event
   */
  public void serviceAdded(ServiceManagerEvent<S> event);

  /**
   * <p>
   * </p>
   * 
   * @param event
   */
  public void serviceRemoved(ServiceManagerEvent<S> event);
}
