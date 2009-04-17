package org.javakontor.sherlog.application.mvc;

import javax.swing.JPanel;

import org.javakontor.sherlog.util.Assert;
import org.javakontor.sherlog.util.ui.GuiExecutor;

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
  

  /**
   * Forwards the ModelChangedEvent to {@link #onModelChanged(ModelChangedEvent)}. This method makes sure,
   * {@link #onModelChanged(ModelChangedEvent)} is called on the Event Dispatcher Thread.
   * 
   * <p>
   * Subclasses should override {@link #onModelChanged(ModelChangedEvent)} to react on the event
   * 
   * @see org.lumberjack.application.mvc.ModelChangedListener#modelChanged(org.lumberjack.application.mvc.ModelChangedEvent)
   */
  public final void modelChanged(final ModelChangedEvent<M, E> event) {
    GuiExecutor.execute(new Runnable() {
      public void run() {
        onModelChanged(event);
      }
    });
  }

  /**
   * Override this method in subclasses to react on ModelChangedEvents.
   * 
   * <p>
   * This method will be invoked on the Swing Event Dispatcher Thread when a {@link ModelChangedEvent} occured.
   * </p>
   * 
   * @see #modelChanged(ModelChangedEvent)
   */
  protected void onModelChanged(final ModelChangedEvent<M, E> event) {

  }
}
