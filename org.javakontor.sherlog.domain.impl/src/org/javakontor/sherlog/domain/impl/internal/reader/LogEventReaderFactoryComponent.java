package org.javakontor.sherlog.domain.impl.internal.reader;

import java.net.URL;
import java.util.LinkedList;
import java.util.List;

import org.javakontor.sherlog.domain.impl.reader.ObjectLogEventProvider;
import org.javakontor.sherlog.domain.impl.reader.TextLogEventProvider;
import org.javakontor.sherlog.domain.reader.LogEventFlavour;
import org.javakontor.sherlog.domain.reader.LogEventReader;
import org.javakontor.sherlog.domain.reader.LogEventReaderFactory;
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
    _objectLogEventProviderReferences = new LinkedList<ServiceReference>();
    _textLogEventProviderReferences = new LinkedList<ServiceReference>();
  }

  /**
   * @see org.lumberjack.core.reader.LogEventReaderFactory#getSupportedLogEventFlavours()
   */
  public LogEventFlavour[] getSupportedLogEventFlavours() {

    // create result list
    final List<LogEventFlavour> result = new LinkedList<LogEventFlavour>();

    // add flavours
    for (final ServiceReference reference : _textLogEventProviderReferences) {
      final String description = (String) reference.getProperty("logevent.flavour");
      final LogEventFlavourImpl flavour = new LogEventFlavourImpl(description, LogEventFlavour.TEXT);
      result.add(flavour);
    }
    for (final ServiceReference reference : _objectLogEventProviderReferences) {
      final String description = (String) reference.getProperty("logevent.flavour");
      final LogEventFlavourImpl flavour = new LogEventFlavourImpl(description, LogEventFlavour.BINARY);
      result.add(flavour);
    }

    // return the result
    return result.toArray(new LogEventFlavour[0]);
  }

  /**
   * @see org.lumberjack.core.reader.LogEventReaderFactory#getLogEventReader(java .net.URL, java.lang.String)
   */
  public LogEventReader getLogEventReader(final URL url, final LogEventFlavour logEventFlavour) {

    //
    if (logEventFlavour.getType() == LogEventFlavour.BINARY) {
      final ServiceReference serviceReference = getObjectLogEventProviderReference(logEventFlavour);
      return getObjectLogEventReader(url, serviceReference);
    }

    if (logEventFlavour.getType() == LogEventFlavour.TEXT) {
      final ServiceReference serviceReference = getTextLogEventProviderReference(logEventFlavour);
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
    _componentContext = context;
  }

  /**
   * @param context
   */
  protected void deactivate(final ComponentContext context) {
    _componentContext = null;
  }

  /**
   * @param logEventProvider
   */
  protected void setTextLogEventProvider(final ServiceReference logEventProvider) {
    _textLogEventProviderReferences.add(logEventProvider);
  }

  /**
   * @param logEventProvider
   */
  protected void unsetTextLogEventProvider(final ServiceReference logEventProvider) {
    _textLogEventProviderReferences.remove(logEventProvider);
  }

  /**
   * @param logEventProvider
   */
  protected void setObjectLogEventProvider(final ServiceReference logEventProvider) {
    _objectLogEventProviderReferences.add(logEventProvider);
  }

  /**
   * @param logEventProvider
   */
  protected void unsetObjectLogEventProvider(final ServiceReference logEventProvider) {
    _objectLogEventProviderReferences.remove(logEventProvider);
  }

  /**
   * @param url
   * @param serviceReference
   * @return
   */
  private LogEventReader getTextLogEventReader(final URL url, final ServiceReference serviceReference) {

    final TextLogEventProvider logEventProvider = (TextLogEventProvider) _componentContext.locateService(
        DS_REFERENCE_NAME_TEXT_LOG_EVENT_PROVIDER, serviceReference);

    LogEventReader logEventReader = null;

    try {
      logEventReader = new TextInputStreamReader(new UrlLogEventReaderInputSource(url), logEventProvider);
    } catch (final Exception e) {
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
  private LogEventReader getObjectLogEventReader(final URL url, final ServiceReference serviceReference) {

    final ObjectLogEventProvider logEventProvider = (ObjectLogEventProvider) _componentContext.locateService(
        DS_REFERENCE_NAME_OBJECT_LOG_EVENT_PROVIDER, serviceReference);

    LogEventReader logEventReader = null;

    try {
      logEventReader = new ObjectInputStreamReader(new UrlLogEventReaderInputSource(url), serviceReference.getBundle(),
          logEventProvider);
    } catch (final Exception e) {
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

    for (final ServiceReference reference : _objectLogEventProviderReferences) {
      final String flavour = (String) reference.getProperty("logevent.flavour");
      if ((flavour != null) && flavour.equals(logEventFlavour.getDescription())) {
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

    for (final ServiceReference reference : _textLogEventProviderReferences) {
      final String flavour = (String) reference.getProperty("logevent.flavour");
      if ((flavour != null) && flavour.equals(logEventFlavour.getDescription())) {
        return reference;
      }
    }
    return null;
  }
}
