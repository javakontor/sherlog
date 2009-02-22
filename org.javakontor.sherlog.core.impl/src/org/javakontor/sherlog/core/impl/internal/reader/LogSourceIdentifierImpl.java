package org.javakontor.sherlog.core.impl.internal.reader;

import java.io.Serializable;
import java.util.HashMap;


public class LogSourceIdentifierImpl implements Serializable,
		LogSourceIdentifier {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final String info;
	transient static private HashMap<String, LogSourceIdentifierImpl> _flyWeigth = new HashMap<String, LogSourceIdentifierImpl>();

	private synchronized static HashMap<String, LogSourceIdentifierImpl> flyWeigth() {
		if (_flyWeigth == null) {
			_flyWeigth = new HashMap<String, LogSourceIdentifierImpl>();
		}
		return _flyWeigth;
	}

	public static LogSourceIdentifierImpl getDatenherkuft(final String in) {
		if (!flyWeigth().containsKey(in)) {
			flyWeigth().put(in, new LogSourceIdentifierImpl(in));
		}
		return flyWeigth().get(in);
	}

	public LogSourceIdentifierImpl(final String info) {
		super();
		this.info = info;
	}

	@Override
	public String toString() {
		return this.info;
	}

}
