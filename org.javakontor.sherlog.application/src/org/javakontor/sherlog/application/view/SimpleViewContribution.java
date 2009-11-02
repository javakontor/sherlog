package org.javakontor.sherlog.application.view;

import javax.swing.JPanel;

import org.javakontor.sherlog.util.Assert;

/**
 * A ready-to-use {@link ViewContribution} implementation
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class SimpleViewContribution extends AbstractViewContribution {

  /**
   * The descriptor that describes the view
   */
  private final ViewContributionDescriptor _descriptor;

  /**
   * The view itself
   */
  private final JPanel                     _panel;

  /**
   * Constructor
   * 
   * @param descriptor
   * @param panel
   */
  public SimpleViewContribution(final ViewContributionDescriptor descriptor, final JPanel panel) {
    Assert.notNull("Parameter 'descriptor' must not be null", descriptor);
    Assert.notNull("Parameter 'panel' must not be null", panel);
    this._descriptor = descriptor;
    this._panel = panel;
  }

  /**
   * Convenience constructor. Creates an instance of {@link DefaultViewContributionDescriptor} with the specified
   * arguments
   * 
   * 
   * 
   * @see DefaultViewContributionDescriptor
   * @param dialogname
   * @param modal
   * @param resizable
   * @param closable
   * @param maximizable
   * @param openMaximized
   * @param panel
   */
  public SimpleViewContribution(final String dialogname, final boolean modal, final boolean resizable,
      final boolean closable, final boolean maximizable, final boolean openMaximized, final JPanel panel) {
    this(new DefaultViewContributionDescriptor(dialogname, modal, resizable, closable, maximizable, openMaximized),
        panel);
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.javakontor.sherlog.application.view.ViewContribution#getDescriptor()
   */
  public ViewContributionDescriptor getDescriptor() {
    return this._descriptor;
  }

  /*
   * (non-Javadoc)
   * 
   * @see org.javakontor.sherlog.application.view.ViewContribution#getPanel()
   */
  public JPanel getPanel() {
    return this._panel;
  }

  /**
   * Forwards the {@link ViewEvent#windowClosing} event to {@link #onWindowClose()}
   */
  @Override
  public void viewEventOccured(final ViewEvent viewEvent) {
    if (viewEvent == ViewEvent.windowClosing) {
      onWindowClose();
    } else {
      super.viewEventOccured(viewEvent);
    }
  }

  /**
   * Overwrite in subclasses to implements logic that closes this view.
   * 
   * <p>
   * Typically this method de-registeres the service from the service registry.
   */
  protected void onWindowClose() {
  }

  /**
   * Constructs a <code>String</code> with all attributes in name = value format.
   * 
   * @return a <code>String</code> representation of this object.
   */
  @Override
  public String toString() {

    final String retValue = "SimpleViewContribution ( " // prefix
        + super.toString() // add super attributes
        + ", _descriptor = '" + this._descriptor + "'" // _descriptor
        + ", _panel = '" + this._panel + "'" // _panel
        + " )";

    return retValue;
  }

}
