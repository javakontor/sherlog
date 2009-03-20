package org.javakontor.sherlog.domain.impl.reader;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.javakontor.sherlog.domain.LogEvent;
import org.javakontor.sherlog.domain.reader.DefaultLogEventHandler;
import org.javakontor.sherlog.domain.store.ModifiableLogEventStore;
import org.javakontor.sherlog.util.Assert;

public class BatchLogEventHandler extends DefaultLogEventHandler {

  private final ModifiableLogEventStore _logEventStore;

  private  List<LogEvent>          _list      ;

  private final int                     _batchSize;

  public BatchLogEventHandler(ModifiableLogEventStore logEventStore) {
    this(logEventStore, 5000);
  }
  
  public BatchLogEventHandler(ModifiableLogEventStore logEventStore, int batchSize) {
    Assert.notNull(logEventStore);
    _logEventStore = logEventStore;
    _batchSize = batchSize;
    this._list = new ArrayList<LogEvent>(_batchSize);
  }

  @Override
  public void handle(LogEvent event) {
    this._list.add(event);

    if (this._list.size() >= _batchSize) {
      _logEventStore.addLogEvents(this._list);
      this._list = new ArrayList<LogEvent>(this._batchSize);
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
