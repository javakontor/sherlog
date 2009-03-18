package org.javakontor.sherlog.ui.loadwizard.filechooser;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.io.File;

import javax.swing.JFileChooser;

import org.javakontor.sherlog.application.mvc.AbstractController;
import org.javakontor.sherlog.application.request.RequestHandler;
import org.javakontor.sherlog.domain.reader.LogEventFlavour;

public class LogFileChooserController extends AbstractController<LogFileChooserModel, LogFileChooserView> {

  public LogFileChooserController(LogFileChooserModel model, LogFileChooserView view, RequestHandler successor) {
    super(model, view, successor);

    createListener();
  }

  public void commit() {
    commitFileNameField();
    commitLogEventFlavourBox();
  }

  protected void commitLogEventFlavourBox() {
    Object object = getView().getFlavourBox().getSelectedItem();
    getModel().setSelectedLogEventFlavour((LogEventFlavour) object);
  }

  protected void commitFileNameField() {
    getModel().setFileName(getView().getFileNameField().getText());
  }

  protected void createListener() {

    // "File chooser" button
    getView().getFileChooserButton().addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        File selectedLogFile = getModel().getSelectedLogFile();
        File directory = (selectedLogFile != null ? selectedLogFile.getParentFile() : getDefaultDirectory());

        JFileChooser chooser = new JFileChooser(directory);
        int result = chooser.showOpenDialog(getView());
        if (result == JFileChooser.APPROVE_OPTION) {
          File selectedFile = chooser.getSelectedFile();
          getModel().setFileName(selectedFile.getAbsolutePath());
        }
      }
    });

    // save file name to model when user leaves filename textfield
    getView().getFileNameField().addFocusListener(new FocusAdapter() {
      @Override
      public void focusLost(FocusEvent e) {
        commitFileNameField();
      }
    });

    getView().getFlavourBox().addFocusListener(new FocusAdapter() {
      @Override
      public void focusLost(FocusEvent e) {
        commitLogEventFlavourBox();
      }

    });

    // // set Flavour to model when user leaves the Flavour ComboBox
    // getView().getFlavourBox().addActionListener(new ActionListener() {
    //
    // public void actionPerformed(ActionEvent e) {
    // Object object = getView().getFlavourBox().getSelectedItem();
    // getModel().setSelectedLogEventFlavour((LogEventFlavour) object);
    // }
    // });

  }

  protected File getDefaultDirectory() {
    return new File(System.getProperty("user.dir"));
  }
}
