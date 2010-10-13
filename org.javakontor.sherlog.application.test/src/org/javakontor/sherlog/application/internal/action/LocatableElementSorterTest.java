package org.javakontor.sherlog.application.internal.action;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.List;

import org.javakontor.sherlog.application.action.contrib.ActionGroupElementContribution;
import org.junit.Test;

public class LocatableElementSorterTest {

  private final List<LocatableElement> _unsortedElements       = new LinkedList<LocatableElement>();

  private final LocatableElementSorter _locatableElementSorter = new LocatableElementSorter();

  private List<LocatableElement>       _sortedElements;

  @Test
  public void firstElementShouldBeOnFirstPosition() {
    addElement("action-1", "actiongroup-1");
    addElement("action-2", "actiongroup-1(first)");
    addElement("action-3", "actiongroup-1(first)");

    sort();

    assertThat(indexOf("action-2"), is(0));
  }

  @Test
  public void lastElementShouldBeAtLastPosition() {
    addElement("action-3", "actiongroup-1");
    addElement("action-1", "actiongroup-1(last)");
    addElement("action-2", "actiongroup-1");

    sort();

    assertThat(indexOf("action-1"), is(2));
  }

  @Test
  public void beforeElementShouldBeBeforeOther() {
    addElement("action-3", "actiongroup-1");
    addElement("action-1", "actiongroup-1(before:action-3)");
    addElement("action-2", "actiongroup-1");

    sort();

    assertTrue(indexOf("action-1") < indexOf("action-3"));
  }

  @Test
  public void afterElementShouldBeAfterOther() {
    addElement("action-3", "actiongroup-1");
    addElement("action-1", "actiongroup-1(after:action-2)");
    addElement("action-2", "actiongroup-1");

    sort();

    assertTrue(indexOf("action-1") > indexOf("action-2"));
  }

  @Test
  public void anElementMustNotBeBeforeAFirstElement() {
    addElement("action-2", "actiongroup-1(before:action-1");
    addElement("action-3", "actiongroup-1");
    addElement("action-1", "actiongroup-1(first)");

    sort();

    assertThat(indexOf("action-1"), is(0));
  }

  @Test
  public void anElementMustNotBeAfterALastElement() {
    addElement("action-2", "actiongroup-1(last)");
    addElement("action-3", "actiongroup-1");
    addElement("action-1", "actiongroup-1(after:action-2)");

    sort();

    assertThat(indexOf("action-2"), is(2));
  }

  @Test
  public void complexOrder() throws Exception {
    addElement("action-1", "actiongroup-1");
    addElement("action-2", "actiongroup-1(first)");
    addElement("action-3", "actiongroup-1(before:action-1)");
    addElement("action-4", "actiongroup-1(before:action-2)");
    addElement("action-l1", "actiongroup-1(last)");
    addElement("action-l3", "actiongroup-1(before:action-l2)");
    addElement("action-5", "actiongroup-1(after:action-7)");
    addElement("action-6", "actiongroup-1(after:action-7)");
    addElement("action-7", "actiongroup-1");
    addElement("action-verylast", "actiongroup-1(last)");
    addElement("action-8", "actiongroup-1(before:xxx)");
    addElement("action-9", "actiongroup-1(before:action-9)");
    addElement("action-l2", "actiongroup-1(after:action-l1)");

    sort();

    assertThat(indexOf("action-2"), is(0));
    assertThat(indexOf("action-verylast"), is(11));
    assertTrue(indexOf("action-3") < indexOf("action-1"));
    assertTrue(indexOf("action-5") > indexOf("action-7"));
    assertTrue(indexOf("action-6") > indexOf("action-7"));
    // as l1 is marked as 'last', 'l2' cannot go behind
    assertTrue(indexOf("action-l2") < indexOf("action-l1"));

  }

  protected void sort() {
    _sortedElements = _locatableElementSorter.sort(_unsortedElements);
    assertThat(_sortedElements, is(notNullValue()));
    assertThat(_sortedElements.size(), is(_unsortedElements.size()));
  }

  protected int indexOf(String actionId) {
    int position = 0;

    for (LocatableElement locatableElement : _sortedElements) {
      if (actionId.equals(locatableElement.getElement().getId())) {
        return position;
      }
      position++;
    }

    fail("action " + actionId + " not found");
    return -1;

  }

  protected void addElement(final String id, final String target) {
    LocatableActionGroupElement element = new LocatableActionGroupElement(new ActionGroupElementContribution() {

      public String getTargetActionGroupId() {
        return target;
      }

      public String getLabel() {
        // TODO Auto-generated method stub
        return "Action: " + id;
      }

      public String getId() {
        // TODO Auto-generated method stub
        return id;
      }

      @Override
      public String toString() {
        return id + " @ " + target;
      }

    });

    _unsortedElements.add(element);
  }

}
