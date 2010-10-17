package org.javakontor.sherlog.domain.impl.internal.reader;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.CancellationException;

import org.javakontor.sherlog.domain.LogEventSource;
import org.javakontor.sherlog.domain.impl.reader.AbstractLogEvent;
import org.javakontor.sherlog.domain.impl.reader.TextLogEventProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TextInputStreamReader extends AbstractLogEventReader {

	private final Logger _logger = LoggerFactory.getLogger(getClass());

	private final TextLogEventProvider _logEventProvider;

	private final String startTag = "|";

	private StringBuilder _textLogEventBuilder;

	/**
	 * <p>
	 * Creates a new instance of type {@link TextInputStreamReader}.
	 * </p>
	 * 
	 * @param inputSource
	 *            A {@link LogEventReaderInputSource} to read the LogEvents from
	 * @param bundle
	 *            the bundle to load classes from
	 * @param logEventProvider
	 *            the {@link TextInputStreamReader}
	 */
	public TextInputStreamReader(final LogEventReaderInputSource inputSource,
			final TextLogEventProvider logEventProvider) {
		super(inputSource);

		_logEventProvider = logEventProvider;

		_textLogEventBuilder = new StringBuilder();
	}

	@Override
	public void readLogFileStream(final InputStream in,
			final LogEventSource logEventSource) {

		// Scanner scanner = new Scanner(new BufferedInputStream(in));

		final long start = System.currentTimeMillis();

		final BufferedReader scanner = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(in)));
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
		} catch (final IOException ioe) {
			ioe.printStackTrace();
		} finally {
			// ensure the underlying stream is always closed
			// scanner.close();
		}

		if (_textLogEventBuilder.length() > 0) {
			createLogEvent(_textLogEventBuilder.toString(), logEventSource);
		}

		_logger.debug("Reading log events took "
				+ (System.currentTimeMillis() - start) + "ms");

	}

	private void processLine(final String aLine,
			final LogEventSource logEventSource) {

		if (aLine.startsWith(startTag)) {
			final String string = _textLogEventBuilder.toString();
			if (string.length() < 1) {
				_textLogEventBuilder.append(aLine);
				return;
			}
			createLogEvent(string, logEventSource);
			// AbstractLogEvent logEvent =
			// this._logEventProvider.wrapLogEvent(string);
			// logEvent.setLogEventSource(logEventSource);
			// fireLogEventHandler(logEvent);

			_textLogEventBuilder = new StringBuilder();
		} else {
			_textLogEventBuilder.append(System.getProperty("line.separator"));
		}
		_textLogEventBuilder.append(aLine);
	}

	private void createLogEvent(final String line,
			final LogEventSource logEventSource) {
		final AbstractLogEvent logEvent = _logEventProvider.wrapLogEvent(line);
		logEvent.setLogEventSource(logEventSource);
		fireLogEventHandler(logEvent);
	}
}
