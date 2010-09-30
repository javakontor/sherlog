package org.javakontor.sherlog.domain.impl.reader;

public interface TextLogEventProvider {

	public AbstractLogEvent wrapLogEvent(Object object);
}
