package org.javakontor.sherlog.application.mvc;

import org.javakontor.sherlog.application.request.RequestHandler;

/**
 * <p>
 * Defines a model in the model-view-controller (MVC) pattern. An implementing class of this type must be parameterized
 * with a specific {@link Model} and an enumeration that defines the possible reasons for change (you can use
 * {@link DefaultReasonForChange} if you don't need any specific change reasons).
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @param <M>
 *          the type of the model
 * @param <E>
 *          the enumeration with all possible reasons for change
 */
public interface Model<M extends Model<M, E>, E extends Enum<E>> extends RequestHandler {

  /**
   * <p>
   * Adds the specified {@link ModelChangedListener} to the model.
   * </p>
   * 
   * @param modelChangedListener
   *          the listener
   */
  public void addModelChangedListener(ModelChangedListener<M, E> modelChangedListener);

  /**
   * <p>
   * Removes the specified {@link ModelChangedListener} from the model.
   * </p>
   * 
   * @param modelChangedListener
   *          the listener
   */
  public void removeModelChangedListener(ModelChangedListener<M, E> modelChangedListener);

  /**
   * <p>
   * Removes the {@link ModelChangedListener}.
   * </p>
   */
  public void removeModelChangedListeners();

  /**
   * <p>
   * Disposes the model.
   * </p>
   */
  public void dispose();
}
