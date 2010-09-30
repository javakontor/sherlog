package org.javakontor.sherlog.ui.colorfilter;

import java.awt.Color;

import org.javakontor.sherlog.domain.LogEvent;
import org.javakontor.sherlog.ui.logview.decorator.LogEventTableCellDecorator;
import org.javakontor.sherlog.ui.logview.decorator.LogEventTableCellDecoratorContext;

public class ColorDecorator implements LogEventTableCellDecorator {

  public void decorateLogEventTableCell(LogEventTableCellDecoratorContext context) {

    LogEvent logEvent = context.getLogEvent();

    Color backgroundColor = (Color) logEvent.getUserDefinedField("COLOR");

    if (backgroundColor == null) {
      return;
    }

    context.getComponent().setBackground(backgroundColor);

  }

}
