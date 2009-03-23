package org.javakontor.sherlog.test.ui.framework;

import java.awt.Component;
import java.io.File;
import java.io.FileInputStream;
import java.net.URLDecoder;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import junit.framework.Assert;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javakontor.sherlog.application.action.ActionGroupElement;
import org.javakontor.sherlog.application.view.ViewContribution;
import org.netbeans.jemmy.operators.JMenuBarOperator;
import org.netbeans.jemmy.operators.JMenuItemOperator;
import org.netbeans.jemmy.operators.JMenuOperator;
import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.BundleException;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.springframework.osgi.util.OsgiBundleUtils;
import org.springframework.osgi.util.OsgiStringUtils;

public class GuiTestSupport extends Assert {

  Log                          _logger = LogFactory.getLog(GuiTestSupport.class);

  private final GuiTestContext _testContext;

  public GuiTestSupport(final GuiTestContext testContext) {
    assertNotNull(testContext);
    this._testContext = testContext;
  }

  protected BundleContext getBundleContext() {
    return this._testContext.getBundleContext();
  }

  /**
   * Installs the specified plug-in project to the OSGi platform
   * 
   * <p>
   * Installing bundles from the target platform is not supported!
   * </p>
   * 
   * 
   * @param bundleProjectName
   *          the name of the bundle project inside the workspace
   * @return
   * @throws Exception
   */
  public Bundle installBundle(final String bundleProjectName) throws Exception {
    final File bundleProjectLocation = new File(this._testContext.getWorkspaceLocation(), bundleProjectName);
    assertTrue("Bundle-Project '" + bundleProjectName + "' not found at location '" + bundleProjectLocation + "'",
        bundleProjectLocation.exists());

    final Bundle bundle = installBundle(bundleProjectLocation);
    assertNotNull(bundle);
    assertTrue("Installed bundle " + bundle.getSymbolicName() + " should be either INSTALLED or RESOLVED", (bundle
        .getState() == Bundle.RESOLVED)
        || (bundle.getState() == Bundle.INSTALLED));
    return bundle;
  }

  public Bundle installAndStartBundle(final String bundleProjectName) throws Exception {
    final Bundle bundle = installBundle(bundleProjectName);
    startBundle(bundle);
    return bundle;
  }

  /**
   * Starts the specified bundle and prints a nice logging message in case of failure.
   * 
   * <p>
   * Copied from org.springframework.osgi.test.AbstractOsgiTests
   * </p>
   * 
   * @param bundle
   * @return
   * @throws BundleException
   */
  public void startBundle(final Bundle bundle) throws BundleException {
    final boolean debug = this._logger.isDebugEnabled();
    final String info = "[" + OsgiStringUtils.nullSafeNameAndSymName(bundle) + "|" + bundle.getLocation() + "]";

    final boolean fragment = OsgiBundleUtils.isFragment(bundle);

    if (fragment) {
      if (debug) {
        this._logger.debug(info + " is a fragment; start not invoked");
      }
      assertTrue("Fragment-Bundle " + info + " should be RESOLVED", bundle.getState() == Bundle.RESOLVED);
    } else {
      if (debug) {
        this._logger.debug("Starting " + info);
      }
      try {
        bundle.start();
      } catch (final BundleException ex) {
        this._logger.error("cannot start bundle " + info, ex);
        throw ex;
      }
      assertTrue("Bundle " + info + " should be ACTIVE", bundle.getState() == Bundle.ACTIVE);
    }

  }

  /**
   * Stops the specified bundle and prints a nice logging message in case of failure.
   * 
   * @param bundle
   * @return
   * @throws BundleException
   */
  public void stopBundle(final Bundle bundle) throws BundleException {
    final boolean debug = this._logger.isDebugEnabled();
    final String info = "[" + OsgiStringUtils.nullSafeNameAndSymName(bundle) + "|" + bundle.getLocation() + "]";

    final boolean fragment = OsgiBundleUtils.isFragment(bundle);
    assertFalse("Bundle " + info + " is a fragment. Fragments cannot be stopped.", fragment);

    if (debug) {
      this._logger.debug("Stoppping " + info);
    }
    try {
      bundle.stop();
    } catch (final BundleException ex) {
      this._logger.error("cannot stop bundle " + info, ex);
      throw ex;
    }
    assertTrue("Bundle " + info + " should be RESOLVED", bundle.getState() == Bundle.RESOLVED);
  }

