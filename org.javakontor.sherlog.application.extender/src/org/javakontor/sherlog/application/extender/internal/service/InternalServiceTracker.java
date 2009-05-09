package org.javakontor.sherlog.application.extender.internal.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.javakontor.sherlog.application.extender.internal.model.ActionEntry;
import org.javakontor.sherlog.application.extender.internal.model.Reference;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

public class InternalServiceTracker extends ServiceTracker {

  private int                     _count = 0;

  private ServiceAwareActionProxy _proxy;

  private ActionEntry             _actionEntry;

  private Method                  _bindMethod;

  private Method                  _unbindMethod;

  public InternalServiceTracker(ActionEntry actionEntry, Reference reference, ServiceAwareActionProxy proxy)
      throws InvalidSyntaxException {
    super(actionEntry.getBundle().getBundleContext(), getFilter(actionEntry, reference), null);

    _proxy = proxy;

    _actionEntry = actionEntry;

    initBindMethods(reference);
  }

  private static Filter getFilter(ActionEntry actionEntry, Reference reference) throws InvalidSyntaxException {
    // create the filter string
    String filterString = "(objectClass=" + reference.getInterface() + ")";
    if (reference.getFilter() != null && !"".equals(reference.getFilter().trim())) {
      filterString = "(&" + filterString + reference.getFilter() + ")";
    }

    // create the filter
    return actionEntry.getBundle().getBundleContext().createFilter(filterString);
  }

  public int getCount() {
    return _count;
  }

  @Override
  public Object addingService(ServiceReference reference) {

    Object service = super.addingService(reference);

    //
    if (_bindMethod != null) {
      try {
        _bindMethod.invoke(_actionEntry.getAction(), service);
      } catch (IllegalArgumentException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    _count++;
    _proxy.setEnabled(_proxy.servicesSatisfied());

    return super.addingService(reference);
  }

  @Override
  public void removedService(ServiceReference reference, Object service) {
    super.removedService(reference, service);

    if (_unbindMethod != null) {
      try {
        _unbindMethod.invoke(_actionEntry.getAction(), service);
      } catch (IllegalArgumentException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }

    _count--;
    _proxy.setEnabled(_proxy.servicesSatisfied());

  }

  private void initBindMethods(Reference reference) {

    try {
      Class<?> parameterType = _actionEntry.getBundle().loadClass(reference.getInterface());
      try {
        _bindMethod = _actionEntry.getAction().getClass().getMethod(reference.getBind(), new Class[] { parameterType });
      } catch (Exception e) {
        //
      }
      try {
        _unbindMethod = _actionEntry.getAction().getClass().getMethod(reference.getUnbind(), parameterType);
      } catch (Exception e) {
        //
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
