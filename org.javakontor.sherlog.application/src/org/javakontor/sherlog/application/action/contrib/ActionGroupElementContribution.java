package org.javakontor.sherlog.application.action.contrib;

/**
 * <p>
 * </p>
 * <p>
 * This class <b>is not intended</b> to be subclassed by clients. To contribute an action or an action group, you can
 * use an instance of type {@link DefaultActionContribution} or {@link DefaultActionGroupContribution}.
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public interface ActionGroupElementContribution {

  public String getId();

  public String getLabel();

  public String getTargetActionGroupId();
}
