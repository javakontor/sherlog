package org.javakontor.sherlog.domain.impl.internal.store;

import static org.javakontor.sherlog.domain.DomainTestUtils.generateLogEvent;
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

    _logStoreComponent.addRegisteredFilterChangeListener(listener);
    _logStoreComponent.addLogEventFilter(logEventFilter);

    verify(listener).filterAdded(logEventFilter);
    verify(listener, never()).filterRemoved(logEventFilter);

    _logStoreComponent.removeLogEventFilter(logEventFilter);
    verify(listener, times(1)).filterAdded(logEventFilter);
    verify(listener, times(1)).filterRemoved(logEventFilter);
  }

  public void test_LogStoreListener() {
    _logStoreComponent.addLogEvent(generateLogEvent());
    // add a listener
    LogEventStoreListener listener = mock(LogEventStoreListener.class);
    _logStoreComponent.addLogStoreListener(listener);

    List<LogEvent> newEvents = new LinkedList<LogEvent>();
    final LogEventMock firstLogEvent = generateLogEvent();
    newEvents.add(firstLogEvent);
    final LogEventMock secondLogEvent = generateLogEvent();
    secondLogEvent.setCategory("new.category");
    newEvents.add(secondLogEvent);
    _logStoreComponent.addLogEvents(newEvents);

    LogEventStoreEvent expectedEvent = new LogEventStoreEvent(_logStoreComponent);

    // after adding the events, the listener should have been invoked
    // exactly once: with the logEventStoreChanged method.
    verify(listener).logEventStoreChanged(expectedEvent);
  }

  public void test_LogStoreListener_AddFilter() {
    // add a listener
    LogEventStoreListener listener = mock(LogEventStoreListener.class);
    _logStoreComponent.addLogEvent(generateLogEvent());

    _logStoreComponent.addLogStoreListener(listener);

    _logStoreComponent.addLogEventFilter(mock(LogEventFilter.class));
    LogEventStoreEvent expectedEvent = new LogEventStoreEvent(_logStoreComponent);

    // adding a filter should lead to an add-event
    verify(listener).logEventStoreChanged(expectedEvent);
  }

  class SimpleTestLogEventFilter extends AbstractTestLogEventFilter {
    private boolean _shouldMatch = false;

    public boolean matches(LogEvent event) {
      return _shouldMatch;
    }

    public boolean isShouldMatch() {
      return _shouldMatch;
    }

    public void setShouldMatch(boolean shouldMatch) {
      _shouldMatch = shouldMatch;
      fireFilterChange();
    }
  }

}
