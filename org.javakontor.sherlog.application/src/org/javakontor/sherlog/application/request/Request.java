package org.javakontor.sherlog.application.request;

import org.javakontor.sherlog.util.Assert;

/**
 * <p>
 * Defines a request that can be passed on a chain of {@link RequestHandler RequestHandlers}.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class Request {

  /** the object that has send the request */
  private Object  _sender;

  /** true, if the request has been handled * */
  private boolean _handled = false;

  /**
   * <p>
   * Creates a new instance of type {@link Request}.
   * </p>
   */
  public Request() {
    this._sender = null;
  }

  /**
   * <p>
   * Creates a new instance of type {@link Request}.
   * </p>
   * 
   * @param sender
   *          the object that has send the request
   * 
   * @precondition sender != null
   */
  public Request(Object sender) {
    Assert.notNull("Parameter sender muss ungleich null sein!", sender);

    this._sender = sender;
  }

  /**
   * <p>
   * Returns <code>true</code> if the request has been handled.
   * 
   * @return <code>true</code> if the request has been handled, <code>false</code> otherwise.
   */
  public boolean isHandled() {
    return this._handled;
  }

  /**
   * <p>
   * Sets the 'handled' property to <code>true</code>.
   * </p>
   */
  public void setHandled() {
    this._handled = true;
  }

  /**
   * <p>
   * Returns <code>true</code> if a sender is set, <code>false</code> otherwise.
   * </p>
   * 
   * @return <code>true</code> if a sender is set, <code>false</code> otherwise.
   */
  public boolean hasSender() {
    return this._sender != null;
  }

  /**
   * <p>
   * Returns the sender of the request.
   * </p>
   * 
   * @return the sender of the request.
   */
  public Object sender() {
    return this._sender;
  }

  /**
   * <p>
   * Sets the sender for this request.
   * </p>
   * 
   * @param sender
   *          the sender for this request.
   * 
   * @precondition sender != null
   */
  public void setSender(Object sender) {
    Assert.notNull("Parameter sender must not be null!", sender);

    this._sender = sender;
  }
}
