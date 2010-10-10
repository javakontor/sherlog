package org.javakontor.sherlog.application.internal.menu;

import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.KeyStroke;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javakontor.sherlog.application.action.AbstractToggleAction;
import org.javakontor.sherlog.application.action.Action;
import org.javakontor.sherlog.application.action.ActionGroupType;
import org.javakontor.sherlog.application.action.ToggleAction;
import org.javakontor.sherlog.application.action.contrib.ActionContribution;
import org.javakontor.sherlog.application.action.contrib.ActionGroupContribution;
import org.javakontor.sherlog.application.action.contrib.ActionGroupElementContribution;
import org.javakontor.sherlog.application.action.set.ActionSet;
import org.javakontor.sherlog.application.internal.action.LocatableActionGroupElement;
import org.javakontor.sherlog.application.internal.action.LocatableElement;
import org.javakontor.sherlog.application.internal.action.LocatableElementSorter;
import org.javakontor.sherlog.util.Assert;

/**
 * 
 * MenuBuilder builds a menu including all submenus from a set of ActionGroupElements
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class MenuBuilder {

  private final Log      _logger = LogFactory.getLog(getClass());

  private final MenuRoot _menuRoot;

  public MenuBuilder(final MenuRoot menuRoot) {
    this._menuRoot = menuRoot;
  }

  public void build(final ActionSet actionSet) {
    this._menuRoot.clear();

    if (actionSet == null) {
      return;
    }

    // get items for the menubar
    final Collection<ActionGroupElementContribution> rootActionGroupContent = actionSet.getRootActionGroupContent();
    if (this._logger.isDebugEnabled()) {
      this._logger.debug("Items for menubar: " + rootActionGroupContent);
    }

    // sort items that are located in the menubar
    final List<LocatableElement> locatableList = createLocatableWrapper(rootActionGroupContent);
    final LocatableElementSorter sorter = new LocatableElementSorter();
    final List<LocatableElement> sortedItems = sorter.sort(locatableList);

    /**
     * The items for the menu root
     */
    final List<JMenuItem> menuRootItems = new LinkedList<JMenuItem>();

    // build each menu including submenus
    for (final LocatableElement locatableObject : sortedItems) {
      final LocatableActionGroupElement menuItem = (LocatableActionGroupElement) locatableObject;

      if (menuItem.isAction()) {
        final JMenuItem subMenuItem = createMenuItem(menuItem.getAction(), false);
        menuRootItems.add(subMenuItem);
      } else {
        final Collection<JComponent> subMenuItems = buildSubMenuItems(actionSet, menuItem);
        menuRootItems.add(createMenu(menuItem.getActionGroup(), subMenuItems));
      }
    }
    this._menuRoot.addAll(menuRootItems);
  }

  private List<LocatableElement> createLocatableWrapper(
      final Collection<ActionGroupElementContribution> actionGroupElements) {
    final List<LocatableElement> result = new LinkedList<LocatableElement>();

    for (final ActionGroupElementContribution actionGroupElement : actionGroupElements) {
      result.add(new LocatableActionGroupElement(actionGroupElement));
    }

    return result;
  }

  /**
   * Creates a JMenu for the specified ActionGroup with the specified items
   * 
   * @param actionGroup
   * @param subMenuItems
   * @return the menu that has been created
   */
  private JMenu createMenu(final ActionGroupContribution actionGroup, final Collection<JComponent> subMenuItems) {
    final JMenu subMenu = new JMenu();
    setTextAndMnemonic(subMenu, actionGroup.getLabel());

    for (final JComponent menuItem : subMenuItems) {
      subMenu.add(menuItem);
    }

    if (subMenuItems.isEmpty()) {
      subMenu.setEnabled(false);
    }

    return subMenu;

  }

  /**
   * Builds (recursive) all menuItems for the menu that is identified via the specified parentActionGroupElement
   * 
   * 
   * @param actionSet
   *          The ActionSet that defines the content of this menu
   * @param parentActionGroupElement
   *          The parent ActionGroupElement. Must contain an ActionGroup
   * @return all menu items that build this menu including separators
   */
  private Collection<JComponent> buildSubMenuItems(final ActionSet actionSet,
      final LocatableActionGroupElement parentActionGroupElement) {
    Assert.assertTrue((parentActionGroupElement != null) && parentActionGroupElement.isActionGroup(),
        "parentMenu must be an ActionGroup");
    final Collection<JComponent> result = new LinkedList<JComponent>();
    final ActionGroupContribution actionGroup = parentActionGroupElement.getActionGroup();

    final Collection<ActionGroupElementContribution> actionGroupContent = actionSet.getActionGroupContent(actionGroup
        .getId());
    if (actionGroupContent == null) {
      return result;
    }

    // Sort menu entries
    final List<LocatableElement> locatableList = createLocatableWrapper(actionGroupContent);
    final LocatableElementSorter sorter = new LocatableElementSorter();
    final List<LocatableElement> sortedItems = sorter.sort(locatableList);

    // ButtonGroup for radiogroup-ActionGroups
    ButtonGroup buttonGroup = null;
    if (actionGroup.getType() == ActionGroupType.radiogroup) {
      buttonGroup = new ButtonGroup();
    }

    // needed for separator
    boolean isFirst = true;
    boolean afterGroup = false;

    // Add items to menu
    for (final LocatableElement object : sortedItems) {
      final LocatableActionGroupElement subMenuItem = (LocatableActionGroupElement) object;

      if (subMenuItem.isAction()) {
        if (afterGroup) {
          result.add(new JPopupMenu.Separator());
        }
        final ActionContribution action = subMenuItem.getAction();
        // Create the menuitem according to the type of the Action and its ActionGroup
        final JMenuItem jSubMenuItem = createMenuItem(action, (buttonGroup != null));
        if (buttonGroup != null) {
          buttonGroup.add(jSubMenuItem);
        }
        result.add(jSubMenuItem);
        afterGroup = false;
      } else {
        final ActionGroupContribution newActionGroup = subMenuItem.getActionGroup();
        final Collection<JComponent> buildSubMenuItems = buildSubMenuItems(actionSet, subMenuItem);
        if (isFlatGroup(newActionGroup)) {
          // add all items to this menu
          if (!isFirst && (buildSubMenuItems.size() > 0)) {
            result.add(new JPopupMenu.Separator());
          }
          result.addAll(buildSubMenuItems);
        } else {
          if (!isFirst) {
            result.add(new JPopupMenu.Separator());
          }
          final JMenu subMenu = createMenu(newActionGroup, buildSubMenuItems);
          result.add(subMenu);
        }
        afterGroup = true;
      }
      isFirst = false;
    }

    return result;

  }

  protected JMenuItem createMenuItem(final ActionContribution actionContribution, final boolean radioButton) {
    final Action action = actionContribution.getAction();
    // Create a Swing-Action that delegates to "our" action
    final MenuItemAdapter menuItemAdapter = new MenuItemAdapter(action);
    // Create the menuitem according to the type of the Action and its ActionGroup
    JMenuItem jSubMenuItem;
    if (!radioButton) {
      if (action instanceof ToggleAction) {
        jSubMenuItem = new JCheckBoxMenuItem(menuItemAdapter);
      } else {
        jSubMenuItem = new JMenuItem(menuItemAdapter);
      }
    } else {
      jSubMenuItem = new JRadioButtonMenuItem(menuItemAdapter);
    }
    if (action instanceof ToggleAction) {
      jSubMenuItem.addItemListener(new ToggleMenuItemListener(jSubMenuItem, (ToggleAction) action));
    }
    setShortcut(jSubMenuItem, actionContribution);
    setTextAndMnemonic(jSubMenuItem, actionContribution.getLabel());

    return jSubMenuItem;
  }

  public boolean isFlatGroup(final ActionGroupContribution actionGroup) {
    return ((actionGroup.getLabel() == null) || (actionGroup.getLabel().trim().length() == 0));
  }

  /**
   * A {@link javax.swing.AbstractAction} that is used in {@link JMenuItem JMenuItems} to connect the JMenuItems with
   * their {@link ActionContribution}
   */
  class MenuItemAdapter extends javax.swing.AbstractAction implements PropertyChangeListener {
    /**
     * the serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    /**
     * The action of the menu item
     */
    private final Action      _action;

    public MenuItemAdapter(final Action action) {
      super();
      this._action = action;

      setEnabled(action.isEnabled());
      // add an PropertyChangeListener to track changes of the Action's "enabled"-property
      action.addPropertyChangeListener(Action.ENABLED_PROPERTY, this);
    }

    /**
     * [Menu-&gt;Action] Runs {@link ActionContribution#execute()} when the menu item is invoked
     */
    public void actionPerformed(final ActionEvent e) {
      this._action.execute();
    }

    /**
     * [Action-&gt;Menu] Updates the menu item's enabled property when the enabled state of the Action changes
     */
    public void propertyChange(final PropertyChangeEvent evt) {
      setEnabled((Boolean) evt.getNewValue());
    }

  }

  class ToggleMenuItemListener implements ItemListener, PropertyChangeListener {

    /**
     *
     */
    private static final long  serialVersionUID = 1L;

    private final JMenuItem    _menuItem;

    private final ToggleAction _toggleAction;

    public ToggleMenuItemListener(final JMenuItem menuItem, final ToggleAction action) {
      this._menuItem = menuItem;
      this._toggleAction = action;
      this._menuItem.setSelected(action.isActive());
      action.addPropertyChangeListener(AbstractToggleAction.ACTIVE_PROPERTY, this);
    }

    public void itemStateChanged(final ItemEvent e) {
      this._toggleAction.setActive(e.getStateChange() == ItemEvent.SELECTED);
    }

    public void propertyChange(final PropertyChangeEvent evt) {
      this._menuItem.setSelected((Boolean) evt.getNewValue());
    }

  }

  /**
   * Sets the default shortcut for the action
   * 
   * <p>
   * TODO: connect with pref service to allow configuring of shortcuts
   * 
   * @param item
   * @param action
   */
  protected void setShortcut(final JMenuItem item, final ActionContribution action) {
    final String defaultShortcut = action.getDefaultShortcut();

    if (defaultShortcut != null) {
      final KeyStroke keyStroke = KeyStroke.getKeyStroke(defaultShortcut);
      item.setAccelerator(keyStroke);
    }
  }

  protected static void setTextAndMnemonic(final JMenuItem menuItem, final String text) {

    if (text == null) {
      return;
    }

    final StringBuilder textWithoutMnemonic = new StringBuilder();
    Character mnemonic = null;
    for (int i = 0; i < text.length(); i++) {
      final char c = text.charAt(i);
      if (c == '&') {
        if (i < text.length() - 1) {
          mnemonic = text.charAt(i + 1);
          continue;
        }
      }
      textWithoutMnemonic.append(c);
    }

    menuItem.setText(textWithoutMnemonic.toString());

    if (mnemonic != null) {
      menuItem.setMnemonic(mnemonic);
    }
  }

}
