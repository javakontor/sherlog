package org.javakontor.sherlog.application.mvc;

import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javakontor.sherlog.application.request.RequestHandlerImpl;
import org.javakontor.sherlog.util.Assert;

/**
 * <p>
 * Abstract implementation of the interface {@link Model}.
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @param <M>
 *          the type of the model
 * @param <E>
 *          the enumeration with all possible reasons for change
 */
public abstract class AbstractModel<M extends Model<M, E>, E extends Enum<E>> extends RequestHandlerImpl implements
    Model<M, E> {

  private final Log                                           _logger;

  /** all registered listeners */
  private final CopyOnWriteArrayList<ModelChangedListener<M, E>> _listenerList;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractModel}.
   * </p>
   */
  public AbstractModel() {
    this._logger = LogFactory.getLog(getClass());

    // create a thread safe CopyOnWriteArrayList
    this._listenerList = new CopyOnWriteArrayList<ModelChangedListener<M, E>>();
  }

  /**
   * <p>
   * Disposes this model. You can overwrite this method in subclasses to do resource cleanups etc..
   * </p>
   * <p>
   * Note: overriding methods must call <tt>super.dispose()</tt>!
   * </p>
   * 
   * @see org.lumberjack.application.mvc.Model#dispose()
   */
  public void dispose() {
    removeModelChangedListeners();
  }

  /**
   * {@inheritDoc}
   */
  public void addModelChangedListener(ModelChangedListener<M, E> modelChangeListener) {
    Assert.notNull("Parameter modelChangeListener has to be set!", modelChangeListener);

    _listenerList.addIfAbsent(modelChangeListener);
  }

  /**
   * {@inheritDoc}
   */
  public void removeModelChangedListener(ModelChangedListener<M, E> modelChangeListener) {
    Assert.notNull("Parameter modelChangeListener has to be set!", modelChangeListener);

    _listenerList.remove(modelChangeListener);
  }

  /**
   * {@inheritDoc}
   */
  public void removeModelChangedListeners() {
    _listenerList.clear();
  }

  /**
   * <p>
   * Propagates a {@link ModelChangedEvent} with the specified reason for change to all registered listeners.
   * </p>
   * 
   * @param reasonForChange
   *          the reason for change
   */
  @SuppressWarnings("unchecked")
  protected void fireModelChangedEvent(E reasonForChange) {
    Assert.notNull("Parameter reasonForChange has to be set!", reasonForChange);

    fireModelChangedEvent(new ModelChangedEvent(this, reasonForChange));
  }

  /**
   * <p>
   * Propagates a {@link ModelChangedEvent} with the specified reason for change and the the specified objects to all
   * registered listeners.
   * </p>
   * 
   * @param reasonForChange
   *          the reason for change
   * @param objects
   *          the associated objects
   */
  @SuppressWarnings("unchecked")
  protected void fireModelChangedEvent(E reasonForChange, Object... objects) {
    Assert.notNull("Parameter reasonForChange has to be set!", reasonForChange);
    Assert.notNull("Parameter objects has to be set!", objects);

    fireModelChangedEvent(new ModelChangedEvent(this, reasonForChange, objects));
  }

  /**
   * <p>
   * Propagates the specified {@link ModelChangedEvent} to all registered listeners.
   * </p>
   * 
   * @param event
   *          the {@link ModelChangedEvent}
   */
  protected final void fireModelChangedEvent(final ModelChangedEvent<M, E> event) {
    Assert.notNull("Parameter event has to be set!", event);

    for (ModelChangedListener<M, E> modelListener : _listenerList) {
      try {
        modelListener.modelChanged(event);
      } catch (RuntimeException ex) {
        _logger.warn("Exception while invoking ModelChangedListener '" + modelListener + "' with event '" + event
            + "': " + ex, ex);
      }
    }
  }
}