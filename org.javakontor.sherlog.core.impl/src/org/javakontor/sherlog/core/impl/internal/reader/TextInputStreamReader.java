package org.javakontor.sherlog.core.impl.internal.reader;

import java.io.InputStream;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.CancellationException;

import org.javakontor.sherlog.core.impl.reader.AbstractLogEvent;
import org.javakontor.sherlog.core.impl.reader.TextLogEventProvider;

public class TextInputStreamReader extends AbstractURLLogEventReader {

  private final TextLogEventProvider _logEventProvider;

  private final String               startTag = "|";

  private StringBuilder              _textLogEventBuilder;

  /**
   * <p>
   * Creates a new instance of type {@link TextInputStreamReader}.
   * </p>
   * 
   * @param urls
   *          the log file URLs
   * @param bundle
   *          the bundle to load classes from
   * @param logEventProvider
   *          the {@link TextInputStreamReader}
   */
  public TextInputStreamReader(final URL[] urls, final TextLogEventProvider logEventProvider) {
    super(urls);

    this._logEventProvider = logEventProvider;

    this._textLogEventBuilder = new StringBuilder();
  }

  @Override
  public void readLogFileStream(final InputStream in, final String logEventSource) {

    Scanner scanner = new Scanner(in);
    try {
      // first use a Scanner to get each line
      while (scanner.hasNextLine()) {
        if (isStopRequested()) {
          throw new CancellationException();
        } else {
          processLine(scanner.nextLine(), logEventSource);

        }

      }
    } finally {
      // ensure the underlying stream is always closed
      scanner.close();
    }

  }

  private void processLine(final String aLine, final String logEventSource) {

    if (aLine.startsWith(this.startTag)) {
      String string = this._textLogEventBuilder.toString();
      if (string.length() < 1) {
        this._textLogEventBuilder.append(aLine);
        return;
      }
      AbstractLogEvent logEvent = this._logEventProvider.wrapLogEvent(string);
      logEvent.setLogEventSource(logEventSource);
      fireLogEventHandler(logEvent);

      this._textLogEventBuilder = new StringBuilder();
    } else {
      this._textLogEventBuilder.append(System.getProperty("line.separator"));
    }
    this._textLogEventBuilder.append(aLine);
  }
}
