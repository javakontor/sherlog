package org.javakontor.sherlog.domain.impl.internal.reader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class UrlLogEventReaderInputSource implements LogEventReaderInputSource {

	private final URL _url;

	public UrlLogEventReaderInputSource(final URL url) {
		super();
		_url = url;
	}

	public InputStream openStream() throws IOException {
		return _url.openStream();
	}

	public String getId() {
		return _url.toExternalForm();
	}

	@Override
	public String toString() {
		return "UrlLogEventReaderInputSource [_url=" + _url + "]";
	}

}
