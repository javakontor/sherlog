package org.javakontor.sherlog.application.extender.internal.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.osgi.framework.Bundle;

public class AbstractBundleAwareEntry {

  /** - */
  private Bundle _bundle;

  @JsonIgnore
  public void setBundle(Bundle bundle) {
    _bundle = bundle;
  }

  public Bundle getBundle() {
    return _bundle;
  }

}
