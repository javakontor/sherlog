package org.javakontor.sherlog.socket.ui;

import static org.javakontor.sherlog.application.action.MenuConstants.FILE_MENU_ID;
import static org.javakontor.sherlog.application.action.MenuConstants.FILE_MENU_TARGET_ID;

import org.javakontor.sherlog.application.action.contrib.ActionContribution;
import org.javakontor.sherlog.application.action.contrib.DefaultActionContribution;
import org.javakontor.sherlog.domain.reader.LogEventReaderFactory;
import org.javakontor.sherlog.domain.store.LogEventStore;
import org.javakontor.sherlog.domain.store.ModifiableLogEventStore;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SocketListenerComponent {

  Logger                          _logger = LoggerFactory.getLogger(getClass());

  private ModifiableLogEventStore _logEventStore;

  private LogEventReaderFactory   _logEventReaderFactory;

  private ComponentContext        _context;

  StartSocketListenerAction       _startSocketListenerAction;

  private ServiceRegistration     _startSocketListenerActionRegistration;

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
    updateMenu();
  }

  /**
   * @param logEventReaderFactory
   *          the LogEventReaderFactory that has been unregistered
   */
  public void unsetLogEventReaderFactory(LogEventReaderFactory logEventReaderFactory) {
    this._logEventReaderFactory = null;
    updateMenu();
  }

  protected void activate(ComponentContext componentContext) {
    this._logger.debug("activate");
    this._context = componentContext;
    registerMenus();
  }

  private void registerMenus() {

    _startSocketListenerAction = new StartSocketListenerAction(_logEventStore, _context.getBundleContext());
    _startSocketListenerAction.setLogEventReaderFactory(_logEventReaderFactory);

    ActionContribution actionContribution = new DefaultActionContribution(FILE_MENU_ID + ".socketListener",
        FILE_MENU_TARGET_ID, "&Open Socket Listener", null, this._startSocketListenerAction);

    this._startSocketListenerActionRegistration = this._context.getBundleContext().registerService(
        ActionContribution.class.getName(), actionContribution, null);

  }

  public synchronized void updateMenu() {
    this._logger.debug("-> updateLoadMenu, _logEventReaderFactory: " + this._logEventReaderFactory);
    if (this._startSocketListenerAction == null) {
      return;
    }
    this._startSocketListenerAction.setLogEventReaderFactory(this._logEventReaderFactory);
  }

  public synchronized void removeMenus() {
    if (this._startSocketListenerActionRegistration != null) {
      this._startSocketListenerActionRegistration.unregister();
    }

    if (_startSocketListenerAction != null) {
      _startSocketListenerAction.deactivate();
    }

    this._startSocketListenerAction = null;
    this._startSocketListenerActionRegistration = null;
  }

  /**
   * @param componentContext
   */
  protected void deactivate(ComponentContext componentContext) {
    this._logger.debug(" -> deactivate");
    removeMenus();
    this._context = null;
  }

}
