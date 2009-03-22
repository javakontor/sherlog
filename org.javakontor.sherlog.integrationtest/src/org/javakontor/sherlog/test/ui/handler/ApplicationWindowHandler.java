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

  public ApplicationWindowHandler(GuiTestContext guiTestContext) {
    this._guiTestContext = guiTestContext;
    _applicationFrameOperator = new JFrameOperator("Sherlog Log Event Viewer");
    _menuBarOperator = new JMenuBarOperator(_applicationFrameOperator);

    // ~ File- and Window-menu are registered together with the ApplicationWindow,
    // they should always be available

    assertSubMenuAvailable(_menuBarOperator, "File");
    assertSubMenuAvailable(_menuBarOperator, "Window");
  }

  public GuiTestContext getGuiTestContext() {
    return _guiTestContext;
  }

  public JFrameOperator getApplicationFrameOperator() {
    return _applicationFrameOperator;
  }

  public JMenuBarOperator getMenuBarOperator() {
    return _menuBarOperator;
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
    return new JMenuOperator(_menuBarOperator, "File");
  }

  public JMenuOperator getWindowMenuOperator() {
    // since Menus can be (re-)registered anytime we always need to
    // get a new reference
    return new JMenuOperator(_menuBarOperator, "Window");
  }

  public void pushMenu(String path, boolean block) {
    if (block) {
      _menuBarOperator.pushMenu(path);
    } else {
      _menuBarOperator.pushMenuNoBlock(path);
    }
  }

  public JMenuItemOperator getToolsMenuOperator() {
    return getSubMenuOperator(_menuBarOperator, "Window|Tools");
    // return getSubMenuItemOperator(getFileMenuOperator(), null)
    // getSubMenu(getFileMenuOperator(), null)
  }

  public void pushWindowMenuItem(String path, boolean block) {
    pushMenu("Window|" + path, block);
  }

  public void pushFileMenuItem(String path, boolean block) {
    pushMenu("File|" + path, block);
  }

}
