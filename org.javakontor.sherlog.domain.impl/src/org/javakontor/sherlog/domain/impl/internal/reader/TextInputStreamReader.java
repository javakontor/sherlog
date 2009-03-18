package org.javakontor.sherlog.domain.impl.internal.reader;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.CancellationException;

import org.javakontor.sherlog.domain.impl.reader.AbstractLogEvent;
import org.javakontor.sherlog.domain.impl.reader.TextLogEventProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextInputStreamReader extends AbstractURLLogEventReader {

  private final Logger               _logger  = LoggerFactory.getLogger(getClass());

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

    // Scanner scanner = new Scanner(new BufferedInputStream(in));

    long start = System.currentTimeMillis();

    BufferedReader scanner = new BufferedReader(new InputStreamReader(new BufferedInputStream(in)));
    String line = null;
    try {
      // first use a Scanner to get each line
      // while (scanner.hasNextLine()) {
      while ((line = scanner.readLine()) != null) {
        if (isStopRequested()) {
          throw new CancellationException();
        } else {
          // processLine(scanner.nextLine(), logEventSource);
          processLine(line, logEventSource);

        }

      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    } finally {
      // ensure the underlying stream is always closed
      // scanner.close();
    }

    if (_textLogEventBuilder.length() > 0) {
      createLogEvent(_textLogEventBuilder.toString(), logEventSource);
    }

    _logger.debug("Reading log events took " + (System.currentTimeMillis() - start) + "ms");

  }

  private void processLine(final String aLine, final String logEventSource) {

    if (aLine.startsWith(this.startTag)) {
      String string = this._textLogEventBuilder.toString();
      if (string.length() < 1) {
        this._textLogEventBuilder.append(aLine);
        return;
      }
      createLogEvent(string, logEventSource);
      // AbstractLogEvent logEvent = this._logEventProvider.wrapLogEvent(string);
      // logEvent.setLogEventSource(logEventSource);
      // fireLogEventHandler(logEvent);

      this._textLogEventBuilder = new StringBuilder();
    } else {
      this._textLogEventBuilder.append(System.getProperty("line.separator"));
    }
    this._textLogEventBuilder.append(aLine);
  }

  private void createLogEvent(String line, String logEventSource) {
    AbstractLogEvent logEvent = this._logEventProvider.wrapLogEvent(line);
    logEvent.setLogEventSource(logEventSource);
    fireLogEventHandler(logEvent);
  }
}
