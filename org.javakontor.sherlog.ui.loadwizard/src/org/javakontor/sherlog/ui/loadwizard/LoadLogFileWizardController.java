package org.javakontor.sherlog.ui.loadwizard;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import org.javakontor.sherlog.application.mvc.AbstractController;
import org.javakontor.sherlog.application.request.CloseDialogRequest;
import org.javakontor.sherlog.application.request.RequestHandler;
import org.javakontor.sherlog.ui.loadwizard.filechooser.LogFileChooserController;

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
        // commit all values from view to model
        commit();
        System.err.println(" * OK BUTTON PRESSED !");

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

  /**
   * Commits all data from the view(s) to the corresponding model(s)
   */
  public void commit() {
    getLogFileChooserController().commit();
  }

  public LogFileChooserController getLogFileChooserController() {
    return this._logFileChooserController;
  }
}
