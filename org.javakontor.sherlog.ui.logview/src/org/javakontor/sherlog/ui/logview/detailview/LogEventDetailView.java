package org.javakontor.sherlog.ui.logview.detailview;

import java.awt.BorderLayout;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.text.html.HTMLEditorKit;

import org.javakontor.sherlog.core.LogEvent;
import org.lumberjack.application.mvc.AbstractView;
import org.lumberjack.application.mvc.DefaultReasonForChange;
import org.lumberjack.application.mvc.ModelChangedEvent;

public class LogEventDetailView extends AbstractView<LogEventDetailModel, DefaultReasonForChange> {

  /** serialVersionUID */
  private static final long   serialVersionUID = -4222822027918013022L;

  /** used to format the logging event * */
  private static final String DETAILS_FORMAT   = "<b>Time:</b><code>%1$td.%1$tm.%1$tY %1$tH:%1$tM:%1$tS,%1$tL</code>"
                                                   + "&nbsp;&nbsp;<b>ID:</b> <code>%2$s</code>"
                                                   + "&nbsp;&nbsp;<b>LogEvent:</b> <code>%3$s</code>"
                                                   + "&nbsp;&nbsp;<b>Thread:</b> <code>%4$s</code>"
                                                   + "<br><b>Quelle:</b> <code>%5$s</code>"
                                                   + "<br><b>Category:</b> <code>%6$s</code>" + "<br><b>Message:</b>"
                                                   + "<pre>%7$s</pre>" + "<b>Throwable:</b>" + "<pre>%8$s</pre>";

  /** the details pane */
  private JEditorPane         _detailsPane;

  /**
   * <p>
   * Creates a new instance of type {@link LogEventDetailView}.
   * </p>
   * 
   * @param model
   *          the model
   */
  public LogEventDetailView(LogEventDetailModel model) {
    super(model);
  }

  @Override
  protected void setUp() {
    setLayout(new BorderLayout());

    this._detailsPane = new JEditorPane();
    this._detailsPane.setEditorKit(new HTMLEditorKit());
    this._detailsPane.setEditable(false);

    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setViewportView(this._detailsPane);
    add(scrollPane, BorderLayout.CENTER);
  }

  public void modelChanged(ModelChangedEvent<LogEventDetailModel, DefaultReasonForChange> event) {

    // no event set?
    if (getModel().getLogEvent() == null) {
      this._detailsPane.setText("(no event selected)");
      return;
    }

    // get log event
    LogEvent logEvent = getModel().getLogEvent();

    // render HTML
    String details = String.format(DETAILS_FORMAT, logEvent.getTimeStamp(), logEvent.getIdentifier(), logEvent
        .getLogLevel(), logEvent.getThreadName(), logEvent.getLogSource(), logEvent.getCategory(), logEvent
        .getMessage(), logEvent.getThrowableInformation());

    // set the text
    this._detailsPane.setText(details);
  }
}
