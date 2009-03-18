package org.javakontor.sherlog.domain.filter;

public interface FilterableChangeListener {

  public void filterAdded(LogEventFilter logEventFilter);

  public void filterRemoved(LogEventFilter logEventFilter);

}
