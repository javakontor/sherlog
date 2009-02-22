package org.javakontor.sherlog.core.impl.internal.reader;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.javakontor.sherlog.core.impl.reader.ObjectLogEventProvider;
import org.javakontor.sherlog.core.impl.reader.TextLogEventProvider;
import org.javakontor.sherlog.core.reader.LogEventFlavour;
import org.javakontor.sherlog.core.reader.LogEventReader;
import org.javakontor.sherlog.core.reader.LogEventReaderFactory;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;

/**
 * <p>
 * </p>
 *
 * @author Gerd Wuetherich (gerd@gerd-wuetherich.de)
 */
public class LogEventReaderFactoryComponent implements LogEventReaderFactory {

  private static final String          DS_REFERENCE_NAME_OBJECT_LOG_EVENT_PROVIDER = "objectLogEventProvider";

  private static final String          DS_REFERENCE_NAME_TEXT_LOG_EVENT_PROVIDER   = "textLogEventProvider";

  /** the component context */
  private ComponentContext             _componentContext;

  /** the list of {@link ObjectLogEventProvider} references */
  private final List<ServiceReference> _objectLogEventProviderReferences;

  /** the list of {@link TextLogEventProvider} references */
  private final List<ServiceReference> _textLogEventProviderReferences;

  /**
   * <p>
   * Creates a new instance of type {@link LogEventReaderFactoryComponent}.
   * </p>
   */
  public LogEventReaderFactoryComponent() {
    // initialize the lists of LogEventProvider references
    this._objectLogEventProviderReferences = new LinkedList<ServiceReference>();
    this._textLogEventProviderReferences = new LinkedList<ServiceReference>();
  }

  /**
   * @see org.lumberjack.core.reader.LogEventReaderFactory#getSupportedLogEventFlavours()
   */
  public LogEventFlavour[] getSupportedLogEventFlavours() {

    // create result list
    List<LogEventFlavour> result = new LinkedList<LogEventFlavour>();

    // add flavours
    for (ServiceReference reference : this._textLogEventProviderReferences) {
      LogEventFlavourImpl flavour = new LogEventFlavourImpl();
      flavour.setType(LogEventFlavour.TEXT);
      flavour.setDescription((String) reference.getProperty("logevent.flavour"));
      result.add(flavour);
    }
    for (ServiceReference reference : this._objectLogEventProviderReferences) {
      LogEventFlavourImpl flavour = new LogEventFlavourImpl();
      flavour.setType(LogEventFlavour.BINARY);
      flavour.setDescription((String) reference.getProperty("logevent.flavour"));
      result.add(flavour);
    }

    // return the result
    return result.toArray(new LogEventFlavour[0]);
  }

  /**
   * @see org.lumberjack.core.reader.LogEventReaderFactory#getLogEventReader(java .net.URL, java.lang.String)
   */
  public LogEventReader getLogEventReader(final URL url, final LogEventFlavour logEventFlavour) {

    return getLogEventReader(new URL[] { url }, logEventFlavour);
  }

  public LogEventReader getLogEventReader(final URL[] url, final LogEventFlavour logEventFlavour) {

    //
    if (logEventFlavour.getType() == LogEventFlavour.BINARY) {
      ServiceReference serviceReference = getObjectLogEventProviderReference(logEventFlavour);
      return getObjectLogEventReader(url, serviceReference);
    }

    if (logEventFlavour.getType() == LogEventFlavour.TEXT) {
      ServiceReference serviceReference = getTextLogEventProviderReference(logEventFlavour);
      return getTextLogEventReader(url, serviceReference);
    }

    throw new RuntimeException();
  }

  /**
   * @see org.lumberjack.core.reader.LogEventReaderFactory#getLogEventReader(int, java.lang.String)
   */
  public LogEventReader getLogEventReader(final int port, final LogEventFlavour logEventFlavour) {
    throw new UnsupportedOperationException("Not implemented yet");
  }

  /**
   * @param context
   */
  protected void activate(final ComponentContext context) {
    this._componentContext = context;
  }

