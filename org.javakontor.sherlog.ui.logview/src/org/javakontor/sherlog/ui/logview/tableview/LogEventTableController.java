package org.javakontor.sherlog.ui.logview.tableview;

import java.util.LinkedList;
import java.util.List;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.javakontor.sherlog.core.LogEvent;
import org.lumberjack.application.mvc.AbstractController;
import org.lumberjack.application.request.RequestHandler;

public class LogEventTableController extends AbstractController<LogEventTableModel, LogEventTableView> {

  public LogEventTableController(LogEventTableModel model, LogEventTableView view, RequestHandler successor) {
    super(model, view, successor);

    initializeListener();
  }

  public LogEventTableController(LogEventTableModel model, LogEventTableView view) {
    super(model, view);

    initializeListener();
  }

  private void initializeListener() {
    getView().getLogEventTableTable().getSelectionModel().addListSelectionListener(new LogEventSelectionListener());
  }

  // AbstractContextMenuListener _popupListener;
  //
  // public AbstractContextMenuListener getContextMenuListener() {
  // return this._popupListener;
  // }

  class LogEventSelectionListener implements ListSelectionListener {

    public void valueChanged(ListSelectionEvent event) {

      // Ignore extra messages.
      if (event.getValueIsAdjusting()) {
        return;
      }

      ListSelectionModel model = (ListSelectionModel) event.getSource();
      LogEvent[] selectedEvents = getSelectedEvents(model);
      getModel().setSelectedLogEvents(selectedEvents);
    }
  }

  /**
   * Listen to changes in the table selection and forward the selected elements to the model
   * 
   */
  protected LogEvent[] getSelectedEvents(ListSelectionModel selectionModel) {
    int minIndex = selectionModel.getMinSelectionIndex();
    int maxIndex = selectionModel.getMaxSelectionIndex();

    final List<LogEvent> events = new LinkedList<LogEvent>();

    for (int i = minIndex; i <= maxIndex; i++) {
      if (selectionModel.isSelectedIndex(i)) {
        events.add(getModel().getLogEventStore().getFilteredLogEvents().get(i));
      }
    }

    // TODO

    return events.toArray(new LogEvent[0]);
  }
  
  // private ContextMenu _contextMenu;
  //
  // private ContextMenuListener _popupListener;
  //  
  // private void createListener() {
  // // getView().getLogEventListView().addTableSelectionListener(new LogEventSelectionListener());
  //
  // this._contextMenu = new ContextMenu(getModel().getBundleContext(), "logEventView.contextMenu");
  // this._contextMenu.open();
  //
  // // enable popup menu
  // this._popupListener = new ContextMenuListener(this._contextMenu);
  // getView().getLogEventTableView().getLogEventTableTable().addMouseListener(this._popupListener);
  // }
  //
  // @Override
  // public void close() {
  // super.close();
  // if (this._contextMenu != null) {
  // this._contextMenu.close();
  // this._contextMenu = null;
  // }
  // }
  //
  // class ContextMenuListener extends AbstractContextMenuListener {
  //
  // public ContextMenuListener(ContextMenu contextMenu) {
  // super(contextMenu);
  // }
  //
  // @Override
  // protected Object getActionContext() {
  // return getModel();
  // }
  //
  // @Override
  // protected void afterMenu() {
  // getView().getLogEventTableView().getLogEventTableTable().repaint();
  // }
  // }
}
