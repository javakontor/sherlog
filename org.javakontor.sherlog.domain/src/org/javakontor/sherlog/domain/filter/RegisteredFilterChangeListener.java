package org.javakontor.sherlog.domain.filter;

public interface RegisteredFilterChangeListener {

  public void filterAdded(LogEventFilter logEventFilter);

  public void filterRemoved(LogEventFilter logEventFilter);
}
