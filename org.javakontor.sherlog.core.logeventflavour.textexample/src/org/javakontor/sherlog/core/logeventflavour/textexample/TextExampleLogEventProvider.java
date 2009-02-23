package org.javakontor.sherlog.core.logeventflavour.textexample;

import org.javakontor.sherlog.core.impl.reader.AbstractLogEvent;
import org.javakontor.sherlog.core.impl.reader.TextLogEventProvider;

public class TextExampleLogEventProvider implements TextLogEventProvider {

  public AbstractLogEvent wrapLogEvent(Object object) {
    return new TextExampleLogEvent(object);
  }
}
