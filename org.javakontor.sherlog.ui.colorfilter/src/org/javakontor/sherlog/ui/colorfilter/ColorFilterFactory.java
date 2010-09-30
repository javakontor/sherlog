package org.javakontor.sherlog.ui.colorfilter;

import org.javakontor.sherlog.domain.filter.LogEventFilter;
import org.javakontor.sherlog.domain.filter.LogEventFilterFactory;
import org.javakontor.sherlog.domain.filter.LogEventFilterMemento;

public class ColorFilterFactory implements LogEventFilterFactory {

  public LogEventFilter createLogEventFilter() {
    return new ColorFilter();
  }

  public LogEventFilter createLogEventFilter(final LogEventFilterMemento memento) {
    return null;
  }

  public String getDescription() {
    return "A LogEventFilter that filters events by color";
  }

}
