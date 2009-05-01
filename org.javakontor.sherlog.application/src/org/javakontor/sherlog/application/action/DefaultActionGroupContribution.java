package org.javakontor.sherlog.application.action;

/**
 * <p>
 * </p>
 */
public class DefaultActionGroupContribution extends AbstractActionGroupElementContribution implements
    ActionGroupContribution {

  /** - */
  private ActionGroupType                  _type;

  private ActionContribution[]             _staticActionContributions;

  private DefaultActionGroupContribution[] _staticActionGroupContributions;

  public DefaultActionGroupContribution() {
    super();
  }

  public void setStaticActionContributions(final ActionContribution[] staticActionContributions) {
    this._staticActionContributions = staticActionContributions;
  }

  public void setStaticActionGroupContributions(final DefaultActionGroupContribution[] staticActionGroupContributions) {
    this._staticActionGroupContributions = staticActionGroupContributions;
  }

  public void setType(final ActionGroupType type) {
    this._type = type;
  }

  /**
   * Creates a {@link ActionGroupType#simple} ActionGroup with the specified parametes
   * 
   * @param id
   *          The actionGroupId - must be unique within the application
   * @param targetActionGroupId
   *          - the id of the parent ActionGroup
   * @param label
   *          The label or null to create a "flat" ActionGroup
   */
  public DefaultActionGroupContribution(final String id, final String targetActionGroupId, final String label) {
    this(ActionGroupType.simple, id, targetActionGroupId, label);
  }

  public DefaultActionGroupContribution(final ActionGroupType type, final String id, final String targetActionGroupId,
      final String label) {

    super(id, targetActionGroupId, label);

    this._type = type;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.javakontor.sherlog.application.action.ActionGroup#getType()
   */
  public ActionGroupType getType() {
    return this._type;
  }

  /*
   * (non-Javadoc)
   * 
   * @see java.lang.Object#toString()
   */
  @Override
  public String toString() {
    return "[DefaultActionGroup, id: " + getId() + ", type: " + getType() + "]";
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public boolean isFinal() {
    return false;
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public ActionGroupContribution[] getStaticActionGroupContributions() {
    return this._staticActionGroupContributions;
  }

  public boolean hasStaticActionGroupContributions() {
    return (getStaticActionGroupContributions() != null) && (getStaticActionGroupContributions().length > 0);
  }

  /**
   * <p>
   * </p>
   * 
   * @return
   */
  public ActionContribution[] getStaticActionContributions() {
    return this._staticActionContributions;
  }

  public boolean hasStaticActionContributions() {
    return (getStaticActionContributions() != null) && (getStaticActionContributions().length > 0);
  }

}
