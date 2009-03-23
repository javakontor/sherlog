package org.javakontor.sherlog.ui.histogram;

import java.awt.BorderLayout;
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

/**
 * <p>
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class HistogramPanel extends JPanel {

	/** serialVersionUID */
	private static final long serialVersionUID = 1L;

	/** the jfree chart panel */
	private ChartPanel _chartPanel;

	/** the jfree chart histogram dataset */
	private HistogramDataset _dataset;

	/**
	 * <p>
	 * Creates a new instance of type {@link HistogramPanel}.
	 * </p>
	 */
	public HistogramPanel() {

		// sets the BorderLayout
		this.setLayout(new BorderLayout());

		// create the jfree chart
		JFreeChart _chart = createChart(createDefaultDataset());

		// create the chart panel
		_chartPanel = new ChartPanel(_chart);

		// add chart panel to histogram panel
		add(_chartPanel, BorderLayout.CENTER);
	}

	/**
	 * <p>
	 * Sets the log events.
	 * </p>
	 *
	 * @param logEvents
	 *            the log events.
	 */
	public void setLogEvents(List<LogEvent> logEvents) {

		HistogramData data = new HistogramData(logEvents);

		IntervalXYDataset intervalXYDataset = createDataset(data.getValues(),
				data.getMinimumDate(), data.getMaximumDate(), data
						.getHourCount());

		_chartPanel.setChart(createChart(intervalXYDataset));
	}

	/**
	 * @return
	 */
	private IntervalXYDataset createDefaultDataset() {
		return createDataset(new double[] { 0.0 }, 0.0, 0.0, 1);

	}

	/**
	 * Creates a sample {@link HistogramDataset}.
	 *
	 * @return the dataset.
	 */
	private IntervalXYDataset createDataset(double[] values, double minimum,
			double maximum, int count) {
		//
		_dataset = new HistogramDataset();

		//
		if (values.length > 0) {
			_dataset.addSeries("Log Events", values, count, minimum, maximum);
		}

		//
		return _dataset;
	}

	/**
	 * Creates a chart.
	 *
	 * @param dataset
	 *            a dataset.
	 *
	 * @return The chart.
	 */
	private JFreeChart createChart(IntervalXYDataset dataset) {
		JFreeChart chart = ChartFactory.createHistogram("Log Event Histogram",
				null, null, dataset, PlotOrientation.VERTICAL, true, true,
				false);
		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setForegroundAlpha(0.85f);

		NumberAxis numberAxis = new NumberAxis("Log event count");
		TickUnitSource units = NumberAxis.createIntegerTickUnits();
		numberAxis.setStandardTickUnits(units);
		plot.setRangeAxis(numberAxis);

		// set the tick size to one week, with formatting...
		DateAxis dateAxis = new DateAxis("time");
		DateFormat formatter = new SimpleDateFormat("HH:mm:ss");
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
