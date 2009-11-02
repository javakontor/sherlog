package org.javakontor.sherlog.ui.managementagent;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JSplitPane;
import javax.swing.text.View;

import org.javakontor.sherlog.application.mvc.AbstractView;
import org.javakontor.sherlog.application.mvc.DefaultReasonForChange;
import org.javakontor.sherlog.ui.managementagent.detailview.BundleDetailView;
import org.javakontor.sherlog.ui.managementagent.tableview.BundleListView;

/**
 * <p>
 * The {@link View} for the {@link LogViewContribution}.
 * </p>
 *
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class ManagementAgentView extends AbstractView<ManagementAgentModel, DefaultReasonForChange> {

  /** serialVersionUID */
  private static final long serialVersionUID = 1L;

  /** the horizontal split pane */
  private JSplitPane        _horizontalSplitPane;

  // /** the vertical split pane */
  // private JSplitPane _verticalSplitPane;

  private BundleListView    _bundleListView;

  private BundleDetailView  _bundleDetailView;

  /**
   * <p>
   * Creates a new instance of type {@link ManagementAgentView}.
   * </p>
   *
   * @param model
   *          the {@link ManagementAgentModel}
   */
  public ManagementAgentView(ManagementAgentModel model) {
    super(model);
  }

  /**
   * @see org.javakontor.sherlog.application.mvc.AbstractView#setUp()
   */
  @Override
  protected void setUp() {
    this._bundleListView = new BundleListView(getModel().getBundleListModel());
    this._bundleDetailView = new BundleDetailView(getModel().getBundleDetailModel());

    setLayout(new BorderLayout());
    setPreferredSize(new Dimension(700, 350));

    // this._verticalSplitPane = new JSplitPane();
    // this._verticalSplitPane.setDividerLocation(200);
    // this._verticalSplitPane.setOrientation(javax.swing.JSplitPane.VERTICAL_SPLIT);
    // this._verticalSplitPane.setOneTouchExpandable(true);
    // this._verticalSplitPane.setRightComponent(this._logEventDetailView);
    // this._verticalSplitPane.setLeftComponent(this._logEventTableView);

    this._horizontalSplitPane = new JSplitPane();
    this._horizontalSplitPane.setOneTouchExpandable(true);
    this._horizontalSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
    this._horizontalSplitPane.setLeftComponent(this._bundleListView);
    this._horizontalSplitPane.setRightComponent(this._bundleDetailView);
    this._horizontalSplitPane.setDividerLocation(175);


    add(this._horizontalSplitPane, BorderLayout.CENTER);

  }

  public BundleListView getBundleListView() {
    return this._bundleListView;
  }

  public BundleDetailView getBundleDetailView() {
    return this._bundleDetailView;
  }

  /**
   * Resets the horizontal Splitpane to it's preferred size.
   *
   * @see JSplitPane#resetToPreferredSizes()
   */
  public void resetHorizontalSplitPane() {
    _horizontalSplitPane.resetToPreferredSizes();
  }

}
