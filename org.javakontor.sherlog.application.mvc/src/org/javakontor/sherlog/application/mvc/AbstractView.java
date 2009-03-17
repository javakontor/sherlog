package org.javakontor.sherlog.application.mvc;

import javax.swing.JPanel;

import org.javakontor.sherlog.util.Assert;

/**
 * <p>
 * Abstract implementation of the interface {@link View}.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @param <M>
 *          the type of the model
 * @param <E>
 *          the enumeration with all possible reasons for change
 */
public abstract class AbstractView<M extends Model<M, E>, E extends Enum<E>> extends JPanel implements View<M, E> {

  /** serialVersionUID */
  private static final long serialVersionUID = 1L;

  /** the model */
  private final M           _model;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractView}.
   * </p>
   * 
   * @param model
   *          the associated model
   */
  protected AbstractView(M model) {
    super();
    Assert.notNull("Parameter model has to be set!", model != null);

    _model = model;

    setUp();
  }

  /**
   * {@inheritDoc}
   */
  public final M getModel() {
    return _model;
  }

  /**
   * <p>
   * Called to set up the view.
   * </p>
   */
  protected abstract void setUp();
}
