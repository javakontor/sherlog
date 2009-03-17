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
import org.javakontor.sherlog.application.action.Action;
import org.javakontor.sherlog.application.action.ActionGroup;
import org.javakontor.sherlog.application.action.ActionGroupContent;
import org.javakontor.sherlog.application.action.ActionGroupType;
import org.javakontor.sherlog.application.action.ActionSet;
import org.javakontor.sherlog.application.action.ToggleAction;
import org.javakontor.sherlog.application.action.impl.AbstractAction;
import org.javakontor.sherlog.application.action.impl.AbstractToggleAction;
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

  public MenuBuilder(MenuRoot menuRoot) {
    this._menuRoot = menuRoot;
  }

  public void build(final ActionSet actionSet) {
    this._menuRoot.clear();

    if (actionSet == null) {
      return;
    }

    // get items for the menubar
    ActionGroupContent menuItems = actionSet.getRootActionGroupContent();
    if (this._logger.isDebugEnabled()) {
      this._logger.debug("Items for menubar: " + menuItems);
    }
    if (menuItems == null) {
      return;
    }

    // sort items that are located in the menubar
    List<LocatableElement> locatableList = new LinkedList<LocatableElement>(menuItems.getAll());
    LocatableElementSorter sorter = new LocatableElementSorter();
    List<LocatableElement> sortedItems = sorter.sort(locatableList);

    /**
     * The items for the menu root
     */
    final List<JMenuItem> menuRootItems = new LinkedList<JMenuItem>();

    // build each menu including submenus
    for (LocatableElement locatableObject : sortedItems) {
      LocatableActionGroupElement menuItem = (LocatableActionGroupElement) locatableObject;

      if (menuItem.isAction()) {
        JMenuItem subMenuItem = createMenuItem(menuItem.getAction(), false);
        menuRootItems.add(subMenuItem);
      } else {

        // if (menuItem.isAction()) {
        // System.err.println("WARNING! An Action (" + menuItem.getElement().getId()
        // + "' has been added to menubar. Only ActionGroups are supported. Action will be ignored");
        // continue;
        // }
        Collection<JComponent> subMenuItems = buildSubMenuItems(actionSet, menuItem);
        menuRootItems.add(createMenu(menuItem.getActionGroup(), subMenuItems));
      }
    }
    this._menuRoot.add(menuRootItems);
  }

  /**
   * Creates a JMenu for the specified ActionGroup with the specified items
   * 
   * @param actionGroup
   * @param subMenuItems
   * @return the menu that has been created
   */
  private JMenu createMenu(ActionGroup actionGroup, Collection<JComponent> subMenuItems) {
    JMenu subMenu = new JMenu();
    setTextAndMnemonic(subMenu, actionGroup.getLabel());

    for (JComponent menuItem : subMenuItems) {
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
      LocatableActionGroupElement parentActionGroupElement) {
    Assert.assertTrue((parentActionGroupElement != null) && parentActionGroupElement.isActionGroup(),
        "parentMenu must be an ActionGroup");
    final Collection<JComponent> result = new LinkedList<JComponent>();
    ActionGroup actionGroup = parentActionGroupElement.getActionGroup();

    ActionGroupContent subMenuItems = actionSet.getActionGroupContent(actionGroup.getId());
    if (subMenuItems == null) {
      return result;
    }

    // Sort menu entries
    List<LocatableElement> locatableList = new LinkedList<LocatableElement>(subMenuItems.getAll());
    LocatableElementSorter sorter = new LocatableElementSorter();
    List<LocatableElement> sortedItems = sorter.sort(locatableList);

    // ButtonGroup for radiogroup-ActionGroups
    ButtonGroup buttonGroup = null;
    if (actionGroup.getType() == ActionGroupType.radiogroup) {
      buttonGroup = new ButtonGroup();
    }

    // needed for separator
    boolean isFirst = true;
    boolean afterGroup = false;

    // Add items to menu
    for (LocatableElement object : sortedItems) {
      LocatableActionGroupElement subMenuItem = (LocatableActionGroupElement) object;

      if (subMenuItem.isAction()) {
        if (afterGroup) {
          result.add(new JPopupMenu.Separator());
        }
        Action action = subMenuItem.getAction();
        // Create the menuitem according to the type of the Action and its ActionGroup
        JMenuItem jSubMenuItem = createMenuItem(action, (buttonGroup != null));
        if (buttonGroup != null) {
          buttonGroup.add(jSubMenuItem);
        }
        result.add(jSubMenuItem);
        afterGroup = false;
      } else {
        ActionGroup newActionGroup = subMenuItem.getActionGroup();
        Collection<JComponent> buildSubMenuItems = buildSubMenuItems(actionSet, subMenuItem);
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
          JMenu subMenu = createMenu(newActionGroup, buildSubMenuItems);
          result.add(subMenu);
        }
        afterGroup = true;
      }
      isFirst = false;
    }

    return result;

  }

  protected JMenuItem createMenuItem(Action action, boolean radioButton) {
    // Create a Swing-Action that delegates to "our" action
    MenuItemAdapter menuItemAdapter = new MenuItemAdapter(action);
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
    setShortcut(jSubMenuItem, action);
    setTextAndMnemonic(jSubMenuItem, action.getLabel());

    return jSubMenuItem;
  }

  public boolean isFlatGroup(ActionGroup actionGroup) {
    return ((actionGroup.getLabel() == null) || (actionGroup.getLabel().trim().length() == 0));
  }

  /**
   * A {@link javax.swing.AbstractAction} that is used in {@link JMenuItem JMenuItems} to connect the JMenuItems with
   * their {@link Action}
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

    public MenuItemAdapter(Action action) {
      super();
      this._action = action;

      setEnabled(action.isEnabled());
      // add an PropertyChangeListener to track changes of the Action's "enabled"-property
      action.addPropertyChangeListener(AbstractAction.ENABLED_PROPERTY, this);
    }

    /**
     * [Menu-&gt;Action] Runs {@link Action#execute()} when the menu item is invoked
     */
    public void actionPerformed(ActionEvent e) {
      this._action.execute();
    }

    /**
     * [Action-&gt;Menu] Updates the menu item's enabled property when the enabled state of the Action changes
     */
    public void propertyChange(PropertyChangeEvent evt) {
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

    public ToggleMenuItemListener(JMenuItem menuItem, ToggleAction action) {
      this._menuItem = menuItem;
      this._toggleAction = action;
      this._menuItem.setSelected(action.isActive());
      action.addPropertyChangeListener(AbstractToggleAction.ACTIVE_PROPERTY, this);
    }

    public void itemStateChanged(ItemEvent e) {
      this._toggleAction.setActive(e.getStateChange() == ItemEvent.SELECTED);
    }

    public void propertyChange(PropertyChangeEvent evt) {
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
  protected void setShortcut(JMenuItem item, Action action) {
    String defaultShortcut = action.getDefaultShortcut();

    if (defaultShortcut != null) {
      KeyStroke keyStroke = KeyStroke.getKeyStroke(defaultShortcut);
      item.setAccelerator(keyStroke);
    }
  }

  protected static void setTextAndMnemonic(JMenuItem menuItem, String text) {

    if (text == null) {
      return;
    }

    StringBuilder textWithoutMnemonic = new StringBuilder();
    Character mnemonic = null;
    for (int i = 0; i < text.length(); i++) {
      char c = text.charAt(i);
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
