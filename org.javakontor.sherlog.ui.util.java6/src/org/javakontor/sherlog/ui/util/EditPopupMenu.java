package org.javakontor.sherlog.ui.util;

import java.awt.Component;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPopupMenu;
import javax.swing.JSeparator;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.JTextComponent;

import org.javakontor.sherlog.util.Assert;

public class EditPopupMenu {

  private final JTextComponent _textComponent;

  private Action               _cutAction;

  private Action               _copyAction;

  private Action               _pasteAction;

  private Action               _selectAllAction;

  private Action               _clearAction;

  /**
   * Creates an EditPopupMenu for the specified {@link JTextComponent}.
   * 
   * <p>
   * The menu returned is already registered as the textcomponent's component popup menu.
   * 
   * @param textComponent
   *          the text component
   * @return the EditPopupMenu that has been created or null if no popup menu could be created
   * 
   * @see JComponent#setComponentPopupMenu(JPopupMenu)
   */
  public static EditPopupMenu createEditPopupMenuFor(JTextComponent textComponent) {
    return new EditPopupMenu(textComponent);
  }

  /**
   * Creates an EditPopupMenu for the specified {@link JComboBox}.
   * 
   * <p>
   * The menu returned is already registered as the combo box' component popup menu.
   * 
   * @param box
   *          The combo box
   * @return the EditPopupMenu that has been created or null if no popup menu could be created
   * 
   * @see JComponent#setComponentPopupMenu(JPopupMenu)
   */
  public static EditPopupMenu createEditPopupMenuFor(JComboBox box) {
    Component editorComponent = box.getEditor().getEditorComponent();
    if (!(editorComponent instanceof JTextComponent)) {
      return null;
    }

    return new EditPopupMenu((JTextComponent) editorComponent);
  }

  protected EditPopupMenu(JTextComponent textComponent) {
    Assert.notNull("Parameter 'textComponent' must not be null", textComponent);
    this._textComponent = textComponent;

    // ~~ create menu (items) and assign menu to component
    createAndSetMenu();

  }

  private void createAndSetMenu() {

    // Create the actions
    this._cutAction = new DefaultEditorKit.CutAction();
    this._cutAction.putValue(Action.NAME, "Cut");
    this._copyAction = new DefaultEditorKit.CopyAction();
    this._copyAction.putValue(Action.NAME, "Copy");
    this._pasteAction = new DefaultEditorKit.PasteAction();
    this._pasteAction.putValue(Action.NAME, "Paste");

    this._selectAllAction = new SelectAllAction();
    this._clearAction = new ClearAction();

    // create the popup menu
    JPopupMenu menu = new JPopupMenu();
    menu.add(this._cutAction);
    menu.add(this._copyAction);
    menu.add(this._pasteAction);
    menu.add(this._selectAllAction);
    menu.add(new JSeparator());
    menu.add(this._clearAction);

    // register a PopupMenuListener that handles the enablement
    menu.addPopupMenuListener(new PopupMenuListener() {
      public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
        EditPopupMenu.this.popupMenuWillBecomeVisible();
      }

      public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
      }

      public void popupMenuCanceled(PopupMenuEvent e) {
      }
    });

    // add popup menu to textComponent
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

    final boolean hasSelection = (this._textComponent.getSelectedText() != null);
    final boolean editable = (this._textComponent.isEditable() && this._textComponent.isEnabled());
    this._cutAction.setEnabled(hasSelection && editable);
    this._copyAction.setEnabled(hasSelection);
    boolean pasteEnabled = editable;
    if (pasteEnabled) {
      // if textcomponent is editable check if there is "paste-able" content in the
      // system clipboard
      try {
        Clipboard systemClipboard = this._textComponent.getToolkit().getSystemClipboard();
        pasteEnabled = this._textComponent.getTransferHandler().canImport(this._textComponent,
            systemClipboard.getAvailableDataFlavors());
      } catch (Exception ex) {
        ex.printStackTrace();
      }
    }
    this._pasteAction.setEnabled(pasteEnabled);
    boolean hasText = (this._textComponent.getText() != null) && (this._textComponent.getText().length() > 0);
    this._selectAllAction.setEnabled(hasText);
    this._clearAction.setEnabled(editable && hasText);

  }

}
