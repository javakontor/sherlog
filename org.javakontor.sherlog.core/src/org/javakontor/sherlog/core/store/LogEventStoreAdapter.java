package org.javakontor.sherlog.core.store;

/**
 * <p>
 * An abstract adapter class for receiving log event store events. The methods in this class are empty. This class
 * exists as convenience for creating listener objects.
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 * 
 */
public class LogEventStoreAdapter implements LogEventStoreListener {

  /*
   * (non-Javadoc)
   * 
   * @seeorg.javakontor.sherlog.core.store.LogEventStoreListener#logEventsAdded(org.javakontor.sherlog.core.store.
   * LogEventStoreChangeEvent)
   */
  public void logEventsAdded(LogEventStoreChangeEvent event) {
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.javakontor.sherlog.core.store.LogEventStoreListener#logEventStoreReset()
   */
  public void logEventStoreReset() {
  }

}
