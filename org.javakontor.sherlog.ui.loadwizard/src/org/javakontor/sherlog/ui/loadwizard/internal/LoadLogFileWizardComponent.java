package org.javakontor.sherlog.ui.loadwizard.internal;

import static org.javakontor.sherlog.application.menu.MenuConstants.FILE_MENU_ID;
import static org.javakontor.sherlog.application.menu.MenuConstants.FILE_MENU_TARGET_ID;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.javakontor.sherlog.application.action.contrib.ActionContribution;
import org.javakontor.sherlog.application.action.contrib.DefaultActionContribution;
import org.javakontor.sherlog.domain.reader.LogEventReaderFactory;
import org.javakontor.sherlog.domain.store.LogEventStore;
import org.javakontor.sherlog.domain.store.ModifiableLogEventStore;
import org.javakontor.sherlog.ui.loadwizard.LoadLogFileWizardMessages;
import org.javakontor.sherlog.util.Assert;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;

public class LoadLogFileWizardComponent {

  private final Log               _logger = LogFactory.getLog(getClass());

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

    ActionContribution actionContribution = new DefaultActionContribution(FILE_MENU_ID + ".loadLogFile",
        FILE_MENU_TARGET_ID + "(first)", "&Load log file...", LoadLogFileWizardMessages.openLogFileWizardShortcut,
        this._loadLogFileAction);

    this._loadLogFileActionRegistration = this._context.getBundleContext().registerService(
        ActionContribution.class.getName(), actionContribution, null);

    ResetLogEventStoreAction resetLogEventStoreAction = new ResetLogEventStoreAction(this._logEventStore);
    DefaultActionContribution resetLogEventStoreActionContribution = new DefaultActionContribution(FILE_MENU_ID
        + ".resetLogStore", FILE_MENU_TARGET_ID + "(first)", "&Reset logstore", null, resetLogEventStoreAction);

    this._resetLogEventStoreActionRegistration = this._context.getBundleContext().registerService(
        ActionContribution.class.getName(), resetLogEventStoreActionContribution, null);
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
      this._resetLogEventStoreActionRegistration.unregister();
    }

    this._loadLogFileAction = null;
    this._loadLogFileActionRegistration = null;
    this._resetLogEventStoreActionRegistration = null;
  }

}
