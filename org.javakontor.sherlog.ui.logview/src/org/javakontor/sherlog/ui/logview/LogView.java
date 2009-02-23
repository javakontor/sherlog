package org.javakontor.sherlog.ui.logview;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JSplitPane;

import org.javakontor.sherlog.ui.logview.detailview.LogEventDetailView;
import org.javakontor.sherlog.ui.logview.filterview.LogEventFilterView;
import org.javakontor.sherlog.ui.logview.tableview.LogEventTableView;
import org.lumberjack.application.mvc.AbstractView;
import org.lumberjack.application.mvc.DefaultReasonForChange;
import org.lumberjack.application.mvc.ModelChangedEvent;

public class LogView extends AbstractView<LogModel, DefaultReasonForChange> {

  /** serialVersionUID */
  private static final long  serialVersionUID = 1L;

  /** the horizontal split pane */
  private JSplitPane         _horizontalSplitPane;

  /** the vertical split pane */
  private JSplitPane         _verticalSplitPane;

  /** the log event detail view */
  private LogEventDetailView _logEventDetailView;

  /** - */
  private LogEventTableView  _logEventListView;

  /** - */
  private LogEventFilterView _logEventFilterView;

  /**
   * <p>
   * Creates a new instance of type {@link LogView}.
   * </p>
   * 
   * @param model
   *          the {@link LogModel}
   */
  public LogView(LogModel model) {
    super(model);
  }

  @Override
  protected void setUp() {

    this._logEventFilterView = new LogEventFilterView(getModel().getLogEventFilterModel());
    this._logEventDetailView = new LogEventDetailView(getModel().getLogEventDetailModel());
    this._logEventListView = new LogEventTableView(getModel().getLogEventTableModel());
    
    setLayout(new BorderLayout());
    setPreferredSize(new Dimension(700, 350));

    this._verticalSplitPane = new JSplitPane();
    this._verticalSplitPane.setDividerLocation(200);
    this._verticalSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
    this._verticalSplitPane.setOneTouchExpandable(true);
    this._verticalSplitPane.setRightComponent(this._logEventDetailView);
    this._verticalSplitPane.setLeftComponent(this._logEventListView);

    this._horizontalSplitPane = new JSplitPane();
    this._horizontalSplitPane.setOneTouchExpandable(true);
    this._horizontalSplitPane.setLeftComponent(this._logEventFilterView);
    this._horizontalSplitPane.setRightComponent(this._verticalSplitPane);

    add(this._horizontalSplitPane, BorderLayout.CENTER);
  }

  public LogEventDetailView getLogEventDetailView() {
    return this._logEventDetailView;
  }

  public LogEventTableView getLogEventTableView() {
    return this._logEventListView;
  }

  public LogEventFilterView getLogEventFilterView() {
    return this._logEventFilterView;
  }

  public void modelChanged(ModelChangedEvent<LogModel, DefaultReasonForChange> event) {
    // nothing to do here...
  }
}
