package org.javakontor.sherlog.core.logeventflavour.l4jbinary;

import java.util.Date;

import org.apache.log4j.Level;
import org.apache.log4j.spi.LoggingEvent;
import org.javakontor.sherlog.core.LogLevel;
import org.javakontor.sherlog.core.impl.reader.AbstractLogEvent;

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
class L4JBinaryLogEvent extends AbstractLogEvent {

	/** - */
	static final long serialVersionUID = 1L;

	/** - */
	LoggingEvent myEvent;

	/** - */
	String calculatedThreadName;

	public L4JBinaryLogEvent(final Object in) {
		super();
		final LoggingEvent orgL4JEvent = (LoggingEvent) in;
		this.myEvent = orgL4JEvent;
	}

	public long getTimeStamp() {
		return this.myEvent.timeStamp;
	}

	public String getRenderedMessage() {
		return this.myEvent.getRenderedMessage() != null ? this.myEvent
				.getRenderedMessage() : "";
	}

	public String getCategory() {
		return this.myEvent.getLoggerName();
	}

	public LogLevel getLogLevel() {
		// if (myEvent.priority.equals(Priority.ERROR))return LogLevel.ERROR;
		// if (myEvent.priority.equals(Priority.WARN))return LogLevel.WARN;
		// if (myEvent.priority.equals(Priority.INFO))return LogLevel.INFO;
		// if (myEvent.priority.equals(Priority.FATAL))return LogLevel.FATAL;
		// if (myEvent.priority.equals(Priority.DEBUG))return LogLevel.DEBUG;

		if (this.myEvent.getLevel().equals(Level.ERROR)) {
			return LogLevel.ERROR;
		}
		if (this.myEvent.getLevel().equals(Level.WARN)) {
			return LogLevel.WARN;
		}
		if (this.myEvent.getLevel().equals(Level.INFO)) {
			return LogLevel.INFO;
		}
		if (this.myEvent.getLevel().equals(Level.FATAL)) {
			return LogLevel.FATAL;
		}
		if (this.myEvent.getLevel().equals(Level.DEBUG)) {
			return LogLevel.DEBUG;
		}
		return LogLevel.UNDEF;
	}

	public String getThreadName() {
		return this.myEvent.getThreadName();
	}

	public String getMessage() {
		return this.myEvent.getRenderedMessage();
	}

	public Object getThrowableInformation() {
		return this.myEvent.getThrowableInformation();
	}

	public Object getNDC() {
		return this.myEvent.getNDC();
	}

	public String[] getThrowableStrRep() {
		return this.myEvent.getThrowableStrRep();
	}

	@Override
	public boolean equals(final Object o) {
		return this.myEvent.equals(((L4JBinaryLogEvent) o).myEvent);
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
		final StringBuffer retVal = new StringBuffer("L4JEvent2 [");
		retVal.append("TS:" + new Date(this.myEvent.getTimeStamp()));
		retVal.append(";Msg:" + this.myEvent.getRenderedMessage());
		retVal.append("]");
		return retVal.toString();
	}
}
