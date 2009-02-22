package org.javakontor.sherlog.core.impl.internal.reader;

import org.javakontor.sherlog.core.reader.LogEventFlavour;

/**
 * <p>
 * </p>
 * 
 * @author Gerd Wuetherich (gerd@gerd-wuetherich.de)
 */
public class LogEventFlavourImpl implements LogEventFlavour {

	private String _description;

	private int _type;

	/**
	 * @see org.lumberjack.core.reader.LogEventFlavour#getDescription()
	 */
	public String getDescription() {
		return this._description;
	}

	/**
	 * @see org.lumberjack.core.reader.LogEventFlavour#getType()
	 */
	public int getType() {
		return this._type;
	}

	void setDescription(final String description) {
		this._description = description;
	}

	void setType(final int type) {
		this._type = type;
	}

}
