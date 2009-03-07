package org.javakontor.sherlog.ui.loadwizard;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.MalformedURLException;
import java.net.URL;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.javakontor.sherlog.core.reader.LogEventFlavour;
import org.javakontor.sherlog.core.reader.LogEventReader;
import org.javakontor.sherlog.core.reader.LogEventReaderFactory;
import org.javakontor.sherlog.ui.loadwizard.filechooser.LogFileChooserController;
import org.javakontor.sherlog.ui.loadwizard.filechooser.LogFileChooserModel;
import org.javakontor.sherlog.ui.loadwizard.filechooser.LogFileChooserView;
import org.lumberjack.application.request.RequestHandlerImpl;

import com.jgoodies.forms.builder.PanelBuilder;
import com.jgoodies.forms.factories.ButtonBarFactory;
import com.jgoodies.forms.layout.CellConstraints;
import com.jgoodies.forms.layout.FormLayout;

// TODO: MVC
public class LoadLogFileWizard extends JDialog {

  private final LogEventReaderFactory _logEventReaderFactory;

  private JButton                     _okButton;

  private JButton                     _cancelButton;

  private LogFileChooserModel         _logFileChooserModel;

  /** The selected {@link LogEventReader} or null if cancel was pressed */
  private LogEventReader              _logEventReader;

  /**
   * 
   */
  private static final long           serialVersionUID = 1L;

  public static LogEventReader openLoadLogFileWizard(Window owner, LogEventReaderFactory logEventReaderFactory,
      String preselectedFile) {
    LoadLogFileWizard dialog;
    if (owner instanceof JFrame) {
      dialog = new LoadLogFileWizard((JFrame) owner, logEventReaderFactory);
    } else {
      dialog = new LoadLogFileWizard((JDialog) owner, logEventReaderFactory);
    }
    if (preselectedFile != null) {
      dialog.getLogFileChooserModel().setFileName(preselectedFile);
    }
    dialog.setVisible(true);
    return dialog.getLogEventReader();
  }

  public static LogEventReader openLoadLogFileWizard(Window owner, LogEventReaderFactory logEventReaderFactory) {
    return openLoadLogFileWizard(owner, logEventReaderFactory, null);
  }

  public LoadLogFileWizard(JFrame owner, LogEventReaderFactory logEventReaderFactory) {
    super(owner);
    this._logEventReaderFactory = logEventReaderFactory;
    setUp();
  }

  public LoadLogFileWizard(JDialog dialog, LogEventReaderFactory logEventReaderFactory) {
    super(dialog);
    this._logEventReaderFactory = logEventReaderFactory;
    setUp();
  }

  protected void setUp() {
    setTitle(LoadLogFileWizardMessages.loadLogFile);
    setModal(true);

    this._logFileChooserModel = new LogFileChooserModel(this._logEventReaderFactory.getSupportedLogEventFlavours());
    LogFileChooserView view = new LogFileChooserView(this._logFileChooserModel);
    new LogFileChooserController(this._logFileChooserModel, view, new LogFileOpenDialogHandler());

    JPanel contentPanel = new JPanel();
    contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    FormLayout layout = new FormLayout("fill:pref:grow", "fill:pref:grow,15dlu,bottom:min,5dlu,bottom:pref");
    contentPanel.setLayout(layout);

    // ~~ build the buttons and the button bar
    this._okButton = new JButton(LoadLogFileWizardMessages.ok);
    // this._okButton.addFocusListener(new DefaultButtonFocusListener());
    getRootPane().setDefaultButton(this._okButton);
    // getRootPane().addFocusListener(new FocusListener() {
    //
    // public void focusLost(FocusEvent e) {
    //
    // System.err.println("focusLost: " + e);
    // }
    //
    // public void focusGained(FocusEvent e) {
    // System.err.println("focusGained: " + e);
    //
    // }
    // });
    // this._okButton.setEnabled(this._logFileChooserModel.isFormValid());
    this._cancelButton = new JButton(LoadLogFileWizardMessages.cancel);
    this._cancelButton.addFocusListener(new DefaultButtonFocusListener());
    PanelBuilder builder = new PanelBuilder(layout, contentPanel);
    CellConstraints cc = new CellConstraints();
    builder.add(view, cc.rc(1, 1));
    builder.addSeparator("", cc.rc(3, 1));
    builder.add(ButtonBarFactory.buildRightAlignedBar(this._okButton, this._cancelButton), cc.rc(5, 1));

    createListener();

    add(contentPanel);

    // layout components
    pack();

    // position dialog in the center of it's owning window
    setLocationRelativeTo(getOwner());
  }

  protected void createListener() {

    this._cancelButton.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        LoadLogFileWizard.this._logEventReader = null;
        setVisible(false);
      }
    });

    this._okButton.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        ValidationResult result = LoadLogFileWizard.this._logFileChooserModel.validateForm();
        if (result.hasError()) {
          JOptionPane.showMessageDialog(LoadLogFileWizard.this, result.getErrorMessage(),
              LoadLogFileWizardMessages.couldNotLoadLogFile, JOptionPane.ERROR_MESSAGE);
          return;
        }

        try {
          URL url = LoadLogFileWizard.this._logFileChooserModel.getSelectedLogFile().toURL();
          LogEventFlavour logEventFlavour = LoadLogFileWizard.this._logFileChooserModel.getSelectedLogEventFlavour();

          LoadLogFileWizard.this._logEventReader = LoadLogFileWizard.this._logEventReaderFactory.getLogEventReader(url,
              logEventFlavour);

          setVisible(false);
        } catch (MalformedURLException ex) {
          ex.printStackTrace();
        }
      }
    });

    // this._logFileChooserModel
    // .addModelChangedListener(new ModelChangedListener<LogFileChooserModel, DefaultReasonForChange>() {
    //
    // public void modelChanged(ModelChangedEvent<LogFileChooserModel, DefaultReasonForChange> event) {
    // LogFileOpenDialog.this._okButton.setEnabled(LogFileOpenDialog.this._logFileChooserModel.isFormValid());
    // }
    // });
    //
  }

  class LogFileOpenDialogHandler extends RequestHandlerImpl {

  }

  public LogFileChooserModel getLogFileChooserModel() {
    return this._logFileChooserModel;
  }

  public LogEventReader getLogEventReader() {
    return this._logEventReader;
  }

}
