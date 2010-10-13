package org.javakontor.sherlog.ui.timefilter;

import org.javakontor.sherlog.domain.LogEvent;
import org.javakontor.sherlog.domain.filter.LogEventFilterMemento;
import org.javakontor.sherlog.domain.impl.filter.AbstractLogEventFilter;

public class TimeFilter extends AbstractLogEventFilter {

  private long _beforeTimestamp = -1;

  private long _afterTimestamp  = Long.MAX_VALUE;

  public TimeFilter() {
  }

  public boolean matches(final LogEvent event) {

    long timestamp = event.getTimeStamp();

    return (_beforeTimestamp <= timestamp && timestamp <= _afterTimestamp);
  }

  public LogEventFilterMemento saveToMemento() {
    return null;
  }

  @Override
  protected void onRestoreFromMemento(final LogEventFilterMemento memento) {
    // TODO Auto-generated method stub
  }

  public void setBeforeTimestamp(long beforeTimestamp) {
    _beforeTimestamp = beforeTimestamp;
    fireFilterChange();
  }

  public void setAfterTimestamp(long afterTimestamp) {
    _afterTimestamp = afterTimestamp;
    fireFilterChange();
  }

  public void reset() {
    _beforeTimestamp = -1;
    _afterTimestamp = Long.MAX_VALUE;

    fireFilterChange();
  }

}
