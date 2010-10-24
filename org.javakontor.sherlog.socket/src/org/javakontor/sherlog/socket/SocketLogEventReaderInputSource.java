package org.javakontor.sherlog.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;

import org.javakontor.sherlog.domain.reader.LogEventReaderInputSource;

public class SocketLogEventReaderInputSource implements LogEventReaderInputSource {
  
  private final Socket _socket;
  
  public SocketLogEventReaderInputSource(Socket socket) {
    super();
    _socket = socket;
  }

  public InputStream openStream() throws IOException {
    return _socket.getInputStream();
  }

  public String getId() {
    return _socket.toString();
  }
  
  

}
