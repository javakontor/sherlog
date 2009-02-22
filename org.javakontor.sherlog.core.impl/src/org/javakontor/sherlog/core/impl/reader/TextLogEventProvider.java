package org.javakontor.sherlog.core.impl.reader;

public interface TextLogEventProvider {

	public AbstractLogEvent wrapLogEvent(Object object);
}
