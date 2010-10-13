package org.javakontor.sherlog.application.request;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class RequestHandlerImplTest {

  @Test
  public void unhandledRequestWillBeMarkedAsUnhandled() throws Exception {
    Request request = new Request();
    new RequestHandlerImpl().handleRequest(request);
    assertThat(request.isHandled(), is(false));
  }

  @Test
  public void handledRequestWillBeMarkedAsHandled() throws Exception {
    Request request = new Request();
    new RequestHandlerImplStub().handleRequest(request);
    assertThat(request.isHandled(), is(true));
  }

  @Test
  public void unhandledRequestWillBePassedToSuccessor() throws Exception {
    RequestHandlerImpl successor = new RequestHandlerImplStub();
    Request request = new Request();
    new RequestHandlerImpl(successor).handleRequest(request);
    assertThat(request.isHandled(), is(true));

  }

  class RequestHandlerImplStub extends RequestHandlerImpl {
    @Override
    public boolean canHandleRequest(Request request) {
      return true;
    }

    @Override
    public void doHandleRequest(Request request) {
      request.setSender("handled by RequestHandlerImplStub");
    }

  }

}
