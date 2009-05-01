package org.javakontor.sherlog.application.action;

/**
 * Defines several constants that can be used from clients to avoid using hardcoded Strings
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public interface MenuConstants {

  /**
   * The id of the menubar.
   * 
   * <p>
   * Must be used to place an {@link org.javakontor.sherlog.application.action.ActionGroupContribution} on the ApplicationWindow's menubar
   */
  public static final String MENUBAR_ID            = "menubar";

  /**
   * The id of the "Window" menu.
   * 
   * @see #WINDOW_MENU_TARGET_ID <p>
   */
  public static final String WINDOW_MENU_ID        = "windowMenu";

  /**
   * The targetActionGroupId of the "Window" menu.
   * 
   * <p>
   * Must be used to place an {@link org.javakontor.sherlog.application.action.AbstractActionGroupElementContribution} into the "Window" menu
   */
  public static final String WINDOW_MENU_TARGET_ID = MENUBAR_ID + "/" + WINDOW_MENU_ID;

  /**
   * The id of the "Tools" submenu inside the "Window" menu.
   * 
   * <p>
   * 
   * @see #TOOLS_MENU_TARGET_ID
   */
  public static final String TOOLS_MENU_ID         = "toolsMenu";

  /**
   * The targetActionGroupId of the "Tools" submenu inside the "Window" menu.
   * 
   * <p>
   * Must be used to place an {@link org.javakontor.sherlog.application.action.AbstractActionGroupElementContribution} into the Tools submenu
   */
  public static final String TOOLS_MENU_TARGET_ID  = MENUBAR_ID + "/" + TOOLS_MENU_ID;

  /**
   * The id of the "File" menu.
   * 
   * @see #FILE_MENU_TARGET_ID <p>
   */
  public static final String FILE_MENU_ID          = "fileMenu";

  /**
   * The id of the "File" menu.
   * 
   * <p>
   * Must be used to place an {@link org.javakontor.sherlog.application.action.AbstractActionGroupElementContribution} inton the "File" menu
   */
  public static final String FILE_MENU_TARGET_ID   = MENUBAR_ID + "/" + FILE_MENU_ID;

}
