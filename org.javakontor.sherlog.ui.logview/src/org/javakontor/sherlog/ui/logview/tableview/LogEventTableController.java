package org.javakontor.sherlog.ui.logview.tableview;

import java.util.LinkedList;
import java.util.List;

import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import org.javakontor.sherlog.core.LogEvent;
import org.javakontor.sherlog.ui.util.SherlogTable;
import org.lumberjack.application.action.ActionSet;
import org.lumberjack.application.menu.AbstractContextMenuListener;
import org.lumberjack.application.menu.ContextMenu;
import org.lumberjack.application.mvc.AbstractController;
import org.lumberjack.application.request.RequestHandler;

public class LogEventTableController extends AbstractController<LogEventTableModel, LogEventTableView> {

  private ContextMenu         _contextMenu;

  private ContextMenuListener _contextMenuListener;

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

    // "logEventView.contextMenu"
    this._contextMenu = new ContextMenu();

    // enable popup menu
    _contextMenuListener = new ContextMenuListener(this._contextMenu);
    getView().getLogEventTableTable().addMouseListener(this._contextMenuListener);
  }

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

    SherlogTable sherlogTable = getView().getLogEventTableTable();

    for (int i = minIndex; i <= maxIndex; i++) {
      if (selectionModel.isSelectedIndex(i)) {
        int convertedIndex = sherlogTable.convertRowIndexToModel(i);
        events.add(getModel().getLogEventStore().getFilteredLogEvents().get(convertedIndex));
      }
    }

    // TODO

    return events.toArray(new LogEvent[0]);
  }

  class ContextMenuListener extends AbstractContextMenuListener {

    public ContextMenuListener(ContextMenu contextMenu) {
      super(contextMenu);
    }

    @Override
    protected Object getActionContext() {
      return getModel();
    }

    @Override
    protected ActionSet getActionGroupRegistry() {
      return getModel().getActionGroupRegistry();
    }

    @Override
    protected void afterMenu() {
      getView().getLogEventTableTable().repaint();
    }

    // @Override
    // protected boolean showPopupMenu() {
    // return getModel().getSelectedLogEvents().length > 0;
    // }
  }
}
