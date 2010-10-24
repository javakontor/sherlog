package org.javakontor.sherlog.ui.managementagent.tableview;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import org.javakontor.sherlog.ui.managementagent.BundleListMessages;
import org.javakontor.sherlog.util.ui.SortableTableModel;
import org.osgi.framework.Bundle;

class BundleListTableModel extends AbstractTableModel implements SortableTableModel {

  private final static String[] _columnNames     = new String[] { BundleListMessages.bundleIdColumnName,
      BundleListMessages.symbolicNameColumnName, BundleListMessages.versionColumnName,
      BundleListMessages.stateColumnName, BundleListMessages.locationColumnName };

  /** - */
  private static final long     serialVersionUID = 1L;

  /** - */
  private List<Bundle>          _bundles;

  /**
   * 
   */
  public BundleListTableModel(List<Bundle> bundles) {
    _bundles = bundles;
  }

  public void setBundles(List<Bundle> bundles) {
    _bundles = bundles;
  }

  public int getDefaultSortColumn() {
    // sort by bundle-id
    return 0;
  }

  @Override
  public Class<?> getColumnClass(int columnIndex) {
    if (columnIndex == 0) {
      return Long.class;
    }
    return String.class;
  }

  @Override
  public String getColumnName(int column) {
    return _columnNames[column];
  }

  public int getColumnCount() {
    return _columnNames.length;
  }

  public int getRowCount() {
    return _bundles.size();
  }

  public Object getValueAt(int row, int column) {

    switch (column) {
    case 0:
      return _bundles.get(row).getBundleId();
    case 1:
      return _bundles.get(row).getSymbolicName();
    case 2:
      return (String) _bundles.get(row).getHeaders().get("Bundle-Version");
    case 3:
      return getBundleStateAsString(_bundles.get(row));
    case 4:
      return _bundles.get(row).getLocation();
    }

    return null;
  }
  
  public int getRow(Bundle bundle) {
    if (bundle == null) {
      return -1;
    }
    int i=0;
    for (Bundle bundleRow : _bundles) {
      if (bundle.equals(bundleRow)) {
        return i; 
      }
      i++;
    }
    return -1;
  }

  protected String getBundleStateAsString(Bundle bundle) {
    switch (bundle.getState()) {
    case Bundle.UNINSTALLED:
      return "UNINSTALLED";
    case Bundle.INSTALLED:
      return "INSTALLED";
    case Bundle.RESOLVED:
      return "RESOLVED";
    case Bundle.STARTING:
      return "STARTING";
    case Bundle.STOPPING:
      return "STOPPING";
    case Bundle.ACTIVE:
      return "ACTIVE";
    default:
    }
    return "UNKNOWN";
  }
}