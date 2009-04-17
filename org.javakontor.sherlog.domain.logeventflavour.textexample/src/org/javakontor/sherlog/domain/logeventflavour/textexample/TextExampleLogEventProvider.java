package org.javakontor.sherlog.domain.logeventflavour.textexample;

import org.javakontor.sherlog.domain.impl.reader.AbstractLogEvent;
import org.javakontor.sherlog.domain.impl.reader.TextLogEventProvider;

public class TextExampleLogEventProvider implements TextLogEventProvider {

  public AbstractLogEvent wrapLogEvent(Object object) {
    return new TextExampleLogEvent(object);
  }
}
