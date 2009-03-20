package org.javakontor.sherlog.ui.histogram;

import javax.swing.JFrame;

import org.javakontor.sherlog.domain.impl.internal.store.LogStoreComponent;
import org.javakontor.sherlog.domain.store.LogEventStore;
import org.javakontor.sherlog.ui.histogram.HistogramPanel;
import org.javakontor.sherlog.ui.histogram.test.mock.LogEventGenerator;

import junit.framework.TestCase;

public class HistogramPanelTest extends TestCase {

  private JFrame _frame;
  private HistogramPanel _histogramPanel;

  @Override
  protected void setUp() throws Exception {
    _frame = new JFrame();
    _histogramPanel = new HistogramPanel();
    _frame.setContentPane(_histogramPanel);
    _frame.pack();
    _frame.setVisible(true);
  }

  @Override
  protected void tearDown() throws Exception {
    _frame.setVisible(false);
    _frame.dispose();
    _frame = null;
  }

  public void testNoEvents() throws Exception {
    // TODO: assert NO_EVENTS_PANEL
    Thread.sleep(5000);
  }

  public void testEvents() throws Exception {
    for (int i = 0; i < 15; i++) {
      _histogramPanel.setLogEvents(LogEventGenerator.generateLogEventList(500));
      Thread.sleep(500);
      _histogramPanel.setLogEvents(null);
      Thread.sleep(5000);
    }
  }
}
