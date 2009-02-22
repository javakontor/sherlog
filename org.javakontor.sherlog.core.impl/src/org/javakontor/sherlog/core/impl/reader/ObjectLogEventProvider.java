package org.javakontor.sherlog.core.impl.reader;

public interface ObjectLogEventProvider {

	public AbstractLogEvent wrapLogEvent(Object object);
}
