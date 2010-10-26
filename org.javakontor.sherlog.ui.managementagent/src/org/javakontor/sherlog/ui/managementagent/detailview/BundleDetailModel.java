package org.javakontor.sherlog.ui.managementagent.detailview;

import org.javakontor.sherlog.application.mvc.AbstractModel;
import org.javakontor.sherlog.application.mvc.DefaultReasonForChange;
import org.javakontor.sherlog.util.Assert;
import org.osgi.framework.Bundle;

/**
 * <p>
 * The model for the bundle detail view.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BundleDetailModel extends AbstractModel<BundleDetailModel, DefaultReasonForChange> {

  /** the bundle */
  private Bundle _bundle;

  /**
   * <p>
   * </p>
   * 
   */
  public BundleDetailModel() {
  }

  /**
   * <p>
   * Returns the bundle.
   * </p>
   * 
   * @return the bundle.
   */
  public Bundle getBundle() {
    return this._bundle;
  }

  /**
   * <p>
   * Sets the bundle.
   * </p>
   * 
   * @param bundle
   */
  public void setBundle(Bundle bundle) {
    Assert.notNull(bundle);

    // set the bundle
    this._bundle = bundle;

    // fire model changed event
    fireModelChangedEvent(DefaultReasonForChange.modelChanged);
  }
}
