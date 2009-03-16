package org.javakontor.sherlog.ui.logview;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JSplitPane;
import javax.swing.text.View;

import org.javakontor.sherlog.ui.logview.detailview.LogEventDetailView;
import org.javakontor.sherlog.ui.logview.filterview.LogEventFilterView;
import org.javakontor.sherlog.ui.logview.tableview.LogEventTableView;
import org.lumberjack.application.mvc.AbstractView;
import org.lumberjack.application.mvc.DefaultReasonForChange;

/**
 * <p>
 * The {@link View} for the {@link LogViewContribution}.
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class LogView extends AbstractView<LogModel, DefaultReasonForChange> {

  /** serialVersionUID */
  private static final long  serialVersionUID = 1L;

  /** the horizontal split pane */
  private JSplitPane         _horizontalSplitPane;

  /** the vertical split pane */
  private JSplitPane         _verticalSplitPane;

  /** the log event detail view */
  private LogEventDetailView _logEventDetailView;

  /** the log event table view */
  private LogEventTableView  _logEventTableView;

  /** the log event filter view */
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

  /**
   * @see org.lumberjack.application.mvc.AbstractView#setUp()
   */
  @Override
  protected void setUp() {

    this._logEventFilterView = new LogEventFilterView(getModel().getLogEventFilterModel());
    this._logEventDetailView = new LogEventDetailView(getModel().getLogEventDetailModel());
    this._logEventTableView = new LogEventTableView(getModel().getLogEventTableModel());

    setLayout(new BorderLayout());
    setPreferredSize(new Dimension(700, 350));

    this._verticalSplitPane = new JSplitPane();
    this._verticalSplitPane.setDividerLocation(200);
    this._verticalSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
    this._verticalSplitPane.setOneTouchExpandable(true);
    this._verticalSplitPane.setRightComponent(this._logEventDetailView);
    this._verticalSplitPane.setLeftComponent(this._logEventTableView);

    this._horizontalSplitPane = new JSplitPane();
    this._horizontalSplitPane.setOneTouchExpandable(true);
    this._horizontalSplitPane.setLeftComponent(this._logEventFilterView);
    this._horizontalSplitPane.setRightComponent(this._verticalSplitPane);

    add(this._horizontalSplitPane, BorderLayout.CENTER);

  }

  /**
   * <p>
   * Returns the {@link LogEventDetailView}.
   * </p>
   * 
   * @return the {@link LogEventDetailView}.
   */
  public LogEventDetailView getLogEventDetailView() {
    return this._logEventDetailView;
  }

  /**
   * <p>
   * Returns the {@link LogEventTableView}.
   * </p>
   * 
   * @return the {@link LogEventTableView}.
   */
  public LogEventTableView getLogEventTableView() {
    return this._logEventTableView;
  }

  /**
   * <p>
   * Returns the {@link LogEventFilterView}.
   * </p>
   * 
   * @return the {@link LogEventFilterView}.
   */
  public LogEventFilterView getLogEventFilterView() {
    return this._logEventFilterView;
  }

}
