package org.javakontor.sherlog.ui.simplefilter.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import org.javakontor.sherlog.application.mvc.AbstractController;
import org.javakontor.sherlog.application.request.RequestHandler;
import org.javakontor.sherlog.domain.LogLevel;

public class SimpleFilterConfigurationController extends
    AbstractController<SimpleFilterConfigurationModel, SimpleFilterConfigurationView> {

  public SimpleFilterConfigurationController(final SimpleFilterConfigurationModel model,
      final SimpleFilterConfigurationView view, final RequestHandler successor) {
    super(model, view, successor);

    initializeListener();
  }

  public SimpleFilterConfigurationController(final SimpleFilterConfigurationModel model,
      final SimpleFilterConfigurationView view) {
    super(model, view);

    initializeListener();
  }

  private void initializeListener() {

    getView().getLevelComboBox().addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent e) {
        final LogLevel logLevel = (LogLevel) getView().getLevelComboBox().getSelectedItem();
        getModel().setLogLevel(logLevel);
      }
    });

    getView().getThreadComboBox().addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent e) {
        String threadName = (String) getView().getThreadComboBox().getSelectedItem();
        threadName = (threadName != null) && "".equals(threadName.trim()) ? null : threadName;
        getModel().setThreadName(threadName);
      }
    });

    getView().getCategoryComboBox().addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent e) {
        getModel().setCategory((String) getView().getCategoryComboBox().getSelectedItem());
      }
    });

    getView().getMessageComboBox().addActionListener(new ActionListener() {
      public void actionPerformed(final ActionEvent e) {
        getModel().setMessage((String) getView().getMessageComboBox().getSelectedItem());
      }
    });
  }
}
