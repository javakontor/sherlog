package org.javakontor.sherlog.ui.logview;

import org.javakontor.sherlog.core.store.ModifiableLogEventStore;
import org.javakontor.sherlog.ui.logview.detailview.LogEventDetailModel;
import org.javakontor.sherlog.ui.logview.filterview.LogEventFilterModel;
import org.javakontor.sherlog.ui.logview.tableview.LogEventTableModel;
import org.javakontor.sherlog.util.Assert;
import org.javakontor.sherlog.application.mvc.AbstractModel;
import org.javakontor.sherlog.application.mvc.DefaultReasonForChange;
import org.javakontor.sherlog.application.mvc.Model;
import org.javakontor.sherlog.application.request.RequestHandler;

/**
 * <p>
 * The {@link Model} for the {@link LogViewContribution}.
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 * @author Gerd W&uuml;therich (gerd@gerd-wuetherich.de)
 */
public class LogModel extends AbstractModel<LogModel, DefaultReasonForChange> implements RequestHandler {

  /** the {@link LogEventDetailModel} */
  private final LogEventDetailModel _logEventDetailModel;

  /** the {@link LogEventTableModel} */
  private final LogEventTableModel  _logEventTableModel;

  /** the {@link LogEventFilterModel} */
  private final LogEventFilterModel _logEventFilterModel;

  /**
   * <p>
   * Creates a new instance of type {@link LogModel}.
   * </p>
   * 
   * @param logEventStore
   *          the (modifiable) log event store
   */
  public LogModel(ModifiableLogEventStore logEventStore) {
    Assert.notNull(logEventStore);

    this._logEventDetailModel = new LogEventDetailModel();
    this._logEventTableModel = new LogEventTableModel(logEventStore);
    this._logEventFilterModel = new LogEventFilterModel(logEventStore);
  }

  /**
   * <p>
   * Returns the {@link LogEventDetailModel}.
   * </p>
   * 
   * @return the {@link LogEventDetailModel}.
   */
  public LogEventDetailModel getLogEventDetailModel() {
    return this._logEventDetailModel;
  }

  /**
   * <p>
   * Returns the {@link LogEventTableModel}.
   * </p>
   * 
   * @return the {@link LogEventTableModel}.
   */
  public LogEventTableModel getLogEventTableModel() {
    return this._logEventTableModel;
  }

  /**
   * <p>
   * Returns the {@link LogEventFilterModel}.
   * </p>
   * 
   * @return the {@link LogEventFilterModel}.
   */
  public LogEventFilterModel getLogEventFilterModel() {
    return this._logEventFilterModel;
  }
}
