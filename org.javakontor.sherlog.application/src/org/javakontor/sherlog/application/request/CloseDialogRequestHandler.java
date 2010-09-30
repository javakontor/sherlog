package org.javakontor.sherlog.application.request;

import org.javakontor.sherlog.util.Assert;
import org.osgi.framework.ServiceRegistration;

public class CloseDialogRequestHandler extends RequestHandlerImpl {

  private final ServiceRegistration _serviceRegistration;

  public CloseDialogRequestHandler(ServiceRegistration serviceRegistration) {
    this(null, serviceRegistration);
  }

  public CloseDialogRequestHandler(RequestHandler successor, ServiceRegistration serviceRegistration) {
    super(successor);
    Assert.notNull("Parameter 'serviceRegistration' must not be null", serviceRegistration);
    this._serviceRegistration = serviceRegistration;
  }

  @Override
  public boolean canHandleRequest(Request request) {
    return (request instanceof CloseDialogRequest);
  }

  @Override
  public void doHandleRequest(Request request) {
    this._serviceRegistration.unregister();
  }

}
