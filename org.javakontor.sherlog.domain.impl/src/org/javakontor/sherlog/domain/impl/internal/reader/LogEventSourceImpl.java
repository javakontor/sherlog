package org.javakontor.sherlog.domain.impl.internal.reader;

import org.javakontor.sherlog.domain.LogEventSource;
import org.javakontor.sherlog.util.Assert;

/**
 * A default implementation of {@link LogEventSource}
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class LogEventSourceImpl implements LogEventSource {

  /**
   * The source in human-readable form
   */
  private final String _source;

  /**
   * Constructs a new LogEventSourceImpl.
   * 
   * @param source
   *          The actual source of a LogEvent in human-readable format
   */
  public LogEventSourceImpl(final String source) {
    Assert.notNull(source);
    _source = source;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.javakontor.sherlog.domain.LogEventSource#getSource()
   */
  public String getSource() {
    return _source;
  }

}
