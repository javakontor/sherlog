package org.javakontor.sherlog.util;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * <p>
 * This interface permits to add (and remove) {@link PropertyChangeListener PropertyChangeListeners}. Usually calls to
 * methods defined in this interface should be delegated to an instance of type {@link PropertyChangeSupport}.
 * </p>
 * <p>
 * This interface is only necessary due to the fact that {@link PropertyChangeSupport} is implemented as a class instead
 * of an interface.
 * </p>
 */
public interface IPropertyChangeSupport {

  /**
   * <p>
   * Add a PropertyChangeListener to the listener list. The listener is registered for all properties. The same listener
   * object may be added more than once, and will be called as many times as it is added. If <code>listener</code> is
   * null, no exception is thrown and no action is taken.
   * </p>
   * 
   * @param listener
   *          The PropertyChangeListener to be added
   */
  public void addPropertyChangeListener(PropertyChangeListener listener);

  /**
   * <p>
   * Add a PropertyChangeListener for a specific property. The listener will be invoked only when a call on
   * firePropertyChange names that specific property. The same listener object may be added more than once. For each
   * property, the listener will be invoked the number of times it was added for that property. If
   * <code>propertyName</code> or <code>listener</code> is null, no exception is thrown and no action is taken.
   * </p>
   * 
   * @param propertyName
   *          The name of the property to listen on.
   * @param listener
   *          The PropertyChangeListener to be added
   */
  public void addPropertyChangeListener(String propertyName, PropertyChangeListener listener);

  /**
   * <p>
   * Remove a PropertyChangeListener from the listener list. This removes a PropertyChangeListener that was registered
   * for all properties. If <code>listener</code> was added more than once to the same event source, it will be notified
   * one less time after being removed. If <code>listener</code> is null, or was never added, no exception is thrown and
   * no action is taken.
   * </p>
   * 
   * @param listener
   *          The PropertyChangeListener to be removed
   */
  public void removePropertyChangeListener(PropertyChangeListener listener);

  /**
   * <p>
   * Remove a PropertyChangeListener for a specific property. If <code>listener</code> was added more than once to the
   * same event source for the specified property, it will be notified one less time after being removed. If
   * <code>propertyName</code> is null, no exception is thrown and no action is taken. If <code>listener</code> is null,
   * or was never added for the specified property, no exception is thrown and no action is taken.
   * </p>
   * 
   * @param propertyName
   *          The name of the property that was listened on.
   * @param listener
   *          The PropertyChangeListener to be removed
   */
  public void removePropertyChangeListener(String propertyName, PropertyChangeListener listener);
}
