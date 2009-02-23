package org.javakontor.sherlog.ui.loadwizzard;

import java.awt.Component;

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

  private JTextField        _fileNameField;

  private JButton           _fileChooserButton;

  private JComboBox         _flavourBox;

  /**
   * 
   */
  private static final long serialVersionUID = 1L;

  public LogFileChooserView(LogFileChooserModel model) {
    super(model);
  }

  public void modelChanged(ModelChangedEvent<LogFileChooserModel, DefaultReasonForChange> event) {
    this._fileNameField.setText(getModel().getFileName());
    this._flavourBox.setSelectedItem(getModel().getSelectedFlavour());
  }

  private void createComponents() {
    this._fileNameField = new JTextField();
    this._fileNameField.setText(getModel().getFileName());
    this._fileChooserButton = new JButton("...");
    this._flavourBox = new JComboBox(getModel().getSupportedFlavours());
    this._flavourBox.setEditable(false);
    this._flavourBox.setSelectedItem(getModel().getSelectedFlavour());
    FlavourCellRenderer renderer = new FlavourCellRenderer(this._flavourBox.getRenderer());
    this._flavourBox.setRenderer(renderer);
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
    builder.add(this._flavourBox, cc.rc(3, 3));
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
    return this._flavourBox;
  }

  public void setFlavourBox(JComboBox flavourBox) {
    this._flavourBox = flavourBox;
  }

}
