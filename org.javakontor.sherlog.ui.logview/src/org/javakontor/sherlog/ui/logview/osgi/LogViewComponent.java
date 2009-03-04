package org.javakontor.sherlog.ui.logview.osgi;

import java.util.Dictionary;
import java.util.Hashtable;
import java.util.Map;

import org.javakontor.sherlog.core.store.LogEventStore;
import org.javakontor.sherlog.ui.logview.LogView;
import org.osgi.service.component.ComponentFactory;
import org.osgi.service.component.ComponentInstance;

/**
 * <p>
 * The {@link LogViewComponent} manages a {@link LogView} for all registered {@link LogEventStore}s
 * </p>
 * 
 * @author Nils Hartmann (nils@nilshartmann.net)
 */
public class LogViewComponent {

  /** the component factory */
  private ComponentFactory                            _componentFactory;

  /** the map of all component instances */
  private final Map<LogEventStore, ComponentInstance> _componentInstances;

  /**
   * <p>
   * </p>
   */
  public LogViewComponent() {
    this._componentInstances = new Hashtable<LogEventStore, ComponentInstance>();
  }

  /**
   * <p>
   * <p>
   * 
   * @param componentFactory
   */
  public void bindComponentFactory(ComponentFactory componentFactory) {
    _componentFactory = componentFactory;
  }

  /**
   * <p>
   * <p>
   * 
   * @param componentFactory
   */
  public void unbindComponentFactory(ComponentFactory componentFactory) {
    _componentFactory = null;
  }

  /**
   * <p>
   * <p>
   * 
   * @param logEventStore
   */
  @SuppressWarnings("unchecked")
  public void bindLogEventStore(LogEventStore logEventStore) {
    Dictionary dictionary = new Hashtable();
    dictionary.put("logEventStore", logEventStore);
    ComponentInstance componentInstance = _componentFactory.newInstance(dictionary);
    _componentInstances.put(logEventStore, componentInstance);
    System.err.println(componentInstance);
  }

  /**
   * <p>
   * <p>
   * 
   * @param logEventStore
   */
  public void unbindLogEventStore(LogEventStore logEventStore) {
    _componentInstances.remove(logEventStore).dispose();
  }
}
