package org.javakontor.sherlog.ui.loadwizard.internal;

import org.javakontor.sherlog.core.reader.LogEventReaderFactory;
import org.javakontor.sherlog.core.store.LogEventStore;
import org.javakontor.sherlog.core.store.ModifiableLogEventStore;
import org.javakontor.sherlog.util.Assert;
import org.javakontor.sherlog.application.action.impl.ActionGroupElementServiceHelper;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoadLogFileWizardComponent {

  private final Logger            _logger = LoggerFactory.getLogger(getClass());

  private ComponentContext        _context;

  private ModifiableLogEventStore _logEventStore;

  private LogEventReaderFactory   _logEventReaderFactory;

  private LoadLogFileAction       _loadLogFileAction;

  private ServiceRegistration     _loadLogFileActionRegistration;

  private ServiceRegistration     _resetLogEventStoreActionRegistration;

  protected void activate(ComponentContext componentContext) {
    this._logger.debug("activate");
    this._context = componentContext;
    registerMenus();
  }

  /**
   * @param componentContext
   */
  protected void deactivate(ComponentContext componentContext) {
    this._logger.debug(" -> deactivate");
    removeMenus();
    this._context = null;
  }

  public void setLogEventStore(LogEventStore logEventStore) {
    this._logger.debug(" -> setLogEventStore: " + logEventStore);
    this._logEventStore = (ModifiableLogEventStore) logEventStore;
  }

  public void unsetLogEventStore(LogEventStore logEventStore) {
    this._logger.debug(" -> unsetLogEventStore: " + logEventStore);
    this._logEventStore = null;
  }

  public void setLogEventReaderFactory(LogEventReaderFactory logEventReaderFactory) {
    this._logEventReaderFactory = logEventReaderFactory;
    updateLoadMenu();
  }

  /**
   * @param logEventReaderFactory
   *          the LogEventReaderFactory that has been unregistered
   */
  public void unsetLogEventReaderFactory(LogEventReaderFactory logEventReaderFactory) {
    this._logEventReaderFactory = null;
    updateLoadMenu();
  }

  public synchronized void registerMenus() {
    Assert.notNull(this._context);
    Assert.notNull(this._logEventStore);
    Assert.assertTrue(this._loadLogFileAction == null, "Property 'loadLogFileAction' must be null");
    this._logger.debug("-> registerLoadMenu, _logEventReaderFactory: " + this._logEventReaderFactory);

    this._loadLogFileAction = new LoadLogFileAction(this._context.getBundleContext(), this._logEventStore);
    this._loadLogFileAction.setLogEventReaderFactory(this._logEventReaderFactory);

    this._loadLogFileActionRegistration = ActionGroupElementServiceHelper.registerAction(this._context
        .getBundleContext(), this._loadLogFileAction);

    ResetLogEventStoreAction resetLogEventStoreAction = new ResetLogEventStoreAction(this._logEventStore);
    this._resetLogEventStoreActionRegistration = ActionGroupElementServiceHelper.registerAction(this._context
        .getBundleContext(), resetLogEventStoreAction);
  }

  public synchronized void updateLoadMenu() {
    this._logger.debug("-> updateLoadMenu, _logEventReaderFactory: " + this._logEventReaderFactory);
    if (this._loadLogFileAction == null) {
      return;
    }
    this._loadLogFileAction.setLogEventReaderFactory(this._logEventReaderFactory);
  }

  public synchronized void removeMenus() {
    if (this._loadLogFileActionRegistration != null) {
      this._loadLogFileActionRegistration.unregister();
    }

    if (this._resetLogEventStoreActionRegistration != null) {
      this._resetLogEventStoreActionRegistration = null;
    }

    this._loadLogFileAction = null;
    this._loadLogFileActionRegistration = null;
    this._resetLogEventStoreActionRegistration = null;
  }

}
