package org.javakontor.sherlog.ui.histogram;

import java.util.List;

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

  private ChartPanel       _chartPanel;

  private HistogramDataset _dataset;

  public HistogramPanel() {
    super();

    JFreeChart _chart = createChart(createDataset(new double[] { 0.0 }));
    _chartPanel = new ChartPanel(_chart);

    add(_chartPanel);
  }

  /**
   * @param logEvents
   */
  public void setLogEvents(List<LogEvent> logEvents) {
    if (logEvents == null) {
      _chartPanel.setChart(createChart(createDataset(new double[] { 0.0 })));
    } else {
      double[] values = new double[logEvents.size()];
      for (int j = 0; j < values.length; j++) {
        values[j] = logEvents.get(j).getTimeStamp();
      }
      _chartPanel.setChart(createChart(createDataset(values)));
    }
  }

  /**
   * Creates a sample {@link HistogramDataset}.
   * 
   * @return the dataset.
   */
  private IntervalXYDataset createDataset(double[] values) {
    _dataset = new HistogramDataset();
    _dataset.addSeries("Log Events", values, 1000);
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
