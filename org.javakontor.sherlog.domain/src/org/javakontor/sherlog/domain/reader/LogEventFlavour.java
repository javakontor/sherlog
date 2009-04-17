package org.javakontor.sherlog.domain.reader;

/**
 * <p>
 * Defines a log event flavour.
 * </p>
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface LogEventFlavour {

  /** the constant LOG_EVENT_FLAVOUR_SERVICE_PROPERTY */
  public static final String LOG_EVENT_FLAVOUR_SERVICE_PROPERTY = "logevent.flavour";

  /** binary type */
  public static final int    BINARY                             = 0;

  /** text type */
  public static final int    TEXT                               = 1;

  /**
   * <p>
   * Returns the description of this {@link LogEventFlavour}.
   * </p>
   *
   * @return the description of this {@link LogEventFlavour}.
   */
  public String getDescription();

  /**
   * <p>
   * Returns the type of this {@link LogEventFlavour}.
   * </p>
   *
   * @return the type of this {@link LogEventFlavour}.
   */
  public int getType();
}
