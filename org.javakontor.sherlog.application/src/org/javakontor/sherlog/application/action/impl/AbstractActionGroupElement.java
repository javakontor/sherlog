package org.javakontor.sherlog.application.action.impl;

import java.util.Properties;

import org.javakontor.sherlog.application.action.ActionGroupElement;
import org.javakontor.sherlog.util.Assert;

/**
 * <p>
 * Abstract implementation of interface {@link ActionGroupElement}.
 * </p>
 */
public abstract class AbstractActionGroupElement extends AbstractPropertyChangeSupport implements ActionGroupElement {

  /** the service */
  private final Properties _serviceProperties;

  /**
   * <p>
   * </p>
   * 
   * @param serviceProperties
   */
  public AbstractActionGroupElement(Properties serviceProperties) {
    Assert.notNull("Parameter serviceProperties has to be set!", serviceProperties);
    // Assert. contains property
    // Assert. contains property

    _serviceProperties = serviceProperties;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.javakontor.sherlog.application.action.ActionGroupElement#getId()
   */
  public String getId() {
    return ActionGroupElementServiceHelper.getId(_serviceProperties);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.javakontor.sherlog.application.action.ActionGroupElement#getLabel()
   */
  public String getLabel() {
    return ActionGroupElementServiceHelper.getLabel(_serviceProperties);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.javakontor.sherlog.application.action.ActionGroupElement#getTargetActionGroupId()
   */
  public String getTargetActionGroupId() {
    return ActionGroupElementServiceHelper.getTargetActionGroupId(_serviceProperties);
  }

  /**
   * Returns the service properties
   * 
   * @return the service properties
   */
  public Properties getServiceProperties() {
    return this._serviceProperties;
  }
}
