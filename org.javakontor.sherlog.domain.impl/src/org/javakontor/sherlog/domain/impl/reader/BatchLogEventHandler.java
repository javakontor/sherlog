package org.javakontor.sherlog.domain.impl.reader;

import java.util.ArrayList;
import java.util.List;

import org.javakontor.sherlog.domain.LogEvent;
import org.javakontor.sherlog.domain.reader.DefaultLogEventHandler;
import org.javakontor.sherlog.domain.store.ModifiableLogEventStore;
import org.javakontor.sherlog.util.Assert;

public class BatchLogEventHandler extends DefaultLogEventHandler {

  private final ModifiableLogEventStore _logEventStore;

  private List<LogEvent>                _list;

  private final int                     _batchSize;

  public BatchLogEventHandler(final ModifiableLogEventStore logEventStore) {
    this(logEventStore, 5000);
  }

  public BatchLogEventHandler(final ModifiableLogEventStore logEventStore, final int batchSize) {
    Assert.notNull(logEventStore);
    _logEventStore = logEventStore;
    _batchSize = batchSize;
    _list = new ArrayList<LogEvent>(_batchSize);
  }

  @Override
  public void handle(final LogEvent event) {
    _list.add(event);

    if (_list.size() >= _batchSize) {
      _logEventStore.addLogEvents(_list);
      _list = new ArrayList<LogEvent>(_batchSize);
    }
    // _list.add(event);
  }

  @Override
  public void dispose() {
    _logEventStore.addLogEvents(_list);
  }

  @Override
  public void handleException(final Exception exception) {
    System.err.println(exception);
  }

}
