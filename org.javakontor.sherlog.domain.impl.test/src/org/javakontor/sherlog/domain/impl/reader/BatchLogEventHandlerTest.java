package org.javakontor.sherlog.domain.impl.reader;

import static org.mockito.Mockito.*;

import java.util.LinkedList;
import java.util.List;

import junit.framework.TestCase;

import org.javakontor.sherlog.domain.DomainTestUtils;
import org.javakontor.sherlog.domain.LogEvent;
import org.javakontor.sherlog.domain.store.ModifiableLogEventStore;

public class BatchLogEventHandlerTest extends TestCase {

  public BatchLogEventHandlerTest(String name) {
    super(name);
  }

  public void test_FewEvents() {
    ModifiableLogEventStore logEventStore = mock(ModifiableLogEventStore.class);
    BatchLogEventHandler handler = new BatchLogEventHandler(logEventStore, 10);

    List<LogEvent> expectedFirstChunk = new LinkedList<LogEvent>();
    for (int i = 0; i < 8; i++) {
      LogEvent logEvent = DomainTestUtils.generateLogEvent();
      handler.handle(logEvent);
      expectedFirstChunk.add(logEvent);
    }

    handler.dispose();

    verify(logEventStore).addLogEvents(expectedFirstChunk);

  }

  public void test_ManyEvents() {
    ModifiableLogEventStore logEventStore = mock(ModifiableLogEventStore.class);
    BatchLogEventHandler handler = new BatchLogEventHandler(logEventStore, 10);

    List<LogEvent> expectedFirstChunk = new LinkedList<LogEvent>();
    List<LogEvent> expectedSecondChunk = new LinkedList<LogEvent>();

    for (int i = 0; i < 10; i++) {
      LogEvent logEvent = DomainTestUtils.generateLogEvent();
      handler.handle(logEvent);
      expectedFirstChunk.add(logEvent);
    }

    LogEvent logEvent = DomainTestUtils.generateLogEvent();
    handler.handle(logEvent);
    expectedSecondChunk.add(logEvent);

    handler.dispose();

    verify(logEventStore).addLogEvents(expectedSecondChunk);
    verify(logEventStore).addLogEvents(expectedFirstChunk);
  }
}
