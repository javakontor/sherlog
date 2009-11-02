package org.javakontor.sherlog.application.mvc;

import java.util.EventListener;

/**
 * <p>
 * A {@link ModelChangedListener} allows to track a model for changes.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @param <M>
 *          the type of the model
 * @param <E>
 *          the enumeration with all possible reasons for change
 */
public interface ModelChangedListener<M extends Model<M, E>, E extends Enum<E>> extends EventListener {

  /**
   * <p>
   * Called if the associated model has changed.
   * </p>
   * 
   * @param event
   *          the {@link ModelChangedEvent}
   */
  public void modelChanged(ModelChangedEvent<M, E> event);
}
