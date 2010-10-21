package org.javakontor.sherlog.ui.loadwizard;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JRootPane;
import javax.swing.SwingUtilities;

import org.javakontor.sherlog.application.mvc.AbstractView;
import org.javakontor.sherlog.application.mvc.DefaultReasonForChange;
import org.javakontor.sherlog.ui.loadwizard.filechooser.LogFileChooserView;
import org.javakontor.sherlog.util.ui.DefaultButtonFocusListener;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

public class LoadLogFileWizardView extends AbstractView<LoadLogFileWizardModel, DefaultReasonForChange> {

  /**
   * 
   */
  private static final long  serialVersionUID = 1L;

  private LogFileChooserView _logFileChooserView;

  private JButton            _okButton;

  private JButton            _cancelButton;

  public LoadLogFileWizardView(LoadLogFileWizardModel model) {
    super(model);
  }

  @Override
  protected void setUp() {
    this._logFileChooserView = new LogFileChooserView(getModel().getLogFileChooserModel());

    setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    FormLayout layout = new FormLayout("fill:pref:grow", "fill:pref:grow,15dlu,bottom:min,5dlu,bottom:pref");
    setLayout(layout);

    // ~~ build the buttons and the button bar
    this._okButton = new JButton(LoadLogFileWizardMessages.ok);
    // getRootPane().setDefaultButton(this._okButton);
    this._cancelButton = new JButton(LoadLogFileWizardMessages.cancel);
    this._cancelButton.addFocusListener(new DefaultButtonFocusListener());
    PanelBuilder builder = new PanelBuilder(layout, this);
    CellConstraints cc = new CellConstraints();
    builder.add(this._logFileChooserView, cc.rc(1, 1));
    builder.addSeparator("", cc.rc(3, 1));
    builder.add(ButtonBarFactory.buildRightAlignedBar(this._okButton, this._cancelButton), cc.rc(5, 1));
  }

  public JButton getOkButton() {
    return this._okButton;
  }

  public JButton getCancelButton() {
    return this._cancelButton;
  }

  @Override
  public void setVisible(boolean visible) {
    if (visible) {
      setDefaultButton();
    }
    super.setVisible(visible);
  }

  public LogFileChooserView getLogFileChooserView() {
    return this._logFileChooserView;
  }

  protected void setDefaultButton() {
    JRootPane rootPane = SwingUtilities.getRootPane(this);
    if (rootPane != null) {
      rootPane.setDefaultButton(this._okButton);
    }
  }
}
