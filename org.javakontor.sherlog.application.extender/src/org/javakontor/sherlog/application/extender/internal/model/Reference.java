package org.javakontor.sherlog.application.extender.internal.model;

import java.lang.reflect.Method;

import org.codehaus.jackson.annotate.JsonIgnore;

public class Reference {

  private String _filter;

  private String _interface;

  private String _bind;

  private String _unbind;

  public String getFilter() {
    return _filter;
  }

  public void setFilter(String filter) {
    _filter = filter;
  }

  public String getInterface() {
    return _interface;
  }

  public void setInterface(String interface1) {
    _interface = interface1;
  }

  public String getBind() {
    return _bind;
  }

  public void setBind(String bind) {
    _bind = bind;
  }

  public String getUnbind() {
    return _unbind;
  }

  public void setUnbind(String unbind) {
    _unbind = unbind;
  }
}
