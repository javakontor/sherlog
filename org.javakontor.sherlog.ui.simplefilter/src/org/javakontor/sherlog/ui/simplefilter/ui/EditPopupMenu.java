package org.javakontor.sherlog.ui.simplefilter.ui;

import java.awt.Component;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComboBox;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;

public class EditPopupMenu {

  private final JTextComponent _textComponent;

  private Action               _cutAction;

  private Action               _copyAction;

  private Action               _pasteAction;

  private Action               _selectAllAction;

  private Action               _clearAction;

  public EditPopupMenu(JComboBox box) {
    Component editorComponent = box.getEditor().getEditorComponent();
    if (!(editorComponent instanceof JTextComponent)) {
      throw new IllegalArgumentException("Edito of '" + box + "' is not a JTextComponent");
    }
    this._textComponent = (JTextComponent) editorComponent;

    initMenu();
  }

  private void initMenu() {

    this._cutAction = new DefaultEditorKit.CutAction();
    this._cutAction.putValue(Action.NAME, "Cut");
    this._copyAction = new DefaultEditorKit.CopyAction();
    this._copyAction.putValue(Action.NAME, "Copy");
    this._pasteAction = new DefaultEditorKit.PasteAction();
    this._pasteAction.putValue(Action.NAME, "Paste");

    this._selectAllAction = new SelectAllAction();
    this._clearAction = new ClearAction();

    JPopupMenu menu = new JPopupMenu();
    menu.add(this._cutAction);
    menu.add(this._copyAction);
    menu.add(this._pasteAction);
    menu.add(this._selectAllAction);
    menu.add(new JSeparator());
    menu.add(this._clearAction);

    menu.addPopupMenuListener(new PopupMenuListener() {
      public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        EditPopupMenu.this.popupMenuWillBecomeVisible();
      }

      public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
      }

      public void popupMenuCanceled(PopupMenuEvent e) {
      }
    });

    this._textComponent.setComponentPopupMenu(menu);

  }

  class SelectAllAction extends AbstractAction {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public SelectAllAction() {
      super("Select all");
    }

    public void actionPerformed(ActionEvent e) {
      EditPopupMenu.this._textComponent.setSelectionStart(0);
      EditPopupMenu.this._textComponent.setSelectionEnd(EditPopupMenu.this._textComponent.getText().length());
    }

  }

  class ClearAction extends AbstractAction {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public ClearAction() {
      super("Clear");
    }

    public void actionPerformed(ActionEvent e) {
      EditPopupMenu.this._textComponent.setText(null);
    }

  }

  protected void popupMenuWillBecomeVisible() {

    boolean hasSelection = (this._textComponent.getSelectedText() != null);
    this._cutAction.setEnabled(hasSelection);
    this._copyAction.setEnabled(hasSelection);
    try {
      Clipboard systemClipboard = this._textComponent.getToolkit().getSystemClipboard();
      boolean canImport = this._textComponent.getTransferHandler().canImport(this._textComponent,
          systemClipboard.getAvailableDataFlavors());
      this._pasteAction.setEnabled(canImport);
    } catch (Exception ex) {
      ex.printStackTrace();
    }

    boolean hasText = (this._textComponent.getText() != null) && (this._textComponent.getText().length() > 0);
    this._selectAllAction.setEnabled(hasText);
    this._clearAction.setEnabled(hasText);

  }

}