  /**
   * Stops the bundle with the given symbolicName.
   * 
   * <p>
   * If there is no such bundle installed, the method fails.
   * 
   * @param bundleSymbolicName
   * @return the bundle has been stopped
   * @throws Exception
   */
  public Bundle stopBundle(final String bundleSymbolicName) throws Exception {
    final Bundle[] allBundles = this._testContext.getBundleContext().getBundles();

    for (final Bundle bundle : allBundles) {
      if (bundleSymbolicName.equals(bundle.getSymbolicName())) {
        stopBundle(bundle);
        return bundle;
      }
    }
    fail("No bundle with symbolic-name '" + bundleSymbolicName + "' installed");
    return null; // never reached
  }

  public void uninstallBundle(final Bundle bundle) throws BundleException {
    assertNotNull(bundle);
    final boolean debug = this._logger.isDebugEnabled();
    final String info = "[" + OsgiStringUtils.nullSafeNameAndSymName(bundle) + "|" + bundle.getLocation() + "]";

    if (debug) {
      this._logger.debug("Uninstalling " + info);
    }
    try {
      bundle.uninstall();
    } catch (final BundleException ex) {
      this._logger.error("cannot uninstall bundle " + info, ex);
      throw ex;
    }
    assertTrue("Bundle " + info + " should be UNINSTALLED", bundle.getState() == Bundle.UNINSTALLED);

  }

  /**
   * Installs an OSGi bundle from the given location.
   * 
   * <p>
   * Copied from org.springframework.osgi.test.AbstractOsgiTests
   * </p>
   * 
   * @param location
   * @return
   * @throws Exception
   */
  protected Bundle installBundle(final File location) throws Exception {
    Assert.assertNotNull(location);
    if (this._logger.isDebugEnabled()) {
      this._logger.debug("Installing bundle from location " + location);
    }

    String bundleLocation;

    try {
      bundleLocation = URLDecoder.decode(location.toURL().toExternalForm(), "UTF-8");
    } catch (final Exception ex) {
      // the URL cannot be created, fall back to the description
      bundleLocation = location.getAbsolutePath();
    }

    if (location.isFile()) {
      return this._testContext.getBundleContext().installBundle(bundleLocation, new FileInputStream(location));
    } else {
      return this._testContext.getBundleContext().installBundle(bundleLocation);
    }
  }

  public void assertNoViewContributionRegistered(final String dialogName) throws Exception {
    assertFalse("There should be no ViewContribution with name '" + dialogName + "' registered",
        isViewContributionRegistered(dialogName));
  }

  public int getRegisteredActionGroupElementCount(final Class<? extends ActionGroupElement> type, final String actionId)
      throws InvalidSyntaxException {
    final ServiceReference[] serviceReferences = getBundleContext().getServiceReferences(type.getName(), null);

    int count = 0;

    if (serviceReferences != null) {

      for (final ServiceReference serviceReference : serviceReferences) {
        final ActionGroupElement actionGroupElement = (ActionGroupElement) getBundleContext().getService(
            serviceReference);
        if ((actionGroupElement != null) && actionId.equals(actionGroupElement.getId())) {
          count++;
        }
      }
    }
    return count;

  }

  /**
   * Asserts that exactly one ViewContribution with the given dialogName is currently registered at the Service Registry
   * 
   * @param dialogName
   * @throws InvalidSyntaxException
   */
  public void assertViewContributionRegistered(final String dialogName) throws InvalidSyntaxException {
    final ServiceReference[] serviceReferences = getBundleContext().getServiceReferences(
        ViewContribution.class.getName(), null);

    ViewContribution matchingContribution = null;

    if (serviceReferences != null) {

      for (final ServiceReference serviceReference : serviceReferences) {
        final ViewContribution viewContribution = (ViewContribution) getBundleContext().getService(serviceReference);
        if ((viewContribution != null) && dialogName.equals(viewContribution.getDescriptor().getName())) {
          if (matchingContribution == null) {
            matchingContribution = viewContribution;
          } else {
            // more than one contribution with same name -> error
            fail("There should be exactly one ViewContribution registered with dialogname '" + dialogName + "'");
          }
        }
      }
    }
    assertNotNull("There should be a ViewContribution registered with dialogName '" + dialogName + "'",
        matchingContribution);
  }

