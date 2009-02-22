package org.javakontor.sherlog.core.impl.internal.reader;

import java.io.EOFException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InterruptedIOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.concurrent.CancellationException;

import org.javakontor.sherlog.core.LogEvent;
import org.javakontor.sherlog.core.impl.reader.AbstractLogEvent;
import org.javakontor.sherlog.core.impl.reader.ObjectLogEventProvider;
import org.osgi.framework.Bundle;

public class ObjectInputStreamReader extends AbstractURLLogEventReader {

	/** the bundle to load classes from */
	private final Bundle _bundle;

	/** the ObjectLogEventProvider */
	private final ObjectLogEventProvider _logEventProvider;

	/**
	 * <p>
	 * Creates a new instance of type {@link ObjectInputStreamReader}.
	 * </p>
	 * 
	 * @param urls
	 *            the log file URLs
	 * @param bundle
	 *            the bundle to load classes from
	 * @param logEventProvider
	 *            the {@link ObjectLogEventProvider}
	 */
	public ObjectInputStreamReader(final URL[] urls, final Bundle bundle,
			final ObjectLogEventProvider logEventProvider) {
		super(urls);

		this._bundle = bundle;

		this._logEventProvider = logEventProvider;
	}

	@Override
	public void readLogFileStream(final InputStream in,
			final LogSourceIdentifier logSourceIdentifier) {

		ObjectInputStream ois = null;

		try {
			ois = new BundleClassLoaderObjectInputStream(this._bundle, in);
			while (true) {
				if (isStopRequested()) {
					throw new CancellationException();
				} else {
					final Object event = ois.readObject();
					LogEvent logEvent = this._logEventProvider
							.wrapLogEvent(event);
					if (logEvent instanceof AbstractLogEvent) {
						((AbstractLogEvent) logEvent)
								.setDatenHerkunft(logSourceIdentifier);
					}
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
