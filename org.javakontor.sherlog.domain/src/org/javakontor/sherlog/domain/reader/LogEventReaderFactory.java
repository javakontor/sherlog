package org.javakontor.sherlog.domain.reader;

import java.net.URL;

/**
 * <p>
 * The {@link LogEventReaderFactory} can be used to create {@link LogEventReader}.
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface LogEventReaderFactory {

  /**
   * <p>
   * Returns all supported {@link LogEventFlavour LogEventFlavours}.
   * </p>
   * 
   * @return all supported {@link LogEventFlavour LogEventFlavours}.
   */
  public LogEventFlavour[] getSupportedLogEventFlavours();

  /**
   * <p>
   * Returns a new {@link LogEventReader} for the specified {@link LogEventFlavour} and the specified URL.
   * </p>
   * 
   * @param url
   *          the URL
   * @param logEventFlavour
   *          the log event flavour
   * @return
   */
  public LogEventReader getLogEventReader(URL url, LogEventFlavour logEventFlavour);

  /**
   * <p>
   * </p>
   * 
   * @param port
   * @param logEventFlavour
   * @return
   */
  public LogEventReader getLogEventReader(int port, LogEventFlavour logEventFlavour);
}
