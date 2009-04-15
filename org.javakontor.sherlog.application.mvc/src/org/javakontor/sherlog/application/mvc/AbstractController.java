package org.javakontor.sherlog.application.mvc;

import org.javakontor.sherlog.application.request.Request;
import org.javakontor.sherlog.application.request.RequestHandler;
import org.javakontor.sherlog.util.Assert;


/**
 * <p>
 * Abstract implementation of the interface {@link Controller}.
 * </p>
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 *
 * @param <M>
 *          the type of the model
 * @param <V>
 *          the type of the view
 */
public abstract class AbstractController<M extends Model<M, E>, V extends View<M, E>, E extends Enum<E>>
    implements Controller<M, V> {

  /** the successor in the chain of responsibilities */
  private RequestHandler _successor;

  /** the model */
  private M              _model;

  /** the view */
  private V              _view;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractController}.
   * </p>
   *
   * @param model
   *          the model
   * @param view
   *          the view
   * @param successor
   *          the successor in the chain of responsibilities
   */
  protected AbstractController(M model, V view, RequestHandler successor) {
    Assert.notNull("Parameter model has to be set!", model);
    Assert.notNull("Parameter view has to be set!", view);

    _model = model;
    _view = view;
    _successor = successor;

    _model.setSuccessor(this);
    _model.addModelChangedListener(_view);
  }

  /**
   * <p>
   * Creates a new instance of type {@link AbstractController}.
   * </p>
   *
   * @param model
   *          the model
   * @param view
   *          the view
   */
  protected AbstractController(M model, V view) {
    this(model, view, null);
  }

  /**
   * {@inheritDoc}}
   */
  public M getModel() {
    return _model;
  }

  /**
   * {@inheritDoc}}
   */
  public V getView() {
    return _view;
  }

  /**
   * {@inheritDoc}}
   */
  public void setSuccessor(RequestHandler successor) {
    _successor = successor;
  }

  /**
   * {@inheritDoc}}
   */
  protected RequestHandler getSuccessor() {
    return _successor;
  }

  /**
   * <p>
   * Implements the method <code>handleRequest</code> of type {@link RequestHandler}.
   * </p>
   *
   * @param request
   *          der Request der behandelt werden soll.
   *
   * @precondition request != null
   *
   * @see org.javakontor.sherlog.application.request.RequestHandler#handleRequest(org.javakontor.sherlog.application.request.Request)
   */
  public final void handleRequest(Request request) {
    Assert.notNull("Parameter request muss ungleich null sein!", request);

    if (canHandleRequest(request)) {
      doHandleRequest(request);
    } else if (_successor != null) {
      _successor.handleRequest(request);
    }
  }

  /**
   * <p>
   * Default implementation of the <code>canHandleRequest</code> method that always returns <code>false</code>.
   * </p>
   *
   * @param request
   *          the request to handle
   * @return
   */
  public boolean canHandleRequest(Request request) {
    return false;
  }

  /**
   * <p>
   * Can be overwritten to handle the specified request. Note that you also have to overwrite the
   * <code>canHandleRequest</code> method.
   * </p>
   * <p>
   * The default implementation does nothing.
   * </p>
   *
   * @param request
   *          the request to handle.
   */
  protected void doHandleRequest(Request request) {
    // empty (default) implementation
  }

  /**
   * {@inheritDoc}}
   */
  public void dispose() {
    _model.removeModelChangedListener(_view);

    _model = null;
    _view = null;
    _successor = null;
  }

}
