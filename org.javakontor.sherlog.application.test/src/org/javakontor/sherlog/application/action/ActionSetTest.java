package org.javakontor.sherlog.application.action;

import org.javakontor.sherlog.application.action.impl.ActionSetImpl;
import static org.mockito.Mockito.*;

import junit.framework.TestCase;

public class ActionSetTest extends TestCase {
  
  public void test_AddAction() {
    
    ActionSetImpl actionSetImpl = new ActionSetImpl("menubar");
    
    Action action = mock(Action.class);
    when(action.getId()).thenReturn("id");
    when(action.getTargetActionGroupId()).thenReturn("menubar");
    
    actionSetImpl.addAction(action);
    
    Action wrongAction = mock(Action.class);
    when(wrongAction.getId()).thenReturn("wrongAction");
    when(wrongAction.getTargetActionGroupId()).thenReturn("wrongLocation");
    
    actionSetImpl.addAction(wrongAction);
  }

}
