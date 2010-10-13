package org.javakontor.sherlog.application.request;

import org.javakontor.sherlog.util.Assert;

public class RequestHandlerImpl implements RequestHandler {

  private RequestHandler _successor;

  public RequestHandlerImpl() {
    this(null);
  }

  /**
   * Erzeugt eines Exemplar des Typs RequestHandler.
   * 
   * @param successor
   *          der Vorgänger in der RequestChain
   */
  public RequestHandlerImpl(final RequestHandler successor) {
    this._successor = successor;
  }

  public void setSuccessor(final RequestHandler successor) {
    this._successor = successor;
  }

  /**
   * Wird aufgefrufen, wenn der Request behandelt werden soll.
   * 
   * @param request
   *          der zu behandelnde Request.
   * 
   * @precondition request != null
   */
  public final void handleRequest(final Request request) {
    Assert.notNull("Parameter request muss ungleich null sein!", request);

    if (canHandleRequest(request)) {
      doHandleRequest(request);
      request.setHandled();
    } else {
      if (this._successor != null) {
        beforePassOnSuccessor(request);
        this._successor.handleRequest(request);
      }
    }
  }

  protected void beforePassOnSuccessor(final Request request) {
    //
  }

  /**
   * Wird aufgerufen, um zu ermitteln ob dieser RequestHandler den als Parameter übergebenen Request behandeln kann oder
   * nicht.
   * 
   * @param request
   *          der Request, der behandelt werden soll.
   * 
   * @return true, wenn der Request behandelt werden kann, false sonst.
   */
  public boolean canHandleRequest(final Request request) {
    return false;
  }

  /**
   * Einschubmethode. Kann von einer Unterklasse überschrieben werden, um den als Parameter übergebenen Request zu
   * behandeln.
   * 
   * @param request
   *          der Request, der zu behandeln ist.
   */
  public void doHandleRequest(final Request request) {
    //
  }
}
