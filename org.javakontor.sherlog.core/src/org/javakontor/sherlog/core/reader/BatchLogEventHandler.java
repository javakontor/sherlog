package org.javakontor.sherlog.core.reader;

import java.util.LinkedList;
import java.util.List;

import org.javakontor.sherlog.core.LogEvent;
import org.javakontor.sherlog.core.store.ModifiableLogEventStore;
import org.javakontor.sherlog.util.Assert;

public class BatchLogEventHandler extends DefaultLogEventHandler {
  
  private final ModifiableLogEventStore _logEventStore;
  private final List<LogEvent> _list = new LinkedList<LogEvent>();
  private final int _batchSize = 5000;
  
  public BatchLogEventHandler(ModifiableLogEventStore logEventStore) {
    Assert.notNull(logEventStore);
    _logEventStore = logEventStore;
  }

  @Override
  public void handle(LogEvent event) {
    this._list.add(event);

    if (this._list.size() > _batchSize) {
      _logEventStore.addLogEvents(this._list);
      this._list.clear();
    }
    // _list.add(event);
  }

  @Override
  public void dispose() {
    _logEventStore.addLogEvents(this._list);
  }

  @Override
  public void handleException(Exception exception) {
    System.err.println(exception);
  }
  
}
