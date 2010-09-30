package org.javakontor.sherlog.ui.managementagent.tableview;

import org.javakontor.sherlog.application.request.Request;
import org.osgi.framework.Bundle;

/**
 * <p>
 * A {@link Request} to set selected {@link Bundle}.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class SetSelectedBundleRequest extends Request {

  private final Bundle _bundle;

  /**
   * <p>
   * Creates a new instance of type {@link SetSelectedBundleRequest}.
   * </p>
   * 
   * @param sender
   * @param bundle
   */
  public SetSelectedBundleRequest(Object sender, Bundle bundle) {
    super(sender);

    this._bundle = bundle;
  }

  /**
   * <p>
   * Returns the selected {@link Bundle}.
   * </p>
   * 
   * @return the selected {@link Bundle}.
   */
  public Bundle getBundle() {
    return this._bundle;
  }

  /**
   * <p>
   * 
   * @return
   */
  public boolean hasBundle() {
    return this._bundle != null;
  }
}
