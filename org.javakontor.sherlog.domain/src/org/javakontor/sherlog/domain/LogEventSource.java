package org.javakontor.sherlog.domain;

/**
 * Represents the source of a {@link LogEvent}.
 * 
 * <p>
 * A Source can be for example a file on the filesystem or an URL to a network path.
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public interface LogEventSource {
  /**
   * Returns this source as a human-readable title
   * 
   * @return this source as a human-readable title
   */
  public String getSource();
}
