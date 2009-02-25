package org.javakontor.sherlog.core.impl.internal.store;

import static org.junit.Assert.assertEquals;

import org.javakontor.sherlog.core.LogEvent;
import org.javakontor.sherlog.core.LogLevel;
import org.javakontor.sherlog.core.filter.LogEventFilter;
import org.javakontor.sherlog.core.filter.LogEventFilterListener;
import org.javakontor.sherlog.core.filter.LogEventFilterMemento;
import org.javakontor.sherlog.core.impl.internal.store.LogStoreComponent;
import org.junit.Assert;
import org.junit.Test;

public class LogStoreComponentTest {
  
  private static long _eventIdentifier = 1L;
  
  @Test
  public void test_Simple() {
    LogStoreComponent logStoreComponent = new LogStoreComponent();

    LogEvent testEvent = generateLogEvent();
    logStoreComponent.addLogEvent(testEvent);
    assertEquals(1, logStoreComponent.getLogEventCount());
    assertEquals(testEvent, logStoreComponent.getLogEvents().get(0));
    assertEquals(1, logStoreComponent.getFilteredLogEventCount());
    
    logStoreComponent.addLogEventFilter(new NoMatchingEventFilter());
    
    assertEquals(1, logStoreComponent.getLogEventCount());
    assertEquals(testEvent, logStoreComponent.getLogEvents().get(0));
    assertEquals(0, logStoreComponent.getFilteredLogEventCount());
    
  }
  
  class NoMatchingEventFilter implements LogEventFilter  {

    public void addLogFilterListener(LogEventFilterListener listener) {
    }

    public boolean matches(LogEvent event) {
      return false;
    }

    public void removeLogFilterListener(LogEventFilterListener listener) {
      
    }

    public void restoreFromMemento(LogEventFilterMemento memento) {
      
    }

    public LogEventFilterMemento saveToMemento() {
      return null;
    }
    
  }
  
  private LogEvent generateLogEvent() {

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
