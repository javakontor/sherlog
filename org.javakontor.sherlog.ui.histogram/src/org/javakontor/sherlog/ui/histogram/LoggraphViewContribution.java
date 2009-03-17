package org.javakontor.sherlog.ui.histogram;

import java.util.Random;

import javax.swing.JPanel;

import org.javakontor.sherlog.application.view.AbstractViewContribution;
import org.javakontor.sherlog.application.view.DefaultViewContributionDescriptor;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYBarRenderer;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.xy.IntervalXYDataset;

public class LoggraphViewContribution extends AbstractViewContribution {

  private JPanel _panel;

  public DefaultViewContributionDescriptor getDescriptor() {
    return new DefaultViewContributionDescriptor("Loggraph", false, true, false, true, false);
  }

  public JPanel getPanel() {
    if (_panel == null) {
      _panel = createDemoPanel();
    }

    return _panel;
  }

  public void viewEventOccured(ViewEvent viewEvent) {
  }

  /**
   * Creates a sample {@link HistogramDataset}.
   *
   * @return the dataset.
   */
  private static IntervalXYDataset createDataset() {
    HistogramDataset dataset = new HistogramDataset();
    double[] values = new double[1000];
    Random generator = new Random(12345678L);
    for (int i = 0; i < 1000; i++) {
      values[i] = generator.nextGaussian() + 5;
    }
    dataset.addSeries("H1", values, 100, 2.0, 8.0);
    // values = new double[1000];
    // for (int i = 0; i < 1000; i++) {
    // values[i] = generator.nextGaussian() + 7;
    // }
    // dataset.addSeries("H2", values, 100, 4.0, 10.0);
    return dataset;
  }

  /**
   * Creates a chart.
   *
   * @param dataset
   *          a dataset.
   *
   * @return The chart.
   */
  private static JFreeChart createChart(IntervalXYDataset dataset) {
    JFreeChart chart = ChartFactory.createHistogram("Histogram Demo 1", null, null, dataset, PlotOrientation.VERTICAL,
        true, true, false);
    XYPlot plot = (XYPlot) chart.getPlot();
    plot.setForegroundAlpha(0.85f);
    XYBarRenderer renderer = (XYBarRenderer) plot.getRenderer();
    renderer.setDrawBarOutline(false);
    // flat bars look best...
    // renderer.setBarPainter(new StandardXYBarPainter());
    // renderer.setShadowVisible(false);
    return chart;
  }

  /**
   * Creates a panel for the demo (used by SuperDemo.java).
   *
   * @return A panel.
   */
  public static JPanel createDemoPanel() {
    JFreeChart chart = createChart(createDataset());
    return new ChartPanel(chart);
  }
}
