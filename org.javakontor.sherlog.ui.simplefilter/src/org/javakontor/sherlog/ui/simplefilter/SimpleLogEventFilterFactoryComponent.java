package org.javakontor.sherlog.ui.simplefilter;

import org.javakontor.sherlog.core.filter.LogEventFilter;
import org.javakontor.sherlog.core.filter.LogEventFilterFactory;
import org.javakontor.sherlog.core.filter.LogEventFilterMemento;
import org.javakontor.sherlog.util.Assert;

public class SimpleLogEventFilterFactoryComponent implements LogEventFilterFactory {

  public LogEventFilter createLogEventFilter() {
    return new SimpleLogEventFilter();
  }

  public LogEventFilter createLogEventFilter(LogEventFilterMemento memento) {
    Assert.instanceOf("memento", memento, SimpleLogEventFilterMemento.class);

    return new SimpleLogEventFilter((SimpleLogEventFilterMemento) memento);
  }

  public String getDescription() {
    return "SimpleLogEventFilterFactory";
  }
}