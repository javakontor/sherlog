package org.javakontor.sherlog.ui.loadwizard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import org.javakontor.sherlog.ui.loadwizard.filechooser.LogFileChooserController;
import org.lumberjack.application.mvc.AbstractController;
import org.lumberjack.application.request.CloseDialogRequest;
import org.lumberjack.application.request.RequestHandler;

public class LoadLogFileWizardController extends AbstractController<LoadLogFileWizardModel, LoadLogFileWizardView> {

  private final LogFileChooserController _logFileChooserController;

  public LoadLogFileWizardController(LoadLogFileWizardModel model, LoadLogFileWizardView view, RequestHandler successor) {
    super(model, view, successor);

    this._logFileChooserController = new LogFileChooserController(model.getLogFileChooserModel(), view
        .getLogFileChooserView(), successor);

    setupListener();
  }

  public LoadLogFileWizardController(LoadLogFileWizardModel model, LoadLogFileWizardView view) {
    super(model, view);
    this._logFileChooserController = new LogFileChooserController(model.getLogFileChooserModel(), view
        .getLogFileChooserView(), null);

    setupListener();
  }

  private void setupListener() {
    getView().getCancelButton().addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        CloseDialogRequest request = new CloseDialogRequest(CloseDialogRequest.CANCEL);
        handleRequest(request);
      }
    });

    getView().getOkButton().addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {

        ValidationResult result = getModel().validateForm();

        if (result.isValid()) {
          try {
            getModel().loadLogFile();
          } catch (Exception ex) {
            JOptionPane.showMessageDialog(getView(), ex.getMessage(), LoadLogFileWizardMessages.couldNotLoadLogFile,
                JOptionPane.ERROR_MESSAGE);
            return;
          }
          // TODO load logfile
          CloseDialogRequest request = new CloseDialogRequest(CloseDialogRequest.OK);
          handleRequest(request);
          return;
        }

        JOptionPane.showMessageDialog(getView(), result.getErrorMessage(),
            LoadLogFileWizardMessages.couldNotLoadLogFile, JOptionPane.ERROR_MESSAGE);
        return;

      }
    });
  }

  public LogFileChooserController getLogFileChooserController() {
    return this._logFileChooserController;
  }

}
