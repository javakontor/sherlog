package org.javakontor.sherlog.domain.impl.reader;

import org.javakontor.sherlog.domain.LogEvent;
import org.javakontor.sherlog.domain.reader.DefaultLogEventHandler;
import org.javakontor.sherlog.domain.reader.LogEventHandler;
import org.javakontor.sherlog.domain.store.ModifiableLogEventStore;

/**
 * A very simple {@link LogEventHandler} that forwards all events to a {@link ModifiableLogEventStore}
 * 
 * @author nils
 * 
 */
public class SimpleLogEventHandler extends DefaultLogEventHandler {

  /**
   * The {@link ModifiableLogEventStore}
   */
  private final ModifiableLogEventStore _logEventStore;

  public SimpleLogEventHandler(final ModifiableLogEventStore logEventStore) {
    super();
    _logEventStore = logEventStore;
  }

  @Override
  public void handle(final LogEvent event) {
    System.out.println("Handle: " + event.getMessage());
    _logEventStore.addLogEvent(event);
  }

}
