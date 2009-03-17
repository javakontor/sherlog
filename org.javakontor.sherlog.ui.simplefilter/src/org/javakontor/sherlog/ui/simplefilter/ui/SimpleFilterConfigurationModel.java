package org.javakontor.sherlog.ui.simplefilter.ui;

import java.util.LinkedList;
import java.util.List;

import org.javakontor.sherlog.core.LogLevel;
import org.javakontor.sherlog.ui.simplefilter.SimpleLogEventFilter;
import org.javakontor.sherlog.ui.simplefilter.SimpleLogEventFilterMemento;
import org.javakontor.sherlog.application.mvc.AbstractModel;
import org.javakontor.sherlog.application.mvc.DefaultReasonForChange;

public class SimpleFilterConfigurationModel extends
    AbstractModel<SimpleFilterConfigurationModel, DefaultReasonForChange> {

  private final SimpleLogEventFilterMemento _memento;

  private final SimpleLogEventFilter        _filter;

  private final HistoryList                 _threadNameHistory;

  private final HistoryList                 _categoryHistory;

  private final HistoryList                 _messageHistory;

  public SimpleFilterConfigurationModel(final SimpleLogEventFilter filter) {
    super();
    this._filter = filter;
    this._memento = (SimpleLogEventFilterMemento) filter.saveToMemento();
    this._memento.setLogLevel(LogLevel.getFinestLogLevel());
    this._threadNameHistory = new HistoryList();
    this._categoryHistory = new HistoryList();
    this._messageHistory = new HistoryList();

  }

  public LogLevel getLogLevel() {
    return this._memento.getLogLevel();
  }

  public void setLogLevel(final LogLevel logLevel) {
    if (logLevel == null) {
      this._memento.setLogLevel(LogLevel.getFinestLogLevel());
    } else {
      this._memento.setLogLevel(logLevel);
    }
    this._filter.restoreFromMemento(this._memento);
    fireModelChangedEvent(DefaultReasonForChange.modelChanged);
  }

  public String getThreadName() {
    return this._memento.getThread();
  }

  public String getCategory() {
    return this._memento.getCategory();
  }

  public String getMessage() {
    return this._memento.getMessage();
  }

  public void setThreadName(final String threadName) {
    this._memento.setThread(threadName);
    this._threadNameHistory.addHistoryItem(threadName);
    this._filter.restoreFromMemento(this._memento);
    fireModelChangedEvent(DefaultReasonForChange.modelChanged);
  }

  public void setCategory(final String category) {
    this._memento.setCategory(category);
    this._categoryHistory.addHistoryItem(category);
    this._filter.restoreFromMemento(this._memento);
    fireModelChangedEvent(DefaultReasonForChange.modelChanged);
  }

  public void setMessage(final String message) {
    this._memento.setMessage(message);
    this._messageHistory.addHistoryItem(message);
    this._filter.restoreFromMemento(this._memento);
    fireModelChangedEvent(DefaultReasonForChange.modelChanged);
  }

  public LogLevel[] getAllLogLevels() {
    return LogLevel.values();
  }

  public String[] getThreadNameHistory() {
    return this._threadNameHistory.toArray();
  }

  public String[] getCategoryHistory() {
    return this._categoryHistory.toArray();
  }

  public String[] getMessageHistory() {
    return this._messageHistory.toArray();
  }

  class HistoryList {

    private final List<String> _list;

    /**
     *
     */
    private static final long  serialVersionUID = 1L;

    private HistoryList() {
      this._list = new LinkedList<String>();
      this._list.add("");
    }

    /**
     * Inserts an item at the beginning to the list. make sure that a default item ("") is added at first position
     * 
     * @param item
     */
    public void addHistoryItem(final String item) {
      if (!hasText(item)) {
        return;
      }
      this._list.remove(item);
      if (this._list.isEmpty()) {
        this._list.add("");
      }
      this._list.add(1, item);
    }

    public String[] toArray() {
      return this._list.toArray(new String[0]);
    }

  }

  protected static boolean hasText(final String s) {
    return ((s != null) && (s.trim().length() > 0));
  }

}
