package org.javakontor.sherlog.ui.logview.tableview;

public enum LogEventTableModelReasonForChange {

  /**
   * some LogEvents have been added to the model. object is a Collection&lt;LogEvent&gt;
   */
  logEventsAdded,

  /**
   * new log categories have been added. object contains the new categories as a Collection of Strings
   */
  categoriesAdded,

  /**
   * the selection (in the table) has been changed. object contains an array of the currently selected LogEvents (an
   * empty array if no events have been selected)
   */
  selectionChanged,

  /**
   * The model (or parts of) has been reseted. The views be updated from scratch
   */
  reset
}
