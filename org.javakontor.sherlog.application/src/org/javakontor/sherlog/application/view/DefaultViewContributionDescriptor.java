package org.javakontor.sherlog.application.view;

import org.javakontor.sherlog.util.Assert;

/**
 * <p>
 * Default implementation of type {@link ViewContributionDescriptor}.
 * </p>
 * 
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class DefaultViewContributionDescriptor implements ViewContributionDescriptor {

  /** the name of the dialog */
  private final String  _dialogname;

  /** resizable */
  private final boolean _resizable;

  /** closable */
  private final boolean _closable;

  /** maximizable */
  private final boolean _maximizable;

  /** modal */
  private final boolean _modal;

  /** openMaximized */
  private final boolean _openMaximized;

  /**
   * <p>
   * Creates a new instance of type {@link DefaultViewContributionDescriptor}.
   * </p>
   * 
   * @param dialogname
   *          the name of the dialog
   * @param modal
   * @param resizable
   * @param closable
   * @param maximizable
   * @param openMaximized
   */
  public DefaultViewContributionDescriptor(String dialogname, boolean modal, boolean resizable, boolean closable, boolean maximizable,
      boolean openMaximized) {

    Assert.nonEmpty(dialogname);

    this._dialogname = dialogname;
    this._resizable = resizable;
    this._closable = closable;
    this._maximizable = maximizable;
    this._modal = modal;
    this._openMaximized = openMaximized;
  }

  /**
   * {@inheritDoc}
   */
  public String getName() {
    return this._dialogname;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isResizable() {
    return this._resizable;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isClosable() {
    return this._closable;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isMaximizable() {
    return this._maximizable;
  }

  /**
   * {@inheritDoc}
   */
  public boolean isModal() {
    return this._modal;
  }

  /**
   * {@inheritDoc}
   */
  public boolean openMaximized() {
    return this._openMaximized;
  }
}