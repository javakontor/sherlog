package org.javakontor.sherlog.application.action;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;

import java.util.Collection;

import junit.framework.TestCase;

import org.javakontor.sherlog.application.action.contrib.ActionContribution;
import org.javakontor.sherlog.application.action.contrib.ActionGroupContribution;
import org.javakontor.sherlog.application.action.contrib.ActionGroupElementContribution;
import org.javakontor.sherlog.application.action.contrib.DefaultActionContribution;
import org.javakontor.sherlog.application.action.contrib.DefaultActionGroupContribution;
import org.javakontor.sherlog.application.internal.action.ActionSetImpl;

public class ActionSetTest extends TestCase {

  private static int _id = 1;

  public void test_EmptyActionSet() {
    // Create an ActionSet
    ActionSetImpl actionSetImpl = new ActionSetImpl("menubar");

    // make sure, accessing the root gets an empty collection
    assertNotNull(actionSetImpl.getRootActionGroupContent());
    assertTrue(actionSetImpl.getRootActionGroupContent().isEmpty());

    // make sure accessing a sub-actiongroup returns null
    assertNull(actionSetImpl.getActionGroupContent("not.there"));
  }

  public void test_AddAction() {
    ActionSetImpl actionSetImpl = new ActionSetImpl("menubar");

    // add an action
    ActionContribution action = newActionContribution();
    actionSetImpl.addAction(action);

    // add an action without explizit root id
    ActionContribution actionWithOutRoot = newActionContribution("newAction", "menubar");
    actionSetImpl.addAction(actionWithOutRoot);

    // try to add an action with a wrong root id
    ActionContribution wrongAction = newActionContribution("wrongAction", "wrongRoot/wrongLocation");
    try {
      actionSetImpl.addAction(wrongAction);
      fail("No exception!");
    } catch (IllegalStateException ex) {
      // ok
    }

    // make sure both actions have been added
    Collection<ActionGroupElementContribution> rootActionGroupContent = actionSetImpl.getRootActionGroupContent();
    assertEquals(asList(action, actionWithOutRoot), rootActionGroupContent);

    assertNull(actionSetImpl.getActionGroupContent("not.there"));
  }

  public void test_ActionTwice() {
    ActionSetImpl actionSetImpl = new ActionSetImpl("menubar");

    // add an action
    ActionContribution action = newActionContribution();
    actionSetImpl.addAction(action);

    try {
      actionSetImpl.addAction(action);
      fail("No exception");
    } catch (IllegalStateException ex) {
      // ok
    }
  }

  public void test_AddActionGroup() {
    ActionSetImpl actionSetImpl = new ActionSetImpl("menubar");

    // create an ActionGroup 'ag-1' and add it to the ActionSet root
    ActionGroupContribution actionGroup = newActionGroupContribution("ag-1");
    actionSetImpl.addActionGroup(actionGroup);

    // make sure ActionSet only contains the ActionGroup
    assertEquals(asList(actionGroup), actionSetImpl.getRootActionGroupContent());

    // add an Action to the ActoinGroup
    ActionContribution action = newActionContribution("a-1", "menubar/ag-1");
    actionSetImpl.addAction(action);

    // make sure it's correctly added
    assertEquals(asList(actionGroup), actionSetImpl.getRootActionGroupContent());
    assertEquals(asList(action), actionSetImpl.getActionGroupContent("ag-1"));

    // remove the action group
    actionSetImpl.removeActionGroup(actionGroup);
    assertTrue(actionSetImpl.getRootActionGroupContent().isEmpty());
    assertNull(actionSetImpl.getActionGroupContent("ag-1"));

    // re-add the action group (action should still be there)
    actionSetImpl.addActionGroup(actionGroup);
    assertEquals(asList(action), actionSetImpl.getActionGroupContent("ag-1"));
  }

  public void test_StaticActionGroup() {
    // Create an ActionSet
    ActionSetImpl actionSetImpl = new ActionSetImpl("menubar");

    ActionContribution staticAction = newActionContribution("a-1", "menubar/ag-1");
    DefaultActionGroupContribution staticActionProvider = newActionGroupContribution("ag-1");
    staticActionProvider.setStaticActionContributions(new ActionContribution[] { staticAction });
    // MyStaticActionProvider staticActionProvider = newActionGroupElement(MyStaticActionProvider.class, "ag-1");
    // when(staticActionProvider.getActions()).thenReturn();

    // make sure, root only contains the action group, as it's static action member
    // has been added to another actiongroup (TODO: should this be allowed?)
    actionSetImpl.addActionGroup(staticActionProvider);
    assertEquals(asList(staticActionProvider), actionSetImpl.getRootActionGroupContent());
    assertEquals(asList(staticAction), actionSetImpl.getActionGroupContent("ag-1"));

    // add more actions to the actiongroup
    ActionContribution action = newActionContribution("a-2", "menubar/ag-1");
    actionSetImpl.addAction(action);
    assertEquals(asList(staticAction, action), actionSetImpl.getActionGroupContent("ag-1"));

    // removing the actiongroup should remove it's static actions as well
    actionSetImpl.removeActionGroup(staticActionProvider);
    assertTrue(actionSetImpl.getRootActionGroupContent().isEmpty());
    assertNull(actionSetImpl.getActionGroupContent("ag-1"));
  }

