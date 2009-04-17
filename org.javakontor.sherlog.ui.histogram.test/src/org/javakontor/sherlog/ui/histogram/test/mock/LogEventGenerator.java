package org.javakontor.sherlog.ui.histogram.test.mock;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.javakontor.sherlog.domain.LogEvent;
import org.javakontor.sherlog.domain.LogLevel;

public class LogEventGenerator {

  private static final String[] LOG_CATEGORIES = { "org.sherlog", "org.sherlog.core",
      "org.sherlog.core.internal", "org.sherlog.util", "org.apache.logging", "org.apache.core" };

  private static int            count          = 0;

  public static List<LogEvent> generateLogEventList(int count) {
    return Arrays.asList(generateLogEvents(count));
  }

  public static LogEvent[] generateLogEvents(int count) {
    LogEvent[] result = new LogEvent[count];

    for (int i = 0; i < result.length; i++) {
      result[i] = generateLogEvent();
    }

    return result;
  }

  public static LogEvent generateLogEvent() {

    Random random = new Random();

    String category = LOG_CATEGORIES[random.nextInt(LOG_CATEGORIES.length)];
    LogLevel logLevel = LogLevel.values()[random.nextInt(LogLevel.values().length)];

    Throwable throwable = null;
    if ((logLevel == LogLevel.WARN) || (logLevel == LogLevel.ERROR) || (logLevel == LogLevel.FATAL)) {
      throwable = new Throwable();
    }

    LogEventMock mock = new LogEventMock(category, ++count, logLevel, "my source", "Message-" + count, Thread
        .currentThread().getName(), throwable);
    return mock;
  }
}
