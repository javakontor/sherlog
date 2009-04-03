package org.javakontor.sherlog.util.logging;

import org.osgi.service.log.LogReaderService;

/**
 * Registeres a {@link LogEntryForwarder} for all registered OSGi Log (Reaer) Services
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class LogComponent {
  private final LogEntryForwarder _logEntryForwarder;

  public LogComponent() {
    _logEntryForwarder = new LogEntryForwarder();
  }

  public void addLogReaderService(LogReaderService logReaderService) {
    logReaderService.addLogListener(_logEntryForwarder);
  }

  public void removeLogReaderService(LogReaderService logReaderService) {
    logReaderService.removeLogListener(_logEntryForwarder);
  }
}
