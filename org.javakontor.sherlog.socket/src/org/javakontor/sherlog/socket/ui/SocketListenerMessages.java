package org.javakontor.sherlog.socket.ui;

import org.javakontor.sherlog.util.nls.NLS;
import org.javakontor.sherlog.util.nls.NLSMessage;

public class SocketListenerMessages {
  @NLSMessage("<html><div style=\"padding:5px;font-size:8px\">Listening for LogEvents on port <b>%d</b></b><p>Active connections: <b>%d</b></div></html>")
  public static String socketListenerStatusBarPanelToolTip;

  static {
    NLS.initialize(SocketListenerMessages.class);
  }

}
