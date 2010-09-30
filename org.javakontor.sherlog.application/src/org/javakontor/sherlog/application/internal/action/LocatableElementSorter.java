package org.javakontor.sherlog.application.internal.action;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * Sorts a list of actions according to their position-strings.
 * 
 * <p>
 * Currently supported:
 * <ul>
 * <li><b><tt>first</tt></b> - adds an item to the front of the locations. If there are more items with <tt>first</tt>
 * position, they are grouped together in an unknown order at the beginning of the list.</li>
 * <li><b><tt>before:ref-id</tt></b> - adds an item before the item that is referenced by <tt>ref-id</tt> in the list.
 * If the referenced item doesn't exists in the list or is itself positioned with <tt>first</tt> or <tt>last</tt> the
 * item is placed between the <tt>first</tt> and <tt>last</tt> items.</li>
 * <li><b><tt>before:ref-id</tt></b> - adds an item after the item that is referenced by <tt>ref-id</tt> in the list. If
 * the referenced item doesn't exists in the list or is itself positioned with <tt>first</tt> or <tt>last</tt> the item
 * is placed between the <tt>first</tt> and <tt>last</tt> items.</li>
 * <li><b><tt>last</tt></b> - adds an item at the end of the locations. If there are more items with <tt>end</tt>
 * position, they are grouped together in an unknown order at the end of the list.</li>
 * </ul>
 * <p>
 * If an item cannot be positioned according to this rules (for example because there are circular references), the
 * exact location where the item will be stored is an undefined position between the after the items positioned with
 * <tt>first</tt> and before the items positioned with <tt>last</tt>
 * 
 * <p>
 * This implementation is based on
 * org.eclipse.ui.internal.menus.WorkbenchMenuService.addContributionsToManager(IServiceLocator, Set,
 * ContributionManager, String, boolean, List)
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class LocatableElementSorter {

  public List<LocatableElement> sort(List<LocatableElement> unsortedLocations) {

    final List<String> sortedById = new LinkedList<String>();
    sortedById.add("FIRST");
    sortedById.add("LAST");

    // Put all locations in hashtable for converting back list of IDs to action locations
    final Map<String, LocatableElement> _locationMap = new Hashtable<String, LocatableElement>();

    // Holds list of Location that cannot be ordered (yet)
    List<LocatableElement> retryList = new ArrayList<LocatableElement>();

    for (LocatableElement unsortedLocation : unsortedLocations) {

      _locationMap.put(unsortedLocation.getElement().getId(), unsortedLocation);

      if (!processAdditions(sortedById, unsortedLocation)) {
        retryList.add(unsortedLocation);
      }
    }

    // OK, iteratively loop through entries whose URI's could not
    // be resolved until we either run out of entries or the list
    // doesn't change size (indicating that the remaining entries
    // can never be resolved).
    boolean done = retryList.size() == 0;
    while (!done) {
      // Clone the retry list and clear it
      List<LocatableElement> curRetry = new ArrayList<LocatableElement>(retryList);
      int retryCount = retryList.size();
      retryList.clear();

      // Walk the current list seeing if any entries can now be resolved
      for (Iterator<LocatableElement> iterator = curRetry.iterator(); iterator.hasNext();) {
        LocatableElement location = iterator.next();
        if (!processAdditions(sortedById, location)) {
          retryList.add(location);
        }

        // We're done if the retryList is now empty (everything done) or
        // if the list hasn't changed at all (no hope)
        done = (retryList.size() == 0) || (retryList.size() == retryCount);
      }
    }

    final List<LocatableElement> result = new LinkedList<LocatableElement>();

    for (String sortedId : sortedById) {
      if ("FIRST".equals(sortedId)) {
        continue;
      }
      if ("LAST".equals(sortedId)) {
        // add all remaining items that couldn't be sorted for some reasons
        // to the end of the menu, but before the "last()" items
        result.addAll(retryList);
        continue;
      }
      result.add(_locationMap.get(sortedId));
    }

    return result;
  }

  private boolean processAdditions(List<String> allItems, LocatableElement unsortedLocation) {
    final int idx = getInsertionIndex(allItems, unsortedLocation);
    if (idx == -1) {
      return false; // can't process (yet)
    }

    allItems.add(idx, unsortedLocation.getElement().getId());
    // itemsAdded.add(unsortedLocation.getId());
    return true;
  }

  /**
   * @param mgr
   * @param uri
   * @return
   */
  private int getInsertionIndex(List<String> items, LocatableElement locatableObject) {
    int additionsIndex = -1;

    // No Query means 'after=additions' (if ther) or
    // the end of the menu

    final ActionLocation location = locatableObject.getLocation();

    if (!location.hasPosition()) {
      additionsIndex = items.indexOf("LAST");
    } else {
      if (location.isBefore()) {
        additionsIndex = items.indexOf(location.getBefore());
        if (additionsIndex != -1 && additionsIndex < items.indexOf("FIRST")) {
          // make sure "first" items are still the first to go...
          additionsIndex = items.indexOf("FIRST") + 1;
        }
      } else if (location.isAfter()) {
        additionsIndex = items.indexOf(location.getAfter());
        if (additionsIndex != -1) {
          ++additionsIndex;
          if (additionsIndex > items.indexOf("LAST")) {
            additionsIndex = items.indexOf("LAST");
          }
        }
      } else if (location.isFirst()) {
        additionsIndex = items.indexOf("FIRST");
      } else if (location.isLast()) {
        additionsIndex = items.indexOf("LAST") + 1;
      } else {
        throw new RuntimeException("Invalid actionLocation " + location + ": position type unkown");
      }
    }
    return additionsIndex;
  }

  // public static void main(String[] args) {
  // try {
  // final List<ActionLocation> locations = new LinkedList<ActionLocation>();
  // locations.add(new ActionLocation("action-1", "actiongroup-1"));
  // // locations.add(new ActionLocation("action-2", "actiongroup-1(before:action-1)"));
  // locations.add(new ActionLocation("action-2", "actiongroup-1(first)"));
  // locations.add(new ActionLocation("action-3", "actiongroup-1(before:action-1)"));
  // locations.add(new ActionLocation("action-4", "actiongroup-1(before:action-2)"));
  // locations.add(new ActionLocation("action-l1", "actiongroup-1(last)"));
  // locations.add(new ActionLocation("action-l3", "actiongroup-1(before:action-l2)"));
  // locations.add(new ActionLocation("action-5", "actiongroup-1(after:action-7)"));
  // locations.add(new ActionLocation("action-6", "actiongroup-1(after:action-7)"));
  // locations.add(new ActionLocation("action-7", "actiongroup-1"));
  // locations.add(new ActionLocation("action-verylast", "actiongroup-1(last)"));
  // locations.add(new ActionLocation("action-8", "actiongroup-1(before:xxx)"));
  // locations.add(new ActionLocation("action-9", "actiongroup-1(before:action-9)"));
  //
  // locations.add(new ActionLocation("action-l2", "actiongroup-1(after:action-l1)"));
  //
  // LocatableObjectSorter sorter = new LocatableObjectSorter();
  // List<ActionLocation> sort = sorter.sort(locations);
  // for (ActionLocation l : sort) {
  // System.out.println(l.getId() + "\t" + l.getPosition());
  // }
  //
  // } catch (Exception ex) {
  // System.err.println("Exception caught in main: " + ex);
  // ex.printStackTrace();
  // }
  // }

}
