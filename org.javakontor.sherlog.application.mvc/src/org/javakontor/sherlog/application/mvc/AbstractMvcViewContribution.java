package org.javakontor.sherlog.application.mvc;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import javax.swing.JPanel;

import org.javakontor.sherlog.application.request.Request;
import org.javakontor.sherlog.application.request.RequestHandler;
import org.javakontor.sherlog.application.view.AbstractViewContribution;
import org.javakontor.sherlog.application.view.ViewContribution;
import org.javakontor.sherlog.application.view.ViewContributionDescriptor;
import org.javakontor.sherlog.util.Assert;

/**
 * <p>
 * Abstract implementation of MVC-enabled ViewContribution.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 * @param <M>
 *          the model
 * @param <V>
 *          the view
 * @param <C>
 *          the controller
 */
public abstract class AbstractMvcViewContribution<M extends Model<M, ?>, V extends AbstractView<M, ?>, C extends Controller<M, V>>
    extends AbstractViewContribution implements ViewContribution {

  /** indicates whether or not the dialog is visible */
  private boolean          _isVisible;

  /** indicates whether or not the dialog is initialized */
  private boolean          _isInitialized = false;

  /** the model */
  private M                _model         = null;

  /** the view */
  private V                _view          = null;

  /** the controller */
  private C                _controller    = null;

  /** the descriptor */
  private final ViewContributionDescriptor _descriptor;

  /** the view class */
  private final Class<V>   _viewClass;

  /** the controller class */
  private final Class<C>   _controllerClass;

  /**
   * <p>
   * Creates a new instance of type {@link AbstractMvcViewContribution}. Using this constructor, the
   * {@link AbstractMvcViewContribution} will be initialized immediately.
   * </p>
   * 
   * @param descriptor
   *          the {@link Descriptor}
   * @param model
   *          the model
   * @param viewClass
   *          the view class
   * @param controllerClass
   *          the controller class
   */
  public AbstractMvcViewContribution(ViewContributionDescriptor descriptor, M model, Class<V> viewClass, Class<C> controllerClass) {
    this(descriptor, viewClass, controllerClass);

    Assert.notNull("Parameter model must not be null!", model);

    setModel(model);
    initialize();
  }

  /**
   * <p>
   * Creates a new instance of type {@link AbstractMvcViewContribution}.
   * </p>
   * 
   * @param descriptor
   *          the {@link ViewContributionDescriptor}
   * @param viewClass
   *          the view class
   * @param controllerClass
   *          the controller class
   */
  public AbstractMvcViewContribution(ViewContributionDescriptor descriptor, Class<V> viewClass, Class<C> controllerClass) {
    _descriptor = descriptor;
    _viewClass = viewClass;
    _controllerClass = controllerClass;
  }

  /**
   * <p>
   * Returns the associated model.
   * </p>
   * 
   * @return the associated model.
   */
  public final M getModel() {
    Assert.assertTrue(_isInitialized, "Not Initialized");

    return _model;
  }

  /**
   * {@inheritDoc}
   */
  public void viewEventOccured(ViewEvent viewEvent) {
    //
  }

  /**
   * {@inheritDoc}
   */
  public ViewContributionDescriptor getDescriptor() {
    return _descriptor;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void beforePassOnSuccessor(Request request) {
    // sender is always the dialog
    request.setSender(this);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean canHandleRequest(Request request) {
    return false;
  }

  /**
   * <p>
   * Returns whether or not the dialog is visible.
   * </p>
   * 
   * @return whether or not the dialog is visible.
   */
  public boolean isVisible() {
    return _isVisible;
  }

  /**
   * <p>
   * Sets the dialog visible.
   * </p>
   * 
   * @param flag
   *          -
   */
  public void setVisible(boolean visible) {
    _isVisible = visible;
  }

  /**
   * <p>
   * Returns the view.
   * </p>
   * 
   * @return the view.
   */
  public final V getView() {
    Assert.assertTrue(_isInitialized, "Not Initialized");

    return _view;
  }

  /**
   * <p>
   * Returns the view.
   * </p>
   * 
   * @return the view.
   */
  public final JPanel getPanel() {
    Assert.assertTrue(_isInitialized, "Not Initialized");

    return _view;
  }

  /**
   * <p>
   * Returns the controller.
   * </p>
   * 
   * @return the controller.
   */
  public final C getController() {
    Assert.assertTrue(_isInitialized, "Not Initialized");

    return _controller;
  }

  /**
   * <p>
   * Sets the model.
   * </p>
   * 
   * @param newModel
   */
  public final void setModel(M newModel) {
    Assert.notNull("Parameter newModel must not be null!", newModel);
    Assert.assertTrue(!_isInitialized, "Initialized");

    _model = newModel;
  }

  /**
   * <p>
   * Initializes the {@link AbstractMvcViewContribution}.
   * </p>
   */
  protected void initialize() {
    Assert.assertTrue(!_isInitialized, "Initialized");

    initialize(_viewClass, _controllerClass);
  }

  /**
   * <p>
   * Initializes the {@link AbstractMvcViewContribution}.
   * </p>
   * 
   * @param viewClass
   * @param controllerClass
   */
  private void initialize(Class<V> viewClass, Class<C> controllerClass) {
    Assert.notNull(_model);

    _view = createInstance(viewClass, new Class<?>[] { _model.getClass() }, new Object[] { _model });

    if (_view == null) {
      // TODO
      throw new RuntimeException("getViewInstance(model) liefert null zurück");
    }

    _controller = createInstance(controllerClass, new Class<?>[] { _model.getClass(), _view.getClass(),
        RequestHandler.class }, new Object[] { _model, _view, this });

    if (_controller == null) {
      throw new RuntimeException("getControllerInstance(model, view) liefert null zurück");
    }

    _isInitialized = true;
  }

  /**
   * <p>
   * Create a new instance of the specified type.
   * </p>
   * 
   * @param <T>
   *          the type
   * @param type
   * @param parameterTypes
   * @param parameters
   * 
   * @return the new instance
   */
  private <T> T createInstance(Class<T> type, Class<?>[] parameterTypes, Object[] parameters) {

    try {
      Constructor<T> constructor = type.getConstructor(parameterTypes);
      constructor.setAccessible(true);
      return constructor.newInstance(parameters);
    } catch (SecurityException e) {
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      e.printStackTrace();
    } catch (InstantiationException e) {
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      e.printStackTrace();
    }

    return null;
  }
}