  public void test_AddAction_Before_ActionGroup() {
    // Create an ActionSet
    ActionSetImpl actionSetImpl = new ActionSetImpl("menubar");

    // create and add an action that has a targetActionGroup that is not part of the ActionSet yet
    ActionContribution action = newActionContribution("a-1", "menubar/ag-1");
    actionSetImpl.addAction(action);

    assertEquals(asList(), actionSetImpl.getRootActionGroupContent());
    assertNull(actionSetImpl.getActionGroupContent("ag-1"));

    // now add the appropriate ActionGroup
    ActionGroupContribution actionGroup = newActionGroupContribution("ag-1");
    actionSetImpl.addActionGroup(actionGroup);

    assertEquals(asList(actionGroup), actionSetImpl.getRootActionGroupContent());
    assertEquals(asList(action), actionSetImpl.getActionGroupContent("ag-1"));
  }

  public void test_StaticFinalActionGroup() {
    // Create an ActionSet
    ActionSetImpl actionSetImpl = new ActionSetImpl("menubar");

    ActionContribution staticAction = newActionContribution("a-1", "menubar/ag-1");
    DefaultActionGroupContribution staticActionProvider = newStaticActionGroupContribution("ag-1", staticAction);
    staticActionProvider.setFinal(true);

    // make sure, root only contains the action group, as it's static action member
    // has been added to another actiongroup (TODO: should this be allowed?)
    actionSetImpl.addActionGroup(staticActionProvider);
    assertEquals(asList(staticActionProvider), actionSetImpl.getRootActionGroupContent());
    assertEquals(asList(staticAction), actionSetImpl.getActionGroupContent("ag-1"));

    // try to add more actions to the actiongroup (should not be ignored since it's a 'final' group)
    ActionContribution action = newActionContribution("a-2", "menubar/ag-1");
    actionSetImpl.addAction(action);
    assertEquals(asList(staticAction), actionSetImpl.getActionGroupContent("ag-1"));

    // removing the actiongroup should remove it's static actions as well
    actionSetImpl.removeActionGroup(staticActionProvider);
    assertTrue(actionSetImpl.getRootActionGroupContent().isEmpty());
    assertNull(actionSetImpl.getActionGroupContent("ag-1"));
  }

  public void test_AddAction_Before_StaticFinalActionGroup() {
    // Create an ActionSet
    ActionSetImpl actionSetImpl = new ActionSetImpl("menubar");

    // add an Action
    ActionContribution firstAction = newActionContribution("a-0", "menubar/ag-1");
    actionSetImpl.addAction(firstAction);

    // add a final, static ActionGroup

    ActionContribution staticAction = newActionContribution("a-1", "menubar/ag-1");
    DefaultActionGroupContribution staticActionProvider = newStaticActionGroupContribution("ag-1", staticAction);
    staticActionProvider.setFinal(true);

    actionSetImpl.addActionGroup(staticActionProvider);

    // since ag-1 is final, it should only contain it's static members, not a-0 which has
    // been added above
    assertEquals(asList(staticAction), actionSetImpl.getActionGroupContent("ag-1"));

  }

  public static ActionContribution newActionContribution(String... props) {
    DefaultActionContribution actionContribution = new DefaultActionContribution((props.length > 0 ? props[0]
        : nextId()), props.length > 1 ? props[1] : "menubar/menubar", "label", null, new DummyAction());
    return actionContribution;
    // return newActionGroupElement(Action.class, props);
  }

  static class DummyAction extends AbstractAction {
    public DummyAction() {
      super();
    }

    public void execute() {
    }
  }

  public static DefaultActionGroupContribution newActionGroupContribution(String... props) {
    DefaultActionGroupContribution actionGroupContribution = new DefaultActionGroupContribution(
        (props.length > 0 ? props[0] : nextId()), props.length > 1 ? props[1] : "menubar/menubar", "label");
    return actionGroupContribution;
    // return newActionGroupElement(ActionGroupContribution.class, props);
  }

  public static DefaultActionGroupContribution newStaticActionGroupContribution(String id,
      ActionContribution... staticActionContributions) {
    DefaultActionGroupContribution contribution = newActionGroupContribution(id);
    contribution.setStaticActionContributions(staticActionContributions);
    return contribution;

  }

  /**
   * Props must be listed in order:
   * <ol>
   * <li>id</li>
   * <li>targetActiongroupId (optional)</li>
   * </ol>
   * 
   * @param props
   * @return
   */
  public static <T extends ActionGroupElementContribution> T newActionGroupElement(Class<T> classToMock,
      String... props) {
    ActionGroupElementContribution action = mock(classToMock);
    when(action.getId()).thenReturn((props.length > 0 ? props[0] : nextId()));
    when(action.getTargetActionGroupId()).thenReturn(props.length > 1 ? props[1] : "menubar/menubar");
    return classToMock.cast(action);
  }

  protected static String nextId() {
    return String.valueOf(++_id);
  }

}