  /**
   * @param context
   */
  protected void deactivate(final ComponentContext context) {
    this._componentContext = null;
  }

  /**
   * @param logEventProvider
   */
  protected void setTextLogEventProvider(final ServiceReference logEventProvider) {
    this._textLogEventProviderReferences.add(logEventProvider);
    // System.err.println(logEventProvider);
  }

  /**
   * @param logEventProvider
   */
  protected void unsetTextLogEventProvider(final ServiceReference logEventProvider) {
    this._textLogEventProviderReferences.remove(logEventProvider);
  }

  /**
   * @param logEventProvider
   */
  protected void setObjectLogEventProvider(final ServiceReference logEventProvider) {
    this._objectLogEventProviderReferences.add(logEventProvider);
    System.err.println(logEventProvider);
  }

  /**
   * @param logEventProvider
   */
  protected void unsetObjectLogEventProvider(final ServiceReference logEventProvider) {
    this._objectLogEventProviderReferences.remove(logEventProvider);
  }

  /**
   * @param url
   * @param serviceReference
   * @return
   */
  private LogEventReader getTextLogEventReader(final URL[] url, final ServiceReference serviceReference) {

    TextLogEventProvider logEventProvider = (TextLogEventProvider) this._componentContext.locateService(
        DS_REFERENCE_NAME_TEXT_LOG_EVENT_PROVIDER, serviceReference);

    LogEventReader logEventReader = null;

    try {
      logEventReader = new TextInputStreamReader(url, logEventProvider);
    } catch (Exception e) {
      e.printStackTrace();
    }

    // return
    return logEventReader;
  }

  /**
   * @param url
   * @param serviceReference
   * @return
   */
  private LogEventReader getObjectLogEventReader(final URL[] url, final ServiceReference serviceReference) {

    ObjectLogEventProvider logEventProvider = (ObjectLogEventProvider) this._componentContext.locateService(
        DS_REFERENCE_NAME_OBJECT_LOG_EVENT_PROVIDER, serviceReference);

    LogEventReader logEventReader = null;

    try {
      logEventReader = new ObjectInputStreamReader(url, serviceReference.getBundle(), logEventProvider);
    } catch (Exception e) {
      e.printStackTrace();
    }

    // return
    return logEventReader;
  }

  /**
   * <p>
   * Returns the {@link ServiceReference} to the {@link ObjectLogEventProvider} with the given flavour. If no such
   * {@link ObjectLogEventProvider} exists, <code>null</code> will be returned instead.
   * </p>
   *
   * @param logEventFlavour
   *          the flavour
   * @return the {@link ServiceReference} to the {@link ObjectLogEventProvider} with the given flavour. If no such
   *         {@link ObjectLogEventProvider} exists, <code>null</code> will be returned instead.
   */
  private ServiceReference getObjectLogEventProviderReference(final LogEventFlavour logEventFlavour) {

    for (ServiceReference reference : this._objectLogEventProviderReferences) {
      String flavour = (String) reference.getProperty("logevent.flavour");
      if (flavour != null && flavour.equals(logEventFlavour.getDescription())) {
        return reference;
      }
    }
    return null;
  }

  /**
   * <p>
   * Returns the {@link ServiceReference} to the {@link TextLogEventProvider} with the given flavour. If no such
   * {@link TextLogEventProvider} exists, <code>null</code> will be returned instead.
   * </p>
   *
   * @param logEventFlavour
   *          the flavour
   * @return the {@link ServiceReference} to the {@link TextLogEventProvider} with the given flavour. If no such
   *         {@link TextLogEventProvider} exists, <code>null</code> will be returned instead.
   */
  private ServiceReference getTextLogEventProviderReference(final LogEventFlavour logEventFlavour) {

    for (ServiceReference reference : this._textLogEventProviderReferences) {
      String flavour = (String) reference.getProperty("logevent.flavour");
      if (flavour != null && flavour.equals(logEventFlavour.getDescription())) {
        return reference;
      }
    }
    return null;
  }
}
