package org.javakontor.sherlog.domain.impl.internal.reader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;

import org.javakontor.sherlog.domain.LogEvent;
import org.javakontor.sherlog.domain.LogEventSource;
import org.javakontor.sherlog.domain.impl.filter.AbstractFilterable;
import org.javakontor.sherlog.domain.reader.LogEventHandler;
import org.javakontor.sherlog.domain.reader.LogEventReader;

public abstract class AbstractURLLogEventReader extends AbstractFilterable implements LogEventReader {

  private final List<LogEventHandler> _listener = new ArrayList<LogEventHandler>();

  private boolean                     _isActive;

  private boolean                     _stopRequested;

  // private final boolean infoCorruptedLogfile = true;

  private final URL[]                 _urls;

  /**
   * @param urls
   */
  public AbstractURLLogEventReader(final URL[] urls) {
    _urls = urls;
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

    for (final URL url : _urls) {

      final LogEventSource logEventSource = new LogEventSourceImpl(url.toExternalForm());
      try {
        readLogFileStream(url.openStream(), logEventSource);
      } catch (final IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    // notify LogEventHandler
    fireTearDown();
  }

  protected abstract void readLogFileStream(InputStream stream, LogEventSource logEventSource);

}
