package org.javakontor.sherlog.socket.test;

import org.apache.log4j.Logger;
import org.apache.log4j.net.SocketAppender;

public class LogEventProducer implements Runnable {

  private SocketAppender _socketAppender = null;

  private final int      _port;

  private boolean        _shouldStop     = false;

  private Logger         _logger         = Logger.getLogger("org.sherlog.test.logproducer."
                                             + System.currentTimeMillis());

  public LogEventProducer(int port) {
    super();
    _port = port;
  }

  public void produceInBackground() {
    Thread t = new Thread(this);
    t.start();
  }

  public void produce(int count) {
    initializeSocketAppender();

    for (int i = 0; i < count; i++) {
      String message = "Log Event #" + i;
      System.out.println("Logging: " + message);
      _logger.info(message);
    }

  }

  public void stop() {
    _shouldStop = true;
  }

  public void run() {
    initializeSocketAppender();

    int i = 0;
    while (!_shouldStop) {
      String text = "2 Log Event #" + (++i);
      System.out.println(text);
      _logger.info(text);
      try {
        Thread.sleep(1000);
      } catch (Exception ex) {
        // ignore
      }
    }

    dispose();
  }

  public void connect() {
    initializeSocketAppender();
  }

  private void initializeSocketAppender() {
    if (_socketAppender == null) {
      _socketAppender = new SocketAppender("localhost", _port);
      _socketAppender.setName("LogFileProducer.socketAppender");
      _logger.addAppender(_socketAppender);
    }
  }

  public void dispose() {
    if (_socketAppender != null) {
      _logger.removeAppender(_socketAppender);
      _socketAppender.close();
      _socketAppender = null;
    }
  }

  public static void main(String[] args) {
    LogEventProducer producer = new LogEventProducer(4445);
    producer.produceInBackground();
  }

}
