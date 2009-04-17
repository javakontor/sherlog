package org.javakontor.sherlog.test.ui.handler;

import javax.swing.ComboBoxModel;

import org.javakontor.sherlog.domain.reader.LogEventFlavour;
import org.javakontor.sherlog.test.ui.framework.AbstractViewContributionHandler;
import org.javakontor.sherlog.test.ui.framework.GuiTestContext;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JComboBoxOperator;
import org.netbeans.jemmy.operators.JTextFieldOperator;
import org.netbeans.jemmy.operators.WindowOperator;

public class LogFileChooserViewHandler extends AbstractViewContributionHandler {

  private final JTextFieldOperator _fileNameTextFieldOperator;

  private final JButtonOperator    _selectFileButtonOperator;

  private final JComboBoxOperator  _logEventFlavourComboBoxOperator;

  LogFileChooserViewHandler(GuiTestContext testContext, WindowOperator containerOperator) {
    super(testContext);
    _fileNameTextFieldOperator = new JTextFieldOperator(containerOperator);
    _selectFileButtonOperator = new JButtonOperator(containerOperator, "...");
    _logEventFlavourComboBoxOperator = new JComboBoxOperator(containerOperator);
  }

  public JTextFieldOperator getFileNameTextFieldOperator() {
    return _fileNameTextFieldOperator;
  }

  public void enterFileName(String fileName) {
    _fileNameTextFieldOperator.typeText(fileName);
  }

  public void selectLogEventFlavour(String eventFlavour) {
    _logEventFlavourComboBoxOperator.clickMouse();
    ComboBoxModel model = _logEventFlavourComboBoxOperator.getModel();
    int index = -1;
    for (int i = 0; i < model.getSize(); i++) {
      LogEventFlavour logEventFlavour = (LogEventFlavour) model.getElementAt(i);
      if (logEventFlavour.getDescription().toLowerCase().startsWith(eventFlavour.toLowerCase())) {
        index = i;
        break;
      }
    }
    _logEventFlavourComboBoxOperator.selectItem(index);
  }

  public JButtonOperator getSelectFileButtonOperator() {
    return _selectFileButtonOperator;
  }

  public JComboBoxOperator getLogEventFlavourComboBoxOperator() {
    return _logEventFlavourComboBoxOperator;
  }

}
