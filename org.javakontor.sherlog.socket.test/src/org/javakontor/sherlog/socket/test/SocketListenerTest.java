package org.javakontor.sherlog.socket.test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;
import org.javakontor.sherlog.domain.LogEvent;
import org.javakontor.sherlog.domain.impl.internal.reader.ObjectInputStreamReader;
import org.javakontor.sherlog.domain.logeventflavour.l4jbinary.L4JBinaryLogEventProvider;
import org.javakontor.sherlog.domain.reader.LogEventFlavour;
import org.javakontor.sherlog.domain.reader.LogEventReader;
import org.javakontor.sherlog.domain.reader.LogEventReaderFactory;
import org.javakontor.sherlog.domain.reader.LogEventReaderInputSource;
import org.javakontor.sherlog.domain.store.ModifiableLogEventStore;
import org.javakontor.sherlog.socket.SocketListener;
import org.javakontor.sherlog.socket.SocketListenerStateChangeListener;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.osgi.framework.Bundle;

@RunWith(MockitoJUnitRunner.class)
public class SocketListenerTest {

  LogEventProducer                _logEventProducer = new LogEventProducer(4445);

  @Mock
  private ModifiableLogEventStore _logEventStore;

  private SocketListener          _socketListener;

  @BeforeClass
  public static void setupAppender() {
    ConsoleAppender newAppender = new ConsoleAppender(new SimpleLayout(), ConsoleAppender.SYSTEM_OUT);
    Logger.getRootLogger().addAppender(newAppender);
  }

  @Before
  public void before() throws Exception {
    _socketListener = new SocketListener(4445, mock(LogEventFlavour.class), _logEventStore,
        new LogEventReaderFactoryStub());
  }

  @After
  public void after() {
    if (_socketListener != null) {
      _socketListener.shutdown();
    }
    _logEventProducer.dispose();
  }

  @Test
  public void receivedLogEventsShouldBeAddedToLogEventStore() throws Exception {
    System.out.println("socketListener new!");

    _socketListener.start();

    _logEventProducer.produce(5);
    System.out.println("produced");

    // Some time to process remaining log events
    Thread.sleep(750);

    verify(_logEventStore, times(5)).addLogEvent(any(LogEvent.class));
  }

  @Test
  public void afterClosingSocketListenerNoEventsShouldBeAddedToLogEventStore() throws Exception {
    _socketListener.start();

    _logEventProducer.produce(5);
    // Some time to process remaining log events
    Thread.sleep(750);

    _socketListener.shutdown();
    _socketListener = null;

    Thread.sleep(750);

    _logEventProducer.produce(5);

    Thread.sleep(750);

    verify(_logEventStore, times(5)).addLogEvent(any(LogEvent.class));

  }

  @Test
  public void getActiveConnectionsShouldReturnsNumberOfActiveConnections() throws Exception {
    _socketListener.start();

    _logEventProducer.connect();

    LogEventProducer anotherProducer = new LogEventProducer(4445);
    anotherProducer.connect();

    Thread.sleep(750);
    assertThat(_socketListener.getActiveConnections(), is(2));

    anotherProducer.dispose();

    Thread.sleep(750);
    assertThat(_socketListener.getActiveConnections(), is(1));
  }

  @Test
  public void afterShutdownActiveConnectionsShouldBeZero() throws Exception {
    _socketListener.start();

    // Connect
    _logEventProducer.connect();

    // Wait
    Thread.sleep(750);
    assertThat(_socketListener.getActiveConnections(), is(1));

    // Shutdown SocketListener
    _socketListener.shutdown();
    Thread.sleep(750);
    assertThat(_socketListener.getActiveConnections(), is(0));
  }

  @Test
  public void stateChangeistenerShouldBeNotifiedAfterNewConnectionEstablished() throws Exception {
    SocketListenerStateChangeListener listener = mock(SocketListenerStateChangeListener.class);
    _socketListener.addSocketListenerStateChangeListener(listener);
    _socketListener.start();

    // Connect
    _logEventProducer.connect();

    // Wait
    Thread.sleep(750);

    verify(listener).stateChanged();
  }

  @Test
  public void stateChangeistenerShouldBeNotifiedAfterConnectionClosed() throws Exception {
    SocketListenerStateChangeListener listener = mock(SocketListenerStateChangeListener.class);
    _socketListener.addSocketListenerStateChangeListener(listener);
    _socketListener.start();

    // Connect
    _logEventProducer.connect();
    _logEventProducer.dispose();

    // Wait
    Thread.sleep(750);

    verify(listener, times(2)).stateChanged();
  }

  class LogEventReaderFactoryStub implements LogEventReaderFactory {

    public LogEventFlavour[] getSupportedLogEventFlavours() {
      throw new UnsupportedOperationException("mock");
    }

    public LogEventReader getLogEventReader(LogEventReaderInputSource inputSource, LogEventFlavour logEventFlavour) {
      try {
        Bundle bundleMock = mock(Bundle.class);
        when(bundleMock.loadClass(anyString())).thenThrow(new ClassNotFoundException());
        return new ObjectInputStreamReader(inputSource, bundleMock, new L4JBinaryLogEventProvider());
      } catch (Exception ex) {
        ex.printStackTrace();
        fail(ex.toString());
        return null;
      }
    }
  }

}
