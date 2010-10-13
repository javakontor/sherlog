package org.javakontor.sherlog.ui.timefilter;

import org.javakontor.sherlog.application.action.contrib.ActionContribution;
import org.javakontor.sherlog.application.action.contrib.ActionGroupContribution;
import org.javakontor.sherlog.application.action.contrib.DefaultActionContribution;
import org.javakontor.sherlog.application.action.contrib.DefaultActionGroupContribution;
import org.javakontor.sherlog.domain.filter.LogEventFilter;
import org.javakontor.sherlog.domain.filter.LogEventFilterFactory;
import org.javakontor.sherlog.domain.filter.LogEventFilterMemento;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class TimeFilterActivator implements BundleActivator {

  /*
   * (non-Javadoc)
   * 
   * @see org.osgi.framework.BundleActivator#start(org.osgi.framework.BundleContext)
   */
  public void start(BundleContext bundleContext) throws Exception {
    registerMenus(bundleContext);
    registerTimeFilterFactory(bundleContext);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.osgi.framework.BundleActivator#stop(org.osgi.framework.BundleContext)
   */
  public void stop(BundleContext bundleContext) throws Exception {
  }

  protected void registerTimeFilterFactory(BundleContext bundleContext) {
    bundleContext.registerService(LogEventFilterFactory.class.getName(), new TimeFilterFactory(), null);
  }

  protected void registerMenus(final BundleContext bundleContext) throws Exception {

    // Create "Filter before"-Action
    final DefaultActionContribution filterBeforeContribution = new DefaultActionContribution("filterBefore",
        "logEventView.contextMenu/timeFilter(first)", "Filter out &older entries", null, new TimeFilterAction(true));

    // Create "Filter after"-Action
    final DefaultActionContribution filterAfterContribution = new DefaultActionContribution("filterAfter",
        "logEventView.contextMenu/timeFilter", "Filter out &newer entries", null, new TimeFilterAction(false));

    // Create "Reset filter"-Action
    final DefaultActionContribution resetFilterContribution = new DefaultActionContribution("resetFilter",
        "logEventView.contextMenu/timeFilter(last)", "&Reset time filter", null, new ResetTimeFilterAction());

    // Create Timefilter ActionGroup
    final DefaultActionGroupContribution timeFilterActionGroup = new DefaultActionGroupContribution("timeFilter",
        "logEventView.contextMenu", "&Timefilter");

    // Register Actions
    bundleContext.registerService(ActionGroupContribution.class.getName(), timeFilterActionGroup, null);
    bundleContext.registerService(ActionContribution.class.getName(), filterBeforeContribution, null);
    bundleContext.registerService(ActionContribution.class.getName(), filterAfterContribution, null);
    bundleContext.registerService(ActionContribution.class.getName(), resetFilterContribution, null);
  }

  class TimeFilterFactory implements LogEventFilterFactory {
    public String getDescription() {
      return "A filter that filters events between two timestamps";
    }

    public LogEventFilter createLogEventFilter(LogEventFilterMemento memento) {
      return null;
    }

    public LogEventFilter createLogEventFilter() {
      return new TimeFilter();
    }
  }

}
