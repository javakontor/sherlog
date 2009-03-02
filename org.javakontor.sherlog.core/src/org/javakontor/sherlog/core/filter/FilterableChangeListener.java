package org.javakontor.sherlog.core.filter;

public interface FilterableChangeListener {

  public void filterAdded(LogEventFilter logEventFilter);

  public void filterRemoved(LogEventFilter logEventFilter);

}
