package org.javakontor.sherlog.domain.store;

import junit.framework.TestCase;

import org.javakontor.sherlog.domain.impl.internal.store.LogStoreComponent;

public class LogEventStoreEventTest extends TestCase {

  public void test_Equals() {

    LogStoreComponent component = new LogStoreComponent();

    LogEventStoreEvent event = new LogEventStoreEvent(component);

    LogEventStoreEvent sameEvent = new LogEventStoreEvent(component);

    assertEquals(event, sameEvent);

  }

}
