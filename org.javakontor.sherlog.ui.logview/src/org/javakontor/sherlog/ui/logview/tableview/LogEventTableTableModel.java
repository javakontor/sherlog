package org.javakontor.sherlog.ui.logview.tableview;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

import org.javakontor.sherlog.core.LogEvent;
import org.javakontor.sherlog.core.LogLevel;
import org.javakontor.sherlog.util.Assert;

/**
 * <p>
 * Implements a table model that allows to display a list of {@link LogEvent LogEvents} in a {@link JTable}.
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class LogEventTableTableModel extends AbstractTableModel {

  /** serialVersionUID */
  private static final long      serialVersionUID   = 1L;

  /** the constant DEFAULT_DATEFORMAT */
  public static final DateFormat DEFAULT_DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

  /** - */
  public static final String[]   COLUMN_NAMES       = new String[] { "Time", "LogLevel", "Trace", "Category", "Message" };

  /** the list of all log events */
  private final List<LogEvent>   _logEvents;

  /**
   * <p>
   * Creates a new instance of type {@link LogEventTableTableModel}.
   * </p>
   * 
   * @param logEventList
   *          the log event list.
   */
  public LogEventTableTableModel(List<LogEvent> logEventList) {
    Assert.notNull(logEventList);

    this._logEvents = logEventList;
  }

  /**
   * @see javax.swing.table.TableModel#getColumnCount()
   */
  public int getColumnCount() {
    return COLUMN_NAMES.length;
  }

  /**
   * @see javax.swing.table.AbstractTableModel#getColumnName(int)
   */
  @Override
  public String getColumnName(int column) {
    return COLUMN_NAMES[column];
  }

  /**
   * @see javax.swing.table.TableModel#getValueAt(int, int)
   */
  public Object getValueAt(int rowIndex, int columnIndex) {

    // get the log event
    LogEvent logEvent = getLogEvent(rowIndex);

    switch (columnIndex) {
    case 0:
      return DEFAULT_DATEFORMAT.format(new Date(logEvent.getTimeStamp()));
    case 1:
      return logEvent.getLogLevel().toString();
    case 2:
      return logEvent.getThrowableInformation() != null;
    case 3:
      return logEvent.getCategory();
    case 4:
      return logEvent.getMessage();
    default:
      throw new IllegalArgumentException("columnIndex " + columnIndex + " invalid");
    }
  }

  /**
   * <p>
   * Returns the log event with the specified index.
   * </p>
   * 
   * @param rowIndex
   *          the row index
   * @return the log event with the specified index.
   */
  public LogEvent getLogEvent(int rowIndex) {
    return this._logEvents.get(rowIndex);
  }

  /**
   * @see javax.swing.table.AbstractTableModel#getColumnClass(int)
   */
  @Override
  public Class<?> getColumnClass(int columnIndex) {
    if (columnIndex == 1) {
      return LogLevel.class;
    }
    if (columnIndex == 2) {
      return Boolean.class;
    }
    return super.getColumnClass(columnIndex);
  }

  /**
   * @see javax.swing.table.TableModel#getRowCount()
   */
  public int getRowCount() {
    return this._logEvents.size();
  }

}
