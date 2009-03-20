package org.javakontor.sherlog.domain;

import org.javakontor.sherlog.domain.impl.internal.store.LogEventMock;

public class DomainTestUtils {

  private static int _eventIdentifier = 0;

  public static LogEventMock generateLogEvent() {

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
