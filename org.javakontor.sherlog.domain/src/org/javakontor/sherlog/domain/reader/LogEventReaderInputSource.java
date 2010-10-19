package org.javakontor.sherlog.domain.reader;

import java.io.IOException;
import java.io.InputStream;

/**
 * An abstraction of a source that is used to read log events from.
 * 
 * @author Nils (nils@nilshartmann.net)
 * 
 */
public interface LogEventReaderInputSource {

  /**
   * Opens an input stream that is used to read the LogEvents from
   * 
   * Clients that open the input stream are responsible for closing the stream when it's not needed anylonger
   * 
   * @return
   */
  public InputStream openStream() throws IOException;

  /**
   * Returns a textual identifier of this source that can be used to differentiate LogEvents according to their source
   * 
   * @return
   */
  public String getId();

}
