package org.javakontor.sherlog.core.filter;

import org.javakontor.sherlog.core.LogEvent;

/**
 * <p>
 * A instance of type {@link LogEvent} can be used to filter
 * {@link LogEvent LogEvents}.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface LogEventFilter {

	/**
	 * <p>
	 * Returns <code>true</code>, if the given {@link LogEvent} matches this
	 * filter.
	 * </p>
	 * 
	 * @param event
	 *            the {@link LogEvent}
	 * @return <code>true</code>, if the given {@link LogEvent} matches this
	 *         filter.
	 */
	public boolean matches(LogEvent event);

	/**
	 * <p>
	 * Adds the specified {@link LogEventFilterListener} to this {@link LogEventFilter}.
	 * </p>
	 * 
	 * @param listener
	 *            the {@link LogEventFilterListener} to add.
	 */
	public void addLogFilterListener(LogEventFilterListener listener);

	/**
	 * <p>
	 * Removes the specified {@link LogEventFilterListener} from this
	 * {@link LogEventFilter}.
	 * </p>
	 * 
	 * @param listener
	 *            the {@link LogEventFilterListener} to remove.
	 */
	public void removeLogFilterListener(LogEventFilterListener listener);
	
	/**
	 * <p>
	 * Saves the state of this filter to a {@link LogEventFilterMemento}.
	 * </p>
	 * 
	 * @return the state of this filter as a {@link LogEventFilterMemento}.
	 */
	public LogEventFilterMemento saveToMemento();

	/**
	 * <p>
	 * Restores the state of this filter from the given {@link LogEventFilterMemento}.
	 * </p>
	 * 
	 * @param memento
	 */
	public void restoreFromMemento(LogEventFilterMemento memento);

}
