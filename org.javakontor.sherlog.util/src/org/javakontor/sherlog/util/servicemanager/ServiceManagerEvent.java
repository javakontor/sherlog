package org.javakontor.sherlog.util.servicemanager;

import java.util.EventObject;

/**
 * <p>
 * An instance of type {@link ServiceManagerEvent} is fired if a
 * {@link FilterConfigurationEditorFactory} is registered with or unregistered from the OSGi service registry.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ServiceManagerEvent<S> extends EventObject {

  /** serialVersionUID */
  private static final long serialVersionUID = 1L;

  /**
   * <p>
   * Creates a new instance of type {@link ServiceManagerEvent}.
   * </p>
   * 
   * @param source
   *          the {@link FilterConfigurationEditorFactory}
   */
  public ServiceManagerEvent(S factory) {
    super(factory);
  }

  /**
   * <p>
   * Returns the {@link FilterConfigurationEditorFactory}.
   * </p>
   * 
   * @return the {@link FilterConfigurationEditorFactory}.
   */
  @SuppressWarnings("unchecked")
  public S getService() {
    return (S) getSource();
  }
}
