package org.javakontor.sherlog.ui.managementagent.detailview;

import java.awt.BorderLayout;

import javax.swing.JEditorPane;
import javax.swing.JScrollPane;
import javax.swing.text.html.HTMLEditorKit;

import org.javakontor.sherlog.application.mvc.AbstractView;
import org.javakontor.sherlog.application.mvc.DefaultReasonForChange;
import org.javakontor.sherlog.application.mvc.ModelChangedEvent;
import org.javakontor.sherlog.util.ui.EditPopupMenu;
import org.osgi.framework.Bundle;

public class BundleDetailView extends AbstractView<BundleDetailModel, DefaultReasonForChange> {

  /** serialVersionUID */
  private static final long   serialVersionUID = -4222822027918013022L;

  /** used to format the bundle information * */
  private static final String DETAILS_FORMAT   = "<b>Identifier: </b>%1$s" + "<br/><b>Symbolic Name: </b>%2$s"
                                                   + "<br/><b>Version:</b>%3$s" + "<br/><b>Location: </b>%4$s"
                                                   + "<br/><b>Status: </b>%5$s";

  /** the details pane */
  private JEditorPane         _detailsPane;

  /**
   * <p>
   * Creates a new instance of type {@link BundleDetailView}.
   * </p>
   * 
   * @param model
   *          the model
   */
  public BundleDetailView(BundleDetailModel model) {
    super(model);
  }

  @Override
  protected void setUp() {
    setLayout(new BorderLayout());

    this._detailsPane = new JEditorPane();
    this._detailsPane.setEditorKit(new HTMLEditorKit());
    this._detailsPane.setEditable(false);
    EditPopupMenu.createEditPopupMenuFor(_detailsPane);

    JScrollPane scrollPane = new JScrollPane();
    scrollPane.setViewportView(this._detailsPane);
    add(scrollPane, BorderLayout.CENTER);
  }

  @Override
  public void onModelChanged(ModelChangedEvent<BundleDetailModel, DefaultReasonForChange> event) {

    // no event set?
    if (getModel().getBundle() == null) {
      this._detailsPane.setText("(no bundle selected)");
      return;
    }

    // get bundle
    Bundle bundle = getModel().getBundle();

    // render HTML
    String details = String.format(DETAILS_FORMAT, bundle.getBundleId(), bundle.getSymbolicName(), bundle.getHeaders()
        .get("Bundle-Version"), bundle.getLocation(), getStateDescription(bundle.getState()));

    // set the text
    this._detailsPane.setText(details);

    // set first line to top of display
    this._detailsPane.setCaretPosition(0);
  }

  JEditorPane getDetailsPane() {
    return _detailsPane;
  }

  private String getStateDescription(int state) {

    switch (state) {
    case 1:
      return "UNINSTALLED";
    case 2:
      return "INSTALLED";
    case 4:
      return "RESOLVED";
    case 8:
      return "STARTING";
    case 16:
      return "STOPPING";
    case 32:
      return "ACTIVE";
    default:
      return "UNDEFINED";
    }
  }
}
