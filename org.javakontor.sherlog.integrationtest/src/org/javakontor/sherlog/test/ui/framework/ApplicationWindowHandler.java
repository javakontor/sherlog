package org.javakontor.sherlog.test.ui.framework;

import org.netbeans.jemmy.operators.JFrameOperator;
import org.netbeans.jemmy.operators.JMenuBarOperator;
import org.netbeans.jemmy.operators.JMenuItemOperator;
import org.netbeans.jemmy.operators.JMenuOperator;

public class ApplicationWindowHandler {

  private final GuiTestContext   _guiTestContext;

  private final JFrameOperator   _applicationFrameOperator;

  private final JMenuBarOperator _menuBarOperator;

  private final JMenuOperator    _fileMenuOperator;

  private final JMenuOperator    _windowMenuOperator;

  private final JMenuOperator    _toolsMenuOperator;

  public ApplicationWindowHandler(GuiTestContext guiTestContext) {
    this._guiTestContext = guiTestContext;
    _applicationFrameOperator = new JFrameOperator("Sherlog Log Event Viewer");
    _menuBarOperator = new JMenuBarOperator(_applicationFrameOperator);

    // ~ File- and Window-menu are registered together with the ApplicationWindow,
    // they should always be available

    _fileMenuOperator = new JMenuOperator(_menuBarOperator, "File");
    _windowMenuOperator = new JMenuOperator(_menuBarOperator, "Window");
    _toolsMenuOperator = null;
    // _toolsMenuOperator = new JMenuOperator(_windowMenuOperator, "Tools");
    // _toolsMenuOperator = new JMenuItemOperator(_windowMenuOperator, "Tools");
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

  public JMenuOperator getFileMenuOperator() {
    return _fileMenuOperator;
  }

  public JMenuOperator getWindowMenuOperator() {
    return _windowMenuOperator;
  }

  public void pushMenu(String path, boolean block) {
    if (block) {
      _menuBarOperator.pushMenu(path);
    } else {
      _menuBarOperator.pushMenuNoBlock(path);
    }
  }

  public JMenuItemOperator getToolsMenuOperator() {
    return _toolsMenuOperator;
  }

  public void pushWindowMenuItem(String path, boolean block) {
    pushMenu("Window|" + path, block);
  }

  public void pushFileMenuItem(String path, boolean block) {
    pushMenu("File|" + path, block);
  }

}
