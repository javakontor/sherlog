package org.javakontor.sherlog.ui.loadwizard.internal;

import org.javakontor.sherlog.application.action.AbstractAction;
import org.javakontor.sherlog.application.request.CloseDialogRequestHandler;
import org.javakontor.sherlog.application.view.ViewContribution;
import org.javakontor.sherlog.domain.reader.LogEventReaderFactory;
import org.javakontor.sherlog.domain.store.ModifiableLogEventStore;
import org.javakontor.sherlog.ui.loadwizard.LoadLogFileWizardContribution;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class LoadLogFileAction extends AbstractAction {

  private final ModifiableLogEventStore _logEventStore;

  private LogEventReaderFactory         _logEventReaderFactory;

  private final BundleContext           _bundleContext;

  public LoadLogFileAction(BundleContext bundleContext, ModifiableLogEventStore logEventStore) {
    this._bundleContext = bundleContext;
    this._logEventStore = logEventStore;
  }

  public void execute() {

    // ~~ create a new LoadLogFileWizardContribution
    LoadLogFileWizardContribution contribution = new LoadLogFileWizardContribution(this._logEventStore,
        getLogEventReaderFactory());

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

  public LogEventReaderFactory getLogEventReaderFactory() {
    return this._logEventReaderFactory;
  }

  public void setLogEventReaderFactory(LogEventReaderFactory logEventReaderFactory) {
    this._logEventReaderFactory = logEventReaderFactory;
    setEnabled((this._logEventReaderFactory != null));
  }
}
