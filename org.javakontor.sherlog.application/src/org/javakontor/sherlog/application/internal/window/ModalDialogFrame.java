package org.javakontor.sherlog.application.internal.window;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.WindowConstants;

import org.javakontor.sherlog.application.view.ViewContribution;
import org.javakontor.sherlog.application.view.ViewContribution.ViewEvent;

public class ModalDialogFrame extends JDialog {

  private final ViewContribution _viewContribution;

  /**
   * 
   */
  private static final long      serialVersionUID = 1L;

  public ModalDialogFrame(JFrame owner, ViewContribution viewContribution) {
    super(owner, viewContribution.getDescriptor().getName(), true);

    this._viewContribution = viewContribution;

    setUp();

  }

  protected void setUp() {
    setResizable(this._viewContribution.getDescriptor().isResizable());
    setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

    setLayout(new BorderLayout());
    add(this._viewContribution.getPanel(), BorderLayout.CENTER);

    addWindowListener(new DelegatingWindowListener());

    pack();
  }

  @Override
  public void setVisible(boolean flag) {
    this._viewContribution.getPanel().setVisible(flag);
    super.setVisible(flag);
  }

  class DelegatingWindowListener extends WindowAdapter {

    public DelegatingWindowListener() {
    }

    @Override
    public void windowActivated(WindowEvent e) {
      ModalDialogFrame.this._viewContribution.viewEventOccured(ViewEvent.windowActivated);
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
      ModalDialogFrame.this._viewContribution.viewEventOccured(ViewEvent.windowDeactivated);
    }

    @Override
    public void windowClosing(WindowEvent e) {
      ModalDialogFrame.this._viewContribution.viewEventOccured(ViewEvent.windowClosing);
    }

    @Override
    public void windowOpened(WindowEvent e) {
      ModalDialogFrame.this._viewContribution.viewEventOccured(ViewEvent.windowOpened);
    }
  }

}
