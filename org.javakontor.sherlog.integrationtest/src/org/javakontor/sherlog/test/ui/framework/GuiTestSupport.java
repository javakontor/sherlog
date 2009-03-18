package org.javakontor.sherlog.test.ui.framework;

import junit.framework.Assert;

import org.javakontor.sherlog.application.view.ViewContribution;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;

public class GuiTestSupport extends Assert {

  private final GuiTestContext _testContext;

  public GuiTestSupport(GuiTestContext testContext) {
    assertNotNull(testContext);
    _testContext = testContext;
  }

  protected BundleContext getBundleContext() {
    return _testContext.getBundleContext();
  }

  public void assertNoViewContributionRegistered(String dialogName) throws InvalidSyntaxException {
    ServiceReference[] serviceReferences = getBundleContext().getServiceReferences(ViewContribution.class.getName(),
        null);

    for (ServiceReference serviceReference : serviceReferences) {
      ViewContribution viewContribution = (ViewContribution) getBundleContext().getService(serviceReference);
      if (viewContribution != null) {
        assertFalse("There should be no ViewContribution with name '" + dialogName + "' registered", dialogName
            .equals(viewContribution.getDescriptor().getName()));
      }
    }
  }

  /**
   * Asserts that exactly one ViewContribution with the given dialogName is currently registered at the Service Registry
   * 
   * @param dialogName
   * @throws InvalidSyntaxException
   */
  public void assertViewContributionRegistered(String dialogName) throws InvalidSyntaxException {
    ServiceReference[] serviceReferences = getBundleContext().getServiceReferences(ViewContribution.class.getName(),
        null);

    ViewContribution matchingContribution = null;

    for (ServiceReference serviceReference : serviceReferences) {
      ViewContribution viewContribution = (ViewContribution) getBundleContext().getService(serviceReference);
      if (viewContribution != null && dialogName.equals(viewContribution.getDescriptor().getName())) {
        if (matchingContribution == null) {
          matchingContribution = viewContribution;
        } else {
          // more than one contribution with same name -> error
          fail("There should be exactly one ViewContribution registered with dialogname '" + dialogName + "'");
        }
      }
    }

    assertNotNull("There should be a ViewContribution registered with dialogName '" + dialogName + "'",
        matchingContribution);
  }

}
