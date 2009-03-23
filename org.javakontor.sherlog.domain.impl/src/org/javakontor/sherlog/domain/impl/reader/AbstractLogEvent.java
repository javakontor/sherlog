package org.javakontor.sherlog.domain.impl.reader;

import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

import org.javakontor.sherlog.domain.LogEvent;
import org.javakontor.sherlog.util.Assert;

public abstract class AbstractLogEvent implements LogEvent {

  /**
   * the default serialVersionUID
   */
  private static final long                                 serialVersionUID   = 1L;

  /**
   * A simple counter used to generate VM-wide unique identifiers for Log Events
   */
  private static int                                        _instanceCounter   = 0;

  /**
   * The id of this Log Event.
   * 
   * <p>
   * Each Log Event that is instantiated must get a unique id.
   */

  private final int                                         _logId;

  /**
   * The source of this Log Event (e.g. the file it was read from)
   */
  private String                                            _logEventSource;

  /**
   * A map of defined fields that can be set by clients of Log Events.
   * 
   */
  private Map<String, Object>                               _userDefinedFields = null;

  private final CopyOnWriteArraySet<LogEventChangeListener> _logEventChangeListeners;

  public AbstractLogEvent() {
    _logId = _instanceCounter++;
    _logEventChangeListeners = new CopyOnWriteArraySet<LogEventChangeListener>();
  }

  public String getLogEventSource() {
    return _logEventSource;
  }

  public void setLogEventSource(final String logEventSource) {
    _logEventSource = logEventSource;
  }

  public long getIdentifier() {
    return _logId;
  }

  public Object getUserDefinedField(final String fieldName) {
    Assert.notNull("Parameter 'fieldName' must not be null", fieldName);

    final Map<String, Object> userDefinedFields = getUserDefinedFields(false);

    if (userDefinedFields == null) {
      return null;
    }

    return userDefinedFields.get(fieldName);
  }

  public final void setUserDefinedField(final String fieldName, final Object value) {
    Assert.notNull("Parameter 'fieldName' must not be null", fieldName);

    if (value == null) {
      getUserDefinedFields(true).remove(fieldName);
    } else {
      getUserDefinedFields(true).put(fieldName, value);
    }

    // notify listeners about the change
    fireLogEventChange();
  }

  public final String[] getUserDefinedFieldNames() {
    final Map<String, Object> userDefinedFields = getUserDefinedFields(false);

    if (userDefinedFields == null) {
      return new String[0];
    }

    return userDefinedFields.keySet().toArray(new String[0]);
  }

  /**
   * Adds the given {@link LogEventChangeListener} to the list of listeners
   * 
   * <p>
   * <b>Note!</b> This method is not part of the public API and should not be invoked by clients other then the
   * LogStoreComponent
   * </p>
   * 
   * @param logEventChangeListener
   *          the listener to add. Must not be null.
   */
  public void addLogEventChangeListener(final LogEventChangeListener logEventChangeListener) {
    _logEventChangeListeners.add(logEventChangeListener);
  }

  /**
   * Removes the given {@link LogEventChangeListener} from the list of listeners
   * 
   * <p>
   * <b>Note!</b> This method is not part of the public API and should not be invoked by clients other then the
   * LogStoreComponent
   * </p>
   * 
   * @param logEventChangeListener
   *          the listener to remove. Must not be null.
   */
  public void removeLogEventChangeListener(final LogEventChangeListener logEventChangeListener) {
    _logEventChangeListeners.remove(logEventChangeListener);
  }

  /**
   * Compares this LogEvent to the other LogEvent by their timestamps. if the timestamps are equals, their identifiers
   * are compared
   */
  public int compareTo(final LogEvent o) {
    if (equals(o)) {
      return 0;
    }
    final LogEvent secondEvent = o;
    int ret = (int) (getTimeStamp() - secondEvent.getTimeStamp());
    if (ret == 0) {
      ret = (int) (getIdentifier() - secondEvent.getIdentifier());
    }
    return ret;
  }

  protected void fireLogEventChange() {
    final LogEventChangeEvent logChangeEvent = new LogEventChangeEvent(this);
    for (final LogEventChangeListener logEventChangeListener : _logEventChangeListeners) {
      try {
        logEventChangeListener.logEventChange(logChangeEvent);
      } catch (final RuntimeException ex) {
        System.err.println("Error while invoking listener " + logEventChangeListener + ": " + ex);
        ex.printStackTrace();
      }
    }
  }

  /**
   * Returns the map of user defined fields.
   * 
   * <p>
   * If the map is <tt>null</tt>, and create is true, a new map will be created
   * 
   * @param create
   *          if true, a new map is created, if there is no map yet
   * @return
   */
  private synchronized Map<String, Object> getUserDefinedFields(final boolean create) {
    if ((_userDefinedFields == null) && create) {
      _userDefinedFields = new Hashtable<String, Object>();
    }
    return _userDefinedFields;
  }

}
