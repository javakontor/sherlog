package org.javakontor.sherlog.test.ui.handler;

import javax.swing.JPopupMenu;

import org.javakontor.sherlog.test.ui.framework.AbstractViewHandler;
import org.netbeans.jemmy.operators.ContainerOperator;
import org.netbeans.jemmy.operators.JPopupMenuOperator;
import org.netbeans.jemmy.operators.JTableOperator;
import org.netbeans.jemmy.util.NameComponentChooser;

public class LogEventTableViewHandler extends AbstractViewHandler {

  private final ContainerOperator _logEventTableViewContainerOperator;

  private final JTableOperator    _logEventTableTableOperator;

  public LogEventTableViewHandler(ContainerOperator parentWindowOperator) {
    super();

    _logEventTableViewContainerOperator = new ContainerOperator(parentWindowOperator, new NameComponentChooser(
        "LogEventTableView"));

    _logEventTableTableOperator = new JTableOperator(_logEventTableViewContainerOperator);
  }

  public ContainerOperator getLogEventTableViewContainerOperator() {
    return _logEventTableViewContainerOperator;
  }

  public JTableOperator getLogEventTableTableOperator() {
    return _logEventTableTableOperator;
  }

  public void selectRow(int row) {
    getLogEventTableTableOperator().selectCell(row, 0);
  }

  public JPopupMenuOperator openContextMenu(int row) {
    JPopupMenu popupMenu = _logEventTableTableOperator.callPopupOnCell(row, 0);
    return new JPopupMenuOperator(popupMenu);
    // getLogEventTableTableOperator().clickForPopup();
    // JPopupMenuOperator popupMenuOperator = new JPopupMenuOperator(_logEventTableViewContainerOperator);
    // return popupMenuOperator;
  }

}
