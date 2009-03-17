package org.javakontor.sherlog.ui.simplefilter.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.javakontor.sherlog.core.LogLevel;
import org.javakontor.sherlog.application.mvc.AbstractController;
import org.javakontor.sherlog.application.request.RequestHandler;

public class SimpleFilterConfigurationController extends
    AbstractController<SimpleFilterConfigurationModel, SimpleFilterConfigurationView> {

  public SimpleFilterConfigurationController(SimpleFilterConfigurationModel model, SimpleFilterConfigurationView view,
      RequestHandler successor) {
    super(model, view, successor);

    initializeListener();
  }

  public SimpleFilterConfigurationController(SimpleFilterConfigurationModel model, SimpleFilterConfigurationView view) {
    super(model, view);

    initializeListener();
  }

  private void initializeListener() {

    getView().getLevelComboBox().addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        LogLevel logLevel = (LogLevel) getView().getLevelComboBox().getSelectedItem();
        getModel().setLogLevel(logLevel);
      }
    });

    getView().getThreadComboBox().addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        String threadName = (String) getView().getThreadComboBox().getSelectedItem();
        threadName = threadName != null && "".equals(threadName.trim()) ? null : threadName;
        getModel().setThreadName(threadName);
      }
    });

    getView().getCategoryComboBox().addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        getModel().setCategory((String) getView().getCategoryComboBox().getSelectedItem());
      }
    });

    getView().getMessageComboBox().addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        getModel().setMessage((String) getView().getMessageComboBox().getSelectedItem());
      }
    });
  }
}
