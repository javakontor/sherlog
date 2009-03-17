package org.javakontor.sherlog.application.view;

/**
 * <p>
 * Describes how a {@link ViewContribution} should be presented by the application framework.
 * </p>
 */
public interface ViewContributionDescriptor {

  /**
   * <p>
   * Returns the name of the {@link ViewContribution}.
   * </p>
   * 
   * @return the name of the {@link ViewContribution}.
   */
  public String getName();

  /**
   * <p>
   * Returns whether or not the {@link ViewContribution} is resizable.
   * </p>
   * 
   * @return whether or not the {@link ViewContribution} is resizable.
   */
  public boolean isResizable();

  /**
   * <p>
   * Returns whether or not the {@link ViewContribution} is resizable.
   * </p>
   * 
   * @return whether or not the {@link ViewContribution} is resizable.
   */
  public boolean isClosable();

  /**
   * <p>
   * Returns whether or not the {@link ViewContribution} is maximizable.
   * </p>
   * 
   * @return whether or not the {@link ViewContribution} is maximizable.
   */
  public boolean isMaximizable();

  /**
   * <p>
   * Returns whether or not the {@link ViewContribution} is modal.
   * </p>
   * 
   * @return whether or not the {@link ViewContribution} is modal.
   */
  public boolean isModal();

  /**
   * <p>
   * Returns whether or not the {@link ViewContribution} should be open maximized.
   * </p>
   * 
   * @return whether or not the {@link ViewContribution} should be open maximized.
   */
  public boolean openMaximized();
}