package org.javakontor.sherlog.ui.managementagent.tableview;

import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.javakontor.sherlog.application.mvc.AbstractController;
import org.javakontor.sherlog.application.request.RequestHandler;
import org.osgi.framework.Bundle;

public class BundleListController extends AbstractController<BundleListModel, BundleListView> {

  public BundleListController(BundleListModel model, BundleListView view, RequestHandler successor) {
    super(model, view, successor);

    initializeListener();
  }

  public BundleListController(BundleListModel model, BundleListView view) {
    super(model, view);

    initializeListener();
  }

  public void addDropTargetListener(DropTargetListener dropTargetListener) {
    new DropTarget(getView().getBundleTable(), dropTargetListener);
  }

  private void initializeListener() {

    getView().getBundleTable().getSelectionModel().addListSelectionListener(new ListSelectionListener() {

      public void valueChanged(ListSelectionEvent e) {
        if (!e.getValueIsAdjusting()) {
          ListSelectionModel model = getView().getBundleTable().getSelectionModel();
          int viewRowIndex = model.getMinSelectionIndex();
          if (viewRowIndex < 0) {
            return;
          }
          int selectedRow = getView().getBundleTable().convertRowIndexToModel(viewRowIndex);
          Bundle bundle = getModel().getBundles().get(selectedRow);
          getModel().setSelectedBundle(bundle);
        }
      }
    });

    getView().getBundleTable().addMouseListener(new MouseAdapter() {

      public void mousePressed(MouseEvent e) {
        maybeShowPopup(e);
      }

      public void mouseReleased(MouseEvent e) {
        maybeShowPopup(e);
      }

      private void maybeShowPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
          getView().getPopupMenu().show(e.getComponent(), e.getX(), e.getY());
        }
      }
    });

    getView().getStartBundleMenuItem().addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        getModel().startSelectedBundle();
      }
    });

    getView().getStopBundleMenuItem().addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        getModel().stopSelectedBundle();
      }
    });

    getView().getUninstallBundleMenuItem().addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        getModel().uninstallSelectedBundle();
      }
    });

    getView().getUpdateBundleMenuItem().addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        getModel().updateSelectedBundle();
      }
    });
  }
}
