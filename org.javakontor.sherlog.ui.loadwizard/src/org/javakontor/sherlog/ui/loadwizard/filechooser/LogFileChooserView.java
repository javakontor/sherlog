package org.javakontor.sherlog.ui.loadwizard.filechooser;

import java.awt.Component;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JList;
import javax.swing.JTextField;
import javax.swing.ListCellRenderer;

import org.javakontor.sherlog.core.reader.LogEventFlavour;
import org.lumberjack.application.mvc.AbstractView;
import org.lumberjack.application.mvc.DefaultReasonForChange;
import org.lumberjack.application.mvc.ModelChangedEvent;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class LogFileChooserView extends AbstractView<LogFileChooserModel, DefaultReasonForChange> {

  private JTextField                   _fileNameField;

  private JButton                      _fileChooserButton;

  private JComboBox                    _logEventFlavourComboBox;

  private LogEventFlavourComboBoxModel _logEventFlavourComboBoxModel;

  /**
   * 
   */
  private static final long            serialVersionUID = 1L;

  public LogFileChooserView(LogFileChooserModel model) {
    super(model);
  }

  public void modelChanged(ModelChangedEvent<LogFileChooserModel, DefaultReasonForChange> event) {
    updateView();
  }

  protected void updateView() {
    this._fileNameField.setText(getModel().getFileName());
    this._logEventFlavourComboBoxModel.setLogEventFlavours(getModel().getSupportedLogEventFlavours());
    this._logEventFlavourComboBoxModel.setSelectedItem(getModel().getSelectedLogEventFlavour());
  }

  private void createComponents() {
    this._fileNameField = new JTextField();
    // this._fileNameField.setText(getModel().getFileName());
    this._fileChooserButton = new JButton("...");
    this._logEventFlavourComboBoxModel = new LogEventFlavourComboBoxModel();
    this._logEventFlavourComboBox = new JComboBox(this._logEventFlavourComboBoxModel);
    this._logEventFlavourComboBox.setEditable(false);
    FlavourCellRenderer renderer = new FlavourCellRenderer(this._logEventFlavourComboBox.getRenderer());
    this._logEventFlavourComboBox.setRenderer(renderer);
  }

  class FlavourCellRenderer implements ListCellRenderer {

    private final ListCellRenderer _defaultRenderer;

    public FlavourCellRenderer(ListCellRenderer defaultRenderer) {
      this._defaultRenderer = defaultRenderer;
    }

    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected,
        boolean cellHasFocus) {

      LogEventFlavour flavour = (LogEventFlavour) value;
      String string = (flavour == null ? "" : flavour.getDescription());

      return this._defaultRenderer.getListCellRendererComponent(list, string, index, isSelected, cellHasFocus);

    }

  }

  @Override
  protected void setUp() {

    // Create the gui components
    createComponents();

    // create the layout
    FormLayout formLayout = new FormLayout( //
        "50dlu,5dlu,fill:100dlu:grow,5dlu,left:pref", // cols
        "p, 3dlu, p" // rows
    );

    PanelBuilder builder = new PanelBuilder(formLayout, this);

    // add components to layout
    // 1st row
    CellConstraints cc = new CellConstraints();
    builder.addLabel("Log File:", cc.rc(1, 1));
    builder.add(this._fileNameField, cc.rc(1, 3, "fill,default"));
    builder.add(this._fileChooserButton, cc.rc(1, 5));

    // 2dn row
    builder.addLabel("Flavour:", cc.rc(3, 1));
    builder.add(this._logEventFlavourComboBox, cc.rc(3, 3));

    // fill form with initial data from view
    updateView();
  }

  public JTextField getFileNameField() {
    return this._fileNameField;
  }

  public void setFileNameField(JTextField fileNameField) {
    this._fileNameField = fileNameField;
  }

  public JButton getFileChooserButton() {
    return this._fileChooserButton;
  }

  public void setFileChooserButton(JButton fileChooserButton) {
    this._fileChooserButton = fileChooserButton;
  }

  public JComboBox getFlavourBox() {
    return this._logEventFlavourComboBox;
  }

  public void setFlavourBox(JComboBox flavourBox) {
    this._logEventFlavourComboBox = flavourBox;
  }

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

}
