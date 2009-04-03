package org.javakontor.sherlog.application.request;

import org.javakontor.sherlog.util.Assert;

public class SetStatusMessageRequest extends Request {

  private final StatusMessage _statusMessage;

  public SetStatusMessageRequest(Object sender, StatusMessage statusMessage) {
    super(sender);

    Assert.notNull(statusMessage);

    this._statusMessage = statusMessage;
  }

  public StatusMessage getStatusMessage() {
    return this._statusMessage;
  }
}
