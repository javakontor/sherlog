package org.javakontor.sherlog.core.logeventflavour.textexample;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.javakontor.sherlog.core.LogLevel;
import org.javakontor.sherlog.core.impl.reader.AbstractLogEvent;

public class TextExampleLogEvent extends AbstractLogEvent {

	static final long serialVersionUID = 1L;
	transient SimpleDateFormat timeformat = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss,SSS");

	String myEvent;
	transient boolean parsed = false;
	transient Long timestamp;
	transient String catName;
	transient String thrN;
	transient LogLevel logL;
	transient String rMsg;

	public TextExampleLogEvent(final Object in) {
		final String orgL4JEvent = (String) in;
		this.myEvent = orgL4JEvent;
		parseString();
	}

	private void parseString() {
		try {

		  // |timestamp|[Thread]LOGLEVEL| category message
			// System.out.println(myEvent);
			String tmp = this.myEvent;
			int index = tmp.substring(1).indexOf('|');
			final String tsString = tmp.substring(1, index);
			final Date ts = this.timeformat.parse(tsString);
			this.timestamp = new Long(ts.getTime());
			index = tmp.indexOf(']');
			this.thrN = tmp.substring(tmp.indexOf('[') + 1, index);
			tmp = tmp.substring(index + 1).trim();
			index = tmp.indexOf(' ');
			this.logL = LogLevel.valueOf(tmp.substring(0, index));
			tmp = tmp.substring(index).trim();
			index = tmp.indexOf(' ');
			this.catName = tmp.substring(0, index);
			tmp = tmp.substring(index).trim();
			this.rMsg = tmp;
			this.parsed = true;
		} catch (final ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public long getTimeStamp() {
		if (!this.parsed) {
			parseString();
		}
		return this.timestamp.longValue();
	}

	public String getRenderedMessage() {
		if (!this.parsed) {
			parseString();
		}
		return this.rMsg;
	}

	public String getCategory() {
		if (!this.parsed) {
			parseString();
		}
		return this.catName;
	}

	public LogLevel getLogLevel() {
		if (!this.parsed) {
			parseString();
		}
		return this.logL;
	}

	public String getThreadName() {
		if (!this.parsed) {
			parseString();
		}
		return this.thrN;
	}

	public String getMessage() {
		return getRenderedMessage();
	}

	public Object getThrowableInformation() {
		return null;
	}

	public Object getNDC() {
		return null;
	}

	public String[] getThrowableStrRep() {
		return null;
	}

	@Override
	public boolean equals(final Object o) {
		return this.myEvent.equals(((TextExampleLogEvent) o).myEvent);
	}

	@Override
	public int hashCode() {
		return this.myEvent.hashCode();
	}

	public Object getInternalRepraesentation() {
		return this.myEvent;
	}

	@Override
	public String toString() {
		return myEvent;
	}

}
