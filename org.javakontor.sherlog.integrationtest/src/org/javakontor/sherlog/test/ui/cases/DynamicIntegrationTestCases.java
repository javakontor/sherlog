package org.javakontor.sherlog.test.ui.cases;

import static org.javakontor.sherlog.test.ui.framework.GuiTestSupport.*;

import org.javakontor.sherlog.application.action.ActionGroup;
import org.javakontor.sherlog.test.ui.framework.GuiTestContext;
import org.javakontor.sherlog.test.ui.handler.ApplicationWindowHandler;
import org.javakontor.sherlog.test.ui.handler.BundleListViewHandler;
import org.javakontor.sherlog.test.ui.handler.LogViewHandler;
import org.netbeans.jemmy.operators.JMenuOperator;
import org.osgi.framework.Bundle;

public class DynamicIntegrationTestCases extends AbstractGuiIntegrationTestCases {

  public DynamicIntegrationTestCases(final GuiTestContext guiTestContext) {
    super(guiTestContext);
  }

  public void test_dynamicManagementBundle() throws Exception {

    this._guiTestSupport.assertNoViewContributionRegistered(BundleListViewHandler.BUNDLE_LIST_VIEWCONTRIBUTION_NAME);

    // make sure, "Tools" menu is there but disabled
    JMenuOperator subMenu = getSubMenuOperator(this.getApplicationWindowHandler().getWindowMenuOperator(), "Tools");
    assertFalse(subMenu.isEnabled());

    // install bundle that provides content for tools menu
    final Bundle managementBundle = this._guiTestSupport.installBundle("org.javakontor.sherlog.ui.managementagent");
    this._guiTestSupport.startBundle(managementBundle);

    // make sure tools menu is visible now
    subMenu = getSubMenuOperator(this.getApplicationWindowHandler().getWindowMenuOperator(), "Tools");
    assertTrue(subMenu.isEnabled());
    // make sure, contributed menu is visible
    assertTrue("Bundle list-menu item should be visible", hasSubMenuItem(subMenu, "Bundle list"));

    // stop bundle again and make sure it's not visible anymore
    this._guiTestSupport.stopBundle(managementBundle);
    assertTrue("Bundle list-menu item should not be visible anymore", hasSubMenuItem(subMenu, "Bundle list"));

    this._guiTestSupport.uninstallBundle(managementBundle);
  }

  public void test_dynamicLogView() throws Exception {
    // Install prereqs
    final Bundle logViewBundle = this._guiTestSupport.installBundle("org.javakontor.sherlog.ui.logview");
    final Bundle filterBundle = this._guiTestSupport.installAndStartBundle("org.javakontor.sherlog.ui.filter");
    // install logview (should not be able to create a View, since LogStore is not available)

    this._guiTestSupport.assertNoViewContributionRegistered(LogViewHandler.LOG_VIEWCONTRIBUTION_NAME);
    this._guiTestSupport.startBundle(logViewBundle);
    this._guiTestSupport.assertNoViewContributionRegistered(LogViewHandler.LOG_VIEWCONTRIBUTION_NAME);

    final Bundle domainImplBundle = this._guiTestSupport.installBundle("org.javakontor.sherlog.domain.impl");
    this._guiTestSupport.startBundle(domainImplBundle);
    this._guiTestSupport.assertViewContributionRegistered(LogViewHandler.LOG_VIEWCONTRIBUTION_NAME);

    // stopping the domain.impl-Bundle removes the Mod.LogEventStore should lead to
    // the unregistering of the LogView
    this._guiTestSupport.stopBundle(domainImplBundle);
    this._guiTestSupport.assertNoViewContributionRegistered(LogViewHandler.LOG_VIEWCONTRIBUTION_NAME);

    this._guiTestSupport.uninstallBundle(domainImplBundle);
    this._guiTestSupport.uninstallBundle(filterBundle);
    this._guiTestSupport.uninstallBundle(logViewBundle);
  }

  public void test_restartDynamicServices() throws Exception {

    // make sure application window and default menus are visible
    assertTrue(ApplicationWindowHandler.hasApplicationWindow());

    assertTrue(hasSubMenu(this.getApplicationWindowHandler().getMenuBarOperator(), "File"));
    assertTrue(hasSubMenu(this.getApplicationWindowHandler().getMenuBarOperator(), "Window"));
    assertTrue(hasSubMenu(this.getApplicationWindowHandler().getMenuBarOperator(), "Window|Look and feel"));

    assertEquals(1, this._guiTestSupport.getRegisteredActionGroupElementCount(ActionGroup.class, "fileMenu"));
    assertEquals(1, this._guiTestSupport.getRegisteredActionGroupElementCount(ActionGroup.class, "windowMenu"));
    assertEquals(1, this._guiTestSupport.getRegisteredActionGroupElementCount(ActionGroup.class, "lafMenu"));

    // stop dynamic service bundle (TODO should not be coupled to equinox)
    final Bundle dsBundle = this._guiTestSupport.stopBundle("org.eclipse.equinox.ds");
    assertFalse(ApplicationWindowHandler.hasApplicationWindow());

    // removing the ApplicationWindow should de-register the file- and windowmenu
    assertEquals(0, this._guiTestSupport.getRegisteredActionGroupElementCount(ActionGroup.class, "fileMenu"));
    assertEquals(0, this._guiTestSupport.getRegisteredActionGroupElementCount(ActionGroup.class, "windowMenu"));
    assertEquals(0, this._guiTestSupport.getRegisteredActionGroupElementCount(ActionGroup.class, "lafMenu"));

    // (re-)start ds-bundle
    this._guiTestSupport.startBundle(dsBundle);

    // make sure menus are registered again
    assertTrue(ApplicationWindowHandler.hasApplicationWindow());
    assertEquals(1, this._guiTestSupport.getRegisteredActionGroupElementCount(ActionGroup.class, "fileMenu"));
    assertEquals(1, this._guiTestSupport.getRegisteredActionGroupElementCount(ActionGroup.class, "windowMenu"));
    assertEquals(1, this._guiTestSupport.getRegisteredActionGroupElementCount(ActionGroup.class, "lafMenu"));
    findApplicationWindow();
    assertTrue(hasSubMenu(this.getApplicationWindowHandler().getMenuBarOperator(), "File"));
    assertTrue(hasSubMenu(this.getApplicationWindowHandler().getMenuBarOperator(), "Window"));
    assertTrue(hasSubMenu(this.getApplicationWindowHandler().getMenuBarOperator(), "Window|Look and feel"));
  }
}
