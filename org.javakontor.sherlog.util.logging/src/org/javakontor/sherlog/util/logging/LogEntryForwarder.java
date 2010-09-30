package org.javakontor.sherlog.util.logging;

import org.osgi.service.log.LogEntry;
import org.osgi.service.log.LogListener;
import org.osgi.service.log.LogService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogEntryForwarder implements LogListener {

  public void logged(LogEntry entry) {

    Logger logger = LoggerFactory.getLogger(entry.getBundle().getSymbolicName());

    switch (entry.getLevel()) {
    case LogService.LOG_DEBUG:
      if (logger.isDebugEnabled()) {
        logger.debug(entry.getMessage(), entry.getException());
      }
      break;
    case LogService.LOG_INFO:
      if (logger.isInfoEnabled()) {
        logger.info(entry.getMessage(), entry.getException());
      }
      break;
    case LogService.LOG_WARNING:
      if (logger.isWarnEnabled()) {
        logger.warn(entry.getMessage(), entry.getException());
      }
      break;
    case LogService.LOG_ERROR:
      if (logger.isErrorEnabled()) {
        logger.error(entry.getMessage(), entry.getException());
      }
      break;
    default:
      System.err.println("Unkown loglevel:" + entry.getLevel());
    }
  }

}
