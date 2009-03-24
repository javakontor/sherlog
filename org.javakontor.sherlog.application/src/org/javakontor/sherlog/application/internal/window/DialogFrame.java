package org.javakontor.sherlog.application.internal.window;

import java.awt.BorderLayout;
import java.awt.Window;

import javax.swing.JInternalFrame;
import javax.swing.WindowConstants;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import org.javakontor.sherlog.application.request.StatusMessage;
import org.javakontor.sherlog.application.view.ViewContribution;
import org.javakontor.sherlog.application.view.ViewContribution.ViewEvent;
import org.javakontor.sherlog.util.Assert;

/**
 *
 */
public class DialogFrame extends JInternalFrame {

  /** serialVersionUID */
  private static final long      serialVersionUID = 1L;

  /** - * */
  private StatusMessagePanel     _statusBarPanel;

  private final ViewContribution _viewContribution;

  /**
   * Creates an instance of type DialogFrame.
   */
  public DialogFrame(final Window parent, final ViewContribution viewContribution) {
    super(viewContribution.getDescriptor().getName(), viewContribution.getDescriptor().isResizable(), viewContribution
        .getDescriptor().isClosable(), viewContribution.getDescriptor().isMaximizable());
    this._viewContribution = viewContribution;

    setUp(parent);
  }

  /**
   *
   */
  public void setUp(final Window parent) {
    Assert.notNull("Parameter parent has to be set!", parent);

    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

    getContentPane().setLayout(new BorderLayout());

    this._statusBarPanel = new StatusMessagePanel();
    getContentPane().add(this._statusBarPanel, BorderLayout.PAGE_END);

    getContentPane().add(this._viewContribution.getPanel(), BorderLayout.CENTER);

    pack();

    addInternalFrameListener(new DelegatingWindowListener());

  }

  /**
   * 
   * 
   * @return -
   */
  public StatusMessage getStatusMessage() {
    return this._statusBarPanel.getStatusMessage();
  }

  /**
   * 
   * 
   * @param message
   *          -
   */
  public void setStatusMessage(final StatusMessage message) {
    Assert.notNull("Parameter message has to be set!", message);

    this._statusBarPanel.setStatusMessage(message);
  }

  class DelegatingWindowListener extends InternalFrameAdapter {

    public DelegatingWindowListener() {
    }

    @Override
    public void internalFrameActivated(final InternalFrameEvent e) {
      DialogFrame.this._viewContribution.viewEventOccured(ViewEvent.windowActivated);
    }

    @Override
    public void internalFrameClosing(final InternalFrameEvent e) {
      DialogFrame.this._viewContribution.viewEventOccured(ViewEvent.windowClosing);
    }

    @Override
    public void internalFrameClosed(final InternalFrameEvent e) {

    }

    @Override
    public void internalFrameDeactivated(final InternalFrameEvent e) {
      DialogFrame.this._viewContribution.viewEventOccured(ViewEvent.windowDeactivated);
    }

    @Override
    public void internalFrameOpened(final InternalFrameEvent e) {
      DialogFrame.this._viewContribution.viewEventOccured(ViewEvent.windowOpened);
    }
  }
}
