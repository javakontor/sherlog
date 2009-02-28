package org.javakontor.sherlog.ui.util;

import javax.swing.table.TableModel;

/**
 * Indicates that a {@link TableModel} that implements this interface can be sorted by it's JTable, if the JTable
 * supports sorting (only in Java >= 6)
 * 
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public interface SortableTableModel {

  /**
   * Returns the column no that should be sorted by default or an integer less than zero to indicate that no column
   * should be sorted by default
   * 
   * @return
   */
  public int getDefaultSortColumn();

}
