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

public abstract class AbstractLogEventReader extends AbstractFilterable implements LogEventReader {

  private final List<LogEventHandler>     _listener = new ArrayList<LogEventHandler>();

  private boolean                         _isActive;

  private boolean                         _stopRequested;

  private final LogEventReaderInputSource _inputSource;

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
          e.printStackTrace();
        }
        _isActive = false;
      }
    });
    thread.start();
  }

  public void stop() {
    _stopRequested = true;
  }

  public boolean isActive() {
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

  // public boolean corruptedLogfileInfo() {
  // return this.infoCorruptedLogfile;
  // }

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
      readLogFileStream(_inputSource.openStream(), logEventSource);
    } catch (final IOException e) {
      e.printStackTrace();
    }

    // notify LogEventHandler
    fireTearDown();
  }

  protected abstract void readLogFileStream(InputStream stream, LogEventSource logEventSource);

}
