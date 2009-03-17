package org.javakontor.sherlog.application.mvc;

import org.javakontor.sherlog.application.request.RequestHandler;

/**
 * <p>
 * Defines a controller in the model-view-controller (MVC) pattern. An implementing class of this type must be
 * parameterized with a specific {@link Model} and a specific view.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @param <M>
 *          the type of the model
 * @param <E>
 *          the enumeration with all possible reasons for change
 */
public interface Controller<M extends Model<M, ?>, V extends View<M, ?>> extends RequestHandler {

  /**
   * <p>
   * Returns the model.
   * </p>
   * 
   * @return the model.
   */
  public M getModel();

  /**
   * <p>
   * Returns the view.
   * </p>
   * 
   * @return the view.
   */
  public V getView();

  /**
   * <p>
   * Disposes the controller.
   * </p>
   */
  public void dispose();
}
