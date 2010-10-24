package org.javakontor.sherlog.socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import org.javakontor.sherlog.domain.impl.reader.SimpleLogEventHandler;
import org.javakontor.sherlog.domain.reader.LogEventFlavour;
import org.javakontor.sherlog.domain.reader.LogEventReader;
import org.javakontor.sherlog.domain.reader.LogEventReaderFactory;
import org.javakontor.sherlog.domain.store.ModifiableLogEventStore;
import org.javakontor.sherlog.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketListener extends Thread {

  private static int                                                   _instanceCounter      = 0;

  Logger                                                               _logger               = LoggerFactory
                                                                                                 .getLogger(getClass());

  private boolean                                                      _shouldStop           = false;

  private final ServerSocket                                           _serverSocket;

  private final LogEventFlavour                                        _logEventFlavour;

  private final ModifiableLogEventStore                                _logEventStore;

  private LogEventReaderFactory                                        _logEventReaderFactory;

  private final List<LogEventReader>                                   _activeLogEventReader = new LinkedList<LogEventReader>();

  private final CopyOnWriteArraySet<SocketListenerStateChangeListener> _stateChangeListener  = new CopyOnWriteArraySet<SocketListenerStateChangeListener>();

  public SocketListener(int port, LogEventFlavour logEventFlavour, ModifiableLogEventStore logEventStore,
      LogEventReaderFactory logEventReaderFactory) throws IOException {
    super("SocketListener-" + (++_instanceCounter));
    _serverSocket = new ServerSocket(port);
    _logEventFlavour = logEventFlavour;
    _logEventStore = logEventStore;
    _logEventReaderFactory = logEventReaderFactory;
  }

  public void setLogEventReaderFactory(LogEventReaderFactory logEventReaderFactory) {
    _logEventReaderFactory = logEventReaderFactory;
  }

  public void run() {
    try {
      doRun();
    } finally {
      dispose();
    }
  }

  public void shutdown() {
    _logger.debug("Stopping SocketListener");
    _shouldStop = true;
    dispose();
  }

  private void doRun() {
    while (!_shouldStop) {
      try {
        _logger.debug("Waiting for connection");
        Socket clientSocket = _serverSocket.accept();
        if (!_shouldStop) {
          SocketLogEventReaderInputSource inputSource = new SocketLogEventReaderInputSource(clientSocket);
          LogEventReader logEventReader = _logEventReaderFactory.getLogEventReader(inputSource, _logEventFlavour);
          if (logEventReader != null) {
            SocketListenerLogEventHandler batchLogEventHandler = new SocketListenerLogEventHandler(_logEventStore,
                logEventReader);
            logEventReader.addLogEventHandler(batchLogEventHandler);
            synchronized (_activeLogEventReader) {
              _activeLogEventReader.add(logEventReader);
            }
            if (!_shouldStop) {
              logEventReader.start();
              fireStateChange();
            }
          }
        }
      } catch (Exception ex) {
        _logger.error("Error: " + ex, ex);
        break;
      }
    }
    _logger.debug("Leaving doRun()");

  }

  private synchronized void dispose() {
    closeServerSocket();
    stopAllLogEventReader();
    removeAllListener();
  }

  private void removeAllListener() {
    _stateChangeListener.clear();
  }

  private void closeServerSocket() {
    try {
      if (!_serverSocket.isClosed()) {
        _logger.info("Closing ServerSocket");
        _serverSocket.close();
      }
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private void stopAllLogEventReader() {
    synchronized (_activeLogEventReader) {
      for (LogEventReader logEventReader : _activeLogEventReader) {
        try {
          _logger.debug("Stopping reader " + logEventReader);
          logEventReader.stop();
        } catch (Exception ex) {
          _logger.warn("could not stop reader: " + ex, ex);
        }
      }
      _activeLogEventReader.clear();
    }

    fireStateChange();
  }

  class SocketListenerLogEventHandler extends SimpleLogEventHandler {
    private final LogEventReader _logEventReader;

    public SocketListenerLogEventHandler(ModifiableLogEventStore logEventStore, LogEventReader logEventReader) {
      super(logEventStore);
      _logEventReader = logEventReader;
    }

    @Override
    public void dispose() {
      // Remove the associated LogEventReader from list of active log event readers
      synchronized (_activeLogEventReader) {
        _activeLogEventReader.remove(_logEventReader);
      }

      // notify listener
      fireStateChange();
    }

  }

  /**
   * Returns the number of active connections
   * 
   * @return
   */
  public int getActiveConnections() {
    synchronized (_activeLogEventReader) {
      return _activeLogEventReader.size();
    }
  }

  public void addSocketListenerStateChangeListener(SocketListenerStateChangeListener listener) {
    Assert.notNull("Parameter 'listener' must not be null", listener);
    _stateChangeListener.add(listener);
  }

  public void removeSocketListenerStateChangeListener(SocketListenerStateChangeListener listener) {
    Assert.notNull("Parameter 'listener' must not be null", listener);
    _stateChangeListener.remove(listener);
  }

  private void fireStateChange() {
    for (SocketListenerStateChangeListener listener : _stateChangeListener) {
      try {
        listener.stateChanged();
      } catch (Exception ex) {
        _logger.warn("Error while invoking Listener: " + ex, ex);
      }
    }
  }
}
