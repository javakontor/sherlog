package org.javakontor.sherlog.application.mvc;

/**
 * <p>
 * Defines a view in the model-view-controller (MVC) pattern. An implementing class of this type must be parameterized
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
public interface View<M extends Model<M, E>, E extends Enum<E>> extends ModelChangedListener<M, E> {

  /**
   * <p>
   * Returns the associated model.
   * </p>
   * 
   * @return the associated model.
   */
  public M getModel();
}
