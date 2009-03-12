package org.javakontor.sherlog.ui.loadwizard.filechooser;

import javax.swing.DefaultComboBoxModel;

import org.javakontor.sherlog.core.reader.LogEventFlavour;

class LogEventFlavourComboBoxModel extends DefaultComboBoxModel {

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public void setLogEventFlavours(LogEventFlavour[] flavours) {
    removeAllElements();
    for (LogEventFlavour flavour : flavours) {
      addElement(flavour);
    }
  }
}