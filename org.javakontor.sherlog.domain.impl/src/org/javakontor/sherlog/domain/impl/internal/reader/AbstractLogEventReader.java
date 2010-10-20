package org.javakontor.sherlog.domain.impl.internal.reader;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;

import org.javakontor.sherlog.domain.LogEvent;
import org.javakontor.sherlog.domain.LogEventSource;
import org.javakontor.sherlog.domain.impl.filter.AbstractFilterable;
import org.javakontor.sherlog.domain.reader.LogEventHandler;
import org.javakontor.sherlog.domain.reader.LogEventReader;
import org.javakontor.sherlog.domain.reader.LogEventReaderInputSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractLogEventReader extends AbstractFilterable implements LogEventReader {

  Logger                                  _logger   = LoggerFactory.getLogger(getClass());

  private final List<LogEventHandler>     _listener = new ArrayList<LogEventHandler>();

  private boolean                         _isActive;

  private boolean                         _stopRequested;

  private final LogEventReaderInputSource _inputSource;

  private InputStream                     _logInputStream;

  /**
   * @param inputSource
   *          The {@link LogEventReaderInputSource} to read log events from
   */
  public AbstractLogEventReader(final LogEventReaderInputSource inputSource) {
    assert inputSource != null;
    _inputSource = inputSource;
    _isActive = false;
  }

  public void start() {
    _isActive = true;
    final Thread thread = new Thread(new Runnable() {
      public void run() {
        try {
          readLogFiles();
        } catch (final CancellationException e) {
          _logger.info("Reader canceled: " + e, e);
        }
        _isActive = false;
      }
    });
    thread.start();
  }

  public void stop() {
    if (_logInputStream != null) {
      try {
        _logInputStream.close();
      } catch (final IOException ioe) {
        _logger.warn("Could not close input stream: " + ioe, ioe);
      }
    }
    _stopRequested = true;
  }

  public boolean isRunning() {
    return _isActive;
  }

  public boolean isStopRequested() {
    return _stopRequested;
  }

  public final void addLogEventHandler(final LogEventHandler handler) {
    _listener.add(handler);
  }

  public final void removeLogEventHandler(final LogEventHandler handler) {
    _listener.remove(handler);
  }

  protected final void fireLogEventHandler(final LogEvent event) {
    if (!isFiltered(event)) {
      return;
    }

    for (final LogEventHandler handler : _listener) {
      handler.handle(event);
    }
  }

  protected final void fireSetup() {
    for (final LogEventHandler handler : _listener) {
      handler.initialize();
    }
  }

  protected final void fireTearDown() {
    for (final LogEventHandler handler : _listener) {
      handler.dispose();
    }
  }

  public void readLogFiles() {
    // notify LogEventHandler
    fireSetup();

    final LogEventSource logEventSource = new LogEventSourceImpl(_inputSource.getId());
    try {
      _logInputStream = _inputSource.openStream();
      readLogFileStream(_logInputStream, logEventSource);
    } catch (final IOException e) {
      e.printStackTrace();
    }

    // notify LogEventHandler
    fireTearDown();
  }

  protected abstract void readLogFileStream(InputStream stream, LogEventSource logEventSource);

}