  public boolean isViewContributionRegistered(final String dialogName) throws Exception {
    final ServiceReference[] serviceReferences = getBundleContext().getServiceReferences(
        ViewContribution.class.getName(), null);

    if (serviceReferences == null) {
      return false;
    }

    for (final ServiceReference serviceReference : serviceReferences) {
      final ViewContribution viewContribution = (ViewContribution) getBundleContext().getService(serviceReference);
      if ((viewContribution != null) && dialogName.equals(viewContribution.getDescriptor().getName())) {
        return true;
      }
    }

    return false;
  }

  public static void assertSubMenuAvailable(final JMenuOperator menuOperator, final String title) {
    assertNotNull(menuOperator);
    assertNotNull(title);

    assertTrue("Menu '" + menuOperator + "' should contain a menu '" + title + "'", hasSubMenu(menuOperator, title));
  }

  public static void assertSubMenuAvailable(final JMenuBarOperator menuBarOperator, final String title) {
    assertNotNull(menuBarOperator);
    assertNotNull(title);

    assertTrue("Menubar should contain a menu '" + title + "'", hasSubMenu(menuBarOperator, title));
  }

  public static boolean hasSubMenu(final JMenuOperator menuOperator, final String title) {
    assertNotNull(menuOperator);
    assertNotNull(title);

    return (getSubMenuItem(menuOperator.getSource(), title) instanceof JMenu);
    // final Component[] menuComponents = menuOperator.getMenuComponents();

    // return containsSubMenuItem(menuComponents, title, true);
  }

  public static boolean hasSubMenuItem(final JMenuOperator menuOperator, final String title) {
    assertNotNull(menuOperator);
    assertNotNull(title);
    final Component[] menuComponents = menuOperator.getMenuComponents();

    return containsSubMenuItem(menuComponents, title, false);
  }

  public static boolean hasSubMenu(final JMenuBarOperator menuBarOperator, final String title) {
    assertNotNull(menuBarOperator);
    assertNotNull(title);

    return (getSubMenuItem(menuBarOperator.getSource(), title) instanceof JMenu);

  }

  public static JMenuOperator getSubMenuOperator(final JMenuBarOperator menuBarOperator, final String path) {
    assertNotNull(menuBarOperator);
    assertNotNull(path);
    final JMenuBar menuBar = (JMenuBar) menuBarOperator.getSource();
    final JMenuItem menuItem = getSubMenuItem(menuBar, path);
    assertTrue("Menu-Item '" + menuItem + "' should be of type JMenu in path '" + path + "'",
        (menuItem instanceof JMenu));
    final JMenu subMenu = (JMenu) menuItem;
    return new JMenuOperator(subMenu);

  }

  public static JMenuItemOperator getSubMenuItemOperator(final JMenuOperator menuOperator, final String path) {
    assertNotNull(menuOperator);
    assertNotNull(path);
    final JMenu currentMenu = (JMenu) menuOperator.getSource();
    final JMenuItem menu = getSubMenuItem(currentMenu, path);

    return new JMenuItemOperator(menu);
  }

  public static JMenuOperator getSubMenuOperator(final JMenuOperator menuOperator, final String path) {
    assertNotNull(menuOperator);
    assertNotNull(path);
    final JMenu currentMenu = (JMenu) menuOperator.getSource();
    final JMenuItem menuItem = getSubMenuItem(currentMenu, path);
    assertTrue("Menu-Item '" + menuItem + "' should be of type JMenu in path '" + path + "'",
        (menuItem instanceof JMenu));
    final JMenu subMenu = (JMenu) menuItem;
    return new JMenuOperator(subMenu);
  }

