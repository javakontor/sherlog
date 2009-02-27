package org.javakontor.sherlog.ui.logview.filterview;

import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JPanel;

import org.javakontor.sherlog.core.filter.LogEventFilter;
import org.javakontor.sherlog.ui.filter.FilterConfigurationEditorFactoryManager;
import org.javakontor.sherlog.util.Assert;
import org.lumberjack.application.mvc.AbstractView;
import org.lumberjack.application.mvc.ModelChangedEvent;

/**
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class LogEventFilterView extends AbstractView<LogEventFilterModel, LogEventFilterModelReasonForChange> {

  /** serialVersionUID */
  private static final long                       serialVersionUID = 1L;

  /** filterConfigurationEditors */
  private final Map<LogEventFilter, JPanel>       _filterConfigurationEditors;

  /** - */
  private FilterConfigurationEditorFactoryManager _configurationEditorFactoryManager;

  /**
   * <p>
   * Creates a new instance of type {@link LogEventFilterView}.
   * </p>
   * 
   * @param model
   *          the LogEventFilterModel
   */
  public LogEventFilterView(LogEventFilterModel model) {
    super(model);

    _filterConfigurationEditors = new HashMap<LogEventFilter, JPanel>();
  }

  @Override
  protected void setUp() {
    setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

    // TODO layout (Apply filter-Button should be last on view)

    // add(new LogEventFilterPanel());
    // add(new JButton("Apply Filter"));
  }

  public void modelChanged(ModelChangedEvent<LogEventFilterModel, LogEventFilterModelReasonForChange> event) {

    switch (event.getReasonForChange()) {
    case filterAdded:
      LogEventFilter logEventFilter = (LogEventFilter) event.getObjects()[0];
      createFilterConfigurationEditor(logEventFilter);
      break;
    default:
      // ignore
      break;
    }
  }

  public synchronized void setFilterConfigurationEditorFactoryManager(FilterConfigurationEditorFactoryManager factory) {
    Assert.notNull(factory);

    // set the factory
    _configurationEditorFactoryManager = factory;

    // create FilterConfigurationEditors
    for (LogEventFilter logEventFilter : getModel().getLogEventFilter()) {
      createFilterConfigurationEditor(logEventFilter);
    }
  }

  /**
   * @param factory
   */
  public synchronized void unsetFilterConfigurationEditorFactoryManager(FilterConfigurationEditorFactoryManager factory) {
    Assert.notNull(factory);

    if (_configurationEditorFactoryManager == null) {
      return;
    }

    for (LogEventFilter logEventFilter : _filterConfigurationEditors.keySet()) {
      disposeFilterConfigurationEditor(logEventFilter);
    }

    _configurationEditorFactoryManager = null;
  }

  /**
   * @param logEventFilter
   */
  private void createFilterConfigurationEditor(LogEventFilter logEventFilter) {

    // assert that the FilterConfigurationEditorFactoryManager is set
    if (_configurationEditorFactoryManager != null) {

      JPanel editorPanel = _configurationEditorFactoryManager.createFilterConfigurationEditor(logEventFilter);

      //
      if (editorPanel != null) {

        //
        _filterConfigurationEditors.put(logEventFilter, editorPanel);
        System.err.println("'''''''''''''''''''''''''");
        System.err.println(editorPanel);
        System.err.println("'''''''''''''''''''''''''");
        add(editorPanel);
        repaintComponent();
      }
    }
  }

  private void disposeFilterConfigurationEditor(LogEventFilter logEventFilter) {

    //
    JPanel panel = _filterConfigurationEditors.get(logEventFilter);

    if (panel != null) {
      remove(panel);
      repaintComponent();
    }
  }

  /**
   * 
   */
  private void repaintComponent() {
    LogEventFilterView.this.revalidate();
    LogEventFilterView.this.repaint();
  }
}
