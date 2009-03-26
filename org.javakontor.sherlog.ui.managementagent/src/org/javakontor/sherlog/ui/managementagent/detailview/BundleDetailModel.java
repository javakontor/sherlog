package org.javakontor.sherlog.ui.managementagent.detailview;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javakontor.sherlog.application.mvc.AbstractModel;
import org.javakontor.sherlog.application.mvc.DefaultReasonForChange;
import org.javakontor.sherlog.util.Assert;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;

/**
 * <p>
 * The model for the bundle detail view.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class BundleDetailModel extends AbstractModel<BundleDetailModel, DefaultReasonForChange> {

  private final Log     _logger = LogFactory.getLog(getClass());

  /** the bundle */
  private Bundle        _bundle;

  /** the bundle */
  private BundleContext _bundleContext;

  /**
   * <p>
   * </p>
   * 
   * @param bundleContext
   */
  public BundleDetailModel(BundleContext bundleContext) {
    Assert.notNull(bundleContext);

    _bundleContext = bundleContext;
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
