package org.javakontor.sherlog.application.action.contrib;

/**
 * <p>
 * Abstract implementation of interface {@link AbstractActionGroupElementContribution}.
 * </p>
 * <p>
 * This class <b>is not intended</b> to be subclassed by clients. To contribute an action or an action group, you can
 * use an instance of type {@link DefaultActionContribution} or {@link DefaultActionGroupContribution}.
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public abstract class AbstractActionGroupElementContribution {

  /** - */
  private String _id;

  /** - */
  private String _label;

  /** - */
  private String _targetActionGroupId;

  /**
   * @param id
   * @param targetActionGroupId
   * @param label
   */
  public AbstractActionGroupElementContribution(final String id, final String targetActionGroupId, final String label) {
    super();
    this._id = id;
    this._label = label;
    this._targetActionGroupId = targetActionGroupId;
  }

  public AbstractActionGroupElementContribution() {
    super();
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.javakontor.sherlog.application.action.ActionGroupElement#getId()
   */
  public String getId() {
    return this._id;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.javakontor.sherlog.application.action.ActionGroupElement#getLabel()
   */
  public String getLabel() {
    return this._label;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.javakontor.sherlog.application.action.ActionGroupElement#getTargetActionGroupId()
   */
  public String getTargetActionGroupId() {
    return this._targetActionGroupId;
  }

  public void setId(final String id) {
    this._id = id;
  }

  public void setLabel(final String label) {
    this._label = label;
  }

  public void setTargetActionGroupId(final String targetActionGroupId) {
    this._targetActionGroupId = targetActionGroupId;
  }
}
