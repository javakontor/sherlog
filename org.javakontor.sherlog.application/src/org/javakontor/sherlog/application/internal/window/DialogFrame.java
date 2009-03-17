package org.javakontor.sherlog.application.internal.window;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

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

  // /** - * */
  // private static final Insets STATUSBAR_INSETS = new Insets(3, 5, 3, 5);
  //
  // /** - * */
  // private static final int STATUSBAR_HEIGHT = 20;

  /** - * */
  private StatusMessagePanel     _statusBarPanel;

  private final ViewContribution _viewContribution;

  /**
   * Creates an instance of type DialogFrame.
   */
  public DialogFrame(Window parent, ViewContribution viewContribution) {
    super(viewContribution.getDescriptor().getName(), viewContribution.getDescriptor().isResizable(),
        viewContribution.getDescriptor().isClosable(), viewContribution.getDescriptor().isMaximizable());
    this._viewContribution = viewContribution;

    setUp(parent);
  }

  /**
   *
   */
  public void setUp(Window parent) {
    Assert.notNull("Parameter parent has to be set!", parent);

    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

    getContentPane().setLayout(new BorderLayout());

    this._statusBarPanel = new StatusMessagePanel();
    getContentPane().add(this._statusBarPanel, BorderLayout.PAGE_END);

    getContentPane().add(this._viewContribution.getPanel(), BorderLayout.CENTER);

    pack();

    this.addComponentListener(new InternalComponentListener());
    addInternalFrameListener(new DelegatingWindowListener());

  }

  /**
   * A {@link ComponentAdapter} that makes sure that the window never gets smaller than it's preferred size
   * 
   */
  private class InternalComponentListener extends ComponentAdapter {
    @Override
    public void componentResized(ComponentEvent e) {
      if ((getSize().width < getPreferredSize().width) || (getSize().height < getPreferredSize().height)) {
        int newWidth = Math.max(getSize().width, getPreferredSize().width);
        int newHeight = Math.max(getSize().height, getPreferredSize().height);
        setSize(new Dimension(newWidth, newHeight));
        revalidate();
      }
    }
  }

  // /**
  // *
  // */
  // @Override
  // public void doLayout() {
  // super.doLayout();
  //
  // JPanel view = this._viewContribution.getPanel();
  //
  // if ((view != null) /** && (_statusBarPanel != null)* */
  // ) {
  // if (view != null) {
  // (view).setBounds(new Rectangle(0, 0, getContentPane().getSize().width, (getContentPane().getSize().height)));
  // (view).validate();
  // }
  //
  // this._statusBarPanel.setBounds(STATUSBAR_INSETS.left, getContentPane().getSize().height
  // - (STATUSBAR_INSETS.top + STATUSBAR_HEIGHT), getContentPane().getSize().width
  // - (STATUSBAR_INSETS.left + STATUSBAR_INSETS.right), STATUSBAR_HEIGHT);
  // }
  // }

  // /**
  // *
  // *
  // * @return -
  // */
  // @Override
  // public Dimension getPreferredSize() {
  // JPanel view = this._viewContribution.getPanel();
  // if (view != null) {
  // return new Dimension((view).getPreferredSize().width + 10, (view).getPreferredSize().height + 33
  // + STATUSBAR_HEIGHT + STATUSBAR_INSETS.top + STATUSBAR_INSETS.bottom);
  // } else {
  // return new Dimension(100, 20);
  // }
  // }

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
  public void setStatusMessage(StatusMessage message) {
    Assert.notNull("Parameter message has to be set!", message);

    this._statusBarPanel.setStatusMessage(message);
  }

  // /**
  // *
  // */
  // @Override
  // public void repaint() {
  // super.repaint();
  // invalidate();
  // }

  class DelegatingWindowListener extends InternalFrameAdapter {

    public DelegatingWindowListener() {
    }

    @Override
    public void internalFrameActivated(InternalFrameEvent e) {
      DialogFrame.this._viewContribution.viewEventOccured(ViewEvent.windowActivated);
    }

    @Override
    public void internalFrameClosing(InternalFrameEvent e) {
      DialogFrame.this._viewContribution.viewEventOccured(ViewEvent.windowClosing);
    }

    @Override
    public void internalFrameClosed(InternalFrameEvent e) {

    }

    @Override
    public void internalFrameDeactivated(InternalFrameEvent e) {
      DialogFrame.this._viewContribution.viewEventOccured(ViewEvent.windowDeactivated);
    }

    @Override
    public void internalFrameOpened(InternalFrameEvent e) {
      DialogFrame.this._viewContribution.viewEventOccured(ViewEvent.windowOpened);
    }

  }

}
