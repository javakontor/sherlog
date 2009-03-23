package org.javakontor.sherlog.test.ui.handler;

import static org.javakontor.sherlog.test.ui.framework.GuiTestSupport.*;

import org.javakontor.sherlog.test.ui.framework.GuiTestContext;
import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JMenuBarOperator;
import org.netbeans.jemmy.operators.JMenuItemOperator;
import org.netbeans.jemmy.operators.JMenuOperator;

public class ApplicationWindowHandler {

  private final GuiTestContext   _guiTestContext;

  private final JFrameOperator   _applicationFrameOperator;

  private final JMenuBarOperator _menuBarOperator;

  public ApplicationWindowHandler(final GuiTestContext guiTestContext) {
    this._guiTestContext = guiTestContext;
    this._applicationFrameOperator = new JFrameOperator("Sherlog Log Event Viewer");
    this._menuBarOperator = new JMenuBarOperator(this._applicationFrameOperator);

    // ~ File- and Window-menu are registered together with the ApplicationWindow,
    // they should always be available

    assertSubMenuAvailable(this._menuBarOperator, "File");
    assertSubMenuAvailable(this._menuBarOperator, "Window");
  }

  public static boolean hasApplicationWindow() {
    return (JFrameOperator.findJFrame("Sherlog Log Event Viewer", false, false) != null);
  }

  public GuiTestContext getGuiTestContext() {
    return this._guiTestContext;
  }

  public JFrameOperator getApplicationFrameOperator() {
    return this._applicationFrameOperator;
  }

  public JMenuBarOperator getMenuBarOperator() {
    return this._menuBarOperator;
  }

  /**
   * Returns the FileMenu. Fails if the menu is not available
   * <p>
   * Note! Since Menus can be (re-)registered at anytime you sould not re-use the returned JMenuOperator reference.
   * Instead grap each time you need to access the MenuOperator a new reference by calling this method
   * 
   * @return the JMenuOperator for File-Menu. Never null
   * 
   */
  public JMenuOperator getFileMenuOperator() {
    // since Menus can be (re-)registered anytime we always need to
    // get a new reference
    return new JMenuOperator(this._menuBarOperator, "File");
  }

  public JMenuOperator getWindowMenuOperator() {
    // since Menus can be (re-)registered anytime we always need to
    // get a new reference
    return new JMenuOperator(this._menuBarOperator, "Window");
  }

  public void pushMenu(final String path, final boolean block) {
    if (block) {
      this._menuBarOperator.pushMenu(path);
    } else {
      this._menuBarOperator.pushMenuNoBlock(path);
    }
  }

  public JMenuItemOperator getToolsMenuOperator() {
    return getSubMenuOperator(this._menuBarOperator, "Window|Tools");
    // return getSubMenuItemOperator(getFileMenuOperator(), null)
    // getSubMenu(getFileMenuOperator(), null)
  }

  public void pushWindowMenuItem(final String path, final boolean block) {
    pushMenu("Window|" + path, block);
  }

  public void pushFileMenuItem(final String path, final boolean block) {
    pushMenu("File|" + path, block);
  }

}
