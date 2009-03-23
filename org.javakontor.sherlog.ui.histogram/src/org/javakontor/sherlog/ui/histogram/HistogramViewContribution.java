package org.javakontor.sherlog.ui.histogram;

import javax.swing.JPanel;

import org.javakontor.sherlog.application.view.AbstractViewContribution;
import org.javakontor.sherlog.application.view.DefaultViewContributionDescriptor;
import org.javakontor.sherlog.application.view.ViewContribution;
import org.javakontor.sherlog.domain.store.LogEventStore;
import org.javakontor.sherlog.domain.store.LogEventStoreEvent;
import org.javakontor.sherlog.domain.store.LogEventStoreListener;

/**
 * <p>
 * {@link ViewContribution} for the histogram view.
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class HistogramViewContribution extends AbstractViewContribution {

	/** the histogram panel */
	private HistogramPanel _histogramPanel;

	/** the log event store */
	private LogEventStore _logEventStore;

	/** the log event store listener */
	private LogEventStoreListener _logEventStoreListener;

	/**
	 * <p>
	 * Creates a new instance of type {@link HistogramViewContribution}.
	 * </p>
	 */
	public HistogramViewContribution() {
		// create the histgram panel
		_histogramPanel = new HistogramPanel();

		// the internal log event store listener
		_logEventStoreListener = new LogEventStoreListener() {
			public void logEventStoreChanged(LogEventStoreEvent event) {
				if (_logEventStore != null) {
					_histogramPanel.setLogEvents(_logEventStore
							.getFilteredLogEvents());
				}
			}
		};
	}

	/**
	 * @see org.javakontor.sherlog.application.view.ViewContribution#getDescriptor()
	 */
	public DefaultViewContributionDescriptor getDescriptor() {
		return new DefaultViewContributionDescriptor("Log Event Histogram",
				false, true, false, true, false);
	}

	/**
	 * @see org.javakontor.sherlog.application.view.ViewContribution#getPanel()
	 */
	public JPanel getPanel() {
		return _histogramPanel;
	}

	/**
	 * <p>
	 * Binds the log event store.
	 * </p>
	 *
	 * @param logEventStore
	 *            the log event store.
	 */
	public void bindLogEventStore(LogEventStore logEventStore) {
		// release old log event store
		releaseLogEventStore();

		// set new log event store
		_logEventStore = logEventStore;

		// add listener
		_logEventStore.addLogStoreListener(_logEventStoreListener);

		// set log events
		_histogramPanel.setLogEvents(_logEventStore.getFilteredLogEvents());
	}

	/**
	 * <p>
	 * Unbinds the log event store.
	 * </p>
	 *
	 * @param logEventStore
	 *            the log event store.
	 */
	public void unbindLogEventStore(LogEventStore logEventStore) {
		// release log event store
		releaseLogEventStore();

		// set log events to null
		_histogramPanel.setLogEvents(null);
	}

	/**
	 * <p>
	 * Releases the log event store.
	 * </p>
	 */
	private void releaseLogEventStore() {
		// remove listener, if log event store is set
		if (_logEventStore != null) {
			_logEventStore.removeLogStoreListener(_logEventStoreListener);
			_logEventStore = null;
		}
	}
}
