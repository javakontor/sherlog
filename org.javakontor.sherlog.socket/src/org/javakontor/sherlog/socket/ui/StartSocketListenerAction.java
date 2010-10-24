package org.javakontor.sherlog.socket.ui;

import static java.lang.String.format;
import static org.javakontor.sherlog.socket.ui.SocketListenerMessages.socketListenerStatusBarPanelToolTip;

import java.io.IOException;

import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import org.javakontor.sherlog.application.action.AbstractToggleAction;
import org.javakontor.sherlog.application.contrib.ApplicationStatusBarContribution;
import org.javakontor.sherlog.domain.reader.LogEventFlavour;
import org.javakontor.sherlog.domain.reader.LogEventReaderFactory;
import org.javakontor.sherlog.domain.store.ModifiableLogEventStore;
import org.javakontor.sherlog.socket.SocketListener;
import org.javakontor.sherlog.socket.SocketListenerStateChangeListener;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StartSocketListenerAction extends AbstractToggleAction {

  Logger                                _logger                = LoggerFactory.getLogger(getClass());

  private final ModifiableLogEventStore _modifiableLogEventStore;

  private LogEventReaderFactory         _logEventReaderFactory;

  private SocketListener                _socketListener;

  private final BundleContext           _bundleContext;

  private ServiceRegistration           _statusBarContributionRegistration;

  private final JLabel                  _statusBarLabel        = new JLabel("Listening on 4445");

  private final ToolTipUpdateListener   _toolTipUpdateListener = new ToolTipUpdateListener();

  public StartSocketListenerAction(ModifiableLogEventStore modifiableLogEventStore, BundleContext bundleContext) {
    super();
    _modifiableLogEventStore = modifiableLogEventStore;
    _bundleContext = bundleContext;
  }

  @Override
  protected void activate() {
    _logger.info("Starting ServerSocket");
    LogEventFlavour logEventFlavour = getLogEventFlavour();
    try {
      _socketListener = new SocketListener(4445, logEventFlavour, _modifiableLogEventStore, _logEventReaderFactory);
    } catch (IOException ioe) {
      _logger.error("Could not start SocketListener: " + ioe, ioe);
      return;
    }
    _socketListener.start();
    _socketListener.addSocketListenerStateChangeListener(_toolTipUpdateListener);

    _logger.info("Listener-Thread gestartet");

    _statusBarContributionRegistration = registerStatusBarContribution();
  }

  @Override
  public void deactivate() {
    stopSocketListener();

    unregisterStatusBarContribution();
  }

  private void stopSocketListener() {
    if (_socketListener != null) {
      _socketListener.removeSocketListenerStateChangeListener(_toolTipUpdateListener);
      _socketListener.shutdown();
      _socketListener = null;
    }
  }

  private void unregisterStatusBarContribution() {
    _statusBarContributionRegistration.unregister();
  }

  private LogEventFlavour getLogEventFlavour() {
    // TODO
    LogEventFlavour[] supportedLogEventFlavours = _logEventReaderFactory.getSupportedLogEventFlavours();
    for (LogEventFlavour logEventFlavour : supportedLogEventFlavours) {
      if (logEventFlavour.getType() == LogEventFlavour.BINARY) {
        return logEventFlavour;
      }
    }

    throw new RuntimeException("No binary flavour found");
  }

  public LogEventReaderFactory getLogEventReaderFactory() {
    return _logEventReaderFactory;
  }

  public void setLogEventReaderFactory(LogEventReaderFactory logEventReaderFactory) {
    _logEventReaderFactory = logEventReaderFactory;
    setEnabled(isActive() || _logEventReaderFactory != null);
  }

  private ServiceRegistration registerStatusBarContribution() {
    return _bundleContext.registerService(ApplicationStatusBarContribution.class.getName(),
        new ApplicationStatusBarContribution() {

          public JComponent getComponent() {
            return _statusBarLabel;
          }
        }, null);
  }

  class ToolTipUpdateListener implements SocketListenerStateChangeListener {

    public void stateChanged() {
      SwingUtilities.invokeLater(new Runnable() {
        public void run() {
          _statusBarLabel.setToolTipText(format(socketListenerStatusBarPanelToolTip, 4445,
              _socketListener.getActiveConnections()));
        }
      });
    }
  };

}
