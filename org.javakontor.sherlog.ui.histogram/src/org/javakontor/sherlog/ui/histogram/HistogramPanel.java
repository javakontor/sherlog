package org.javakontor.sherlog.ui.histogram;

import java.awt.CardLayout;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.javakontor.sherlog.domain.LogEvent;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.TickUnitSource;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.IntervalXYDataset;

public class HistogramPanel extends JPanel {

  private static final String NO_EVENTS_PANEL = "NO_EVENTS_PANEL";

  private static final String CHART_PANEL     = "CHART_PANEL";

  private CardLayout          _cardLayout;

  private JPanel              _noEventsPanel;

  private ChartPanel          _chartPanel;

  private HistogramDataset    _dataset;

  public HistogramPanel() {
    super();

    _cardLayout = new CardLayout();
    setLayout(_cardLayout);

    _noEventsPanel = new JPanel();
    _noEventsPanel.add(new JLabel("No log events available"));

    JFreeChart _chart = createChart(createDataset(new double[] { 0.0 }));
    _chartPanel = new ChartPanel(_chart);

    add(_noEventsPanel, NO_EVENTS_PANEL);
    add(_chartPanel, CHART_PANEL);
  }

  /**
   * @param logEvents
   */
  public void setLogEvents(List<LogEvent> logEvents) {

    System.err.println("setLogEvents(" + logEvents + ")");
    if (logEvents == null) {
      logEvents = new LinkedList<LogEvent>();
    }
    // if (logEvents == null || logEvents.isEmpty()) {
    // _cardLayout.show(this, NO_EVENTS_PANEL);
    // } else {
    double[] values = new double[logEvents.size()];
    for (int j = 0; j < values.length; j++) {
      values[j] = logEvents.get(j).getTimeStamp();
    }
    _chartPanel.setChart(createChart(createDataset(values)));
    _cardLayout.show(this, CHART_PANEL);
    // }
  }

  /**
   * Creates a sample {@link HistogramDataset}.
   * 
   * @return the dataset.
   */
  private IntervalXYDataset createDataset(double[] values) {
    _dataset = new HistogramDataset();
    if (values.length > 0) {
      _dataset.addSeries("Log Events", values, 1000);
    }
    return _dataset;
  }

  /**
   * Creates a chart.
   * 
   * @param dataset
   *          a dataset.
   * 
   * @return The chart.
   */
  private JFreeChart createChart(IntervalXYDataset dataset) {
    JFreeChart chart = ChartFactory.createHistogram("Log Event Histogram", null, null, dataset,
        PlotOrientation.VERTICAL, true, true, false);
    XYPlot plot = (XYPlot) chart.getPlot();
    plot.setForegroundAlpha(0.85f);
    NumberAxis numberAxis = new NumberAxis("HALLO");
    TickUnitSource units = NumberAxis.createIntegerTickUnits();
    numberAxis.setStandardTickUnits(units);
    plot.setRangeAxis(numberAxis);
    XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
    renderer.setDrawBarOutline(false);
    // flat bars look best...
    // renderer.setBarPainter(new StandardXYBarPainter());
    // renderer.setShadowVisible(false);
    return chart;
  }
}
