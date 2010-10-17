package org.javakontor.sherlog.domain.impl.internal.reader;

import org.javakontor.sherlog.domain.reader.LogEventFlavour;

/**
 * <p>
 * </p>
 * 
 * @author Gerd Wuetherich (gerd@gerd-wuetherich.de)
 */
public class LogEventFlavourImpl implements LogEventFlavour {

  private final String _description;

  private final int    _type;

  public LogEventFlavourImpl(final String description, final int type) {
    super();
    _description = description;
    _type = type;
  }

  /**
   * @see org.lumberjack.core.reader.LogEventFlavour#getDescription()
   */
  public String getDescription() {
    return _description;
  }

  /**
   * @see org.lumberjack.core.reader.LogEventFlavour#getType()
   */
  public int getType() {
    return _type;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + ((_description == null) ? 0 : _description.hashCode());
    result = prime * result + _type;
    return result;
  }

  @Override
  public boolean equals(final Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    final LogEventFlavourImpl other = (LogEventFlavourImpl) obj;
    if (_description == null) {
      if (other._description != null) {
        return false;
      }
    } else if (!_description.equals(other._description)) {
      return false;
    }
    if (_type != other._type) {
      return false;
    }
    return true;
  }

  @Override
  public String toString() {
    return "LogEventFlavourImpl [_description=" + _description + ", _type=" + _type + "]";
  }

}
