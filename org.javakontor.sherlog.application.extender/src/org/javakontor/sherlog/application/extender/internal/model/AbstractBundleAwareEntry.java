package org.javakontor.sherlog.application.extender.internal.model;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.osgi.framework.Bundle;

/**
 * <p>
 * Abstract base class for bundle aware entries in a {@link ActionDefinition}.
 * </p>
 *
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractBundleAwareEntry {

  /** the bundle */
  private Bundle _bundle;

  /**
   * <p>
   * Sets the bundle
   * </p>
   *
   * @param bundle the bundle to set
   */
  @JsonIgnore
  public void setBundle(Bundle bundle) {
    _bundle = bundle;
  }

  /**
   * <p>
   * Returns the bundle.
   * </p>
   *
   * @return the bundle.
   */
  public Bundle getBundle() {
    return _bundle;
  }
}
