package org.javakontor.sherlog.application.request;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class StatusMessage {

  public static final int NONE        = 0;

  public static final int INFORMATION = 1;

  public static final int WARNING     = 2;

  public static final int ERROR       = 3;

  /** - */
  private final String    _messageText;

  /** - */
  private final int       _messageType;

  /**
   * @param string
   * @param waiting2
   */
  public StatusMessage(String messageText, int messageType) {
    this._messageText = messageText;
    this._messageType = messageType;
  }

  public StatusMessage() {
    this._messageText = "";
    this._messageType = NONE;
  }

  public boolean hasStatusMessageText() {
    return (this._messageText != null) && !this._messageText.trim().equals("");
  }

  public int getStatusMessageType() {
    return this._messageType;
  }

  public String getStatusMessageText() {
    return this._messageText;
  }

}
