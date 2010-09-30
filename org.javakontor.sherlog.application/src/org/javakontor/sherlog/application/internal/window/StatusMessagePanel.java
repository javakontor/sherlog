package org.javakontor.sherlog.application.internal.window;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import org.javakontor.sherlog.application.request.StatusMessage;

/**
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class StatusMessagePanel extends JLabel {

  /** - */
  private static final long serialVersionUID = -5021554755870170084L;

  /** - * */
  public static final Font  STANDARD_FONT    = new Font("Dialog", Font.PLAIN, 13);

  /** - * */
  public static final Font  BOLD_FONT        = new Font("Dialog", Font.BOLD, 13);

  /** the status message * */
  private StatusMessage     _statusMessage;

  /**
   * 
   */
  public StatusMessagePanel() {
    super(" ");
    setFont(STANDARD_FONT);
    setHorizontalTextPosition(SwingConstants.LEFT);
    setForeground(Color.black);

    setBorder(BorderFactory.createCompoundBorder(BorderFactory.createEtchedBorder(), new EmptyBorder(5, 5, 5, 5)));

    this._statusMessage = new StatusMessage();
  }

  /**
   * <p>
   * Returns the status message.
   * </p>
   * 
   * @return the status message.
   */
  public StatusMessage getStatusMessage() {
    return this._statusMessage;
  }

  /**
   * 
   * 
   * @param message
   *          -
   */
  public void setStatusMessage(StatusMessage message) {

    // No message is set, when the message is meant as clearing for
    // a particular StatusMessageType and the StatusBarPanel has a
    // different StatusMessageType
    if ((null != message) && (!message.hasStatusMessageText())
        && (message.getStatusMessageType() != StatusMessage.NONE)
        && (message.getStatusMessageType() != this._statusMessage.getStatusMessageType())) {
    } else {
      this._statusMessage = message;
    }

    if (this._statusMessage == null) {
      setForeground(Color.BLACK);
      setText(" ");
    } else {
      if (this._statusMessage.getStatusMessageType() == StatusMessage.NONE) {
        setForeground(Color.BLACK);
      } else if (this._statusMessage.getStatusMessageType() == StatusMessage.INFORMATION) {
        setForeground(Color.BLACK);
      } else if (this._statusMessage.getStatusMessageType() == StatusMessage.WARNING) {
        setForeground(Color.RED);
      } else if (this._statusMessage.getStatusMessageType() == StatusMessage.ERROR) {
        setForeground(Color.RED);
      }

      if (this._statusMessage.hasStatusMessageText()) {
        setText(this._statusMessage.getStatusMessageText());
        setToolTipText(getHtmlMessageWithLinefeeds(this._statusMessage.getStatusMessageText()));
      } else {
        setText(" ");
        setToolTipText("");
      }
    }
  }

  private String getHtmlMessageWithLinefeeds(String message) {
    String str = "<html><body><div style=\"width:300px;padding:5px\">";
    str += message;
    str += "</div></body></html>";

    return str;
  }
}
