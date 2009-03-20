package org.javakontor.sherlog.ui.logview.tableview;

public enum LogEventTableModelReasonForChange {

  /**
   * some LogEvents have been added or removed from the model.
   */
  logEventsChanged,

  /**
   * the selection (in the table) has been changed. object contains an array of the currently selected LogEvents (an
   * empty array if no events have been selected)
   */
  selectionChanged,

  decoratorAdded,

  decoratorRemoved,

}
