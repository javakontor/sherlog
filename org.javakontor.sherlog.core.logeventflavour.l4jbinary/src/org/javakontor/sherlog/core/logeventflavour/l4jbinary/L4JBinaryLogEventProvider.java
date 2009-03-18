package org.javakontor.sherlog.core.logeventflavour.l4jbinary;

import org.javakontor.sherlog.domain.impl.reader.AbstractLogEvent;
import org.javakontor.sherlog.domain.impl.reader.ObjectLogEventProvider;

/**
 * <p>
 * </p>
 * 
 * @author
 */
public class L4JBinaryLogEventProvider implements ObjectLogEventProvider {

  /**
   * @see org.lumberjack.core.impl.reader.ObjectLogEventProvider#wrapLogEvent(java.lang.Object)
   */
  public AbstractLogEvent wrapLogEvent(Object object) {
    return new L4JBinaryLogEvent(object);
  }
}
