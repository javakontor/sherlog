package org.javakontor.sherlog.domain.impl.internal.reader;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.ObjectInputStream;
import java.util.concurrent.CancellationException;

import org.javakontor.sherlog.domain.LogEventSource;
import org.javakontor.sherlog.domain.impl.reader.AbstractLogEvent;
import org.javakontor.sherlog.domain.impl.reader.ObjectLogEventProvider;
import org.javakontor.sherlog.domain.reader.LogEventReaderInputSource;
import org.osgi.framework.Bundle;

public class ObjectInputStreamReader extends AbstractLogEventReader {

	/** the bundle to load classes from */
	private final Bundle _bundle;

	/** the ObjectLogEventProvider */
	private final ObjectLogEventProvider _logEventProvider;

	/**
	 * <p>
	 * Creates a new instance of type {@link ObjectInputStreamReader}.
	 * </p>
	 * 
	 * @param inputSource
	 *            the {@link LogEventReaderInputSource} that provides the
	 *            InputStream to read the LogEvents from
	 * @param bundle
	 *            the bundle to load classes from
	 * @param logEventProvider
	 *            the {@link ObjectLogEventProvider}
	 */
	public ObjectInputStreamReader(final LogEventReaderInputSource inputSource,
			final Bundle bundle, final ObjectLogEventProvider logEventProvider) {
		super(inputSource);

		_bundle = bundle;

		_logEventProvider = logEventProvider;
	}

	@Override
	public void readLogFileStream(final InputStream in,
			final LogEventSource logEventSource) {

		ObjectInputStream ois = null;

		try {
			ois = new BundleClassLoaderObjectInputStream(_bundle, in);
			while (true) {
				if (isStopRequested()) {
					throw new CancellationException();
				} else {
					final Object event = ois.readObject();
					final AbstractLogEvent logEvent = _logEventProvider
							.wrapLogEvent(event);
					logEvent.setLogEventSource(logEventSource);
					fireLogEventHandler(logEvent);
				}
			}

		} catch (final FileNotFoundException e) {
			// throw new CorruptedLogfileException(logfileID, e);
			e.printStackTrace();
		} catch (final EOFException e) {
			// done
		} catch (final InterruptedIOException e) {
			// throw e;
			e.printStackTrace();
		} catch (final IOException e) {
			// throw new CorruptedLogfileException(logfileID, e);
			e.printStackTrace();
		} catch (final ClassNotFoundException e) {
			// throw new CorruptedLogfileException(logfileID, e);
			e.printStackTrace();
		} finally {
			try {
				ois.close();
				ois = null;
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
	}
}