  /**
   * Waits two seconds if the specified condition get true.
   * 
   * @param condition
   *          the condition to evaluate
   * 
   */
  public static void assertTrue(final Condition condition) {
    assertTrue(condition, 2000L);
  }

  /**
   * Waits timeoutInMillis milliseconds if the specified condition gets true
   * 
   * @param condition
   *          the {@link Condition} to evaluate
   * @param timeoutInMillis
   *          the timeout that specifies how long the method should wait (max)
   * 
   */
  public static void assertTrue(final Condition condition, final long timeoutInMillis) {
    assertNotNull(condition);

    boolean isTrue = false;
    final long startTimeInMillis = System.currentTimeMillis();

    Throwable throwable = null;

    // try each 50ms if the condition is true yet
    for (long elapsedTime = 0; !isTrue && (elapsedTime <= timeoutInMillis); sleep(50), elapsedTime = System
        .currentTimeMillis()
        - startTimeInMillis) {
      try {
        isTrue = condition.isTrue();
      } catch (final Throwable e) {
        isTrue = false;
        throwable = e;
      }
    }

    String message = "";
    if (!isTrue) {
      message = condition.getFailMessage();

      if (throwable != null) {
        message = message + " Throwable: " + throwable.toString() + "\n" + throwable.getMessage();
        System.err.println(message);
        throwable.printStackTrace();
      }

      fail(message);
    }
  }

  /**
   * Defines a condition, that must evaluate to <tt>true</tt>
   * 
   * 
   */
  public static interface Condition {

    public boolean isTrue() throws Throwable;

    /**
     * Returns an error message that is displayed when the condition is not true
     * 
     * @return
     */
    public String getFailMessage();
  }

  // ~~~~~~~~~ INTERNAL ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~++
  private static boolean containsSubMenuItem(final Component[] menuComponents, final String title,
      final boolean menuExpected) {
    if (menuComponents == null) {
      return false;
    }

    int found = 0;

    for (final Component component : menuComponents) {

      if (component instanceof JMenuItem) {
        final JMenuItem menuItem = (JMenuItem) component;
        if (title.equals(menuItem.getText())) {
          final boolean isMenu = (menuItem instanceof JMenu);
          if (isMenu) {
            if (!menuExpected) {
              fail("Menu-Item '" + title + "' expected, but found a menu");
            }
          } else {
            if (menuExpected) {
              fail("Menu '" + title + "' expected, but found a menu item");
            }
          }
          found++;
        }
      }
    }

    assertFalse("Found " + found + " menu items with title '" + title + "' but expected one", found > 1);

    return (found == 1);
  }

  private static JMenuItem getSubMenuItem(final Component menu, final String path) {
    final String[] pathElements = path.split("\\|");
    Component currentMenu = menu;

    for (final String title : pathElements) {

      if (currentMenu instanceof JMenuBar) {
        final Component[] menuComponents = ((JMenuBar) currentMenu).getComponents();
        currentMenu = findJMenuItem(menuComponents, title);
      } else if (currentMenu instanceof JMenu) {
        final Component[] menuComponents = ((JMenu) currentMenu).getMenuComponents();
        currentMenu = findJMenuItem(menuComponents, title);
      } else {
        fail("MenuItem '" + currentMenu + "' must be of type JMenu or JMenuBar while looking for path-element '"
            + title + "' in path '" + path + "'");
      }
    }

    return (JMenuItem) currentMenu;
  }

  private static JMenuItem findJMenuItem(final Component[] menuComponents, final String title) {
    for (final Component component : menuComponents) {
      if (component instanceof JMenuItem) {
        final JMenuItem candidate = (JMenuItem) component;
        if (title.equals(candidate.getText())) {
          return candidate;
        }
      }
    }
    fail("No Menu with title '" + title + "' found");
    return null; // never reached
  }

  /**
   * Makes the current thread sleeping for millis milliseconds
   * 
   * @param millis
   */
  private static void sleep(final long millis) {
    try {
      Thread.sleep(millis);
    } catch (final InterruptedException e) {
      e.printStackTrace();
    }
  }

}
