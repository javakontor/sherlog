package org.javakontor.sherlog.ui.managementagent;

import java.awt.datatransfer.DataFlavor;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.io.File;
import java.util.Iterator;
import java.util.List;

import org.javakontor.sherlog.ui.managementagent.tableview.BundleListModel;

public class BundleInstallDropTargetListener implements DropTargetListener {

  private final BundleListModel _model;

  public BundleInstallDropTargetListener(BundleListModel model) {
    super();
    this._model = model;
  }

  public void dragEnter(DropTargetDragEvent dropTargetDragEvent) {
    if ((dropTargetDragEvent.getSourceActions() & DnDConstants.ACTION_COPY) == 0) {
      dropTargetDragEvent.rejectDrag();
      return;

    }

    if (!dropTargetDragEvent.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
      dropTargetDragEvent.rejectDrag();
      return;
    }

  }

  public void dragExit(DropTargetEvent dte) {
  }

  public void dragOver(DropTargetDragEvent dtde) {
  }

  @SuppressWarnings("unchecked")
  public void drop(DropTargetDropEvent dropTargetDropEvent) {
    try {
      dropTargetDropEvent.acceptDrop(DnDConstants.ACTION_COPY);
      final List<File> fileList = (List<File>) dropTargetDropEvent.getTransferable().getTransferData(
          DataFlavor.javaFileListFlavor);
      dropTargetDropEvent.dropComplete(true);

      for (final Iterator<File> iter = fileList.iterator(); iter.hasNext();) {
        File file = iter.next();
        if ("manifest.mf".equalsIgnoreCase(file.getName())) {
          file = file.getParentFile().getParentFile();
        }
        if (file.isDirectory() && "meta-inf".equalsIgnoreCase(file.getName())) {
          file = file.getParentFile();
        }
        _model.installNewBundle(file);
      }
    } catch (Exception ex) {
      ex.printStackTrace();
      dropTargetDropEvent.dropComplete(false);
    }

  }

  public void dropActionChanged(DropTargetDragEvent dtde) {
  }

}
