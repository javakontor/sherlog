package org.javakontor.sherlog.ui.loadwizard.internal;

import org.javakontor.sherlog.core.reader.LogEventReaderFactory;
import org.javakontor.sherlog.core.store.LogEventStore;
import org.javakontor.sherlog.core.store.ModifiableLogEventStore;
import org.javakontor.sherlog.util.Assert;
import org.lumberjack.application.action.impl.ActionGroupElementServiceHelper;
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

  protected void activate(ComponentContext componentContext) {
    _logger.debug("activate");
    this._context = componentContext;
    registerLoadMenu();
  }

  protected void deactivate(ComponentContext componentContext) {
    _logger.debug(" -> deactivate");
    removeLoadMenu();
    this._context = null;
  }

  public void setLogEventStore(LogEventStore logEventStore) {
    _logger.debug(" -> setLogEventStore: " + logEventStore);
    this._logEventStore = (ModifiableLogEventStore) logEventStore;
  }

  public void unsetLogEventStore(LogEventStore logEventStore) {
    _logger.debug(" -> unsetLogEventStore: " + logEventStore);
    this._logEventStore = null;
  }

  public void setLogEventReaderFactory(LogEventReaderFactory logEventReaderFactory) {
    this._logEventReaderFactory = logEventReaderFactory;
    updateLoadMenu();
  }

  public void unsetLogEventReaderFactory(LogEventReaderFactory logEventReaderFactory) {
    this._logEventReaderFactory = null;
    updateLoadMenu();
  }

  public synchronized void registerLoadMenu() {
    Assert.notNull(_context);
    Assert.notNull(_logEventStore);
    Assert.assertTrue(_loadLogFileAction == null, "Property 'loadLogFileAction' must be null");
    _logger.debug("-> registerLoadMenu, _logEventReaderFactory: " + _logEventReaderFactory);

    _loadLogFileAction = new LoadLogFileAction(_logEventStore);
    _loadLogFileAction.setLogEventReaderFactory(_logEventReaderFactory);

    _loadLogFileActionRegistration = ActionGroupElementServiceHelper.registerAction(_context.getBundleContext(),
        _loadLogFileAction, _loadLogFileAction.getServiceProperties());
  }

  public synchronized void updateLoadMenu() {
    _logger.debug("-> updateLoadMenu, _logEventReaderFactory: " + _logEventReaderFactory);
    if (_loadLogFileAction == null) {
      return;
    }
    _loadLogFileAction.setLogEventReaderFactory(_logEventReaderFactory);
  }

  public synchronized void removeLoadMenu() {
    if (_loadLogFileActionRegistration != null) {
      _loadLogFileActionRegistration.unregister();
    }
    _loadLogFileAction = null;
    _loadLogFileActionRegistration = null;
  }

}
