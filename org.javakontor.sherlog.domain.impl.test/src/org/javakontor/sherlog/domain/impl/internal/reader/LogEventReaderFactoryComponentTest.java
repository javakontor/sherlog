package org.javakontor.sherlog.domain.impl.internal.reader;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

import java.net.URL;

import org.javakontor.sherlog.domain.impl.reader.ObjectLogEventProvider;
import org.javakontor.sherlog.domain.impl.reader.TextLogEventProvider;
import org.javakontor.sherlog.domain.reader.LogEventFlavour;
import org.javakontor.sherlog.domain.reader.LogEventReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.ComponentContext;

@RunWith(MockitoJUnitRunner.class)
public class LogEventReaderFactoryComponentTest {

  @Mock
  private ComponentContext               _componentContextMock;

  private LogEventReaderFactoryComponent _component;

  @Before
  public void setup() {
    _component = new LogEventReaderFactoryComponent();
    _component.activate(_componentContextMock);
  }

  @Test
  public void getSupportedLogEventFlavours_ShouldReturnEmptyArrayWhenNoProvidersHaveBeenRegistered() {
    LogEventFlavour[] supportedLogEventFlavours = _component.getSupportedLogEventFlavours();
    assertThat(supportedLogEventFlavours, is(emptyArray()));

  }

  @Test
  public void getSupportedLogEventFlavours_ShouldReturnOneFlavourForEachRegisteredLogEventProvider() {
    _component.setObjectLogEventProvider(newObjectLogEventProviderServiceReference("log4j-binary"));
    _component.setObjectLogEventProvider(newObjectLogEventProviderServiceReference("log4j.old-binary"));
    _component.setTextLogEventProvider(newTextLogEventProviderServiceReference("log4j.text"));

    LogEventFlavour[] supportedLogEventFlavours = _component.getSupportedLogEventFlavours();
    assertThat(
        supportedLogEventFlavours,
        arrayContainingInAnyOrder(aBinaryFlavour("log4j-binary"), aBinaryFlavour("log4j.old-binary"),
            aTextFlavour("log4j.text")));
    System.out.println(supportedLogEventFlavours);
  }

  @Test
  public void getLogEventReader_ShouldReturnObjectLogEventReaderMatchingSpecifiedFlavour() throws Exception {
    ServiceReference binaryReference = newObjectLogEventProviderServiceReference("log4j.binary");
    _component.setObjectLogEventProvider(binaryReference);
    ServiceReference textReference = newTextLogEventProviderServiceReference("log4j.text");
    _component.setTextLogEventProvider(textReference);

    LogEventReader logEventReader = _component
        .getLogEventReader(new URL("file:/a.log"), aBinaryFlavour("log4j.binary"));
    assertThat(logEventReader, is(notNullValue()));

    verify(_componentContextMock).locateService("objectLogEventProvider", binaryReference);
    verify(_componentContextMock, never()).locateService(eq("textLogEventProvider"),
        Mockito.any(ServiceReference.class));
  }

  @Test
  public void getLogEventReader_ShouldReturnTextLogEventReaderMatchingSpecifiedFlavour() throws Exception {
    ServiceReference binaryReference = newObjectLogEventProviderServiceReference("log4j.binary");
    _component.setObjectLogEventProvider(binaryReference);
    ServiceReference textReference = newTextLogEventProviderServiceReference("log4j.text");
    _component.setTextLogEventProvider(textReference);

    LogEventReader logEventReader = _component.getLogEventReader(new URL("file:/a.log"), aTextFlavour("log4j.text"));
    assertThat(logEventReader, is(notNullValue()));

    verify(_componentContextMock).locateService("textLogEventProvider", textReference);
    verify(_componentContextMock, never()).locateService(eq("objectLogEventProvider"),
        Mockito.any(ServiceReference.class));
  }

  static LogEventFlavour aBinaryFlavour(String description) {
    return new LogEventFlavourImpl(description, LogEventFlavour.BINARY);
  }

  static LogEventFlavour aTextFlavour(String description) {
    return new LogEventFlavourImpl(description, LogEventFlavour.TEXT);
  }

  protected ServiceReference newObjectLogEventProviderServiceReference(String flavour) {
    ServiceReference sr = mock(ServiceReference.class);
    given(sr.getProperty("logevent.flavour")).willReturn(flavour);
    ObjectLogEventProvider objectLogEventProviderMock = mock(ObjectLogEventProvider.class);
    given(_componentContextMock.locateService("objectLogEventProvider", sr)).willReturn(objectLogEventProviderMock);
    return sr;
  }

  protected ServiceReference newTextLogEventProviderServiceReference(String flavour) {
    ServiceReference sr = mock(ServiceReference.class);
    given(sr.getProperty("logevent.flavour")).willReturn(flavour);
    TextLogEventProvider textLogEventProviderMock = mock(TextLogEventProvider.class);
    given(_componentContextMock.locateService("objectLogEventProvider", sr)).willReturn(textLogEventProviderMock);
    return sr;
  }

}
