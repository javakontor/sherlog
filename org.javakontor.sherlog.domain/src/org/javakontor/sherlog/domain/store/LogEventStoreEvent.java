package org.javakontor.sherlog.domain.store;

import java.util.EventObject;

/**
 * An event that is sent to {@link LogEventStoreListener LogEventStoreListeners}
 * when a {@link LogEventStore} has changed (i.e. new entries have been added,
 * filtered etc)
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class LogEventStoreEvent extends EventObject {

  /**
   * 
   */
  private static final long    serialVersionUID = 1L;

  /**
   * Creates a new @link LogEventStoreChangeEvent
   * @param source the {@link LogEventStore} that caused this event
   */
  public LogEventStoreEvent(LogEventStore source) {
    super(source);
  }

  /**
   * Returns the {@link LogEventStore} that is the source of
   * this event
   * @return the source of this event
   */
  public LogEventStore getLogEventStore() {
    return (LogEventStore) source;
  }
  
  @Override
  public boolean equals(Object object) {
    if (object==null) {
      return false;
    }
    
    if (!object.getClass().equals(getClass())) {
      return false;
    }
    
    return getSource().equals(getSource());
  }

  

}
