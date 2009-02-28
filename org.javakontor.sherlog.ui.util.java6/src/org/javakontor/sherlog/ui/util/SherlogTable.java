package org.javakontor.sherlog.ui.util;

import javax.swing.JTable;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class SherlogTable extends JTable {
  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  /**
   * Adds a RowSorter if the given {@link TableModel} is an instance of {@link SortableTableModel}
   */
  @Override
  public void setModel(TableModel dataModel) {
    super.setModel(dataModel);
    if (dataModel instanceof SortableTableModel) {
      SortableTableModel sortableTableModel = (SortableTableModel) dataModel;
      // // set a RowSorter and sort by first column (bundle id)
      TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(dataModel);
      int defaultSortColumn = sortableTableModel.getDefaultSortColumn();
      if (defaultSortColumn >= 0) {
        sorter.toggleSortOrder(defaultSortColumn);
      }
      setRowSorter(sorter);
    }
  }

}
