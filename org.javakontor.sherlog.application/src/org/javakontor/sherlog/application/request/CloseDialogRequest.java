package org.javakontor.sherlog.application.request;

public class CloseDialogRequest extends Request {

  public static final int UNKNOWN = -1;

  public static final int OK      = 0;

  public static final int CANCEL  = 1;

  private final int       _reason;

  public CloseDialogRequest() {
    this(UNKNOWN);
  }

  public CloseDialogRequest(int reason) {
    this._reason = reason;
  }

  public int getReason() {
    return this._reason;
  }

  public boolean isOk() {
    return (this._reason == OK);
  }

  public boolean isCancel() {
    return (this._reason == CANCEL);
  }

}
