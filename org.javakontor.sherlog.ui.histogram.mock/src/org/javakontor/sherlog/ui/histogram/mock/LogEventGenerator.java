package org.javakontor.sherlog.ui.histogram.mock;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import org.javakontor.sherlog.domain.LogEvent;
import org.javakontor.sherlog.domain.LogLevel;
import org.javakontor.sherlog.ui.histogram.mock.internal.LogEventMock;

/**
 * <p>
 * Helper class to generate (Mock-) {@link LogEvent LogEvents}. To do so, just call
 * <code>LogEventGenerator.generateLogEventList()</code> or <code>LogEventGenerator.generateLogEvents()</code>.
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class LogEventGenerator {

  /** the dummy categories */
  private static final String[] LOG_CATEGORIES = { "org.javakontor", "org.javakontor.core",
      "org.javakontor.core.internal", "org.javakontor.util", "org.apache.logging", "org.apache.core" };

  /** the count */
  private static int            count          = 0;

  /**
   * <p>
   * Generates a list that contains {@link LogEvent LogEvents}.
   * </p>
   *
   * @param count
   *          the count
   * @return a list that contains {@link LogEvent LogEvents}.
   */
  public static List<LogEvent> generateLogEventList(int count) {
    return Arrays.asList(generateLogEvents(count));
  }

  /**
   * <p>
   * Generates an array that contains {@link LogEvent LogEvents}.
   * </p>
   *
   * @param count
   *          the count
   * @return an array that contains {@link LogEvent LogEvents}.
   */
  public static LogEvent[] generateLogEvents(int count) {
    LogEvent[] result = new LogEvent[count];

    for (int i = 0; i < result.length; i++) {
      result[i] = generateLogEvent();
    }

    return result;
  }

  /**
   * <p>
   * Generates a single (Mock-){@link LogEvent}.
   * </p>
   *
   * @return a (Mock-){@link LogEvent}.
   */
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
