package org.javakontor.sherlog.ui.histogram;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.javakontor.sherlog.domain.LogEvent;

/**
 * <p>
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class HistogramData {

  /** - */
  private Date     _minimumDate;

  /** - */
  private Date     _maximumDate;

  /** - */
  private double[] _values;

  /** - */
  private int      _hourCount;

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public double getMinimumDate() {
    return _minimumDate.getTime();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public double getMaximumDate() {
    return _maximumDate.getTime();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public double[] getValues() {
    return _values;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public int getHourCount() {
    return _hourCount;
  }

  /**
   * <p>
   * </p>
   * 
   * @param logEvents
   */
  public void compute(List<LogEvent> logEvents) {

    // set default values if necessary
    if (logEvents == null || logEvents.isEmpty()) {

      //
      _minimumDate = _maximumDate = new Date();

      //
      _values = new double[] {};

      //
      _hourCount = 1;

      // return
      return;
    }

    _values = new double[logEvents.size()];
    Date minimum = null;
    Date maximum = null;

    for (int j = 0; j < _values.length; j++) {

      //
      Date date = new Date(logEvents.get(j).getTimeStamp());

      //
      if (minimum == null) {
        minimum = date;
      } else if (minimum.after(date)) {
        minimum = date;
      }

      //
      if (maximum == null) {
        maximum = date;
      } else if (date.after(maximum)) {
        maximum = date;
      }

      _values[j] = logEvents.get(j).getTimeStamp();
    }

    Calendar cal_1 = new GregorianCalendar();
    Calendar cal_2 = new GregorianCalendar();
    cal_1.setTime(minimum); // erster Zeitpunkt
    cal_2.setTime(maximum); // zweiter Zeitpunkt
    long time = cal_2.getTime().getTime() - cal_1.getTime().getTime(); // Differenz in ms
    _hourCount = (int) Math.ceil((double) time / (1000.));

    _minimumDate = new Date(minimum.getYear(), minimum.getMonth(), minimum.getDate(), minimum.getHours(), 0);
    _maximumDate = new Date(maximum.getYear(), maximum.getMonth(), maximum.getDate(), maximum.getHours() + 1, 0);
  }

  /**
   * @see java.lang.Object#toString()
   */
  public String toString() {
    StringBuffer buffer = new StringBuffer();
    buffer.append("[HistogramComputer:");
    buffer.append(" _minimumDate: ");
    buffer.append(_minimumDate);
    buffer.append(" _maximumDate: ");
    buffer.append(_maximumDate);
    buffer.append(" _hourCount: ");
    buffer.append(_hourCount);
    buffer.append("]");
    return buffer.toString();
  }

}
