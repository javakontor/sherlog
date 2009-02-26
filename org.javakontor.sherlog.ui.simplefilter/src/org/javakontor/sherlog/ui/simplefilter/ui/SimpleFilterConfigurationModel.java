package org.javakontor.sherlog.ui.simplefilter.ui;

import java.util.LinkedList;
import java.util.List;

import org.javakontor.sherlog.core.LogLevel;
import org.javakontor.sherlog.ui.simplefilter.SimpleLogEventFilter;
import org.javakontor.sherlog.ui.simplefilter.SimpleLogEventFilterMemento;
import org.lumberjack.application.mvc.AbstractModel;
import org.lumberjack.application.mvc.DefaultReasonForChange;

public class SimpleFilterConfigurationModel extends
    AbstractModel<SimpleFilterConfigurationModel, DefaultReasonForChange> {

  private SimpleLogEventFilterMemento _memento;

  private SimpleLogEventFilter        _filter;

  private final HistoryList           _threadNameHistory;

  private final HistoryList           _categoryHistory;

  private final HistoryList           _messageHistory;

  public SimpleFilterConfigurationModel(SimpleLogEventFilter filter) {
    super();
    _filter = filter;
    _memento = (SimpleLogEventFilterMemento)filter.saveToMemento();
    _memento.setLogLevel(LogLevel.getFinestLogLevel());
    this._threadNameHistory = new HistoryList();
    this._categoryHistory = new HistoryList();
    this._messageHistory = new HistoryList();

  }

  public LogLevel getLogLevel() {
    return this._memento.getLogLevel();
  }

  public void setLogLevel(LogLevel logLevel) {
    if (logLevel == null) {
      _memento.setLogLevel(LogLevel.getFinestLogLevel());
    } else {
      _memento.setLogLevel(logLevel);
    }
    _filter.restoreFromMemento(_memento);
    fireModelChangedEvent(DefaultReasonForChange.modelChanged);
  }

  public String getThreadName() {
    return _memento.getThread();
  }

  public String getCategory() {
    return _memento.getCategory();
  }

  public String getMessage() {
    return _memento.getMessage();
  }

  public void setThreadName(String threadName) {
    this._memento.setThread(threadName);
    this._threadNameHistory.addHistoryItem(threadName);
    _filter.restoreFromMemento(_memento);
    fireModelChangedEvent(DefaultReasonForChange.modelChanged);
  }

  public void setCategory(String category) {
    this._memento.setCategory(category);
    this._categoryHistory.addHistoryItem(category);
    _filter.restoreFromMemento(_memento);
    fireModelChangedEvent(DefaultReasonForChange.modelChanged);
  }

  public void setMessage(String message) {
    this._memento.setMessage(message);
    this._messageHistory.addHistoryItem(message);
    _filter.restoreFromMemento(_memento);
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
    public void addHistoryItem(String item) {
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

  protected static boolean hasText(String s) {
    return ((s != null) && (s.trim().length() > 0));
  }

}
