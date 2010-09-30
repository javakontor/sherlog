package org.javakontor.sherlog.domain.impl.reader;

public interface ObjectLogEventProvider {

	public AbstractLogEvent wrapLogEvent(Object object);
}
