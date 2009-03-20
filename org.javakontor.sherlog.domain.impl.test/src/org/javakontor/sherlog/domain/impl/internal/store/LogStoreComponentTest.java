package org.javakontor.sherlog.domain.impl.internal.store;

import static org.javakontor.sherlog.domain.DomainTestUtils.generateLogEvent;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

import org.javakontor.sherlog.domain.LogEvent;
import org.javakontor.sherlog.domain.filter.LogEventFilter;
import org.javakontor.sherlog.domain.filter.RegisteredFilterChangeListener;
import org.javakontor.sherlog.domain.store.LogEventStoreEvent;
import org.javakontor.sherlog.domain.store.LogEventStoreListener;

public class LogStoreComponentTest extends TestCase {
  LogStoreComponent _logStoreComponent;

  public LogStoreComponentTest(String name) {
    super(name);
  }

  @Override
  protected void setUp() throws Exception {
    _logStoreComponent = new LogStoreComponent();
  }

  public void test_AddLogEvent() {
    final LogEvent testEvent = generateLogEvent();
    _logStoreComponent.addLogEvent(testEvent);
    assertEquals(1, _logStoreComponent.getLogEventCount());
    assertEquals(testEvent, _logStoreComponent.getLogEvents().get(0));
    assertEquals(1, _logStoreComponent.getFilteredLogEventCount());
  }

  public void test_Filter() {
    final LogEvent testEvent = generateLogEvent();
    _logStoreComponent.addLogEvent(testEvent);

    // add a filter that doesn't match any log events
    SimpleTestLogEventFilter simpleTestLogEventFilter = new SimpleTestLogEventFilter();
    _logStoreComponent.addLogEventFilter(simpleTestLogEventFilter);
    assertEquals(1, _logStoreComponent.getLogEventCount());
    assertEquals(testEvent, _logStoreComponent.getLogEvents().get(0));
    assertEquals(0, _logStoreComponent.getFilteredLogEventCount());

    // test: modifying filter I (anyway, filter matches any log event)
    simpleTestLogEventFilter.setShouldMatch(true);
    assertEquals(1, _logStoreComponent.getLogEventCount());
    assertEquals(testEvent, _logStoreComponent.getLogEvents().get(0));
    assertEquals(1, _logStoreComponent.getFilteredLogEventCount());

    // test: modifying filter II (filter should *not* match anymore)
    simpleTestLogEventFilter.setShouldMatch(false);
    assertEquals(1, _logStoreComponent.getLogEventCount());
    assertEquals(testEvent, _logStoreComponent.getLogEvents().get(0));
    assertEquals(0, _logStoreComponent.getFilteredLogEventCount());

    // remove filter
    _logStoreComponent.removeLogEventFilter(simpleTestLogEventFilter);
    assertEquals(1, _logStoreComponent.getLogEventCount());
    assertEquals(testEvent, _logStoreComponent.getLogEvents().get(0));
    assertEquals(1, _logStoreComponent.getFilteredLogEventCount());
  }

  public void test_FilterListener() {

    RegisteredFilterChangeListener listener = mock(RegisteredFilterChangeListener.class);
    LogEventFilter logEventFilter = mock(LogEventFilter.class);

    // add the listener
    _logStoreComponent.addRegisteredFilterChangeListener(listener);

    // add a filter
    _logStoreComponent.addLogEventFilter(logEventFilter);

    // adding a filter must lead to a 'filterAdded' invocation, 'filterRemoved' should not be called
    verify(listener).filterAdded(logEventFilter);
    verify(listener, never()).filterRemoved(any(LogEventFilter.class));

    // remove the filter. make sure we've got a 'filterRemoved', but not a 'filterAdded'
    _logStoreComponent.removeLogEventFilter(logEventFilter);
    verify(listener, times(1)).filterAdded(logEventFilter);
    verify(listener, times(1)).filterRemoved(logEventFilter);
  }

  public void test_LogStoreListener_AddEvents() {
    _logStoreComponent.addLogEvent(generateLogEvent());
    // add a listener
    LogEventStoreListener listener = mock(LogEventStoreListener.class);
    _logStoreComponent.addLogStoreListener(listener);

    // add some events; should lead to logEventStoreChange invocation
    List<LogEvent> newEvents = new LinkedList<LogEvent>();
    newEvents.add(generateLogEvent());
    newEvents.add(generateLogEvent());
    ((LogEventMock) newEvents.get(1)).setCategory("new.category");
    _logStoreComponent.addLogEvents(newEvents);

    LogEventStoreEvent expectedEvent = new LogEventStoreEvent(_logStoreComponent);

    // after adding the events, the listener should have been invoked
    // exactly once: with the logEventStoreChanged method.
    verify(listener).logEventStoreChanged(expectedEvent);

    // add one more event
    _logStoreComponent.addLogEvent(generateLogEvent());
    verify(listener, times(2)).logEventStoreChanged(expectedEvent);

    // remove listener
    _logStoreComponent.removeLogStoreListener(listener);
    // add an event
    _logStoreComponent.addLogEvent(generateLogEvent());
    // listener should not be called again since it's de-registered
    verify(listener, times(2)).logEventStoreChanged(expectedEvent);

  }

  public void test_LogStoreListener_AddFilter() {
    // add an event to store
    _logStoreComponent.addLogEvent(generateLogEvent());

    // add a listener
    LogEventStoreListener listener = mock(LogEventStoreListener.class);
    _logStoreComponent.addLogStoreListener(listener);

    _logStoreComponent.addLogEventFilter(mock(LogEventFilter.class));
    LogEventStoreEvent expectedEvent = new LogEventStoreEvent(_logStoreComponent);

    // adding a filter should lead to an add-event
    verify(listener).logEventStoreChanged(expectedEvent);
  }

  public void test_LogStoreListener_reset() {
    // add a listener
    LogEventStoreListener listener = mock(LogEventStoreListener.class);
    _logStoreComponent.addLogStoreListener(listener);

    // reset the store
    _logStoreComponent.reset();

    // reset should lead to an event
    verify(listener).logEventStoreChanged(new LogEventStoreEvent(_logStoreComponent));

  }

}
