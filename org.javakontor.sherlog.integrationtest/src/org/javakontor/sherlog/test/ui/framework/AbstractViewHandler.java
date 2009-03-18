package org.javakontor.sherlog.test.ui.framework;

import org.netbeans.jemmy.ComponentChooser;
import org.netbeans.jemmy.operators.Operator.StringComparator;

/**
 * A ViewHandler contains all Jemmy Operators for the view and it's child components
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class AbstractViewHandler {

  private boolean _enableComponentTraceing;

  AbstractViewHandler() {
    this._enableComponentTraceing = true;
  }

  public boolean isEnableComponentTraceing() {
    return _enableComponentTraceing;
  }

  public void setEnableComponentTraceing(boolean enableComponentTraceing) {
    _enableComponentTraceing = enableComponentTraceing;
  }

  ComponentChooser wrapTracingComponentChooser(ComponentChooser originalChooser) {
    ComponentChooser chooser;
    if (isEnableComponentTraceing()) {
      chooser = new TracingComponentChooser(originalChooser);
    } else {
      chooser = originalChooser;
    }
    return chooser;
  }

  StringComparator wrapTracingStringComparator(StringComparator originalStringComparator) {
    StringComparator stringComparator;
    if (isEnableComponentTraceing()) {
      stringComparator = new TracingStringComparator(originalStringComparator);
    } else {
      stringComparator = originalStringComparator;
    }

    return stringComparator;
  }

}
