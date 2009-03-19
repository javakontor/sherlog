package org.javakontor.sherlog.domain.impl.internal.store;

import java.util.LinkedList;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.javakontor.sherlog.domain.LogEvent;
import org.javakontor.sherlog.domain.LogLevel;
import org.javakontor.sherlog.domain.impl.internal.store.LogStoreComponent;
import org.javakontor.sherlog.domain.store.LogEventStoreChangeEvent;
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

  private static long _eventIdentifier = 1L;

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

    // test modifying filter I
    simpleTestLogEventFilter.setShouldMatch(true);
    assertEquals(1, _logStoreComponent.getLogEventCount());
    assertEquals(testEvent, _logStoreComponent.getLogEvents().get(0));
    assertEquals(1, _logStoreComponent.getFilteredLogEventCount());

    // test modifying filter II (filter should *not* match anymore)
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

  public void test_Listener() {
    _logStoreComponent.addLogEvent(generateLogEvent());
    // add a listener
    MyListener listener = new MyListener();
    _logStoreComponent.addLogStoreListener(listener);

    List<LogEvent> newEvents = new LinkedList<LogEvent>();
    final LogEventMock firstLogEvent = generateLogEvent();
    newEvents.add(firstLogEvent);
    final LogEventMock secondLogEvent = generateLogEvent();
    secondLogEvent.setCategory("new.category");
    newEvents.add(secondLogEvent);
    _logStoreComponent.addLogEvents(newEvents);

    // after adding the events, the listener should have been invoked
    // exactly once: with the logEventStoreChanged method.
    // the logEventStoreReset should not have been invoked.
    assertEquals(1, listener._addedInvocations);
    assertEquals(0, listener._storeResetInvocations);

    // the logEventStoreChanged should have received a LogEventStoreChangeEvent that
    // should contain the delta
    Assert.assertNotNull(listener._lastEvent);
    assertEquals(newEvents, listener._lastEvent.getLogEvents());
    assertEquals(newEvents, listener._lastEvent.getFilteredLogEvents());
    assertEquals(1, listener._lastEvent.getCategories().size());
    assertEquals("new.category", listener._lastEvent.getCategories().get(0));

    // add more events - with filter
    listener.reset();
    _logStoreComponent.addLogEventFilter(new CategoryLogEventFilter("org.javakontor.sherlog.test"));
    _logStoreComponent.addLogEvents(newEvents);

    assertEquals(1, listener._addedInvocations);
    // adding a filter leads to a store reset
    assertEquals(1, listener._storeResetInvocations);
    Assert.assertNotNull(listener._lastEvent);
    assertEquals(newEvents, listener._lastEvent.getLogEvents());
    // only the 'org.javakontor.sherlog.test' log event should have been filtered
    assertEquals(1, listener._lastEvent.getFilteredLogEvents().size());
    assertEquals(firstLogEvent, listener._lastEvent.getFilteredLogEvents().get(0));

    // no new categories should have been added
    assertEquals(0, listener._lastEvent.getCategories().size());

  }

  class MyListener implements LogEventStoreListener {

    int                      _addedInvocations      = 0;

    int                      _storeResetInvocations = 0;

    LogEventStoreChangeEvent _lastEvent             = null;

    void reset() {
      _addedInvocations = 0;
      _storeResetInvocations = 0;
      _lastEvent = null;
    }

    public void logEventStoreChanged(LogEventStoreChangeEvent event) {
      _addedInvocations++;
      _lastEvent = event;
    }

    public void logEventStoreReset() {
      _storeResetInvocations++;
      _lastEvent = null;
    }
  }

  class CategoryLogEventFilter extends AbstractTestLogEventFilter {
    private final String _category;

    private CategoryLogEventFilter(String category) {
      super();
      _category = category;
    }

    public boolean matches(LogEvent event) {
      return _category.equals(event.getCategory());
    }
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

  private LogEventMock generateLogEvent() {

    String category = "org.javakontor.sherlog.test"; // this.LOG_CATEGORIES[this.random.nextInt(this.LOG_CATEGORIES.length)];
    LogLevel logLevel = LogLevel.INFO; // this.ALL_LEVEL[this.random.nextInt(this.ALL_LEVEL.length)];

    Throwable throwable = null;
    if ((logLevel == LogLevel.WARN) || (logLevel == LogLevel.ERROR) || (logLevel == LogLevel.FATAL)) {
      throwable = new Throwable();
    }

    LogEventMock mock = new LogEventMock(category, ++_eventIdentifier, logLevel, "my source", "Message-"
        + _eventIdentifier, "Thread-1", throwable);
    return mock;
  }

}
