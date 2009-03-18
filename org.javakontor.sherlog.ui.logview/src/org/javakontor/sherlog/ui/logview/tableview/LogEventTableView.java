package org.javakontor.sherlog.ui.logview.tableview;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.table.TableColumnModel;

import org.javakontor.sherlog.application.mvc.AbstractView;
import org.javakontor.sherlog.application.mvc.ModelChangedEvent;
import org.javakontor.sherlog.ui.logview.decorator.LogEventTableCellDecorator;
import org.javakontor.sherlog.util.Assert;

public class LogEventTableView extends AbstractView<LogEventTableModel, LogEventTableModelReasonForChange> {

  /** serialVersionUID */
  private static final long       serialVersionUID = 1L;

  /** the 'log event table' table */
  private LogEventTableTable      _logEventTableTable;

  /** the 'log event table' table model */
  private LogEventTableTableModel _logEventListTableModel;

  /**
   * <p>
   * Creates a new instance of type {@link LogEventTableView}.
   * </p>
   * 
   * @param model
   *          the model.
   */
  public LogEventTableView(LogEventTableModel model) {
    super(model);
  }

  // private LogEventListCellRenderer _cellRenderer;

  // protected void configureColumn(int index, int prefSize) {
  // TableColumnModel columnModel = this._logEventListTable.getColumnModel();
  // TableColumn column = columnModel.getColumn(index);
  // column.setPreferredWidth(prefSize);
  // }

  @Override
  protected void setUp() {
    setLayout(new BorderLayout());
    this._logEventTableTable = new LogEventTableTable();
    // this._logEventTableTable = new JTable();
    // this._cellRenderer = new LogEventListCellRenderer();
    this._logEventListTableModel = new LogEventTableTableModel(getModel().getLogEventStore().getFilteredLogEvents());
    this._logEventTableTable.setModel(this._logEventListTableModel);
    // this._logEventListTable.setDefaultRenderer(Object.class, new LogEventListCellRenderer());
    // this._logEventTableTable.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
    TableColumnModel columnModel = this._logEventTableTable.getColumnModel();
    // configureColumn(0, 110);
    // configureColumn(1, 50);
    // configureColumn(2, 40);
    // configureColumn(3, 100);
    // configureColumn(4, 500);

    columnModel.getColumn(0).setPreferredWidth(110);
    columnModel.getColumn(1).setPreferredWidth(50);
    columnModel.getColumn(2).setPreferredWidth(40);
    columnModel.getColumn(2).setMaxWidth(40);
    columnModel.getColumn(3).setPreferredWidth(100);
    columnModel.getColumn(4).setPreferredWidth(500);
    // this._logEventListTable.setDefaultRenderer(Object.class, this._cellRenderer);
    // for (int i = 0; i < columnModel.getColumnCount(); i++) {
    // TableColumn column = columnModel.getColumn(i);
    // column.setCellRenderer(this._cellRenderer);
    // }

    JScrollPane scrollPane = new JScrollPane(this._logEventTableTable);
    add(scrollPane, BorderLayout.CENTER);
  }

  /**
   * @see org.javakontor.sherlog.application.mvc.ModelChangedListener#modelChanged(org.javakontor.sherlog.application.mvc.ModelChangedEvent)
   */
  @Override
  public void onModelChanged(ModelChangedEvent<LogEventTableModel, LogEventTableModelReasonForChange> event) {

    switch (event.getReasonForChange()) {
    case logEventsAdded:
      this._logEventListTableModel.fireTableDataChanged();
      break;
    case reset:
      this._logEventListTableModel.fireTableDataChanged();
      break;
    case decoratorAdded:
      this._logEventTableTable.addLogEventDecorator((LogEventTableCellDecorator) event.getObjects()[0]);
      break;
    case decoratorRemoved:
      this._logEventTableTable.removeLogEventDecorator((LogEventTableCellDecorator) event.getObjects()[0]);
      break;
    default:
      // ignore;
    }
  }

  /**
   * <p>
   * Returns the 'log event table' table.
   * </p>
   * 
   * @return the 'log event table' table.
   */
  LogEventTableTable getLogEventTableTable() {
    return this._logEventTableTable;
  }

/**
   * <p>
   * Adds the specified {@link LogEventTableCellDecorator.
   * </p>
   * 
   * @param eventDecorator
   *          the specified {@link LogEventTableCellDecorator}.
   */
  public void addLogEventDecorator(LogEventTableCellDecorator eventDecorator) {
    Assert.notNull(eventDecorator);

    // add the log event decorator
    this._logEventTableTable.addLogEventDecorator(eventDecorator);
  }

  /**
   * <p>
   * Removes the specified {@link LogEventTableCellDecorator}.
   * </p>
   * 
   * @param eventDecorator
   *          the specified {@link LogEventTableCellDecorator}.
   */
  public void removeLogEventDecorator(LogEventTableCellDecorator eventDecorator) {
    Assert.notNull(eventDecorator);

    // remove the log event decorator
    this._logEventTableTable.removeLogEventDecorator(eventDecorator);
  }
}