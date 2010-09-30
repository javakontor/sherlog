package org.javakontor.sherlog.application.request;

/**
 * <p>
 * Defines the interface for elements of the chain of responsibility.
 * </p>
 */
public interface RequestHandler {

  /**
   * <p>
   * Sets the successor for this {@link RequestHandler}. If this {@link RequestHandler} cannot handle the specified
   * {@link Request} it must be passed on to specified successor.
   * </p>
   * 
   * @param successor
   *          the successor for this {@link RequestHandler}.
   */
  public void setSuccessor(RequestHandler successor);

  /**
   * <p>
   * Handles the specified {@link Request}. If this {@link RequestHandler} cannot handle the specified {@link Request}
   * it must be passed on to specified successor.
   * </p>
   * 
   * @param request
   */
  public void handleRequest(Request request);
}
