package org.javakontor.sherlog.core.filter;

public interface FilterableChangeListener {

  public void logEventFilterAdded(LogEventFilter logEventFilter);

  public void logEventFilterRemoved(LogEventFilter logEventFilter);

}
