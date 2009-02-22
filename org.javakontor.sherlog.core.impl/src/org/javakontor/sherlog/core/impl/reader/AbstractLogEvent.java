package org.javakontor.sherlog.core.impl.reader;

import java.util.Date;
import java.util.Hashtable;
import java.util.Map;

import org.javakontor.sherlog.core.LogEvent;
import org.javakontor.sherlog.core.LogLevel;
import org.javakontor.sherlog.core.impl.internal.reader.LogSourceIdentifier;
import org.javakontor.sherlog.core.impl.internal.reader.ThreadMode;
import org.javakontor.sherlog.core.impl.internal.reader.ThreadNameFactory;

public abstract class AbstractLogEvent implements LogEvent {

  static final long           serialVersionUID   = 1L;

  // private Color backgroundcolor;

  private LogSourceIdentifier datenHerkunft;

  private String              fileThreadName;

  private String              filePatternThreadName;

  private static int          laufendeNummer     = 0;

  private final Date          modelTimestamp     = null;

  // private final boolean modellTimestampExtracted = false;
  private final int           erzeugungNr;

  private Map<Object, Object> _userDefinedFields = null;

  public AbstractLogEvent() {
    this.erzeugungNr = laufendeNummer++;
  }

  public Map<Object, Object> getUserDefinedFields() {
    if (_userDefinedFields == null) {
      _userDefinedFields = new Hashtable<Object, Object>();
    }
    return _userDefinedFields;
  }

  public Date getModelTimestamp() {
    // if (!this.modellTimestampExtracted) {
    // final String msg = getRenderedMessage();
    // // [...]
    // }
    return this.modelTimestamp;
  }

  public String getLogSource() {
    return this.datenHerkunft.toString();
  }

  public void setDatenHerkunft(final LogSourceIdentifier datenHerkunft) {
    this.datenHerkunft = datenHerkunft;
  }

  // TODO!!
  public String getLJThreadName() {
    if (ThreadMode.ORIGINAL == ThreadMode.getThreadMode()) {
      return getThreadName();
    }
    if (ThreadMode.DATENHERKUNFT == ThreadMode.getThreadMode()) {
      if (this.fileThreadName == null) {
        this.fileThreadName = ThreadNameFactory.getThreadname(getThreadName(), getLogSource());
      }
      return this.fileThreadName;
    }
    if (ThreadMode.PATTERN == ThreadMode.getThreadMode()) {
      if (this.filePatternThreadName == null) {
        this.filePatternThreadName = ThreadNameFactory.getThreadnamePattern(getThreadName(), getLogSource());
      }
      return this.filePatternThreadName;
    }
    // nun ist was falsch :-(
    // org.apache.log4j.Logger.getLogger(LogEventAdapter.class).error(
    // "Threadmode invalid, use mode " + ThreadMode.ORIGINAL);
    return getThreadName();
  }

  public long getIdentifier() {
    return this.erzeugungNr;
  }

  // public Color getMarkierungColor() {
  // // if (new Random().nextBoolean()) return Color.RED;
  // return this.backgroundcolor;
  // }
  //
  // public void setMarkierungColor(final Color c) {
  // this.backgroundcolor = c;
  // }

  public Object getUserDefinedField(Object key) {
    // TODO Auto-generated method stub
    return null;
  }

  public void setUserDefinedField(Object key, Object value) {
    // TODO Auto-generated method stub

  }

  public String getCategory() {
    // TODO Auto-generated method stub
    return null;
  }

  public LogLevel getLogLevel() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getMessage() {
    // TODO Auto-generated method stub
    return null;
  }

  public Object getNestedDiagnosticContext() {
    // TODO Auto-generated method stub
    return null;
  }

  public String getThreadName() {
    // TODO Auto-generated method stub
    return null;
  }

  public Object getThrowableInformation() {
    // TODO Auto-generated method stub
    return null;
  }

  public String[] getThrowableStrRep() {
    // TODO Auto-generated method stub
    return null;
  }

  public long getTimeStamp() {
    // TODO Auto-generated method stub
    return 0;
  }

  public boolean hasNestedDiagnosticContext() {
    // TODO Auto-generated method stub
    return false;
  }

  public int compareByTime(final Object o) {
    if (equals(o)) {
      return 0;
    }
    // sonst niemals 0!
    final LogEvent secondEvent = (LogEvent) o;
    int ret = (int) (getTimeStamp() - secondEvent.getTimeStamp());
    if (ret == 0) {
      ret = (int) (getIdentifier() - secondEvent.getIdentifier());
    }
    if (ret == 0) {
      System.err.println("compareTo fails");
    }
    return ret;
  }

  public int compareByID(final Object o) {
    if (equals(o)) {
      return 0;
    }
    // sonst niemals 0!
    final LogEvent secondEvent = (LogEvent) o;
    final long ret = getIdentifier() - secondEvent.getIdentifier();
    if (ret == 0) {
      System.err.println("compareTo fails");
    }
    return (int) ret;
  }

  public int compareTo(final LogEvent o) {
    return compareByTime(o);
  }
}
