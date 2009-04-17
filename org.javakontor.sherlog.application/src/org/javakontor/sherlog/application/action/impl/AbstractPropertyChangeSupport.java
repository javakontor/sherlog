package org.javakontor.sherlog.application.action.impl;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * <p>
 * </p>
 */
public class AbstractPropertyChangeSupport {

  /** - */
  protected final PropertyChangeSupport _propertyChangeSupport;

  /**
   * <p>
   * </p>
   */
  public AbstractPropertyChangeSupport() {
    _propertyChangeSupport = new PropertyChangeSupport(this);
  }

  /**
   * @see PropertyChangeSupport#addPropertyChangeListener(listener)
   */
  public void addPropertyChangeListener(PropertyChangeListener listener) {
    _propertyChangeSupport.addPropertyChangeListener(listener);

  }

  /**
   * @see PropertyChangeSupport#addPropertyChangeListener(propertyName, listener)
   */
  public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener) {
    _propertyChangeSupport.addPropertyChangeListener(propertyName, listener);

  }

  /**
   * @see PropertyChangeSupport#removePropertyChangeListener(PropertyChangeListener)
   */
  public void removePropertyChangeListener(PropertyChangeListener listener) {
    _propertyChangeSupport.removePropertyChangeListener(listener);

  }

  /**
   * @see PropertyChangeSupport#removePropertyChangeListener(String, PropertyChangeListener)
   */
  public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener) {
    _propertyChangeSupport.removePropertyChangeListener(propertyName, listener);
  }

  /**
   * @see PropertyChangeSupport#firePropertyChange(String, Object, Object)
   */
  protected void firePropertyChangeEvent(String propertyName, Object oldValue, Object newValue) {
    _propertyChangeSupport.firePropertyChange(propertyName, oldValue, newValue);
  }
}
