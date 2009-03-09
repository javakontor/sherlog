package org.javakontor.sherlog.ui.loadwizard.internal;

import static org.lumberjack.application.menu.MenuConstants.FILE_MENU_ID;
import static org.lumberjack.application.menu.MenuConstants.FILE_MENU_TARGET_ID;

import org.javakontor.sherlog.core.reader.LogEventReaderFactory;
import org.javakontor.sherlog.core.store.ModifiableLogEventStore;
import org.javakontor.sherlog.ui.loadwizard.LoadLogFileWizardContribution;
import org.javakontor.sherlog.ui.loadwizard.LoadLogFileWizardMessages;
import org.lumberjack.application.action.impl.AbstractAction;
import org.lumberjack.application.request.CloseDialogRequestHandler;
import org.lumberjack.application.view.ViewContribution;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class LoadLogFileAction extends AbstractAction {

  private final ModifiableLogEventStore _logEventStore;

  private LogEventReaderFactory         _logEventReaderFactory;

  private final BundleContext           _bundleContext;

  public LoadLogFileAction(BundleContext bundleContext, ModifiableLogEventStore logEventStore) {
    super(FILE_MENU_ID + ".loadLogFile", FILE_MENU_TARGET_ID + "(first)", "&Load log file...");
    this._bundleContext = bundleContext;
    this._logEventStore = logEventStore;
  }

  public void execute() {

    // ~~ create a new LoadLogFileWizardContribution
    LoadLogFileWizardContribution contribution = new LoadLogFileWizardContribution(getLogEventReaderFactory()
        .getSupportedLogEventFlavours());

    // ~~ register the contribution
    ServiceRegistration serviceRegistration = this._bundleContext.registerService(ViewContribution.class.getName(),
        contribution, null);

    // ~~ register a handler that closes the dialog (by unregistering it from the service registry)
    contribution.setSuccessor(new CloseDialogRequestHandler(serviceRegistration));

    System.err.println("_logEventStore:" + this._logEventStore);
    // LogEventReader logEventReader = LoadLogFileWizard.openLoadLogFileWizard(null, getLogEventReaderFactory());
    // if (logEventReader != null) {
    // logEventReader.addLogEventHandler(new BatchLogEventHandler(this._logEventStore));
    // logEventReader.start();
    // }
  }

  @Override
  public String getDefaultShortcut() {
    return LoadLogFileWizardMessages.openLogFileWizardShortcut;
  }

  public LogEventReaderFactory getLogEventReaderFactory() {
    return this._logEventReaderFactory;
  }

  public void setLogEventReaderFactory(LogEventReaderFactory logEventReaderFactory) {
    this._logEventReaderFactory = logEventReaderFactory;
    setEnabled((this._logEventReaderFactory != null));
  }

}
