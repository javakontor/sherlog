package org.javakontor.sherlog.ui.histogram;

import javax.swing.JPanel;

import org.javakontor.sherlog.application.view.AbstractViewContribution;
import org.javakontor.sherlog.application.view.DefaultViewContributionDescriptor;
import org.javakontor.sherlog.domain.store.LogEventStore;
import org.javakontor.sherlog.domain.store.LogEventStoreEvent;
import org.javakontor.sherlog.domain.store.LogEventStoreListener;

public class LoggraphViewContribution extends AbstractViewContribution {

  private HistogramPanel        _panel;

  private LogEventStore         _logEventStore;

  private LogEventStoreListener _logEventStoreListener = new LogEventStoreListener() {

                                                         public void logEventStoreChanged(LogEventStoreEvent event) {
                                                           if (_logEventStore != null) {
                                                             _panel.setLogEvents(_logEventStore.getFilteredLogEvents());
                                                           }
                                                         }
                                                       };

  public LoggraphViewContribution() {
    _panel = new HistogramPanel();
  }

  public DefaultViewContributionDescriptor getDescriptor() {
    return new DefaultViewContributionDescriptor("Log Event Histogram", false, true, false, true, false);
  }

  public JPanel getPanel() {
    return _panel;
  }

  public void bindLogEventStore(LogEventStore logEventStore) {
    releaseLogEventStore();

    _logEventStore = logEventStore;
    _logEventStore.addLogStoreListener(_logEventStoreListener);

    _panel.setLogEvents(_logEventStore.getFilteredLogEvents());
  }

  public void unbindLogEventStore(LogEventStore logEventStore) {
    releaseLogEventStore();

    _panel.setLogEvents(null);
  }

  private void releaseLogEventStore() {
    if (_logEventStore != null) {
      _logEventStore.removeLogStoreListener(_logEventStoreListener);
    }
    _logEventStore = null;
  }
}
