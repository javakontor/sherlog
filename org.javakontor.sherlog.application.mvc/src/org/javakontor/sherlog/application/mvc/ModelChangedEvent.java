package org.javakontor.sherlog.application.mvc;

import java.util.EventObject;

import org.javakontor.sherlog.util.Assert;

/**
 * <p>
 * Defines a model changed event.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @param <M>
 *          the type of the model
 * @param <E>
 *          the enumeration with all possible reasons for change
 */
public class ModelChangedEvent<M extends Model<M, E>, E extends Enum<E>> extends EventObject {

  /** serialVersionUID */
  private static final long serialVersionUID = 3720142661280331477L;

  /** the reason for change */
  private E                 _reasonForChange;

  /** an array of associated objects */
  private Object[]          _objects         = new Object[0];

  /**
   * <p>
   * Creates a new instance of type {@link ModelChangedEvent}.
   * </p>
   * 
   * @param model
   *          the model
   * @param reasonForChange
   *          the reason for change
   */
  public ModelChangedEvent(M model, E reasonForChange) {
    this(model, reasonForChange, (Object[]) null);
  }

  /**
   * <p>
   * Creates a new instance of type {@link ModelChangedEvent}.
   * </p>
   * 
   * @param model
   *          the model
   * @param reasonForChange
   *          the reason for change
   * @param objects
   *          associated objects
   */
  public ModelChangedEvent(M model, E reasonForChange, Object... objects) {
    super(model);

    Assert.notNull(model);

    _reasonForChange = reasonForChange;
    _objects = objects;
  }

  /**
   * <p>
   * Returns <code>true</code> if any associated objects are set, <code>false</code> otherwise.
   * </p>
   * 
   * @return <code>true</code> if any associated objects are set, <code>false</code> otherwise.
   */
  public boolean hasObjects() {
    return _objects != null;
  }

  /**
   * <p>
   * Returns the array of associated objects.
   * </p>
   * 
   * @return the array of associated objects.
   */
  public Object[] getObjects() {
    return _objects;
  }

  /**
   * <p>
   * Returns the reason for change.
   * </p>
   * 
   * @return the reason for change.
   */
  public E getReasonForChange() {
    return _reasonForChange;
  }

  /**
   * <p>
   * Returns the model.
   * </p>
   * 
   * @return the model.
   */
  @SuppressWarnings("unchecked")
  public M getModel() {
    return (M) getSource();
  }
}
