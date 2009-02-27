package org.javakontor.sherlog.ui.simplefilter.ui;

import javax.swing.JPanel;

import org.javakontor.sherlog.core.filter.LogEventFilter;
import org.javakontor.sherlog.ui.filter.FilterConfigurationEditorFactory;
import org.javakontor.sherlog.ui.simplefilter.SimpleLogEventFilter;

public class SimpleFilterConfigurationEditorFactory implements FilterConfigurationEditorFactory {

  /**
   * @see org.javakontor.sherlog.ui.filter.FilterConfigurationEditorFactory#createFilterConfigurationEditorPanel(org.javakontor.sherlog.core.filter.LogEventFilter)
   */
  public JPanel createFilterConfigurationEditorPanel(LogEventFilter logEventFilter) {
    SimpleFilterConfigurationModel model = new SimpleFilterConfigurationModel((SimpleLogEventFilter) logEventFilter);
    SimpleFilterConfigurationView view = new SimpleFilterConfigurationView(model);
    new SimpleFilterConfigurationController(model, view);
    return view;
  }

  /**
   * @see org.javakontor.sherlog.ui.filter.FilterConfigurationEditorFactory#isSuitableFor(org.javakontor.sherlog.core.filter.LogEventFilter)
   */
  public boolean isSuitableFor(LogEventFilter logEventFilter) {
    return logEventFilter instanceof SimpleLogEventFilter;
  }

  // public JPanel createFilterPanel(LogEventStore store) {
  //
  // // TODO ...
  // SimpleFilter filter = new SimpleFilter();
  // store.addLogEventFilter(filter);
  //
  // // Create Model...
  // SimpleFilterConfigModel model = new SimpleFilterConfigModel();
  //
  // // Connect Model and Filter via FilterConfig
  // FilterConfigModelListener listener = new FilterConfigModelListener(filter);
  // model.addModelChangedListener(listener);
  //
  // // Create view
  // SimpleFilterConfigView view = new SimpleFilterConfigView(model);
  //
  // return view;
  // }
  //
  // static class FilterConfigModelListener implements ModelChangedListener<Model<M, E>, Enum<E>> {
  //
  // private final SimpleFilter _filter;
  //
  // private FilterConfigModelListener(SimpleFilter filter) {
  // this._filter = filter;
  // }
  //
  // public <M extends Model> void modelChanged(ModelChangedEvent<M, E> event) {
  // SimpleFilterConfigModel model = (SimpleFilterConfigModel) event.getSource();
  //
  // String threadName = stringOrNull(model.getThreadName());
  // String category = stringOrNull(model.getCategory());
  // String message = stringOrNull(model.getMessage());
  // SimpleFilterConfig config = new SimpleFilterConfig(model.getLogLevel(), threadName, category, message);
  // this._filter.restoreFromMemento(config);
  // }
  //
  // }
  //
  // protected static String stringOrNull(String s) {
  // if (!hasText(s)) {
  // return null;
  // }
  // return s;
  // }
  //
  // protected static boolean hasText(String s) {
  // return ((s != null) && (s.trim().length() > 0));
  // }

}
