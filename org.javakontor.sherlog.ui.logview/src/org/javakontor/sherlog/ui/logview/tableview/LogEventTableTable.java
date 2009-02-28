package org.javakontor.sherlog.ui.logview.tableview;

import java.awt.Color;
import java.awt.Component;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JTable;
import javax.swing.JViewport;
import javax.swing.table.TableCellRenderer;

import org.javakontor.sherlog.core.LogEvent;
import org.javakontor.sherlog.ui.logview.decorator.LogEventDecorator;
import org.javakontor.sherlog.ui.logview.decorator.LogEventDecoratorContext;
import org.javakontor.sherlog.ui.util.SherlogTable;

/**
 * <p>
 * The LogEventTable adds support for {@link LogEventDecorator LogEventDecorators} to a JTable. Using LogEventDecorators
 * clients can customize ("decorate") the table cells, to get a context-specific rendering (e.g. highlight rows
 * according to client specific criteria).
 * </p>
 * <p>
 * This implementation also ensures, that the table is at least as large as it's containing {@link JViewport} to allow
 * opening the context menu even if the table is empty.
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class LogEventTableTable extends SherlogTable {

  /** serialVersionUID */
  private static final long            serialVersionUID = 1L;

  /** list of registered {@link LogEventDecorator LogEventDecorators} that will be applied to a cell component */
  private final Set<LogEventDecorator> _logEventDecorators;

  /** {@link DelegatingCellRenderer} that applies the registered LogEventDecorators to a rendered cell component */
  private final DelegatingCellRenderer _delegatingCellRenderer;

  /**
   * <p>
   * Creates a new instance of {@link LogEventTableTable}.
   * </p>
   */
  public LogEventTableTable() {
    // the log event decorator list
    this._logEventDecorators = new HashSet<LogEventDecorator>();

    // the delegating cell renderer
    this._delegatingCellRenderer = new DelegatingCellRenderer();
  }

  /**
   * <p>
   * This implementation wraps the {@link TableCellRenderer} returned by the superclass with a
   * {@link DelegatingCellRenderer} that applies all registered {@link LogEventDecorator LogEventDecorators}
   * </p>
   * 
   * @see javax.swing.JTable#getCellRenderer(int, int)
   */
  @Override
  public TableCellRenderer getCellRenderer(int row, int column) {
    // get default Renderer
    TableCellRenderer cellRenderer = super.getCellRenderer(row, column);

    // wrap with DelegatinCellRender, that invokes all registered decorators
    // on the default renderer to perform customizing the cell's UI
    this._delegatingCellRenderer.setDelegatee(cellRenderer);

    // return result
    return this._delegatingCellRenderer;
  }

  // /**
  // * <p>
  // * Returns {@code true} if the preferred height of the table is smaller than the viewport's height.
  // * </p>
  // * <p>
  // * Taken from the Java6 implementation of this method.
  // * </p>
  // *
  // * @see javax.swing.JTable#getScrollableTracksViewportHeight()
  // */
  // @Override
  // public boolean getScrollableTracksViewportHeight() {
  //
  // // Make sure, table is at least as large as the view port
  // // (taken from Java6 JTable implementation)
  // return (getParent() instanceof JViewport) && (((JViewport) getParent()).getHeight() > getPreferredSize().height);
  // }

  /**
   * <p>
   * A {@link TableCellRenderer} implementation that asks a list of {@link LogEventDecorator LogEventDecorators} to
   * decorate a component that was returned by a wrapped TableCellRenderer.
   * </p>
   */
  class DelegatingCellRenderer implements TableCellRenderer {
    /**
     * The default foreground color, that has been set initially, before a decorator modified it
     */
    private Color             _defaultForegroundColor = null;

    /**
     * The default background color, that has been set initially, before a decorator modified it
     */
    private Color             _defaultBackgroundColor = null;

    /**
     * The delegatee that creates the initial component
     */
    private TableCellRenderer _delegatee;

    DelegatingCellRenderer() {
    }

    /**
     * Sets the delegatee that is reponsible for rendering the "base" component, that gets decorated by the registered
     * decorators
     * 
     * @param delegatee
     */
    public void setDelegatee(TableCellRenderer delegatee) {
      this._delegatee = delegatee;
    }

    /**
     * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object,
     *      boolean, boolean, int, int)
     */
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
        int row, int column) {
      Component component = this._delegatee.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
          column);

      // Save default colors on first access,
      // restore on following requests
      if (!isSelected) {
        if (this._defaultBackgroundColor == null) {
          this._defaultBackgroundColor = component.getBackground();
        } else {
          component.setBackground(this._defaultBackgroundColor);
        }

        if (this._defaultForegroundColor == null) {
          this._defaultForegroundColor = component.getForeground();
        } else {
          component.setForeground(this._defaultForegroundColor);
        }
      }

      // determine selected logEvent
      LogEventTableTableModel tableModel = (LogEventTableTableModel) table.getModel();
      LogEvent logEvent = tableModel.getLogEvent(row);

      // Apply decorators on the rendered cell component
      LogEventDecoratorContext.Attribute attribute = LogEventDecoratorContext.Attribute.values()[column];
      LogEventDecoratorContext context = new LogEventDecoratorContext(component, logEvent, attribute, value, hasFocus,
          isSelected);
      for (LogEventDecorator decorator : LogEventTableTable.this._logEventDecorators) {
        decorator.decorate(context);
      }

      // return the decorated component
      return component;
    }
  }

  /**
   * <p>
   * Add the specified {@link LogEventDecorator} to the list of decorators, that are used to decorate a table cell
   * </p>
   * 
   * @param eventDecorator
   *          the {@link LogEventDecorator}
   */
  public void addLogEventDecorator(LogEventDecorator eventDecorator) {
    this._logEventDecorators.add(eventDecorator);
  }

  /**
   * <p>
   * Removes the specified {@link LogEventDecorator} from the list of registered decorators
   * </p>
   * 
   * @param eventDecorator
   *          the {@link LogEventDecorator}
   */
  public void removeLogEventDecorator(LogEventDecorator eventDecorator) {
    this._logEventDecorators.remove(eventDecorator);
  }
}
