package org.javakontor.sherlog.ui.histogram;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.javakontor.sherlog.domain.LogEvent;

/**
 * <p>
 * Helper class that computes all necessary values for the histogram panel.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class HistogramData {

  /** the minimum date */
  private Date     _minimumDate;

  /** the maximum date */
  private Date     _maximumDate;

  /** all values */
  private double[] _values;

  /** the number of milliseconds */
  private long     _numberOfMilliseconds;

  /**
   * <p>
   * Creates a new instance of type {@link HistogramData}.
   * </p>
   * 
   * @param logEvents
   */
  public HistogramData(List<LogEvent> logEvents) {
    compute(logEvents);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public double getMinimumTime() {
    return _minimumDate.getTime();
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public double getMaximumTime() {
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
  public int getNumberOfSeconds() {
    return (int) Math.ceil((double) _numberOfMilliseconds / 1000.);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public int getNumberOfMinutes() {
    return (int) Math.ceil((double) _numberOfMilliseconds / (60. * 1000.));
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public int getNumberOfHours() {
    return (int) Math.ceil((double) _numberOfMilliseconds / (60. * 60. * 1000.));
  }

  /**
   * <p>
   * </p>
   * 
   * @param logEvents
   */
  private void compute(List<LogEvent> logEvents) {

    // set default values if necessary
    if (logEvents == null || logEvents.isEmpty()) {

      //
      _minimumDate = _maximumDate = new Date();

      //
      _values = new double[] {};

      //
      _numberOfMilliseconds = 1;

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

    Calendar calendarMinimum = new GregorianCalendar();
    Calendar calendarMaximum = new GregorianCalendar();
    calendarMinimum.setTime(minimum);
    calendarMaximum.setTime(maximum);

    calendarMinimum.set(Calendar.MINUTE, 0);
    calendarMinimum.set(Calendar.SECOND, 0);
    calendarMinimum.set(Calendar.MILLISECOND, 0);

    calendarMaximum.set(Calendar.HOUR, calendarMaximum.get(Calendar.HOUR) + 1);
    calendarMaximum.set(Calendar.MINUTE, 0);
    calendarMaximum.set(Calendar.SECOND, 0);
    calendarMaximum.set(Calendar.MILLISECOND, 0);

    _numberOfMilliseconds = calendarMaximum.getTime().getTime() - calendarMinimum.getTime().getTime();

    _minimumDate = calendarMinimum.getTime();
    _maximumDate = calendarMaximum.getTime();
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
    buffer.append(_numberOfMilliseconds);
    buffer.append("]");
    return buffer.toString();
  }

}
