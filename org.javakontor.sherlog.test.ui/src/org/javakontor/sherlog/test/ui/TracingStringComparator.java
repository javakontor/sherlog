package org.javakontor.sherlog.test.ui;

import org.netbeans.jemmy.operators.Operator.StringComparator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TracingStringComparator implements StringComparator {
  protected final Logger         _log = LoggerFactory.getLogger(getClass());

  private final StringComparator _comparatorUnderTrace;

  public TracingStringComparator(StringComparator comparatorUnderTrace) {
    super();
    _comparatorUnderTrace = comparatorUnderTrace;
  }

  public boolean equals(String caption, String match) {
    boolean result = _comparatorUnderTrace.equals(caption, match);
    _log.debug(String.format("Comparing '%s' with '%s' results in: %b", caption, match, result));
    return result;
  }

}
