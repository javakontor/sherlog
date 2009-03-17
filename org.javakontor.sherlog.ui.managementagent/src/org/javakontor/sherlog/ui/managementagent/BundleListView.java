package org.javakontor.sherlog.ui.managementagent;

import java.awt.BorderLayout;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import org.javakontor.sherlog.application.mvc.AbstractView;
import org.javakontor.sherlog.application.mvc.ModelChangedEvent;
import org.javakontor.sherlog.ui.util.SherlogTable;

public class BundleListView extends AbstractView<BundleListModel, BundleListModelReasonForChange> {

  /** - */
  private static final long    serialVersionUID = 1L;

  /** - */
  private SherlogTable         _bundleTable;

  private JMenuItem            _startBundleMenuItem;

  private JMenuItem            _stopBundleMenuItem;

  private JMenuItem            _updateBundleMenuItem;

  private JMenuItem            _uninstallBundleMenuItem;

  private JMenuItem            _copyLocationToClipboardMenuItem;

  private BundleListTableModel _bundleListTableModel;

  private JPopupMenu           _popupMenu;

  public BundleListView(BundleListModel model) {
    super(model);
  }

  public void onModelChanged(ModelChangedEvent<BundleListModel, BundleListModelReasonForChange> event) {

    if (event != null) {

      switch (event.getReasonForChange()) {
      case bundleListChanged:
        _bundleListTableModel.setBundles(getModel().getBundles());
        _bundleListTableModel.fireTableDataChanged();
        break;

      case selectionChanged:
        _startBundleMenuItem.setEnabled(getModel().canStartSelectedBundle());
        _stopBundleMenuItem.setEnabled(getModel().canStopSelectedBundle());
        _updateBundleMenuItem.setEnabled(getModel().canUpdateSelectedBundle());
        _uninstallBundleMenuItem.setEnabled(getModel().canUninstallSelectedBundle());
        _copyLocationToClipboardMenuItem.setEnabled(getModel().hasSelectedBundle());
        break;
      case bundleStateChanged:
        _startBundleMenuItem.setEnabled(getModel().canStartSelectedBundle());
        _stopBundleMenuItem.setEnabled(getModel().canStopSelectedBundle());
        _updateBundleMenuItem.setEnabled(getModel().canUpdateSelectedBundle());
        _uninstallBundleMenuItem.setEnabled(getModel().canUninstallSelectedBundle());
        _copyLocationToClipboardMenuItem.setEnabled(getModel().hasSelectedBundle());
        _bundleListTableModel.fireTableRowsUpdated((Integer) event.getObjects()[0], (Integer) event.getObjects()[0]);
        break;

      default:
        break;
      }

    } else {
      _bundleListTableModel.setBundles(getModel().getBundles());
      _bundleListTableModel.fireTableDataChanged();
      _startBundleMenuItem.setEnabled(getModel().canStartSelectedBundle());
      _stopBundleMenuItem.setEnabled(getModel().canStopSelectedBundle());
      _updateBundleMenuItem.setEnabled(getModel().canUpdateSelectedBundle());
      _uninstallBundleMenuItem.setEnabled(getModel().canUninstallSelectedBundle());
      _copyLocationToClipboardMenuItem.setEnabled(getModel().hasSelectedBundle());
    }
  }

  public SherlogTable getBundleTable() {
    return _bundleTable;
  }

  public JPopupMenu getPopupMenu() {
    return _popupMenu;
  }

  public JMenuItem getStartBundleMenuItem() {
    return _startBundleMenuItem;
  }

  public JMenuItem getStopBundleMenuItem() {
    return _stopBundleMenuItem;
  }

  public JMenuItem getUpdateBundleMenuItem() {
    return _updateBundleMenuItem;
  }

  public JMenuItem getUninstallBundleMenuItem() {
    return _uninstallBundleMenuItem;
  }

  public JMenuItem getCopyLocationToClipboardMenuItem() {
    return _copyLocationToClipboardMenuItem;
  }

  @Override
  protected void setUp() {

    setLayout(new BorderLayout());
    // add(new JLabel("Installed bundles"), BorderLayout.NORTH);

    _bundleTable = new SherlogTable();
    _bundleTable.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    _bundleListTableModel = new BundleListTableModel(getModel().getBundles());
    _bundleTable.setModel(_bundleListTableModel);
    // // set a RowSorter and sort by first column (bundle id)
    // TableRowSorter<BundleListTableModel> sorter = new TableRowSorter<BundleListTableModel>(_bundleListTableModel);
    // sorter.toggleSortOrder(0);
    // _bundleTable.setRowSorter(sorter);
    _bundleTable.getColumnModel().getColumn(0).setPreferredWidth(70);
    _bundleTable.getColumnModel().getColumn(1).setPreferredWidth(250);
    _bundleTable.getColumnModel().getColumn(2).setPreferredWidth(100);
    _bundleTable.getColumnModel().getColumn(3).setPreferredWidth(80);
    _bundleTable.getColumnModel().getColumn(4).setPreferredWidth(200);

    JScrollPane scrollPane = new JScrollPane(_bundleTable);
    _bundleTable.setFillsViewportHeight(true);

    _popupMenu = new JPopupMenu();
    _startBundleMenuItem = new JMenuItem(BundleListMessages.startBundleCtxMenuTitle);
    _stopBundleMenuItem = new JMenuItem(BundleListMessages.stopBundleCtxMenuTitle);
    _updateBundleMenuItem = new JMenuItem(BundleListMessages.updateBundleCtxMenuTitle);
    _uninstallBundleMenuItem = new JMenuItem(BundleListMessages.uninstallBundleCtxMenuTitle);
    _copyLocationToClipboardMenuItem = new JMenuItem(BundleListMessages.copyBundleLocationCtxMenuTitle);

    _popupMenu.add(_startBundleMenuItem);
    _popupMenu.add(_stopBundleMenuItem);
    _popupMenu.add(_updateBundleMenuItem);
    _popupMenu.addSeparator();
    _popupMenu.add(_uninstallBundleMenuItem);
    _popupMenu.addSeparator();
    _popupMenu.add(_copyLocationToClipboardMenuItem);

    add(scrollPane, BorderLayout.CENTER);
    // add(new JLabel("To install a new bundle drag the bundle jar into this window"), BorderLayout.SOUTH);

    modelChanged(null);
  }
}
