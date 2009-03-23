package org.javakontor.sherlog.ui.histogram;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.swing.JPanel;

import org.javakontor.sherlog.domain.LogEvent;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.TickUnitSource;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.IntervalXYDataset;

public class HistogramPanel extends JPanel {

  /** serialVersionUID */
  private static final long serialVersionUID = 1L;

  private ChartPanel        _chartPanel;

  private HistogramDataset  _dataset;

  /**
   * 
   */
  public HistogramPanel() {
    super();

    // 
    JFreeChart _chart = createChart(createDataset(new double[] { 0.0 }, 0.0, 0.0, 1));

    //
    _chartPanel = new ChartPanel(_chart);

    //
    add(_chartPanel);
  }

  /**
   * @param logEvents
   */
  public void setLogEvents(List<LogEvent> logEvents) {

    HistogramData data = new HistogramData();
    data.compute(logEvents);

    IntervalXYDataset intervalXYDataset = createDataset(data.getValues(), data.getMinimumDate(), data.getMaximumDate(),
        data.getHourCount());

    _chartPanel.setChart(createChart(intervalXYDataset));
  }

  /**
   * Creates a sample {@link HistogramDataset}.
   * 
   * @return the dataset.
   */
  private IntervalXYDataset createDataset(double[] values, double minimum, double maximum, int count) {
    _dataset = new HistogramDataset();
    if (values.length > 0) {
      _dataset.addSeries("Log Events", values, count, minimum, maximum);
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

    NumberAxis numberAxis = new NumberAxis("Log event count");
    TickUnitSource units = NumberAxis.createIntegerTickUnits();
    numberAxis.setStandardTickUnits(units);
    plot.setRangeAxis(numberAxis);

    // set the tick size to one week, with formatting...
    DateAxis dateAxis = new DateAxis("time");
    DateFormat formatter = new SimpleDateFormat("HH:mm:ss Z");
    DateTickUnit unit = new DateTickUnit(DateTickUnit.MINUTE, 60, formatter);
    dateAxis.setTickUnit(unit);
    plot.setDomainAxis(dateAxis);

    XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
    renderer.setDrawBarOutline(false);
    renderer.setBaseSeriesVisibleInLegend(false);
    // flat bars look best...
    // renderer.setBarPainter(new StandardXYBarPainter());
    // renderer.setShadowVisible(false);
    return chart;
  }
}
